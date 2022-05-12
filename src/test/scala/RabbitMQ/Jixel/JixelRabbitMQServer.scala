package RabbitMQ.Jixel

import RabbitMQ.Consumer.JixelRabbitMQConsumer

object JixelRabbitMQServer {

  /**
   * MUSA >CONSUMER< WHEN USING rabbitmq_incoming_queue == "jixel2musa"
   *
   */
  def main(argv: Array[String]): Unit = {
    val consumer = new JixelRabbitMQConsumer()
    consumer.init()
    consumer.startConsumerAndAwait(100)
  }
}



