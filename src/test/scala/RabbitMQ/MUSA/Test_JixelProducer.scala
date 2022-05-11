package RabbitMQ.MUSA

import com.rabbitmq.client.ConnectionFactory


object Test_JixelProducer {
  private val EXCHANGE_NAME = "topic_logs"
  private val host = "localhost"

  private val rabbitmq_incoming_queue="jixel2musa"
  private val rabbitmq_outcoming_queue="musa2jixel"

  def main(argv: Array[String]): Unit = {

    val factory = new ConnectionFactory
    factory.setHost(host)
    try {
      val connection = factory.newConnection
      val channel = connection.createChannel
      try {
        channel.exchangeDeclare(EXCHANGE_NAME, "topic")
        val routingKey = "jixel."//getRouting(argv)
        val message = "hello world"
        channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes("UTF-8"))
        System.out.println(" [x] Sent '" + routingKey + "':'" + message + "'")
      } finally {
        if (connection != null) connection.close()
        if (channel != null) channel.close()
      }
    }
  }
}
