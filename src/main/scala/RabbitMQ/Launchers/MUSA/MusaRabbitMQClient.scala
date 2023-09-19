package RabbitMQ.Launchers.MUSA

import RabbitMQ.Producer.MUSARabbitMQProducer
import RabbitMQ.Serializer.JixelEventJsonSerializer
import RabbitMQ.{JixelEventReport, _}

object MusaRabbitMQClient {



  var MUSA: MUSARabbitMQProducer = null

  def main(argv: Array[String]): Unit = {

    val testEvent3 = "{\"id\":3760,\"description\":\"descrizione\",\"casualties\":null,\"caller_name\":null,\"caller_phone\":null,\"incident_interface_fire\":false,\"incident_distance\":null,\"incident_id\":null,\"headline\":\"oggetto\",\"date\":\"2022-10-26T09:48:21+0200\",\"completable\":false,\"public\":false,\"incident_subtype\":null,\"incident_severity\":{\"id\":4,\"description\":\"Minor\",\"_locale\":\"en-GB\",\"cap_description_value\":\"Minor\"},\"incident_urgency\":{\"id\":3,\"description\":\"Past\",\"_locale\":\"en-GB\",\"cap_description_value\":\"Past\"},\"incident_status\":{\"id\":1,\"description\":\"Reported\",\"_locale\":\"en-GB\",\"description_name\":\"Event status\",\"description_value\":\"Reported\",\"description_category\":\"Event\",\"additional_parameters\":{\"3\":{\"id\":3,\"description\":\"(1) URL - URL Esterno [Standard Testo]\"}}},\"incident_type\":{\"id\":100,\"description\":\"Alluvione\",\"color\":null,\"icon\":null,\"app_enabled\":false,\"order\":null,\"deleted\":null,\"default_value\":true,\"category\":\"Met\",\"interoperability_incident_type_id\":2,\"post_emergency\":0,\"_locale\":\"en-GB\",\"description_name\":\"Event type\",\"description_value\":\"Alluvione\",\"description_category\":\"Event\",\"additional_parameters\":[],\"subtypes\":\"\",\"cap_type_description\":\"Flood\"},\"incident_msgtype\":{\"id\":5,\"enum_value\":\"Pre-operational\",\"_locale\":\"en-GB\",\"description_name\":\"Event communication type\",\"description_value\":\"Pre-operational\",\"description_category\":\"Event\",\"additional_parameters\":[],\"cap_description_value\":null},\"controllable_object\":{\"id\":75389,\"created\":\"2022-10-26T09:48:21+0200\",\"modified\":\"2022-10-26T09:48:21+0200\",\"organisation_id\":2242,\"controllable_object_type_id\":1,\"last_activity_entry_id\":16311,\"check_code\":\"d422c98670a3cbf55b04077fad191b95\",\"emergency_scenario_id\":null,\"emergency_scenario_activity\":0,\"create_user_id\":9,\"edit_user_id\":9,\"edit_organisation_id\":2242,\"deleted\":null,\"locations\":[{\"id\":57242,\"description\":\"Via Provinciale, Monreale, Palermo, Sicilia\",\"geotype\":\"marker\",\"coordinates\":\"13.22522 38.04631\",\"is_strategic_positions\":null,\"code\":null,\"controllable_object_id\":75389,\"feature_collection\":null,\"road\":\"Via Provinciale\",\"town\":\"Monreale\",\"county\":\"Palermo\",\"postcode\":\"90046\",\"state\":\"Sicilia\",\"country\":\"\",\"loggable_object_id\":23903,\"coordinates_ne\":\"38.04631 13.22522\",\"latitude\":\"38.04630972668314\",\"longitude\":\"13.22521524877168\"}],\"additional_parameter_value\":[],\"creator_with_organisation\":\"Giovanni Francesco Luigi Catania (IES Solutions SRL)\",\"additional_parameters\":[],\"additional_parameters_for_list\":[],\"attachment_file_names\":{\"d67e1dc241ae67d9963e6214cddc233a\":\"Scheda Evento Emer2.pdf\"},\"attachment_url_accesses\":{\"\":\"Scheda Evento Emer2.pdf\"},\"creator_organisation\":\"IES - IES Solutions SRL\"},\"weblink\":\"https:\\/\\/lambda.dev.ies.solutions\\/incidents\\/view\\/3760\",\"recipients\":[],\"updates\":0,\"entity_type\":\"Event\",\"entity_description\":\"3760 - oggetto\",\"instructions\":null}"
    val testEv = "{\n  \"id\": 3760,\n  \"description\": \"descrizione\",\n  \"casualties\": null,\n  \"caller_name\": null,\n  \"caller_phone\": null,\n  \"incident_interface_fire\": false,\n  \"incident_distance\": null,\n  \"incident_id\": null,\n  \"headline\": \"oggetto\",\n  \"date\": \"2022-10-26T09:48:21+0200\",\n  \"completable\": false,\n  \"public\": false,\n  \"incident_subtype\": null,\n  \"incident_severity\": {\n    \"id\": 4,\n    \"description\": \"Minor\",\n    \"_locale\": \"en-GB\",\n    \"cap_description_value\": \"Minor\"\n  },  \n  \"incident_urgency\": {\n    \"id\": 3,\n    \"description\": \"Past\",\n    \"_locale\": \"en-GB\",\n    \"cap_description_value\": \"Past\"\n  },\n  \"incident_status\": {\n    \"id\": 1,\n    \"description\": \"Reported\",\n    \"_locale\": \"en-GB\",\n    \"description_name\": \"Event status\",\n    \"description_value\": \"Reported\",\n    \"description_category\": \"Event\",\n    \"additional_parameters\": []\n  },\n  \"incident_type\": {\n    \"id\": 100,\n    \"description\": \"Alluvione\",\n    \"color\": null,\n    \"icon\": null,\n    \"app_enabled\": false,\n    \"order\": null,\n    \"deleted\": null,\n    \"default_value\": true,\n    \"category\": \"Met\",\n    \"interoperability_incident_type_id\": 2,\n    \"post_emergency\": 0,\n    \"_locale\": \"en-GB\",\n    \"description_name\": \"Event type\",\n    \"description_value\": \"Alluvione\",\n    \"description_category\": \"Event\",\n    \"additional_parameters\": [],\n    \"subtypes\": \"\",\n    \"cap_type_description\": \"Flood\"\n  },  \n  \"incident_msgtype\": {\n    \"id\": 5,\n    \"enum_value\": \"Pre-operational\",\n    \"_locale\": \"en-GB\",\n    \"description_name\": \"Event communication type\",\n    \"description_value\": \"Pre-operational\",\n    \"description_category\": \"Event\",\n    \"additional_parameters\": [],\n    \"cap_description_value\": null\n  },\n  \"controllable_object\": {\n    \"id\": 75389,\n    \"created\": \"2022-10-26T09:48:21+0200\",\n    \"modified\": \"2022-10-26T09:48:21+0200\",\n    \"organisation_id\": 2242,\n    \"controllable_object_type_id\": 1,\n    \"last_activity_entry_id\": 16311,\n    \"check_code\": \"d422c98670a3cbf55b04077fad191b95\",\n    \"emergency_scenario_id\": null,\n    \"emergency_scenario_activity\": 0,\n    \"create_user_id\": 9,\n    \"edit_user_id\": 9,\n    \"edit_organisation_id\": 2242,\n    \"deleted\": null,\n    \"locations\": [\n      {\n        \"id\": 57242,\n        \"description\": \"Via Provinciale, Monreale, Palermo, Sicilia\",\n        \"geotype\": \"marker\",\n        \"coordinates\": \"13.22522 38.04631\",\n        \"is_strategic_positions\": null,\n        \"code\": null,\n        \"controllable_object_id\": 75389,\n        \"feature_collection\": null,\n        \"road\": \"Via Provinciale\",\n        \"town\": \"Monreale\",\n        \"county\": \"Palermo\",\n        \"postcode\": \"90046\",\n        \"state\": \"Sicilia\",\n        \"country\": \"\",\n        \"loggable_object_id\": 23903,\n        \"coordinates_ne\": \"38.04631 13.22522\",\n        \"latitude\": \"38.04630972668314\",\n        \"longitude\": \"13.22521524877168\"\n      }\n    ],\n    \"additional_parameter_value\": [],\n    \"creator_with_organisation\": \"Giovanni Francesco Luigi Catania (IES Solutions SRL)\",\n    \"additional_parameters\": [],\n    \"additional_parameters_for_list\": [],\n    \"attachment_file_names\": {\n      \"d67e1dc241ae67d9963e6214cddc233a\": \"Scheda Evento Emer2.pdf\"\n    },\n    \"attachment_url_accesses\": {\n      \"\": \"Scheda Evento Emer2.pdf\"\n    },\n    \"creator_organisation\": \"IES - IES Solutions SRL\"\n  },\n  \"weblink\": \"https://lambda.dev.ies.solutions/incidents/view/3760\",\n  \"recipients\": [],\n  \"updates\": 0,\n  \"entity_type\": \"Event\",\n  \"entity_description\": \"3760 - oggetto\",\n  \"instructions\": null\n}"
    val ev3767 = "{\n  \"caller_name\": null,\n    \"caller_phone\": null,\n    \"casualties\": null,\n    \"completable\": false,\n    \"controllable_object\": {\n        \"additional_parameter_value\": [\n        ],\n        \"additional_parameters\": [\n        ],\n        \"additional_parameters_for_list\": [\n        ],\n        \"attachment_file_names\": {\n            \"438d782ddf8d26e6faa0a0fe54c2410d\": \"Scheda Evento Emer2.pdf\"\n        },\n        \"attachment_url_accesses\": {\n            \"\": \"Scheda Evento Emer2.pdf\"\n        },\n        \"check_code\": \"5b34764202c7773d2403ec8c6f8faec8\",\n        \"controllable_object_type_id\": 1,\n        \"create_user_id\": 9,\n        \"created\": \"2022-11-18T11:15:01+0100\",\n        \"creator_organisation\": \"IES - IES Solutions SRL\",\n        \"creator_with_organisation\": \"Giovanni Francesco Luigi Catania (IES Solutions SRL)\",\n        \"deleted\": null,\n        \"edit_organisation_id\": 2242,\n        \"edit_user_id\": 9,\n        \"emergency_scenario_activity\": 0,\n        \"emergency_scenario_id\": null,\n        \"id\": 75912,\n        \"last_activity_entry_id\": 16327,\n        \"locations\": [\n            {\n                \"code\": null,\n                \"controllable_object_id\": 75912,\n                \"coordinates\": \"13.12634 38.00304\",\n                \"coordinates_ne\": \"38.00304 13.12634\",\n                \"country\": \"\",\n                \"county\": \"Palermo\",\n                \"description\": \"Monreale, Palermo, Sicilia\",\n                \"feature_collection\": null,\n                \"geotype\": \"marker\",\n                \"id\": 57257,\n                \"is_strategic_positions\": null,\n                \"latitude\": \"38.003037573283\",\n                \"loggable_object_id\": 23926,\n                \"longitude\": \"13.12633829566074\",\n                \"postcode\": \"90042\",\n                \"road\": \"\",\n                \"state\": \"Sicilia\",\n                \"town\": \"Monreale\"\n            }\n        ],\n        \"modified\": \"2022-11-18T11:15:02+0100\",\n        \"organisation_id\": 2242\n    },\n    \"date\": \"2022-11-18T11:15:01+0100\",\n    \"description\": \"desc\",\n    \"entity_description\": \"3767 - TEST MUSA HEADLINE\",\n    \"entity_type\": \"Event\",\n    \"headline\": \"TEST MUSA HEADLINE\",\n    \"id\": 3767,\n    \"incident_distance\": null,\n    \"incident_id\": null,\n    \"incident_interface_fire\": false,\n    \"incident_msgtype\": {\n        \"_locale\": \"en-GB\",\n        \"additional_parameters\": [\n        ],\n        \"cap_description_value\": \"Request\",\n        \"description_category\": \"Event\",\n        \"description_name\": \"Event communication type\",\n        \"description_value\": \"Operational\",\n        \"enum_value\": \"Operational\",\n        \"id\": 1\n    },\n    \"incident_severity\": {\n        \"_locale\": \"en-GB\",\n        \"cap_description_value\": \"Moderate\",\n        \"description\": \"Moderate\",\n        \"id\": 1\n    },\n    \"incident_status\": {\n        \"_locale\": \"en-GB\",\n        \"additional_parameters\": [\n        ],\n        \"description\": \"Pending/Verifying\",\n        \"description_category\": \"Event\",\n        \"description_name\": \"Event status\",\n        \"description_value\": \"Pending/Verifying\",\n        \"id\": 2\n    },\n    \"incident_subtype\": null,\n    \"incident_type\": {\n        \"_locale\": \"en-GB\",\n        \"additional_parameters\": [\n        ],\n        \"app_enabled\": true,\n        \"cap_type_description\": null,\n        \"category\": \"Other\",\n        \"color\": \"#F4511E\",\n        \"default_value\": true,\n        \"deleted\": null,\n        \"description\": \"Explosion\",\n        \"description_category\": \"Event\",\n        \"description_name\": \"Event type\",\n        \"description_value\": \"Explosion\",\n        \"icon\": \"http://anchiosegnalo2.jixel.eu/img/explosion.png\",\n        \"id\": 31,\n        \"interoperability_incident_type_id\": null,\n        \"order\": 6,\n        \"post_emergency\": 0,\n        \"subtypes\": \"\"\n    },\n    \"incident_urgency\": {\n        \"_locale\": \"en-GB\",\n        \"cap_description_value\": \"Immediate\",\n        \"description\": \"Immediate\",\n        \"id\": 1\n    },\n    \"instructions\": null,\n    \"public\": false,\n    \"recipients\": [\n        {\n            \"group_items\": [\n                {\n                    \"id\": 6681,\n                    \"text\": \"IES Solutions SRL\"\n                }\n            ],\n            \"group_name\": \"Organizzazione\"\n        }\n    ],\n    \"updates\": 0,\n    \"weblink\": \"https://lambda.dev.ies.solutions/incidents/view/3767\"\n}"


    val ack_event_severity = "{\n    \"result_code\": 200,\n    \"original_message\":{\n  \"command\":\"updateEventSeverity\",\n  \"data\":{\n    \"incident_id\":3767,\n    \"incident_severity_id\":1\n  }\n}"


    val ss = JixelEventJsonSerializer.toJSon(JixelAckEventSeverity(200,JixelMsgEventSeverity("updateEventSeverity",new JixelMsgDataEventSeverity(1,1))))





    val add1 = JixelEventJsonSerializer.toJSon(JixelAckAddRecipient(200,JixelMsgAddRecipient("addRecipient",new JixelMsgDataAddRecipient(1,List(1, 2, 3, 4)))))
    val up1 = JixelEventJsonSerializer.toJSon(JixelAckUpdateCommType(200,JixelMsgUpdateCommType("updateCommType",JixelMsgDataCommType(3760,1))))
    val up2 = JixelEventJsonSerializer.toJSon(JixelAckEventTypology(200,JixelMsgEventTypology("updateEventTypology",JixelMsgDataEventTypology(3760,1))))
    val up3 = JixelEventJsonSerializer.toJSon(JixelAckUrgencyLevel(200,JixelMsgUrgencyLevel("updateUrgencyLevel",JixelMsgDataUrgencyLevel(3760,1))))
    val up4 = JixelEventJsonSerializer.toJSon(JixelAckEventSeverity(200,JixelMsgEventSeverity("updateEventSeverity",JixelMsgDataEventSeverity(3760,1))))
    val up5 = JixelEventJsonSerializer.toJSon(JixelAckEventDescription(200,JixelMsgEventDescription("updateEventDescription",JixelMsgDataEventDescription(3760,"hello"))))

    println(add1)
    println(up1)
    println(up2)
    println(up3)
    println(up4)
    println(up5)



    /*

            this.getMUSAService().updateEventSeverity(evt, JixelDomainInformation.SEVERITY_LEVEL_MINORE);

            this.getMUSAService().updateEventDescription(evt, "test");
      */



    val parsed_ack = JixelEventJsonSerializer.fromJson(ack_event_severity)
    val parsed_ack2 = JixelEventJsonSerializer.fromJson(ss)


    val parsedJixelEvent3767 = JixelEventJsonSerializer.fromJson(ev3767).asInstanceOf[JixelEvent]
    val parsedJixelEvent = JixelEventJsonSerializer.fromJson(testEv).asInstanceOf[JixelEvent]

    val eventUpdate = JixelEventJsonSerializer.toJSon(JixelEventUpdate(parsedJixelEvent, JixelEventUpdateDetail(1, "the update")))
    val eventReport = JixelEventJsonSerializer.toJSon(JixelEventReport(parsedJixelEvent, List(JixelEventReportFileAttachments("1", "2"))))
    val recipient = JixelEventJsonSerializer.toJSon(Recipient(parsedJixelEvent, "recipient"))

    val ack_message = "{\n    \"result_code\": 200,\n    \"original_message\":{\"command\": \"addRecipient\", \"data\": {\"incident_id\": 3750, \"actor_ids\": [6681, 6879]}}\n}"
    val the_ack_message = JixelEventJsonSerializer.fromJson(ack_message)

    /*
    var response: String = null
    try {
      MUSA = new MUSARabbitMQProducer
      //Notify event
      println(" [x] notifying event")
      response = MUSA.addRecipient(null,List(1))
      println(" [.] Got '" + response + "' from MUSA")
      Thread.sleep(1000)
    } catch {
      case e: Exception => e.printStackTrace()
    } finally {
      if (MUSA == null)
        return;

      try {
        MUSA.close()
      } catch {
        case _: Exception =>
      }
    }*/
  }


}
