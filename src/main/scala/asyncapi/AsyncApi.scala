package asyncapi

import eu.timepit.refined._
import eu.timepit.refined.api.Refined
import eu.timepit.refined.boolean._
import eu.timepit.refined.collection._
import eu.timepit.refined.string._
import shapeless.{ ::, HNil }

import AsyncApi._

case class AsyncApi(
  asyncapi: String Refined VersionString,
  info: Info,
  baseTopic: Option[String],
  servers: Option[List[Server]],
  topics: Map[String Refined NotStartsWithDot, TopicItem],
  components: Option[Components],
  security: Option[Map[String, List[String] Refined Empty]],
  tags: Option[List[Tag]],
  externalDocs: Option[ExternalDocumentation])

object AsyncApi {
  type VersionString = And[StartsWith[W.`"1.0."`.T], MatchesRegex[W.`"[0-9]+\\\\.[0-9]+\\\\.[0-9]+"`.T]]

  type NotStartsWithDot = Not[StartsWith[W.`"."`.T]]

  case class Info(
    title: String,
    version: String,
    description: Option[String],
    termsOfService: Option[String],
    contact: Option[Contact],
    license: Option[License])

  case class Contact(
    name: Option[String],
    url: Option[String Refined Url],
    email: Option[String])

  case class License(
    name: String,
    url: Option[String Refined Url])

  type Scheme = OneOf[W.`"amqp"`.T :: W.`"amqps"`.T :: W.`"mqtt"`.T :: W.`"mqtts"`.T :: W.`"ws"`.T :: W.`"wss"`.T :: W.`"stomp"`.T :: W.`"stomps"`.T :: HNil]

  case class Server(
    url: String Refined Url,
    scheme: String Refined Scheme,
    description: Option[String],
    variables: Option[Map[String, ServerVariable]])

  case class ServerVariable(
    enum: Option[List[String]],
    default: Option[String],
    description: Option[String])

  case class TopicItem(
    $ref: Option[String],
    subscribe: Option[Message],
    publish: Option[Message])

  case class Message(
    headers: Option[Schema],
    payload: Option[Schema],
    summary: Option[String],
    description: Option[String],
    tags: Option[List[Tag]],
    externalDocs: Option[ExternalDocumentation])

  case class Tag(
    name: String,
    description: Option[String],
    externalDocs: Option[ExternalDocumentation])

  case class ExternalDocumentation(
    description: Option[String],
    url: String Refined Url)

  case class Reference[A](
    $ref: String)

  type AllowedChars = MatchesRegex[W.`"^[a-zA-Z0-9\\\\.\\\\-_]+$"`.T]

  case class Components(
    schemas: Option[Map[String Refined AllowedChars, Either[Message, Reference[Message]]]],
    messages: Option[Map[String Refined AllowedChars, Either[Message, Reference[Message]]]],
    securitySchemes: Option[Map[String Refined AllowedChars, Either[SecurityScheme, Reference[SecurityScheme]]]])

  case class Schema( /* TODO: Figure out what goes here. The JSON Schema Spec is horrid */ )

  type SecuritySchemeType = AnyOf[W.`"userPassword"`.T :: W.`"apiKey"`.T :: W.`"X509"`.T :: W.`"symmetricEncryption"`.T :: W.`"asymmetricEncryption"`.T :: W.`"httpApiKey"`.T :: W.`"http"`.T :: HNil]

  type ApiKeyLocation = AnyOf[W.`"user"`.T :: W.`"password"`.T :: W.`"query"`.T :: W.`"header"`.T :: W.`"cookie"`.T :: HNil]

  /* TODO: Convert this to a trait and set the `type` field as the discriminator with circe-generic-extras */
  case class SecurityScheme(
    `type`: String Refined SecuritySchemeType,
    description: Option[String],
    name: Option[String],
    in: Option[String Refined ApiKeyLocation],
    scheme: Option[String],
    bearerFormat: Option[String])
}
