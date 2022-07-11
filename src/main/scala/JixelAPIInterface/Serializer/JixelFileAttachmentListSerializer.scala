package JixelAPIInterface.Serializer

import JixelAPIInterface.Alert.{JixelFileAttachment, JixelFileAttachmentList}
import net.liftweb.json.JsonAST.{JField, JObject, JValue}
import net.liftweb.json.{Formats, JArray, JString, MappingException, Serializer, TypeInfo}

/**
 * This class is used to parse file attachments in jixel events. Because file attachments are
 * in polymorphic JSon format, it requires a specific parsing rule, which is found in this class.
 *
 * @author Davide A. Guastella (davide.guastella@icar.cnr.it)
 * @see See [[https://stackoverflow.com/questions/7525875/polymorphic-lift-json-deserialization-in-a-composed-class]] and [[https://stackoverflow.com/questions/31676098/lift-json-custom-serializer-for-java-8-localdatetime-throwing-mapping-exception]]
 *
 */
class JixelFileAttachmentListSerializer extends Serializer[JixelFileAttachmentList] {
  private val Class = classOf[JixelFileAttachmentList]

  def deserialize(implicit format: Formats): PartialFunction[(TypeInfo, JValue), JixelFileAttachmentList] = {
    case (TypeInfo(Class, ti), json) => json match {
      case JObject(List(JField(field_name, field_value))) => {
        val value = field_value match {
          case JString(s) => s
          case _ => ""
        }
        JixelFileAttachmentList(List(JixelFileAttachment(field_name, value)))
      }

      case JArray(_) => JixelFileAttachmentList(List())
      case x => throw new MappingException("Can't convert " + x + " to JixelFileAttachment.")
    }
  }

  def serialize(implicit format: Formats) = {
    //note: this is unused...
    case p: JixelFileAttachment => JArray(JString("E") :: JString("E") :: Nil)
  }

}
