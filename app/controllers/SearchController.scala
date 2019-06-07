package controllers

import javax.inject._
import play.api._
import play.api.mvc._

@Singleton
class SearchController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  def index(tags: List[String]) = Action { implicit request: Request[AnyContent] =>
    Ok("111" + tags.toString())
  }
}
