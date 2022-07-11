package RabbitMQ.Launchers.MUSA

import RabbitMQ.Consumer.MUSARabbitMQConsumer


//https://pedrorijo.com/blog/scala-rabbitmq/
object MusaRabbitMQServer {

  /**
   * Start MUSA as a consumer for messages received by Jixel
   */
  def main(argv: Array[String]): Unit = {
    val consumer = new MUSARabbitMQConsumer()
    consumer.init()
    consumer.startConsumerAndAwait(100, Option.empty)
  }
}


