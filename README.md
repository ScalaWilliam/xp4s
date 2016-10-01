XML Printing for Scala (xp4s)
=

No hand-writing of XML, very useful for rapid prototyping.


```scala
import xp4s.modes.reflection._
case class Coat(brand: Option[String])
case class Human(title: String, name: String, coats: Seq[Coat])
val human = Human("Mr.", "Johnny", List(Coat(Option("BOSS")), Coat(None), Coat(Option("Armani"))))
val printer = new scala.xml.PrettyPrinter(80, 2)
println(printer.format(xp4s.XMLPrinter.print(human).head))
```

Gives:

```xml
<Human>
  <title>Mr.</title>
  <name>Johnny</name>
  <coats>
    <Coat>
      <brand>BOSS</brand>
    </Coat>
    <Coat/>
    <Coat>
      <brand>Armani</brand>
    </Coat>
  </coats>
</Human>
```
