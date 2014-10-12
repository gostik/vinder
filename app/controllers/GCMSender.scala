package controllers

import com.fasterxml.jackson.databind.JsonNode
import models.Notification
import play.api.libs.json._
import play.api.libs.ws.WS
import play.api.libs.ws.WS.WSRequestHolder

/**
 * Created by user_sca on 12.10.2014.
 */
class GCMSender {
   def sendMessageViaGCM(notification : String):Unit= {

     val data = Json.toJson(notification)

     val holder: WSRequestHolder = WS.url(Constants.gcm_send_url)
     val complexHolder: WSRequestHolder = holder
       .withHeaders("Content-Type" -> "application/json",
         "Authorization" -> ("key=" + Constants.gcm_app_key))

     complexHolder.post(data)
   }
}
