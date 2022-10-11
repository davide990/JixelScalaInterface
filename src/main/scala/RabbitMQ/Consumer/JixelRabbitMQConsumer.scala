package RabbitMQ.Consumer

import RabbitMQ.Config.defaultConfig
import RabbitMQ.ConsumerCallback.JixelConsumerCallback
import RabbitMQ.Listener.JixelConsumerListener
import com.rabbitmq.client.{Channel, Connection, ConnectionFactory}

import java.util.concurrent.CountDownLatch

/**
 * Consumer for Jixel, used to process messages received from MUSA
 *
 * This class must be used jointly with Flowable to control the execution of contingency plans, following the exchange
 * of information via Jixel. To do this, an instance of this class is created in the Flowable engine and associated with
 * a [[JixelConsumerListener]] whose overridden methods invoke Flowable's methods to complete workflow tasks.
 * For example:
 * {{{
 *   new JixelConsumerListener() {
 *@Override
 *public void onCreateEvent(JixelEvent event) {
 *completeTask(event);
 *}
 *
 *@Override
 *public void onAddRecipient(Recipient r) {
 *completeTask(r);
 *}
 *
 *@Override
 *public void onEventUpdate(JixelEventUpdate update) {
 *completeTask(update);
 *}
 *
 *}
 * }}}
 *
 * Here, the consumer listener can be added to an istance of [[JixelRabbitMQConsumer()]], it will be invoked at each
 * message received from Jixel.
 *
 * @author Davide A. Guastella (davide.guastella@icar.cnr.it)
 */
class JixelRabbitMQConsumer {
  private var connection: Connection = null
  private var channel: Channel = null

  def init(): Unit = {
    try {
      val factory = new ConnectionFactory()
      factory.setHost(defaultConfig.rabbitmq_host)
      factory.setUsername(defaultConfig.rabbitmq_username)
      factory.setPassword(defaultConfig.rabbitmq_password)
      factory.setPort(defaultConfig.rabbitmq_port)
      factory.setVirtualHost(defaultConfig.rabbitmq_vhost)

      connection = factory.newConnection()
      channel = connection.createChannel()

      //This setting imposes a limit on the amount of data the server will deliver to consumers before requiring
      // acknowledgements. In the example above, the server will only deliver 1 message and wait for the ack before
      // delivering the next one. Thus, the server will always prefer to send messages to free receivers, making the
      // workload better distributed.
      channel.basicQos(1)
      //channel.confirmSelect

      channel.exchangeDeclare(defaultConfig.exchangeName, "topic")

      //musa2jixel queue used in jixel
      channel.queueDeclare(defaultConfig.musa2jixelQueue, true, false, false, null)

      //channel.queuePurge(defaultConfig.musa2jixelQueue)

      // binding coda<>exchange. l'exchange MUSA smistera i messaggi nella coda per la comunicazione verso jixel
      // Musa subscribes to this queue to receive messages from jixel
      //channel.queueBind(defaultConfig.musa2jixelQueue, defaultConfig.exchangeName, defaultConfig.musa2jixelRoutingKey)
      channel.queueBind(defaultConfig.jixel2musaQueue, defaultConfig.exchangeName, defaultConfig.jixel2musaRoutingKey)
    }
    catch {
      case x: Exception => println("Unable to run consumer: " + x)
    }
  }

  def startConsumerAndAwait(messageCount: Int, listener: Option[JixelConsumerListener] = Option.empty): Unit = {
    try {
      // stop after 100 consumed messages
      val latch = new CountDownLatch(messageCount)
      val serverCallback = new JixelConsumerCallback(channel, latch, listener)
      // if basicAck is used in callback, autoAck should be set to false
      channel.basicConsume(defaultConfig.musa2jixelQueue, false, serverCallback, (_: String) => {})
      println(Console.BLUE_B + Console.YELLOW + " [Jixel] Awaiting requests from MUSA..." + Console.RESET)
      latch.await()
    } catch {
      case e: Exception => e.printStackTrace()
    } finally {
      try {
        connection.close()
      } catch {
        case _: Exception =>
      }
    }

  }

}
