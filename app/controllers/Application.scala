package controllers

import com.mohiva.play.silhouette.contrib.services.CachedCookieAuthenticator
import com.mohiva.play.silhouette.core.Environment
import com.mohiva.play.silhouette.core.Silhouette

import models.User
import play.api.mvc.Action
import utils.EnvironmentModule

object MyApplication
  extends Silhouette[User, CachedCookieAuthenticator] with EnvironmentModule {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

}