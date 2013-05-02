package models

import scala.collection.Seq
import org.joda.time.DateTime
import com.elogiclab.guardbee.core.AuthorizationRequest
import com.elogiclab.guardbee.core.Scope
import com.novus.salat.annotations.Key
import com.novus.salat.dao.SalatDAO
import play.api.Play.current
import se.radley.plugin.salat._
import utils.context._
import com.novus.salat.dao.ModelCompanion
import com.mongodb.casbah.commons.MongoDBObject

case class MongoAuthorizationRequest(
  @Key("_id") code: String,
  client_id: String,
  user: String,
  response_type: String,
  redirect_uri: String,
  scope: Seq[Scope],
  state: Option[String],
  request_timestamp: DateTime,
  request_expiration: DateTime) extends AuthorizationRequest

object MongoAuthorizationRequestDAO extends SalatDAO[MongoAuthorizationRequest, String](collection = mongoCollection("app_authorization_requests"))

object MongoAuthorizationRequest extends ModelCompanion[MongoAuthorizationRequest, String] {
  val dao = MongoAuthorizationRequestDAO
  val collection = dao.collection

  def apply(auth_req: AuthorizationRequest): MongoAuthorizationRequest = {
    MongoAuthorizationRequest(auth_req.code,
      auth_req.client_id,
      auth_req.user,
      auth_req.response_type,
      auth_req.redirect_uri,
      auth_req.scope,
      auth_req.state,
      auth_req.request_timestamp,
      auth_req.request_expiration)
  }

  def saveRequest(authzRequest: AuthorizationRequest): MongoAuthorizationRequest = {
    val new_auth_request = MongoAuthorizationRequest(authzRequest)
    dao.save(new_auth_request)
    new_auth_request
  }
  
  def consumeRequest(code: String): Option[MongoAuthorizationRequest] = {
    dao.findOne(MongoDBObject("code"->code)) match {
      case Some(auth) => {
        dao.remove(MongoDBObject("code"->code))
        Some(auth)
      }
      case _ => None
    }
  }

}