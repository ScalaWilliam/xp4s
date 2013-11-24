Dynamically print Case classes into XML
=

Devised by William Narmontas <https://vynar.com/>

No hand-writing of XML, very useful for rapid prototyping. Performance unknown, but should not be too bad with
the extra memoisation added.

```scala
scala> import com.v.utils.XmlPrinting._
import com.v.utils.XmlPrinting._

scala>   case class Coat(brand: Option[String])
defined class Coat

scala>   case class Human(title: String, name: String, coats: Seq[Coat])
defined class Human

scala>   val human = Human("Mr.", "Johnny", List(Coat(Option("BOSS")), Coat(None), Coat(Option("Armani"))))
human: Human = Human(Mr.,Johnny,List(Coat(Some(BOSS)), Coat(None), Coat(Some(Armani))))

scala>   val printer = new scala.xml.PrettyPrinter(80, 2)
val printer = new scala.xml.PrettyPrinter(80, 2)

scala>   println(printer.format(human.xmlString))
```

Gives us this:
```xml
<Human>
  <title>Mr.</title>
  <name>Johnny</name>
  <coats>
    <Coat>
      <brand>BOSS</brand>
    </Coat>
    <Coat>
      <brand xsi:nil="true"/>
    </Coat>
    <Coat>
      <brand>Armani</brand>
    </Coat>
  </coats>
</Human>```