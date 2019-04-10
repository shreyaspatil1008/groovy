//currying
def fc(x,y,z){
    return x-y
}

def fcc = this.&fc

fcc(10,20,30)

def fcc_x = fcc.curry(10) //starts from left and finalizes the arg value as default
fcc_x(20,30)

def fcc_z = fcc.rcurry(30) //starts from right and finalizes the arg value as default
fcc_z(10,20)



//compose

//f(g(x)) , g(f(x))

def plus2 = {it+2}
def times3 = {it*3}

def comp1  =plus2 << times3
def comp2 = times3 << plus2
println "comp1=${comp1(10)}, comp2=${comp2(10)}"