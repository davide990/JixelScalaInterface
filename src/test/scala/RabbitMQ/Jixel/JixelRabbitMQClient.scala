package RabbitMQ.Jixel

import RabbitMQ._

object JixelClientTest {

  val event = JixelEvent("12343", "incendio")
  val eventUpdate = JixelEventUpdate(event, JixelEventUpdateDetail("detail","blabla"))
  val eventReport = JixelEventReport(event,List(JixelEventReportFileAttachments("file1","file.doc")))

  var jixel: JixelRPCClient = null

  def main(argv: Array[String]) {
    var response: String = null
    try {
      val host = if (argv.isEmpty) "localhost" else argv(0)
      jixel = new JixelRPCClient(host)

      //Notify event
      println(" [x] notifying event")
      response = jixel.notifyEvent(event)
      println(" [.] Got '" + response + "' from MUSA")
      Thread.sleep(1000)

      println(" [x] notifying event 2")
      response = jixel.notifyEvent(event)
      println(" [.] Got '" + response + "' from MUSA")
      Thread.sleep(1000)

      println(" [x] notifying event 3")
      response = jixel.notifyEvent(event)
      println(" [.] Got '" + response + "' from MUSA")
      Thread.sleep(1000)

      //Notify event update
      println(" [x] notifying update")
      response = jixel.updateEvent(eventUpdate)
      println(" [.] Got '" + response + "' from MUSA")
      Thread.sleep(1000)

      //Notify event report
      println(" [x] notifying report(s)")
      response = jixel.notifyReport(eventReport)
      println(" [.] Got '" + response + "' from MUSA")

    } catch {
      case e: Exception => e.printStackTrace()
    } finally {
      if (jixel == null)
        return;

      try {
        jixel.close()
      } catch {
        case _: Exception =>
      }
    }
  }




}