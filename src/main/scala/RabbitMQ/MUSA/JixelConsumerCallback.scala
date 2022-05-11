package RabbitMQ.MUSA

import RabbitMQ.Serializer.JixelEventJsonSerializer
import RabbitMQ.{JixelEvent, JixelEventUpdate, Recipient}
import com.rabbitmq.client._

import java.util.concurrent.CountDownLatch

class JixelConsumerCallback(val ch: Channel, val latch: CountDownLatch) extends DeliverCallback {

  override def handle(consumerTag: String, delivery: Delivery): Unit = {
    val message = new String(delivery.getBody, "UTF-8")
    try {
      // handle the parsed message
      val parsed = JixelEventJsonSerializer.fromJson(message)
      parsed match {
        case _: JixelEvent => println("[JIXEL] received an event from MUSA")
        case _: Recipient => println("[JIXEL] received an addRecipient request from MUSA")
        case evUpdate: JixelEventUpdate => println(s"[JIXEL] received an updateEvent (code #${evUpdate.update.updateType}) request from MUSA")
        case _ => throw new Exception("unhandled message")
      }
    } catch {
      case e: Exception => println(Console.BLACK_B + Console.RED+s"[JIXEL] ERROR, cannot parse ${message}: ${e.toString}"+Console.RESET)
    } finally {
      println(Console.GREEN_B + Console.WHITE + "[JIXEL] acknowledge to MUSA")
      // ack the message received from MUSA.
      ch.basicAck(delivery.getEnvelope.getDeliveryTag, false)

      latch.countDown()
      print(Console.RESET)
      println(s"[JIXEL] Consumed message ${message}")
    }
  }
}