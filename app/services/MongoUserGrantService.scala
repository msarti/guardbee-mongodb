package services

import play.api.Application
import com.elogiclab.guardbee.core.UserGrantServicePlugin
import com.elogiclab.guardbee.core.UserGrant
import models.MongoUserGrant
import com.elogiclab.guardbee.core.AuthorizationRequest
import models.MongoAuthorizationRequest

class MongoUserGrantService(application: Application) extends UserGrantServicePlugin(application) {
  
  def save(authorization: UserGrant): UserGrant = MongoUserGrant.create(authorization)

  def saveRequest(authzRequest: AuthorizationRequest): AuthorizationRequest = MongoAuthorizationRequest.saveRequest(authzRequest)

  def consumeRequest(code: String): Option[AuthorizationRequest] =MongoAuthorizationRequest.consumeRequest(code)

  def findByClientIdAndUser(client_id: String, user: String): Option[UserGrant] = MongoUserGrant.findByClientIdAndUser(client_id, user)

  def delete(client_id: String, user: String): Unit = MongoUserGrant.delete(client_id, user)

}