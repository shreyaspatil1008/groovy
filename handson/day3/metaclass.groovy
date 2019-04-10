//metaclass
@groovy.transform.Canonical
class Person{
    String firstName
    String secondName
}

def pmg = new Person("XYZ","ABC")

Person.metaClass.fullName << {-> "$firstName $secondName"}

Person.metaClass.fullName << {String pre -> "$pre $firstName $secondName"}

Person.metaClass{
    greet {-> "Hello ${fullName()}"} 
    //another one
}

Person.metaClass.'static'.whatIsMyType << {-> Person}

def pmg2 = new Person("DFE","LMJ")

println pmg2.fullName()

println pmg2.fullName("Mr. ")
println pmg2.greet()
println pmg2.whatIsMyType()








