package RabbitMQ.Launchers.Jixel

import JixelAPIInterface.JixelInterface
import JixelAPIInterface.Login.ECOSUsers
import RabbitMQ.Producer.JixelRabbitMQProducer
import RabbitMQ._
import Utils.JixelUtil


//for testing only
object JixelClientTest {

  //val event = JixelEvent("12343", "incendio")
  //val event2 = JixelEvent("56789", "incendio2")
  //val eventUpdate = JixelEventUpdate(event, JixelEventUpdateDetail(JixelEventUpdateTypology.EventDescription, "blabla"))
  //val eventReport = JixelEventReport(event, List(JixelEventReportFileAttachments("file1", "file.doc")))

  var jixel: JixelRabbitMQProducer = null

  def main(argv: Array[String]): Unit = {
    var response: String = null
    try {
      jixel = new JixelRabbitMQProducer
      val login = ECOSUsers.davide_login
      val jixelUser = JixelInterface.parseToJixelCredential(JixelInterface.connect(login))
      //val eventSummary = JixelUtil.eventFromEventSummary(login, Utils.JixelUtil.getAnyJixelEvent(login));
      val event = Utils.JixelUtil.getAnyJixelEvent(login)
      val eventSummary = JixelUtil.eventFromEventSummary(login, event);
      response = jixel.notifyEvent(event)

      //Notify event
      //println(" [x] notifying event")
      //response = jixel.notifyEvent(event)
      println(" [.] Got '" + response + "' from MUSA")
      //Thread.sleep(1000)
      /*
            println(" [x] notifying event 2")
            response = jixel.notifyEvent(event2)
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
      */
    } catch {
      case e: Exception => e.printStackTrace()
    } finally {
      if (jixel == null)
        return;

      try {
        jixel.close()
      } catch {
        case x: Exception => println(x)
      }
    }
  }


}