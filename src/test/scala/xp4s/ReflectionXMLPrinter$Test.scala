package xp4s

import ReflectionXMLPrinter$Test._
import xp4s.modes.reflection._
import org.custommonkey.xmlunit.Diff
import org.scalatest.FunSuite

class ReflectionXMLPrinter$Test extends FunSuite {

  test("A composite of Test1, Test2, List and Option types prints as expected") {
    val expected =
      """<Test2><sub><Test1><value>Good.</value></Test1></sub>""" +
        """<maybe>Stuff</maybe><l><Test1><value>1</value></Test1>""" +
        """<Test1><value>2</value></Test1><Test1><value>3</value></Test1></l></Test2>"""
    val test2 = Test2(Test1("Good."), Some("Stuff"), None, (1 to 3).map(_.toString).map(Test1).toList)
    val result = XMLPrinter.print(test2)
    val diff = new Diff(expected, result.toString())
    withClue(s"Got result: $result") {
      if (!diff.similar()) fail(s"$diff")
    }
  }

  test("It prints atuple as expected") {
    val expected = """<Tuple2><_1>This</_1><_2>5</_2></Tuple2>"""
    val have = "This" -> 5
    val result = XMLPrinter.print(have)

    val diff = new Diff(expected, result.toString())
    withClue(s"Got result: $result") {
      if (!diff.similar()) fail(s"$diff")
    }
  }
}

object ReflectionXMLPrinter$Test {

  case class Test1(value: String)

  case class Test2(sub: Test1, maybe: Option[String], maybe2: Option[String], l: List[Test1])

}
