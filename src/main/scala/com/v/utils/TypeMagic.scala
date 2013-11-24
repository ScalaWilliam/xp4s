package com.v.utils

object TypeMagic {


  /*
  Relevant:
   1. http://stackoverflow.com/questions/15067872/get-scala-type-for-a-java-lang-classt-in-scala-2-10
   2. http://docs.scala-lang.org/overviews/reflection/overview.html
   3. http://www.encodedknowledge.com/2013/01/scala-2-10-reflection-experiments/
   4. http://stackoverflow.com/questions/4697534/knowing-if-a-scala-object-is-an-instance-of-case-class
   5. http://stackoverflow.com/questions/7525142/how-to-programmatically-determine-if-the-the-class-is-a-case-class-or-a-simple-c
   6. http://stackoverflow.com/questions/15730155/why-dont-scala-case-class-fields-reflect-as-public
   7. http://stackoverflow.com/questions/2224251/reflection-on-a-scala-case-class
   8. http://stackoverflow.com/questions/16079113/scala-2-10-reflection-how-do-i-extract-the-field-values-from-a-case-class

   */


  import scala.reflect.runtime.universe._

  /* Borrowed from somebody */
  class Memoize1[-T, +R](f: T => R) extends (T => R) {
    private[this] val vals = scala.collection.mutable.Map.empty[T, R]
    def apply(x: T): R = vals.getOrElseUpdate(x, f(x))
  }

  def getType[T](clazz: Class[T])(implicit runtimeMirror: Mirror) =
    runtimeMirror.classSymbol(clazz).toType

  implicit val mirror = runtimeMirror(getClass.getClassLoader)

  type productable[T] = Class[T with Product]

  // Get the list of fields based on a class: Map[String, Field]
  // Note that scala runtime reflection at the moment is not thread-safe.

  def getFieldsL(clazz: productable[_]) = this.synchronized {{
    val args = getType(clazz).member(nme.CONSTRUCTOR).asMethod.paramss.head
    val argNames = for ( a <- args ) yield a.name.decoded
    for {
      field <- clazz.getDeclaredFields
      name = field.getName
      if argNames contains name
    } yield (name, field)
  }.toMap}


  // Create a field getter which will act on any input.
  // All selected `fields` will be used and the results returned as a
  // Map[String, Any]
  def makeFieldGetterF(fields: Map[String, java.lang.reflect.Field]): (Product => Map[String, Any]) =
    (input: Product) => {
      for {
        (name, field) <- fields.toSeq
        madeAccessible = field.setAccessible(true)
        value = field.get(input)
      }
      yield (name, value) }.toMap


  def makeFieldGetter[T<:Product](clazz:productable[_]) = makeFieldGetterF(getFieldsL(clazz))

  val getFieldGetter = new Memoize1(makeFieldGetter)

  def getFields(a: Product) = getFieldGetter(a.getClass.asInstanceOf[productable[_]])(a)

  val getFieldsOf = getFields(_)

}
