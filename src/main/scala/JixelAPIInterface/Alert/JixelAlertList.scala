package JixelAPIInterface.Alert

case class JixelAlertList(alerts: List[JixelAlert], pagination: Map[String, JixelPagination])