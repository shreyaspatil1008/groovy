"""
Day-1
    Starting Groovy 
    Difference between Java and Groovy
    gradle as build tool 
    Groovy operators
    Groovy Imperative 
    Groovy  String, Collections
    Groovy Object Oriented and AST transformation
"""

///**** Executing groovy file 
$ groovyConsole 

// groovy file
$ groovy hello.groovy
//hello.groovy
println("${args[0]}")  //command line handling

//script can contain many classes, import etc(not package) 
//by class name and filename should not be same 

//or
//Test.groovy
package support 
class Test {
  public static void main(String[] args) {
     println("${args[0]}")
  }
}
//OR 
//Test.groovy
package support 
class Test2 {
  static def main(args){
     println("${args[0]}")
  }
}
//compile with groovyc 
$ groovyc -d ./classes Test.groovy
$ java -cp "./classes;%GROOVY_HOME%/lib/*"   support.Test 123

///*Using Grape in scripts 
//hello.groovy 
@Grapes([ 
 @Grab('org.apache.httpcomponents:httpclient:4.2.1')
 ])
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.client.methods.HttpGet

def httpClient = new DefaultHttpClient()
def url = 'http://www.google.com/search?q=Groovy'
def httpGet = new HttpGet(url)
def httpResponse = httpClient.execute(httpGet)
new File('result.html').text =   httpResponse.entity.content.text
println  httpResponse.allHeaders.name 

//execute 
$ groovy hello.groovy




///*** Groovy Quick Intro 


///* Groovy Default Imports 
//Note Groovy automatically imports below
java.io.*
java.lang.*
java.math.BigDecimal
java.math.BigInteger
java.net.*
java.util.*
groovy.lang.*
groovy.util.*


///* Groovy Extension methods - GDK 
//DefaultGroovyMethods  - http://www.groovy-lang.org/gdk.html
//This class defines new groovy methods which appear on normal JDK classes inside the Groovy environment. 

//Static methods are used with the first parameter being the destination class,
//public static String reverse(String self) //provides a reverse() method for String. 
groovy> groovy.ui.Console.consoleControllers[0].@maxOutputChars=1000000 
groovy> import org.codehaus.groovy.runtime.DefaultGroovyMethods 
groovy>  DefaultGroovyMethods.methods.each{println(it)} 


//In  Groovy, there is no need to access getter/setter by java way
//use 
a.text       // where object a has method getText()
a.text = ..  // where object a has method setText(..)  

//Note any getXYZ() without any arg can be coverted to a.xYZ 
//If there is any arg to getMethod(arg), then Use Java way 



//Note that do..while is not supported in Groovy
//Else all java code can be written inside groovy as it is

//Groovy is dynamic types, hence Generics does not have any meaning 


///* Accessing property/field and method with empty argument
//Parenthesis is must for no arg method 
obj.a    // a is property/public field, or getA()
obj.a()  // a is method

//For any arg method, Parenthesis is optional 
println("OK")
println "OK"
obj.b 1,2,3 
obj.b(1,2,3)

///* Printing and interpolation 
def a = "OK"
println "$a has double length= ${a.size() * 2}"


///* no primitive 
int i               //primitives are initialized to zero , but converted to reference type 
Double k            //reference are initialized to null. 
def j = 20.2        //by default Bigdecimal 
def j = 20.2 as Double //now double 
def s = "OK" //String 
println "${i.class}, ${j.class} ${k.class} ${s.class}"
//get/set 
println "${i.class} == ${i.getClass()}"

//Note all below are equal interms of type 
x = 1
def y = 1
int z = 1
Integer a = 1
[x.class, y.class, z.class, a.class]



///* Number type suffixes
//Type			Suffix
BigInteger		G or g
Long			L or l
Integer			I or i or no suffix
BigDecimal		no suffix
Double			D or d
Float			F or f

///* if else 
//Anything nonempty is true , else false (= 0,"", [],[:] null)
def x = 0
x as Boolean 

if(!x) {
  println "OK"
}

///* Space ship operator <=> , returns -1, 0, +1
1 <=> 2  //-1 

//All comparison operators are null-safe in groovy, 
//and null is always less than any non-null value

//content checking: == , uses a.compareTo(b) at first (when implements Comparable<T>)
//if not available, then a.equals(b) 
//identity checking : 'is'  (in java == for object), Note .is(arg) is a method, not operator 
new Integer(1) ==  new Long(1)  //type conversion
new Integer(1).is new Long(1)

//boolean operator && , ||, !
//Shorts circuit 


///HandsOn 
define two variables , name and age 
if name is "XYZ" and age is below 40, 
print "suitable" 
else if age is greater than 50, print "old", 
else print "OK"
For all other names, print "not known" 



///* for/while  loop (like java), no do..while loop 
def c = 0 
while(c < 3) {
    println c 
    c++
}
//OR 
def slc = "Hello World"
for(e in slc){
    println(e)
}
//OR 
for(e in 1..10){
    println(e)
}
//Java way 
def lc = [1,2,3] 
for(def i = 0; i< 3; i++){
    println lc[i]
}
//OR 
for(def e: lc){
    println(e)
}

///* Few operations on Numeric 
//BigInteger and BigDecimal supports +, -, *, ** etc via GDK
//BigDecimal / with Double, float => Double , / with Integer => BigDecimal 
// BigDecimal / BigDecimal => BigDecimal with certain percision
//for extra precision, Use BigDecimal.divide(BigDecimal, new java.math.MathContext(precision)) 
//** for power , Result can be Integer or Long or Double 
//for Integer , bitwise and, or, xor are  - &, |, ^ 
//Integer has .intdiv(Integer) to get int division else int/int => BigDecimal, int/double => Double

def  x = "7"  as Integer
def y = 22 as BigDecimal 
z = y/x 
[z, z.class]  //Result: [3.1428571429, class java.math.BigDecimal]

def  x = "7"  as Integer
def y = 22 as BigDecimal 
z = y.divide(x)       //BigDecimal division, Error 

z = y.divide(x, new java.math.MathContext(10)) //10 digits 
[z, z.class]
[3.142857143, class java.math.BigDecimal]

def  x = "7"  as Integer 
def y = 22  
z = y.intdiv(x) 
[z, z.class]   //Result: [3, class java.lang.Integer]

///* To convert from one Numeric, String to another Numeric, use 
//toBigDecimal()   toBigInteger() toDouble()  toFloat()  toInteger()  toLong() 


///*Multiple assignments, swapping
def (aa,ba) = [1,2]
println("$aa $ba")
(ba,aa) = [aa,ba] 
println("$aa $ba")

///* GPATH expression 
a.b.c 
    for POJO((Plain Old Java Objects)s, yields all c properties for all the b properties of a 
    (sort of like a.getB().getC() in JavaBeans). 
    Note b, c  are properties only (not methods, get* is OK ) 
    and return could be a array if  any property yields a array even in between(flattened)
    If b,c are methods, use Spread Operators 
    a.b()*.c()
//Example 
String.methods.name
String.methods.name*.size() //[2,3,..]
String.methods.name.size() //76



        
///**** Groovy - Closure and Function 

///* closure 
def fxc = {int xi -> println xi }
fxc = { println it }   //same as above 

fxc = { -> println "NoArgs"} // no Arg 

//no overloading of closure as variable 'fxc' gets overritten 
//can have multiline , don't use {} as it's closure syntax 
//last line is return 
fxc = { def xi1 -> 
            println "def" 
            xi1*2          
       }
fxc(2)
fxc(2.0)

//with default 
def f = {  x, y=2 -> x+y }
f(2)
f(2,3)


///* Number functional 
//Check GDK -  http://www.groovy-lang.org/gdk.html

0.1.upto( 10 ) {  //inclusive , BigDecimal, Integer, step = 1
  println it
}

0.step( 10, 2 ) { //only for BigDecimal Integer 
  println it
}

10.times {  //for for BigDecimal, Integer
  println it
}
10.5.downto(0) { // inclusive , BigDecimal, Integer, step =1 
  println it
}

///Hands on -Print Pythogorous triplets below 100 


def res = [] as Set
def limit = 99
3.upto(limit){ x ->
  x.upto(limit){ y ->
    y.upto(limit){ z ->
       if ( z*z == (x*x + y*y) )
           res << [z,y,x]
           }
        }
    }
res.size()




///* Function 
//last line is return , or use 'return' explicitly 
def  fun( x, y=20) {
  def z = x * y
  z
}

//Various way arg passing 
println fun(y=20, x=10)
println fun(10) 
println fun(10, y=30)  

///Hands On - convert the above Pythogrous triplets into two arg function 


//Nested funtion not possible, but nested closure possible 
//ERROR
def fun(x){
    def z(y){
        x+y 
    }
    z
} 
//OK 
def fun(x){
    def z= { y -> x+y}  //note scope of y is of fun , hence var name must be unique
    z
} 
fun(2)(3)


//first arg is Map 
def fun1(m) {
  m.x * 2
}
println fun1(x:2)
println fun1( *:[x:2])

//Scope - LEGB 
def x = 23 
def println = 3 
println(x) //G , hence Error 

def fun(y){
    println z+y 
}
fun(2) //Error 
//OK 
z=20
fun(2) //22


///* Function - Overloading OK as long as type differs
//Note def is Object 
def fx(int x){  //1
  println 'fx(int x)'
}

def fx( def y){  //2
  println 'fx( def y)'
  }
  
def fx( def x, def y){  //3
    println "fx( def x, def y)"
} 

def fx( Integer... x){   //4
    println "Int ${x?.size()}"
} 
   
def fx( Object... x){   //5
    println "Obj ${x?.size()}"
} 
   
fx(2)  //1
fx("OK")   //2
fx(2,3) //3
fx() //5 
fx(1,"OK",3.0)  //5 
fx(1,2,3) //4
fx(*[1,2,3]) //4 

 



///*** Groovy - String and Character 
'a single quoted string'
"a double quoted string"

'''line one
line two
line three'''

assert 'ab' == 'a' + 'b'  //concatenation

//\n, \t .. etc are allowed as escaping characters

//Slashy string  /..../  , no need to escape backslashes, use with regex
//escape only forward slashes, can be multiline and can be GString via ${} interpolation
//An empty slashy string is not //  , it is comment
"\\w+"
/\w+/

//Dollar slashy string    $/..../$   , like /.../ but escape character is $, 
//can not have GString
$/\w+/$

///* Characters 
//Must use explicit type char or as or (char) casting because '..' is String in Groovy
char c1 = 'A' 
assert c1 instanceof Character

def c2 = 'B' as char 
assert c2 instanceof Character

def c3 = (char)'C' 
assert c3 instanceof Character

Character.methods.name


///* String is CharSequence, hence all Collection methods can be used on each Char 
//http://www.groovy-lang.org/gdk.html

def sx = "Groovy"

sx.size()  // size() or length() are same
sx[0]
"o" in sx.toList() //for only char , 'in 'with String not possible , use contains(str)
sx.contains("oo")  //checking

//iteration
for(ch in sx){
   println ch 
}
for(def ch : sx){
   println ch 
}
sx.each { ch -> println ch }

//String is immutable 
def sx1 = sx << " Grrovy" //Appends, converts to new String

"H" * 4  //repeat
"H" + "L" //concatenation

sx[0..2] //inclusive 
sx[-2..<-5] //exclusive 
sx[-1]
sx[-1..-sx.size()]  // reverse ===> nhoj  ,

//All Java methods, eg trim, startsWith, endsWith(suffix) , startsWith(prefix)
//split(deliminator),  length(), isEmpty(), trim(), toLowerCase(), 
//And many GDK methods on String and CharSequence 
sx.toUpperCase()
sx.trim()
sx.startsWith("G")   //===> true
//convert to List of chars 
sx as List 
sx.toList()
sx.split(/\s+/)  //split takes Regex 

//Conversion to number , toDouble(), toInteger()
try{
    "123.k".toDouble()
} catch(ex){
    println(ex)
}

//command execution 
def x = "ls -al".execute()  //executing command
println(x.text)  //x.err or x.in or x.out are err or in or out
def lines = x.text.split(/\n/)


///HandsOn - can you download "http://www.google.com"
//Check GDK methods of URL and String 

def x = "http://www.google.com"
x.toURL().text

///HandsOn  - encode/decode base 64 

def s = 'Hello Groovy '
String encoded = s.bytes.encodeBase64().toString()
byte[] decoded = encoded.decodeBase64()
assert s == new String(decoded)


///HandsOn =given string 
def str =  "Hello Groovy Hello"
//Print frquency of alphabets 



str.each { ch ->
    count = 0
    str.each { ch1 -> 
     if (ch == ch1) 
         count++         
         }
     println "$ch $count"
     
   }


     

///*** Groovy - Regex 
//Note =~, ==~ can take String as regex or java.util.regex.Pattern
// str=~pattern returns Matcher and str==~pattern returns bool if full String contains the Pattern 

def str = "Groovy Groovy"

if (str ==~ /\w+\s+\w+/){ //string must fully match the pattern 
    println "found"
}

//FInding all pattern 
str.findAll(/\w+/) //returns list of all found match, note grouping () does not work 
//Spliting 
str.split( /\s+/)   //Returns list, String is regex pattern, dont pass java.util.regex.Pattern

//Matcher Object 
def match = ( str =~ /(\w+)/)   //returns Matcher 
match[0] //[fullmatch, group1, group2,...] 
match[1][1]  //2nd match, group1

//Iteration of matcher 
match.each { m -> println m[1] }  //for any number of groups 
match.each { m, g -> println g }  // fullmatch, group1, group2, ...
//OR Use eachMatch of the String 
//if no group 
str.eachMatch(/\w+/) {m ->
  println m
}
//If groups , then m is array ie [fullmatch, group1, group2,...] 
str.eachMatch(/(\w+)/) {m ->
  println m
}
//in this case, destructure as m, g1,g2,...
res.eachMatch(/(\w+)/) {m,g ->
  println g
}

//replaceFirst, replaceAll 
str.replaceAll(/(\w+)/, /$1s/)
//If Pattern does not contain group, m is found String 
str.replaceAll(/\w+/){ m -> m.toUpperCase() }
//If pattern contains group, then m is array ie [fullmatch, group1, group2,...] 
def str = "Name Groovy"
str.replaceAll(/Name\s+(\w+)/){ m -> println "${m.class}:$m"}   //class java.util.ArrayList:[Name Groovy, Groovy]
//in this case, destructure as m, g 
str.replaceAll(/Name\s+(\w+)/){ m,g -> println "$m,$g"} //Name Groovy,Groovy


//Note ~/ / , converts a string to Pattern 
def pat = ~/(\s+)/
pat.class // java.util.regex.Pattern
pat.split("A B C")



   


///*** Groovy - StringBuffer 
//StringBuffer is mutable
def x = new StringBuffer()

x.append("OK")
x.append(" I am ")

x[0]        //K
x[0..<1] = 'K' as char   //KK I am 


///*** Groovy - List 
def le = []
println le.empty  // or le.isEmpty()

def l = [1,2,3, *[1,2,3]] //spreading 

//heterogeneous and mutable 
def ll = [1,2,"OK", [1,2,3]]
ll[-1][-1] = 30
println ll //[1,2,"OK", [1,2,30]]

//List operations 
le << 2 << 3  //append element 

ll + [2,3,4]  // append list , immutable , RHS can be Iterable, Collection , List, Object 
ll.plus(1, ll) //1st arg, index 

ll += [2,3,4] 
ll -= [2]  //all 2 removed 
println(ll)
ll.remove(0) //index based remove 
println(ll)

ll.clone() //shallow copy , deepCopy,- convert to json and then read it back 

"OK" in ll   //member existance 
le == [2,3]  //content checking 
le.is([2,3]) //reference checking 
//le < [30,40] - no comparison 
println ll.size()
println ll[0]

//advanced slicing 
println ll[0..2]
ll[0..<2] = ["changed", 2] //delete 0,1 and replace by RHS 
println ll    //[changed, 2, OK, [1, 2, 3]]
ll[0..<0] = ["changed", 2] //prepend 
ll[ll.size()..<ll.size()] = ["changed", 2] //append 
ll[2..<2] = ["changed", 2] //insert at i 


//beyond size
ll[25] == null //index returns null
ll[10] = 200   ///intermidiate values are null

//careful , reference copying , use clone()
def ll = [1,2,3]
def lg = [ll,ll]
println(lg)
lg[-1][0] = 200  //change lg
println(ll)     //ll is changed 


//Iterating on a list
def list= ['a',2,'c',4]

for (def ele: list) {  //def is must 
   println("Item: $ele")
}

//Or
list.each {
    println "Item: $it" // `it` is an implicit parameter corresponding to the current element
}
for (e in list) {   //using in
    println e
}

///HandsOn  - Given [1,2,3], do [1,4,9]




///*** Groovy - Range 
///Range - list in disguise
def r1 = 0..15 
def r2 = 0..<15 
println r1.size()
println r2.size()
//all list methods can be used
12 in r1 

def ri = 0..15
def rc = 'a'..<'l'

println ri.toList()
println rc.toList()
'b' in rc 
ri.each { e ->  }


///*** Groovy - Array
//Java Arrays - by 'as T[]', homogeneous contents, does not grow/shrink, fixed size, 
//all List methods can be used except sort

String[] arrStr = ['Ananas', 'Banana', 'Kiwi']  
assert arrStr instanceof String[] 

//or with initial size
def p = new Integer[10]
p[0] = 2  //[2, null, null, null, null, null, null, null, null, null]

   
//Create using type cast 
def numArr = [1, 2, 3] as int[]      
assert numArr.size() == 3
numArr[0] = 200
//numArr[10] = 6      		// Does not grow/shrink. fixed size , java.lang.ArrayIndexOutOfBoundsException
//numArr[0..<0]  = [2,3]   // Can not grow/shrink
println(numArr[0..1])     //OK
// << not possible on Array as Array does not grow

//Conversion to List by ' as List'
def numArr = [1, 2, 3] as int[]   
assert (numArr as List) instanceof List

//Multidimensional by as [][] 
def matrix3 = new Integer[3][3]       
assert matrix3.size() == 3

Integer[][] matrix2                    
matrix2 = [[1, 2], [3, 4]]
assert matrix2 instanceof Integer[][]

def matrix1 = [[1, 2], [3, 4]] as Integer[][]
assert matrix1.size() == 2





///*** Groovy - Set 
//unordered non duplicates 

def se = [1,2,3,4,1,2,3,4] as Set //as SortedSet  for SortedSet 
se = [1,2,3,4,1,2,3,4].toSet()

se << 100            //add 
se -= [100] as Set  //removing 

se[0]                       // Ok, but unordered, hence no meaning, 
//se[0..1]                  //ERROR
//se[0]  = 200              //ERROR

se +  ([100,200,1] as Set)                    //Union , returns new Set
se.intersect( [100,200,1] as Set)      // Intersect , [1]
se.disjoint( [100,200,1] as Set)        //false
se - [1,2,3] as Set                    //Set difference [4,5]
//All list methods  can be used 

//Returns true if the two sets have the same size, and every member of the specified set is contained in this set 
Set s1 = ["a", 2]
def s2 = [2, 'a'] as Set
Set s3 = [3, 'a']
def s4 = [2.0, 'a'] as Set
def s5 = [2L, 'a'] as Set
assert s1.equals(s2)
assert !s1.equals(s3)
assert s1.equals(s4)
assert s1.equals(s5)



///HandON: how many methods are extra in SortedSet compared to Set 
(SortedSet.methods.name as Set) - (Set.methods.name as Set)


///Handson: frequency of words using list methods , remove duplicates 
//note split returns Array, hence convert to List 
def str =  "Hello Groovy Hello"

str.split(/\s+/).toList().toSet().each { wd ->     
      println "$wd ${str.split(/\s+/).count{wd2 -> wd == wd2}}"  
}


///*** Groovy - Queue 
def qu = [1,2,3] as Queue //actually DeQueue
qu.addFirst(20)
qu.addLast(30)
qu.removeFirst()
qu.removeLast()


///*** Groovy - Stack 
def st = [1,2,3] as Stack 
st.push(20)
st.pop()





///*** Groovy - Map
def me = [:]
println me.isEmpty()  //checking empty 

me.entrySet()
me.keySet()   //keys 
me.values()  //values

def mll = ['ok': 1, nok: 2, 3:4]  //by default, Key is string if not Numeric or specify by "key", 

me << [ok: 2] //append element 
mll + [new:2]  // append another Map, immutable  
mll += [new: 30] 
mll -= [new:30]  // new removed 
println(mll)

mll.clone() //shallow copy //for deepcopy converts to json and read it back 

"ok" in mll 
me == [ok:2] //content checking

println mll.size()
println mll['nok']
println mll.nok   //only for string key 

mll[10] = 200   //ading new key 

println mll

//non existing key 
mll[25] == null //index returns null
mll.get(100) //get also returns null 
//or return default value 
mll.get(100, "default")

//Spread list elements by *list
def items = [4,5]                      
def list = [1,2,3,*items,6]            
assert list == [1,2,3,4,5,6]           
//Spread map elements, by *:map
def m1 = [c:3, d:4]                   
def map = [a:1, b:2, *:m1]            
assert map == [a:1, b:2, c:3, d:4]   

//iteration 
for(kv in mll) { //kv is EntrySet .key, .value
  println kv.key
}

//either take Entry(use .key, .value) or two args k,v 
mll.each {kv -> println(kv.key) }
mll.each {k,v -> println(v) }




///*** Groovy - List, Set, Map - Synchronized 
lst.asSynchronized()
set.asSynchronized()
map.asSynchronized()

///*** Groovy - List, Set, Map - Immutable 
lst.asImmutable()
set.asImmutable()
map.asImmutable()


        
///**** Groovy - switch 
// switch(a) {case b: } means b.isCase(a) 
//So class b can customize meaning of isCase 
//for example, for list.isCase(a) means "Does a in list", same for Range ie range.isCase() means "is a in rnage"
//For Map, map.isCase(a) means "Does a in Map's key"
//For Class, class.isCase(a) means " Is a instanceof class"
//For Regex , regex.isCase(a) means " a ==~ regex"
//For Closure , closure.isCase(a) means " is return of closure(a) a true"


def values= [
    'abc': 'abc',
    'xyz': 'list',
       18: 'range',
       31: BigInteger,
       40: Integer,
       50.5d:Double ,
  'dream': 'something beginning with dr',
     1.23: 'none',]


def sres = values.each{
  def result  
  switch( it.key ){               //if switched expression matches case-expression, execute all
                                //statements until 'break'

    case 'abc':                 // object equal to ,  it.key == 'abc'
      result= 'abc'
      break

    case [4, 5, 6, 'xyz']:        // list membership  it.key in [4,5,6, 'xyz'] or [4,5,6, 'xyz'].isCase(it.key)
      result= 'list'
      break
      
    case [foo:true, bar:false]:  //Map membership,  it.key in [foo:true, bar:false]
     result= 'map'                
     break

    case 'xyz':                 //this case is never chosen because 'xyz' is matched by
                                //previous case, then 'break' executed
      result= 'xyz'
      break

    case 12..30:                // range membership,   it.key in 12..30
      result= 'range'
      break

    case Integer:                // instanceOf ,   it.key instanceOf Integer

      result= Integer             //because this case doesn't have a 'break', result
                                  //overwritten by BigInteger in next line

    case BigInteger:            // instanceof ,  it.key instanceof BigInteger
      result= BigInteger
      break

    case ~/dr.*/:                // RegEx checking  it.key ==~  ~/dr.*/
      result= 'something beginning with dr'
      break

    case { it instanceof Double && it>30.0}:     //use Closure, returns true for it's case,  key is accessed by 'it'
      result= 'result is > 30'                  
      break

    default:                    // for 1.23 case
      result= 'none'
  }
  
  println result

}

println sres

  
  
  
///**** Groovy Object Oriented and AST transformation
//don't use field (instance variable with access control) as recomended as Groovy really does not follow access modifier  
//private/protected fields/methods do not restrict access from public Groovy user from same/different package
//Only subclass can not access private super fields/methods, subclass can access protected fields/methods

//Note - instance variable without access control(ie public) - property 

class Person implements Serializable{
  private String first
  String last
  
}

def x = new Person(first:"D", last:"N")
x.first //can access 
def xd = new Person() //default

//Note by default only default and maplike ctor is generated(and tuple ctor with TupleConstructor)
//Any user defined ctor would remove those including TupleConstructor 


//AST does not take field into account 
//(for that explicitly mention includeFields=True), Many such options are there 
//http://groovy-lang.org/metaprogramming.html#_available_ast_transformations

@groovy.transform.Canonical     //toString, hash, equals, tuple constructor 
@groovy.transform.Sortable       //compareTo and compatorByFIELD based on Order 
@groovy.transform.AutoClone
class Person implements Serializable{
    String firstName          //property, auto get*()/set*() generated (not for field)
    String lastName           //property
}


def cl = new Person('Ada1','Lovelace1')            //tuple constructor
Person lt = ['Ada2','Lovelace2']                        // list constructors, order matters 
def mp = [firstName:'Ada3', lastName:'Lovelace3'] as Person     // map constructor,order does not matter 
def mpLike = new Person (firstName:'Ada', lastName:'Lovelace') //map like constructor, order does not matter 

println mpLike

def de  = new Person()                                     //default constructor
//inside 'with', this = de 
de.with {        
       setFirstName("Das")       
       lastName = "Das" 
       }              

println(de)    
//shallow copy
def de1 = de.clone()
println (de1.is(de))
println(de1 == de)

//few properties changed 
def y = x.clone().with{
  last ="ND"
  delegate   //return delegate 
}
//Easy construction 
@groovy.lang.Newify([Person])  //[class1, class2, ...]
def pl = [ Person("N","D"), Person("M","E")] //python style 
def pl = [ Person.new("N","D"), Person.new("M","E")] //ruby style 


//Sort 
def lp = [ cl, lt, mp, mpLike, de] 
println( lp[0] > lp[1])  //from Sortable 
lp.sort() //inplace 
lp.sort(false){ p -> p.first } //sortBy
println( lp.sort(false).reverse()*.lastName)
println( lp.sort(false).reverse().lastName) //GPATH

println(lp.sort(false, Person.comparatorByFirstName())*.toString())
Person.methods.name

///* spread operator 
def i = 0
i.class.methods*.name 
//equivalent, GPATH 
i.class.methods.name 

///* null safe operator 
class Person{
  String name 
  }
  
def p = new Person()
println (p?.name?.size())

def p = new Person(name:"Das") //by default , default and map ctor

p.name?.size()

///Elvis operator 
p.name != null ? p.name : "default name"
//or 
p.name ?: "default name"

///Above all are dynamic code , 
//But with below annotation, code generated is equivalent to java 
@groovy.transform.TypeChecked
    1. type inference is activated only for local variables and return of closure 
       Field, methods args and returns must be static typed 
    2. method calls are resolved at compile time
    3. all the compile time errors are shown 
    4. But methods are selected based on the inferred types of the arguments, not on the declared types
       Exactly like dynamic groovy 
        //In java 
        public Integer compute(String str) {
            return str.length();
        }
        public String compute(Object o) {
            return "Nope";
        }
        // ...
        Object string = "Some string";          
        Object result = compute(string);        
        System.out.println(result); //Nope 
        //in groovy 
        int compute(String string) { string.length() }
        String compute(Object o) { "Nope" }
        Object o = 'string'
        def result = compute(o)
        println result  //6 
    5.  But if runtime metaprogramming is used , this annotation would not be to catch 
        and program might fail at runtime 
        class Computer {
            int compute(String str) {
                str.length()
            }
            String compute(int x) {
                String.valueOf(x)
            }
        }

        @groovy.transform.TypeChecked
        void test() {
            def computer = new Computer()
            computer.with {
                assert compute(compute('foobar')) =='6'
            }
        }
        //monkey patching or runtime metaprogramming 
        Computer.metaClass.compute = { String str -> new Date() }
        test() //compilation successful but fails at runtime as runtime called metaClass version
    
@groovy.transform.CompileStatic
    1. All of the above 
    2. as well as immune to Monkey patching 
    4. statically compilation like Java , hence performance improvement (more visible for CPU bound code (close to java))
    Note: Performace of invokedynamic(JVM support ie in java bytecode) version of Groovy(Groovy 2 with >=JDK8) 
          is very close to statically compilation(using groovy-x-y-z-indy.jar)
          Use groovy\indy\* jar for this , by default non indy jars are used 
          and use groovy --indy foo.groovy  (--indy flag)
          OR in gradle 
            compileJava {
                sourceCompatibility = 1.7
                targetCompatibility = 1.7
            }             
            compileGroovy {
                groovyOptions.optimizationOptions.indy = true
            }             
            dependencies {
                compile 'org.codehaus.groovy:groovy-all:2.4.11:indy'
            }
            //OR in code 
            def compilerConfig = new CompilerConfiguration()
            compilerConfig.optimizationOptions.indy = true
             
            def shell = new GroovyShell(compilerConfig)


    
    
    

///*** Groovy - AST configuration 
//http://groovy-lang.org/metaprogramming.html#_available_ast_transformations
//Many AST has arguments/options 


@groovy.transform.ToString 
    creates 'String toString()' method of an class
    By default stringify only properties and does not include Super
    Options could be 
    includeNames, excludes, includes, includeSuper, includeFields, ignoreNulls	, 
    includeSuperProperties, includePackage	
@groovy.transform.EqualsAndHashCode 
    generates 'Boolean equals(Object o)' and 'int hashCode()' methods  
    After that can use == methods for comparing two objects
    By default includes properties and does not call Super
    Options could be 
    excludes, includes, callSuper, includeFields	
    //Examples:
        @EqualsAndHashCode(excludes=['firstName'])
        @EqualsAndHashCode(includes=['lastName'])
        @EqualsAndHashCode(callSuper=true)   //Whether to include super in equals and hashCode calculations
@groovy.transform.TupleConstructor  
    Creates tuple constructor . 
    Generates combinations of constructors, Any user defined constructor would removed these generated ones 
    By default includes properties
    Options could be 
    excludes, includes, includeProperties, includeFields, includeSuperFields, includeSuperProperties, force
    //Example
        @TupleConstructor(excludes=['lastName'])
        @TupleConstructor(includes=['firstName'])
        @TupleConstructor(includeFields=true)  
        @TupleConstructor(includeProperties=false)  
        @TupleConstructor(includeSuperFields=true)  
        @TupleConstructor(includeSuperProperties=true)
        @TupleConstructor(includeSuperProperties = true, callSuper=true)  //Should super properties be called within a call to the parent constructor rather than set as properties
        @TupleConstructor(force=true)  //By default, if class has any user defined constructor, no tuple constructor is generated. it inverses this default
@groovy.transform.Canonical
    The @Canonical meta-annotation combines 
    the @ToString, @EqualsAndHashCode and @TupleConstructor annotations
@groovy.transform.Immutable
    Similar to @Canonical and  all properties are made Immutable (ie properties are final)
    and all properties must be  well known Immutable object (builtin or userdefined with @ImmutableOptions)
@groovy.transform.ImmutableOptions
    Groovy’s immutability support relies on a predefined list of known immutable classes (like java.net.URI or java.lang.String)
    and fails if you use a type which is not in that list.
    Use @ImmutableOptions to specify a new type 
    //Example 
    import groovy.transform.Immutable
    import groovy.transform.TupleConstructor
    @TupleConstructor
    final class Point {
        final int x
        final int y
        public String toString() { "($x,$y)" }
    }
    @Immutable(knownImmutableClasses=[Point])
    class Triangle {
        Point a,b,c
    }
@groovy.transform.Memoized
    Creates a memoized version of a function 
    //Example 
    @Memoized
    long longComputation(int seed) {
        // slow computation
        Thread.sleep(100*seed)
        System.nanoTime()
    }
    def x = longComputation(1) // returns after 100 milliseconds
    def y = longComputation(1) // returns immediatly
    def z = longComputation(2) // returns after 200 milliseconds
@groovy.transform.TailRecursive
    Creates a TailRecursive function 
    //Example 
    @groovy.transform.TailRecursive
    def mysum( List lst, BigInteger acc =0G){
        if(lst.empty)
            return acc 
        else 
            return mysum(lst.tail(), acc + lst.head())
    }
    def lst = (0..10000).toList 
    mysum(lst)    
@groovy.transform.IndexedProperty
    Generates indexed getters/setters for properties of list/array types.
    //Example 
    class SomeBean {
        @IndexedProperty String[] someArray = new String[2]
        @IndexedProperty List someList = []
    }
    def bean = new SomeBean()
    bean.setSomeArray(0, 'value')
    bean.setSomeList(0, 123)
    assert bean.someArray[0] == 'value'
    assert bean.someList == [123]
@groovy.lang.Lazy
    The @Lazy AST transformation implements lazy initialization of fields. 
    //Example 
    class SomeBean {
        @Lazy LinkedList myField  //= { ['a','b','c']}() , if initialization is required 
    }
    // will produce the following code:
    List $myField
    List getMyField() {
        if ($myField!=null) { return $myField }
        else {
            $myField = new LinkedList() //$myField = { ['a','b','c']}(), where initialization is given 
            return $myField
        }
    }
@groovy.util.logging.Log
    @Log annotation which relies on the JDK logging framework
    //Exmaple 
    @groovy.util.logging.Log //generates private static final Logger log = Logger.getLogger(Greeter.name)
    class Greeter {
        void greet() {
            log.info 'Called greeter'
            println 'Hello, world!'
        }
    }
@groovy.util.logging.Commons
    For apache commons 
    Generates 
         private static final Log log = LogFactory.getLog(Greeter)
@groovy.util.logging.Log4j
    For  Apache Log4j 1.x 
    Generates 
        private static final Logger log = Logger.getLogger(Greeter)
@groovy.util.logging.Log4j2
    For For  Apache Log4j 1.x 
    Generates 
        private static final Logger log = LogManager.getLogger(Greeter)
@groovy.util.logging.Slf4j
    Groovy supports the Simple Logging Facade for Java (SLF4J) framework using to the @Slf4j annotation
    Generates 
        private static final Logger log = LoggerFactory.getLogger(Greeter)      
@groovy.transform.AutoClone       
        Implements java.lang.Cloneable interface 
@groovy.transform.AutoExternalize      
    Implements  java.io.Externalizable interface 
@groovy.transform.WithReadLock 
@groovy.transform.WithWriteLock   
@groovy.transform.Synchronized
@groovy.transform.ThreadInterrupt
@groovy.transform.TimedInterrupt
@groovy.transform.ConditionalInterrupt
    For thread handling, check Thread content 
@groovy.transform.Field  
    only for script, creates a field of Script class
    Note method can not access def var , no such problem with closure
    //Example 
    def x = 2		//use @Field def x=2,  without which becomes local variable in run
    String line() {
        "=" * x
    }
    line()             //groovy.lang.MissingPropertyException: No such property: x for class
@groovy.transform.PackageScope
    Creates a package private field instead of a property
@groovy.transform.TypeChecked
    @TypeChecked activates compile-time type checking on your Groovy code. 
@groovy.transform.CompileStatic
    @CompileStatic activates static compilation on your Groovy code.
@groovy.transform.CompileDynamic
    @CompileDynamic disables static compilation on parts of your Groovy code. 
@groovy.lang.DelegatesTo
    It is aimed at documenting code and helping the compiler 
    while using CompileStatic, TypeChecked
@groovy.beans.Bindable
@groovy.beans.ListenerList
@groovy.beans.Vetoable
    Swing Patterns 
@groovy.lang.Grab
@groovy.lang.GrabConfig
@groovy.lang.GrabExclude
@groovy.lang.GrabResolver
@groovy.lang.Grapes
    Grab configurations (dependencies managements)
    //http://docs.groovy-lang.org/latest/html/documentation/grape.html
    //Example for JDBC 
    @GrabConfig(systemClassLoader=true)
    @Grab(group='mysql', module='mysql-connector-java', version='5.1.6')
    //excluding transitive dependencies 
    @Grab('net.sourceforge.htmlunit:htmlunit:2.8')
    @GrabExclude('xml-apis:xml-apis')
    //multiple grab 
    @Grapes([
       @Grab(group='commons-primitives', module='commons-primitives', version='1.0'),
       @Grab(group='org.ccil.cowan.tagsoup', module='tagsoup', version='0.9.7')])
    class Example {
    // ...
    }
    
 
 
    
    
///Hands-On - Use @groovy.lang.Singleton to create Singleton class <*>
@groovy.lang.Singleton 
@groovy.transform.ToString
class Person implements Serializable{
  String first = "N"
  String last = "D" 
}

def x = Person.instance
def y =  Person.instance
x.is y 





///*** Groovy - Inheritance 
//Note  default and map ctor are genereated for derived class which takes base properties correctly 
//any user defined ctor would remove those 

@groovy.transform.ToString
class A{
  String p
  }
  
@groovy.transform.ToString(includeSuper=true) //default not to include super 
class B extends A {
   String q 
}

def b = new B ( p: "Ok", q: "NOK")
println b 


//Many AST by default does not include Super properties , use explicitely 
@groovy.transform.TupleConstructor(includeSuperProperties=true)
@groovy.transform.ToString(includeSuper=true)
@EqualsAndHashCode(callSuper=true)

//by default, no user defined constructors are inherited
//you must write Derived class constructors calling 'super(args)' at the first line
//or use @InheritConstructors, generates derived class ctor matching Base class user defined ctor 
//but only for if Derived class does not have any new property

//Example with user defined ctor 
@groovy.transform.ToString(includeNames = true)
class BaseClass {
  def  propB
  def BaseClass(Map options) {    //simulate map arg pattern by taking Map option
    this.propB = options.propB    
  }
  def meth1(x) { "baseClass"  }
}

@groovy.transform.ToString(includeNames = true, includeSuper = true)    // Include super's property 
class AnotherClass extends BaseClass {
  def propD

  def AnotherClass(options) {
    super(options)
	this.propD = options.propD
  }
  def meth1(x) { super.meth1(x) + "Derived"  }
  
}

def fun(BaseClass p) { p.meth1(2) }
//or
def fun(p) { p.meth1(2) }
//or
def String fun(def p) { p.meth1(2) }

def p = new AnotherClass(propB : 1, propD: 2)
println(p)
p.meth1(1)
fun(p)				//passing as BaseClass is OK


//OR, using InheritConstructors, but only for if Derived class does not have any new property

@groovy.transform.InheritConstructors			//will create the constructors corresponding to the superclass constructors 
@groovy.transform.ToString(includeNames = true, includeSuper = true) 
class AnotherClass extends BaseClass {
   def propD   //remove this for InheritConstructors to work 
   }

def p = new AnotherClass(propB : 1, propD: 2)  // But propD is not initialized!!, hence use with care!!
println(p)										// but without 'InheritConstructors' it gives error


//Good Example is Exception 
import groovy.transform.InheritConstructors

@InheritConstructors
class CustomException extends Exception {}

// all those are generated constructors
new CustomException()
new CustomException("A custom message")
new CustomException("A custom message", new RuntimeException())
new CustomException(new RuntimeException())



///*** Groovy Operators - Advanced 
///Operator precedence from highest to lowest:
//Level Operator(s) 	                    Name(s)
1       new   ()                            object creation, explicit parentheses
        ()   {}   []                        method call, closure, literal list/map
        .   .&   .@                         member access, method closure, field/attribute access
        ?.   *   *.   *:                    safe dereferencing, spread, spread-dot, spread-map
        ~   !   (type)                      bitwise negate/pattern, not, typecast
        []   ++   --                        list/map/array index, post inc/decrement
        
2       **                                  power
        
3       ++   --   +   -                     pre inc/decrement, unary plus, unary minus
        
4       *   /   %                           multiply, div, remainder
        
5       +   -                               addition, subtraction
        
6       <<   >>   >>>   ..   ..<            left/right (unsigned) shift, inclusive/exclusive range
        
7       <  <=  >  >=  in  instanceof  as    less/greater than/or equal, in, instanceof, type coercion

8       ==   !=   <=>                       equals, not equals, compare to	
        =~   ==~                            regex find, regex match

9       &                                   binary/bitwise and

10      ^                                   binary/bitwise xor

11      |                                   binary/bitwise or

12      &&                                  logical and

13      ||                                  logical or

14      ? :                                 ternary conditional
        ?:                                  elvis operator

15      =   **=   *=   /=   %=   +=   -=    various assignments
        <<=   >>=   >>>=   &=   ^=   |=
	

///Special operators
Spaceship
    <=>			
    Used in comparisons, returns -1 if left is smaller 0 if == to right or 1 if greater than the right
    //Example 
        3 <=> 4   //-1
Regex find				
    =~			
    Find with a regular expresion
    //Example 
        def x = "I am OK"
        def m = x =~ /(\w+)/
        m as Boolean //true 
        m[0]  //[I,I] ie [fullMatch, firstGroup, 2ndGroup..]
        m[1]  //[am, am]
        m[2]  //[OK, OK]
        m.size() //3
        m.class.methods.name.toSet()
        // [getClass, hasTransparentBounds, requireEnd, wait, useTransparentBounds, notifyAll, pattern, replaceFirst, regionStart, replaceAll, notify, lookingAt, toMatchResult, quoteReplacement, find, hashCode, end, group, start, regionEnd, matches, hasAnchoringBounds, appendReplacement, appendTail, groupCount, equals, reset, toString, region, hitEnd, useAnchoringBounds, usePattern]
Regex match				
    ==~			
    Get a match via a regex, (only for full string  match ), ie java matches() of String
    //Example 
        def x = "I am OK"
        x ==~ "I am OK"   //true 
Java Field Override 	
    .@			
    Direct access to field bypassing getter/setter
    //Example 
        class X
        {
            def field
            def getField()
            {
                field + 1
            }
        }
        x = new X(field:1) //x.field = 1
        println x.field    // getField(), 2
        println x.@field   // 1
Spread					
    *.			
    Used to invoke an action on all items of an aggregate object, ie, parent.collect{ each -> each?.action }
    //Example 
        def a = 1
        a.class.methods*.name
        //OR
        a.class.methods.name    //GPath, only for property/field and getXYZ() 
        //in function 
        int function(int x, int y, int z) {
            x*y+z
        }
        def args = [4,5,6]
        assert function(*args) == 26
        //Spread list elements by *list
        def items = [4,5]                      
        def list = [1,2,3,*items,6]            
        assert list == [1,2,3,4,5,6]           
        //Spread map elements, by *:map
        def m1 = [c:3, d:4]                   
        def map = [a:1, b:2, *:m1]            
        assert map == [a:1, b:2, c:3, d:4]    
Spread Java Field		
    *.@			
    Amalgamation of the above two
Method Reference		
    .&			
    Get a reference to a method, can be useful for creating closures from methods
    //Example 
        def fun (x ) { x}
        def a = this.&fun           // inside script or inside class
        def str = 'example of method reference'          
        def fun = str.&toUpperCase          	//for particular's intance              
        def upper = fun()                                  
        assert upper == str.toUpperCase() 
asType Operator			
    as			
    Used for groovy casting, coercing one type to another.
    //Example 
        Integer x = 123
        String s = (String) x         
        //OR
        String s = x as String   
        //OR
        String s = x.asType(String)
Membership Operator		
    in			
    Can be used as replacement for collection.contains()
    //Example 
        def list = ['Grace','Rob','Emmy']
        assert ('Emmy' in list)    
        def map = [ 1:'Grace', 2:'Rob', 3:'Emmy']
        1 in map //true
Safe Navigation			
    ?.			
    returns null instead of throwing NullPointerExceptions when accessing member
    //Example 
        String x = null
        println x?.size() //null 
Elvis Operator 			
    ?:			
    Shorter ternary operator, a ?: "default" is equivalent to 'a ? a : "default"'
    //Example 
        def user = null
        def displayName = user ? user : "Anonymous" 	//traditional ternary operator usage
        println  user ?: "Anonymous"  	// more compact Elvis operator - does same as above



///**** Groovy Test - Spock 
//Spock can be used for unit, integration or BDD (behavior-driven-development) testing, 


//Testing with Specifications
@Grab('org.spockframework:spock-core:1.0-groovy-2.4')
import spock.lang.*

class StackSpec extends spock.lang.Specification {

    def "adding an element leads to size increase"() {  
        setup: "a new stack instance is created"        //Setup block
            def stack = new Stack()

        when:                                          //pre condition , can access setup block's var
            stack.push 42

        then:                                           //result
            stack.size() == 1
    }
}


///Testing with Table

class HelloSpockSpec extends spock.lang.Specification {
  def "length of Spock's and his friends' names"() {
    expect:
    name.size() == length

    where:
    name     | length
    "Spock"  | 5
    "Kirk"   | 4
    "Scotty" | 6
  }
}  

///Specification with expect

import spock.lang.*

// Hit 'Run Script' below
class MyFirstSpec extends Specification {
  def "let's try this!"() {
    expect:
    Math.max(1, 2) == 3
  }
}

//Data Driven spock
import spock.lang.*

@Unroll
class DataDrivenSpec extends Specification {
  def "maximum of two numbers"() {
    expect:
    Math.max(a, b) == c

    where:
    a << [3, 5, 9]
    b << [7, 4, 9]
    c << [7, 5, 9]
  }

  def "minimum of #a and #b is #c"() {
    expect:
    Math.min(a, b) == c

    where:
    a | b || c
    3 | 7 || 3
    5 | 4 || 4
    9 | 9 || 9
  }

  def "#person.name is a #sex.toLowerCase() person"() {
    expect:
    person.getSex() == sex

    where:
    person                    || sex
    new Person(name: "Fred")  || "Male"
    new Person(name: "Wilma") || "Female"
  }

  static class Person {
    String name
    String getSex() {
      name == "Fred" ? "Male" : "Female"
    }
  }
}


//Database Driven

import groovy.sql.Sql
import spock.lang.Shared
import spock.lang.Specification

class DatabaseDrivenSpec extends Specification {
  @Shared sql = Sql.newInstance('jdbc:mysql://localhost:3306/groovy', 'root', '', 'com.mysql.jdbc.Driver')
  
  // insert data (usually the database would already contain the data)
  def setupSpec() {
    sql.execute("create table maxdata (id int primary key, a int, b int, c int)")
    sql.execute("insert into maxdata values (1, 3, 7, 7), (2, 5, 4, 5), (3, 9, 9, 9)")
  }

  def "maximum of two numbers"() {
    expect:
    Math.max(a, b) == c

    where:
    [a, b, c] << sql.rows("select a, b, c from maxdata")
  }
}








