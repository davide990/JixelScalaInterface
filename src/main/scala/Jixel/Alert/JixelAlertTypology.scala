package Jixel.Alert

case class JixelAlertTypology(id: Integer,
                               description: String,
                               default_label: String,
                               default_style: String,
                               accept_response: Boolean,
                               code: String,
                               dichiarazione: String,
                               template_title: String,
                               template_description: String,
                               validation_levels: Integer,
                               is_valid: Boolean,
                               template_start_date: String,
                               template_end_date: String,
                               template_days_after_start_date: Integer,
                               mandatory_public_attachment: Object,
                               next_counter: Object,
                               last_counter_year: Object,
                               template_start_date_offset: Integer,
                               template_specific_message: String,
                               template_specific_message_notification_text: String,
                               template_is_public: Boolean,
                               description_name: String,
                               description_value: String,
                               description_category: String,
                               layout_sheets: List[Any],
                               additional_parameters: List[Any])
