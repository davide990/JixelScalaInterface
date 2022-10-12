package RabbitMQ.Producer

import RabbitMQ.{JixelEvent, JixelEventReport, JixelEventSummary, JixelEventUpdate}

trait JixelProducer {
  /**
   * Notify an event to MUSA
   *
   * @param eventJSon
   */
  def notifyEvent(event: JixelEvent): String

  /**
   * Notify an event to MUSA
   *
   * @param eventJSon
   */
  def notifyEventSummary(event: JixelEventSummary): String

  /**
   * Communicate an update to an incident situation
   *
   * @param update
   */
  def updateEvent(update: JixelEventUpdate): String

  /**
   * Notify to MUSA a report received by emergency corps
   *
   * @param report
   */
  def notifyReport(report: JixelEventReport): String


}
