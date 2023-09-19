package RabbitMQ

case class RabbitMQConfiguration(
                                  rabbitmq_username: String,
                                  rabbitmq_password: String,
                                  rabbitmq_host: String,
                                  rabbitmq_port: Int,
                                  rabbitmq_vhost: String,
                                  exchangeName: String,
                                  jixel2musaQueue: String,
                                  musa2jixelQueue: String,
                                  jixel2musaRoutingKey: String,
                                  musa2jixelRoutingKey: String,
                                  maxWaitForAck: Int //(in ms) maximum time that Jixel wait to receive an ack from MUSA
                                )

object Config {

  // Local test configuration
  private val rabbitMQLocalConf = RabbitMQConfiguration(
    "guest",
    "guest",
    "localhost",
    5672,
    "/",
    "",//"MUSAJIXEL",
    "jixel2musa",
    "musa2jixel",
    "jixel.jixel_to_musa.*",
    "jixel.musa_to_jixel.*",
    3000)

  // to use with IES working machine
  private val rabbitMQIESConf = RabbitMQConfiguration(
    "musa",
    "musa",
    "192.168.32.1",
    5672,
    "musa",
    "",//"MUSAJIXEL",
    "jixel2musa",
    "musa2jixel",
    "jixel.jixel_to_musa.*",
    "jixel.musa_to_jixel.*",
    3000)

  //val defaultConfig = rabbitMQIESConf
  val defaultConfig = rabbitMQLocalConf

  val verbose = true
}