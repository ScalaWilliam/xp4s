package xp4s.modes

import xp4s.XMLPrinter

/**
  * Created by me on 01/10/2016.
  */
package object reflection {
  implicit def xmlPrinter[T]: XMLPrinter[T] = ReflectionXMLPrinter.reflectionXmlPrinter
}
