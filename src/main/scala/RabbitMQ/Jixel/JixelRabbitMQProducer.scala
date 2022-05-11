package RabbitMQ.Jixel

import RabbitMQ.Config.defaultConfig
import RabbitMQ.Serializer.JixelEventJsonSerializer
import RabbitMQ.{JixelEvent, JixelEventReport, JixelEventUpdate}
import com.rabbitmq.client.AMQP.BasicProperties
import com.rabbitmq.client._

import java.util.UUID

/**
 * This is the Jixel part. This is supposed to be used by Anch'ioSegnalo for communicating to MUSA
 *
 * - notify(Event)
 * - updateEvent(Event)
 * - notifyReport(Event,Report)
 *
 * @param argv
 */
class JixelRabbitMQProducer(host: String) {
  val factory = new ConnectionFactory()
  factory.setHost(defaultConfig.rabbitmq_host)
  factory.setUsername(defaultConfig.rabbitmq_username)
  factory.setPassword(defaultConfig.rabbitmq_password)
  factory.setPort(defaultConfig.rabbitmq_port)
  factory.setVirtualHost(defaultConfig.rabbitmq_vhost)

  val connection: Connection = factory.newConnection()
  val channel: Channel = connection.createChannel()

  //This setting imposes a limit on the amount of data the server will deliver to consumers before requiring
  // acknowledgements. In the example above, the server will only deliver 1 message and wait for the ack before
  // delivering the next one. Thus, the server will always prefer to send messages to free receivers, making the
  // workload better distributed.
  channel.basicQos(1)

  //When publisher confirms are enabled on a channel, messages the client publishes are confirmed asynchronously by the
  // broker, meaning they have been taken care of on the server side.
  channel.confirmSelect

  //Declare a new exchange. Topic exchanges route messages to queues based on wildcard matches between the routing key
  // and the routing pattern, which is specified by the queue binding. Messages are routed to one or many queues based
  // on a matching between a message routing key and this pattern.
  channel.exchangeDeclare(defaultConfig.exchangeName, "topic")

  // bind the exchange to the queue with their respective routing key
  // The routing key is a message attribute the exchange looks at when deciding how to route the message to queues
  // (depending on exchange type). The consumers indicate which topics they are interested in (like subscribing to a
  // feed for an individual tag). The consumer creates a queue and sets up a binding with a given routing pattern to the
  // exchange. All messages with a routing key that match the routing pattern are routed to the queue and stay there
  // until the consumer consumes the message.
  // (see https://www.cloudamqp.com/blog/part4-rabbitmq-for-beginners-exchanges-routing-keys-bindings.html)
  channel.queueBind(defaultConfig.jixel2musaQueue, defaultConfig.exchangeName, defaultConfig.jixel2musaRoutingKey)
  channel.queueBind(defaultConfig.musa2jixelQueue, defaultConfig.exchangeName, defaultConfig.musa2jixelRoutingKey)

  /**
   * Notify an event to MUSA
   *
   * @param eventJSon
   */
  def notifyEvent(event: JixelEvent): String = call(JixelEventJsonSerializer.toJSon(event))

  /**
   * Communicate an update to an incident situation
   *
   * @param update
   */
  def updateEvent(update: JixelEventUpdate): String = call(JixelEventJsonSerializer.toJSon(update))

  /**
   * Notify to MUSA a report received by emergency corps
   *
   * @param report
   */
  def notifyReport(report: JixelEventReport): String = call(JixelEventJsonSerializer.toJSon(report))

  /**
   * Sends a message (in Json form) to MUSA
   *
   * @param jsonMessage
   * @return
   */
  private def call(jsonMessage: String): String = {
    // We're going to set it to a unique value for every request. Later, when we receive a message in the callback queue
    // we'll look at this property, and based on that we'll be able to match a response with a request. If we see an
    // unknown correlationId value, we may safely discard the message - it doesn't belong to our requests.
    // (see https://www.rabbitmq.com/tutorials/tutorial-six-java.html)
    val corrID = UUID.randomUUID.toString

    val props = new BasicProperties.Builder().correlationId(corrID).replyTo(defaultConfig.musa2jixelQueue).build()

    //jixel publish to topic using the routing key "jixel.jixel2musa.*"
    channel.basicPublish(defaultConfig.exchangeName, defaultConfig.jixel2musaRoutingKey, props, jsonMessage.getBytes("UTF-8"))

    println(s"[JIXEL] published ${jsonMessage}\n[JIXEL]now waiting response...\n")

    // wait for ack message...
    channel.waitForConfirms(defaultConfig.maxWaitForAck).toString
  }

  def close() {
    connection.close()
  }
}

