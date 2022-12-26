package RabbitMQ.ConsumerCallback

import RabbitMQ.Listener.MUSAConsumerListener
import RabbitMQ.Serializer.JixelEventJsonSerializer
import RabbitMQ._
import com.rabbitmq.client._
import org.slf4j.LoggerFactory

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

  private val logger = LoggerFactory.getLogger(classOf[MUSAConsumerCallback])

  override def handle(consumerTag: String, delivery: Delivery): Unit = {
    //Get the content of the message
    val message = new String(delivery.getBody, "UTF-8")
    try {
      // parse the message
      val parsed = JixelEventJsonSerializer.fromJson(message)
      // invoke the listener
      parsed match {
        case ev: JixelEvent => {
          listener.foreach(l => l.onNotifyEvent(ev))
          logger.info("[MUSA] received an event from Jixel")
        }
        case ev: JixelEventSummary => {
          listener.foreach(l => l.onNotifyEventSummary(ev))
          logger.info("[MUSA] received an event summary from Jixel")
        }
        case eu: JixelEventUpdate => {
          listener.foreach(l => l.onEventUpdate(eu))
          logger.info("[MUSA] received an event update from Jixel")
        }
        case r: JixelEventReport => {
          listener.foreach(l => l.onReceiveJixelReport(r))
          logger.info("[MUSA] received an event report from Jixel")
        }
        case r: Recipient => {
          listener.foreach(l => l.onAddRecipient(r))
          logger.info("[MUSA] added recipient")
        }

        //------------------------------
        // ACK FROM JIXEL
        //------------------------------
        case r: JixelAckAddRecipient => {
          listener.foreach(l => l.onJixelAckAddRecipient(r))
          logger.info(Console.GREEN_B + Console.WHITE + "[MUSA] received ACK from Jixel [add recipient]" + Console.RESET)
        }
        case r: JixelAckUrgencyLevel => {
          listener.foreach(l => l.onJixelAckUrgencyLevel(r))
          logger.info(Console.GREEN_B + Console.WHITE + "[MUSA] received ACK from Jixel [urgency level]" + Console.RESET)
        }
        case r: JixelAckEventSeverity => {
          listener.foreach(l => l.onJixelAckEventSeverity(r))
          logger.info(Console.GREEN_B + Console.WHITE + "[MUSA] received ACK from Jixel [severity level]" + Console.RESET)
        }
        case r: JixelAckEventTypology => {
          listener.foreach(l => l.onJixelAckEventTypology(r))
          logger.info(Console.GREEN_B + Console.WHITE + "[MUSA] received ACK from Jixel [event typology]" + Console.RESET)
        }
        case r: JixelAckEventDescription => {
          listener.foreach(l => l.onJixelAckEventDescription(r))
          logger.info(Console.GREEN_B + Console.WHITE + "[MUSA] received ACK from Jixel [event description]" + Console.RESET)
        }
        case r: JixelAckUpdateCommType => {
          listener.foreach(l => l.onJixelAckUpdateCommType(r))
          logger.info(Console.GREEN_B + Console.WHITE + "[MUSA] received ACK from Jixel [comm type]" + Console.RESET)
        }
        case r: JixelAckUpdateCommTypeError => {
          listener.foreach(l => l.onJixelAckUpdateCommTypeError(r))
          logger.info(Console.GREEN_B + Console.WHITE + "[MUSA] received ACK from Jixel [comm type ERROR]" + Console.RESET)
        }

        case _ =>
          Config.verbose match {
            case true => throw new Exception(Console.RED_B + Console.WHITE + s"[MUSA] Unhandled message: ${parsed}" + Console.RESET)
            case _ => throw new Exception(Console.RED_B + Console.WHITE + s"[MUSA] Unhandled message:" + Console.RESET)
          }

      }
    } catch {
      case e: Exception =>
        logger.info(Console.RED_B + Console.WHITE + s"[MUSA] ERROR, cannot parse. Cause: ${e.toString}" + Console.RESET)
    } finally {
      // ack the message received from jixel.
      ch.basicAck(delivery.getEnvelope.getDeliveryTag, false)
      latch.countDown()
      //logger.info(Console.BLUE_B + Console.YELLOW + "[MUSA] acknowledge to Jixel OK" + Console.RESET)
      /*Config.verbose match {
        case true => logger.info(Console.BLUE_B + Console.YELLOW + s"[MUSA] Consumed message ${message}" + Console.RESET)
        case false => logger.info(Console.BLUE_B + Console.YELLOW + s"[MUSA] Consumed message from Jixel" + Console.RESET)
      }*/
      logger.info(Console.BLUE_B + Console.YELLOW + s"[MUSA] Consumed message from Jixel" + Console.RESET)
    }
  }
}
