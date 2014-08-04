package controllers

import com.mohiva.play.silhouette.contrib.services.CachedCookieAuthenticator
import com.mohiva.play.silhouette.core.Silhouette

import models.User
import utils.EnvironmentModule

object MyApplication
  extends Silhouette[User, CachedCookieAuthenticator]
  with EnvironmentModule {

  /**
   * Renders the index page.
   *
   * @returns The result to send to the client.
   */
  def index = UserAwareAction { implicit request =>
    val userName = request.identity match {
      case Some(identity) => identity.fullName
      case None => "Guest"
    }
    Ok("Hello %s".format(userName))
  }

  /**
   * Renders the home page.
   *
   * @returns The result to send to the client.
   */
  def home = SecuredAction { implicit request =>
    val userName = request.identity.fullName
    Ok("Hello %s".format(userName))
  }

}