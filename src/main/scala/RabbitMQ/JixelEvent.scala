package RabbitMQ

case class JixelEvent(id: String, eventType: String)

case class JixelEventUpdateDetail(updateType: String, content: String)

case class JixelEventUpdate(event: JixelEvent, update: JixelEventUpdateDetail)

case class JixelEventReportFileAttachments(fileID: String, fileName: String)

case class JixelEventReport(event: JixelEvent, files: List[JixelEventReportFileAttachments])