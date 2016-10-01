package xp4s

import scala.xml.NodeSeq

trait XMLPrinter[T] {
  xp =>
  def print(t: T): scala.xml.NodeSeq

  def contraMap[V](f: V => T): XMLPrinter[V] = new XMLPrinter[V] {
    override def print(t: V): NodeSeq = xp.print(f(t))
  }
}

object XMLPrinter {

  def apply[T: XMLPrinter]: XMLPrinter[T] = implicitly[XMLPrinter[T]]

  def print[T: XMLPrinter](t: T): NodeSeq = implicitly[XMLPrinter[T]].print(t)

}
