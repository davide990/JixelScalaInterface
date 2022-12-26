package RabbitMQ.Serializer

import RabbitMQ.Consumer.JixelRabbitMQConsumer
import RabbitMQ._
import net.liftweb.json.Extraction.decompose
import net.liftweb.json.JsonAST.{JField, JObject}
import net.liftweb.json.{DefaultFormats, MappingException, parse, prettyRender}
import org.slf4j.LoggerFactory


/**
 * @author Davide A. Guastella (davide.guastella@icar.cnr.it)
 */
object JixelEventJsonSerializer {
  implicit val formats = DefaultFormats

  private val logger = LoggerFactory.getLogger(classOf[JixelRabbitMQConsumer])

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
      //println("----> Message is a JixelEvent.")
      logger.info(Console.BLUE_B + Console.YELLOW + "[MUSA] Parsed Jixel Message of Type: JixelEvent" + Console.RESET)
      return event
    } catch {
      case _: MappingException =>
    }

    ll match {
      /** ************************************* JIXEL EVENT SUMMARY ************************************************* */
      case JObject(List(JField("id", _), JField("incident_msgtype", _), JField("incident_type", _),
      JField("incident_status", _), JField("incident_urgency", _),
      JField("incident_severity", _), JField("headline", _), JField("description", _),
      JField("caller_name", _), JField("caller_phone", _), JField("instructions", _),
      JField("locations", _), JField("controllable_object", _), JField("recipients", _),
      JField("voluntary_organisations", _))) =>
        //println("---->Message is a JixelEventSummary.")
        logger.info(Console.BLUE_B + Console.YELLOW + "[MUSA] Parsed Jixel Message of Type: JixelEventSummary" + Console.RESET)
        ll.extract[JixelEventSummary]

      /** ************************************* JIXEL EVENT UPDATE ************************************************** */
      case JObject(List(JField("event", _), JField("update", _))) =>
        //println("---->Message is a JixelEventUpdate.")
        logger.info(Console.BLUE_B + Console.YELLOW + "[MUSA] Parsed Jixel Message of Type: JixelEventUpdate" + Console.RESET)
        ll.extract[JixelEventUpdate]

      /** ************************************* JIXEL EVENT REPORT ************************************************** */
      case JObject(List(JField("event", _), JField("files", _))) =>
        //println("---->Message is a JixelEventReport.")
        logger.info(Console.BLUE_B + Console.YELLOW + "[MUSA] Parsed Jixel Message of Type: JixelEventReport" + Console.RESET)
        ll.extract[JixelEventReport]

      /** ************************************* JIXEL RECIPIENT ***************************************************** */
      case JObject(List(JField("event", _), JField("recipient", _))) =>
        //println("---->Message is a Recipient.")
        logger.info(Console.BLUE_B + Console.YELLOW + "[MUSA] Parsed Jixel Message of Type: Recipient" + Console.RESET)
        ll.extract[Recipient]

      /** ************************************* JIXEL ACK ADD RECIPIENT ********************************************* */
      case JObject(List(JField("result_code", _), JField("original_message", JObject(List(JField("command", _), JField("data", JObject(List(JField("incident_id", _), JField("actor_ids", _))))))))) =>
        //println("---->Message is an ACK update add recipient from Jixel.")
        logger.info(Console.BLUE_B + Console.YELLOW + "[MUSA] Parsed Jixel Message of Type: JixelAckAddRecipient" + Console.RESET)
        ll.extract[JixelAckAddRecipient]

      /** ************************************* JIXEL ACK urgency level ********************************************* */
      case JObject(List(JField("result_code", _), JField("original_message", JObject(List(JField("command", _), JField("data", JObject(List(JField("incident_id", _), JField("incident_urgency_id", _))))))))) =>
        //println("---->Message is an ACK update urgency level from Jixel.")
        logger.info(Console.BLUE_B + Console.YELLOW + "[MUSA] Parsed Jixel Message of Type: JixelAckUrgencyLevel" + Console.RESET)
        ll.extract[JixelAckUrgencyLevel]

      /** ************************************* JIXEL ACK severity level ******************************************** */
      case JObject(List(JField("result_code", _), JField("original_message", JObject(List(JField("command", _), JField("data", JObject(List(JField("incident_id", _), JField("incident_severity_id", _))))))))) =>
        //println("---->Message is an ACK update event severity from Jixel.")
        logger.info(Console.BLUE_B + Console.YELLOW + "[MUSA] Parsed Jixel Message of Type: JixelAckEventSeverity" + Console.RESET)
        ll.extract[JixelAckEventSeverity]

      /** ************************************* JIXEL ACK event typology ******************************************** */
      case JObject(List(JField("result_code", _), JField("original_message", JObject(List(JField("command", _), JField("data", JObject(List(JField("incident_id", _), JField("incident_type_id", _))))))))) =>
        //println("---->Message is an ACK update event typology from Jixel.")
        logger.info(Console.BLUE_B + Console.YELLOW + "[MUSA] Parsed Jixel Message of Type: JixelAckEventTypology" + Console.RESET)
        ll.extract[JixelAckEventTypology]

      /** ************************************* JIXEL ACK evt description ******************************************* */
      case JObject(List(JField("result_code", _), JField("original_message", JObject(List(JField("command", _), JField("data", JObject(List(JField("incident_id", _), JField("description", _))))))))) =>
        //println("---->Message is an ACK update event description from Jixel.")
        logger.info(Console.BLUE_B + Console.YELLOW + "[MUSA] Parsed Jixel Message of Type: JixelAckEventDescription" + Console.RESET)
        ll.extract[JixelAckEventDescription]

      /** ************************************* JIXEL ACK Update comm type ****************************************** */
      case JObject(List(JField("result_code", _), JField("original_message", JObject(List(JField("command", _), JField("data", JObject(List(JField("incident_id", _), JField("incident_msgtype_id", _))))))))) =>
        //println("---->Message is an ACK update comm type from Jixel.")
        logger.info(Console.BLUE_B + Console.YELLOW + "[MUSA] Parsed Jixel Message of Type: JixelAckUpdateCommType" + Console.RESET)
        ll.extract[JixelAckUpdateCommType]

      /** ************************************* JIXEL ACK UPDATE COMM TYPE ****************************************** */
      case JObject(List(JField("result_code", _), JField("original_message", JObject(List(JField("command", _), JField("data", JObject(List(JField("incident_id", _), JField("incident_msgtype_id", _)))),
      JField("result_code", _), JField("error_message", JObject(List(JField("incident_msgtype_id", JObject(List(JField("_existsIn", _)))))))))))) =>
        //println("---->Message is an ACK update comm type (error) from Jixel.")
        logger.info(Console.BLUE_B + Console.YELLOW + "[MUSA] Parsed Jixel Message of Type: JixelAckUpdateCommTypeError" + Console.RESET)
        ll.extract[JixelAckUpdateCommTypeError]

      case _ => throw new Exception("Not parsable")
    }

  }
}