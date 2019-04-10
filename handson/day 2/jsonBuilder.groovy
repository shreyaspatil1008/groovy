import groovy.json.*

def builder = new JsonBuilder()
builder.records{
    car {    
            name "bmw"
            year 1970,1980
        }
   car2 {    
        name "bmw"
        year 1970,1980
    }
    
    car3 {    
        name "bmw"
        year 1970,1980
    }
}
def str = builder.toString()