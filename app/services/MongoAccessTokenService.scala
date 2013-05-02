package services

import play.api.Application
import com.elogiclab.guardbee.core.AccessTokenServicePlugin
import models.MongoAccessToken
import com.elogiclab.guardbee.core.AccessToken

class MongoAccessTokenService(application: Application) extends AccessTokenServicePlugin(application) {
  def save(token: AccessToken): AccessToken = MongoAccessToken.create(token)
  def delete(token: String): Unit = MongoAccessToken.delete(token)
  def findByToken(token: String): Option[AccessToken] = MongoAccessToken.findByToken(token)
  def findByRefreshToken(refresh_token: String): Option[AccessToken] = MongoAccessToken.findByRefreshToken(refresh_token)

}