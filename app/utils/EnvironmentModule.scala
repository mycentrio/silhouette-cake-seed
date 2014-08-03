package utils

import com.mohiva.play.silhouette.contrib.services.CachedCookieAuthenticator
import com.mohiva.play.silhouette.core.Environment
import com.mohiva.play.silhouette.core.EventBus
import com.mohiva.play.silhouette.core.services.AuthenticatorService

import models.User
import models.services.UserService

trait EnvironmentModule {

  lazy val environment: Environment[User, CachedCookieAuthenticator] = {
    Environment[User, CachedCookieAuthenticator](
      userService,
      authenticatorService,
      Map(),
      eventBus)
  }

  def userService: UserService
  def authenticatorService: AuthenticatorService[CachedCookieAuthenticator]
  def eventBus: EventBus

}