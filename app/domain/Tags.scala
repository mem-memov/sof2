package domain

import play.api.libs.ws.{WSClient, WSRequest}

case class Tags(values: List[String]) {

  def createRequests(client: WSClient): List[WSRequest] =
    values.map(value => Tag(value).createUrl().createRequest(client))
}
