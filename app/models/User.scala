package models

import java.util.UUID

import com.mohiva.play.silhouette.core.Identity
import com.mohiva.play.silhouette.core.LoginInfo

import play.api.libs.json.Json

/**
 * The user object.
 *
 * @param userID The unique ID of the user.
 * @param loginInfo The linked login info.
 * @param firstName Maybe the first name of the authenticated user.
 * @param lastName Maybe the last name of the authenticated user.
 * @param fullName Maybe the full name of the authenticated user.
 * @param email Maybe the email of the authenticated provider.
 * @param avatarURL Maybe the avatar URL of the authenticated provider.
 */
case class User(
  userID: UUID,
  loginInfo: LoginInfo,
  firstName: Option[String],
  lastName: Option[String],
  fullName: Option[String],
  email: Option[String],
  avatarURL: Option[String]) extends Identity

/*
 * Companion object for the station case class
*/
object User {
  import play.api.libs.concurrent.Execution.Implicits._
  import play.api.libs.json.Json
  import play.api.data._
  import play.api.data.Forms._
  import play.api.libs.json._
  import play.api.libs.functional.syntax._

  // create the formats object for LoginInfo.
  implicit val loginInfoFormats = Json.format[LoginInfo]

  // create the formats object for LoginInfo.
  implicit val userFormats = Json.format[User]

}