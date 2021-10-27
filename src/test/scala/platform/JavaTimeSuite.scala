package platform

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.must.Matchers

import java.time.{DayOfWeek, LocalDate}

/** Usage examples for the java.time API in form of a test suite. */
class JavaTimeSuite extends AnyFunSuite with Matchers {

  import DayOfWeek._

  def isWorkday(weekDay: DayOfWeek): Boolean =
    weekDay match {
      case SATURDAY | SUNDAY => false
      case _ => true
    }

  test("A LocalDate must be equal to an equal one") {
    val today = LocalDate.now()
    val alsoToday = today.plusDays(30).minusDays(30)
    alsoToday mustBe LocalDate.now()
  }
  test("We have the well-known seven days in a week") {
    val weekDays = DayOfWeek.values().toSeq
    weekDays mustBe Seq(MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY)
    weekDays.map(_.toString) mustBe Seq("MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY")
    weekDays.map(_.getValue) mustBe Seq(1, 2, 3, 4, 5, 6, 7)
  }
  test("Monday to Friday are workdays") {
    val weekDays = DayOfWeek.values()
    for (weekDay <- weekDays) {
      if (Set(MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY) contains weekDay) {
        isWorkday(weekDay) mustBe true
      } else {
        isWorkday(weekDay) mustBe false
      }
    }
    weekDays mustBe Seq(MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY)
    weekDays.map(_.toString) mustBe Seq("MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY")
    weekDays.map(_.getValue) mustBe Seq(1, 2, 3, 4, 5, 6, 7)
  }

}
