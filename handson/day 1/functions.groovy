def add(x,y=20){
    z = x+y
    return z
    // or z
}

println add(2,3)
println add(4)
println add(y=3, x=9)
println add(7,y=100)

// nested function is not possible
// nested closure is possible

def f(x){
    //def n(p){
    //    retrun x+p
    //}
    def n = {
        p -> x+p
        // x - > x + x   is not possible
    }
    
    
    n(x+10)
}

println (f(5))

def t(Integer... l){
    println(l)
}

t()
t(1)
t(1,3)