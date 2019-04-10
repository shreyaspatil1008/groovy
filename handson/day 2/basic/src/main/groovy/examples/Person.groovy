package examples

@groovy.transform.Canonical 
class Person{
    String name
    def getAge(){
        20
    }
    def upper(){
        name.toUpperCase()
    }
}