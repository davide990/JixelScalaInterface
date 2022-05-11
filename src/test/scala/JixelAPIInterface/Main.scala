package JixelAPIInterface

import JixelAPIInterface.Login.ECOSUsers

object Main {


  def main(args: Array[String]): Unit = {
    /**
     * Connect to jixel using login (user/password)
     */
    val connectionResult = JixelInterface.connect(ECOSUsers.davide_login)
    /**
     * Parse obtained login json to JixelCredential object
     */
    val jixelUser = JixelInterface.parseToJixelCredential(connectionResult)

    println(s"Login OK\n Logged user: ${jixelUser.user.username}\nName:${jixelUser.user.name}")

    /**
     * Retrieve the alerts list from Jixel
     */
    val alertList = JixelInterface.requestAlertsList(jixelUser.token)

    /**
     * Retrieve all the attachments from alerts
     */
    val attachments = JixelInterface.getAttachmentsList(alertList)

    /**
     * Get the details about the alert with id "62"
     */
    val alert_62 = JixelInterface.getAlertDetails(62, alertList)

    /**
     * Retrieve the attachment image with the specified ID and save to disk
     */
    JixelInterface.saveAttachmentImageToDisk("a3a60a1fa198a15856de8e8a3a7d22ff",
      jixelUser.token,
      "/Users/dguastel/Desktop/output.png")

    /**
     * Close the connection
     */
    JixelInterface.closeConnection()
    print("All done...")
  }


}
