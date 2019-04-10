println "Hello!!! World"

a = 1
println a.class

f = 1.2d
println(f.class)
b=true
println b.class
s = "OK"
println s.class

//operations
c = a+1
g = f/2
println "$c $g"

s == "OK"
s == "OK" && a == 1
s == "OK" || a == 1
!(s != "OK")
//convert
"1".toInteger() //toDouble(), toBigDecimal()// from gdk
1.2.toInteger()
//if

if(a >= 1){
    println 'true'
} else{
    println "false"
}