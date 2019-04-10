//AST
@groovy.lang.Singleton
class Person{
    static String first
    static String second
    
    static def init(f,s){
       this.first = f
       this.second = s 
    }
}
Person.init("D","E")
def one = Person.instance
def two = Person.instance
one.is(two)