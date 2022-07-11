package RabbitMQ.Launchers.MUSA

import RabbitMQ.Producer.MUSARabbitMQProducer
import RabbitMQ._

object MusaRabbitMQClient {

  //val event = JixelEvent("12343", "incendio")
  //val event2 = JixelEvent("56789", "incendio2")

  //val eventUpdate = JixelEventUpdate(event, JixelEventUpdateDetail(JixelEventUpdateTypology.EventDescription, "blabla"))
  //val eventReport = JixelEventReport(event, List(JixelEventReportFileAttachments("file1", "file.doc")))

  var MUSA: MUSARabbitMQProducer = null

  def main(argv: Array[String]) {
    var response: String = null
    try {
      MUSA = new MUSARabbitMQProducer
      //Notify event
      println(" [x] notifying event")
      response = MUSA.notifyEvent(null)
      println(" [.] Got '" + response + "' from MUSA")
      Thread.sleep(1000)

      //Notify event update


    } catch {
      case e: Exception => e.printStackTrace()
    } finally {
      if (MUSA == null)
        return;

      try {
        MUSA.close()
      } catch {
        case _: Exception =>
      }
    }
  }


}
