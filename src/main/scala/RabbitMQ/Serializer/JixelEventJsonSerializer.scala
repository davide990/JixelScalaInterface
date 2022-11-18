package RabbitMQ.Serializer

import RabbitMQ._
import net.liftweb.json.Extraction.decompose
import net.liftweb.json.JsonAST.{JField, JObject}
import net.liftweb.json.{DefaultFormats, MappingException, parse, prettyRender}


/**
 * @author Davide A. Guastella (davide.guastella@icar.cnr.it)
 */
object JixelEventJsonSerializer {
  implicit val formats = DefaultFormats

  def toJSon(entity: Any): String = prettyRender(decompose(entity))

  def parseFromJson(j: String): Any = {
    parse(j)
  }

  def getEvent(j: String): Any = {
    parse(j).extract[JixelEvent]
  }

  def fromJson(j: String): Any = {
    val ll = parse(j)

    try {
      val event = ll.extract[JixelEvent]
      println("---->Message is a JixelEvent.")
      return event
    } catch {
      case e: MappingException => //println("---->Message is not a JixelEvent.")
    }

    ll match {
      /** ************************************* JIXEL EVENT ********************************************************* */
      case JObject(List(JField("id", _), JField("incident_msgtype", _), JField("incident_type", _),
      JField("incident_status", _), JField("incident_urgency", _),
      JField("incident_severity", _), JField("headline", _), JField("description", _),
      JField("caller_name", _), JField("caller_phone", _), JField("instructions", _),
      JField("locations", _), JField("controllable_object", _), JField("recipients", _),
      JField("voluntary_organisations", _))) =>
        println("---->Message is a JixelEventSummary.")
        ll.extract[JixelEventSummary]

      /** ************************************* JIXEL EVENT UPDATE ************************************************** */
      case JObject(List(JField("event", _), JField("update", _))) =>
        println("---->Message is a JixelEventUpdate.")
        ll.extract[JixelEventUpdate]

      /** ************************************* JIXEL EVENT REPORT ************************************************** */
      case JObject(List(JField("event", _), JField("files", _))) =>
        println("---->Message is a JixelEventReport.")
        ll.extract[JixelEventReport]

      /** ************************************* JIXEL RECIPIENT ***************************************************** */
      case JObject(List(JField("event", _), JField("recipient", _))) =>
        println("---->Message is a Recipient.")
        ll.extract[Recipient]

    //-------
      case JObject(List(JField("result_code",_), JField("original_message",JObject(List(JField("command", _), JField("data", JObject(List(JField("incident_id", _), JField("actor_ids", _))))))))) =>
        println("---->Message is an ACK update add recipient from Jixel.")
        ll.extract[JixelAckAddRecipient]

      /** ************************************* JIXEL ACK urgency level ***************************************************** */
      case JObject(List(JField("result_code",_), JField("original_message",JObject(List(JField("command", _), JField("data", JObject(List(JField("incident_id", _), JField("incident_urgency_id", _))))))))) =>
        println("---->Message is an ACK update urgency level from Jixel.")
        ll.extract[JixelAckUrgencyLevel]

      /** ************************************* JIXEL ACK severity level ***************************************************** */
      case JObject(List(JField("result_code",_), JField("original_message",JObject(List(JField("command", _), JField("data", JObject(List(JField("incident_id", _), JField("incident_severity_id", _))))))))) =>
        println("---->Message is an ACK update event severity from Jixel.")
        ll.extract[JixelAckEventSeverity]

      /** ************************************* JIXEL ACK event typology ***************************************************** */
      case JObject(List(JField("result_code",_), JField("original_message",JObject(List(JField("command", _), JField("data", JObject(List(JField("incident_id", _), JField("incident_type_id", _))))))))) =>
        println("---->Message is an ACK update event typology from Jixel.")
        ll.extract[JixelAckEventTypology]

      /** ************************************* JIXEL ACK evt description ***************************************************** */
      case JObject(List(JField("result_code",_), JField("original_message",JObject(List(JField("command", _), JField("data", JObject(List(JField("incident_id", _), JField("description", _))))))))) =>
        println("---->Message is an ACK update event description from Jixel.")
        ll.extract[JixelAckEventDescription]

      /** ************************************* JIXEL ACK Update comm type ***************************************************** */
      case JObject(List(JField("result_code",_), JField("original_message",JObject(List(JField("command", _), JField("data", JObject(List(JField("incident_id", _), JField("incident_msgtype_id", _))))))))) =>
        println("---->Message is an ACK update comm type from Jixel.")
        ll.extract[JixelAckUpdateCommType]

      /** ************************************* JIXEL ACK UPDATE COMM TYPE ****************************************** */
      case JObject(List(JField("result_code",_), JField("original_message",JObject(List(JField("command", _), JField("data", JObject(List(JField("incident_id", _), JField("incident_msgtype_id", _)))),
      JField("result_code", _), JField("error_message", JObject(List(JField("incident_msgtype_id", JObject(List(JField("_existsIn", _)))))))))))) =>
        println("---->Message is an ACK update comm type (error) from Jixel.")
        ll.extract[JixelAckUpdateCommTypeError]

      case _ => throw new Exception("Not parsable")
    }

  }
}