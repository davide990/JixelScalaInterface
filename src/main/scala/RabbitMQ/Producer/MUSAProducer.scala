package RabbitMQ.Producer

import RabbitMQ.{JixelEvent}

trait MUSAProducer {
  /*MUSA to JIXEL
  //addRecipient
  {"command":"addRecipient","data":{"incident_id":3750,"actor_ids":[6681,6879]}}
  //updateUrgencyLevel
  {"command":"updateUrgencyLevel","data":{"incident_id":3750,"incident_urgency_id":6}}
  //updateEventSeverity
  {"command":"updateEventSeverity","data":{"incident_id":3750,"incident_severity_id":6}}
  //updateEventTypology
  {"command":"updateEventTypology","data":{"incident_id":3750,"incident_type_id":6}}
  //updateEventDescription
  {"command":"updateEventDescription","data":{"incident_id":3750,"description":"Ciccio ci siamo ?"}}
  //updateCommType
  {"command":"updateCommType","data":{"incident_id":3750,"incident_msgtype_id":1}}*/

  def addRecipient(ev: JixelEvent, actors_id: List[Int]): String

  def updateUrgencyLevel(ev: JixelEvent, incident_urgency_id: Int): String

  def updateEventSeverity(ev: JixelEvent, incident_severity_id: Int): String

  def updateEventTypology(ev: JixelEvent, incident_type_id: Int): String

  def updateEventDescription(ev: JixelEvent, description: String): String

  def updateCommType(ev: JixelEvent, incident_msgtype_id: Int): String

  /*
  def notifyEvent(event: JixelEvent): String

  def addRecipient(ev: JixelEvent, recipient: String): String

  def updateUrgencyLevel(ev: JixelEvent, level: String): String

  def updateEventSeverity(ev: JixelEvent, severity: String): String

  def updateEventTypology(ev: JixelEvent, typology: String): String

  def updateEventDescription(ev: JixelEvent, description: String): String

  def updateCommType(ev: JixelEvent, commType: String): String
  */

}
