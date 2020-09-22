import spock.lang.Specification

class FileSummarySpec extends Specification {
    def "引数に指定した条件に該当する行があるか真偽値で返す"() {
        expect:
        String fileName = "fileName.txt"
        List<String> lines = new ArrayList<>(["first", "second", "third"])
        FileSummary fileSummary = new FileSummary(fileName, lines)
        fileSummary.anyLine(line -> line.contains(substring)) == result

        where:
        substring | result
        "thir"    | true
        "XXXX"    | false
    }

    def "引数に指定した条件に該当する行を返す"() {
        expect:
        String fileName = "fileName.txt"
        List<String> lines = new ArrayList<>(["first", "second", "third"])
        FileSummary fileSummary = new FileSummary(fileName, lines)
        fileSummary.findLine(line -> line.contains(substring)) == result

        where:
        substring | result
        "thir"    | "third"
        "XXXX"    | null
    }
}
