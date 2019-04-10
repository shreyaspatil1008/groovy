package examples

class GroovyTests extends GroovyTestCase{
    def per
    void setUp(){
        per = new Person(name:"XYZ")
    }

    void testPerson(){
        assert per.name == "XYZ"
    }

    void testFail(){
        def p = new Person()
        shouldFail{
            p.name.size()
        }
    }
}
