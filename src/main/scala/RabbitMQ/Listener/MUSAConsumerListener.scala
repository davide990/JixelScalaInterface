package RabbitMQ.Listener

import RabbitMQ.{JixelEvent, JixelEventReport, JixelEventSummary, JixelEventUpdate, Recipient}

/**
 * The methods of this interface are invoked when the respective MUSA events are generated.
 * In other words, when MUSA consumes a message, the method corresponding to the received message
 * is triggered. This is useful when the musajixel interface is used by an external system
 * (for instance, used by flowable)
 *
 * @author Davide A. Guastella (davide.guastella@icar.cnr.it)
 */
trait MUSAConsumerListener {
  /**
   * This method is invoked when a new event is notified by jixel to MUSA
   *
   * @param event
   */
  def onNotifyEvent(event: JixelEvent): Unit

  /**
   * This method is invoked when a new event is notified by jixel to MUSA
   *
   * @param event
   */
  def onNotifyEventSummary(event: JixelEventSummary): Unit

  /**
   * This method is invoked when an update to a jixel event is notified to MUSA
   *
   * @param update
   */
  def onEventUpdate(update: JixelEventUpdate): Unit

  /**
   * This method is invoked when a new report is received from jixel
   *
   * @param report
   */
  def onReceiveJixelReport(report: JixelEventReport): Unit

  /**
   * This method is invoked when a new recipient is added to a jixel event
   *
   * @param r
   */
  def onAddRecipient(r: Recipient): Unit
}
