package RabbitMQ

//abstract class JixelEventUpdateTypology(id: Int)

object JixelEventUpdateTypology {
  val UrgencyLevel = 0x0
  val EventSeverity = 0x1
  val EventTypology = 0x2
  val EventDescription = 0x3
  val CommType = 0x4

  /*case object UrgencyLevel extends JixelEventUpdateTypology(0x0)

  case object EventSeverity extends JixelEventUpdateTypology(0x1)

  case object EventTypology extends JixelEventUpdateTypology(0x2)

  case object EventDescription extends JixelEventUpdateTypology(0x3)

  case object CommType extends JixelEventUpdateTypology(0x4)*/
}

// In jixel, Event != alert.
case class JixelEvent(id: String, eventType: String)

case class JixelEventUpdate(event: JixelEvent, update: JixelEventUpdateDetail)

case class JixelEventUpdateDetail(updateType: Int, content: String)

case class JixelEventReportFileAttachments(fileID: String, fileName: String)

case class JixelEventReport(event: JixelEvent, files: List[JixelEventReportFileAttachments])

// recipient should be of type JixelAlertActor
case class Recipient(event: JixelEvent, recipient: String)
