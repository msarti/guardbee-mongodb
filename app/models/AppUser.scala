package models
import play.api.Play.current
import securesocial.core._
import com.novus.salat.annotations.Key
import com.novus.salat.dao.ModelCompanion
import com.novus.salat.dao._
import com.novus.salat._
import com.mongodb.casbah.Imports._
import utils.context._
import se.radley.plugin.salat._
import securesocial.core.providers.Token
import org.joda.time.DateTime
import play.api.Logger

case class AppUser(@Key("_id") id: UserId, firstName: String, lastName: String, fullName: String, email: Option[String],
  avatarUrl: Option[String], authMethod: AuthenticationMethod,
  oAuth1Info: Option[OAuth1Info] = None,
  oAuth2Info: Option[OAuth2Info] = None,
  passwordInfo: Option[PasswordInfo] = None) extends Identity

object AppUserDAO extends SalatDAO[AppUser, UserId](collection = mongoCollection("app_users"))

object TokenDAO extends SalatDAO[Token, ObjectId](collection = mongoCollection("securesocial_tokens"))



object AppUser extends ModelCompanion[AppUser, UserId] {
  val dao = AppUserDAO
  val collection = dao.collection
  val tokenDao = TokenDAO

  def apply(user: Identity): AppUser = {
    AppUser(user.id, user.firstName, user.lastName,
      user.fullName, user.email, user.avatarUrl,
      user.authMethod, user.oAuth1Info,
      user.oAuth2Info, user.passwordInfo)
  }
  
  def save(user: Identity): Identity = {
    val newUser = AppUser(user)
    dao.save(newUser)
    newUser
  }

  def find(id: UserId): Option[AppUser] = {
    dao.findOne(MongoDBObject("_id.id"->id.id, "_id.providerId"->id.providerId))
  }
  
   def findByEmailAndProvider(email: String,providerId: String): Option[AppUser] = {
     dao.findOne(MongoDBObject("email" -> email, "_id.providerId"->providerId))
   }
   
   def save(token: Token) = {
     tokenDao.save(token)
   }
   
   def deleteExpiredTokens(): Unit = {
     tokenDao.remove(MongoDBObject("expirationTime" -> MongoDBObject("$lt"-> DateTime.now)))
   }
   
   def deleteToken(uuid: String): Unit = {
     tokenDao.remove(MongoDBObject("uuid" -> uuid))
   }
   
   def findToken(token: String): Option[Token] = {
     tokenDao.findOne(MongoDBObject("uuid" -> token))
   }
  
}
                      
