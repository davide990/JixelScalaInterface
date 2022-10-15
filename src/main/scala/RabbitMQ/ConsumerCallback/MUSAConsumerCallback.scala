package RabbitMQ.ConsumerCallback

import RabbitMQ.Listener.MUSAConsumerListener
import RabbitMQ.Serializer.JixelEventJsonSerializer
import RabbitMQ.{Config, JixelEvent, JixelEventReport, JixelEventSummary, JixelEventUpdate, Recipient}
import com.rabbitmq.client._

import java.util.concurrent.CountDownLatch

/**
 * Callback invoked when a message is received by MUSA on its rabbitMQ queue.
 *
 * @author Davide A. Guastella (davide.guastella@icar.cnr.it)
 * @param ch
 * @param latch
 * @param listener
 */
class MUSAConsumerCallback(val ch: Channel, val latch: CountDownLatch, val listener: Option[MUSAConsumerListener] = Option.empty) extends DeliverCallback {

  override def handle(consumerTag: String, delivery: Delivery): Unit = {
    //Get the content of the message
    val message = new String(delivery.getBody, "UTF-8")
    try {
      // parse the message
      val parsed = JixelEventJsonSerializer.fromJson(message)
      // invoke the listener
      parsed match {
        case ev: JixelEvent => {
          listener.map(l => l.onNotifyEvent(ev))
          println("[MUSA] received an event from Jixel")
        }
        case ev: JixelEventSummary => {
          listener.map(l => l.onNotifyEventSummary(ev))
          println("[MUSA] received an event summary from Jixel")
        }
        case eu: JixelEventUpdate => {
          listener.map(l => l.onEventUpdate(eu))
          println("[MUSA] received an event update from Jixel")
        }
        case r: JixelEventReport => {
          listener.map(l => l.onReceiveJixelReport(r))
          println("[MUSA] received an event report from Jixel")
        }
        case r: Recipient => {
          listener.map(l => l.onAddRecipient(r))
          println("[MUSA] added recipient")
        }
        case _ =>
        Config.verbose match {
          case true => throw new Exception(s"[MUSA] Unhandled message: ${parsed}")
          case _ => throw new Exception(s"[MUSA] Unhandled message:")
        }

      }
    } catch {
      case e: Exception => println(s"[MUSA] ERROR, cannot parse ${message}: ${e.toString}")
    } finally {
      println(Console.GREEN_B + Console.WHITE + "[MUSA] acknowledge to Jixel")
      // ack the message received from jixel.
      ch.basicAck(delivery.getEnvelope.getDeliveryTag, false)
      latch.countDown()
      print(Console.RESET)
      Config.verbose match {
        case true => println(s"[MUSA] Consumed message ${message}")
        case false => println(s"[MUSA] Consumed message")
      }
    }
  }
}
