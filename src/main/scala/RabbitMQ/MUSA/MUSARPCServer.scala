package RabbitMQ.MUSA

import RabbitMQ.{JixelEvent, JixelEventReport, JixelEventUpdate}
import RabbitMQ.Serializer.JixelEventSerializer
import com.rabbitmq.client.AMQP.BasicProperties
import com.rabbitmq.client._

import java.util.concurrent.CountDownLatch

class MUSAServerCallback(val ch: Channel/*, val latch: CountDownLatch*/) extends DeliverCallback {

  override def handle(consumerTag: String, delivery: Delivery): Unit = {
    var response = ""
    val replyProps = new BasicProperties.Builder()
      .correlationId(delivery.getProperties.getCorrelationId)
      .build

    try {
      // the message received from Jixel (a JSon string)
      /*val message = new String(delivery.getBody, "UTF-8")
      // handle the parsed message
      val parsed = JixelEventSerializer.fromJson(message)
      parsed match {
        case _:JixelEvent => println("[MUSA] received an event from Jixel")
        case _:JixelEventUpdate => println("[MUSA] received an event update from Jixel")
        case _:JixelEventReport => println("[MUSA] received an event report from Jixel")
        case _ => throw new Exception("unhandled message")
      }*/
      response = "OK"

    } catch {
      case e : Exception => {
        println(s"[MUSA] ERROR: ${e.toString}")
        response = "ERROR"
      }
    } finally {
      ch.basicPublish("", delivery.getProperties.getReplyTo, replyProps, response.getBytes("UTF-8"))
      // println("[MUSA] published finally")
      ch.basicAck(delivery.getEnvelope.getDeliveryTag, false)

      //latch.countDown()
      println(s"[MUSA] OK message ${delivery.getBody}")
      println()
    }
  }
}
