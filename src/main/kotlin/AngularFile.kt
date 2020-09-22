import kotlinx.serialization.Serializable

@Serializable
data class AngularFile(val component: FileSummary, val template: FileSummary? = null)