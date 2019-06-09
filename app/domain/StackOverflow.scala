package domain

import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._
import scala.util.parsing.json.JSONObject


case class StackOverflow(body: String) {

  def json = Json.parse(body)

  case class Item(is_answered: Boolean, tags: Seq[String])
  case class Items(items: Seq[Item])

  case class Occurence(tag: String, isAnswered: Boolean)
  case class Statistics(total: Int, answered: Int)

  def getStatistics = {

    implicit val ItemReads = Json.reads[Item]
    implicit val ItemsReads = Json.reads[Items]

    val items = this.json.as[Items]

    val listOfTagOccurrences = for {
      item <- items.items
      tag <- item.tags
    } yield Occurence(tag = tag, isAnswered = item.is_answered)

    val statistics = listOfTagOccurrences.foldLeft(Map[String, Statistics]())((map, occurrence) => {
      map.get(occurrence.tag) match {
        case None => map + (occurrence.tag -> Statistics(total = 1, answered = if (occurrence.isAnswered) 1 else 0))
        case Some(statistics) => {
          val newStatistics = statistics.copy(
            total = statistics.total + 1,
            answered = if (occurrence.isAnswered) statistics.answered + 1 else statistics.answered
          )
          map + (occurrence.tag -> newStatistics)
        }
      }
    })

    val records = for {
      (tag, record) <- statistics
    } yield (tag -> JSONObject(Map(
      "total" -> record.total,
      "answered" -> record.answered
    )))

    Json.prettyPrint(Json.parse(JSONObject(records).toString()))
  }
}

