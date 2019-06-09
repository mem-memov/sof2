package domain

import play.api.libs.ws.{WSClient, WSRequest, WSResponse}
import javax.inject._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext
import scala.util.{Success, Failure}

@Singleton
class Search @Inject()(client: WSClient) {

  def find(tagValues: List[String]) = {
    implicit val ec = ExecutionContext.global

    val tags = Tags(tagValues)
    val listOfRequests: List[WSRequest] = tags.createRequests(client)
    val listOfFuturesWhithResponse: List[Future[WSResponse]] = listOfRequests.map(_.get())
    val futureOfListOfResponses: Future[List[WSResponse]] = Future.sequence(listOfFuturesWhithResponse)
    val futureOfListOfBodies: Future[List[String]] = futureOfListOfResponses.map(
      future => future.map(
        response => response.body
      )
    )
    futureOfListOfBodies
  }
}
