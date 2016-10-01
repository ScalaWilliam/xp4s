package xp4s.modes.reflection

import scala.reflect.runtime.universe._

private[reflection] object ReflectiveFieldAccess {

  private def getType[T](clazz: Class[T])(implicit runtimeMirror: Mirror) =
    runtimeMirror.classSymbol(clazz).toType

  private implicit val mirror = runtimeMirror(getClass.getClassLoader)

  private def getFieldsL(clazz: Class[_]): Map[String, java.lang.reflect.Field] = this.synchronized {
    {
      val args = getType(clazz).member(nme.CONSTRUCTOR).asMethod.paramss.head
      val argNames = for (a <- args) yield a.name.decoded
      for {
        field <- clazz.getDeclaredFields
        name = field.getName
        if argNames contains name
      } yield (name, field)
    }.toMap
  }

  private def getField(product: Product)(field: java.lang.reflect.Field): Any = {
    val isAccessible = field.isAccessible
    field.setAccessible(true)
    try field.get(product)
    finally field.setAccessible(isAccessible)
  }

  def getFields(a: Product): Map[String, Any] = {
    val fields = getFieldsL(a.getClass)
    fields.mapValues(getField(a))
  }

}
