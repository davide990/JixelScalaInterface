package RabbitMQ.Listener

import RabbitMQ.{JixelEvent, JixelEventUpdate, Recipient}

/**
 * The methods of this interface are invoked when the respective jixel events are generated.
 * In other words, when jixel consumes a message, the method corresponding to the received message
 * is triggered. This is useful when the musajixel interface is used by an external system
 * (for instance, used by flowable)
 *
 * @author Davide A. Guastella (davide.guastella@icar.cnr.it)
 */
trait JixelConsumerListener {
  /**
   * This method is invoked when a new incidental event is created
   * and added into the jixel platform.
   *
   * @param event
   */
  def onCreateEvent(event: JixelEvent): Unit

  /**
   * This method is invoked when a new recipient is added to an event.
   * Please note that the Recipient class already contains a reference to a Jixel event
   *
   * @param r
   */
  def onAddRecipient(r: Recipient): Unit

  /**
   * This method is invoked when a jixel event is updated
   *
   * @param update
   */
  def onEventUpdate(update: JixelEventUpdate): Unit
}
