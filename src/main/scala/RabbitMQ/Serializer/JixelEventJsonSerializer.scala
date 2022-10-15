package RabbitMQ.Serializer

import RabbitMQ.{JixelEvent, JixelEventReport, JixelEventSummary, JixelEventUpdate, Recipient}
import net.liftweb.json.Extraction.decompose
import net.liftweb.json.JsonAST.{JField, JObject}
import net.liftweb.json.{DefaultFormats, MappingException, parse, prettyRender}


/**
 * @author Davide A. Guastella (davide.guastella@icar.cnr.it)
 */
object JixelEventJsonSerializer {
  implicit val formats = DefaultFormats

  def toJSon(entity: Any): String = prettyRender(decompose(entity))

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

      case _ => throw new Exception("Not parsable")
    }

  }
}