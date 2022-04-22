package RabbitMQ.Jixel

import RabbitMQ.Serializer.JixelEventSerializer
import RabbitMQ.{JixelEvent, JixelEventReport, JixelEventUpdate}
import com.rabbitmq.client.AMQP.BasicProperties
import com.rabbitmq.client._

import java.util.UUID
import java.util.concurrent.{ArrayBlockingQueue, BlockingQueue}

class ResponseCallback(val corrId: String) extends DeliverCallback {
  val response: BlockingQueue[String] = new ArrayBlockingQueue[String](1)

  override def handle(consumerTag: String, message: Delivery): Unit = {
    message.getProperties.getCorrelationId match {
      case corrId => response.offer(new String(message.getBody, "UTF-8"))
      case _ => print("Unknown correlation ID.")
    }
  }

  def take(): String = response.take();
}

/**
 * This is the Jixel part. This is supposed to be used by Anch'ioSegnalo for communicating to MUSA
 *
 * - notify(Event)
 * - updateEvent(Event)
 * - notifyReport(Event,Report)
 *
 * @param argv
 */
class JixelRPCClient(host: String) {
  val factory = new ConnectionFactory()
  factory.setHost(host)

  val connection: Connection = factory.newConnection()
  val channel: Channel = connection.createChannel()
  val requestQueueName: String = "rpc_queue"
  val replyQueueName: String = channel.queueDeclare().getQueue

  /**
   * Notify an event to MUSA
   *
   * @param eventJSon
   */
  def notifyEvent(event: JixelEvent): String = call(JixelEventSerializer.toJSon(event))

  /**
   * Communicate an update to an incident situation
   *
   * @param update
   */
  def updateEvent(update: JixelEventUpdate): String = call(JixelEventSerializer.toJSon(update))

  /**
   * Notify to MUSA a report received by emergency corps
   *
   * @param report
   */
  def notifyReport(report: JixelEventReport): String = call(JixelEventSerializer.toJSon(report))

  /**
   * Sends a message (in JSon form) to MUSA
   *
   * @param jsonMessage
   * @return
   */
  private def call(jsonMessage: String): String = {
    // This value is used to match response with request. Client associates the request with this ID, so then when
    // the server send back a response, if this has an unknown ID, then the answer is safely removed.
    val corrId = UUID.randomUUID().toString

    //Publish the request on the channel, so that the server (MUSA) can consume it
    val props = new BasicProperties.Builder().correlationId(corrId).replyTo(replyQueueName).build()
    channel.basicPublish("", requestQueueName, props, jsonMessage.getBytes("UTF-8"))

    // Once we received a response from MUSA, consume it
    val responseCallback = new ResponseCallback(corrId)
    channel.basicConsume(replyQueueName, false, responseCallback, (_: String) => {print("cancelled")})


    responseCallback.take()
  }

  def close() {
    connection.close()
  }
}

