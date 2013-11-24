package com.v.utils

/**
 * 
 * Scala XML object printing example.
 * 2013 - William Narmontas https://vynar.com/
 * 
 */
import org.scalatest._
import scala.xml.Node

class XmlPrintingTest extends FlatSpec with Matchers {

  import XmlPrinting._

  case class Test1(value: String)
  case class Test2(sub: Test1, maybe: Option[String], maybe2: Option[String], l: Seq[Test1])


  it should "Print a composite of Test1, Test2, List and Option types" in {
    val expected = <Test2><sub><Test1><value>Good.</value></Test1></sub><maybe>Stuff</maybe><maybe2 xsi:nil="true"/><l><Test1><value>1</value></Test1><Test1><value>2</value></Test1><Test1><value>3</value></Test1></l></Test2>.toString
    val have = Test2(Test1("Good."), Some("Stuff"), None, for (i <- 1 to 3) yield Test1(i.toString)).xmlString.toString
    have should be === expected
  }

  "XmlPrinter" should "print a simple string as xml" in {
    val expected: String = <String>Hello</String>.toString
    val have: String = "Hello".xmlString.toString
    have should be === expected
  }

  "XmlPrinter" should "as root, print an empty option" in {
    val expected: String = <Some><x xsi:nil="true"/></Some>.toString
    val q: Option[String] = None
    val have: String = Option(q).xmlString.toString
    have should be === expected
  }

  "XmlPrinter" should "as root, print an actual option" in {
    val expected: String = <Some><x>Hello</x></Some>.toString
    val have: String = Option("Hello").xmlString.toString
    have should be === expected
  }

  it should "Print a tuple in this format as xml" in {
    val expected = <Tuple2><_1>This</_1><_2>5</_2></Tuple2>.toString
    val have = ("This" -> 5).xmlString.toString
    have should be === expected
  }



/*  case class Coat(brand: Option[String])

  case class Human(title: String, name: String, coats: Seq[Coat])

  val human = Human("Mr.", "Johnny", List(Coat(Option("BOSS")), Coat(None), Coat(Option("Armani"))))

  val printer = new scala.xml.PrettyPrinter(80, 2)

  println(printer.format(human.xmlString))

  println(printer.format(List(List("a","b"),List(5,6),List(human,human)).xmlString))

  println(printer.format(human.xmlString))

  println(printer.format(List(List("a","b"),List(5,6),List(human,human)).xmlString))
  */
}
