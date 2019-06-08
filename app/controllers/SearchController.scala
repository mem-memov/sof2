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


import scala.util.{Failure, Success}

@Singleton
class SearchController @Inject()(cc: ControllerComponents, ws: WSClient) extends AbstractController(cc) {
  def index(tags: List[String]) = Action { implicit request: Request[AnyContent] =>
    val request: WSRequest = ws.url("https://api.stackexchange.com/2.2/search?pagesize=100&order=desc&sort=creation&tagged=clojure&site=stackoverflow")
    implicit val ec = ExecutionContext.global
    val futureResponse: Future[WSResponse] = request.get()
    futureResponse onComplete {
      case Success(response) => println(response.json)
      case Failure(t) => println("An error has occurred: " + t.getMessage)
    }
    Ok("tags1: " + tags.toString())
  }
}
