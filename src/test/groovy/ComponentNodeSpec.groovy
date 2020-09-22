import spock.lang.Specification

class ComponentNodeSpec extends Specification {
    // 以下のような依存関係があった場合:
    // A(ターゲット)
    // B(コンポーネントでAを使っている)
    // C(テンプレートでAを使っている)
    // D(コンポーネントでBを使っている)
    // E(テンプレートでBを使っている)
    // F(依存関係なし
    // このようなツリーになる
    // A
    // --B
    // ----D
    // ----E
    // --C

    // 単体テストじゃなくなるが、Mock作るのが面倒なので実際のファイルでAngularFileFinderを使ってテスト
    // 別途AngularFileFinderはテストされている前提
    def "依存関係のツリーを正しく作れること"() {
        given:
        AngularFileFinder finder = new AngularFileFinder()
        List<AngularFile> files = finder.findAllAngularFiles("src/test/resources/component-node-spec")
        List<Component> components = new ArrayList<Component>()
        files.forEach(file -> components.add(new Component(file)))
        Component a = components.find(cmp -> cmp.className == "CompAComponent")
        ComponentNode node = new ComponentNode(components, a)

        expect:
        node.children.size() == 2
        ComponentNode nodeB = node.children.find(child -> child.me.className == "CompBComponent")
        nodeB.children.size() == 2
        nodeB.children.any(child -> child.me.className == "CompDComponent")
        nodeB.children.any(child -> child.me.className == "CompEComponent")
        ComponentNode nodeC = node.children.find(child -> child.me.className == "CompCComponent")
        nodeC.children.isEmpty()
    }
}
