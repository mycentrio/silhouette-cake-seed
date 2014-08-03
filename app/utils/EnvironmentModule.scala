package utils

import com.mohiva.play.silhouette.contrib.services.CachedCookieAuthenticator
import com.mohiva.play.silhouette.contrib.utils.PlayCacheLayer
import com.mohiva.play.silhouette.contrib.utils.SecureRandomIDGenerator
import com.mohiva.play.silhouette.core.Environment
import com.mohiva.play.silhouette.core.EventBus

import models.User
import models.services.UserService

trait EnvironmentModule extends AuthenticatorServiceModule with UserServiceModule {

  lazy val cacheLayer = new PlayCacheLayer
  lazy val eventBus = EventBus()
  lazy val idGenerator = new SecureRandomIDGenerator()
  lazy val environment: Environment[User, CachedCookieAuthenticator] = {
    Environment[User, CachedCookieAuthenticator](
      userService,
      authenticatorService,
      Map(),
      eventBus)
  }

  def userService: UserService

}