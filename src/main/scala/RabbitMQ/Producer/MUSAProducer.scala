package RabbitMQ.Producer

import RabbitMQ.JixelEventSummary

trait MUSAProducer {

  def notifyEvent(event: JixelEventSummary): String

  def addRecipient(ev: JixelEventSummary, recipient: String): String

  def updateUrgencyLevel(ev: JixelEventSummary, level: String): String

  def updateEventSeverity(ev: JixelEventSummary, severity: String): String

  def updateEventTypology(ev: JixelEventSummary, typology: String): String

  def updateEventDescription(ev: JixelEventSummary, description: String): String

  def updateCommType(ev: JixelEventSummary, commType: String): String
}
