package RabbitMQ.Producer

import RabbitMQ.Config.defaultConfig
import RabbitMQ.Consumer.MUSARabbitMQConsumer
import RabbitMQ.Serializer.JixelEventJsonSerializer
import RabbitMQ._
import com.rabbitmq.client.AMQP.BasicProperties
import com.rabbitmq.client.{Channel, Connection, ConnectionFactory}
import org.slf4j.LoggerFactory

import java.util.UUID

/**
 * @author Davide A. Guastella (davide.guastella@icar.cnr.it)
 */
class MUSARabbitMQProducer extends MUSAProducer {
  val factory = new ConnectionFactory()
  factory.setHost(defaultConfig.rabbitmq_host)
  factory.setUsername(defaultConfig.rabbitmq_username)
  factory.setPassword(defaultConfig.rabbitmq_password)
  factory.setPort(defaultConfig.rabbitmq_port)
  factory.setVirtualHost(defaultConfig.rabbitmq_vhost)

  val connection: Connection = factory.newConnection()
  val channel: Channel = connection.createChannel()

  private val logger = LoggerFactory.getLogger(classOf[MUSARabbitMQProducer])

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
  channel.exchangeDeclare(defaultConfig.exchangeName, "")

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

    val props = new BasicProperties.Builder().correlationId(corrID).replyTo(defaultConfig.jixel2musaQueue).build()

    //jixel publish to topic using the routing key "jixel.musa2jixel.*"
    channel.basicPublish(defaultConfig.exchangeName, defaultConfig.musa2jixelRoutingKey, props, jsonMessage.getBytes("UTF-8"))

    logger.info(Console.BLUE_B + Console.YELLOW + s"[MUSA] Published message to Jixel" + Console.RESET)
    logger.info(Console.BLUE_B + Console.YELLOW + s"[MUSA] Now waiting for ACK from JIXEL...\n" + Console.RESET)

    //println(s"[MUSA] published ${jsonMessage}\n[MUSA]now waiting response...\n")

    // wait for ack message...
    channel.waitForConfirms(defaultConfig.maxWaitForAck).toString
  }

  def close(): Unit = {
    connection.close()
  }

  override def addRecipient(ev: JixelEvent, actors_id: List[Int]): String = {
    val msg = JixelMsgAddRecipient("addRecipient", JixelMsgDataAddRecipient(ev.id, actors_id))
    call(JixelEventJsonSerializer.toJSon(msg))
  }

  override def updateUrgencyLevel(ev: JixelEvent, incident_urgency_id: Int): String = {
    val msg = JixelMsgUrgencyLevel("updateUrgencyLevel", JixelMsgDataUrgencyLevel(ev.id, incident_urgency_id))
    call(JixelEventJsonSerializer.toJSon(msg))
  }

  override def updateEventSeverity(ev: JixelEvent, incident_severity_id: Int): String = {
    val msg = JixelMsgEventSeverity("updateEventSeverity", JixelMsgDataEventSeverity(ev.id, incident_severity_id))
    call(JixelEventJsonSerializer.toJSon(msg))
  }

  override def updateEventTypology(ev: JixelEvent, incident_type_id: Int): String = {
    val msg = JixelMsgEventTypology("updateEventTypology", JixelMsgDataEventTypology(ev.id, incident_type_id))
    call(JixelEventJsonSerializer.toJSon(msg))
  }

  override def updateEventDescription(ev: JixelEvent, description: String): String = {
    val msg = JixelMsgEventDescription("updateEventDescription", JixelMsgDataEventDescription(ev.id, description))
    call(JixelEventJsonSerializer.toJSon(msg))
  }

  override def updateCommType(ev: JixelEvent, incident_msgtype_id: Int): String = {
    val msg = JixelMsgUpdateCommType("updateCommType", JixelMsgDataCommType(ev.id, incident_msgtype_id))
    call(JixelEventJsonSerializer.toJSon(msg))
  }
}
