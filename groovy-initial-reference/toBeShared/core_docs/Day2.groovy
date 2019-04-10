"""
Day-2 
    Gradle as Build Tool 
    Groovy closures
    Groovy Functional with Collections
    *XML, JSON 
    Groovy file 
    *groovy rest client and HTML
"""


///*** Groovy with gradle 


// check task list
$ gradle tasks
// execute as, it runs test as well
$ gradle build
$ gradle run   //runs mainClassName
$ gradle -q run   //runs mainClassName and only main class outputs 
$ gradle -PmainClass=foo runApp  //for multiple class , runs 'foo'
$ gradle -i test                 //if test code has any println
// check test report in build/reports/tests/index.html
//with java
$ java -cp "build\libs\*;%GROOVY_HOME%\lib\*" support.Person 123
//or with shadow Jar 
$ gradle shadowJar 
$ java -jar build\libs\base-*all*.jar 


//->file:build.gradle:
buildscript {   
    repositories {
        mavenCentral()
        jcenter()
    }
    dependencies {
        classpath "com.github.jengelman.gradle.plugins:shadow:2.0.4"
    }
}
apply plugin: "com.github.johnrengelman.shadow" //for generating fatjar , use gradle shadowJar
apply plugin: 'groovy'
apply plugin:'application'
mainClassName = "examples.Main"   //gradle run 

repositories {
          mavenCentral()	
          jcenter()
        }

dependencies {    
    compile 'org.codehaus.groovy:groovy-all:2.4.11'

    testCompile 'org.spockframework:spock-core:1.0-groovy-2.4'
    //testing
	testCompile 'junit:junit:4.10'
}

//gradle build and gradle -PmainClass=foo runApp
task runApp(type:JavaExec) {
    classpath = sourceSets.main.runtimeClasspath
    main = project.hasProperty("mainClass") ? project.getProperty("mainClass") : "examples.Main"
}





//->file:settings.gradle:
rootProject.name = 'base'



//->file:src\main\groovy\examples\GroovyGreetingImpl.groovy:
package examples


class GroovyGreetingImpl implements GreetingService {
String greet(String name) {
  "Hello ${name ?: 'stranger'}. Greeting from Groovy"
 }
}


//->file:src\main\groovy\examples\HelloWorld.groovy:
package examples

class HelloWorld {

    public static void main(String[] args) {
       args.each{ println it }
       println("helloWorld")
    }

}


//->file:src\main\groovy\examples\Main.groovy:
package examples


class Main {
    public static void main(String[] args){
        println("hello $args")
    }
}


//->file:src\main\java\examples\GreetingService.java:
package examples;

public interface GreetingService {
  String greet(final String name);
}


//->file:src\main\java\examples\JavaGreetingImpl.java:
package examples;

public class JavaGreetingImpl implements GreetingService {
 public String greet(final String name) {
  return "Hello " + (name != null ? name : "stranger") + ". Greeting from Java.";
 }
}


//->file:src\test\groovy\examples\GroovyGreetingTest.groovy:
package examples

import static org.junit.Assert.*
import org.junit.Test

public class GroovyGreetingTest {


  final GreetingService service = new GroovyGreetingImpl ();


@Test public void testGreet() {
  assertEquals("Hello OK. Greeting from Groovy", service.greet("OK"));
 }

@Test public void testGreetNull() {
  assertEquals("Hello stranger. Greeting from Groovy", service.greet(null));
 }
}



//->file:src\test\groovy\examples\GroovyGreetingTest2.groovy:
//methods with test prefix are executed 
package examples

public class GroovyGreetingTest2 extends GroovyTestCase {
 
 def service

  void setUp() {
   service = new GroovyGreetingImpl ();
   }

void testGreet() {
  assert "Hello OK. Greeting from Groovy" == service.greet("OK")
 }

void testFail() {
    def numbers = [1,2,3,4]
    shouldFail {
        numbers.get(4)
    }

 }
 void testGreetNull() {
  assert "Hello stranger. Greeting from Groovy" == service.greet(null)
 }
}


///*** ADVANCED closure 
///* Groovy Closure - advanced 
println( {null} instanceof Closure ) //true 
Closure.methods.name 

def ic = Closure.IDENTITY
println(ic(200))


///* Currying- Partially applied function 
//only for Closure
def fc(x,y){
    x - y 
}
def fcc = this.&fc 
def fccl = fcc.curry(10)
println(fccl(20))    //-10
def fccr = fcc.rcurry(10)
println(fccr(20))  //10

///* Compose 
def plus2  = { it + 2 }
def times3 = { it * 3 }

def pt = times3 << plus2  //RHS first
println(pt(20))
def tp = times3 >> plus2  //LHS first 
println(tp(20))

///* Memoization 
def fibNoM                    //for recursive, this line is must
fibNoM = { long n -> n<2 ? n : fibNoM(n-1)+fibNoM(n-2) }          // no memoization
println(fibNoM(15)) // slow!

//with memoization
def fib 
fib = { long n -> n<2?n:fib(n-1)+fib(n-2) }.memoize()          // with memoization
println( fib(25) ) // fast! 

//check time 
def withTime = {Closure operation , n->
              def start = System.currentTimeMillis()
              def res = operation(n)
              def timeSpent = System.currentTimeMillis() - start
              println "TIME IS ~ ${timeSpent}ms"
              res
            }
    
withTime(fibNoM, 15 )
withTime( fib, 15 )

///*TCO - with accumulator and trampoline
def fibfast
fibfast= { def n,  prev = 0G, nxt = 1G ->          
          n<2 ? nxt : fibfast.trampoline( n-1, nxt, prev+nxt)         
        }

fibfast =fibfast.trampoline()

withTime(fibfast, 1000)

//With methods 
//Creates a memoized version of a function 
//Example 
@groovy.transform.Memoized
long longComputation(int seed) {
    // slow computation
    Thread.sleep(100*seed)
    System.nanoTime()
}
def x = longComputation(1) // returns after 100 milliseconds
def y = longComputation(1) // returns immediatly
def z = longComputation(2) // returns after 200 milliseconds

//Creates a TailRecursive function 
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

///* ADVANCED: Method taking closure as argument and closure take variable arguments, 
//Use  Closure.maximumNumberOfParameters to determine no of parameters

def method(Closure c){
    def firstValue = 'a'
    def secondValue = 'b'
    if (c.maximumNumberOfParameters == 1)
        c(firstValue)
    else
        c(firstValue, secondValue)
}

//execute
method { a ->
    println "I just need $a"
}
method { a, b ->
    println "I need both $a and $b"
}



///* Groovy Closure - delegate  

//delegate=owner, owner=nesting closure or this, this=class instance
//default strategy OWNER_FIRST (then delegate) for unscoped variable
//use delegate always inside closure to access closure var as a clarity

//Example Delegate_First 
class Test {
     def x = 30
     def y = 40

     def run() {
         def data = [ x: 10, y: 20 ]
         def cl = { y = x + y }  //x,y from delegate ie from data, 
         cl.delegate = data
         cl.resolveStrategy = Closure.DELEGATE_FIRST //comment out this to see that this.y = 70
         cl()
         println x     //this.x //30
         println y     //this.y //40
         println data  //[x:10, y:30]
     }
 }

 new Test().run()
 
//another example 
class Test{
  def x = 20 
  
  def fun(){
     //def x = 23 
     
     def cl = { -> 
                //def x = 30 
                def cli = { ->
                    // def x = 0       //both this and above are in same scope 
                     println "$x"
                  }
                cli.delegate = [x:100]
                cli.resolveStrategy = Closure.DELEGATE_FIRST
                return cli
              }
     cl()()  
  }
}


new Test().fun()
 
//Using delegate 
class Person2 {
    String name
}
class Thing2 {
    String name
}

def p = new Person2(name: 'Norman')
def t = new Thing2(name: 'Teapot')

//name = "OK" // Uncomment this, as by-default OWNER_FIRST, assertion fails
//Clear script context once above is commented
def upperCasedName = { name.toUpperCase() }    //use delegate.name.toUpperCase()

upperCasedName.delegate = p                // change delegate, behaviour is changed
assert upperCasedName() == 'NORMAN'
upperCasedName.delegate = t
assert upperCasedName() == 'TEAPOT'




///**** Groovy Functional with Collections
//map is called 'collect', filter is called 'findAll' , reduce is called 'inject'
//flatMap is called 'collectMany', sort is called 'sort'
//zip is called transpose, slide is called collate 
//Mapping of List to Map by collectEntries with map closure(outputs list of two elements)

//Conversions are toMap(), toSet(), toList() etc 


//For Map, closure takes one arg(Entry, .key, .value) or two k,v args 
//For List,set, Closure takes one (each element) arg 
//For index operation , closure takes two, each_element, index arg 




///*** Groovy - List functional 
//http://www.groovy-lang.org/gdk.html


def ll = [1,2,"OK", [1,2,3]]
ll.findAll { e -> e instanceof String }  //filter

[null, 0, 1].grep()  //returns Groovy truth values 
ll.grep( String )  //calls the grep_arg#isCase(it), note this is not closure 
ll.grep{ it instanceof String}   //now passing closure , closure#isCase(it)

[1,2,3,4].split { it % 2 == 0 }  //split based on closure condition 

le.inject(200) { r, e -> r+e }     //reduce

[1,[2,3],[4],[]].collectNested { it * 2 } //recursive map, [2,[4,6],[8],[]] 
ll.collect{ e -> [e, e?.class] }    //map
ll.collectEntries{ e -> [e, e?.class] } //convert to Map 
ll.collectMany{ e -> [e, e?.class] } //flatMap

//spread operation 
ll*.class             //Spread operation , null removed , calling getClass() on each element 
def lle = [le, le, *le]  //spread Operation 

//sort 
def le2 = [2,100,300,1,0]
le2.sort()
println le2  //inplace 
le2.sort(false) //new copy 
//immutable version
lle = [ "a", "b", "xyz", "bb", "z", "zyxw"]
lle.sort(false){ e -> e.size()}  //sortBy
//two params Sort functions like Comparator 
assert ["hi","hey","hello"] == ["hello","hi","hey"].sort { a, b -> a.length() <=> b.length() }


///*Hands On 
def s = ["ok", "nok", "nnok", "o", "k", null, null, "b"]
Get a list with size of the each word 
Get a new list containing words starts with "o"
Get a size of each word from list  containing words starts with "o"
sort by length of Base64 encoding 

///HandsON: Find Unique element , def l1 = [1,2,3,1,1,2]


def res = []
def l1 = [1,2,3,1,1,2]

l1.each { ele ->
  if( !( ele in res)){
      res << ele
  }
  
  }

res



//Other imp operations 
[[1, 2, 3], [4, 5]].transpose() // [[1, 4], [2, 5]]   //zip 
[1,2,3].withIndex()  //[ [value, index] ... 
[1,2,3].join(",")
["a", "b", "c"].remove(2)  //index removal , even for integer list 
["a", "b", "c"].remove("a") //first value 

assert [1,3,4].containsAll([1,4])       	// `containsAll` will check that all elements are found



//groupBy
[1,2,3,4,5,6].groupBy { it % 2 } //[0:[2,4,6], 1:[1,3,5]] 
//list of closures, 1st closure, 1st group, 2nd closure, 1st subgroup
[1,2,3,4,5,6].groupBy({ it % 2 }, { it < 4 }) //[1:[(true):[1, 3], (false):[5]], 0:[(true):[2], (false):[4, 6]]]
//or 
[1,2,3,4,5,6].groupBy([{ it % 2 }, { it < 4 }])


//Finding Index
assert [1, 2, 3].find { it > 1 } == 2           	// find 1st element matching criteria
assert ['a', 'b', 'c', 'd', 'e'].findIndexOf {     // find index of 1st element matching criteria
    it in ['c', 'e', 'g']
} == 2

assert ['a', 'b', 'c', 'd', 'c'].indexOf('c') == 2  	// index returned
assert ['a', 'b', 'c', 'd', 'c'].indexOf('z') == -1 	// index -1 means value not in list
assert ['a', 'b', 'c', 'd', 'c'].lastIndexOf('c') == 4



//Duplicating elements 
assert [1, 2, 3] * 3 == [1, 2, 3, 1, 2, 3, 1, 2, 3]
assert [1, 2, 3].multiply(2) == [1, 2, 3, 1, 2, 3]

//grep 
//without any arg, find truth value 
//with one arg, does arg.isCase(each_element) ie behaves like switch truth 
def items = [1, 2, 0, false, true, '', 'foo', [], [4, 5], null]
assert items.grep() == [1, 2, true, 'foo', [4, 5]]

def list = ['a', 'b', 'aa', 'bc', 3, 4.5]
assert list.grep( ~/a+/ )  == ['a', 'aa']
assert list.grep( ~/../ )  == ['aa', 'bc']
assert list.grep( Number ) == [ 3, 4.5 ]
assert list.grep{ it.toString().size() == 1 } == [ 'a', 'b', 3 ]



//sum , max, min 
assert 4+6+10+12 == [2,3,5,6].sum { it * 2 }
assert 1+2+3+4 == [1,2,3,4].sum()
assert 5 == [2,3,1,5,4].max()
assert "hello" == ["hello","hi","hey"].max { it.length() }
assert "hello" == ["hello","hi","hey"].max( { a, b -> a.length() <=> b.length() } as Comparator )
assert 2 == [4,2,5].min()
assert "hi" == ["hello","hi","hey"].min { it.length() }

def lastDigit = { a, b -> a % 10 <=> b % 10 }
assert [19, 55, 91].min(lastDigit) == 91


//take, drop 
def strings = [ 'a', 'b', 'c' ]
assert strings.take( 2 ) == [ 'a', 'b' ]
assert strings.takeRight( 2 ) == [ 'b', 'c' ]
assert strings.takeWhile{ it < 'b' } == ['a']
assert strings.dropRight( 2 ) == [ 'a' ]
assert strings.drop( 2 ) == [ 'c' ]
assert "horse".iterator().dropWhile{ it < 'r' } == ['r', 's', 'e']

//count
assert [2,4,2,1,3,5,2,4,3].count{ it % 2 == 0 } == 5
assert [2,4,2,1,3,5,2,4,3].count(4) == 2
assert [0:2, 1:3] == [1,2,3,4,5].countBy { it % 2 }


///HandsOn: Explore List GDK other Methods 

Others functions 
    flatten()  
    first()  last(), head(), tail(), init() (all except last)
    eachWithIndex(Closure closure) // item, index 
    pop(), push(Object)
    reverse() reverse(true) (mutable version), reverseEach(Closure closure) 
    subsequences() 
    swap(int i, int j) 

    String toListString()
    toSet(), toList(), toSorted()

    toUnique() , toUnique(Closure condition) , toUnique(Comparator comparator) 
    mutable version - unique() unique(Closure closure) unique(Comparator comparator) 
    withEagerDefault(Closure init) withLazyDefault/withDefault(Closure init) 
    
    //hooks getAt only 
    def list = [0, 1].withLazyDefault{ 42 }
    assert list[3] == 42
    def list2 = [5].withLazyDefault{ index -> index * index }
    assert list2[2] == 4

Below coming from iterable, hence can be used on any iterable 
    every(Closure closure), any(Closure closure)
    countBy(Closure closure)
    asCollection(), asList()
    sum(Closure closure), sum(),sum(Object initialValue)
    permutations(),permutations(Closure function)(applies each permutation to closure)
    eachCombination(Closure function), eachPermutation(Closure closure)
    combinations() combinations(Closure function)(applies each combination to closure)
    Map indexed() //to Map with [index:value]
    join(String separator)
    min(), min(Closure closure), min(Comparator comparator)
    max(), max(Closure closure), max(Comparator comparator)
    collate(int size),collate(int size, boolean keepRemainder) //breaks list into sublist of size ie slide
    collate(int size, int step), collate(int size, int step, boolean keepRemainder)
    //Example 
    def list = [ 1, 2, 3, 4, 5, 6, 7 ]
    def coll = list.collate( 3 )
    assert coll == [ [ 1, 2, 3 ], [ 4, 5, 6 ], [ 7 ] ]
    def list = [ 1, 2, 3, 4 ]
    def coll = list.collate( 3, 1 )
    assert coll == [ [ 1, 2, 3 ], [ 2, 3, 4 ], [ 3, 4 ], [ 4 ] ]


//HandsOn: Explore List GDK other Methods 
//Can u use collate, CountBy, withLazyDefault, join, eachCombination

//combinations 
assert [['a', 'b'],[1, 2, 3]].combinations() == [['a', 1], ['b', 1], ['a', 2], ['b', 2], ['a', 3], ['b', 3]]
assert [[2, 3],[4, 5, 6]].combinations {x,y -> x*y } == [8, 12, 10, 15, 12, 18]

def result = [1, 2, 3].permutations()
assert result == [[3, 2, 1], [3, 1, 2], [1, 3, 2], [2, 3, 1], [2, 1, 3], [1, 2, 3]] as Set

Set result = [1, 2, 3].permutations { it.collect { v -> 2*v }}
assert result == [[6, 4, 2], [6, 2, 4], [2, 6, 4], [4, 6, 2], [4, 2, 6], [2, 4, 6]] as Set

//Each version 
[[2, 3],[4, 5, 6]].eachCombination { println "Found $it" } 
//Output Found [2, 4]Found [3, 4]Found [2, 5]Found [3, 5]Found [2, 6]Found [3, 6]

def permutations = []
[1, 2, 3].eachPermutation{ permutations << it }
assert permutations == [[1, 2, 3], [1, 3, 2], [2, 1, 3], [2, 3, 1], [3, 1, 2], [3, 2, 1]]


///Handson :
def x = [1,20,45,36]
Get a list where each element is index multiplied by element 
take 2 element 
drop 1 and then take 2 
take while element is less than 20 
drop while element is less than 20 

///Handson : Find list of Unique element using GDK 
def l1 = [1,2,3,1,1,2]

///Handson : frequency of words using list methods 
def str =  "Hello Groovy Hello"

str.split(/\s+/).each { wd ->     
      println "$wd ${str.split(/\s+/).count{wd2 -> wd == wd2}}"  
}



///*** Groovy -  Lazy list, use from jdk8 stream. these methods are stream methods 
//get() and collect() are forcing it 
[-1,1,2,3,4].stream().filter{it>0}.map{it*4}.filter{it < 100}.findFirst().get()
[-1,1,2,3,4].stream().filter{it>0}.map{it*4}.filter{it < 100}.collect()

//or use groovy-stream - must use collect() to force it 
//other methods eg drop(), take() dropWhile() etc 
@Grab( 'com.bloidonia:groovy-stream:0.8.1' )
import groovy.stream.Stream

List integers = [ -1, 1, 2, 3, 4 ]

//.first() or .last() whatever is needed
Stream.from integers filter{ it > 0 } map{ it * 4 } filter{ it < 15 }.collect()

//or use RxJava- Groovy - use subscribe to force it 
//has skip(for drop), take and thier while version 
//note from takes Array , future or Iterable (not iterator)
@Grab('com.netflix.rxjava:rxjava-groovy:0.20.7')
import rx.Observable
Observable.from( [-1, 1, 2, 3, 4, 666] )
    .filter {  it > 0 }
    .map {  it * 4 }
    .filter {  it < 100 }
    .subscribe { println "result $it" }

    

///*** Groovy - Set Functional 
//Similar to List, only unordered 


def items = [1, 2, 0, false, true, '', 'foo', [], [4, 5], null] as Set
assert items.findAll() == [1, 2, true, 'foo', [4, 5]] as Set

assert ([2,4] as Set) == ([1,2,3,4] as Set).findAll { it % 2 == 0 }

assert [1,2,3,4,5] as Set == ([1,[2,3],[[4]],[],5] as Set).flatten()

def items = [1, 2, 0, false, true, '', 'foo', [], [4, 5], null] as Set
assert items.grep() == [1, 2, true, 'foo', [4, 5]] as Set

def set = ['a', 'b', 'aa', 'bc', 3, 4.5] as Set
assert set.grep( ~/a+/ )  == ['a', 'aa'] as Set
assert set.grep( ~/../ )  == ['aa', 'bc'] as Set
assert set.grep( Number ) == [ 3, 4.5 ] as Set
assert set.grep{ it.toString().size() == 1 } == [ 'a', 'b', 3 ] as Set

assert [4,5] as Set == ([1,2,3,4,5] as Set).intersect([4,5,6,7,8])

assert [[2,4] as Set, [1,3] as Set] == ([1,2,3,4] as Set).split { it % 2 == 0 }



///*** Groovy - Map Functional 

def mll = ['ok': 1, nok: 2, 3:4]

//All closure(except sort) in Map, either take Entry(use .key, .value) or two args k,v 
mll.each {kv -> println(kv.key) }
mll.each {k,v -> println(v) }

mll.findAll { kv -> kv.key instanceof String }  //filter

me.inject(200) { r, k, v -> r+v }     //reduce



//collect converts to List , to convert to another map use collectEntries
mll.collect{ kv -> [kv.value, kv?.key.class] }    //map
mll.collectEntries{ kv -> [kv.value, kv?.key.class] }    //map

def map = [bread:3, milk:5, butter:2]
def result = map.collectMany{ k, v -> k.startsWith('b') ? k.toList() : [] } //flatMap 

//Note sort only take Entry as arg or comparator 
//sort is always immutable, no mutable version ie sort(true)(Closure) does not exist 
def m = [ok:1, nok:2]
m << [old:10]     //===> [ok:1, nok:2, new:5, old:10]
def x = m.sort()  //===> based on key 
x.is(m)  //false 
x == m //true 
x instanceof SortedMap //true 
m instanceof Map  //true 
//sort by 
m.sort{ kv -> kv.value}       //returns new  sorted map, based on value // returns SortedMap
println m                    //original is not modified 

m.sort { kv1, kv2 -> kv1.value <=> kv2.value } //Comparator version

//another version of collect/collectEntries/collectMany takes collector as first arg 
[a:1, b:2].collect( [] as HashSet ) { key, value -> key*value } == ["a", "bb"] as Set

//contBy and groupBy 
//closure should return the key that each item should be grouped under
def result = [a:1,b:2,c:3,d:4,e:5].countBy { it.value % 2 } //[0:2, 1:3]
[a:1,b:2,c:3,d:4,e:5,f:6].groupBy { it.value % 2 }//[0:[b:2, d:4, f:6], 1:[a:1, c:3, e:5]]

mll.entrySet()*.key*.class            //Spread operation , null removed 
//mll.entrySet()*.key.class  //No GPATH exists 
[a:1,b:2,*:[c:3,d:4,e:5]] //spread operation 


///Hands On: Explore Map GDK other Methods 
Other methods 
    any(Closure closure)  every(Closure closure)  
    asImmutable() asSynchronized() 
    count(Closure closure)  - no of items satishfying Closure 
    drop(int num)  dropWhile(Closure condition) 
    take(int num)  takeWhile(Closure condition) 
    eachWithIndex(Closure closure) //index is last 
    Entry  find(Closure closure)  
    findResult(Closure closure) findResults(Closure filteringTransform) (closure result) 
    Map groupBy(List closures) Map groupBy(Object closures) (list of closures, 1st closure => Top level group, 2nd closure => its 1st sub group ...)
    intersect(Map right) 
    max(Closure closure) ,min(Closure closure) 
    reverseEach(Closure closure) 
    subMap(Object[] keys) subMap(Collection keys) 
    toMapString() 
    toSorted() toSorted(Closure condition) toSorted(Comparator comparator) 
    Map  withDefault(Closure init)  
    //Example 
    def map = [a:1, b:2].withDefault{ k -> k.toCharacter().isLowerCase() ? 10 : -10 }
    map.c//10



///HandsON: Write Frequency above Solution via Map 


str.toList().collectEntries{ ch ->
 [ ch, str.toList().count { ch1 -> ch == ch1 }]
 }
  
//another way 
def sl = s.toList()
def x = { ch1 -> sl.count {ch -> ch == ch1 }  }
sl.groupBy(x).collectEntries{ k,v -> [k, v.toSet()] }











///*** Groovy - Iterator 
//Iterable - which has .iterator() return a Iterator
//iterator which has next() and hasNext()
//Iterator is Lazy computations , hence no indexing operation, [int] like List 
//Note Iterable and Iterator user API are almost same 
  
//java.util.Collection (extends java.util.Iterable) has .iterator() 
//Hence List, Set (but not Map as Map does not extend Collection) has .iterator()
//GDK has many enhancements to Iterator 

def lst = [1,2,3].iterator() //java.util.ArrayList$Itr
lst.each { println it }
//single pass, so exhausted 
lst.each { println it } //empty 


//Infinite sequence 
//Either class based or SAM based 
class Fib implements Iterator<BigInteger>{
   def a = 0G 
   def b = 1G 
   boolean hasNext(){    //must match Iterator signature 
            return true 
        }
   BigInteger next(){
        (a,b) = [b,a+b]
        return a 
    }   
}

f = new Fib()
f.take(10).each{ println it}

//or with SAM 
class Fib implements Iterable<BigInteger>{
   def a = 0G 
   def b = 1G 
   Iterator<BigInteger> iterator(){
        [ hasNext: { -> true }, next:{ -> (a,b) = [b, a+b]; a}] as Iterator
   }
}

f = new Fib()
f.take(10).each{ println it}
//OR 
f.iterator().take(10).each{println it}
  
  
  
        
  
///*** GROOVY JSON 

//JsonSlurper results conform to GPath expressions. 
//JSON			Groovy Object
string			java.lang.String
number			java.lang.BigDecimal or java.lang.Integer
object			java.util.LinkedHashMap
array			java.util.ArrayList
true			true
false			false
null			null
date			java.util.Date based on the yyyy-MM-dd’T’HH:mm:ssZ date format



//example.json: { "details" : {"person":{"name":"John Doe","age":40,"cars":["bmw","ford"]} } }

import groovy.json.*

def slurper = new JsonSlurper()
def result = slurper.parse(new File('example.json')) //parseText(str)

println   result.details.person.age 
println   result.details.person.age
println   result.details.person.cars.size()
println   result.details.person.cars[0]
println   result.details.person.cars[1]

/*
{ "details" :[ {"person":{"name":"John Doe","age":40,"cars":["bmw","ford"]} },
{"person":{"name":"John Doe","age":42,"cars":["bmw","ford"]} },
{"person":{"name":"John Doe","age":45,"cars":["bmw","ford"]}  },
{"person":{"name":"John Doe","age":45,"cars":["honda","toyota"]} },
{"person":{"name":"John Doe","age":46,"cars":["bmw","ford"]} },
{"person":{"name":"John Doe","age":47,"cars":["nano","audi"]} }]
}
*/
def result = slurper.parse(new File('examples-o.json')) //parseText(str)
result.details[0].person
result.details*.person*.age //spread
//GPATH
result.details.person.age
//collect and findAll 
result.details.collect{ it.person }.collectMany{ it.cars }.toSet()
result.details.person.findAll{ it.age >= 45 }.cars.flatten().toSet()



///* JsonBuilder

import groovy.json.*
//Array by ,
JsonBuilder builder = new JsonBuilder()
builder.records {
  car {
        name 'HSV Maloo'
        make 'Holden'
        year 2006,2007,2008
        country 'Australia'
        record {
            type 'speed'
            description 'production pickup truck with speed of 271kph'
        }
  }
}
String json = builder.toString()

//Converting object to Json string 
import groovy.json.*

class Me {
    String name
}

def o = new Me( name: 'tim' )

println new JsonBuilder( o ).toPrettyString()

//can be used for deepcopy 
def in1 = [ok : 2 , nok : 3]
def li = [in1: [in2: in1.clone()]]

import groovy.json.* 
def b = new JsonBuilder(li)
def s = b.toPrettyString()
def lii = new JsonSlurper().parseText(s)
lii.in1.in2.ok = 20
lii
li


//Creating from another array 
def contact = [ 
    [ getFirstName : { 'A' }, getLastName : { 'B' }, getTitle : { 'C' } ], 
    [ getFirstName : { 'D' }, getLastName : { 'E' }, getTitle : { 'F' } ], 
    [ getFirstName : { 'G' }, getLastName : { 'H' }, getTitle : { 'I' } ]     
]

def jsonBuilder = new groovy.json.JsonBuilder()

jsonBuilder {
    contacts contact.collect { 
        [ 
            FirstName: it.getFirstName(), 
            LastName: it.getLastName(), 
            Title: it.getTitle() 
        ] 
    }
}

println jsonBuilder.toPrettyString()



///* Hands On - jSON 
import groovy.json.*
def slurper = new JsonSlurper()
def result = slurper.parse(new File('emps.json'))

//note 
result.details.firstName   //GPATH and all first name 
//Any time , break nested GPATH with one of List methods eg .collect, .findAll, etc 
//closures of these method take that element's value on which the method is called 
json.details.collect{it.firstName}  //here it is 'details' value 
//and again GPATH can be started 
result.collect{ it.details}.firstName  //here it is each element of root array
//Full Name 
result.details.collect{ it.firstName + it.lastName } //here it is 'details' value 
//Office phone number , here ar is 'phoneNumbers' value ie an array
result.details.phoneNumbers.collectMany { ar -> ar.findAll{ it.type =="office"}.collect{ it.number}}
result.details.phoneNumbers.collectMany { ar -> ar.findAll{ it.type =="office"}.number }




///*** Groovy XML - groovy.util and groovy.xml 
//groovy.util.XmlParser and groovy.util.XmlSlurper
//Both have the same approach to parse an xml. 
//Eg parseText: It parses a XML String and recursively converts it to a list or map of objects.
//for file use parse(File)

//Both Support GPATH

//XmlSlurper.parse retuns GPathResult, use .element  to get text 
//XmlParser.parse returns Node , use element.text() to get text 

//http://docs.groovy-lang.org/latest/html/api/groovy/util/slurpersupport/GPathResult.html
//http://docs.groovy-lang.org/latest/html/api/groovy/util/Node.html
//they are in groovy.util.*, hence no need to import 
XmlParser() 
XmlSlurper() 
    Creates a non-validating and non-namespace-aware XmlParser 
    which does not allow DOCTYPE declarations in documents.
 
XmlParser(boolean validating, boolean namespaceAware) 
XmlSlurper(boolean validating, boolean namespaceAware) 
    Creates a XmlParser which does not allow DOCTYPE declarations in documents.
 
XmlParser(boolean validating, boolean namespaceAware, boolean allowDocTypeDeclaration) 
XmlSlurper(boolean validating, boolean namespaceAware, boolean allowDocTypeDeclaration) 
    Creates a XmlParser.
 
XmlParser(SAXParser parser)  
XmlSlurper(SAXParser parser) 

XmlParser(XMLReader reader)  
XmlSlurper(XMLReader reader) 

//XmlSlurper 
def text = '''
    <list>
        <technology>
            <name>Groovy</name>
        </technology>
    </list>
'''

def list = new XmlSlurper().parseText(text) 

assert list instanceof groovy.util.slurpersupport.GPathResult 
assert list.technology.name == 'Groovy' 

//XmlParser

def text = '''
    <list>
        <technology>
            <name>Groovy</name>
        </technology>
    </list>
'''

def list = new XmlParser().parseText(text) 

assert list instanceof groovy.util.Node 
assert list.technology.name.text() == 'Groovy' 

///similarities between XMLParser and XMLSlurper first:
    Both are based on SAX so they both are low memory footprint
    Both can update/transform the XML

///differences:
    XmlSlurper evaluates the structure lazily. 
        So if you update the xml you’ll have to evaluate the whole tree again.
    XmlSlurper returns GPathResult instances when parsing XML
        XmlParser returns Node objects when parsing XML
    XMLSlurper does not require ns , but XMLParser requires ns like 'x:tag'
        

///When to use one or the another?
    If you want to transform an existing document to another then XmlSlurper will be the choice
    If you want to update and read at the same time then XmlParser is the choice.
    If you just have to read a few nodes XmlSlurper should be your choice, 
        since it will not have to create a complete structure in memory

        
///DOMCategory
//There is another way of parsing XML documents , groovy.xml.dom.DOMCategory 
//which is a category class which adds GPath style operations to Java’s DOM classes.
	
//Java has in-built support for DOM processing of XML using classes 
//representing the various parts of XML documents, e.g. Document, Element, NodeList, Attr etc. 

static def CAR_RECORDS = '''
  <records>
    <car name='HSV Maloo' make='Holden' year='2006'>
      <country>Australia</country>
      <record type='speed'>Production Pickup Truck with speed of 271kph</record>
    </car>
    <car name='P50' make='Peel' year='1962'>
      <country>Isle of Man</country>
      <record type='size'>Smallest Street-Legal Car at 99cm wide and 59 kg in weight</record>
    </car>
    <car name='Royale' make='Bugatti' year='1931'>
      <country>France</country>
      <record type='price'>Most Valuable Car at $15 million</record>
    </car>
  </records>
'''

import groovy.xml.DOMBuilder 
import groovy.xml.dom.DOMCategory

def reader = new StringReader(CAR_RECORDS)
def doc = DOMBuilder.parse(reader) 
def records = doc.documentElement

use(DOMCategory) { 
    assert records.car.size() == 3
}

///GPath
//The two main places where you use GPath expressions is when dealing with nested POJOs 
//or when dealing with XML
    a.b.c → for XML, yields all the <c> elements inside <b> inside <a>
    a.b.c → all POJOs, yields the <c> properties for all the <b> properties of <a> (sort of like a.getB().getC() in JavaBeans)

//For XML, you can also specify attributes, e.g.:
    a["@href"] → the href attribute of all the a elements
    a.'@href' → an alternative way of expressing this
    a.@href → an alternative way of expressing this when using XmlSlurper

static final String books = '''
    <response version-api="2.0">
        <value>
            <books>
                <book available="20" id="1">
                    <title>Don Xijote</title>
                    <author id="1">Manuel De Cervantes</author>
                </book>
                <book available="14" id="2">
                    <title>Catcher in the Rye</title>
                   <author id="2">JD Salinger</author>
               </book>
               <book available="13" id="3">
                   <title>Alice in Wonderland</title>
                   <author id="3">Lewis Carroll</author>
               </book>
               <book available="5" id="4">
                   <title>Don Xijote</title>
                   <author id="4">Manuel De Cervantes</author>
               </book>
           </books>
       </value>
    </response>
'''

//Simply traversing the tree
def response = new XmlSlurper().parseText(books)
def authorResult = response.value.books.book[0].author
assert authorResult.text() == 'Manuel De Cervantes'

//GPathResult has many convenient methods to convert the text inside a node to any other type such as:
    toInteger()
    toFloat()
    toBigInteger()

//Getting an attribute’s value
def response = new XmlSlurper().parseText(books)

def book = response.value.books.book[0] 
def bookAuthorId1 = book.@id 
def bookAuthorId2 = book['@id'] 

assert bookAuthorId1 == '1' 
assert bookAuthorId1.toInteger() == 1 
assert bookAuthorId1 == bookAuthorId2



      

//Important methods of GPathResult(XmlSlurper) and Node(XmlParser)
GPathResult(XmlSlurper)
    //note to get Document root 
    GPathResult XmlSlurper_instance.getDocument()  
    //http://docs.groovy-lang.org/latest/html/api/groovy/util/slurpersupport/GPathResult.html
    void appendNode(Object newValue)  
    Iterator breadthFirst()  
    Iterator childNodes()  
    GPathResult children() 
    GPathResult declareNamespace(Map newNamespaceMapping)  
    Iterator depthFirst()  
    boolean equals(Object obj)  
    GPathResult find(Closure closure)  
    GPathResult findAll(Closure closure)  
    Object getAt(int index) 
    Object getAt(IntRange range)  
    Closure getBody()  
    Object getProperty(String property)  
    void setProperty(String property, Object newValue) 
    boolean isEmpty()  
    Iterator iterator() 
    Object leftShift(Object newValue) //append Node 
    List list() 
    String lookupNamespace(String prefix) //Returns the namespace mapped to the specified prefix.
    String name() //Returns the name of this GPathResult.
    Iterator nodeIterator()  
    GPathResult parent() 
    GPathResult parents() 
    Object plus(Object newValue) //Lazily adds the specified Object to this GPathResult.
    GPathResult pop()  //Returns the parent of this GPathResult.
    
    void putAt(int index, Object newValue) 
    void replaceBody(Object newValue)  
    void replaceNode(Closure newValue)  
    int size() 
    String text() 
    BigDecimal toBigDecimal() //Converts the text of this GPathResult to a BigDecimal object.
    BigInteger toBigInteger()  
    Boolean toBoolean() 
    Double toDouble() 
    Float toFloat() 
    Integer toInteger() 
    Long toLong()  
    String toString()  
    URI toURI()  
    URL toURL() 

Node(XmlParser)
    //http://docs.groovy-lang.org/latest/html/api/groovy/util/Node.html
    Node(Node parent, Object name) 
    Node(Node parent, Object name, Map attributes) 
    Node(Node parent, Object name, Map attributes, Object value) 
    Node(Node parent, Object name, Object value) 

    boolean append(Node child) //Appends a child to the current node. 
    Node appendNode(Object name) //Creates a new node as a child of the current node.
    Node appendNode(Object name, Map attributes) 
    Node appendNode(Object name, Map attributes, Object value) 
    Node appendNode(Object name, Object value) 
    Object attribute(Object key) 
    Map attributes() 
    List breadthFirst() 
    List children() 
    Object clone() 
    List depthFirst() 
    Object get(String key) 
    NodeList getAt(QName name)   //http://docs.groovy-lang.org/latest/html/api/groovy/xml/QName.html
    Iterator iterator() 
    List<String> localText() //Returns the list of any direct String nodes of this node.
    Object name() 
    Node parent() 
    void plus(Closure c) //Adds sibling nodes (defined using builder-style notation via a Closure) after the current node.
    void print(PrintWriter out) 
    boolean remove(Node child) 
    Node replaceNode(Closure c) 
    Node replaceNode(Node n) 
    void setParent(Node parent) 
    void setValue(Object value) 
    String text() 
    String toString()  
    Object value() 


///With NameSpace 

/*
<?xml version="1.0" ?>
<bib:bibliography xmlns:bib="http://bibliography.org"
  xmlns:lit="http://literature.org">
  <bib:author >William Shakespeare</bib:author>
  <lit:play>
    <lit:year>1589</lit:year>
    <lit:title lang='Eng'>The Two Gentlemen of Verona.</lit:title>
  </lit:play>
  <lit:play>
    <lit:year>1594</lit:year>
    <lit:title lang='ita'>Love's Labour's Lost.</lit:title>
  </lit:play>
  <lit:play>
    <lit:year>1594</lit:year>
    <lit:title lang='Eng'>Romeo and Juliet.</lit:title>
  </lit:play>
  <lit:play>
    <lit:year>1595</lit:year>
    <lit:title lang='esp'>A Midsummer-Nights Dream.</lit:title>
  </lit:play>
</bib:bibliography>


*/



//bibliography becomes 'bibliography' root element from xml file 

import groovy.util.*
def xmlSource = new File('../data/example.xml')
def bibliographyS = new XmlSlurper().parse(xmlSource)  // OR use parseText() 

bibliographyS.play.title[0]   //first one GPATH, starts from zero 
//With Parser
def bibliographyP = new XmlParser().parse(xmlSource) // type is groovy.util.Node

//XmlSlurper, does not require FQN
println bibliographyS.author

// OR with Parser , must have FQN, Returns groovy.util.Node, hence text() method is needed
//
println bibliographyP.'bib:author'.text()
println bibliographyP.'lit:play'.'lit:title'*.text() //result is list 

//For XML , to access attribute, the href attribute of all the a elements, (return could be array)
//a.@href -> direct notation 
//a["@href"] -> map-like notation 
//a.'@href' -> property notation 

//Any time , break nested GPATH with one of List methods eg .collect, .findAll, etc 
//closures of these method take that element on which the method is called 
//and again GPATH can be started 

//Use findAll, xmlSlurper - searches child of play , Here it is play 
bibliographyS.play.findAll { it.year.toInteger() > 1592 } .each { println it.title.@lang }
bibliographyS.play.findAll { it.year.toInteger() > 1592 }.title.@lang //Again GPATH started , all @lang 
bibliographyS.play.findAll { it.year.toInteger() > 1592 }.title*.@lang  //Note list , [Eng, ita, Eng, esp]
bibliographyS.play.findAll { it.year.toInteger() > 1592 } 
       .collect { it.title.@lang }
// OR with Parser
bibliographyP.'lit:play'
    .findAll { it.'lit:year'.text().toInteger() > 1592 }
    .each { println it.'lit:title'.text() }


//Using spread operator
bibliographyS.play*.title*.@lang  //[Eng, ita, Eng, esp]
// or GPATH way   
bibliographyS.play.title.@lang  //EngitaEngesp
//size 
bibliographyS.play.size()


//search across all the nodes of the document 
//(as opposed to a specific set of children, such as play), 
//use the depthFirst method (or its shortcut **) on the root node
//Note all elements would be iterated ie 

groovy> bibliographyP.'**'.each{ println it} 
{http://bibliography.org}bibliography[attributes={}; value=[{http://bibliography.org}author[attributes={}; value=[William Shakespeare]], {http://literature.org}play[attributes={}; value=[{http://literature.org}year[attributes={}; value=[1589]], {http://literature.org}title[attributes={lang=Eng}; value=[The Two Gentlemen of Verona.]]]], {http://literature.org}play[attributes={}; value=[{http://literature.org}year[attributes={}; value=[1594]], {http://literature.org}title[attributes={lang=ita}; value=[Love's Labour's Lost.]]]], {http://literature.org}play[attributes={}; value=[{http://literature.org}year[attributes={}; value=[1594]], {http://literature.org}title[attributes={lang=Eng}; value=[Romeo and Juliet.]]]], {http://literature.org}play[attributes={}; value=[{http://literature.org}year[attributes={}; value=[1595]], {http://literature.org}title[attributes={lang=esp}; value=[A Midsummer-Nights Dream.]]]]]]
{http://bibliography.org}author[attributes={}; value=[William Shakespeare]]
{http://literature.org}play[attributes={}; value=[{http://literature.org}year[attributes={}; value=[1589]], {http://literature.org}title[attributes={lang=Eng}; value=[The Two Gentlemen of Verona.]]]]
{http://literature.org}year[attributes={}; value=[1589]]
{http://literature.org}title[attributes={lang=Eng}; value=[The Two Gentlemen of Verona.]]
{http://literature.org}play[attributes={}; value=[{http://literature.org}year[attributes={}; value=[1594]], {http://literature.org}title[attributes={lang=ita}; value=[Love's Labour's Lost.]]]]
{http://literature.org}year[attributes={}; value=[1594]]
{http://literature.org}title[attributes={lang=ita}; value=[Love's Labour's Lost.]]
{http://literature.org}play[attributes={}; value=[{http://literature.org}year[attributes={}; value=[1594]], {http://literature.org}title[attributes={lang=Eng}; value=[Romeo and Juliet.]]]]
{http://literature.org}year[attributes={}; value=[1594]]
{http://literature.org}title[attributes={lang=Eng}; value=[Romeo and Juliet.]]
{http://literature.org}play[attributes={}; value=[{http://literature.org}year[attributes={}; value=[1595]], {http://literature.org}title[attributes={lang=esp}; value=[A Midsummer-Nights Dream.]]]]
{http://literature.org}year[attributes={}; value=[1595]]
{http://literature.org}title[attributes={lang=esp}; value=[A Midsummer-Nights Dream.]]

groovy> bibliographyS.'**'.each{ println it.name()} 
bibliography
author
play
year
title
play
year
title
play
year
title
play
year
title

//seraching from root, we are looking for only element which has .title ie play element 
bibliographyS.'**'.findAll {                           
  it.title.text().contains('Dream')  //text() is must, also note all childrens are searched (including author, hence .toInterger() would create problem)
}.each {                                
  println "- ${it.title}"
}

bibliographyS.'**'.findAll{ n ->
   def v = null
    try{
       v = n.year.toInteger()
    }
    catch(Exception){
    }
    v > 1593 //null safe 
} .collect { it.title }


///HandsOn - country.xml 


def data = new XmlSlurper().parse(new File(/data\country.xml/))

//All ranks 
data.country.rank*.toInteger()
//All names 
data.country*.@name
//Country name vs neighbor names 
data.country.collectEntries{ [it.@name , it.'**'.findAll{n -> n.name() == 'neighbor'}*.@name]} 
//OR 
data.country.collectEntries{ [it.@name , it.neighbor*.@name]} 

 



///* XMLBuilder
import groovy.xml.*
def writer = new StringWriter()
def xml = new MarkupBuilder(writer)

xml.bibliography {                                    // each closure creates sub section
          author 'William Shakespeare'                 //each method becomes xml tag
          play {
            year '1595'
            title 'A Midsummer-Night Dream.'
            status(['private':true], 'pendding')   //attributes in map
          }
        }

println writer

//With Namespace 
import groovy.xml.*
def writer = new StringWriter()
def xml = new MarkupBuilder(writer)
xml.'rec:records'('xmlns:rec': 'http://groovy.codehaus.org') {
  'rec:car'(name:'HSV Maloo', make:'Holden', year:2006) {
    country('Australia')
    record(type:'speed', ' Truck with speed of 271kph')
  }
}
def str = groovy.xml.XmlUtil.serialize(writer.toString() )  

//Another example 
def xmlWriter = new StringWriter()
def xmlMarkup = new MarkupBuilder(xmlWriter)

xmlMarkup
    .'x:movies'('xmlns:x':'http://www.groovy-lang.org') {
        (1..3).each { n -> 
            'x:movie'(id: n, "the godfather $n")
            if (n % 2 == 0) { 
                'x:movie'(id: n, "the godfather $n (Extended)")
            }
        }
    }

def movies = new XmlSlurper()
        .parseText(xmlWriter.toString())
        .declareNamespace(x:'http://www.groovy-lang.org')

assert movies.'x:movie'.size() == 4
assert movies.'x:movie'*.text().every { name -> name.startsWith('the')}





///*Printing XML 
//Note groovy.xml.XmlUtil.serialize
static String serialize(org.w3c.dom.Element element)  //super interface org.w3c.dom.Node
static void serialize(Element element, OutputStream os)  //Write a pretty version of the Element to the OutputStream.
static void serialize(Element element, Writer w) 
static String serialize(GPathResult node) 
static void serialize(GPathResult node, OutputStream os) 
static void serialize(GPathResult node, Writer w) 
static String serialize(Node node) 
static void serialize(Node node, OutputStream os) 
static void serialize(Node node, Writer w) 
static String serialize(String xmlString) 
static void serialize(String xmlString, OutputStream os) 
static void serialize(String xmlString, Writer w) 
static String serialize(Writable writable) //Return a pretty String version of the XML content produced by the Writable.
static void serialize(Writable writable, OutputStream os) 
static void serialize(Writable writable, Writer w) 

//In Groovy get , file is new File(...)
InputStream stream = file.newWriter()
InputStream stream = file.newOutputStream()
//then
stream.close()
//Or use as 
file.withOutputStream { OutputStream stream ->					// for binary file, stream is closed
           groovy.xml.XmlUtil.serialize(changedResponse, stream )
         }

         

///* Updating XML 
//XmlSlurper evaluates the structure lazily. 
//After Update, parse again 
//http://groovy-lang.org/processing-xml.html
//http://docs.groovy-lang.org/latest/html/api/groovy/xml/QName.html
//http://docs.groovy-lang.org/latest/html/api/groovy/util/Node.html

//Modifying a node 
def xml = """
<response version-api="2.0">
    <value>
        <books>
            <book id="2">
                <title>Don Xijote</title>
                <author id="1">Manuel De Cervantes</author>
            </book>
        </books>
    </value>
</response>
"""
//Note for Parser, you must use 'namespace:tag' if it has namespace, (check above)
def response = new XmlParser().parseText(xml)

/* Use the same syntax as groovy.xml.MarkupBuilder */
response.value.books.book[0].replaceNode{ 
   book(id:"3"){
       title( response.value.books.book[0].title.text())
       author(id:"3","Harper Lee")
   }
}
response.@numberOfResults = "2"  //adding one attribute 
response.value.books.book[0].appendNode(  //appending one node
    new groovy.xml.QName("http://literature.org", "numberOfResults", "lit"), //QName(String localPart) , QName(String namespaceURI, String localPart, String prefix) 
    [:],  //attributes as map 
    "1"  //value 
)
def str = groovy.xml.XmlUtil.serialize(response)  //get String value 

//Result 
 <?xml version="1.0" encoding="UTF-8"?><response version-api="2.0" numberOfResults="2">
  <value>
    <books>
      <book id="3">
        <title>Don Xijote</title>
        <author id="3">Harper Lee</author>
        <lit:numberOfResults xmlns:lit="http://literature.org">1</lit:numberOfResults>
      </book>
    </books>
  </value>
</response>

///With XmlSlurper , Note lazy!!!
import groovy.xml.*
def response = new XmlSlurper().parseText(xml)

/* Use the same syntax as groovy.xml.MarkupBuilder */
response.value.books.book[0].replaceNode{ 
   book(id:"3"){
       title( response.value.books.book[0].title.text())
       author(id:"3","Harper Lee")
   }
}
response.@numberOfResults = "2"  //adding one attribute 
response.value.books.book[0].appendNode(  //appending one node
    new groovy.xml.QName("http://literature.org", "numberOfResults", "lit"), //QName(String localPart) , QName(String namespaceURI, String localPart) 
    [:],  //attributes as map 
    "1"
)

/* That mkp is a special namespace used to escape away from the normal building mode
   of the builder and get access to helper markup methods
   'yield', 'pi', 'comment', 'out', 'namespaces', 'xmlDeclaration' and
   'yieldUnescaped' */
def result = new StreamingMarkupBuilder().bind { mkp.yield response }.toString()
def changedResponse = new XmlSlurper().parseText(result)
def str = groovy.xml.XmlUtil.serialize(changedResponse)

//Another example 
String doc = '''<entityProps>
               |  <candidate> <id>1</id><key></key> </candidate>
               |  <candidate> <id>2</id><key></key> </candidate>
               |  <candidate> <id>3</id><key></key> </candidate>
               |  <candidate> <id>4</id><key></key> </candidate>
               |</entityProps>'''.stripMargin()

String frag = '<candidate> <id>5</id><key></key> </candidate>'
def xml = new XmlSlurper().parseText( doc )
def fragxml = new XmlSlurper().parseText( frag )
xml.appendNode( fragxml )
String newDoc = new groovy.xml.StreamingMarkupBuilder().bind { mkp.yield xml }
println newDoc


///* Hands On - handson.xml 
Find al texts and ids from foo:company when id > 3 
Transform that to new xmlns bar='http://groovy.codehaus.org'
with same id and same text 
Update original xml for TCS, id = 35 
Add another foo:company with id 36, name MyCompany


//sol
def f = new File("handson.xml")

def root = new XmlParser().parse(f)

def texts = root.'foo:companies'.'foo:company'
        .findAll{ it.@id.toInteger() > 1}*.text()
def ids = root.'foo:companies'.'foo:company'
        .findAll{ it.@id.toInteger() > 1}*.@id
        
def sw = new StringWriter()
def b = new groovy.xml.MarkupBuilder(sw)
//Creating new 
b.'bar:companies'('xmlns:bar' : 'http://groovy.codehaus.org'){
    texts.eachWithIndex { e, i ->
        'bar:company'('id': ids[i], e)
    }
}

println sw
//Modifying 
root.'foo:companies'.'foo:company'[0].replaceNode{
    'foo:company'(id:'35', root.'foo:companies'.'foo:company'[0].text())
}
println groovy.xml.XmlUtil.serialize(root)
root.'foo:companies'[0].appendNode(
   "foo:company", [id:36], "My company"
)
println groovy.xml.XmlUtil.serialize(root)




///*** Groovy HTML 
//Traversing 
//XPATH and groovy equivalent 
/following-sibling::othernode       Look for a node "othernode" in the same level  .'*' or .children()
//                                  Look everywhere                                .'**' or .depthFirst(), can use breadthFirst()

//Use GPATH or *.  for collecting all subelements 
//use collect(), collectMany(), find() first element, findAll() for iterating 
// [index] for indexing if it is array of elements 


//for html , use 

@Grab(group='org.ccil.cowan.tagsoup', module='tagsoup', version='0.9.7')

def q = "http://www.imdb.com/find?q=The+Handmaid%27s+Tale&s=tt&exact=true&ref_=fn_tt_ex"

def bigText = q.toURL().getText(requestProperties: ['User-Agent': 'Non empty'])
def html = new XmlParser(new org.ccil.cowan.tagsoup.Parser()).parseText(bigText)
//for example all a under /html/body//a[contains(@href, '/title/tt')]
println html.body.'**'.a.@href.grep{ it =~  $//title/tt/$ }.unique()
//or complex way 
println html.body.'**'.tr.findAll{ it.@class?.contains("findResult") }
       .td.span.a.find{ it.@href =~ /title\/tt/ }.flatten()*.@href

//For example for //div[@id='foo']
//This would traverse all Nodes (all depth) one by one  from 'node' 
def foo = node.depthFirst().findAll { it.name() == 'div' && it.@id == 'foo'}
    
       

///*** Http rest api with HTTPBuilder 
//build.gradle 
compile 'io.github.http-builder-ng:http-builder-ng-core:1.0.3' 

//where CLIENT is replaced with the client library name (core, apache, or okhttp).
compile 'io.github.http-builder-ng:http-builder-ng-CLIENT:1.0.3'

//Example - GET 
@Grab('io.github.http-builder-ng:http-builder-ng-core:1.0.3')
import static groovyx.net.http.HttpBuilder.configure

def httpBin = configure {
    request.uri = 'http://httpbin.org/'
}

//now let's GET /get endpoint at httpbin.
//This will return a JSON formatted response with an origin property.
//Auto parsing of Json 
def result = httpBin.get {
    request.uri.path = '/get'
}
    
println("Your ip address is: ${result.origin}")
//println( JsonOutput.prettyPrint(response.json.toString()) )

//With query params 
def json = httpBin.get {
    request.uri.path = '/get'
    request.uri.query = [name: 'Bob']
}

///Request - verb()
//The no-argument method executes a request with the verb equivalent of the method name. 

@Grab('io.github.http-builder-ng:http-builder-ng-core:1.0.3')

import static groovyx.net.http.HttpBuilder.configure

def astros = configure {
    request.uri = 'http://api.open-notify.org/astros.json'
}.get()

println "There are ${astros.number} astronauts in space right now."

astros.people.each { p->
    println " - ${p.name} (${p.craft})"
}

///Request - verb(Closure)
//The configuration for the request will come from the merging of the client and request configurations. 

@Grab('io.github.http-builder-ng:http-builder-ng-core:1.0.3')

import static groovyx.net.http.HttpBuilder.configure

def iss = configure {
    request.uri = 'http://api.open-notify.org'
}.get {
    request.uri.path = '/iss-now.json'
}

println "The ISS is currently at lat:${iss.iss_position.latitude}, lon:${iss.iss_position.longitude}."

//Some default parsers are provided:
HTML (when either the 'org.jsoup:jsoup:' or 'net.sourceforge.nekohtml:nekohtml:' library is on the classpath),
JSON (when either Groovy or the com.fasterxml.jackson.core:jackson-databind:2.8.1 library is on the classpath)
CSV (when the com.opencsv:opencsv:3.8 library is on the classpath)
XML (without any additional libraries)
TEXT (without any additional libraries)



///ContentType
HttpBuilder.configure {
    request.uri = 'http://localhost:8675'
    request.contentType = 'text/plain'
    request.charset = Charsets.UTF_8
}.post {
    request.body = 'Let them eat content!'
}


////Sending Form Data (POST)
@Grab('io.github.http-builder-ng:http-builder-ng-core:1.0.3')
import static groovyx.net.http.HttpBuilder.configure
import static groovyx.net.http.ContentTypes.JSON
import groovyx.net.http.*
import static groovy.json.JsonOutput.prettyPrint

def result = configure {
    request.uri = 'http://httpbin.org'
    request.contentType = JSON[0] //application/json, http://javadox.com/org.codehaus.groovy.modules.http-builder/http-builder/0.6/groovyx/net/http/ContentType.html#JSON
}.post {
    request.uri.path = '/post'
    request.body = [id: '234545', label: 'something interesting']
    request.contentType = 'application/x-www-form-urlencoded'
    request.encoder 'application/x-www-form-urlencoded', NativeHandlers.Encoders.&form
}

println "You submitted: id=${result.form.id} and label=${result.form.id}"



///Scraping Web Content (GET)
@Grab('io.github.http-builder-ng:http-builder-ng-core:1.0.3')
@Grab('org.jsoup:jsoup:1.9.2')

import static groovyx.net.http.HttpBuilder.configure
import org.jsoup.nodes.Document

Document page = configure {
    request.uri = 'https://mvnrepository.com/artifact/org.codehaus.groovy/groovy-all'
}.get()

String license = page.select('span.b.lic').collect { it.text() }.join(', ')

println "Groovy is licensed under: ${license}"

///Download File (GET)
//groovyx.net.http.optional.Download class toFile(HttpConfig, File) 
File file = configure {
    request.uri = "http://example.org/download"
}.get {
    Download.toFile(delegate, saved)
}





     



///**** Groovy -  File Handling 
//Writing from GDK 
File file = new File('output.txt')
file.text = 'Just a text \n'    // closed automatically
file << 'NextLine\n'
file << 'AnotherLine from Groovy'

//Reading - from GDK 
file.readLines()
file.text           //slurp  , for binary, array of bytes= file.bytes
file.eachLine {
    line -> null
}

//finding max line 
File file = new File('output.txt')
file.readLines().withIndex().collect{ line, index -> 
      [index, line.size()]
      }.sort{kv -> kv[1] }[-1][0]
      
      
///Handson - total dir size
def filesList(dir) {
   def files = new File(dir).listFiles().findAll { it.isFile()}
   def dirs   = new File(dir).listFiles().findAll { it.isDirectory()}
   def subdirList = dirs.collectMany{ subdir ->  filesList(dir + "/" + subdir.name ) }
   files + subdirList
  }   
//gets list of File
def total = filesList(".").collect { it.length() }.sum()

///HandsOn - Count no of Words in a file
File file = new File('output.txt')
file.readLines()*.split(/\s+/).flatten().size()


///*Details - Reading from a file

def file = new File('access-ex.groovy')


String textContent = file.text					//slurping, Groovy closes the file automatically
file.readLines() 								//Array of lines
file.readLines().withIndex().each { line, index -> } 
file.readLine() 								//line

textContent = file.getText('UTF-8')              //slurping, Groovy closes the file automatically

file.eachLine { String line ->					//closed automatically
           println line
         }
file.eachLine('UTF-8') { String line ->					//closed automatically
           println line
         }

byte[] binaryContent = file.bytes				//binary contents, array of bytes, closes automatically		

file.eachByte { int b ->
      ...
    }

file.withReader { Reader reader ->					//reader is closed after closure, text file
          def firstLine = reader.readLine()
          println firstLine
}


file.withInputStream { InputStream stream ->		// stream is closed, binary fle
          def firstByte = stream.read()
          println firstByte
}


Reader reader = file.newReader()					// text file, reading, must close explicitly

InputStream stream = file.newInputStream()			// binary file, reading, must close explicitly


reader.readLine()   				//one line reading
reader.readLines()  				//list of lines
reader.eachLine { String line ->
        ...
      }
	  
String textContent = reader.text   //slurping

byte[] binaryContent = stream.bytes

reader.close()
stream.close()

//Processing every word in a text file
def file = new File('access-ex.groovy') 
int wordCount = 0
file.eachLine { String line ->
  line.tokenize().each { String word ->
    wordCount++
    println word
  }
}
println "Number of words: $wordCount"

//tokenize().each method is equivalent to split 
line.split(/[\s\t]+/).findAll{ it.trim() }.each { String word ->   //findAll only keeps non null, non false ones
  ...
}

//OR
int wordCount = 0
file.splitEachLine(/[\s\t]+/) { Collection words ->
  words.findAll{ it.trim() }.each { String word ->
    wordCount++
    println word
  }
}
println "Number of words: $wordCount"


//Writing to a file
File file = new File('output.txt')

file.text = 'Just a text'    // closed automatically

file.text = '''Whats in a name? That which we call a rose
        By any other name would smell as sweet.'''

file.bytes = [ 65, 66, 67, 68 ] as byte[]

file.append('What\'s in a name? That which we call a rose,\n')

file.append('By any other name would smell as sweet.')
 
file << 'What\'s in a name? That which we call a rose\n'
file << 'By any other name would smell as sweet.'

file.withWriter { Writer writer ->							// with writer, writer is closed, text file
           writer << 'What\'s in a name? That which we call a rose\n'  //'
           writer << 'By any other name would smell as sweet.'
}

file.withOutputStream { OutputStream stream ->					// for binary file, stream is closed
           stream << 'What\'s in a name? That which we call a rose\n'  //'
           stream << 'By any other name would smell as sweet.'
}

//can use  withDataOutputStream(File) , withObjectOutputStream(File) , withPrintWriter  (can call println)
//Can Use newWriter, newOutputStream, newDataOutputStream,and newObjectOutputStream. Must closed explicitly



//Replacing tabs with spaces in a text file  by using transformLine
def inputFile = new File('access-ex.groovy')
def outputFile = new File('output.txt')

outputFile.withWriter { Writer writer ->
           inputFile.withReader { Reader reader ->
             reader.transformLine(writer) { String line ->
               line.replaceAll('\t', ' ')
             }
           }
}

//OR
inputFile.withReader { reader ->
           reader.transformLine(outputFile.newWriter()) { line ->
             line.replaceAll('\t', ' ')
           }
}

//OR
outputFile.text = inputFile.text.replaceAll('\t', '                      ')  // whole file content is loaded in memory, hence might be problem


//with char
inputFile.withReader { Reader reader ->
      reader.transformChar(outputFile.newWriter()) { String chr ->
        chr == '\t' ? ' ' : chr
      }
}



//Filtering a text file's content
def inputFile = new File('access-ex.groovy')
def outputFile = new File('output.txt')

outputFile.withPrintWriter { writer ->              //println can be called
          inputFile.eachLine { line ->
            if (!line.startsWith('''//''')) {
              writer.println(line)
            }
          }
 }

//OR
outputFile.withWriter { writer ->
          inputFile.filterLine(writer) { line ->   // writer is closed
            !line.startsWith('''//''')
          }
}

  
//OR
inputFile.filterLine(outputFile.newWriter()) { line ->
  !line.startsWith('''//''')
}

//OR
outputFile << inputFile.filterLine { !it.startsWith('''//''') }




//Deleting a file or directory
//always need to check the result of the delete or deleteDir method.
def dir = new File('./tmp1')
def file = new File('./tmp2/test.txt')

println    file.deleteDir()     // false  , file is not dir
println    file.delete()		// true
println    dir.delete()			// false , can only delete empty dir
println    dir.deleteDir()		// true , can delete non empty dir


//Walking through a directory recursively
def currentDir = new File('.')

//java methods 
currentDir.listFiles().name     // listFiles() returns List of File, 
file.isFile() 					//true if file

// called on each file or dir
currentDir.eachFileRecurse { File file ->  // file or dir
           println file.name
}
		 
// Use File.eachFile and File.eachDir methods for first level iterations
// filter on only files
import groovy.io.FileType

currentDir.eachFileRecurse(FileType.FILES) { File file ->
          println file.name
}

//called on each dir only
currentDir.eachDirRecurse { File dir ->
          println dir.name
}


// OR using trampoline(), 
//trampoline method passes a copy of the parent closure to the nested eachFile call, making it recursive

currentDir.eachFile { File file ->
           println file.name
           if (file.isDirectory()) {
             file.eachFile( trampoline() )
           }
 }

// same as eachFileRecurse
currentDir.traverse { File file ->
      println file.name
    }


//Searching for files
def currentDir = new File('.')

// each file
currentDir.eachFileMatch(~/.*\.groovy/) { File file ->
  println file.name
}

// Use eachDirMatch for dir
currentDir.traverse { File file ->
          if (file.name.endsWith('.groovy')) {
            println file.name
          }
}

// or with Map version
currentDir.traverse(nameFilter: ~/.*\.groovy/)
          { File file ->
          println file.name
}

// Or complex filtering

import static groovy.io.FileType.*
currentDir.traverse(
          type: FILES,
          nameFilter: ~/.*\.groovy/,
          excludeNameFilter: ~/^C.*$/) { File file ->
            println file.name
 }

// or put action in visit parameter
import static groovy.io.FileType.*

currentDir.traverse(
          type:                     FILES,				// or FileType.DIRECTORIES
          nameFilter:               { it.matches(/.*\.groovy/) },  //matches() is ==~ hence it ==~ /.*\.groovy/  or it =~ /.*\.groovy/ since =~ creates a Matcher which returns true on boolean context of Groovy(which calls matchcer.find() internally)
          excludeNameFilter:        { it.matches(/^C.*$/)      },
          visit:                    { println it.name          }
)


// with file atrributes
def today = new Date()

currentDir.traverse(
  filter:        {
                    it.lastModified() < (today-5).time &&     // it is File
                    it.name.endsWith('.groovy')
                 },
  excludeFilter: { it.isDirectory() },
  visit:         { println it.name }
)

// prune/remove some directory tree branches by using the preDir (or postDir) closure parameters:
import static groovy.io.FileVisitResult.*

currentDir.traverse(
  preDir:            {
                               if (it.name == '.svn') {
                                 return SKIP_SUBTREE		//CONTINUE, SKIP_SIBLINGS,  TERMINATE
                               }
                       },
    nameFilter:        { it.matches(/.*\.groovy/) },
    excludeNameFilter: { it.matches(/^C.*$/)      },
    visit:             { println it.name          }
)

/*other useful options:
maxDepth
    It says how deep should we go in our search.
sort
    It is a closure that sets the sorting order in which files and directories will   be processed.
visitRoot
    It indicates that the visit closure should also be called for the target  search directory.
preRoot
    It indicates that the preDir closure should also be called for the target  search directory.
postRoot
    It indicates that postDir closure should also be called for the target  search directory.
*/



//Changing file attributes on Windows
f = new File('test.txt')
f << 'hello, this is a test file'

/*
$ attrib test.txt

A          I      C:\hello.txt
*/

// make read only
f.setReadOnly()

//To make a file "Hidden":
'attrib +H C:/hello.txt'.execute()

//to remove the "Hidden" attribute:
'attrib -H C:/hello.txt'.execute()



///* Execute a command
println 'ls -al'.execute().text

//OR Line by line
p = 'ls -al'.execute()
p.in.eachLine { line -> print line }   // in = output of execution,   out = sending string to execution, err = for error output
p.in.readLines()


//With NIO
import java.nio.file.*
import java.nio.file.attribute.*
def f = new File('hello.txt')

f << 'hello, hello'

def path = Paths.get('hello.txt')
def dosView = Files.getFileAttributeView(
                     path, DosFileAttributeView
                   )

dosView.hidden = true
dosView.archive = true
dosView.system = true
dosView.readOnly =true



//File modified Date 
//To get access to properties not supported by the Java File API we can parse the output of a 'dir' or 'ls' command:
def file = 'output.txt'
def cmd = ['cmd', '/c', 'dir', file, '/tc'].execute()
cmd.in.eachLine { line ->
    if (line.contains(file)) {
        def created = line.split()[0]
        println "$file is created on $created"
    }
} 










