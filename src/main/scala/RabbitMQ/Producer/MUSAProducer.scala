package RabbitMQ.Producer

import RabbitMQ.JixelEvent

trait MUSAProducer {

  def notifyEvent(event: JixelEvent): String

  def addRecipient(ev: JixelEvent, recipient: String): String

  def updateUrgencyLevel(ev: JixelEvent, level: String): String

  def updateEventSeverity(ev: JixelEvent, severity: String): String

  def updateEventTypology(ev: JixelEvent, typology: String): String

  def updateEventDescription(ev: JixelEvent, description: String): String

  def updateCommType(ev: JixelEvent, commType: String): String
}
