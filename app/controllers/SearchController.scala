package controllers

import javax.inject._
import play.api._
import play.api.mvc._

@Singleton
class SearchController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  def index() = Action { implicit request: Request[AnyContent] =>
    Ok("111")
  }
}
