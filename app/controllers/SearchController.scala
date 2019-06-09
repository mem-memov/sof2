package controllers

import javax.inject._
import javax.inject.Inject

import scala.concurrent.Future
import play.api.mvc._
import play.api.libs.ws._

import scala.concurrent.ExecutionContext

import domain.Search

@Singleton
class SearchController @Inject()(cc: ControllerComponents, ws: WSClient, search: Search) extends AbstractController(cc) {

  def index(tags: List[String]) = Action.async {
    implicit val ec = ExecutionContext.global
    search.find(tags).map(listOfBodies =>
      Ok(listOfBodies.reduceLeft((r, b) => r + b))
    )
  }
}


