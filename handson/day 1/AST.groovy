//AST

import groovy.transform.*
@Canonical  //toString, hashCode, equals methods
@Sortable //compareTo and compareByField
@AutoClone // clonable

class Person{
    String first
    String second
}

def p1 = new Person(first:"ABC", second:"XYZ")
def p2 = ["SDF","FH"] as Person
def p3 = [first:"SDF",second:"FH"] as Person
def p4 = new Person("ABC", "XYZ")
def p5 = new Person()
def p6 = new Person()
p6.with{
    first = "C"
    second = "D"
}
@groovy.lang.Newify([Person])
def p7 = new Person("ABC", "XYZ")
def p8 = Person.new("ABC","XYZ")
def p9 = p1.clone() //autoclone
def lst = [p1,p2,p3,p4,p5,p6,p7,p8,p9]
//lst*.getFirst()
println lst.first
p1 == p2

p1 > p2

lst.sort(false){p->p.first}  // true=> inplace, false = returns a new copy

//or

lst.sort(false, Person.comparatorByFirst())


///nullsafe

def p = new Person()
println p?.first?.size()
println p == null ? null : (p.first == null)? null : p.first.size()

p=null
name = p? p.first : "no name"
println name
//Elvis operator
name =  p? p : "no name"
name = p?: "no name"
