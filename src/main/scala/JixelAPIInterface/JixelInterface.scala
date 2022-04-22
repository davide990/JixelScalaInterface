package JixelAPIInterface

import JixelAPIInterface.Alert.{JixelAlert, JixelAlertList, JixelFileAttachment}
import JixelAPIInterface.Login.{JixelCredential, LoginToken}
import JixelAPIInterface.Serializer.JixelFileAttachmentListSerializer
import net.liftweb.json.Extraction.decompose
import net.liftweb.json.{DefaultFormats, parse, prettyRender}
import org.apache.http.HttpHeaders
import org.apache.http.client.methods.{CloseableHttpResponse, HttpGet, HttpPost}
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.HttpClients
import org.apache.http.util.EntityUtils

import java.io.{File, FileOutputStream}

object JixelInterface {
  implicit val formats = DefaultFormats + new JixelFileAttachmentListSerializer

  val JixelAddress = "https://nettunit.jixel.eu/api/users/login"
  val client = HttpClients.createDefault()

  /**
   * Connect to Jixel using the input login (username/password or, if available, token)
   *
   * @param loginToken
   * @return
   */
  def connect(loginToken: LoginToken): String = {
    val httpPost = new HttpPost(JixelAddress)
    httpPost.setHeader("Accept", "application/json")
    httpPost.setHeader("Content-type", "application/json")
    httpPost.setEntity(getLoginAsStringEntity(loginToken)) //Json to String entity
    val response = client.execute(httpPost) // POST request
    val statusCode = response.getStatusLine.getStatusCode
    assert(statusCode == 200, s"Expected 200, received $statusCode") // Assert everything ok

    getConnectionResultAsJson(response)
    //response // return the response
  }


  def requestAlertsList(token: String): JixelAlertList = {
    val httpGet = new HttpGet("https://nettunit.jixel.eu/api/alerts/all")
    httpGet.setHeader(HttpHeaders.CONTENT_TYPE, "application/json")
    httpGet.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token)
    val response = client.execute(httpGet) // GET request
    parseAlertList(response)
  }


  /**
   * Convert the underlying entity in the input CloseableHttpResponse to a string
   *
   * @param c a CloseableHttpResponse, obtained as result of a (successful) connection to Jixel
   * @return a Json string
   */
  def getConnectionResultAsJson(c: CloseableHttpResponse): String = EntityUtils.toString(c.getEntity)

  /**
   * Convert a LoginToken class to a StringEntity to be used for login into Jixel
   *
   * @param c
   * @return
   */
  def getLoginAsStringEntity(c: LoginToken): StringEntity = new StringEntity(prettyRender(decompose(c)))

  /**
   * Parse the result from login to Jixel to a JixelCredential object
   *
   * @param s
   * @return
   */
  def parseToJixelCredential(s: String): JixelCredential = parse(s).extract[JixelCredential]

  /**
   * Parse all alerts (in json) from Jixel and return a corresponding JixelAlertList object
   *
   * @param alertListResponse
   * @return
   */
  def parseAlertList(alertListResponse: CloseableHttpResponse): JixelAlertList = {
    val jsonvalue = parse(getConnectionResultAsJson(alertListResponse).toString)
    jsonvalue.extract[JixelAlertList]
  }

  /**
   * Retrieve the list of all the attachments in the input alerts list
   *
   * @param alertList
   * @return
   */
  def getAttachmentsList(alertList: JixelAlertList): List[JixelFileAttachment] = {
    val l = alertList.alerts.map(a => a.controllable_object.attachment_file_names).toList
    l.flatMap(x => x.list).toList
  }

  /**
   * Retrieve the details of an alert
   *
   * @param id        the ID of the alert
   * @param alertList the list of alerts
   * @return
   */
  def getAlertDetails(id: Int, alertList: JixelAlertList): Option[JixelAlert] = {
    alertList.alerts.find(alert => alert.id == id)
  }

  /**
   * Download the attachment with the given ID from Jixel to file
   *
   * @param id
   * @param loginToken
   * @param outFname
   */
  def saveAttachmentImageToDisk(id: String, loginToken: String, outFname: String): Unit = {
    val connectionString = s"https://nettunit.jixel.eu/api/attachments/view/${loginToken}/${id}"
    val response = client.execute(new HttpGet(connectionString)) // GET request
    response.getEntity.writeTo(new FileOutputStream(new File(outFname)));
  }

  def closeConnection() : Unit = client.close()

}
