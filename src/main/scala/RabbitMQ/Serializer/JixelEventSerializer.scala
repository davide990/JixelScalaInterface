package RabbitMQ.Serializer

import RabbitMQ.{JixelEvent, JixelEventReport, JixelEventUpdate}
import net.liftweb.json.Extraction.decompose
import net.liftweb.json.JsonAST.{JField, JObject}
import net.liftweb.json.{DefaultFormats, parse, prettyRender}

object JixelEventSerializer {
  implicit val formats = DefaultFormats

  def toJSon(jixelEvent: JixelEvent): String = prettyRender(decompose(jixelEvent))

  def toJSon(jixelEvent: JixelEventUpdate): String = prettyRender(decompose(jixelEvent))

  def toJSon(jixelEvent: JixelEventReport): String = prettyRender(decompose(jixelEvent))

  def fromJson(j: String): Any = {
    val ll = parse(j)
    ll match {
      case JObject(List(JField("id", _), JField("eventType", _))) => ll.extract[JixelEvent]
      case JObject(List(JField("event", _), JField("update", _))) => ll.extract[JixelEventUpdate]
      case JObject(List(JField("event", _), JField("files", _))) => ll.extract[JixelEventReport]
      case _ => throw new Exception("Not parsable")
    }
  }
}