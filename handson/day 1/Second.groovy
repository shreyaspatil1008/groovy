//https://github.com/ndas1971/GroovyQs
//mailid: ndas1971@gmail.com
//http://www.groovy-lang.org/gdk.html

name = "XYZ"
age = 40
if (name == "XYZ"){
    if(age < 40){
        println "suitable"
    }else if(age > 50){
        println "old"
    }else {
        //println "OK"
    }
}else{
    println "not known"
}

// while
def z = 0
while (z < 3){
    //println z
    z++
}

for(e in 1..10){// 1..10 is called as range
    //println e
}

slc = " Test"
for(e in slc){
    //println e
}


//multiple assignments
(aa,bb) = [1,2]
//println "$aa $bb"

// Closure
def cl = {x -> x*x}
cl(3)

cl = {it * it}
//cl(5)

cl = { -> println("OK")}
//cl()

cl = { a,b,d=10 ->
                cv = a + b + d
                //println(cv)
                cv  // last line is return      
      }
      
z = cl(1,2)


0.1.upto(10){
    //println it
}

0.step(10,2){
    //println it
}

10.times{
    //println it
}

10.5.downto(0){
    //println it
}

 //limit = 100
 def pythogorous(limit){
     count = 0
    1.upto(limit){ a ->
                a.upto(limit){
                    b -> b.upto(limit){
                        c-> justSquare = c*c
                            sumOfNumberSquare  =  a*a + b*b
                           
                            if(justSquare == sumOfNumberSquare){
                                count ++
                                //println "$count) Triplet is: $a, $b, $c"
                         }
                     }
                 }
             }
    //println "Total count: $count" 
     count
 }

num = pythogorous(100)