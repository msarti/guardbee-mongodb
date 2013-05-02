package services

import play.api.Application
import com.elogiclab.guardbee.core.AuthCodeServicePlugin
import models.MongoAuthCode
import com.elogiclab.guardbee.core.AuthCode

class MongoAuthCodeService(application: Application) extends AuthCodeServicePlugin(application) {

  def save(authCode: AuthCode): AuthCode = MongoAuthCode.save(authCode)

  def consume(code: String): Option[AuthCode] = MongoAuthCode.consume(code)

}