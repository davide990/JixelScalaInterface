package JixelAPIInterface.Alert

/**
 * @author Davide A. Guastella (davide.guastella@icar.cnr.it)
 */
case class JixelAlertList(alerts: List[JixelAlert], pagination: Map[String, JixelPagination])
