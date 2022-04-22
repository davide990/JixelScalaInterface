package RabbitMQ.MUSA

import com.rabbitmq.client.{Channel, Connection, ConnectionFactory}

import java.util.concurrent.CountDownLatch

object MusaRabbitMQServer {
  private val RPC_QUEUE_NAME = "rpc_queue"
  private val hostAddress = "localhost"

  def main(argv: Array[String]) {
    var connection: Connection = null
    var channel: Channel = null
    try {
      val factory = new ConnectionFactory()
      factory.setHost(hostAddress)
      // Ask for up to 32 channels per connection. Will have an effect as long as the server is configured
      // to use a higher limit, otherwise the server's limit will be used.
      factory.setRequestedChannelMax(32)


      connection = factory.newConnection()
      channel = connection.createChannel()
      channel.queueDeclare(RPC_QUEUE_NAME, false, false, false, null)
      channel.basicQos(1)
      // stop after 100 consumed messages
      //val latch = new CountDownLatch(100)
      val serverCallback = new MUSAServerCallback(channel) //, latch)

      channel.basicConsume(RPC_QUEUE_NAME, false, serverCallback, (_: String) => {})

      while (true) {
        println(" [MUSA] Awaiting RPC requests from Jixel")
        // we don't want to kill the receiver,
        // so we keep him alive waiting for more messages
        Thread.sleep(1000)
      }

      //println(" [MUSA] Awaiting RPC requests from Jixel")
      //latch.await()
    } catch {
      case e: Exception => e.printStackTrace()
    } finally {
      if (connection == null)
        return

      try {
        connection.close()
      } catch {
        case _: Exception =>
      }

    }
  }
}


