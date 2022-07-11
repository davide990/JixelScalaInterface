package RabbitMQ

import JixelAPIInterface.Alert.{JixelFileAttachmentList, JixelPagination}

/**
 * @author Davide A. Guastella (davide.guastella@icar.cnr.it)
 */

object JixelEventUpdateTypology {
  // NOTE: Using val and not case object because these last cannot be serialized through lift unless custom serializer.
  val UrgencyLevel = 0x0
  val EventSeverity = 0x1
  val EventTypology = 0x2
  val EventDescription = 0x3
  val CommType = 0x4
}


/**
 * per jixelEvent devono esserci:
 * - communication type (enum fissa, 4 val)
 * - tipo di evento (eventType sarebbe una enum, ha dei valori di base ma puo essere arricchita con nuove tipologie)
 * - sottotipologia Optional<tipologia?>
 * esempio: se il tipo è "fire" allora la sottotipologia potrebbe essere "forest", o "building". E' un supporto, non obbiglatorio
 *
 * - stato: 4 stati fissi (reported, pending (prima di procedere, controllare), under_control (gia fatto dispatching delle risorse), managed (apposto))
 * - livello urgenza (OPZIONALE): (past, immediate, future) > interessante future, da vedere bene....
 * - liv. criticità (OPZIONALE): esprime quattro gradi di criticità (minor, moderate, severe, extreme)
 * - oggetto: free-text, ma obbligatorio, fatto dall'operatore
 * - descrizione: free-text, ma obbligatorio, fatto dall'operatore
 * - caller (optional): chi ha creato e/o iniziato l'evento
 * - instructions (opzionale): istruzioni "generiche" specifiche per l'organizzazione che riceve il messaggio. non sono strutture, testo libero
 * - location (latitudine, longitudine) in WGS84
 * - attachments: una lista di coppie (hash, nomefile). Tirarlo da jixel è facile, mandarlo a jixel...DA VEDERE
 * - destinatari: array di [actor_id, operations] dove operations è una enum di 13 valori
 * - voluntary_organisations = array di id di organizzazioni di volontariato
 */

case class JixelIncidentMsgType(id: Int,
                                enum_value: String,
                                description_name: String,
                                description_value: String,
                                description_category: String,
                                additional_parameters: List[String],
                                cap_description_value: String)

case class JixelIncidentType(id: Int,
                             description: String,
                             color: Object,
                             icon: Object,
                             app_enabled: Boolean,
                             order: Object,
                             deleted: Object,
                             default_value: Boolean,
                             category: String,
                             interoperability_incident_type_id: Object,
                             post_emergency: Int,
                             description_name: String,
                             description_value: String,
                             description_category: String,
                             additional_parameters: List[String],
                             subtypes: String,
                             cap_type_description: String)

case class JixelIncidentSubType(id: Int,
                                incident_type_id: Int,
                                description: String,
                                color: Object,
                                icon: String,
                                app_enabled: Boolean,
                                deleted: Object,
                                default_value: Int,
                                description_name: String,
                                description_value: String,
                                description_category: String,
                                additional_parameters: List[String])

case class JixelIncidentStatus(id: Int,
                               description: String,
                               description_name: String,
                               description_value: String,
                               description_category: String,
                               additional_parameters: List[String])

case class JixelUrgencyLevel(id: Int,
                             description: String,
                             cap_description_value: String)

case class JixelSeverityLevel(id: Int,
                              description: String,
                              cap_description_value: String)

case class JixelIncidentLocation(id: Int,
                                 description: String,
                                 geotype: String,
                                 coordinates: String, //example: "14.27949 37.08711",
                                 is_strategic_positions: Object,
                                 code: Object,
                                 controllable_object_id: Int,
                                 feature_collection: String,
                                 road: String,
                                 town: String,
                                 county: String,
                                 postcode: String,
                                 state: String,
                                 country: String,
                                 loggable_object_id: Int,
                                 coordinates_ne: String, //example: "14.27949 37.08711",
                                 latitude: String, //example: "37.08711",
                                 longitude: String) //example: "14.27949")

case class JixelRecipientGroupItems(id: Int, text: String)

case class JixelRecipient(group_name: String, group_items: List[JixelRecipientGroupItems])

case class JixelEventPagination(Incidents: JixelEventPaginationDetails)

case class JixelEventPaginationDetails(finder: String,
                                       page: Integer,
                                       current: Integer,
                                       count: Integer,
                                       perPage: Integer,
                                       prevPage: Boolean,
                                       nextPage: Boolean,
                                       pageCount: Integer,
                                       sort: String,
                                       direction: String,
                                       limit: Object,
                                       sortDefault: String,
                                       directionDefault: String)

case class JixelEventList(incidents: List[JixelEventSummary], pagination: JixelEventPagination)

case class JixelEventDetail(incident: JixelEvent)


case class JixelEventControllableObject(id: Int,
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
                                        locations: List[JixelIncidentLocation],
                                        additional_parameter_value: List[String],
                                        creator_with_organisation: String,
                                        additional_parameters: List[String],
                                        additional_parameters_for_list: List[String],
                                        attachment_file_names: JixelFileAttachmentList,
                                        attachment_url_accesses: JixelFileAttachmentList,
                                        creator_organisation: String)


case class JixelEvent(id: String,
                      incident_msgtype: JixelIncidentMsgType,
                      incident_type: JixelIncidentType,
                      //incident_subtype: Option[JixelIncidentSubType],
                      incident_status: JixelIncidentStatus,
                      incident_urgency: JixelUrgencyLevel,
                      incident_severity: JixelSeverityLevel,
                      headline: String,
                      description: String,
                      caller_name: String,
                      caller_phone: String,
                      instructions: String,
                      locations: List[JixelIncidentLocation],
                      controllable_object: JixelEventControllableObject,
                      recipients: List[JixelRecipient],
                      voluntary_organisations: List[Int])

case class JixelEventSummary(id: Int,
                             description: String,
                             casualties: Object,
                             caller_name: String,
                             caller_phone: String,
                             incident_interface_fire: Boolean,
                             incident_distance: Object,
                             incident_msgtype_id: Int,
                             incident_type_id: Int,
                             incident_status_id: Int,
                             incident_id: Object,
                             headline: String,
                             date: String,
                             completable: Boolean,
                             public: Boolean,
                             controllable_object: JixelEventControllableObject,
                             incident_status: JixelIncidentStatus,
                             incident_msgtype: JixelIncidentMsgType,
                             is_user_recipient: Boolean,
                             updates: Int,
                             entity_type: String,
                             entity_description: String,
                             instructions: String)

case class JixelEventUpdate(event: JixelEvent, update: JixelEventUpdateDetail)

case class JixelEventUpdateDetail(updateType: Int, content: String)

case class JixelEventReportFileAttachments(fileID: String, fileName: String)

case class JixelEventReport(event: JixelEvent, files: List[JixelEventReportFileAttachments])

// recipient should be of type JixelAlertActor
case class Recipient(event: JixelEvent, recipient: String)

case class ReadAckRequiredHead(read_ack_required: ReadAckRequired)

case class ReadAckRequired(co_id: Int, check_code: String)
