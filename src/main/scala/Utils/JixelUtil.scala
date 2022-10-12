package Utils

import JixelAPIInterface.JixelInterface
import JixelAPIInterface.Login.LoginToken
import RabbitMQ.{JixelEventSummary, JixelEvent}

import scala.util.Random

/**
 * @author Davide A. Guastella (davide.guastella@icar.cnr.it)
 */
object JixelUtil {

  /**
   * Obtain a JixelEvent object from a JixelEventSummary.
   *
   * @param login
   * @param eventSummary
   * @return
   */
  def eventFromEventSummary(login: LoginToken, eventSummary: JixelEvent): JixelEventSummary = {
    val connectionResult = JixelInterface.connect(login)
    val jixelUser = JixelInterface.parseToJixelCredential(connectionResult)
    JixelInterface.requestEventDetail(jixelUser.token, eventSummary.id).incident;
  }

  /**
   * Return a random jixel event
   *
   * @param login
   * @return
   */
  def getAnyJixelEvent(login: LoginToken): JixelEvent = {
    val connectionResult = JixelInterface.connect(login)
    val jixelUser = JixelInterface.parseToJixelCredential(connectionResult)
    val eventList = JixelInterface.requestEventList(jixelUser.token).incidents;
    Random.shuffle(eventList).head
  }

  /**
   *
   * Get the jixel event with the specified ID
   *
   * @author Davide A. Guastella (davide.guastella@icar.cnr.it)
   * @param login
   * @param id
   * @return
   */
  def getJixelEvent(login: LoginToken, id: Int): Option[JixelEventSummary] = {
    val connectionResult = JixelInterface.connect(login)
    val jixelUser = JixelInterface.parseToJixelCredential(connectionResult)
    try {
      Some(JixelInterface.requestEventDetail(jixelUser.token, id).incident)
    } catch {
      case _: Exception => {
        println(s"Unable to get event with ID ${id}")
        None
      }
    }
  }

}
