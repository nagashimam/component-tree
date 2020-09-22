import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
class FileSummary(val fileName: String, @Transient val lines: List<String> = ArrayList()) {
    fun anyLine(predicate: (String) -> Boolean): Boolean = lines.any(predicate)
    fun findLine(predicate: (String) -> Boolean): String? = lines.find(predicate)
}