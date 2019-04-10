class Operator implements Comparable<Operator>{
    def plus(other){
        println("plus")
    }
    def call(Integer... args){
        println("call")    
    }
    int compareTo(other){
        println "compareTo"
        1
    }
}

def o1 = new Operator()
def o2 = new Operator()

o1 + o2
o1(1,2,3,4)
o1 == o2
o1 > o2


//interface is same as java, in groovy we have trait
//abstract class is same as java, static inner class is same as java
//nonststic inner class is completely changed in usage in groovy - better to avoid
// anonymous object is similar
import java.util.concurrent.*
Timer timer = new Timer()
timer.schedule(
    new TimerTask(){
        void run(){
            //println "executed"
        }
    }, 1000
)



//Trait
trait Greet{
    abstract String name()
    def greet(){"hello ${name()}"}
}
class Person{
    String surname
    String firstname
    
}

class PersonG extends Person implements Greet{
    String name(){"${this.firstname} $surname"}
}

def pg = new PersonG(surname:"X", firstname:"Y")
println pg.greet()
//runtime mixing
trait Name{
    String name(){"$firstname $surname"}
}

def pg1 = new Person(surname:"X", firstname: "Y")
def pgg1 = pg1.withTraits Name, Greet
println pgg1.greet()



trait Log{
    String name(){
        println "LOG>>> something"
        def res = super.name()
        println "LOG>>> happened"
        res
    }
}

def pgg2 = pg1.withTraits Name,Log, Greet
pgg2.greet()

//SAM

def pg2 = {-> "Hello"} as Greet
println pg2.greet()
//MAM

def pg3 = [name: {-> "Again hello"},
            greet: {-> " greeting"}] as Greet
pg3.greet()
 

