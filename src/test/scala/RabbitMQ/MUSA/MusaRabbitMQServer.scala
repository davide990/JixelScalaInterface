package RabbitMQ.MUSA

import RabbitMQ.Consumer.MUSARabbitMQConsumer


//https://pedrorijo.com/blog/scala-rabbitmq/
object MusaRabbitMQServer {

  /**
   * MUSA >CONSUMER< WHEN USING rabbitmq_incoming_queue == "jixel2musa"
   *
   */
  def main(argv: Array[String]): Unit = {
    val consumer = new MUSARabbitMQConsumer()
    consumer.init()
    consumer.startConsumerAndAwait(100)
  }
}


