package services

import play.api.Application
import securesocial.core.UserServicePlugin
import securesocial.core.Identity
import models.AppUser
import securesocial.core.UserId
import securesocial.core.providers.Token

class MongoUserService(application: Application) extends UserServicePlugin(application) {

  def save(user: Identity): Identity = AppUser.save(user)

  def find(id: UserId): Option[Identity] = AppUser.find(id)

  def findByEmailAndProvider(email: String, providerId: String): Option[Identity] = AppUser.findByEmailAndProvider(email, providerId)

  def save(token: Token) = AppUser.save(token)
  
  def deleteExpiredTokens(): Unit = AppUser.deleteExpiredTokens
  
  def deleteToken(uuid: String): Unit = AppUser.deleteToken(uuid)
  
  def findToken(token: String): Option[securesocial.core.providers.Token] = AppUser.findToken(token)
  
}