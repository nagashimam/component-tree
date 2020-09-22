import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
class ComponentNode(
    @Transient
    private val allComponents: List<Component> = ArrayList(),
    val me: Component,
) {
    val children: List<ComponentNode>

    init {
        children = findChildren(allComponents)
    }

    private fun findChildren(allComponents: List<Component>): List<ComponentNode> =
        allComponents
            .filter { it.contains(me.className, me.selectorName) }
            .map { ComponentNode(allComponents, it) }
}