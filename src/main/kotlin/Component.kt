import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
class Component(private val angularFile: AngularFile) {
    @Serializable
    val selectorName: String

    @Serializable
    val className: String

    @Transient
    //「export class XXXComponent」から「XXXComponent」を抽出する
    private val extractClassNameFromLine = { line: String ->
        line.split(" ").let {
            if (it.size >= 3) {
                it[2]
            } else {
                null
            }
        }
    }

    @Transient
    //「selector: 'app-home'」から「app-home」を抽出する
    private val extractSelectorFromLine = { line: String ->
        val groupValues = Regex("['\"](.+)['\"]").find(line)?.groupValues
        groupValues?.let {
            if (it.size >= 2) {
                it[1]
            } else {
                null
            }
        }
    }

    init {
        val classNameLine = angularFile.component.findLine { line -> line.contains("class") }
        val className = classNameLine?.let(extractClassNameFromLine)
        val selectorNameLine = angularFile.component.findLine { line -> line.contains("selector") }
        val selectorName = selectorNameLine?.let(extractSelectorFromLine)
        //str.contains(other)で判定するとき、otherが""だとtrueになってしまう
        if (className.isNullOrEmpty() || selectorName.isNullOrEmpty()) {
            throw Exception(
                """"
                |ファイルの形式が間違っています
                |コンポーネントファイル:${angularFile.component.fileName}
                |テンプレートファイル:${(angularFile.template?.fileName) ?: "TSファイルにベタ書き"}
            """.trimMargin()
            )
        } else {
            this.className = className
            this.selectorName = selectorName
        }
    }

    fun contains(className: String, selectorName: String): Boolean {
        if (this.className == className && this.selectorName == selectorName) return false

        val hasNameInLine = { line: String ->
            line.contains(className) || line.contains(selectorName)
        }
        return angularFile.component.anyLine(hasNameInLine)
                || (angularFile.template?.anyLine(hasNameInLine)) ?: false
    }

}