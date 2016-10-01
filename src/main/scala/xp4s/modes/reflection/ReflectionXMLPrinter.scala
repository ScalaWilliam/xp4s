package xp4s.modes.reflection

import xp4s.XMLPrinter

import scala.xml.{NodeSeq, Text}

/**
  * Created by me on 01/10/2016.
  */
private[reflection] object ReflectionXMLPrinter {

  implicit def reflectionXmlPrinter[T]: XMLPrinter[T] = new XMLPrinter[T] {
    override def print(t: T): NodeSeq = {
      t match {
        case l: Traversable[_] =>
          NodeSeq.fromSeq(l.view.flatMap { item => reflectionXmlPrinter[Any].print(item) }.toSeq)
        case Some(v) => reflectionXmlPrinter[Any].print(v)
        case None => NodeSeq.Empty
        case p: Product =>
          val children = ReflectiveFieldAccess.getFields(p).map {
            case (name, value) =>
              val child = reflectionXmlPrinter[Any].print(value)
              if (child.isEmpty) NodeSeq.Empty
              else <node/>.copy(label = name, child = child)
          }
            <node/>.copy(label = p.productPrefix, child = NodeSeq.fromSeq(children.flatten.toSeq))
        case a => Text(a.toString)
      }
    }
  }

}
