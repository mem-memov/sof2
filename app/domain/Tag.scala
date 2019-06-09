package domain

case class Tag(value: String) {

  def createUrl(): Url = Url("https://api.stackexchange.com/2.2/search?pagesize=100&order=desc&sort=creation&tagged=" + value + "&site=stackoverflow")
}
