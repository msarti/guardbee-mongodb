package models

import scala.collection.Seq
import org.joda.time.DateTime
import com.elogiclab.guardbee.core._
import com.novus.salat.annotations.Key
import com.novus.salat.dao.SalatDAO
import play.api.Play.current
import se.radley.plugin.salat._
import utils.context._
import com.novus.salat.dao.ModelCompanion
import com.mongodb.casbah.commons.MongoDBObject

case class MongoAccessToken(
  @Key("_id") token: String,
  refresh_token: String,
  token_type: String,
  user: String,
  client_id: String,
  scope: Seq[Scope],
  issued_on: DateTime,
  token_expiration: DateTime,
  refresh_token_expiration: DateTime) extends AccessToken

object MongoAccessTokenDAO extends SalatDAO[MongoAccessToken, String](collection = mongoCollection("access_tokens"))

object MongoAccessToken extends ModelCompanion[MongoAccessToken, String] {

  val dao = MongoAccessTokenDAO
  val collection = dao.collection

  def apply(token: AccessToken): MongoAccessToken = {
    MongoAccessToken(
      token.token,
      token.refresh_token,
      token.token_type,
      token.user,
      token.client_id,
      token.scope,
      token.issued_on,
      token.token_expiration,
      token.refresh_token_expiration)
  }

  def create(token: AccessToken): MongoAccessToken = {
    val new_access_token = MongoAccessToken(token)
    dao.save(new_access_token)
    new_access_token
  }
  def delete(token: String): Unit = {
    dao.remove(MongoDBObject("_id" -> token))
  }
  def findByToken(token: String): Option[MongoAccessToken] = {
    dao.findOne(MongoDBObject("_id" -> token))
  }
  def findByRefreshToken(refresh_token: String): Option[MongoAccessToken] = {
    dao.findOne(MongoDBObject("refresh_token" -> refresh_token))
  }

}