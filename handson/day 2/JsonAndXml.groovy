//JSON
import groovy.json.*

def s = new JsonSlurper()
def json = s.parse(new File("../../groovy-initial-reference/toBeShared/data/example.json"))
json.details.person.name

//complex
json = s.parse(new File("../../groovy-initial-reference/toBeShared/data/example-o.json"))
json.details.collect{it.person}.collect{it.age}
//spread
json.details*.person*.age
//GPATH
json.details.person.age
//All unique cars
def carSet = json.details*.person*.cars as Set
println carSet.flatten()
println json.details*.person*.cars.toSet().flatten()
//[bmw, ford, honda, toyota, nano, audi]

//all unique cars when age >= 45 
json.details*.person.findAll{it.age>=45}.cars.toSet().flatten()



json = s.parse(new File("../../groovy-initial-reference/toBeShared/data/emp.json"))
json.details.phoneNumbers*.findAll{it.type == 'office'}.number.flatten()