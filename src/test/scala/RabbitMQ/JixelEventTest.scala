package RabbitMQ

import RabbitMQ.Serializer.JixelEventJsonSerializer

object JixelEventTest {
  val fireInRefinery = JixelEvent("INCENDIO IN RAFFINERIA", "INCENDIO")
  val fireInRefineryUpdate = JixelEventUpdate(fireInRefinery, JixelEventUpdateDetail("Description", "INCENDIO messo male"))
  val fireInRefineryReport1 = JixelEventReport(fireInRefinery, List(JixelEventReportFileAttachments("id_1", "fname_1.doc")))
  val fireInRefineryReport2 = JixelEventReport(fireInRefinery, List(JixelEventReportFileAttachments("id_1", "fname_1.doc"),
    JixelEventReportFileAttachments("id_2", "fname_2.doc")))

  def main(args: Array[String]): Unit = {
    val parsed = JixelEventJsonSerializer.toJSon(fireInRefinery)
    val parsedUpdate = JixelEventJsonSerializer.toJSon(fireInRefineryUpdate)
    val parsedReport1 = JixelEventJsonSerializer.toJSon(fireInRefineryReport1)
    val parsedReport2 = JixelEventJsonSerializer.toJSon(fireInRefineryReport2)

    val event = JixelEventJsonSerializer.fromJson(parsed)
    val eventUpdate = JixelEventJsonSerializer.fromJson(parsedUpdate)
    val eventReport1 = JixelEventJsonSerializer.fromJson(parsedReport1)
    val eventReport2 = JixelEventJsonSerializer.fromJson(parsedReport2)

    assert(fireInRefinery == event)
    assert(eventUpdate == fireInRefineryUpdate)
    assert(eventReport1 == fireInRefineryReport1)
    assert(eventReport2 == fireInRefineryReport2)
  }
}
