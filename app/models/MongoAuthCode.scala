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

case class MongoAuthCode(
  @Key("_id") code: String,
  user: String,
  redirect_uri: String,
  scope: Seq[Scope],
  issued_on: DateTime,
  expire_on: DateTime) extends AuthCode

object MongoAuthCodeDAO extends SalatDAO[MongoAuthCode, String](collection = mongoCollection("auth_codes"))

object MongoAuthCode extends ModelCompanion[MongoAuthCode, String] {
  val dao = MongoAuthCodeDAO
  val collection = dao.collection

  def apply(code: AuthCode): MongoAuthCode = {
    MongoAuthCode(
      code.code,
      code.user,
      code.redirect_uri,
      code.scope,
      code.issued_on,
      code.expire_on)
  }
  
   def save(authCode: AuthCode): MongoAuthCode = {
     val new_auth_code = MongoAuthCode(authCode)
     dao.save(new_auth_code)
     new_auth_code
   }
   
   def consume(code: String): Option[MongoAuthCode] = {
     
     dao.findOne(MongoDBObject("_id" -> code)) match {
       case Some(found) => {
         dao.remove(found)
         Some(found)
       }
       case None => None
     }
   }
  

}