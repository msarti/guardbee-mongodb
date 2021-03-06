package utils

import com.novus.salat._
import com.novus.salat.json._
import org.joda.time.format.ISODateTimeFormat
import org.joda.time.DateTimeZone


package object context {

  implicit val ctx = new Context {
    
    val name = "json-test-context"
    override val typeHintStrategy = StringTypeHintStrategy(when = TypeHintFrequency.Always,
      typeHint = "_t")
    override val jsonConfig = JSONConfig(
      dateStrategy = StringDateStrategy(dateFormatter = ISODateTimeFormat.dateTime.withZone(DateTimeZone.forID("US/Eastern"))))
  }
}
