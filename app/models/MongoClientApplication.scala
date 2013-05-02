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

case class MongoClientApplication(
  @Key("_id") client_id: String,
  client_secret: String,
  owner_user: String,
  app_name: String,
  app_description: Option[String],
  redirect_uris: Seq[String],
  issued_on: DateTime) extends ClientApplication

object MongoClientApplicationDAO extends SalatDAO[MongoClientApplication, String](collection = mongoCollection("app_clients"))

object MongoClientApplication extends ModelCompanion[MongoClientApplication, String] {
  val dao = MongoClientApplicationDAO
  val collection = dao.collection

  def apply(clientApp: ClientApplication): MongoClientApplication = {
    MongoClientApplication(
      clientApp.client_id,
      clientApp.client_secret,
      clientApp.owner_user,
      clientApp.app_name,
      clientApp.app_description,
      clientApp.redirect_uris,
      clientApp.issued_on)
  }

  def save(clientApp: ClientApplication): MongoClientApplication = {
    val newClientApp = MongoClientApplication(clientApp)
    dao.save(newClientApp)
    newClientApp
  }

  def findByClientId(clientId: String): Option[MongoClientApplication] = dao.findOne(MongoDBObject("_id" -> clientId))

  def delete(clientId: String): Unit = dao.remove(MongoDBObject("_id" -> clientId))

}