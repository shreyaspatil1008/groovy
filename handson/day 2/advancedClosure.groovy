//delegate = by default owner, a prop search order against delegate
// owner = enclosing closure or enclosing this pointer
// this = this pointer, own instance
// closure.ResolveStrategy = Closure.OWNER_FIRST //default

class Person{
    String name
}


class Thing{
    String name
}

def p = new Person(name:"ABC")
def t = new Thing(name:"XYZ")
name = "TST"
def upper = {->name.toUpperCase()}
upper.resolveStrategy = Closure.DELEGATE_ONLY
upper.delegate = p // closure.ResolveStrategy = Closure.OWNER_FIRST, owner for script is Script class
println upper()

upper.delegate = t
println upper()



