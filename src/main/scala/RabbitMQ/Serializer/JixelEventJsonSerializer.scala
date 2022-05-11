package RabbitMQ.Serializer

import RabbitMQ.{JixelEvent, JixelEventReport, JixelEventUpdate, Recipient}
import net.liftweb.json.Extraction.decompose
import net.liftweb.json.JsonAST.{JField, JObject}
import net.liftweb.json.{DefaultFormats, parse, prettyRender}

object JixelEventJsonSerializer {
  implicit val formats = DefaultFormats

  /*def toJSon(jixelEvent: JixelEvent): String = prettyRender(decompose(jixelEvent))

  def toJSon(jixelEvent: JixelEventUpdate): String = prettyRender(decompose(jixelEvent))

  def toJSon(jixelEvent: JixelEventReport): String = prettyRender(decompose(jixelEvent))*/

  def toJSon(entity: Any): String = prettyRender(decompose(entity))

  /*case class JixelEventUpdate(event: JixelEvent, update: JixelEventUpdateDetail)

case class JixelEventUpdateDetail(updateType: JixelEventUpdateTypology, content: String)*/
  def fromJson(j: String): Any = {
    val ll = parse(j)
    ll match {
      case JObject(List(JField("id", _), JField("eventType", _))) => ll.extract[JixelEvent]
      case JObject(List(JField("event", _), JField("update", _))) => ll.extract[JixelEventUpdate]
      case JObject(List(JField("event", _), JField("files", _))) => ll.extract[JixelEventReport]
      case JObject(List(JField("event", _), JField("recipient",_))) => ll.extract[Recipient]
      case _ => throw new Exception("Not parsable")
    }
  }
}