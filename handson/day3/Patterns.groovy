//delegate
@groovy.transform.Canonical
class Event{
    String title
    @Delegate Date when //all methods if Date would be proxied to 'when'
}

def ev = new Event("XYZ", Date.parse('yyyy/MM/dd','2014/08/08'))

println ev.title
Date.methods.name
def now = new Date()
println ev.before(now)//true
ev.class.methods.name


//categories
use(groovy.time.TimeCategory){
    println 1.minute.from.now
    println 10.hours.ago
    def n = new Date()
    println (n-3.months)    
}
class Distance {
    def n
    String toString(){"${n}m"}
}

@Category(Number)
class NumberCategory{
    Distance getMeters(){
        new Distance(n:this)
    }
}

use(NumberCategory){
    println 20.meters
}


use(NumberCategory){
    println 20.5.meters
}


class NumberToDouble{
    static Double getDouble(Integer self){
        self.toDouble()        
    }
    
   

}

use(NumberToDouble){
    println 20.double
    
}
