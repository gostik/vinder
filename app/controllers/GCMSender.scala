package controllers

import com.fasterxml.jackson.databind.JsonNode
import models.Notification
import play.Logger
import play.api.libs.json._
import play.api.libs.ws.{Response, WS}
import play.api.libs.ws.WS.WSRequestHolder
import play.libs.F
import play.mvc.Result
import play.api.libs.concurrent.Execution.Implicits._

/**
 * Created by user_sca on 12.10.2014.
 */
class GCMSender {

  def log(response: Response): Unit = {
    Logger.debug(response.toString);
    response.status == 200
  }

  def sendMessageViaGCM(registration_id: String, type_of_message:String ,notification: String): Unit = {



    val holder: WSRequestHolder = WS.url(Constants.gcm_send_url)
    val complexHolder: WSRequestHolder = holder
      .withHeaders(//"Content-Type" -> "",
        "Authorization" -> ("key=" + Constants.gcm_app_key))

    val data = "{\""+type_of_message+"\":\""+notification+"\""+"}";
    //val data = Json.toJson(notification);

    complexHolder
      .withQueryString(
        "registration_id" -> registration_id,
        "data" -> notification)
      .post(String.valueOf("")).map {
      response =>
        log(response)
    }

    //{"type_of_message":notification}

  }

}
