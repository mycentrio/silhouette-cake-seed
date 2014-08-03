
/**
 * The global configuration.
 */
import com.mohiva.play.silhouette.core.Logger
import com.mohiva.play.silhouette.core.SecuredSettings

import controllers.MyApplication
import play.api.Application
import play.api.GlobalSettings
import utils.EnvironmentModule
import play.api.mvc.Controller

trait DynamicGlobal extends GlobalSettings with SecuredSettings with EnvironmentModule with Logger {

  val controllers: Map[Class[_ <: Controller], _ <: Controller] = Map(classOf[MyApplication] -> new MyApplication())

  override def onStart(app: Application) {
    play.api.Logger.info("Application has started")
    play.api.Logger.info("env: " + environment)
  }

  override def getControllerInstance[A](controllerClass: Class[A]): A = controllers.get(controllerClass.asInstanceOf[Class[_ <: Controller]]) match {
    case Some(controller) => controller.asInstanceOf[A]
    case _ => super.getControllerInstance(controllerClass)
  }

}

object Global extends DynamicGlobal