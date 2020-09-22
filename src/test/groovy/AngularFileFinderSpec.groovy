import spock.lang.Specification

class AngularFileFinderSpec extends Specification {
    def "指定ディレクトリ以下のコンポーネントファイルとテンプレートファイルのペア(AngularFile)をリストとしてとして返すこと"() {
        given:
        AngularFileFinder finder = new AngularFileFinder()
        List<AngularFile> angularFiles = finder.findAllAngularFiles("src/test/resources/angular-file-finder-spec")

        expect:
        angularFiles.size() == 3

        AngularFile topAngularFile = angularFiles.get(0)
        topAngularFile.component.fileName == "src/test/resources/angular-file-finder-spec/top.component.ts"
        topAngularFile.component.lines[0] == "1階層目のテストコンポーネント1行目"
        topAngularFile.component.lines[1] == "1階層目のテストコンポーネント2行目"
        topAngularFile.template.fileName == "src/test/resources/angular-file-finder-spec/top.component.html"
        topAngularFile.template.lines[0] == "1階層目のテストテンプレート1行目"
        topAngularFile.template.lines[1] == "1階層目のテストテンプレート2行目"

        AngularFile middleAngularFile = angularFiles.get(1)
        middleAngularFile.component.fileName == "src/test/resources/angular-file-finder-spec/other-files/middle.component.ts"
        middleAngularFile.component.lines[0] == "2階層目のテストコンポーネント1行目"
        middleAngularFile.component.lines[1] == "2階層目のテストコンポーネント2行目"
        middleAngularFile.template.fileName == "src/test/resources/angular-file-finder-spec/other-files/middle.component.html"
        middleAngularFile.template.lines[0] == "2階層目のテストテンプレート1行目"
        middleAngularFile.template.lines[1] == "2階層目のテストテンプレート2行目"

        AngularFile bottomAngularFile = angularFiles.get(2)
        bottomAngularFile.component.fileName == "src/test/resources/angular-file-finder-spec/other-files/even-more-files/bottom.component.ts"
        bottomAngularFile.component.lines[0] == "これはコンポーネントだけ"
        bottomAngularFile.template == null
    }
}
