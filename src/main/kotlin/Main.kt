import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun main(args: Array<String>) {
    val components = AngularFileFinder().findAllAngularFiles(args[0]).map { Component(it) }
    val target = args[1].let { target -> components.find { target == it.className || target == it.selectorName } }
    val componentNode = target?.let { ComponentNode(components, target) }
    val result = if (componentNode == null) {
        "名前が間違っていないか確認してください"
    } else {
        Json.encodeToString(componentNode)
    }
    print(result)
}