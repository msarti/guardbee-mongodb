package services

import play.api.Application
import com.elogiclab.guardbee.core.ScopeServicePlugin
import com.elogiclab.guardbee.core.Scope
import models.MongoScope

class MongoScopeService(application: Application) extends ScopeServicePlugin(application) {

  def save(scope: Scope): Scope = MongoScope.create(scope)

  def findByCode(scope: String): Option[Scope] = MongoScope.findByCode(scope)

  def delete(scope: String): Unit = MongoScope.delete(scope)

}