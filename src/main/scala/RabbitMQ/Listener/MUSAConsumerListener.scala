package RabbitMQ.Listener

import RabbitMQ._

/**
 * The methods of this interface are invoked when the respective MUSA events are generated.
 * In other words, when MUSA consumes a message, the method corresponding to the received message
 * is triggered. This is useful when the musajixel interface is used by an external system
 * (for instance, used by flowable)
 *
 * @author Davide A. Guastella (davide.guastella@icar.cnr.it)
 */
trait MUSAConsumerListener {

  def onNotifyEvent(event: JixelEvent): Unit

  def onNotifyEventSummary(event: JixelEventSummary): Unit

  def onEventUpdate(update: JixelEventUpdate): Unit

  def onReceiveJixelReport(report: JixelEventReport): Unit

  def onAddRecipient(r: Recipient): Unit

  def onJixelAckAddRecipient(msg: JixelAckAddRecipient): Unit

  def onJixelAckUrgencyLevel(msg: JixelAckUrgencyLevel): Unit

  def onJixelAckEventSeverity(msg: JixelAckEventSeverity): Unit

  def onJixelAckEventTypology(msg: JixelAckEventTypology): Unit

  def onJixelAckEventDescription(msg: JixelAckEventDescription): Unit

  def onJixelAckUpdateCommType(msg: JixelAckUpdateCommType): Unit

  def onJixelAckUpdateCommTypeError(msg: JixelAckUpdateCommTypeError): Unit

}
