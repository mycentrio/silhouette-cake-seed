package controllers

import java.util.UUID

import scala.concurrent.Future

import com.mohiva.play.silhouette.contrib.services.CachedCookieAuthenticator
import com.mohiva.play.silhouette.core.LoginEvent
import com.mohiva.play.silhouette.core.LoginInfo
import com.mohiva.play.silhouette.core.SignUpEvent
import com.mohiva.play.silhouette.core.Silhouette
import com.mohiva.play.silhouette.core.exceptions.AuthenticationException
import com.mohiva.play.silhouette.core.providers.CredentialsProvider

import forms.SignUpForm
import models.User
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc.Action
import utils.EnvironmentModule

/**
 * The sign up controller.
 *
 * @param env The Silhouette environment.
 * @param userService The user service implementation.
 * @param authInfoService The auth info service implementation.
 * @param avatarService The avatar service implementation.
 * @param passwordHasher The password hasher implementation.
 */
object SignUpController
  extends Silhouette[User, CachedCookieAuthenticator]
  with EnvironmentModule {

  /**
   * Registers a new user.
   *
   * @return The result to display.
   */
  def signUp = Action.async { implicit request =>
    SignUpForm.form.bindFromRequest.fold(
      form => Future.successful(BadRequest(views.html.signUp(form))),
      data => {
        val loginInfo = LoginInfo(CredentialsProvider.Credentials, data.email)
        val authInfo = passwordHasher.hash(data.password)
        val user = User(
          userID = UUID.randomUUID(),
          loginInfo = loginInfo,
          firstName = Some(data.firstName),
          lastName = Some(data.lastName),
          fullName = Some(data.firstName + " " + data.lastName),
          email = Some(data.email),
          avatarURL = None)
        for {
          avatar <- avatarService.retrieveURL(data.email)
          user <- userService.save(user.copy(avatarURL = avatar))
          authInfo <- authInfoService.save(loginInfo, authInfo)
          maybeAuthenticator <- env.authenticatorService.create(user)
        } yield {
          maybeAuthenticator match {
            case Some(authenticator) =>
              env.eventBus.publish(SignUpEvent(user, request, request2lang))
              env.eventBus.publish(LoginEvent(user, request, request2lang))
              env.authenticatorService.send(authenticator, Redirect(routes.MyApplication.index))
            case None => throw new AuthenticationException("Couldn't create an authenticator")
          }
        }
      })
  }
}