import java.io.File

/**
 * ユーザーが指定した階層より下にあるコンポーネントファイルとテンプレートファイルのペアを探すクラス
 */
class AngularFileFinder {
    fun findAllAngularFiles(rootDirPath: String): List<AngularFile> {
        val angularFiles: MutableList<AngularFile> = mutableListOf()
        val onEnterFunc = { file: File ->
            findAngularFileUnderDirectory(file)?.let { angularFiles.add(it) }
            true
        }
        File(rootDirPath).walkTopDown().onEnter(onEnterFunc).asSequence().toList()
        return angularFiles
    }

    private fun findAngularFileUnderDirectory(file: File): AngularFile? = file.listFiles()
        ?.filterNotNull()
        ?.filter { it.path.matches(Regex(".+(component|page)\\.(html|ts)")) }
        ?.ifEmpty { null }
        ?.let { createAngularFile(it) }

    private fun createAngularFile(files: List<File>): AngularFile {
        val fileSummaryFactory = { file: File? -> file?.let { FileSummary(file.path, file.readLines()) } }
        val filterFile = { suffix: String -> files.find { it.path.endsWith(suffix) } }
        val tsFileSummary = fileSummaryFactory(filterFile(".ts"))
        val htmlFileSummary = fileSummaryFactory(filterFile(".html"))
        return AngularFile(tsFileSummary as FileSummary, htmlFileSummary)
    }
}

