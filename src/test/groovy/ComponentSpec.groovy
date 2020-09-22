import spock.lang.Specification

class ComponentSpec extends Specification {
    def "コンポーネントファイルのクラス定義のフォーマットが間違っている場合に投げられる例外のメッセージに、ファイル名が含まれていること"() {
        given:
        String componentName = "component.ts"
        List<String> componentLines = new ArrayList<>([
                "@Component({"
                , "  selector: 'app-component',"
                , "  templateUrl: './component.component.html',"
                , "  styleUrls: ['./component.component.scss'],"
                , "})"
                , "class                           "
        ])
        FileSummary fs = new FileSummary(componentName, componentLines)

        when:
        new Component(new AngularFile(fs, null))

        then:
        Exception e = thrown(Exception)
        e.message.contains("component.ts")
    }

    def "コンポーネントファイルのセレクタ定義のフォーマットが間違っている場合に投げられる例外のメッセージに、ファイル名が含まれていること"() {
        given:
        String componentName = "component.ts"
        List<String> componentLines = new ArrayList<>([
                "@Component({"
                , "  selector: '',"
                , "  templateUrl: './component.component.html',"
                , "  styleUrls: ['./component.component.scss'],"
                , "})"
                , "export class Component implements OnInit {"
        ])
        FileSummary fs = new FileSummary(componentName, componentLines)

        when:
        new Component(new AngularFile(fs, null))

        then:
        Exception e = thrown(Exception)
        e.message.contains("component.ts")
    }

    def "コンポーネントファイルのフォーマットが正しい場合、クラス名とセレクタ名をプロパティに設定すること"() {
        given:
        String componentName = "first.component.ts"
        List<String> componentLines = new ArrayList<>([
                "@Component({"
                , "  selector: 'app-component',"
                , "  templateUrl: './component.component.html',"
                , "  styleUrls: ['./component.component.scss'],"
                , "})"
                , "export class Component implements OnInit {"
        ])
        FileSummary fs = new FileSummary(componentName, componentLines)
        Component cmp = new Component(new AngularFile(fs, null))

        expect:
        cmp.selectorName == "app-component"
        cmp.className == "Component"
    }

    def "toString()の結果が正しいこと"() {
        given:
        String componentName = "first.component.ts"
        List<String> componentLines = new ArrayList<>([
                "@Component({"
                , "  selector: 'app-component',"
                , "  templateUrl: './component.component.html',"
                , "  styleUrls: ['./component.component.scss'],"
                , "})"
                , "export class Component implements OnInit {"
        ])
        FileSummary fs = new FileSummary(componentName, componentLines)
        Component cmp = new Component(new AngularFile(fs, null))

        expect:
        cmp.toString() == "Component(app-component)"
    }

    def "contains()で自身の使っているコンポーネントかどうか判定できること(templateがnullでない場合)"() {
        given:
        String componentName = "test.component.ts"
        List<String> componentLines = new ArrayList<>([
                "@Component({"
                , "  selector: 'app-test',"
                , "  templateUrl: './test.component.html',"
                , "  styleUrls: ['./test.component.scss'],"
                , "})"
                , "export class TestComponent implements OnInit {"
                , "OtherComponentを使って何かする行"
        ])
        FileSummary component = new FileSummary(componentName, componentLines)
        String templateName = "test.component.html"
        List<String> templateLines = new ArrayList<>([
                "<app-other>"
                , "</app-other>"
        ])
        FileSummary template = new FileSummary(templateName, templateLines)
        Component cmp = new Component(new AngularFile(component, template))

        expect:
        (cmp.contains(className, selectorName)) == result

        where:
        className        | selectorName | result
        "OtherComponent" | "app-text"   | true
        "wrong"          | "app-other"  | true
        "wrong"          | "wrong"      | false
    }

    def "contains()で自身の使っているコンポーネントかどうか判定できること(templateがnullの場合)"() {
        given:
        String componentName = "test.component.ts"
        List<String> componentLines = new ArrayList<>([
                "@Component({"
                , "  selector: 'app-test',"
                , "  templateUrl: './test.component.html',"
                , "  styleUrls: ['./test.component.scss'],"
                , "})"
                , "export class TestComponent implements OnInit {"
                , "OtherComponentを使って何かする行"
        ])
        FileSummary component = new FileSummary(componentName, componentLines)
        Component cmp = new Component(new AngularFile(component, null))

        expect:
        (cmp.contains(className, selectorName)) == result

        where:
        className        | selectorName | result
        "OtherComponent" | "app-text"   | true
        "wrong"          | "app-other"  | false
        "wrong"          | "wrong"      | false
    }

}