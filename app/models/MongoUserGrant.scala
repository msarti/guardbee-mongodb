package models
import play.api.Play.current
import scala.collection.Seq
import org.joda.time.DateTime
import com.elogiclab.guardbee.core._
import com.novus.salat.annotations.Key
import com.novus.salat.dao.SalatDAO
import se.radley.plugin.salat._
import utils.context._
import com.novus.salat.dao.ModelCompanion
import org.bson.types.ObjectId
import com.mongodb.casbah.commons.MongoDBObject

case class MongoUserGrant(@Key("_id") id: ObjectId = new ObjectId, client_id: String,
  user: String,
  scope: Seq[Scope],
  granted_on: DateTime) extends UserGrant

object MongoUserGrantDAO extends SalatDAO[MongoUserGrant, ObjectId](collection = mongoCollection("app_user_grants"))

object MongoUserGrant extends ModelCompanion[MongoUserGrant, ObjectId] {
  val dao = MongoUserGrantDAO
  val collection = dao.collection

  def apply(user_grant: UserGrant): MongoUserGrant =
    MongoUserGrant(client_id = user_grant.client_id,
      user = user_grant.user,
      scope = user_grant.scope, granted_on = user_grant.granted_on)

  def create(grant: UserGrant): MongoUserGrant = {
    val new_user_grant = MongoUserGrant(grant)
    dao.save(new_user_grant)
    new_user_grant
  }

  def delete(client_id: String, user: String): Unit = {
    dao.remove(MongoDBObject("client_id" -> client_id, "user" -> user))
  }

  def findByClientIdAndUser(client_id: String, user: String): Option[MongoUserGrant] = {
    dao.findOne(MongoDBObject("client_id" -> client_id, "user" -> user))

  }

}