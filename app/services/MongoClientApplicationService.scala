package services

import play.api.Application
import com.elogiclab.guardbee.core.ClientAppServicePlugin
import models.MongoClientApplication
import com.elogiclab.guardbee.core.ClientApplication

class MongoClientApplicationService(application: Application) extends ClientAppServicePlugin(application) {
  def save(clientApp:ClientApplication):ClientApplication = MongoClientApplication.save(clientApp)
  
  def findByClientId(clientId: String): Option[ClientApplication] = MongoClientApplication.findByClientId(clientId)
  
  def delete(clientId:String):Unit = MongoClientApplication.delete(clientId)

}