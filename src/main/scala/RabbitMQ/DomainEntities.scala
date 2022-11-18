package RabbitMQ

import JixelAPIInterface.Alert.{JixelAlertActor, JixelFileAttachmentList, JixelOrganisation, JixelPagination}
import net.liftweb.json.JInt

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
                                _locale: Option[String],
                                description_name: String,
                                description_value: String,
                                description_category: String,
                                additional_parameters: List[Object],
                                cap_description_value: Option[String])

case class JixelIncidentType(id: Int,
                             description: String,
                             color: Option[String],
                             icon: Option[String],
                             app_enabled: Boolean,
                             order: Option[Int],
                             deleted: Option[String],
                             default_value: Boolean,
                             category: String,
                             interoperability_incident_type_id: Option[Int],
                             post_emergency: Int,
                             _locale: Option[String],
                             description_name: String,
                             description_value: String,
                             description_category: String,
                             additional_parameters: List[Object],
                             subtypes: String,
                             cap_type_description: Option[String])

case class JixelEventIncidentSubType(id: Int,
                                     description: String)

case class JixelIncidentSubType(id: Int,
                                incident_type_id: Int,
                                description: String,
                                color: Option[String], //Object
                                icon: Option[String],
                                app_enabled: Boolean,
                                deleted: Object,
                                default_value: Int,
                                description_name: String,
                                description_value: String,
                                description_category: String,
                                additional_parameters: List[String])

case class JixelIncidentStatus(id: Int,
                               description: String,
                               _locale: Option[String],
                               description_name: String,
                               description_value: String,
                               description_category: String,
                               additional_parameters: List[Object])

case class JixelUrgencyLevel(id: Int,
                             description: String,
                             _locale: Option[String],
                             cap_description_value: String)

case class JixelSeverityLevel(id: Int,
                              description: String,
                              _locale: Option[String],
                              cap_description_value: String)

case class JixelIncidentLocation(id: Int,
                                 description: String,
                                 geotype: String,
                                 coordinates: String, //example: "14.27949 37.08711",
                                 is_strategic_positions: Object,
                                 code: Object,
                                 controllable_object_id: Int,
                                 feature_collection: Option[String],
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

case class JixelEventList(incidents: List[JixelEvent], pagination: JixelEventPagination)

case class JixelEventDetail(incident: JixelEventSummary)

case class JixelEventControllableObject(id: Int,
                                        created: String,
                                        modified: String,
                                        organisation_id: Int,
                                        controllable_object_type_id: Int,
                                        last_activity_entry_id: Int,
                                        check_code: String,
                                        emergency_scenario_id: Option[Int],
                                        emergency_scenario_activity: Int,
                                        create_user_id: Int,
                                        edit_user_id: Int,
                                        edit_organisation_id: Int,
                                        deleted: Option[String],
                                        locations: List[JixelIncidentLocation],
                                        additional_parameter_value: List[Object],
                                        creator_with_organisation: String,
                                        additional_parameters: List[String],
                                        additional_parameters_for_list: List[String],
                                        attachment_file_names: JixelFileAttachmentList,
                                        attachment_url_accesses: JixelFileAttachmentList,
                                        creator_organisation: String)

case class JixelEventSummary(id: String,
                             incident_msgtype: JixelIncidentMsgType,
                             incident_type: JixelIncidentType,
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

/*"id": INT,
    "description": STRING,
    "casualties": null/STRING,
    "caller_name": null/STRING,
    "caller_phone": null/STRING,
    "incident_interface_fire": BOOL,
    "incident_distance": null/STRING,
    "incident_id": null/INT,
    "headline": STRING,
    "date": STRING,
    "completable": BOOL,
    "public": BOOL,,*/
case class JixelEvent(id: Int,
                      description: String,
                      casualties: Option[String],
                      caller_name: Option[String],
                      caller_phone: Option[String],
                      incident_interface_fire: Boolean,
                      incident_distance: Option[String],
                      incident_id: Option[Int],
                      headline: String,
                      date: String,
                      completable: Boolean,
                      public: Boolean,
                      incident_subtype: JixelEventIncidentSubType,
                      incident_severity: Option[JixelSeverityLevel], //NEW: OPTION
                      incident_urgency: Option[JixelUrgencyLevel], //NEW: OPTION
                      incident_status: JixelIncidentStatus,
                      incident_type: JixelIncidentType,
                      incident_msgtype: JixelIncidentMsgType,
                      controllable_object: JixelEventControllableObject,
                      weblink: String,
                      recipients: List[JixelEventRecipient],
                      updates: Int,
                      entity_type: String,
                      entity_description: String,
                      instructions: Option[String])

case class JixelSummaryMatchingData(ControllableObjects: JixelEventControllableObject,
                                    ControllableObjectActors: JixelControllableObjectActors,
                                    Organisations: JixelIncidentOrganisation,
                                    Actors: JixelIncidentActor)

case class JixelIncidentOrganisation(id: Integer,
                                     acronym: String,
                                     address: String,
                                     district: String,
                                     cap: String,
                                     province: String,
                                     phone: String,
                                     mobile: String,
                                     fax: String,
                                     email: String,
                                     organisation_type_id: Integer,
                                     actor_id: Integer,
                                     incident_creator: Boolean,
                                     pec: String,
                                     geotype: String,
                                     coordinates: String,
                                     feature_collection: Object,
                                     deleted: Object,
                                     photo: String,
                                     incident_default_tab: Integer,
                                     loggable_object_id: Integer,
                                     controllable_object_interface_id: Integer, //!
                                     allow_badges: Boolean)

case class JixelIncidentActor(id: Integer,
                              description: String,
                              actor_type_id: Integer,
                              interoperability_identifier: Object,
                              incident_type_group_id: Object,
                              deleted: Object,
                              description_with_actor_type: String,
                              actor_type_description: String)

case class JixelControllableObjectActors(id: Integer,
                                         controllable_object_id: Integer,
                                         actor_id: Integer,
                                         controllable_object_actor_type_id: Integer,
                                         validation_level_id: Object,
                                         deleted: Object)

case class JixelEventUpdate(event: JixelEvent, update: JixelEventUpdateDetail)

case class JixelEventUpdateDetail(updateType: Int, content: String)

case class JixelEventReportFileAttachments(fileID: String, fileName: String)

case class JixelEventReport(event: JixelEvent, files: List[JixelEventReportFileAttachments])

case class Recipient(event: JixelEvent, recipient: String)

case class JixelEventRecipient(group_name: String,
                               group_items: List[JixelEventRecipientGroupItem])

case class JixelEventRecipientGroupItem(id: Int, text: String)

case class ReadAckRequiredHead(read_ack_required: ReadAckRequired)

case class ReadAckRequired(co_id: Int, check_code: String)

case class JixelMsgAddRecipient(command: String, data: JixelMsgDataAddRecipient)

case class JixelMsgDataAddRecipient(incident_id: Int, actor_ids: List[Int])

case class JixelMsgUrgencyLevel(command: String, data: JixelMsgDataUrgencyLevel)

case class JixelMsgDataUrgencyLevel(incident_id: Int, incident_urgency_id: Int)

case class JixelMsgEventSeverity(command: String, data: JixelMsgDataEventSeverity)

case class JixelMsgDataEventSeverity(incident_id: Int, incident_severity_id: Int)

case class JixelMsgEventTypology(command: String, data: JixelMsgDataEventTypology)

case class JixelMsgDataEventTypology(incident_id: Int, incident_type_id: Int)

case class JixelMsgEventDescription(command: String, data: JixelMsgDataEventDescription)

case class JixelMsgDataEventDescription(incident_id: Int, description: String)

case class JixelMsgUpdateCommType(command: String, data: JixelMsgDataCommType)

case class JixelMsgDataCommType(incident_id: Int, incident_msgtype_id: Int)

case class JixelMsgUpdateCommTypeError(command: String, data: JixelMsgUpdateCommTypeData, result_code: Int, error_message: JixelMsgUpdateCommTypeErrMessage)

case class JixelMsgUpdateCommTypeData(incident_id: Int, incident_msgtype_id: Int)

case class JixelMsgUpdateCommTypeErrMessage(incident_msgtype_id: JixelMsgUpdateCommTypeErrMessageID)

case class JixelMsgUpdateCommTypeErrMessageID(_existsIn: String)

//-------

case class JixelAckAddRecipient(result_code: Int, original_message: JixelMsgAddRecipient)

case class JixelAckUrgencyLevel(result_code: Int, original_message: JixelMsgUrgencyLevel)

case class JixelAckEventSeverity(result_code: Int, original_message: JixelMsgEventSeverity)

case class JixelAckEventTypology(result_code: Int, original_message: JixelMsgEventTypology)

case class JixelAckEventDescription(result_code: Int, original_message: JixelMsgEventDescription)

case class JixelAckUpdateCommType(result_code: Int, original_message: JixelMsgUpdateCommType)

case class JixelAckUpdateCommTypeError(result_code: Int, original_message: JixelMsgUpdateCommTypeError)
