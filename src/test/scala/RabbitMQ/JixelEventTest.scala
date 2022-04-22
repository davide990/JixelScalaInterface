package RabbitMQ

import RabbitMQ.Serializer.JixelEventSerializer

object JixelEventTest {
  val fireInRefinery = JixelEvent("INCENDIO IN RAFFINERIA", "INCENDIO")
  val fireInRefineryUpdate = JixelEventUpdate(fireInRefinery, JixelEventUpdateDetail("Description", "INCENDIO messo male"))
  val fireInRefineryReport1 = JixelEventReport(fireInRefinery, List(JixelEventReportFileAttachments("id_1", "fname_1.doc")))
  val fireInRefineryReport2 = JixelEventReport(fireInRefinery, List(JixelEventReportFileAttachments("id_1", "fname_1.doc"),
    JixelEventReportFileAttachments("id_2", "fname_2.doc")))

  def main(args: Array[String]): Unit = {
    val parsed = JixelEventSerializer.toJSon(fireInRefinery)
    val parsedUpdate = JixelEventSerializer.toJSon(fireInRefineryUpdate)
    val parsedReport1 = JixelEventSerializer.toJSon(fireInRefineryReport1)
    val parsedReport2 = JixelEventSerializer.toJSon(fireInRefineryReport2)

    val event = JixelEventSerializer.fromJson(parsed)
    val eventUpdate = JixelEventSerializer.fromJson(parsedUpdate)
    val eventReport1 = JixelEventSerializer.fromJson(parsedReport1)
    val eventReport2 = JixelEventSerializer.fromJson(parsedReport2)

    assert(fireInRefinery == event)
    assert(eventUpdate == fireInRefineryUpdate)
    assert(eventReport1 == fireInRefineryReport1)
    assert(eventReport2 == fireInRefineryReport2)
  }
}
