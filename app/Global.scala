
/**
 * The global configuration.
 */
import com.mohiva.play.silhouette.core.Logger
import com.mohiva.play.silhouette.core.SecuredSettings

import play.api.Application
import play.api.GlobalSettings
import utils.EnvironmentModule

object Global extends GlobalSettings with SecuredSettings with EnvironmentModule with Logger {

  override def onStart(app: Application) {
    play.api.Logger.info("Application has started")
  }

}
