package examples

import spock.lang.Specification

class GroovySpec extends Specification{

    def "Testing mutating of Person"(){
        setup: "Person created"
            def per = new Person(name:"XYZ")

        when:
            per.name = "ABC"
        then:
            per.name == "ABC"
    }

}
