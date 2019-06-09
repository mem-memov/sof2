package domain

import play.api.libs.ws.{WSClient, WSRequest}

case class Url(value: String) {

  def createRequest(client: WSClient): WSRequest = client.url(this.value)
}
