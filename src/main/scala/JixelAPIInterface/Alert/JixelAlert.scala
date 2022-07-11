package JixelAPIInterface.Alert

/**
 * @author Davide A. Guastella (davide.guastella@icar.cnr.it)
 */
case class JixelAlert(id: Int,
                      code: String,
                      date: String,
                      title: String,
                      description: String,
                      start_date: String,
                      end_date: String,
                      get_response_for_each_day: Boolean,
                      update_id: Int,
                      level: Object,
                      specific_message: String,
                      is_public: Boolean,
                      controllable_object: JixelControllableObject,
                      alert_typology: JixelAlertTypology,
                      user_org_has_answered: Boolean,
                      status: String,
                      entity_type: String,
                      entity_description: String)
