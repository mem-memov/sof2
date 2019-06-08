package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import javax.inject.Inject

import scala.concurrent.Future
import scala.concurrent.duration._
import play.api.mvc._
import play.api.libs.ws._
import play.api.http.HttpEntity
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl._
import akka.util.ByteString

import scala.concurrent.ExecutionContext

import play.api.libs.json._

@Singleton
class SearchController @Inject()(cc: ControllerComponents, ws: WSClient) extends AbstractController(cc) {

  private def buildUrl(tag: String) = "https://api.stackexchange.com/2.2/search?pagesize=100&order=desc&sort=creation&tagged=" + tag + "&site=stackoverflow"

  def index(tags: List[String]) = Action.async {
    println(tags.toString())
    implicit val ec = ExecutionContext.global
    val request: WSRequest = ws.url(buildUrl(tags.head))
    val futureResponse: Future[WSResponse] = request.get()
    futureResponse.map(response =>
      Ok(response.body.toString)
    )
  }
}


