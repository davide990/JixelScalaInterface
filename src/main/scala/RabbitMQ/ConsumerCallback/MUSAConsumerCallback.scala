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
          logger.info("[MUSA] received ACK from Jixel [add recipient]")
        }
        case r: JixelAckUrgencyLevel => {
          listener.foreach(l => l.onJixelAckUrgencyLevel(r))
          logger.info("[MUSA] received ACK from Jixel [urgency level]")
        }
        case r: JixelAckEventSeverity => {
          listener.foreach(l => l.onJixelAckEventSeverity(r))
          logger.info("[MUSA] received ACK from Jixel [severity level]")
        }
        case r: JixelAckEventTypology => {
          listener.foreach(l => l.onJixelAckEventTypology(r))
          logger.info("[MUSA] received ACK from Jixel [event typology]")
        }
        case r: JixelAckEventDescription => {
          listener.foreach(l => l.onJixelAckEventDescription(r))
          logger.info("[MUSA] received ACK from Jixel [event description]")
        }
        case r: JixelAckUpdateCommType => {
          listener.foreach(l => l.onJixelAckUpdateCommType(r))
          logger.info("[MUSA] received ACK from Jixel [comm type]")
        }
        case r: JixelAckUpdateCommTypeError => {
          listener.foreach(l => l.onJixelAckUpdateCommTypeError(r))
          logger.info("[MUSA] received ACK from Jixel [comm type ERROR]")
        }

        case _ =>
          Config.verbose match {
            case true => throw new Exception(s"[MUSA] Unhandled message: ${parsed}")
            case _ => throw new Exception(s"[MUSA] Unhandled message:")
          }

      }
    } catch {
      case e: Exception =>
        logger.info( s"[MUSA] ERROR, cannot parse. Cause: ${e.toString}")
    } finally {
      // ack the message received from jixel.
      ch.basicAck(delivery.getEnvelope.getDeliveryTag, false)
      latch.countDown()
      //logger.info(Console.BLUE_B + Console.YELLOW + "[MUSA] acknowledge to Jixel OK" + Console.RESET)
      /*Config.verbose match {
        case true => logger.info(Console.BLUE_B + Console.YELLOW + s"[MUSA] Consumed message ${message}" + Console.RESET)
        case false => logger.info(Console.BLUE_B + Console.YELLOW + s"[MUSA] Consumed message from Jixel" + Console.RESET)
      }*/
      logger.info(s"[MUSA] Consumed message from Jixel")
    }
  }
}
