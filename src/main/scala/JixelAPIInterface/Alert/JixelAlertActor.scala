package JixelAPIInterface.Alert

/**
 * @author Davide A. Guastella (davide.guastella@icar.cnr.it)
 */
case class JixelAlertActor(id: Int,
                           description: String,
                           actor_type_id: Int,
                           interoperability_identifier: Object,
                           incident_type_group_id: Object,
                           deleted: Object,
                           description_with_actor_type: String,
                           actor_type_description: String)
