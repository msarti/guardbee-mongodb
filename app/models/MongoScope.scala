package models
import play.api.Play.current
import com.elogiclab.guardbee.core.Scope
import com.novus.salat.annotations.Key
import com.novus.salat.dao.SalatDAO
import se.radley.plugin.salat._
import utils.context._
import com.novus.salat.dao.ModelCompanion
import com.mongodb.casbah.commons.MongoDBObject

case class MongoScope(@Key("_id") scope: String, description: String) extends Scope

object MongoScopeDAO extends SalatDAO[MongoScope, String](collection = mongoCollection("app_scopes"))

object MongoScope extends ModelCompanion[MongoScope, String] {
  val dao = MongoScopeDAO
  val collection = dao.collection
  
  def apply(scope: Scope):MongoScope = {
    MongoScope(scope.scope, scope.description)
  }

  def create(scope: Scope): MongoScope = {
    val newScope = MongoScope(scope)
    dao.save(newScope)
    newScope
  }
  
  def findByCode(scope: String): Option[MongoScope] = {
    dao.findOne(MongoDBObject("_id" -> scope))
  }
  
  def delete(scope: String): Unit = {
    dao.remove(MongoDBObject("_id" -> scope))
  }
  
}