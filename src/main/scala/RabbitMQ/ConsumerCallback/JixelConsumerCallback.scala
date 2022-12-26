package RabbitMQ.ConsumerCallback

import RabbitMQ.Listener.JixelConsumerListener
import RabbitMQ.Serializer.JixelEventJsonSerializer
import RabbitMQ._
import com.rabbitmq.client._
import org.slf4j.LoggerFactory

import java.util.concurrent.CountDownLatch

/**
 * Callback invoked when a message is received by Jixel on its rabbitMQ queue.
 *
 * @author Davide A. Guastella (davide.guastella@icar.cnr.it)
 * @param ch
 * @param latch
 * @param listener
 */
class JixelConsumerCallback(val ch: Channel, val latch: CountDownLatch, val listener: Option[JixelConsumerListener] = Option.empty) extends DeliverCallback {

  private val logger = LoggerFactory.getLogger(classOf[JixelConsumerCallback])

  override def handle(consumerTag: String, delivery: Delivery): Unit = {
    val message = new String(delivery.getBody, "UTF-8")
    try {
      // handle the parsed message
      val parsed = JixelEventJsonSerializer.fromJson(message)
      parsed match {
        case ev: JixelEvent => {
          listener.map(l => l.onCreateEvent(ev))
          logger.info("[JIXEL] received an event from MUSA")
        }
        case ev: JixelEventSummary => {
          listener.map(l => l.onCreateEventSummary(ev))
          logger.info("[JIXEL] received an event summary from MUSA")
        }
        case r: Recipient => {
          listener.map(l => l.onAddRecipient(r))
          logger.info("[JIXEL] received an addRecipient request from MUSA")
        }
        case evUpdate: JixelEventUpdate => {
          listener.map(l => l.onEventUpdate(evUpdate))
          logger.info(s"[JIXEL] received an updateEvent (code #${evUpdate.update.updateType}) request from MUSA")
        }

        case _ => throw new Exception("unhandled message")
      }
    } catch {
      case e: Exception => println(Console.BLACK_B + Console.RED + s"[JIXEL] ERROR, cannot parse ${message}: ${e.toString}" + Console.RESET)
    } finally {
      logger.info(Console.GREEN_B + Console.WHITE + "[JIXEL] acknowledge to MUSA")
      // ack the message received from MUSA.
      ch.basicAck(delivery.getEnvelope.getDeliveryTag, false)

      latch.countDown()
      print(Console.RESET)
      Config.verbose match {
        case true => logger.info(s"[JIXEL] Consumed message ${message}")
        case false => logger.info(s"[JIXEL] Consumed message")
      }

    }
  }
}