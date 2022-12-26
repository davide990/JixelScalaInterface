package RabbitMQ.Producer

import RabbitMQ.JixelEvent

trait MUSAProducer {
  def addRecipient(ev: JixelEvent, actors_id: List[Int]): String

  def updateUrgencyLevel(ev: JixelEvent, incident_urgency_id: Int): String

  def updateEventSeverity(ev: JixelEvent, incident_severity_id: Int): String

  def updateEventTypology(ev: JixelEvent, incident_type_id: Int): String

  def updateEventDescription(ev: JixelEvent, description: String): String

  def updateCommType(ev: JixelEvent, incident_msgtype_id: Int): String

}
