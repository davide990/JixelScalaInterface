package JixelAPIInterface.Alert

/**
 * @author Davide A. Guastella (davide.guastella@icar.cnr.it)
 */
case class JixelOrganisation(id: Int,
                             acronym: String,
                             address: String,
                             district: String,
                             cap: String,
                             province: String,
                             phone: String,
                             mobile: String,
                             fax: String,
                             email: String,
                             organisation_type_id: String,
                             actor_id: Int,
                             incident_creator: Boolean,
                             pec: String,
                             geotype: String,
                             coordinates: String,
                             feature_collection: Object,
                             deleted: Object,
                             photo: String,
                             incident_default_tab: Integer,
                             loggable_object_id: Integer,
                             controllable_object_interface_id: Integer,
                             allow_badges: Boolean,
                             actor: JixelAlertActor)