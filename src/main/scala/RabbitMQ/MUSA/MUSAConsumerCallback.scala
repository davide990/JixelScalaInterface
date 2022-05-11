package RabbitMQ.MUSA

import RabbitMQ.Serializer.JixelEventJsonSerializer
import RabbitMQ.{JixelEvent, JixelEventReport, JixelEventUpdate}
import com.rabbitmq.client._

import java.util.concurrent.CountDownLatch

class MUSAConsumerCallback(val ch: Channel, val latch: CountDownLatch) extends DeliverCallback {

  override def handle(consumerTag: String, delivery: Delivery): Unit = {
    val message = new String(delivery.getBody, "UTF-8")
    try {
      // handle the parsed message
      val parsed = JixelEventJsonSerializer.fromJson(message)
      parsed match {
        case _: JixelEvent => println("[MUSA] received an event from Jixel")
        case _: JixelEventUpdate => println("[MUSA] received an event update from Jixel")
        case _: JixelEventReport => println("[MUSA] received an event report from Jixel")
        case _ => throw new Exception("unhandled message")
      }
    } catch {
      case e: Exception => println(s"[MUSA] ERROR, cannot parse ${message}: ${e.toString}")
    } finally {


      println(Console.GREEN_B + Console.WHITE + "[MUSA] acknowledge to Jixel")
      // ack the message received from jixel.
      ch.basicAck(delivery.getEnvelope.getDeliveryTag, false)

      latch.countDown()
      print(Console.RESET)
      println(s"[MUSA] Consumed message ${message}")
    }
  }
}
