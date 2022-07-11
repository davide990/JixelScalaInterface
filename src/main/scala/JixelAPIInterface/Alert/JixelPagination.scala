package JixelAPIInterface.Alert

/**
 * @author Davide A. Guastella (davide.guastella@icar.cnr.it)
 */
case class JixelPagination(finder: String,
                           page: Integer,
                           current: Integer,
                           count: Integer,
                           perPage: Integer,
                           prevPage: Boolean,
                           nextPage: Boolean,
                           pageCount: Integer,
                           sort: Object,
                           direction: Boolean,
                           limit: Object,
                           sortDefault: Boolean,
                           directionDefault: Boolean)
