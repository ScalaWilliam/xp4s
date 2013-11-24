package com.v.utils

object XmlPrinting {


/*

Took ideas from:
 9. http://stackoverflow.com/questions/15718506/scala-how-to-print-case-classes-like-pretty-printed-tree
10. https://github.com/nikita-volkov/sext/blob/master/src/main/scala/sext/package.scala#L118-L173
11. https://github.com/nikita-volkov/sext
12. http://stackoverflow.com/questions/15718506/scala-how-to-print-case-classes-like-pretty-printed-tree
13. https://github.com/ymasory/labeled-tostring
14. http://docs.scala-lang.org/tutorials/tour/case-classes.html
 */


  implicit def headNode(nodes: Traversable[scala.xml.Node]):scala.xml.Node = nodes.head

  import TypeMagic.getFieldsOf

  implicit class pretty[A](a:A) {

    // we might want to have different behaviours for types depending on their rootness.
    // e.g. we don't want to return a plain string into straight xml.
    // might need further revision.

    def xmlString: Traversable[scala.xml.Node] = a match {
      case list: Traversable[_] =>
        <list>{list.collect{
          case subList: Traversable[_] => subList.xmlString
          case item=>{<item>{item.childXmlString}</item>}
        }}</list>
      case x:String => <String>{x}</String>
      case x:Unit => <Unit/>
      case x:Any => x.childXmlString
    }

    def childXmlString: Traversable[scala.xml.Node] = a match {
      case list: Traversable[_] =>
        for ( item <- list; sub <- item.childXmlString )
        yield sub
      case product: Product =>
        <node>{
          for {
            (title, value) <- getFieldsOf(product)
            base = <node/>.copy(label = title)
          } yield value match {
            case Some(x) => base.copy(child = x.childXmlString.toSeq)
            case None => <node xsi:nil="true"/>.copy(label = title)
            case x => base.copy(child = x.childXmlString.toSeq)
          }
          }</node>.copy(label=product.productPrefix)
      case null =>
          <nil xsi:nil="true"/>
      case x => scala.xml.Text(x.toString)
    }
  }


}