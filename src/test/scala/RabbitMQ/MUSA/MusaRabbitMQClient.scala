package RabbitMQ.MUSA

import RabbitMQ.Jixel.MUSARabbitMQProducer
import RabbitMQ._

object MusaRabbitMQClient {

  val event = JixelEvent("12343", "incendio")
  val event2 = JixelEvent("56789", "incendio2")

  val eventUpdate = JixelEventUpdate(event, JixelEventUpdateDetail(JixelEventUpdateTypology.EventDescription, "blabla"))
  val eventReport = JixelEventReport(event, List(JixelEventReportFileAttachments("file1", "file.doc")))

  var MUSA: MUSARabbitMQProducer = null

  def main(argv: Array[String]) {
    var response: String = null
    try {
      MUSA = new MUSARabbitMQProducer
      //Notify event
      println(" [x] notifying event")
      response = MUSA.notifyEvent(event)
      println(" [.] Got '" + response + "' from MUSA")
      Thread.sleep(1000)

      //Notify event update
      println(" [x] notifying update")
      response = MUSA.updateEventDescription(event, "questo incendio")
      println(" [.] Got '" + response + "' from MUSA")
      Thread.sleep(1000)

      //add recipient
      println(" [x] adding recipient (s)")
      response = MUSA.addRecipient(event2, "pompieri")
      println(" [.] Got '" + response + "' from MUSA")

      //add recipient
      println(" [x] adding recipient (s)")
      response = MUSA.addRecipient(event, "polizia")
      println(" [.] Got '" + response + "' from MUSA")

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
