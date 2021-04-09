package probe

import scala.io.StdIn.readLine

/**
  * @author Christoph Knabe
  */
object Zinsen {

  def foo(x: Array[String]): String = x.foldLeft("")((a, b) => a + b)

  def main(args: Array[String]): Unit = {
    println("Berechnung eines Verzinsungsverlaufs")
    val bausparsumme = readLine("Bausparsumme? ").toDouble
    val abschlussgebuehr = bausparsumme / 100
    val sparzinssatz = readLine("Sparzinssatz? ").toDouble
    val monatseinzahlung = readLine("Monatseinzahlung? ").toDouble
    val anzahlJahre = readLine("Anzahl Jahre? ").toInt
    val monatszinsfaktor = sparzinssatz / 100 / 12
    var saldo = -abschlussgebuehr
    println("Anfangssaldo:\t" + saldo)
    for (jahr <- 0 until anzahlJahre) {
      var jahreszins: Double = 0
      for (monat <- 1 to 12) {
        val monatszins = if (saldo > 0) saldo * monatszinsfaktor else 0
        jahreszins += monatszins
        saldo += monatseinzahlung
        println(jahr.toString + "-" + monat + "\t" + monatseinzahlung + "\t-> " + saldo)
      }
      saldo += jahreszins
      println(jahr.toString + "-Zins\t" + jahreszins + "\t-> " + saldo)
    }
  }

}
