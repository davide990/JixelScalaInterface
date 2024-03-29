package JixelAPIInterface.Alert

/**
 * @author Davide A. Guastella (davide.guastella@icar.cnr.it)
 */
case class JixelControllableObject(id: Int,
                                   created: String,
                                   modified: String,
                                   organisation_id: Int,
                                   controllable_object_type_id: Int,
                                   last_activity_entry_id: Int,
                                   check_code: String,
                                   emergency_scenario_id: Object,
                                   emergency_scenario_activity: Int,
                                   create_user_id: Int,
                                   edit_user_id: Int,
                                   edit_organisation_id: Int,
                                   deleted: Object,
                                   organisation: JixelOrganisation,
                                   additional_parameter_value: List[Object],
                                   creator_with_organisation: String,
                                   additional_parameters: List[Any],
                                   additional_parameters_for_list: List[Map[String, Any]],
                                   attachment_file_names: JixelFileAttachmentList,
                                   attachment_url_accesses: JixelUrlAccess,
                                   creator_organisation: String)
