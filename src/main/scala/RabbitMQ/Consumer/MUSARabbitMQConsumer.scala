package RabbitMQ.Consumer

import RabbitMQ.Config.defaultConfig
import RabbitMQ.ConsumerCallback.MUSAConsumerCallback
import RabbitMQ.Listener.{JixelConsumerListener, MUSAConsumerListener}
import com.rabbitmq.client.{Channel, Connection, ConnectionFactory}

import java.util.concurrent.CountDownLatch

/**
 * Consumer for MUSA, used to process messages received from Jixel.
 *
 * @author Davide A. Guastella (davide.guastella@icar.cnr.it)
 */
class MUSARabbitMQConsumer {
  var connection: Connection = null
  var channel: Channel = null
  val factory = new ConnectionFactory()

  def init(): Unit = {
    try {
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

      //jixel2musa in musa
      channel.queueDeclare(defaultConfig.jixel2musaQueue, true, false, false, null)

      //channel.queuePurge(defaultConfig.jixel2musaQueue)

      // binding coda<>exchange. l'exchange MUSA smistera i messaggi nella coda per la comunicazione verso jixel
      // Musa subscribes to this queue to receive messages from jixel
      channel.queueBind(defaultConfig.musa2jixelQueue, defaultConfig.exchangeName, defaultConfig.musa2jixelRoutingKey)
      channel.queueBind(defaultConfig.jixel2musaQueue, defaultConfig.exchangeName, defaultConfig.jixel2musaRoutingKey)
    }
    catch {
      case x: Exception => println("Unable to run consumer: " + x)
    }
  }

  def startConsumerAndAwait(messageCount: Int, listener: Option[MUSAConsumerListener]): Unit = {
    try {
      // stop after 100 consumed messages
      val latch = new CountDownLatch(messageCount)
      val serverCallback = new MUSAConsumerCallback(channel, latch, listener)
      // if basicAck is used in callback, autoAck should be set to false
      channel.basicConsume(defaultConfig.jixel2musaQueue, false, serverCallback, (_: String) => {})
      println(Console.BLUE_B + Console.YELLOW + " [MUSA] Awaiting messages from Jixel..." + Console.RESET)
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
