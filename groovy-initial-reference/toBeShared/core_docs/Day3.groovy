"""
Day-3
    Groovy Operator overload
    SAM and Trait
    Categories
    Runtime metaprogramming 
    Groovy CSV 
    Groovy sql 
    groovy DSL 
    Groovy spring BeanBuilder 
    Groovy Thread 
"""


///**** Groovy Operator overload
//Operator  and it’s Method
+			a.plus(b)
a[b]		a.getAt(b)
-			a.minus(b)
a[b] = c	a.putAt(b, c)
*			a.multiply(b)
<<			a.leftShift(b)
/			a.div(b)
>>			a.rightShift(b)
%			a.mod(b)
++			a.next()
**			a.power(b)
--			a.previous()
|			a.or(b)
+a			a.positive()
&			a.and(b)
-a			a.negative()
^			a.xor(b)
~a			a.bitwiseNegative()
==			a.compareTo(b), if compareTo does not exist, calls a.equals(b)
!=			!a.equals(b)
>,<,>=.<=	a.compareTo(b) , must implements Comparable<T>
as 			a.asType(b)
in 			a.isCase(b)
<=>			a.compareTo(b)
()			a.call(b)
//Range Operator
..          any Comparable object that has next() 
..<         and previous() methods to determine the next / previous item in the range 


///Examples 
//For compareTo must implements Comparable<Operator>
class Operator implements Comparable<Operator>{
    def getAt(index) { println("o[index]")}
    def plus(other)  { println("o1 + o2")}
    def putAt(index,v) { println("o[index]=v")}
    def isCase(value) { println("in");true}
    def asType(Class t) { println("as")}
    int compareTo(other) { println("<=>"); 1} //return type should be int 
    def call(Integer...args ){ println("fn, args is list")}    
}

def o = new Operator()
def o2 = new Operator()
o <=> o2 
o[2]
o[3] = 2
o + o 
3 in o 
o as String 
o <=> o 
o(1,2,3)


//Example - User defined type 

@groovy.transform.Immutable  //can not have any userdefined ctor , same as Canonical
//@groovy.transform.Sortable 
class  MyInt implements Comparable<MyInt>{
    Integer val    
    def plus(o)  { new MyInt(this.val + o.val) }
    def asType(Class t){
        if(t == Integer){
            return new Integer(this.val)
        }
        if(t == Double){
         return val.toDouble()
        }
    }
    def isCase(def v){
        if (this.val == v.val)
            return true
         else
             return false 
    }
    int compareTo(MyInt o) {  this.val.compareTo(o.val) } //return type int is must 
    def next()  {  new MyInt(this.val + 1) }
    def previous() { new MyInt(this.val -1) }
}

//Usage 
def a = new MyInt(1)
def b = new MyInt(2)
assert new MyInt(3) == a + b
def ai =  a as Integer
println(a as Double) //2.0 
switch(a){
    case new MyInt(1):    //b.isCase(a)
        println("found")
        break;
    default:
        println("not found")
}
//++, -- and .. 
b = new MyInt(3)
a > b 
a == b 
def aa = ++a //prefix aa is stored after increment , postfix, aa is having old 
def bb = b-- 
(a..b).collect() // [MyInt(1), MyInt(2), MyInt(3)]



///* Unitesting with GroovyTestCase
class MayIntTest extends GroovyTestCase {

    // in Groovy Console, GroovyTestCase should be only class, then executes correctly
   	// While executing through gradle/outside, put this inside own class
      
    
    @groovy.transform.Immutable  //can not have any userdefined ctor , same as Canonical
    @groovy.transform.Sortable
    class  MyInt {
        Integer val    
        def plus(o)  { new MyInt(this.val + o.val) }
        def asType(Class t){
            if(t == Integer){
                return new Integer(this.val)
            }
            if(t == Double){
             return val.toDouble()
            }
        }
        def isCase(def v){
            if (this.val == v.val)
                return true
             else
                 return false 
        }
    }

   def i1, i2 

    void setUp() {
     i1 = new MyInt(2)
     i2 = new MyInt(3) 
     }

    void testAdd() {
     assert i1+i2 == new MyInt(5)
   }

   void testFail() {
    def numbers = [1,2,3,4]
    shouldFail {   
        numbers.get(4) //must raise exception
    }

 }
}


  
///HandsOn - Implement immutable Rational(n,d) 
@groovy.transform.Canonical
@groovy.transform.AutoClone
class MyRational{    
    int n
    int d   
    def minus(o){    
        def nr=((this.n)*d)-((o.n)*this.d)
        def dr=(this.d)*(o.d)
        return new MyRational(nr,dr)
    }    
    def asType(Class t){
        if(t==BigDecimal){
            return ((this.n))/((this.d)).toBigDecimal()       
        }
    }
}
def a= new MyRational(15,3)
def b= new MyRational(5,3)
def res=a-b
println "$res"
def res2= b as BigDecimal





///Few Points for ++ and -- 
//when the ++ operator is used on a reference, 
//it also reassigns that reference to the value returned by next()
int i = 0
i++
assert i == 1

int j = 0
j.next() // 1 is returned by next(), but not assigned to j, keeping its value unchanged
assert j == 0

//Like Java, the placement of ++ or -- before or after a reference determines 
//if the reassignment occurs before or after the operation is executed.
int i = 1
int j = ++i // i is incremented, then its value is assigned to j
int k = i++ // k is assigned the current value of i, then i is incremented
assert [i, j, k] == [3, 2, 2]

//True even for java.util.Date in the Groovy JDK.
def date = { int dayOfMonth -> new Date(2017 - 1900, Calendar.MAY, dayOfMonth).clearTime() }
Date d = date(1)
Date e = ++d
Date f = d++
assert [d, e, f].date == [3, 2, 2]

//Unlike Java, however, Groovy lets you use the operator on both sides of the reference simultaneously 
//(++a++); the corresponding method is called twice, 
//but not in chained succession
class Foo {
    int value
    def next() {
        println "next() called when value is $value"
        new Foo(value + 1)
    }
}

def f = new Foo(1)
++f++ // our wacky tandem usage of ++
println "The final value is $f.value"

//When executed, it prints:
next() called when value is 1
next() called when value is 1
The final value is 2


///*** Groovy - Nested Class, Abstract class ,Interfaces and Trait
//Abstract class and Interface are same as Java 

//abstract class can have constructor, interface - can not have constructor like java 
//use 'abstract' if method implementation is missing 

//Nested Class concept is same as Java except nested class behaves 
//like static nested class in Java 
//static  inner class fully supported 
class A {
    static class B {}
}

new A.B()
//But Syntax changed of inner class 
//java 
public class Y {
    public class X {}
    public X foo() {
        return new X();
    }
    public static X createX(Y y) {
        return y.new X();
    }
}
//groovy 
class Y {
    class X {}
    def X foo() {
        return new X()
    }
    static def X createX(Y y) {
        return new X(y)
    }
}

def y = new Y()
y.foo()
Y.createX(y)

//Anonymous class  by 'new interface() { ... } ', for trait not possible
import java.util.concurrent.*

Timer timer = new Timer()
timer.schedule(new TimerTask() {
    void run() {
        println("executed")
    }
}, 1000)  //ms delay 

//trait is like interface, can have default and abstract method 
//class 'extends' another concrete/abstract class (no multi inheritance)
//class always 'implements' trait/interface t1,t2,t3..., 
//trait 'extends' one trait or 'implements' t1,t2,i1,i2
//interface 'extends' i1,i2,i3 

trait Greet {
    abstract String name()
    def greet() { "hello ${name()}" }  //default 
}

class Person{ 
  String firstName
  String lastName
}

class PersonG extends Person implements Greet{
    String name() { "$firstName $lastName" }
}

def pg = new PersonG(firstName: "N", lastName:"Das")
println(pg.greet())

//Runtime trait mixing 
trait Name{
    String name() {  "$firstName $lastName" }    
}
pg = new Person(firstName: "N", lastName:"Das")
def pg1 = pg.withTraits Name, Greet 
println(pg1.greet())

///Conflicting methods 
trait GreetEsp {
    abstract String name()
    def greet() { "holla ${name()}" }
}
pg = new Person(firstName: "N", lastName:"Das")
def pg2 = pg.withTraits Name, GreetEsp, Greet //order matters, from Right side first 
println(pg2.greet())

println (pg2 instanceof Person) //false, withTraits created new class
println (pg2 instanceof Greet)  //true

//Anonymous class with Trait not possible unlike interface
//new Greet() {  String name() { "Das" } } .greet()

//but SAM coercion possible for trait or interface or abstract class having one abstract method 
def g1 = { "Das" } as Greet
g1.greet()

///multiple abstract methods can be overridden via MAP 
def g2 = [name: { "Das" } ] as Greet 
g2.greet()

///Stackable trait, use super.method to pass the method to next one 
trait Log {
    String greet() { 
         print(String.format("LOG %s>> ",this.toString())) //inside this means class's this          
         super.greet()
     }
}
println(pg.toString())
def pg3 = pg.withTraits Name, Greet,Log  //Person object got changed
println(pg3.greet())



///HandsOn - write a WithTime trait which would print Timings of the code 

trait Greet {
    abstract String name()
    def greet() { "hello ${name()}" }
}

class Person{ 
  String firstName
  String lastName
}

trait Name{
    String name() {  "$firstName $lastName" }    
}



trait withTime {
     String greet(){        
              def start = System.currentTimeMillis()
              def res = super.greet()
              def timeSpent = System.currentTimeMillis() - start
              println "TIME IS ~ ${timeSpent}ms"
              res
            }
}
def pg = new Person(firstName: "N", lastName:"Das")
def pg3 = pg.withTraits Name, Greet,withTime
println(pg3.greet("ABC"))



///*** Groovy -  SAM coercion 
//interface abstract class, trait with single abstract method 
interface Intf {
    public  greet(pre) 
}

abstract class Abs {
    public  abstract greet() 
}

def pi = { pre -> "$pre World" } as Intf
def pia = { -> "Hello World" } as Abs 
pi.greet("Hello")
pia.greet()


///for multiple methods- use Map coercion eg Userdefined Iterator 
//Iterable has iterator() and Iterator has next(), hasNext() methods 

def fibI
def (pr, nx) = [0, 1]
fibI = [ hasNext: { -> true }, next:{ -> (pr,nx) = [nx, pr+nx]; pr}] as Iterator
def c = 0
def list=[]
for (e in fibI){
    if (++c > 20) break
    list << e
}
print list 
//or
@Grab( 'com.bloidonia:groovy-stream:0.8.1' )
import groovy.stream.Stream

def fibI
def (pr, nx) = [0, 1]
fibI = [ hasNext: { -> true }, next:{ -> (pr,nx) = [nx, pr+nx]; pr}] as Iterator

Stream.from(fibI).drop(10).take(10).collect()

//or 
@Grab('com.netflix.rxjava:rxjava-groovy:0.20.7')
import rx.Observable

def fibI
def (pr, nx) = [0, 1]
fibI = [ hasNext: { -> true }, next:{ -> (pr,nx) = [nx, pr+nx]; pr}] as Iterator
fibIble = [iterator : { -> fibI } ] as Iterable 
def res = []
Observable.from(fibIble).take(5).subscribe{ res << it }
res





///*** Groovy builtin Patterns - Few examples 

///* Use @groovy.lang.Delegate to create delegate Pattern
•If multiple delegate fields are used and the same method signature occurs 
 in more than one of the respective field types, 
 then the delegate will be made to the first defined field having that signature. 
•By default, methods of the delegate type marked as @Deprecated 
 are not automatically added to the owner class , Use deprecated = true attribute for otherwise
•Static methods, synthetic methods or methods from the GroovyObject interface 
 are not candidates for delegation 
 They are not available in owner class
•All methods defined in the owner class (including static, abstract or private etc.) 
 take precedence over methods with identical signatures from a @Delegate field

//Example 
@groovy.transform.Canonical
class Event {
    @groovy.lang.Delegate Date when    // event.method(arg) , if Date.method() exists, then delegate when.method(args)
    String title
}

def ev = new Event(title:"D", when: Date.parse('yyyy/MM/dd', '2013/09/10'))
ev.time //1378751400000
def now = new Date()
ev.before(now) //true
groovy> ev.class.methods.each{println it} 
public void Event.setProperty(java.lang.String,java.lang.Object)
public java.lang.Object Event.getProperty(java.lang.String)
public boolean Event.equals(java.lang.Object)
public java.lang.String Event.toString()
public int Event.hashCode()
public int Event.compareTo(java.lang.Object)
public int Event.compareTo(java.util.Date)
public boolean Event.after(java.util.Date)
public boolean Event.before(java.util.Date)
public long Event.getTime()
public void Event.setTime(long)
public java.time.Instant Event.toInstant()
public java.lang.Object Event.invokeMethod(java.lang.String,java.lang.Object)
public void Event.setMetaClass(groovy.lang.MetaClass)
public groovy.lang.MetaClass Event.getMetaClass()
public java.util.Date Event.getWhen()
public java.lang.String Event.getTitle()
public java.lang.String Event.super$1$toString()
public boolean Event.canEqual(java.lang.Object)
public void Event.setWhen(java.util.Date)
public void Event.setTitle(java.lang.String)
public int Event.super$1$hashCode()
public boolean Event.super$1$equals(java.lang.Object)
public final void java.lang.Object.wait() throws java.lang.InterruptedException
public final void java.lang.Object.wait(long,int) throws java.lang.InterruptedException
public final native void java.lang.Object.wait(long) throws java.lang.InterruptedException
public final native java.lang.Class java.lang.Object.getClass()
public final native void java.lang.Object.notify()
public final native void java.lang.Object.notifyAll()



///* @groovy.transform.builder.Builder - to create building pattern

//builderStrategy		builderClassName	builderMethodName	    buildMethodName			prefix				includes/excludes
//chained setters
SimpleStrategy			n/a			 		n/a					    n/a						yes, default "set"	yes

//explicit builder class, class being built untouched
ExternalStrategy	    n/a 				n/a					    yes, default "build"	yes, default ""		yes

//creates a nested helper class
DefaultStrategy			yes, default <TypeName>Builder 
											yes, default "builder"	yes, default "build"    yes, default ""		yes
//creates a nested helper class providing type-safe fluent creation  
InitializerStrategy	    yes, default <TypeName>Initializer	
											yes, default "createInitializer"	
																	yes, default "create"   yes, default ""		yes
 

import groovy.transform.builder.*
//SimpleStrategy  - no builder class and no build method, default prefix 'set*'
//Can use the SimpleStrategy in conjunction with @Canonical. 
//SimpleSTratgey uses Canonical's includes/excludes
@Builder(builderStrategy=SimpleStrategy)
class Person {
    String first
    String last
    Integer born
}

def p1 = new Person().setFirst('Johnny').setLast('Depp').setBorn(1963)
assert "$p1.first $p1.last" == 'Johnny Depp'


//ExternalStrategy  - Used when original source code can not be annotated as it creates a separate builder class
//build method name - 'build', prefix - ""
//Builder class - separate 
//Options could be, includes=['first', 'last'], buildMethodName='create', prefix='with'
@groovy.transform.Canonical
class Person {
    String first
    String last
    int born
}

@Builder(builderStrategy=ExternalStrategy, forClass=Person)
class PersonBuilder { }

def p = new PersonBuilder().first('Johnny').last('Depp').born(1963).build()
assert "$p.first $p.last" == 'Johnny Depp'







///* Use @groovy.beans.Bindable to transform a property to a bound property 
//Bound property can generate event when changed 
//And any registered event listener would be notified 

@groovy.beans.Bindable  
    transforms a regular property into a bound property (according to the JavaBeans specification)
    The @Bindable annotation can be placed on a property or a class. 
    To convert all properties of a class into bound properties, one can annotate the class 
    //Property change interface 
    interface java.beans.PropertyChangeListener {
        void propertyChange(PropertyChangeEvent evt)  //This method gets called when a bound property is changed.
    }
    //PropertyChangeEvent method:
    Object getNewValue() 		
        Gets the new value for the property, expressed as an Object. 
    Object getOldValue() 		
        Gets the old value for the property, expressed as an Object.
    String getPropertyName() 	
        Gets the programmatic name of the property that was changed.
    //class when using @Bindable at class level or at property level would implement below methods automatically
    void addPropertyChangeListener(PropertyChangeListener listener) 
    void addPropertyChangeListener(String property_name, PropertyChangeListener listener) 
    void removePropertyChangeListener(PropertyChangeListener listener) 
    void removePropertyChangeListener(String property_name, PropertyChangeListener listener) 
    void firePropertyChange(String property_name, Object oldValue, Object newValue) 
    public PropertyChangeListener[] getPropertyChangeListeners() 
    PropertyChangeListener[] getPropertyChangeListeners(String property_name) 


//Example 
import groovy.beans.*
import java.beans.*

class BindableTest {     // Implements interface PropertyChangeListener, hence  propertyChange is available
  @Bindable String name 
 }

def sb = new BindableTest(name:"foo")
def changed = 0
def l = {evt ->    // PropertyChangeEvent
             println "${evt.propertyName}  ${evt.newValue}  ${evt.oldValue}"
             changed++
        } as java.beans.PropertyChangeListener    //SAM coercion to PropertyChangeListener.propertyChange

sb.addPropertyChangeListener(l)
//change bound proprty 
sb.name = "baz"
println changed   //1


///* Observable list, map and set  
//as application of @Bindable
//any registered event listener would be notified if Observable list, map and set are updated 

ObservableList,ObservableSet,ObservableMap 
    adds listener by addPropertyChangeListener(java.beans.PropertyChangeListener)
    listener triggers propertyChange(java.beans.PropertyChangeEvent evt) of PropertyChangeListener
    when elements (for list, set) or property (for map) are added, removed or changed. 
    //Property change interface 
    interface java.beans.PropertyChangeListener {
        void propertyChange(PropertyChangeEvent evt)  //This method gets called when a bound property is changed.
    }
groovy.util.ObservableList 
    Implements java.util.List, hence all List method can be called,
    Constructor
        ObservableList() 
        ObservableList(Closure test) 
        ObservableList(java.util.List delegate) 
        ObservableList(java.util.List delegate, Closure test) 
    An optional Closure may be specified and will work as a filter
    if it returns true the property will trigger an event (if the value indeed changed), otherwise it wonot
         // skip all properties whose value is a closure
         def map = new ObservableList( {!(it instanceof Closure)} )
         // skip all properties whose name matches a regex
         def map = new ObservableList( { name -> !(name =˜ /[A-Z+]/) } )
    adding an element to an observable list fires an ObservableList.ElementAddedEvent event.
    Other events 
        ObservableList.ElementRemovedEvent 
            an element is removed from the list
        ObservableList.ElementUpdatedEvent 
            an element changes value (same as regular PropertyChangeEvent)
        ObservableList.ElementClearedEvent 
            all elements have been removed from the list
        ObservableList.MultiElementAddedEvent 
            triggered by calling list.addAll()
        ObservableList.MultiElementRemovedEvent 
            triggered by calling list.removeAll()/list.retainAll()
    ObservableList.ElementEvent extends java.beans.PropertyChangeEvent 
    and Base class of all *Event class (eg ElementAddedEvent ..)
    ObservableList.ElementEvent methods:
        ObservableList.ChangeType getChangeType()  
        int getIndex()  
        int getType()  
        String getTypeAsString()  
        //PropertyChangeEvent method:
        Object getNewValue() 		
            Gets the new value for the property, expressed as an Object. 
        Object getOldValue() 		
            Gets the old value for the property, expressed as an Object.
        String getPropertyName() 	
            Gets the programmatic name of the property that was changed.
    ObservableList.ChangeType enums:
        ADDED  
        CLEARED  
        MULTI_ADD  
        MULTI_REMOVE  
        NONE  
        REMOVED  
        UPDATED  
    
groovy.util.ObservableMap
    Implements Map, hence all Map method can be called, 
    Constructor 
        ObservableMap() 
        ObservableMap(Closure test) 
        ObservableMap(java.util.Map delegate) 
        ObservableMap(java.util.Map delegate, Closure test) 
    An optional Closure may be specified and will work as a filter, 
    if it returns true the property will trigger an event (if the value indeed changed), otherwise it wonot. 
    The Closure may receive 1 or 2 parameters, the single one being the value, 
    the other one both the key and value
         // skip all properties whose value is a closure
         def map = new ObservableMap( {!(it instanceof Closure)} )
         // skip all properties whose name matches a regex
         def map = new ObservableMap( { name, value -> !(name =~ /[A-Z+]/) } )
    Events 
        ObservableMap.PropertyAddedEvent 
            a new property is added to the map
        ObservableMap.PropertyRemovedEvent 
            a property is removed from the map
        ObservableMap.PropertyUpdatedEvent 
            a property changes value (same as regular PropertyChangeEvent)
        ObservableMap.PropertyClearedEvent 
            all properties have been removed from the map
        ObservableMap.MultiPropertyEvent 
            triggered by calling map.putAll(), contains Added|Updated events
    ObservableMap.PropertyEvent extends java.beans.PropertyChangeEvent 
    and Base class of all *Event class (eg PropertyAddedEvent ..)
    ObservableMap.PropertyEvent methods:
        ObservableMap.ChangeType getChangeType()  
        int getType()  
        String getTypeAsString()  
        //PropertyChangeEvent method:
        Object getNewValue() 		
            Gets the new value for the property, expressed as an Object. 
        Object getOldValue() 		
            Gets the old value for the property, expressed as an Object.
        String getPropertyName() 	
            Gets the programmatic name of the property that was changed.
    ObservableMap.ChangeType enum:
        ADDED  
        CLEARED  
        MULTI  
        NONE  
        REMOVED  
        UPDATED  

ObservableSet
    Implements Set, hence all Set method can be called, 
    Constructor 
        ObservableSet() 
        ObservableSet(Closure test) 
        ObservableSet(java.util.Set<E> delegate) 
        ObservableSet(java.util.Set<E> delegate, Closure test) 
    An optional Closure may be specified and will work as a filter, 
    if it returns true the property will trigger an event (if the value indeed changed), otherwise it wonot. 
         // skip all properties whose value is a closure
         def set = new ObservableSet( {!(it instanceof Closure)} )
         // skip all properties whose name matches a regex
         def set = new ObservableSet( { name -> !(name =˜ /[A-Z+]/) } )
    Events 
        ObservableSet.ElementAddedEvent 
            a new element is added to the set
        ObservableSet.ElementRemovedEvent 
            an element is removed from the set
        ObservableSet.ElementUpdatedEvent 
            an element changes value (same as regular PropertyChangeEvent)
        ObservableSet.ElementClearedEvent 
            all elements have been removed from the list
        ObservableSet.MultiElementAddedEvent 
            triggered by calling set.addAll()
        ObservableSet.MultiElementRemovedEvent 
            triggered by calling set.removeAll()/set.retainAll()
    ObservableSet.ElementEvent extens from java.beans.PropertyChangeEvent and base class of all *Events. 
    Methods 
        ObservableSet.ChangeType getChangeType()  
        int getType()  
        String getTypeAsString()  
        //PropertyChangeEvent method:
        Object getNewValue() 		
            Gets the new value for the property, expressed as an Object. 
        Object getOldValue() 		
            Gets the old value for the property, expressed as an Object.
        String getPropertyName() 	
            Gets the programmatic name of the property that was changed.
    ObservableSet.ChangeType enum:
        ADDED  
        CLEARED  
        MULTI_ADD  
        MULTI_REMOVE  
        NONE  
        REMOVED  


///Examples 
// ObservableList.ElementAddedEvent:
def event                                       
def listener = {  it ->
                    if (it instanceof ObservableList.ElementEvent){   
                        event = it
                    }
                } as java.beans.PropertyChangeListener //SAM coercion of method  'propertyChange( PropertyChangeEvent it)'

def observable = [1, 2, 3] as ObservableList    
observable.addPropertyChangeListener(listener)  
observable.add 42        //or        observable << 42                 
assert event instanceof ObservableList.ElementAddedEvent

def elementAddedEvent = event as ObservableList.ElementAddedEvent
assert elementAddedEvent.changeType == ObservableList.ChangeType.ADDED
assert elementAddedEvent.index == 3
assert elementAddedEvent.oldValue == null
assert elementAddedEvent.newValue == 42
println elementAddedEvent  //groovy.util.ObservableList$ElementAddedEvent[propertyName=content; oldValue=null; newValue=42; propagationId=null; source=groovy.util.ObservableList@e93e9]


// ObservableList.ElementClearedEvent - triggered when clear() is executed. It holds all old values
observable.clear()
assert event instanceof ObservableList.ElementClearedEvent
def elementClearedEvent = event as ObservableList.ElementClearedEvent
assert elementClearedEvent.values == [1, 2, 3]
assert observable.size() == 0
println elementClearedEvent  





///*** Groovy - Categories 
//are implemented with category classes. Builtin class also can be extended
//To use them , use 'use' method
//Category classes are either
1. Extension class  ie a static class with static method with first arg as class_which_is_extended
2. or use @Catagory(class_which_is_extended)  annotation

//Builtin Standard Catagory
    groovy.time.TimeCategory
    groovy.servlet.ServletCategory
    groovy.xml.dom.DOMCategory
//Example of groovy.time.TimeCategory
use(groovy.time.TimeCategory)  {
    println 1.minute.from.now       //TimeCategory adds methods to Integer , Date
    println 10.hours.ago
    def someDate = new Date()       
    println someDate - 3.months
}

///User defined Extension class 
class Distance {
    def number
    String toString() { "${number}m" }
}

@Category(Number)					   //  Number is to be extended
class NumberCategory {
    Distance getMeters() {			   //Args : method's argument, return type = based on requirement
        new Distance(number: this)     //access own's instance by 'this'
    }
}

//OR
class NumberCategory {								//must be static method always
    static Distance getMeters(Number self) {		//First argument defines the Class which is extended(here Number), return type and other args are from requirement	
        new Distance(number: self)    				//access own's instance by 'self' first argument
    }
}

use (NumberCategory)  {
    assert 42.meters.toString() == '42m'
}

///HandsOn - Extended Integer class with triple method 
class TripleCategory {
    public static Integer triple(Integer self) {		//Integers
        3*self
    }
}
//OR
@Category(Integer)			// target type Integer
class TripleCategory {
    public Integer triple() { 3*this }	// access own by this
}
//Usage 
use (TripleCategory) {
    assert 9 == 3.triple()
}


///*** Groovy - Global Extension Module 
//Extending existing classes- are used to add new methods to existing classes
//These are available globally. 
//Unlike categories, extension modules are compatible with type checking

Builtin Standard extension method(GDK)
    DateGroovyMethods, DefaultGroovyMethods, IOGroovyMethods, NioGroovyMethods, 
    PluginDefaultGroovyMethods, ProcessGroovyMethods, ResourceGroovyMethods, 
    SocketGroovyMethods, StringGroovyMethods
    
//example of few builtin Modules 
//Downloading content from the Internet
def   outputFile = new File('image.png')
def   baseUrl = 'http://groovy.codehaus.org'
def   imagePath = '/images/groovy-logo-medium.png'
def   url = new URL("${baseUrl}${imagePath}")
outputFile.delete()
url.withInputStream { inputStream ->    // close automatically
  outputFile << inputStream
}    
//OR to a text file
def outputFile = new File('groovy.html')
def baseUrl = 'http://groovy.codehaus.org'
def url = new URL(baseUrl)
// Saving textual content.
outputFile.delete()
outputFile.text = url.text

//Using httpclient (to get full access to REST)
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

///User defined Extention modules 
Extension Module Setup
    extension module classes and descriptor must be available on classpath 
    either provide the classes and module descriptor directly on classpath
    or bundle extension module into a jar for re usability
    Note extensionClasses and staticExtensionClasses are comma seperated class names
    *STEP-1
        Module descriptor
        //file:META-INF/services/org.codehaus.groovy.runtime.ExtensionModule:
        //under src/main/resources 
        moduleName=Test module for specifications
        moduleVersion=1.0-test
        extensionClasses=support.Example
        staticExtensionClasses=support.StaticStringExtension
    *STEP-2 
        Write Example and support.StaticStringExtension files
        //src/main/groovy/support/StaticStringExtension.groovy
        package support
        class StaticStringExtension {                     //must be static method always
            static String greeting(String self) {         //First argument defines the Class which is extended(here String), return type and other args are as per requirement 
                'Hello, world!'							  //you can access own String instance via self
            }
        }
        //src/main/groovy/support/Example.groovy
        package support 
        public class Example {									//must be static method always
          public static Integer getNameSize(File self)  {  		//First argument defines the Class which is extended(here File)
            return self.name.size()								//you can access own File instance via self, can call any File methods on self
          }
        }

//Usage  - EXM contains all extension.groovy files as well META-INF. Must be in class path
$ gradle jar 
//Get the <<jar file location>> 
//Usage:
//Test.groovy:
def file = new File("groovysh.bat")
def s = file.getNameSize()
println s
println String.greeting()  // Static method
//Execute 
$ groovy -cp <<jar file location>> Test.groovy


///HandsOn: Call code till a max number of times 
//MaxRetriesExtension.groovy:
class MaxRetriesExtension {                                     
    static void maxRetries(Integer self, Closure code) {        // maxRetries for Integer
        int retries = 0
        Throwable e = null
        while (retries<self) {				      				// box/unbox automatically
            try {
                code.call()
                break
            } catch (Throwable err) {
                e = err
                retries++
            }
        }

        if (e) {
            throw e
        }
    }
}
//To quickly check in groovy console, use category syntax ie 'use' 
//use( MaxRetriesExtension)  {
    int i=0
    5.maxRetries {
        i++
    }
    println i    //1
    i=0
    try {
     5.maxRetries {
            i++
            throw new RuntimeException("oops")
        }
    } catch (RuntimeException e) {
        println i   //5
    }

//}  //for use



///HandsOn:For Date, get MonthName 
//src/main/groovy/examples/DateExtentions.groovy 
package examples

import static java.util.Calendar.*
import java.text.DateFormatSymbols

class DateExtensions {
    static String getMonthName(Date date) {
         def dfs = new DateFormatSymbols()
         dfs.months[date[MONTH]]
    }
}
//src/main/resources/META-INF/services/org.codehaus.groovy.runtime
moduleName=groovy-extension
moduleVersion=1.0
extensionClasses=org.groovy.cookbook.DateExtensions
staticExtensionClasses=

//Build  
$ gradle clean build

//Usage
//Test.groovy:
def now = new Date()
println now.monthName
//execute
$ groovy -cp ./build/libs/* Test.groovy                  //*/











///***Groovy - Dynamic or meta Programming 

///* Dynamic programming with Expando 
def expando = new Expando()
expando.name = "das"
println(expando.name)

///* POJO and POGO 
POJO 
    A regular Java object whose class can be written in Java or any other language for the JVM.

POGO 
    A Groovy object whose class is written in Groovy. 
    It extends java.lang.Object and implements the groovy.lang.GroovyObject interface by default.
    package groovy.lang;
    public interface GroovyObject {
        //It is also invoked when the method called is not present on a Groovy object. 
        Object invokeMethod(String name, Object args);
        //Every read access to a property can be intercepted by overriding the getProperty() method of the current object
        Object getProperty(String propertyName);
        void setProperty(String propertyName, Object newValue);
        MetaClass getMetaClass();
        void setMetaClass(MetaClass metaClass);
    }
    
GroovyObject.invokeMethod
    This method is primarily intended to be used in conjunction with the GroovyInterceptable interface 
    or an object’s MetaClass where it will intercept all method calls.
    Note when class implements GroovyInterceptable, its invokeMethod is called 
    for defined or undefined methods 
    If class does not implement GroovyInterceptable, then its invokeMethod
    is only called for undefined method ,In this case , it is clear if one 
    uses methodMissing
    //example 
    class SomeGroovyClass {
        def invokeMethod(String name, Object args) {
            return "called invokeMethod $name $args"
        }
        def test() {
            return 'method exists'
        }
    }
    def someGroovyClass = new SomeGroovyClass()
    assert someGroovyClass.test() == 'method exists'
    assert someGroovyClass.someMethod() == 'called invokeMethod someMethod []'

GroovyObject.getProperty
    Every read access to a property can be intercepted by overriding 
    the getProperty() method of the current object. 
    Use propertyMissing if you want to hook undefined property 
    //Example 
    class SomeGroovyClass {
        def property1 = 'ha'
        def field2 = 'ho'
        def field4 = 'hu'
        def getField1() {
            return 'getHa'
        }
        def getProperty(String name) {
            if (name != 'field3')
                return metaClass.getProperty(this, name) 
            else
                return 'field3'
        }
    }
    def someGroovyClass = new SomeGroovyClass()
    assert someGroovyClass.field1 == 'getHa'
    assert someGroovyClass.field2 == 'ho'
    assert someGroovyClass.field3 == 'field3'
    assert someGroovyClass.field4 == 'hu'

GroovyObject.setProperty
    You can intercept write access to properties by overriding the setProperty() method:
    //Example 
    class POGO {
        String property
        void setProperty(String name, Object value) {
            this.@"$name" = 'overridden'
        }
    }
    def pogo = new POGO()
    pogo.property = 'a'
    assert pogo.property == 'overridden'

    
org.codehaus.groovy.runtime.DefaultGroovyMethods
    This class defines new groovy methods which appear on normal JDK classes inside the Groovy environment. 
    These methods(eg println) are injected by Groovy metaClass
    Check for all methods for java.lang.Object at  http://groovy-lang.org/gdk.html    

    
Groovy Interceptor 
    The groovy.lang.GroovyInterceptable interface is marker interface 
    that extends GroovyObject and is used to notify the Groovy runtime 
    that all methods should be intercepted through the method dispatcher mechanism 
    of the Groovy runtime.
    //Example   
    //Can not default groovy methods like println because these methods are injected into all Groovy objects 
    class Interception implements GroovyInterceptable {
        def definedMethod() { }
        def invokeMethod(String name, Object args) {
            'invokedMethod'
        }
    }
    //Testing 
    class InterceptableTest extends GroovyTestCase {

        void testCheckInterception() {
            def interception = new Interception()

            assert interception.definedMethod() == 'invokedMethod'
            assert interception.someMethod() == 'invokedMethod'
        }
    }
    //To intercept all method calls but do not want to implement the GroovyInterceptable interface 
    //we can implement invokeMethod() on an object’s MetaClass    
    class InterceptionThroughMetaClassTest extends GroovyTestCase {

        void testPOJOMetaClassInterception() {
            String invoking = 'ha'
            invoking.metaClass.invokeMethod = { String name, Object args ->
                'invoked'
            }

            assert invoking.length() == 'invoked'
            assert invoking.someMethod() == 'invoked'
        }

        void testPOGOMetaClassInterception() {
            Entity entity = new Entity('Hello')
            entity.metaClass.invokeMethod = { String name, Object args ->
                'invoked'
            }

            assert entity.build(new Object()) == 'invoked'
            assert entity.someMethod() == 'invoked'
        }
    }




///* Meta Programming with class or with instance using 'metaClass' 
//Dynamically a class can be extended with new method, overloaded method, static methods, property and constructors 
//Always use 'delegate' inside metaClass closure which holds a 'instance' of that class 

//check - getMetaClass in GroovyObject interface 
GroovyObject.methods.name
GroovyObject.getMetaClass()
//or 
GroovyObject.metaClass

//Example 
@groovy.transform.Canonical
class Person{ 
  String firstName
  String lastName
}

//def pmg = new Person("N", "Das") //old instance not impacted
//always use delegate inside explicitely, else would go to Owner which is this Script 

//Note << for append (for creating overloaded method), = for replacing (for creating new methods)

//If instance is given , then these extension methods are only applicable for that instance 
def pmg = new Person("N", "Das")
pmg.metaClass.fullName << {String pre ->
        "$pre ${delegate.firstName} ${delegate.lastName}"
        }
pmg.fullName()
def pmg1 = new Person("N", "Das")
pmg1.fullName() //Error 

//If class is given, then applicable for all instanced getting created from now on 
//person.fullName(String)
Person.metaClass.fullName << {String pre ->
        "$pre ${delegate.firstName} ${delegate.lastName}"
        }
//can be overloaded
Person.metaClass.fullName << {int pre ->
        "${pre} ${delegate.firstName} ${delegate.lastName}"
        }
        
def pm = new Person("N", "Das")        
println(pm.fullName("Hello"))
//static method 
Person.metaClass.'static'.hello << { ->
        "hello"
        }
println(Person.hello())



//Possible for constructor, property as well ,
//Note << for append , = for replacing 
//If instance is given , then these extension methods are only applicable for that instance 
MyClass_or_Instance.metaClass.constructor << { String arg -> }
MyClass_or_Instance.metaClass.constructor = { String arg -> }
MyClass_or_Instance.metaClass.myProperty = "blah"     //property name is 'myProperty' with value blah 
MyClass.metaClass.'static'.myProperty = "blah"

//can use DSL syntax 
Person.metaClass {
   hl  { -> "hello $delegate.lastName" }
   hf  { -> "hello $delegate.firstName" }
}
def pmd = new Person("N", "Das")       
println(pmd.hl() + " " +  pmd.hf())




///* Category and metaClass 
@Category(Person)
class Who{
    def whoAmI() { "Who: ${this.firstName} ${this.lastName}" }
}

class Status{
  public static String st(Person s){
      "Status: $s.firstName  $s.lastName"
  }
}
//this works with earlier defined instance as well 
use(Who, Status){
  println(pmd.whoAmI() + " " + pmd.st())
}



///* Category class with mixing 
Person.metaClass.mixin Who, Status  //groovy.lang.ExpandoMetaClass
def pmd = new Person("N", "Das")    //not applicable for earlier defined instances 
println(pmd.whoAmI() + " " + pmd.st()) //old class



///* Category class with instance level also possible 
@Category(Person)
class Talk{
    def talk() { "Talk: ${this.firstName} ${this.lastName}" }
}
def pmd1 = new Person("N", "Das")
pmd1.metaClass.mixin Talk 
//println(pmd.talk())  //Error 
println(pmd1.talk())   //OK


///* Extending builtin class via metaClass 
//always use delegate inside explicitely to mean 'this', else would go to Owner which is this Script 
ExpandoMetaClass.enableGlobally()  
List.metaClass.sizeDoubled = {-> delegate.size() * 2 }  //delegate is List instance

def list = []
list << 1
list << 2
assert 4 == list.sizeDoubled()

///HandsOn - freq method 
ArrayList.metaClass.freq = { ->
  delegate.collectEntries{ e -> [e, delegate.count{ e1 -> e == e1 }] }
}

def l = [1,2,3,1,3,3]

l.freq()


///* Dynamic handling of methods and properties 

methodMissing(String name, def args) 
    is invoked  when no method can be found for the given name and/or the given arguments. 
    Note methodMissing is searched at first, if it does not exists, invokeMethod is called
    If you add methodMissing and propertyMissing on metaClass.'static', 
    it can deal with static methods and properties 
    //Example 
    class Foo {
       def methodMissing(String name, def args) {  //args is list 
            return "this is me"
       }
       def invokeMethod(String name, args) { 
           return name
       }
    }
    //or 
    Foo.metaClass.methodMissing << {name, args ->
        "this is me"
    } 
    //usage 
    assert new Foo().someUnknownMethod(42l) == 'this is me'


propertyMissing 
    is called- when property search is failed, 
    for getter, it takes one String name argument, 
    for setter, it takes two, name and value
    //Example 
    class Foo {
       def storage = [:]
       def propertyMissing(String name, value) { storage[name] = value }  // called for setter
       def propertyMissing(String name) { storage[name] }			// called for getter
    }
    //usage 
    def f = new Foo()
    f.foo = "bar"
    assert f.foo == "bar"

$static_methodMissing
    Static variant of methodMissing method can be implemented at the class level with $static_methodMissing method.
    class Foo {
        static def $static_methodMissing(String name, Object args) {
            return "Missing static method name is $name"
        }
    }
    assert Foo.bar() == 'Missing static method name is bar'

$static_propertyMissing
    Static variant of propertyMissing method can be implemented at the class level with $static_propertyMissing method.
    class Foo {
        static def $static_propertyMissing(String name) {
            return "Missing static property name is $name"
        }
    }
    assert Foo.foobar == 'Missing static property name is foobar'


metaClass.getAttribute 
metaClass.setAttribute
    In the default implementation you can access fields without invoking their getters 
    //Example 
    class SomeGroovyClass {
        def field1 = 'ha'
        def field2 = 'ho'
        def getField1() {
            return 'getHa'
        }
    }
    def someGroovyClass = new SomeGroovyClass()
    assert someGroovyClass.metaClass.getAttribute(someGroovyClass, 'field1') == 'ha'
    assert someGroovyClass.metaClass.getAttribute(someGroovyClass, 'field2') == 'ho'
    //Example 
    class POGO {
        private String field
        String property1
        void setProperty1(String property1) {
            this.property1 = "setProperty1"
        }
    }
    def pogo = new POGO()
    pogo.metaClass.setAttribute(pogo, 'field', 'ha')
    pogo.metaClass.setAttribute(pogo, 'property1', 'ho')
    assert pogo.field == 'ha'
    assert pogo.property1 == 'ho'





///**** Groovy -  DSL - Various techniques


///* Technique-1: using Map and Closure 

//Command chains  
// a b c d ==== a(b).c(d)
//a = closure  with arg b returns Map with key c ie ['c':another_closure]
//value of c = closure taking arg d and returning final value
//Note instead of Map, it could be a class with method 'c(d)'
//Or Class with methodMissing(name, args) are setup 

//Note advantage of closure: inside last closure , all external variables are captured 
//To get the same effect with Class, all these should be construtor parameters 

//For example- turn(left).then(right) ie turn(left) returns ['then': {right ->}]
turn left then right

//For example- take(2.pills).of(chloroquinine).after(6.hours)
take 2.pills of chloroquinine after 6.hours

//For example- paint(wall).with(red, green).and(yellow)
paint wall with red, green and yellow

// with named parameters too
//For example- check(that: margarita).tastes(good)
check that: margarita tastes good

// with closures as parameters
//For example- given({}).when({}).then({})
given { } when { } then { }

//For example-select(all).unique().from(names)
select all unique() from names

//For example- take(3).cookies
// and also this: take(3).getCookies()
take 3 cookies




///Example 
//please(show).the(square_root).of(100)
//closures are show, square_root and value of 'of' 
//Maps are ['the':..], ['of':..]
//Note inside closure of 'of', all external variables are captured 

import java.Math.*

show = { println it }
square_root = { Math.sqrt(it) }

def please(displayFn) {
    [the: {actionFn ->
            [of: {val->displayFn(actionFn(val)) }]
        }
    ]
}
//Usage 
please show the square_root of 100                    // ==> 10.0



///handsOn 
please add(2,3) then minus with 10

//above means 
please(add(2,3)).then(minus).with(10)

def add = { Object ...args -> args.inject(0){ r,e -> r+e } }
def minus = { x, y -> x-y }

def please(add_value) {
    [then: {actionFn ->
            [with: { val->actionFn(add_value, val) }]
        }
    ]
}



///* Technique-2:  Setting up Closure delegate with a class 
//Then inside closure, all class methods can be accessed 
//Note class methods can take another closure and above steps could be repeated 
//Note class can have methodMissing,propertyMissing as well 


void sendEmail() {
    email {                                //email(Closure)
        from 'das@mycompany.com'           //Closure.delegate = Someclass , use Closure.rehydrate(Object delegate, Object owner, Object thisObject) method 
        to 'somebody@waitaminute.com'      //set Closure.resolveStrategy = DELEGATE_ONLY such that only delegate is searched 
        subject 'Groovy is good'           //from, to, subject, body are methods of that class taking single argument
        header  "X-USER=OK","Y-USER=NOK"   //header is hitting to methodMissing 
        body "Hello there"
    }
}

def email( Closure code){
    def esp = new EmailSpec()
    def nc = code.rehydrate(esp, null, null)  // Copy a Closure with new delegate, owner, this by  rehydrate(Object delegate, Object owner, Object thisObject)
    nc.resolveStrategy = Closure.DELEGATE_ONLY
    nc()
}

class EmailSpec {
    void from(String from) { println "From: $from"}
    void to(String to) { println "To: $to"}
    void subject(String subject) { println "Subject: $subject"}
    void body(String text) { println text }
    def methodMissing(String methodName, args) {
            println("${methodName.toUpperCase()}  ${args.join(",")}")
    }
}

sendEmail()




///Handson: Implement Below 

void sendEmail() {
    email {                                  //email(Closure)
        from 'das@mycompany.com'             // from, to, subject, body are methods taking single argument
        to 'somebody@waitaminute.com'        
        subject 'Groovy is good'             
        header  "X-USER=OK","Y-USER=NOK"     //header is hitting to methodMissing 
        body {                               //body(Closure)
            header()                         
            p 'Yes, it is good , learn it'   // p is method taking single argument
            h1  'Ok'
            footer()
        }
    }
}







//Implementation 
def email( Closure code){
    def esp = new EmailSpec()
    def nc = code.rehydrate(esp, null, null)  // Copy a Closure with new delegate, owner, this by  rehydrate(Object delegate, Object owner, Object thisObject)
    nc.resolveStrategy = Closure.DELEGATE_ONLY
    nc()
}

class EmailSpec {
    void from(String from) { println "From: $from"}
    void to(String to) { println "To: $to"}
    void subject(String subject) { println "Subject: $subject"}
    void body(Closure body) {
        def bodySpec = new BodySpec()
        def code = body.rehydrate(bodySpec,null, null)
        code.resolveStrategy = Closure.DELEGATE_ONLY
        code()
    }
    def methodMissing(String methodName, args) {
            println("${methodName.toUpperCase()}  ${args.join(",")}")
    }
}

class BodySpec {
        def header() { println "<html><body>" }
        def methodMissing(String methodName, args) { 
            println("<${methodName}>  ${args.join(",")}</${methodName}>")
        }
        def footer(){ println "</body></html>" }
}
sendEmail()



///*** Groovy CSV 

class com.xlson.groovycsv.CsvParser
    java.util.Iterator 	parse(java.util.Map args = [:], java.lang.String csv)
        Parses the csv supplied using the reader.
        Returns an instance of com.xlson.groovycsv.CsvIterator
            void 	close()
                Closes the underlying reader object.
            boolean 	hasNext()
                Checks if there is more data available.
            boolean 	isClosed()
                Checks if the underlying reader is closed.
            java.lang.Object 	next()
                Gets the next row in the csv file.
            void 	remove()
                remove is not supported in CsvIterator.
    java.util.Iterator 	parse(java.util.Map args = [:], java.io.Reader reader)
        Parses the supplied csv and returns a CsvIterator that can be use to access the data.
    static java.util.Iterator 	parseCsv(java.util.Map args = [:], java.lang.String csv)
        Parses a string as csv in the same way as CsvParser.parse(...)
        CsvIterator has the dynamic handling of header as an attribute 
    static java.util.Iterator 	parseCsv(java.util.Map args = [:], java.io.Reader reader)
        Parses a reader as csv in the same way as CsvParser.parse(...)
         Arguments for configuration:
            separator: configures the separator character to use (default: ,)
            quoteChar: configures the quote character to use (default: ")   //"
            escapeChar: configures the escape character for the separator and quoteChar (default:\)
            autoDetect: sets up autodetect that will honor other configurations you have done (default: false)
            columnNames: set custom column names instead of using the first line
            readFirstLine: reads the first line as csv instead of using it as headers
            trimWhitespaceFromColumnNames: trims leading and trailing whitespace for column names when parsing them (default: false)
            skipLines: skips the specified number of lines at the beginning (default: 0) 

//without header 
@Grab('com.xlson.groovycsv:groovycsv:1.3')
import com.xlson.groovycsv.CsvParser
def csv = '''Apple,2
Pear,5'''

def data = new CsvParser().parse(csv, readFirstLine:true,
                                 columnNames:['fruit', 'qty'])
for(line in data) {
    println "$line.fruit ${line[1]}"
}

//With header 
@Grab('com.xlson.groovycsv:groovycsv:1.3')
import static com.xlson.groovycsv.CsvParser.parseCsv
def csv = '''ID,Product
3,Shoe
1,Table'''

def data = parseCsv(csv)
for(line in data) {
    println "ID=$line.ID, Product=$line.Product"
}

//Example iris.csv 
@Grab('com.xlson.groovycsv:groovycsv:1.3')
import static com.xlson.groovycsv.CsvParser.parseCsv
import com.xlson.groovycsv.CsvIterator


//for(line in parseCsv(new FileReader('D:/PPT/Groovy/data/iris.csv'))) {
//    println "$line.SepalLength,$line.SepalWidth,$line.PetalLength,$line.PetalWidth,$line.Name"
//}

@groovy.transform.Canonical     //toString, hash, equals, tuple constructor 
@groovy.transform.Sortable       //compareTo and compatorByFIELD based on Order 
@groovy.transform.ToString(includeNames=true)
@groovy.transform.AutoClone
class Iris implements Serializable{
    double sl
    double sw
    double pl 
    double pw 
    String name 
    def toList() {
        return [this.sl, this.sw, this.pl, this.pw , this.name]
    }
}

class CsvIteratorToIrisIterator {                                
    static Iterator toIris (CsvIterator self) {        
        return [hasNext: self.&hasNext, next: { 
            def line = self.next() 
            new Iris(line.SepalLength.toDouble(), line.SepalWidth.toDouble(), line.PetalLength.toDouble(), line.PetalWidth.toDouble(), line.Name)        
        }] as Iterator 
    }
}
def csviterator = parseCsv(new File('data/iris.csv').newReader())
def csvlist
use (CsvIteratorToIrisIterator)  {
   csvlist =csviterator.toIris().toList()
}
csvlist.each{println it}

csvlist.map{it.name}.toSet()
csvlist.collect{it.name}.toSet().each{println it} //uique name 
csvlist.findAll{it.name == "Iris-setosa"}.collect{it.sl}.max()
csvlist.groupBy{it.name}.collectEntries{k,v -> [k, ['max': v.collect{it.sl}.max()]] }.each{println it} 



///*** Database Access with Groovy - Groovy SQL 

/* for MySQL 
@GrabConfig(systemClassLoader=true)
@Grab('mysql:mysql-connector-java:5.1.33')
import com.mysql.jdbc.Driver
import groovy.sql.Sql 
def sql = Sql.newInstance('jdbc:mysql://localhost:3306/groovy', 'root', '', 'com.mysql.jdbc.Driver')  
*/

@Grapes([
 @Grab(group='org.xerial',module='sqlite-jdbc',version='3.7.2'),
 @GrabConfig(systemClassLoader=true)
]) 
import java.sql.*
import org.sqlite.SQLite
import groovy.sql.Sql

def sql = Sql.newInstance("jdbc:sqlite:iris.db", "org.sqlite.JDBC") //D:/PPT/Groovy/iris.db

sql.execute("drop table if exists IRIS")
sql.execute '''
     create table IRIS (
         sl double, sw double, pl double, pw double, name string
     )
 '''
//csvlist.each{println it}
csvlist.each{ row -> sql.execute('insert into IRIS values (?, ?, ?,?,?)', row.toList())}

// a query using rows:  
def rows = sql.rows("select name, max(sl) from IRIS group by name") //[ [],[], ..]
def rows2 = rows.collectEntries{map -> [map.name, map['max(sl)']]}
println(rows2)
sql.close()



//Other funtionalities 
//insert a row 
def map = [id:20, name:'Grails', url:'http://grails.codehaus.org']
sql.execute "insert into PROJECT (id, name, url) values ($map.id, $map.name, $map.url)"
 
//a row update:  
def newUrl = 'http://grails.org'
def project = 'Grails'
sql.executeUpdate "update PROJECT set url=$newUrl where name=$project"
 
//query using eachRow:  
println 'Some GR8 projects:'
sql.eachRow('select * from PROJECT') { row ->  //row is map with column as key 
     println "${row.name.padRight(10)} ($row.url)"
 }
 
// a query using rows:  
def rows = sql.rows("select * from PROJECT where name like 'Gr%'") //[ [],[], ..]
assert rows.size() == 2
println rows.join('\n')

sql.close()



///*** Groovy spring BeanBuilder 

///* Spring with Groovy  -Using Grails bean- builder 
//http://docs.grails.org/3.3.5/api/grails/spring/BeanBuilder.html

import grails.spring.BeanBuilder
import groovy.transform.ToString

class Login {
    def authorize(User user){
        if(user.credentials.username == "John" && user.credentials.password == "Doe"){
            "${user.greetings} ${user.credentials.username}"
        }else
            "You are not ${user.greetings}"
    }
}

@ToString(includeNames=true)
class Credentials{
    String username
    String password
}

@ToString(includeNames=true)
class User{
    Credentials credentials
    String greetings
}

def bb = new BeanBuilder()
         
 //Create two instances= login, user 
//all Bean DSL can be used 
//eg bean.autowire = 'byName', extbean=ref('extBean') etc 
bb.beans {
    login(Login)
    user(User){ bean ->
        credentials = new Credentials(username:"John", password:"Doe")
        greetings = 'Welcome!!'
    }

}

def ctx = bb.createApplicationContext()

def u = ctx.getBean("user")
println u

def l = ctx.getBean("login")
println l.authorize(u)


///*** Groovy Threads - use Thread.start(Closure)

Thread.start {
     println Thread.currentThread().name + " started"
   }
   
//or passing an argument 
def worker = { arg -> println Thread.currentThread().name + " started with $arg" }

Thread.start worker.curry(10)  //curry returns another closure 


///* Thread - With join
def thread = Thread.start {
  sleep(2000)
  println "new thread"
}
thread.join()
println "old thread"    // Output is  new thread , old thread


///Catching Exceptions with an Exception Handler
def th = Thread.start {
    println 'start'
    println Thread.currentThread()
    sleep 1000
    throw new NullPointerException()
}
th.setDefaultUncaughtExceptionHandler({t, ex ->
        println 'ignoring: ' + ex.class.name
    } as Thread.UncaughtExceptionHandler )

th.join()





///* Thread - With Lock
import java.util.concurrent.locks.ReentrantLock 
 
def startTime = System.currentTimeMillis()
 
//withLock(Closure)
ReentrantLock.metaClass.withLock = {     // create withLock Method
  delegate.lock()									//calls ReentrantLock's instance.lock()
  try {
    it()
  }
  finally {
      delegate.unlock()
  }
}
 
def lock = new ReentrantLock()
 
def worker = { threadNum ->
  4.times { count ->
    lock.withLock {
      print " " * threadNum
      print "." * (count + 1)
      println " ${System.currentTimeMillis() - startTime}" 
    }
    Thread.sleep(100);
  }
}
 
5.times {
  Thread.start worker.curry(it)
}
 






///* Thread Pooling and future

import java.util.concurrent.*


def myClosure = {num -> println "I Love Groovy ${num}"; num * num}
List<Future> futures

def threadPool = Executors.newFixedThreadPool(4)
try {
   futures = (1..10).collect{ num->
          threadPool.submit ( {->  myClosure num } as Callable)
	   } 
} finally {
  threadPool.shutdown()
}


def results = futures.collect{it.get()}  //it=Future //blocks 


// for submitting task that would not return anything
import java.util.concurrent.*
def worker = { num -> println "I Love Groovy ${num}"}
def tasks = []

ExecutorService pool = Executors.newFixedThreadPool(6); 
for( i in 1..10 ) { 
    pool.execute worker.curry(i)
} 
pool.shutdown();


///* Using @groovy.transform.Synchronized 
//synchronized access to a method 

import java.util.concurrent.atomic.AtomicInteger 

def counter = new AtomicInteger() 

@groovy.transform.Synchronized 
def out(message) { 
 println(message) 
} 


def th = Thread.start { 
 for( i in 1..8 ) { 
     sleep 30 
     out "thread loop $i" 
     counter.incrementAndGet() 
   } 
  
  for( j in 1..4 ) { 
    sleep 50 
    out "main loop $j" 
    counter.incrementAndGet() 
  }
}  
th.join() 
assert counter.get() == 12

///* @groovy.transform.WithReadLock and @groovy.transform.WithWriteLock 
//provide read/write synchronization using the ReentrantReadWriteLock facility 
//The annotation can be added to a method or a static method. 


import groovy.transform.WithReadLock
import groovy.transform.WithWriteLock

class Counters {
    public final Map<String,Integer> map = [:].withDefault { 0 }

    @WithReadLock
    int get(String id) {
        map.get(id)
    }

    @WithWriteLock
    void add(String id, int num) {
        Thread.sleep(200) 				// emulate long computation
        map.put(id, map.get(id) + num)
    }
}

def cnt = new Counters()
Random random = new Random()

 
5.times { x ->
  Thread.start  {       
      cnt.add(x.toString(), random.nextInt(100))      
   }

  Thread.start  {
      2.times { 
          println "${Thread.currentThread().name }   ${cnt.get(x.toString())}"
      }
   }
}

///* @groovy.transform.ThreadInterrupt
//Allows "interrupt-safe" executions of scripts by adding Thread.currentThread().isInterrupted() 
//checks into loops (for, while, each) and at the start of closures and methods

//Note that in JVM world , a thread can’t be stopped. 
//The Thread#stop method exists but is deprecated (and isn’t reliable) 

//Options	thrown		java.lang.InterruptedException		
//Specifies the type of exception which is thrown if the thread is interrupted.



@groovy.transform.ThreadInterrupt
class MyClass {
   def myMethod() {
     for (i in (1..10)) {
       println 'executing method...'
     }
   }
 }

def worker = { num ->
   num.times {     
     println 'executing script method...'
     new MyClass().myMethod()
     Thread.sleep(1000)
   }
 }

def test = Thread.start  worker.curry(10)
Thread.sleep(500)
test.interrupt()    	// java.lang.InterruptedException: sleep interrupted






///* @groovy.transform.TimedInterrupt 	
//instead of checking the interrupt flag of the thread, 
//it will automatically throw an exception if the thread has been running for too long.

// @TimedInterrupt is currently not compatible with static methods!  
//Note TimeUnit's enum - DAYS  ,HOURS  ,MICROSECONDS  ,MILLISECONDS  ,MINUTES  ,NANOSECONDS ,SECONDS  

//Options	thrown		java.util.concurrent.TimeoutException 		
//Specifies the type of exception which is thrown if timeout is reached. 


import java.util.concurrent.*

@groovy.transform.TimedInterrupt(value=1L, unit=TimeUnit.MILLISECONDS)
class MyClass {
    def fib(int n) {
        n<2?n:fib(n-1)+fib(n-2)
    }
}


new MyClass().fib(30)        //java.util.concurrent.TimeoutException: Execution timed out after 1 units






///* @groovy.transform.ConditionalInterrupt 		
//uses custom stratgey for interruption, 
//Condition checking is inserted at method/closure/each loop

//Note ConditionalInterrupt must be first call in the script. else 'property not found' error is thrown

//Options	thrown		java.lang.InterruptedException		
//Specifies the type of exception which is thrown if execution should be aborted. 


@ConditionalInterrupt({ counter == false})   // breaks when this condition is true
import groovy.transform.ConditionalInterrupt

counter = true				// this must be without any type
def scriptMethod() {
      10.times {			
          println 'executing script method...'          
          if (it == 5) counter = false
      }
 }

scriptMethod()                    //thrown java.lang.InterruptedException: Execution interrupted








///********************* Advanced ***************/

///*** Groovy DSL applications - Some useful builders 

///* swing - building GUI 
import groovy.swing.SwingBuilder
import java.awt.BorderLayout as BL

count = 0
new SwingBuilder().edt {
  frame(title: 'Frame', size: [300, 300], show: true) {
    borderLayout()
    textlabel = label(text: 'Click the button!', constraints: BL.NORTH)
    button(text:'Click Me',
         actionPerformed: {count++; textlabel.text = "Clicked ${count} time(s)."; println "clicked"}, constraints:BL.SOUTH)
  }
}


//re-use via a closure.

import groovy.swing.SwingBuilder
import javax.swing.*
import java.awt.*

def swing = new SwingBuilder()

def sharedPanel = {
     swing.panel() {
        label("Shared Panel")
    }
}

count = 0
swing.edt {
    frame(title: 'Frame', defaultCloseOperation: JFrame.EXIT_ON_CLOSE, pack: true, show: true) {
        vbox {
            textlabel = label('Click the button!')
            button(
                text: 'Click Me',
                actionPerformed: {
                    count++
                    textlabel.text = "Clicked ${count} time(s)."
                    println "Clicked!"
                }
            )
            widget(sharedPanel())
            widget(sharedPanel())
        }
    }
}

//Example - relies on observable beans and binding:

import groovy.swing.SwingBuilder
import groovy.beans.Bindable

class MyModel {
   @Bindable int count = 0
}

def model = new MyModel()
new SwingBuilder().edt {
  frame(title: 'Java Frame', size: [100, 100], locationRelativeTo: null, show: true) {
    gridLayout(cols: 1, rows: 2)
    label(text: bind(source: model, sourceProperty: 'count', converter: { v ->  v? "Clicked $v times": ''}))
    button('Click me!', actionPerformed: { model.count++ })
  }
}


///* NodeBuilder
//NodeBuilder is used for creating nested trees of java.util.Node objects for handling arbitrary data. 


def nodeBuilder = new groovy.util.NodeBuilder()
def userlist = nodeBuilder.userlist {
    user(id: '1', firstname: 'John', lastname: 'Smith') {
        address(type: 'home', street: '1 Main St.', city: 'Springfield', state: 'MA', zip: '12345')
        address(type: 'work', street: '2 South St.', city: 'Boston', state: 'MA', zip: '98765')
    }
    user(id: '2', firstname: 'Alice', lastname: 'Doe')
}

//by using GPath expressions:
assert userlist.user.@firstname.join(', ') == 'John, Alice'
assert userlist.user.find { it.@lastname == 'Smith' }.address.size() == 2





///* Ant - Ant scripts execution 

def ant = new groovy.util.AntBuilder()
ant.zip(destfile: 'sources.zip', basedir: 'src')

// print 
ant.echo("hello")

// here is an example of a block of Ant inside GroovyMarkup
ant.sequential {
    echo("inside sequential")
    def myDir = "target/AntTest/"
    mkdir(dir: myDir)
    copy(todir: myDir) {
        fileset(dir: "src/test") {
            include(name: "**/*.groovy")   //Ant pattern, **/ means - any depth directory
        }
    }
    echo("done")
}


///* cli - command line option parsing 
// specify parameters
def cli = new groovy.util.CliBuilder(usage: 'groovy Greeter [option]') 
cli.a(longOpt: 'audience', args: 1, 'greeting audience')   //specify  -a parameter taking a single argument with an optional long variant --audience
cli.h(longOpt: 'help', 'display usage')                    //last parameter is help text           

// parse and process parameters
def options = cli.parse(args)                              
if (options.h) cli.usage()                                 
else println "Hello ${options.a ? options.a : 'World'}"    



///* POJO graph building - bulding POJO class on the fly 

package com.acme

class Company {
    String name
    Address address
    List<Employee> employees = []
}

class Address {
    String line1
    String line2
    int zip
    String state
}

class Employee {
    String name
    String id 
    Address address
    Company company
}


def builder = new groovy.util.ObjectGraphBuilder(classLoader: getClass().classLoader)                      
//builder.classNameResolver = "com.acme"                         

def acme = builder.company(name: 'ACME', address: null) {                     
    3.times {
        employee(id: it.toString(), name: "Drone $it") {       
            address(line1:"Post street")                       
        }
    }
}

assert acme != null
assert acme instanceof Company
assert acme.name == 'ACME'
assert acme.employees.size() == 3
def employee = acme.employees[0]
assert employee instanceof Employee
assert acme.employees[0].name == 'Drone 0'
assert employee.address instanceof Address


//Nother example 
class School {
    String name
    List<Course> courses = []
}
 
class Course {
    String name
    Teacher teacher
    List<Student> students = []
}
 
class Teacher { String name }
 
class Student { String name }
 
def builder = new ObjectGraphBuilder(classLoader: getClass().classLoader) // Need classLoader when in Groovy console.
def firstSchool = builder.school(name: 'First School') {
    course(name: 'Math') {
        teacher(name: 'Matt')
        student(name: 'Mary', id: 'Mary')  // id attribute allows us to reference it later in the builder.
        student(name: 'John', id: 'John')
        student(name: 'Rose', id: 'Rose')
    }
    course(name: 'English') {
        teacher(name: 'Emily', id: 'Emily')
        student(refId: 'Mary')  // refId refers to id attribute we set earlier.
        student(name: 'Alex')
        student(refId: 'Rose')
    }
    course(name: 'Java') {
        teacher(refId: 'Emily')
        student(name: 'mrhaki')
        student(refId: 'John')
        student(refId: 'Mary')
    }   
}
 
assert 'First School' == firstSchool.name
assert 3 == firstSchool.courses.size()
assert 'Math' == firstSchool.courses[0].name
assert 'Matt' == firstSchool.courses[0].teacher.name
assert 3 == firstSchool.courses[0].students.size()
assert ['English', 'Java'] == firstSchool.courses.findAll{ it.teacher.name == 'Emily' }.name
assert 'mrhaki' == firstSchool.courses[2].students.find { it.name == 'mrhaki' }.name
assert 'Mary' == firstSchool.courses.find{ it.name == 'Java' }.students.find{ it.name == 'Mary' }.name



///*file tree builder - Creating nested dirs 

tmpDir = File.createTempDir()
def fileTreeBuilder = new groovy.util.FileTreeBuilder(tmpDir)
fileTreeBuilder.dir('src') {
    dir('main') {
       dir('groovy') {
          file('Foo.groovy', 'println "Hello"')
       }
    }
    dir('test') {
       dir('groovy') {
          file('FooTest.groovy', 'class FooTest extends GroovyTestCase {}')
       }
    }
 }
 
 
///*** Groovy - Printing CLASSPATH in groovyConsole by classLoder.uRls

def printClassPath(classLoader) {
  println "$classLoader"
  classLoader.getURLs().each {url->
     println "- ${url.toString()}"
  }
  if (classLoader.parent) {
     printClassPath(classLoader.parent)
  }
}
printClassPath this.class.classLoader




///*** Groovy - Groovy Time, Date
java.util package
    Date  			
        Represents a discrete point in time  
    Calendar  		
        This is the parent class for calendars and has some useful constants and a static factory for creating calendars. Calendars know about fields like month, day, year and time.  
    DateFormat  	
        Used for parsing and generating string versions of dates and times  
Goorvy Enhancements:
    TimeCategory  		
        Allows you to write human friendly date manipulation expressions  


//Using Groovy's TimeCategory by 'use'
now = new Date()
println now
use(groovy.time.TimeCategory) {
    footballPractice = now + 1.week - 4.days + 2.hours - 3.seconds
}
println footballPractice



//FUrther examples 
import groovy.time.*

// Define period of 2 years, 3 months, 15 days, 0 hours, 23 minutes, 2 seconds and 0 milliseconds.
def period = new DatumDependentDuration(2, 3, 15, 0, 23, 2, 0)
assert '2 years, 3 months, 15 days, 23 minutes, 2.000 seconds' == period.toString()

def year2000 = new Date(100, 0, 0)  // Jan 1, 2000
assert 'Mon Apr 15 00:23:02 IST 2002' == (period + year2000).toString()

// Define time period of 5 hours, 54 minutes and 30 milliseconds.
def time = new TimeDuration(5, 54, 0, 30)
assert '5 hours, 54 minutes, 0.030 seconds' == time.toString()

use (TimeCategory) {
    assert period.toString() == (2.years + 3.months + 15.days + 0.hour + 23.minutes + 2.seconds).toString()
    assert time.toString() == (5.hours + 54.minutes + 30.milliseconds).toString()

    // We can use period.from.now syntax.    
    def d1 = 1.week - 1.day
    def d2 = new Date() + 6.days
    assert d2.format('yyyy-MM-dd') == d1.from.now.toString()
    
    // We can use period.ago syntax.
    def d3 = 3.days.ago
    def d4 = new Date() - 3
    assert d4.format('yyyy-MM-dd') == d3.toString()
}

use ( TimeCategory ) {
     // application on numbers:
     println 1.minute.from.now
     println 10.hours.ago

     // application on dates
     def someDate = new Date()
     println someDate - 3.months
 }


///With Groovy 1.6 and above you can also use the mixin notation, then no need to use 'use'
import groovy.time.*

Integer.metaClass.mixin TimeCategory
Date.metaClass.mixin TimeCategory

footballPractice = 1.week.from.now - 4.days + 2.hours - 3.seconds
println footballPractice




///Java : To get milliseconds, nanosecond
println System.currentTimeMillis()
println System.nanoTime()



///Java+Groovy: Using Timers
java.util.Timer
    Timer()
        Creates a new timer.
    Timer(boolean isDaemon)
        Creates a new timer whose associated thread may be specified to run as a daemon.
    Timer(String name)
        Creates a new timer whose associated thread has the specified name.
    Timer(String name, boolean isDaemon)
    void 	cancel()
        Terminates this timer, discarding any currently scheduled tasks.
    int 	purge()
        Removes all cancelled tasks from this timers task queue.
    void 	schedule(TimerTask task, Date time)
        Schedules the specified task for execution at the specified time.
    void 	schedule(TimerTask task, Date firstTime, long period)
        Schedules the specified task for repeated fixed-delay execution, beginning at the specified time.
    void 	schedule(TimerTask task, long delay)
        Schedules the specified task for execution after the specified delay.
    void 	schedule(TimerTask task, long delay, long period)
        Schedules the specified task for repeated fixed-delay execution, beginning after the specified delay.
    void 	scheduleAtFixedRate(TimerTask task, Date firstTime, long period)
        Schedules the specified task for repeated fixed-rate execution, beginning at the specified time.
    void 	scheduleAtFixedRate(TimerTask task, long delay, long period)
        Schedules the specified task for repeated fixed-rate execution, beginning after the specified delay.
    //Groovy
    TimerTask 	runAfter(int delay, Closure closure)
        This timer will execute the given closure after the given delay(in millis)
java.util.TimerTask extends Object implements Runnable
    boolean 	cancel()
        Cancels this timer task.
    abstract void 	run()
        The action to be performed by this timer task.
    long 	scheduledExecutionTime()
        Returns the scheduled execution time of the most recent actual execution of this task.
//Example 
import static java.util.Calendar.*
cal = Calendar.instance

//void 	roll(int field, int amount)
//Adds a signed amount to the specified calendar field without changing larger fields.
cal.roll SECOND, 1
def timer = new Timer(true)
def task = { println 'Background Task Done' }
timer.schedule task as TimerTask, cal.time
Thread.sleep(10)
//Example 
def timer = new Timer()
def task = timer.runAfter(10000) {
    println "Actually executed at ${new Date()}."
}
println "Current date is ${new Date()}. Task is executed at ${new Date(task.scheduledExecutionTime())}."

//Example 
// File: newtimer.groovy
class GroovyTimerTask extends TimerTask {
    Closure closure
    void run() {
        closure()
    }
}
 
class TimerMethods {
    static TimerTask runEvery(Timer timer, long delay, long period, Closure codeToRun) {
        TimerTask task = new GroovyTimerTask(closure: codeToRun)
        timer.schedule task, delay, period
        task
    }
}
 
use (TimerMethods) {
    def timer = new Timer()
    def task = timer.runEvery(1000, 5000) {
        println "Task executed at ${new Date()}."
    }
    println "Current date is ${new Date()}."
}


///Creating date instances, use Date()  only for now instance, for any other Use Calender
def today = new Date()
println today
def year2000 = new Date(100, 0, 0)  // Jan 1, 2000 //Date(int year, int month, int date)


//Creating calendar instances
def christmas = new GregorianCalendar(2008, Calendar.DECEMBER, 25)
println christmas.time				//java.util.Date


//Converting between calendar and dates and milliseconds
now = Calendar.instance
println 'now is a ' + now.class.name
date = now.time							//java.util.Date
println 'date is a ' + date.class.name + ' with value ' + date
millis = date.time						//Long
println 'millis is a ' + millis.class.name + ' with value ' + millis



//From millis to Date using Date(milis)
now = Calendar.instance
millis = 0
date = new Date(millis)
now.time = date

println now.time



//Manipulating calendar and dates,  by + and - 
def newyears = new GregorianCalendar(2009, Calendar.JANUARY, 1)
def christmas = new GregorianCalendar(2008, Calendar.DECEMBER, 25)


def newYearsEve = newyears.time - 1
println newYearsEve

def boxingDay = christmas.time + 1
println boxingDay

// or normal Java arithmetic to convert milliseconds to days
def daysBetween = (newYearsEve.time - boxingDay.time) / (24 * 60 * 60 * 1000)
println daysBetween



//OR Using index operations, 
//AM PM AM_PM DATE DAY_OF_MONTH  DAY_OF_WEEK DAY_OF_WEEK_IN_MONTH 
//DAY_OF_YEAR DST_OFFSET ERA HOUR HOUR_OF_DAY MILLISECOND MINUTE MONTH SECOND WEEK_OF_YEAR WEEK_OF_MONTH  YEAR ZONE_OFFSET 

import static java.util.Calendar.*

def nowCal = Calendar.instance
y = nowCal.get(YEAR)

Date nowDate = nowCal.time
m = nowDate[MONTH] + 1          //0 based
d = nowDate[DATE]
println "Today is $d $m $y"

nowCal.set DATE, 1
nowCal.set MONTH, FEBRUARY
println "Changed to $nowCal.time"


//or with tuple assignments
import static java.util.Calendar.*

cal = Calendar.instance
cal.set 1988, APRIL, 4, 0, 0, 0
date = cal.time

def (doy, woy, y) = [DAY_OF_YEAR, WEEK_OF_YEAR, YEAR].collect{ date[it] }
println "$date is day $doy and week $woy of year $y"


//Or with range
def today = new Date()
def holidays = boxingDay..newYearsEve

println holidays.size()
println today in holidays
println holidays.collect{ it.toString()[0] }  //[F, S, S, M, T, W]



//Writing dates as strings by DateFormat and SimpleDateFormat:
import java.text.DateFormat

def today = new Date()
def plainFormatter = DateFormat.instance

println plainFormatter.format(today)

//with locale
import static java.text.DateFormat.*
import static java.util.Locale.*

def newyears = new GregorianCalendar(2009, Calendar.JANUARY, 1)

[ITALY, FRANCE, GERMAN].each { loc ->
    println getDateInstance(FULL, loc).format(newyears.time)
}


//SimpleDataFormat in combination with some additional calendar methods for adjusting dates:
import java.text.SimpleDateFormat
printCal = {cal -> new SimpleDateFormat().format(cal.time)}

cal = Calendar.instance
cal.set 2000, JANUARY, 1, 00, 01, 0
assert printCal(cal) == '1/01/00 00:01'

// roll minute back by 2 but don't adjust other fields
cal.roll MINUTE, -2
assert printCal(cal) == '1/01/00 00:59'

// adjust hour back 1 and adjust other fields if needed
cal.add HOUR, -1
assert printCal(cal) == '31/12/99 23:59'


//Creating dates from strings by DateFormat or SimpleDateFormat:
Date d2 = new java.text.SimpleDateFormat("yyyy-MMM-dd").parse("2008-DEC-25")
println d2











///*** Groovy - Parallel working using gpars 
//Check for variaous options in http://www.gpars.org/guide/guide/dataParallelism.html
/*
Note if the error is
Unsupported major.minor version 52.0
That means , Module's required Java versions is higher than in the system, use lower version module
*/
/*
For error SLF4J: Failed to load class “org.slf4j.impl.StaticLoggerBinder”
add below
@Grab('org.slf4j:slf4j-api:1.7.5'), 
@Grab('org.slf4j:slf4j-simple:1.7.5')

If Grab download fails for any Jar
remove corresponding dir in
.m2/repository/xyz.abc...
.groovy/grapes/xyz.abc..

*/

//Example 
@Grab('org.codehaus.gpars:gpars:1.2.1')
import static groovyx.gpars.GParsPool.*


def rnd = new Random()
def number = rnd.nextInt(10) + 17401


def libraryUrl = 'http://www.gutenberg.org/cache/epub/'
def bookFile = "${number}/pg${number}.txt"
def bigText = "${libraryUrl}${bookFile}".toURL()
println bigText
words = bigText.text.tokenize()


//Filter in parallel
withPool {
            words.findAllParallel { token ->
              token.length() > 10 && !token.startsWith('http')
            }.size()
}



//Print some words  with each parallel
withPool {
            words.eachParallel { token ->
              if (token.length() > 10 &&
              !token.startsWith('http')) {
                println token
              }
            }
}


// Check every parallel, 10 threads 
withPool(10) {
            assert !(words.everyParallel { token ->
               token.length() > 20
            })
}


//Work in parallel
withPool {
            println words
            .findAllParallel { it.length() > 10 &&   !it.startsWith('http') }
            .groupByParallel { it.length() }  //[length:[items,items...]]
            .collectParallel { "WORD LENGTH ${it.key}: " +  it.value*.toLowerCase().unique().size() }
}

//Outputs
//http://www.gutenberg.org/cache/epub/17409/pg17409.txt
//[WORD LENGTH 22: 4, WORD LENGTH 12: 202, WORD LENGTH 20: 3, WORD LENGTH 68: 1, WORD LENGTH 13: 117, WORD LENGTH 19: 10, WORD LENGTH 28: 1, WORD LENGTH 18: 7, WORD LENGTH 36: 1, WORD LENGTH 17: 14, WORD LENGTH 23: 1, WORD LENGTH 16: 20, WORD LENGTH 15: 36, WORD LENGTH 21: 1, WORD LENGTH 14: 50, WORD LENGTH 11: 318]

///Type of Pools , Both have the same usage patterns 
GParsPool 
    the JSR-166y based concurrent collection processor
    The following methods are currently supported on all objects in Groovy:
        eachParallel()
        eachWithIndexParallel()
        collectParallel()
        collectManyParallel()
        findAllParallel()
        findAnyParallel
        findParallel()
        everyParallel()
        anyParallel()
        grepParallel()
        groupByParallel()
        foldParallel()
        minParallel()
        maxParallel()
        sumParallel()
        splitParallel()
        countParallel()
        foldParallel()
GParsExecutorsPool 
    the Java Executors based concurrent collection processor
    The following methods on all objects, which support iterations in Groovy, are currently supported:
        eachParallel()
        eachWithIndexParallel()
        collectParallel()
        findAllParallel()
        findParallel()
        allParallel()
        anyParallel()
        grepParallel()
        groupByParallel()
Map-Reduce
    Turn a collection into a Parallel Array(.parallel) and back by (.collection)
    Faster if there is method chain 
    This feature is only available when using in the Fork/Join-based GParsPool , not in GParsExecutorsPool .
    Below methods are available on .parallel     
        map()
        reduce()
        filter()
        size()
        sum()
        min()
        max()
        sort()
        groupBy()
        combine()
Parallel Arrays
    As an alternative, the efficient tree-based data structures defines in JSR-166y can be used directly. 
    The parallelArray property on any collection or object will return 
    a jsr166y.forkjoin.ParallelArray instance holding the elements of the original collection, 
    which then can be manipulated through the jsr166y API. 


        
//Example 
//multiply numbers asynchronously
 GParsPool.withPool {
     final List result = [1, 2, 3, 4, 5].collectParallel {it * 2}
     assert ([2, 4, 6, 8, 10].equals(result))
 }

GParsExecutorsPool.withPool {
     Collection<Future> result = [1, 2, 3, 4, 5].collectParallel{it * 10}
     assert new HashSet([10, 20, 30, 40, 50]) == new HashSet((Collection)result*.get())
 }
//multiply numbers asynchronously using an asynchronous closure
GParsExecutorsPool.withPool {
 def closure={it * 10}
 def asyncClosure=closure.async()
 Collection<Future> result = [1, 2, 3, 4, 5].collect(asyncClosure)
 assert new HashSet([10, 20, 30, 40, 50]) == new HashSet((Collection)result*.get())
}
  
  
//Map-reduce variants 
import static groovyx.gpars.GParsPool.withPool

def words = "This is just a plain text to count words in"
print count(words)

def count(arg) {
  withPool {
    return arg.parallel
      .map{[it, 1]}
      .groupBy{it[0]}.getParallel() //.key, .value from Entry
      .map {it.value=it.value.size();it}
      .sort{-it.value}.collection
  }
}

//Or with general combine operation:
//The combine operation expects on its input a list of tuples (two-element lists) considered to be key-value pairs (such as [key1, value1, key2, value2, key1, value3, key3, value4 … ] ) with potentially repeating keys
//combine merges the values for identical keys using the provided accumulator function and produces a map mapping the original (unique) keys to their accumulated values.
def words = "This is just a plain text to count words in"
print count(words)

def count(arg) {
  withPool {
    return arg.parallel
      .map{[it, 1]}
      .combine(0) {sum, value -> sum + value}.getParallel() //initial=0, accumulator function= sum,value ->...
      .sort{-it.value}.collection
  }
}

    
//Parallel Array 
import groovyx.gpars.extra166y.Ops

groovyx.gpars.GParsPool.withPool {
    assert 15 == [1, 2, 3, 4, 5].parallelArray.reduce({a, b -> a + b} as Ops.Reducer, 0)                                        //summarize
    assert 55 == [1, 2, 3, 4, 5].parallelArray.withMapping({it ** 2} as Ops.Op).reduce({a, b -> a + b} as Ops.Reducer, 0)       //summarize squares
    assert 20 == [1, 2, 3, 4, 5].parallelArray.withFilter({it % 2 == 0} as Ops.Predicate)                                       //summarize squares of even numbers
            .withMapping({it ** 2} as Ops.Op)
            .reduce({a, b -> a + b} as Ops.Reducer, 0)

    assert 'aa:bb:cc:dd:ee' == 'abcde'.parallelArray                                                                            //concatenate duplicated characters with separator
            .withMapping({it * 2} as Ops.Op)
            .reduce({a, b -> "$a:$b"} as Ops.Reducer, "")  
}    
    
    
    
///WithPool() method takes  number of threads , an unhandled exception handler.
withPool(10) {...}
withPool(20, exceptionHandler) {...}
//Note the implementations 
public static withPool(int numberOfThreads, UncaughtExceptionHandler handler, Closure cl) {
        final ForkJoinPool pool = createPool(numberOfThreads, handler)
        try {
            return withExistingPool(pool, cl)
        } finally {
            pool.shutdown()
            pool.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS)
        }
}
//Example 
GParsPool.withPool(5) {ForkJoinPool pool ->
     assert [1, 2, 3, 4, 5].everyParallel {it > 0}
     assert ![1, 2, 3, 4, 5].everyParallel {it > 1}
 }

GParsExecutorsPool.withPool(5) {ExecutorService service ->
     service.submit({performLongCalculation()} as Runnable)
 }
//withExistingPool takes another pool 
withExistingPool(ForkJoinPool pool, Closure cl)
 
///Meta-class enhancer
//ParallelEnhancer for GParsPool
import groovyx.gpars.ParallelEnhancer

def list = [1, 2, 3, 4, 5, 6, 7, 8, 9]
ParallelEnhancer.enhanceInstance(list)
println list.collectParallel {it * 2 }

def animals = ['dog', 'ant', 'cat', 'whale']
ParallelEnhancer.enhanceInstance animals
println (animals.anyParallel {it ==~ /ant/} ? 'Found an ant' : 'No ants found')
println (animals.everyParallel {it.contains('a')} ? 'All animals contain a' : 'Some animals can live without an a')

//GParsExecutorsPoolEnhancer for GParsExecutorsPool
import groovyx.gpars.GParsExecutorsPoolEnhancer

def list = [1, 2, 3, 4, 5, 6, 7, 8, 9]
GParsExecutorsPoolEnhancer.enhanceInstance(list)
println list.collectParallel {it * 2 }

def animals = ['dog', 'ant', 'cat', 'whale']
GParsExecutorsPoolEnhancer.enhanceInstance animals
println (animals.anyParallel {it ==~ /ant/} ? 'Found an ant' : 'No ants found')
println (animals.allParallel {it.contains('a')} ? 'All animals contain a' : 'Some animals can live without an a')
 
 
///Avoid side-effects in functions
//Don't do this: Use .asSynchronized()
def thumbnails = []
images.eachParallel {thumbnails << it.thumbnail}  //Concurrently accessing a not-thread-safe collection of thumbnails, don't do this!
 
 
///Transparently parallel collections
//Use makeConcurrent() and  makeSequential() to make a list concurrent or sequential 
import static groovyx.gpars.GParsPool.withPool

def list = [1, 2, 3, 4, 5, 6, 7, 8, 9]

println 'Sequential: '
list.each { print it + ',' }
println()

withPool {
    println 'Sequential: '
    list.each { print it + ',' }
    println()

    list.makeConcurrent()

    println 'Concurrent: '
    list.each { print it + ',' }
    println()

    list.makeSequential()

    println 'Sequential: '
    list.each { print it + ',' }
    println()
    //specify code blocks, in which the collection maintains concurrent semantics.
    list.asConcurrent {
        println 'Concurrent: '
        list.each { print it + ',' }
        println()
    }
}

println 'Sequential: '
list.each { print it + ',' }
println()
 
///Memoize
//The memoize function enables caching of function's return values. 
//use Groovy functionality in general, Below functionality does concurrently
//Other variants- memoizeAtMost(2), memoizeAtLeast(2), memoizeBetween(2,10)
GParsPool.withPool {
    def urls = ['http://www.dzone.com', 'http://www.theserverside.com', 'http://www.infoq.com']
    Closure download = {url ->
        println "Downloading $url"
        url.toURL().text.toUpperCase()
    }
    Closure cachingDownload = download.gmemoize()

    println 'Groovy sites today: ' + urls.findAllParallel {url -> cachingDownload(url).contains('GROOVY')}
    println 'Grails sites today: ' + urls.findAllParallel {url -> cachingDownload(url).contains('GRAILS')}
    println 'Griffon sites today: ' + urls.findAllParallel {url -> cachingDownload(url).contains('GRIFFON')}
    println 'Gradle sites today: ' + urls.findAllParallel {url -> cachingDownload(url).contains('GRADLE')}
    println 'Concurrency sites today: ' + urls.findAllParallel {url -> cachingDownload(url).contains('CONCURRENCY')}
    println 'GPars sites today: ' + urls.findAllParallel {url -> cachingDownload(url).contains('GPARS')}
}
 
 
 
 
 
 
 
///Reference 
java.util.concurrent.Future<V>
    boolean cancel(boolean mayInterruptIfRunning)
        Attempts to cancel execution of this task.
    V get()
        Waits if necessary for the computation to complete, and then retrieves its result.
    V get(long timeout, TimeUnit unit)
        Waits if necessary for at most the given time for the computation to complete,
        and then retrieves its result, if available.
        TimeUnit enums are DAYS ,HOURS ,MICROSECONDS ,MILLISECONDS ,MINUTES ,NANOSECONDS ,SECONDS 
    boolean isCancelled()
        Returns true if this task was cancelled before it completed normally.
    boolean isDone()
        Returns true if this task completed.
groovy.time.Duration  
    Duration(int days, int hours, int minutes, int seconds, int millis) 
    java.util.Date getAgo() 
    DatumDependentDuration 	minus(DatumDependentDuration rhs) 
    Duration minus(Duration rhs) 
    TimeDatumDependentDuration 	minus(TimeDatumDependentDuration rhs) 
    TimeDuration minus(TimeDuration rhs) 
    DatumDependentDuration 	plus(DatumDependentDuration rhs) 
    Duration plus(Duration rhs) 
    Duration plus(TimeDuration rhs) 
    long toMilliseconds() 
    //Methods inherited from class groovy.time.BaseDuration
    compareTo, getDays, getHours, getMillis, getMinutes, getMonths, getSeconds, getYears, plus, toString


Enhancements to groovy.lang.Closure
    groovy.lang.Closure async()
        Creates an asynchronous variant of the supplied closure, 
        which, when invoked returns a future for the potential return value
    groovy.lang.Closure asyncFun( FJPool pool)
        Creates an asynchronous and composable variant of the supplied closure, 
        which, when invoked returns a DataflowVariable for the potential return value
    groovy.lang.Closure asyncFun( FJPool pool, boolean blocking)
        Creates an asynchronous and composable variant of the supplied closure, 
        which, when invoked returns a DataflowVariable for the potential return value
    groovy.lang.Closure asyncFun( boolean blocking)
        Creates an asynchronous and composable variant of the supplied closure, 
        which, when invoked returns a DataflowVariable for the potential return value
    groovy.lang.Closure asyncFun()
        Creates an asynchronous and composable variant of the supplied closure, 
        which, when invoked returns a DataflowVariable for the potential return value
    <T> java.util.concurrent.Future<T> callAsync( java.lang.Object... args)
        Calls a closure in a separate thread supplying the given arguments, 
        returning a future for the potential return value.
    <T> java.util.concurrent.Future<T> callTimeoutAsync( groovy.time.Duration timeout, java.lang.Object... args)
        Calls a closure in a separate thread supplying the given arguments, 
        returning a future for the potential return value.
    <T> java.util.concurrent.Future<T> callTimeoutAsync( long timeout, java.lang.Object... args)
        Calls a closure in a separate thread supplying the given arguments, 
        returning a future for the potential return value.
    <T> java.util.concurrent.Future<T> callParallel()
        schedules the supplied closure for processing in the underlying thread pool.

Enhancement to java.lang.Object which is in general a collection 
Note Closure<K> cl , means cl(each_element): K 
    <K> java.util.Map<K,java.util.List<java.lang.Object>> groupByParallel( groovy.lang.Closure<K> cl)
        Creates a Parallel Map out of the supplied collection/object by K, key returning closure 
    <T> java.util.Collection<T> collectParallel( groovy.lang.Closure<? extends T> cl)
        Creates a Parallel Array out of the supplied collection/object by map closure 
    <T> java.util.List<T> collectManyParallel( groovy.lang.Closure<java.util.Collection<? extends T>> projection)
        Creates a Parallel Array out of the supplied collection/object by flatMap closure 
    boolean anyParallel( groovy.lang.Closure cl)
        Creates a Parallel Array out of the supplied collection/object by the filter predicate closure 
    boolean everyParallel( groovy.lang.Closure cl)
        Creates a Parallel Array out of the supplied collection/object by the filter predicate closure 
    int countParallel( groovy.lang.Closure filter)
        Creates a Parallel Array out of the supplied collection/object by the filter predicate closure 
    int countParallel( java.lang.Object filter)
        Creates a Parallel Array out of the supplied collection/object by an Object 
    java.lang.Object findAnyParallel( groovy.lang.Closure cl)
        Creates a Parallel Array out of the supplied collection/object by the filter predicate closure 
    java.lang.Object findParallel( groovy.lang.Closure cl)
        Creates a Parallel Array out of the supplied collection/object by the filter predicate closure 
    java.lang.Object grepParallel( java.lang.Object filter)
        Creates a Parallel Array out of the supplied collection/object  by an Object 
    java.lang.Object injectParallel( groovy.lang.Closure cl)
        Creates a Parallel Array out of the supplied collection/object by the reduction closure 
    java.lang.Object injectParallel( java.lang.Object seed, groovy.lang.Closure cl)
        Creates a Parallel Array out of the supplied collection/object by the reduction closure 
    java.lang.Object maxParallel( groovy.lang.Closure cl)
        Creates a Parallel Array out of the supplied collection/object and invokes its max() method by the comparator closure .
    java.lang.Object minParallel( groovy.lang.Closure cl)
        Creates a Parallel Array out of the supplied collection/object and invokes its min() method by the comparator closure .
    java.lang.Object splitParallel( java.lang.Object filter)    
        Creates a Parallel Array out of the supplied collection/object by the filter predicate closure 
    java.util.Collection<java.lang.Object> findAllParallel( groovy.lang.Closure cl)
        Creates a Parallel Array out of the supplied collection/object by the filter predicate closure 
    void asConcurrent( groovy.lang.Closure code)
        Makes the collection concurrent for the passed-in block of code.
    java.lang.Object makeConcurrent())  
        Overrides the iterative methods like each() collect() and such, 
        so that they call their parallel variants from the GParsPoolUtil class like eachParallel(), collectParallel() and such.
    java.lang.Object makeSequential()
        Gives the iterative methods like each() or find() the original sequential semantics.
    java.lang.Object maxParallel()
        Creates a Parallel Array out of the supplied collection/object 
        and invokes its max() method using the default comparator.
    java.lang.Object minParallel()
        Creates a Parallel Array out of the supplied collection/object 
        and invokes its min() method using the default comparator.
    java.lang.Object sumParallel()
        Creates a Parallel Array out of the supplied collection/object 
        and summarizes its elements using the foldParallel() method with the + operator and the reduction operation.
    boolean isConcurrent()
        Indicates whether the iterative methods like each() or collect() work have been altered to work concurrently.
    ParallelArray getParallelArray()
        Creates a ParallelArray wrapping the elements of the original collection.
    groovyx.gpars.pa.PAWrapper getParallel()
        Creates a PAWrapper around a ParallelArray wrapping the elements of the original collection.

Enhancement to java.util.Collection
Note Closure<K> cl , means cl(each_element): K , for index,  cl(each_element, index):K
    <T> java.util.Collection<T> collectParallel( groovy.lang.Closure<? extends T> cl)
        Creates a Parallel Array out of the supplied collection/object by supplied closure as the transformation operation.
    <T> java.util.List<T> collectManyParallel( groovy.lang.Closure<java.util.Collection<? extends T>> projection)
        Creates a Parallel Array out of the supplied collection/object by supplied projection closure as the transformation operation.
    boolean anyParallel( groovy.lang.Closure cl)
        Creates a Parallel Array out of the supplied collection/object by supplied closure as the filter predicate.
    boolean everyParallel( groovy.lang.Closure cl)
        Creates a Parallel Array out of the supplied collection/object by the supplied closure as the filter predicate.
    int countParallel( groovy.lang.Closure filter)
        Creates a Parallel Array out of the supplied collection/object by the supplied rule as the filter predicate.
    int countParallel( java.lang.Object filter)
        Creates a Parallel Array out of the supplied collection/object by the supplied rule as the filter predicate.
    <K,T> java.util.Map<K,java.util.List<T>> groupByParallel( groovy.lang.Closure<K> cl)
        Creates a Parallel Array out of the supplied collection/object by the supplied closure which returns key, k 
    <T> T findAnyParallel( groovy.lang.Closure cl)
        Creates a Parallel Array out of the supplied collection/object by the supplied closure as the filter predicate.
    <T> T findParallel( groovy.lang.Closure cl)
        Creates a Parallel Array out of the supplied collection/object by the supplied closure as the filter predicate.
    <T> T injectParallel( T seed, groovy.lang.Closure cl)
        Creates a Parallel Array out of the supplied collection/object by supplied closure as the reduction operation.
    <T> T injectParallel( groovy.lang.Closure cl)
        Creates a Parallel Array out of the supplied collection/object by supplied closure as the reduction operation.
    <T> T maxParallel( groovy.lang.Closure cl)
        Creates a Parallel Array out of the supplied collection/object and invokes its max() method using the supplied closure as the comparator.
    <T> T minParallel( groovy.lang.Closure cl)
        Creates a Parallel Array out of the supplied collection/object and invokes its min() method using the supplied closure as the comparator.
    <T> java.util.Collection<T> eachParallel( groovy.lang.Closure cl)
        Creates a Parallel Array out of the supplied collection/object by the supplied closure as the transformation operation.
    <T> java.util.Collection<T> eachWithIndexParallel( groovy.lang.Closure cl)
        Creates a Parallel Array out of the supplied collection/object by using the supplied closure as the transformation operation.
    <T> java.util.Collection<T> findAllParallel( groovy.lang.Closure cl)
        Creates a Parallel Array out of the supplied collection/object by the supplied closure as the filter predicate.
    <T> java.util.Collection<T> grepParallel( java.lang.Object filter)
        Creates a Parallel Array out of the supplied collection/object by the supplied rule as the filter predicate.
    <T> T maxParallel()
        Creates a Parallel Array out of the supplied collection/object and invokes its max() method using the default comparator.
    <T> T minParallel()
        Creates a Parallel Array out of the supplied collection/object and invokes its min() method using the default comparator.
    <T> T sumParallel()
        Creates a Parallel Array out of the supplied collection/object and sums with the + operator and the reduction operation.
    <T> ParallelArray<T> getParallelArray()
        Creates a ParallelArray wrapping the elements of the original collection.
    <T> groovyx.gpars.pa.PAWrapper<T> getParallel()
        Creates a PAWrapper around a ParallelArray wrapping the elements of the original collection.

Enhancement to java.util.Map
Here Note Closure<K> cl , means cl(k,v or Entry): K , for index,  cl(k,v or Entry, index):K
    <T> java.util.Collection<T> collectParallel( groovy.lang.Closure<? extends T> cl)
        Creates a Parallel Array out of the supplied map by supplied closure as the transformation operation.
    <T> java.util.List<T> collectManyParallel( groovy.lang.Closure<java.util.Collection<? extends T>> projection)
        Creates a Parallel Array out of the supplied collection/object by supplied projection closure as the transformation operation.
    boolean anyParallel( groovy.lang.Closure cl)
        Creates a Parallel Array out of the supplied map by the supplied closure as the filter predicate.
    boolean everyParallel( groovy.lang.Closure cl)
        Creates a Parallel Array out of the supplied map by the supplied closure as the filter predicate.
    <K,V> java.util.Map.Entry<K,V> findAnyParallel( groovy.lang.Closure cl)
        Creates a Parallel Array out of the supplied map by the supplied closure as the filter predicate.
    <K,V> java.util.Map.Entry<K,V> findParallel(groovy.lang.Closure cl)
        Creates a Parallel Array out of the supplied map by the supplied closure as the filter predicate.
    <K,V> java.util.Map<K,V> eachParallel(groovy.lang.Closure cl)
        Creates a Parallel Array out of the supplied map by the supplied closure as the transformation operation.
    <K,V> java.util.Map<K,V> eachWithIndexParallel(groovy.lang.Closure cl)
        Does parallel eachWithIndex on maps
    <K,V> java.util.Map<K,V> findAllParallel(groovy.lang.Closure cl)
        Creates a Parallel Array out of the supplied map by the supplied closure as the filter predicate.
    <K,V> java.util.Map<K,V> grepParallel(java.lang.Object filter)
        Creates a Parallel Array out of the supplied map by the supplied rule as the filter predicate.

Enhancements to jsr166y.ForkJoinPool pool
    <T> java.util.concurrent.Future<T> leftShift( groovy.lang.Closure<T> task)
        Submits the task for asynchronous processing returning the Future received from the executor service.








///* Gpars - Running tasks in parallel and asynchronously - by closure.callAsync(...)


@Grab('org.codehaus.gpars:gpars:1.2.1')
import groovyx.gpars.GParsPool
		 
def fetchData(url) {
      url.toURL().text.size()
    }

List getData(List urls) {
      def response = [].asSynchronized()
      GParsPool.withPool {         //withPool adds callAsync() to closure
        urls.each { url ->
          response << this.&fetchData.callAsync(url)  // callAsync returns java.util.concurrent.Future, 
        }
      }
      response
    }

def data = getData(["http://www.google.co.in", "http://www.google.co.in", "http://www.google.co.in"])   //returns future
data.each {
           println "data received: ${it.get()}"   //call future.get()
         }

///* WIth timeouts 
{->
    while(true) {
        Thread.sleep 1000  //Simulate a bit of interesting calculation
        if (Thread.currentThread().isInterrupted()) break;  //We've been cancelled
    }
}.callTimeoutAsync(2000)

///* Running functions parallely 
GParsPool.withPool {
    assert [10, 20] == GParsPool.executeAsyncAndWait({calculateA()}, {calculateB()}   //waits for results
    assert [10, 20] == GParsPool.executeAsync({calculateA()}, {calculateB()})*.get()  //returns Futures instead and doesn't wait for results to be calculated
}

         
///*Parallel Arrays
//The parallelArray property on any collection or object will return a jsr166y.forkjoin.ParallelArray instance 
//holding the elements of the original collection, which then can be manipulated through the jsr166y API. 


import groovyx.gpars.extra166y.Ops

groovyx.gpars.GParsPool.withPool {
    assert 15 == [1, 2, 3, 4, 5].parallelArray.reduce({a, b -> a + b} as Ops.Reducer, 0)                                        //summarize
    assert 55 == [1, 2, 3, 4, 5].parallelArray.withMapping({it ** 2} as Ops.Op).reduce({a, b -> a + b} as Ops.Reducer, 0)       //summarize squares
    assert 20 == [1, 2, 3, 4, 5].parallelArray.withFilter({it % 2 == 0} as Ops.Predicate)                                       //summarize squares of even numbers
            .withMapping({it ** 2} as Ops.Op)
            .reduce({a, b -> a + b} as Ops.Reducer, 0)

    assert 'aa:bb:cc:dd:ee' == 'abcde'.parallelArray                                                                            //concatenate duplicated characters with separator
            .withMapping({it * 2} as Ops.Op)
            .reduce({a, b -> "$a:$b"} as Ops.Reducer, "")
            
            

///* gpars - DataFlow
/*
Using dataflow variables for lazy evaluation
A variable can only be assigned a value once in its lifetime, while the number of reads is unlimited. 
If a variable value is not written by a write operation, 
all the read operations are blocked until the variable is actually written (bind).

task creates a separate Thread where you can read or write to dataflow variable
*/

@Grab('org.codehaus.gpars:gpars:1.2.1')
import static groovyx.gpars.dataflow.Dataflow.task
import groovyx.gpars.dataflow.*

final def x = new DataflowVariable()
final def y = new DataflowVariable()
final def z = new DataflowVariable()

task {
    z << x.val + y.val				//set DataFlowVariable  //blocks as x and y are not written yets 
    println "Result: ${z.val}"		//read by .val 
}

task {
    x << 10                //set DataFlowVariable
}

task {
    Thread.sleep(1000)
    y << 5					//set DataFlowVariable
}


///* gpars - Actor 
@Grab('org.codehaus.gpars:gpars:1.2.1')
import groovyx.gpars.actor.*
import java.util.concurrent.*

final class PingMessage {}
final class PongMessage {}
final class StopMessage {}
final class StartMessage {}


class Play extends DynamicDispatchActor {
    String myMessage 
    Play other
    static Map messageMap = [ ping : new PingMessage(), pong : new PongMessage(), 
                   start: new StartMessage(), stop: new StopMessage()]

    void onMessage(PingMessage message) { 
      println "${Thread.currentThread().name} got ping"
      Thread.sleep(1000)
      other << messageMap.pong
      
    }

    void onMessage(PongMessage message) {
      println "${Thread.currentThread().name} got pong"
      Thread.sleep(1000)
      reply messageMap.ping
    }
    void onMessage(StopMessage message) {
      other.terminate() //graceful stop
      stop()  //abrupt stop 
    }
    
    void onMessage(StartMessage message) {
      other.start()
      this << messageMap.ping
    }

}
        
def controller = new Play(myMessage: "ping", , other : new Play(myMessage:"pong"))
controller.start()
controller << Play.messageMap.start 
Thread.sleep(10000)
controller << Play.messageMap.stop




///* Actor DSL 
@Grab('org.codehaus.gpars:gpars:1.2.1')
import groovyx.gpars.actor.Actor
import groovyx.gpars.actor.Actors 

final def doubler = Actors.reactor { message ->  //gets message and replys 
    2 * message
}

Actor actor = Actors.actor {        //full flegded actor 
    (1..10).each {doubler << it}
    int i = 0
    loop {
        i += 1
        if (i > 10) stop()
        else {
            react {message ->
                println "Double of $i = $message"
            }
        }
    }
}

actor.join()
doubler.stop()
doubler.join()

//with class

@Grab('org.codehaus.gpars:gpars:1.2.1')
import groovyx.gpars.actor.DefaultActor
import groovyx.gpars.actor.Actors 

//call any actor's methods from within the act() method 
//and use the react() or loop() methods in them
class MyDemoActor extends DefaultActor {    

    protected void act() {
        handleA()
    }

    private void handleA() {
        react {a ->
            handleB(a)
        }
    }

    private void handleB(int a) {
        react {b ->
            println a + b
            reply a + b
        }
    }
}

final def demoActor = new MyDemoActor()
demoActor.start()

Actors.actor {
    demoActor 10
    demoActor 20
    react {
        println "Result: $it"
    }
}.join()






///* Agent 
//An Agent wraps a reference to mutable state, held inside a single field, and accepts code (closures / commands) as messages, 
//which can be sent to the Agent using the '<<' operator, 
//the send() methods or the implicit call() method
//check the current value with Agent.val or Agent.valAsync(closure).

@Grab('org.codehaus.gpars:gpars:1.2.1') 
import groovyx.gpars.agent.Agent
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * Create a new Agent wrapping a list of strings
 */
def jugMembers = new Agent<List<String>>(['Me'])  //add Me

jugMembers.send {it.add 'James'}  //add James

final Thread t1 = Thread.start {
    jugMembers.send {it.add 'Joe'}  //add Joe
}

final Thread t2 = Thread.start {
    jugMembers << {it.add 'Dave'}  //add Dave
    jugMembers {it.add 'Alice'}    //add Alice (using the implicit call() method)
}

[t1, t2]*.join()
println jugMembers.val
jugMembers.valAsync {println "Current members: $it"}

jugMembers.await()

///* STM (Software Transactional Memory) 
//is a concurrency control mechanism for managing access to shared memory. 
//Using STM to atomically update fields (ACI part of the ACID ) by GParsStm.atomic {Closure}  
//- depends on multiverse

@Grab('org.multiverse:multiverse-beta:0.7-RC-1')    //Only valid with this
@Grab('org.codehaus.gpars:gpars:1.0.0')	            //Only valid with this
import groovyx.gpars.stm.GParsStm
import org.multiverse.api.references.TxnInteger  //like java concurrent atomic integer, has many methods 
import static org.multiverse.api.StmUtils.newTxnInteger

class Account {
    private final TxnInteger amount = newTxnInteger(0);

    public void transfer(final int a) {
        GParsStm.atomic {
            amount.increment(a);
            //....
            //any other call here 
            //...
        }
    }

    public int getCurrentAmount() {
        GParsStm.atomicWithInt {
            amount.get();
        }
    }
}

def a = new Account()
a.transfer(20)
a.getCurrentAmount()

/* 
◦ atomic - returning Object 
◦ atomicWithInt - returning int 
◦ atomicWithLong - returning long 
◦ atomicWithBoolean - returning boolean 
◦ atomicWithDouble - returning double 
◦ atomicWithVoid - no return value

*/


///*** Using GORM for Hibernate Outside Grails(Note GORM6, spring is not required)
//NOte Hibenate does not support SQLite
//Hence, Use custom dialect for example as given in gorm-hibernate-sqlite-master

//build.gradle for H2DB
compile "org.grails:grails-datastore-gorm-hibernate5:6.0.11.RELEASE"
runtime "com.h2database:h2:1.4.192"
runtime "org.apache.tomcat:tomcat-jdbc:8.5.0"
runtime "org.apache.tomcat.embed:tomcat-embed-logging-log4j:8.5.0"
runtime "org.slf4j:slf4j-api:1.7.10"

//The above example also uses the H2 Database and Tomcat connection pool. 
//However other pool implementations are supported including commons-dbcp, tomcat pool or hikari. 
//If a connection pool is not specified org.springframework.jdbc.datasource.DriverManagerDataSource 
//is used, which creates a new connection to the database each time you request a connect. 
//The latter will probably cause issues with an H2 in-memory database in that 
//it will create a new in-memory database each time a connection is requested, 
//losing previously created tables. Normal databases (MySql, Postgres or even file-based H2) 
//are not affected. 

//Note sqlite is not supported by hibernate, 
//Use custom dialect and with files , check src\org\.... in 2.examples 
dataSource:
    pooled: true
    jmxExport: true
    dialect: org.hibernate.dialect.SQLiteDialect
    driverClassName: org.sqlite.JDBC
    #    username: ''
    #    password: ''
    dbCreate: create-drop
    url: jdbc:sqlite:sqliteDev.db

    
    
//src/main/groovy/model.groovy 
package examples 
import org.grails.datastore.gorm.*
import java.util.*

@grails.gorm.annotation.Entity 
class Person implements GormEntity<Person> { 1
    String firstName
    String lastName
    static constraints = {
        firstName blank:false
        lastName blank:false
    }
     /*
    //check http://docs.grails.org/3.0.17/ref/Database%20Mapping/Usage.html
    static mapping = {
        table 'my_table'
        id column: "Mgr_id"
        version false
    }
    */
}

@grails.gorm.annotation.Entity 
class Book implements GormEntity<Book>{
    String title
    Date releaseDate  //java.util.Date(int year, int month, int date), with GDK enhancements like +,- etc
    Author author
}

@grails.gorm.annotation.Entity 
class Author implements GormEntity<Author>{
    String name
}


//Usage 
import org.grails.orm.hibernate.HibernateDatastore
Map configuration = [
    'hibernate.hbm2ddl.auto':'create-drop',
    'dataSource.url':'jdbc:h2:mem:myDB'
    'dataSource.driverClassName': 'org.h2.Driver'
    'dataSource.username': 'sa'
    'dataSource.password': ''
]


HibernateDatastore datastore = new HibernateDatastore( configuration, Person)

Person.withTransaction { status ->  //status.setRollbackOnly()to rollback 
        new Person(firstName:"Fred", lastName:"Das").save()
    }

...
datastore.close()


//Details configration 
//http://gorm.grails.org/6.0.x/hibernate/manual/#configuration
//org.grails.orm.hibernate.HibernateDatastore 
//Datastore implementation that uses a Hibernate SessionFactory underneath. 
//HibernateDatastore uses by default file for conf src/main/resources/application.yml 
//exactly same as grails's dataSource one 
/*
hibernate.hbm2ddl.auto:
    none : disable , explicitly create DDL 
    validate: validate the schema, makes no changes to the database.
    update: update the schema.
    create: creates the schema, destroying previous data.
    create-drop: drop the schema when the SessionFactory is closed explicitly, typically when the application is stopped.
   
    In case of update, if schema is not present in the DB then the schema is created.
    In case of validate, if schema does not exists in DB, it is not created. Instead, it will throw an error:- Table not found:<table name>
    In case of create-drop, schema is not dropped on closing the session. It drops only on closing the SessionFactory.
    In case if i give any value to this property(say abc, instead of above four values discussed above)
    or it is just left blank. It shows following behaviour:
    -If schema is not present in the DB:- It creates the schema
    -If schema is present in the DB:- update the schema.
     
*/

//Create
def p = new Person(name: "Fred", age: 40, lastVisit: new Date())
p.save()

//Read
//GORM transparently adds an implicit id property to  domain class 
def p = Person.get(1)
assert 1 == p.id
//You can also load an object in a read-only state by using the read method:
def p = Person.read(1)

// you can also load a proxy for an instance by using the load method:
def p = Person.load(1)
//This incurs no database access until a method other than getId() is called. 
//Hibernate then initializes the proxied instance, or throws an exception 
//if no record is found for the specified id.


///Update
//To update an instance, change some properties and then call save again:
def p = Person.get(1)
p.name = "Bob"
p.save()

//Delete
//To delete an instance use the delete method:
def p = Person.get(1)
p.delete()

///Listing instances
//Use the list() method to obtain all instances of a given class:

def books = Book.list()

//The list() method supports arguments to perform pagination:
def books = Book.list(offset:10, max:20)

//as well as sorting:
def books = Book.list(sort:"title", order:"asc")


///Retrieval by Database Identifier
def book = Book.get(23)
def books = Book.getAll(23, 93, 81)

///Dynamic Finders
//A dynamic finder looks like a static method invocation, 
//but the methods themselves don’t actually exist in any form at the code level.
//Instead, a method is auto-magically generated using code synthesis at runtime, 
//based on the properties of a given class.
class Book {
    String title
    Date releaseDate  
    Author author
}

class Author {
    String name
}

//The Book class has properties such as title, releaseDate and author. 
//These can be used by the findBy* and findAllBy* methods in the form of "method expressions":

def book = Book.findByTitle("The Stand")
book = Book.findByTitleLike("Harry Pot%")
book = Book.findByReleaseDateBetween(firstDate, secondDate)
book = Book.findByReleaseDateGreaterThan(someDate)
book = Book.findByTitleLikeOrReleaseDateLessThan("%Something%", someDate)

///Method Expressions
//A method expression in GORM is made up of the prefix such as findBy* 
//followed by an expression that combines one or more properties.
Book.findBy(<<Property>><<Comparator>><<Boolean Operator>>)?<<Property>><<Comparator>>

//The tokens marked with a ? are optional.
//Each comparator changes the nature of the query. For example:
def book = Book.findByTitle("The Stand")
book =  Book.findByTitleLike("Harry Pot%")

//The possible comparators include:
    InList - In the list of given values
    LessThan - less than a given value
    LessThanEquals - less than or equal a give value
    GreaterThan - greater than a given value
    GreaterThanEquals - greater than or equal a given value
    Like - Equivalent to a SQL like expression
    Ilike - Similar to a Like, except case insensitive
    NotEqual - Negates equality
    InRange - Between the from and to values of a Groovy Range
    Rlike - Performs a Regexp LIKE in MySQL or Oracle otherwise falls back to Like
    Between - Between two values (requires two arguments)
    IsNotNull - Not a null value (doesn’t take an argument)
    IsNull - Is a null value (doesn’t take an argument)

//Example 
def now = new Date()
def lastWeek = now - 7
def book = Book.findByReleaseDateBetween(lastWeek, now)

books = Book.findAllByReleaseDateIsNull()
books = Book.findAllByReleaseDateIsNotNull()

///Boolean logic (AND/OR)
//Method expressions can also use a boolean operator to combine two or more criteria:
def books = Book.findAllByTitleLikeAndReleaseDateGreaterThan("%Java%", new Date() - 30)
def books = Book.findAllByTitleLikeOrReleaseDateGreaterThan( "%Java%", new Date() - 30)

///Querying Associations
def author = Author.findByName("Stephen King")
def books = author ? Book.findAllByAuthor(author) : []

///Pagination and Sorting
def books = Book.findAllByTitleLike("Harry Pot%",    [max: 3, offset: 2, sort: "title", order: "desc"])

/// Where Queries
//The where() method accepts a closure  
//The closure should define the logical criteria in regular Groovy syntax, 
def query = Person.where {
   firstName == "Bart"
}
Person bart = query.find()

//The returned object is a DetachedCriteria instance, 
//which means it is not associated with any particular database connection or session. 
//This means you can use the where method to define common queries at the class level:

import grails.gorm.*

class Person {
    static DetachedCriteria<Person> simpsons = where {
         lastName == "Simpson"
    }
    ...
}
...
Person.simpsons.each { Person p ->
    println p.firstname
}

//Query execution is lazy and only happens upon usage of the DetachedCriteria instance. 
//If you want to execute a where-style query immediately 
//there are variations of the findAll and find methods to accomplish this:

def results = Person.findAll {
     lastName == "Simpson"
}
def results = Person.findAll(sort:"firstName") {
     lastName == "Simpson"
}
Person p = Person.find { firstName == "Bart" }

//Each Groovy operator maps onto a regular criteria method. 
//The following table provides a map of Groovy operators to methods:
//Operator    Criteria Method     Description
==            eq                  Equal to
!=            ne                  Not equal to
>             gt                  Greater than
<             lt                  Less than
>=            ge                  Greater than or equal to
<=            le                  Less than or equal to
in            inList              Contained within the given list
==~           like                Like a given string, SQL like or regex if DB supports 
=~            ilike               Case insensitive like
//logic 
&&            and 
||            or 
!             not 

//It is possible use regular Groovy comparison operators and logic to formulate complex queries:
def query = Person.where {
    (lastName != "Simpson" && firstName != "Fred") || (firstName == "Bart" && age > 9)
}
def results = query.list(sort:"firstName")

//The Groovy regex matching operators map onto like and ilike queries 
//unless the expression on the right hand side is a Pattern object, in which case they map onto an rlike query:
def query = Person.where {
     firstName ==~ ~/B.+/
}

//Note that rlike queries are only supported if the underlying database supports regular expressions
//A between criteria query can be done by combining the in keyword with a range:
def query = Person.where {
     age in 18..65
}

//isNull and isNotNull style queries by using null with regular comparison operators:
def query = Person.where {
     middleName == null
}

///Query Composition
DetachedCriteria<Person> query = Person.where {
     lastName == "Simpson"
}
DetachedCriteria<Person> bartQuery = query.where {
     firstName == "Bart"
}
Person p = bartQuery.find()

//Note that you cannot pass a closure defined as a variable into the where method 
//unless it has been explicitly cast to a DetachedCriteria instance. 
//In other words the following will produce an error:

def callable = {
    lastName == "Simpson"
}
def query = Person.where(callable)

//The above must be written as follows:

import grails.gorm.DetachedCriteria

def callable = {
    lastName == "Simpson"
} as DetachedCriteria<Person>
def query = Person.where(callable)

///Conjunction, Disjunction and Negation
//combine regular Groovy logical operators (|| and &&) to form conjunctions and disjunctions:
def query = Person.where {
    (lastName != "Simpson" && firstName != "Fred") || (firstName == "Bart" && age > 9)
}

def query = Person.where {
    firstName == "Fred" && !(lastName == 'Simpson')
}

//Property Comparison Queries
//If you use a property name on both the left hand and right side of a comparison expression 
//then the appropriate property comparison criteria is automatically used:

def query = Person.where {
   firstName == lastName
}

//property comparison criteria
//Groovy version is  ==, !=, > . < >=, <=
eqProperty
neProperty
gtProperty
ltProperty
geProperty
leProperty


///Querying Associations - Not FOR MANY Side (only for One side)
//Associations can be queried by using the dot operator to specify the 
//property name of the association to be queried:
//(Note for one to Many only, One book as one Author ) 
def query = Book.where {
    author.name == "Joe" || author.fullName == "Fred"
}


//You can group multiple criterion inside a closure method call 
//where the name of the method matches the association name
//(Note for one to Many only, One book as one Author ) 
def query = Book.where {
    author { name == "Jack" || fullName == "Joe" }
}


//combined with other top-level criteria:
def query = Book.where {
     author { name == "Jack" } || title == "Ed"
}

//%%
//For collection associations it is possible to apply queries 
//to the size of the collection:
//(for Many to Many )
def query = Author.where {
       books.size() == 2
}

//criteria method when size() is used 
sizeEq
sizeNe
sizeGt
sizeGe
sizeLe
 

 
///Query Aliases and Sorting - - Not FOR MANY Side (only for One side)
//If you define a query for an association an alias is automatically generated for the query. 
//For example the following query
//Will generate an alias for the author association such as author_alias_0. 
//(One to Many)
def query = Book.where {
    author.name == "Fred"
}


//not useful if you want to later sort or use a projection on the results. 
//For example the following query will fail:

Book.where {
    author.name == "Fred"
}.list(sort:"author.name")

//%%
//Use  an explicit alias should be used 
def query = Book.where {
    def o1 = author        //Define an alias called o1
    o1.name == "Fred" 
}.list(sort:'o1.fullName') 


//%%
///Subqueries
//to execute subqueries within where queries.

final query = Author.where {
  age > avg(age)
}

//possible values 
avg 
sum
max
min
count
property

//You can apply additional criteria to any subquery by using the 'of' method 
//and passing in a closure containing the criteria:

//means => there are many names "Stephen King", avg of these names 
//age > avg of above age 
def query = Author.where {
  age > avg(age).of { name == "Stephen King" } && fullName == "Homer"
}

//%%
//Since the 'property' subquery returns multiple results, 
//the criterion used compares all results. 
//For example the following query will find all author younger 
//than people with the name "Simpson":

Author.where {
    age < property(age).of { name == "Simpson" }
}

//%%
//You can now use in with nested subqueries

def results = Author.where {
    name in where { age > 18 }.name
}.list()

//%%
//Criteria and where queries can be seamlessly mixed:
//Author.where{}... can be on any object eg Person.where{} 
def results = Author.withCriteria {
    notIn "name", Author.where { age > 18 }.name  //notIn is a method taking two args 
 }

//Subqueries can be used with projections
def results = Author.where {
    age > where { age > 18 }.avg('age')
}
	
//Other Functions
//There are several functions available  within the context of a query. 
second  The second of a date property
minute
hour
day
month
year
lower   Converts a string property to lower case
upper   
length  The length of a string property
trim    Trims a string property
 

//Currently functions can only be applied to properties or associations of domain classes. 
//You cannot, for example, use a function on a result of a subquery.  
//%%
//For example the following query can be used to find all releasedate  in 2011:
def query = Book.where {
    year(releaseDate) < 2018
}

//Only on Many side 
//You can also apply functions to associations:
//(One to many)
def query = Author.where {
    year(book.releaseDate) == 2009
}


///Batch Updates and Deletes
//Since each where method call returns a DetachedCriteria instance, 
//you can use where queries to execute batch operations such as batch updates and deletes. 

//For example, the following query will update all people with the surname "Simpson" to have the surname "Bloggs":
//%%
def query = Author.where {
    name == 'Simpson'
}
int total = query.updateAll(fullName:"Bloggs")


//Note that one limitation with regards to batch operations is that join queries 
//(queries that query associations) are not allowed.  


//To batch delete records you can use the deleteAll method:
def query = Author.where {
    name == 'Simpson'
}
int total = query.deleteAll()

///Batch Updates and Deletes
DetachedCriteria<Person> query = Person.where {
    lastName == 'Simpson'
}
int total = query.updateAll(lastName:"Bloggs")

//Note that one limitation with regards to batch operations is that join queries (queries that query associations) are not allowed.
//To batch delete records you can use the deleteAll method:

DetachedCriteria<Person> query = Person.where {
    lastName == 'Simpson'
}
int total = query.deleteAll()

///Criteria
//is an advanced way to query that uses a Groovy builder to construct potentially complex queries. 
//It is a much better approach than building up query strings using a StringBuilder.


//Criteria can be used either with the createCriteria() or withCriteria(closure) methods.
//The builder uses Hibernate’s Criteria API. 
//The nodes on this builder map the static methods found in 
//the Restrictions class of the Hibernate Criteria API
//https://docs.jboss.org/hibernate/orm/3.2/api/org/hibernate/criterion/Restrictions.html

//withCriteria(...) gives list 
//createCriteria().list {...}
//If you invoke the builder with no method name such as:
c { ... }
//The build defaults to listing all the results and hence the above is equivalent to:
c.list { ... }


list            This is the default method. It returns all matching rows.
get             Returns a unique result set i.e. just one row. The criteria has to be formed in a way that it only queries one row. This method is not to be confused with a limit to just the first row.
scroll          Returns a scrollable result set.
listDistinct    If subqueries or associations are used one may end up with the same row multiple times in the result set. This allows listing only distinct entities and is equivalent to DISTINCT_ROOT_ENTITY of the CriteriaSpecification class.
count           Returns the number of matching rows.
 


//%% Check what methods are available 
//https://docs.jboss.org/hibernate/orm/3.2/api/org/hibernate/criterion/Restrictions.html
//Criteria Method     Description
eq                  Equal to
ne                  Not equal to
gt                  Greater than
lt                  Less than
ge                  Greater than or equal to
le                  Less than or equal to
inList              Contained within the given list
like                Like a given string, SQL like or regex if DB supports 
ilike               Case insensitive like

and 
or 
not 

//Example 
def c = Account.createCriteria()
def results = c.list {
    between("balance", 500, 1000)
    eq("branch", "London")
    or {
        like("holderFirstName", "Fred%")
        like("holderFirstName", "Barney%")
    }
    maxResults(10)
    order("holderLastName", "desc")
}

//If no records are found with the above criteria, an empty List is returned.
//All top level conditions are implied to be AND’d together.
//Other Example 
or {
    between("balance", 500, 1000)
    eq("branch", "London")
}

and {
    between("balance", 500, 1000)
    eq("branch", "London")
}

not {
    between("balance", 500, 1000)
    eq("branch", "London")
}




///Querying Associations
//Associations can be queried by having a node that matches the property name. 

//%%
def c = Author.createCriteria()
def now = new Date()
def results = c.list {
    books {
        between('releaseDate', now - 10, now)
    }
}

//You can also nest such association queries within logical blocks:
//%%
def c = Author.createCriteria()
def now = new Date()
def results = c.list {
    or {
        between('age', 10, 60)
        books {
            between('releaseDate', now - 10, now)
        }
    }
}


/// Querying with Projections
//Projections may be used to customise the results.
//Define a "projections" node within the criteria builder tree to use projections. 

//methods within the projections node to the methods found 
//in the Hibernate Projections class
//https://docs.jboss.org/hibernate/orm/3.2/api/org/hibernate/criterion/Projections.html

//%%
def c = Author.createCriteria()

def numberOfBranches = c.get {
    projections {
        countDistinct('name')
    }
}
//When multiple fields are specified in the projection, 
//a List of values will be returned. 
//A single value will be returned otherwise.


///Transforming Projection Results
//the result can be transformed with a ResultTransformer. 

//to transform the criteria results into a Map 
//https://docs.jboss.org/hibernate/orm/3.2/api/org/hibernate/criterion/CriteriaSpecification.html

//%%
import org.hibernate.criterion.*

def c = Author.createCriteria()

def authorOverview = c.get {
    resultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP)
    projections {
        sum('age', 'allSum')    //2nd arg, key of map 
        countDistinct('name', 'nameDistinct') //add  an alias to each projection as an additional parameter
    }
}

// authorOverview.allSum
// authorOverview.nameDistinct


//We can also transform the result into an object of our choosing 
//via the Transformers.aliasToBean() method. 

//To transform it into an AccountsOverview:
//Each alias must have a corresponding property 
//or explicit setter on the bean otherwise an exception will be thrown.

import org.hibernate.criterion.*
import org.hibernate.transform.*

//must have getter and setter, in groovy autoimatic 
class AccountsOverview {
    Number allBalances
    Number nameDistinct
    
}


def c = Author.createCriteria()

def accountsOverview = c.get {
    resultTransformer(Transformers.aliasToBean(AccountsOverview))
    projections {
        sum('age', 'allBalances')    //2nd arg, key of map 
        countDistinct('name', 'nameDistinct') //add  an alias to each projection as an additional parameter
    }
}

// accountsOverview instanceof AccountsOverview




///Criteria - SQL Projections
//The criteria DSL provides access to Hibernate’s SQL projection API.

// Box is a domain class...
class Box {
    int width
    int height
}


// Use SQL projections to retrieve the perimeter and area of all of the Box instances...
def c = Box.createCriteria()

def results = c.list {
    projections {
      sqlProjection '(2 * (width + height)) as perimeter, (width * height) as area', ['perimeter', 'area'], [INTEGER, INTEGER]
    }
}
//The first argument to the sqlProjection method is the SQL which defines the projections. 
//The second argument is a list of Strings which represent column aliases corresponding to the projected values expressed in the SQL. 
//The third argument is a list of org.hibernate.type.Type instances which correspond to the projected values expressed in the SQL. 
//The API supports all org.hibernate.type.Type objects but constants like INTEGER, LONG, FLOAT etc. are provided by the DSL which correspond to all of the types defined in org.hibernate.type.StandardBasicTypes.
//In case of conflict, use StandardBasicTypes.INTEGER instead of INTEGER.  

//The query above would return results like this:
[[18, 14], [20, 16], [22, 18], [26, 36]]


//If only 1 value is being projected, 
//the alias and the type do not need to be included in a list.
def results = c.list {
    projections {
      sqlProjection 'sum(width * height) as totalArea', 'totalArea', INTEGER
    }
}


//The DSL supports grouped projections with the sqlGroupProjection method.
def results = c.list {
    projections {
        sqlGroupProjection 'width, sum(height) as combinedHeightsForThisWidth', 'width', ['width', 'combinedHeightsForThisWidth'], [INTEGER, INTEGER]
    }
}

//The first argument to the sqlGroupProjection method is the SQL which defines the projections.
//The second argument represents the group by clause that should be part of the query. 
//That string may be single column name or a comma separated list of column names. 
//The third argument is a list of Strings which represent column aliases corresponding to the projected values expressed in the SQL. 
//The fourth argument is a list of org.hibernate.type.Type instances which correspond to the projected values expressed in the SQL.


//The query above is projecting the combined heights of boxes grouped by width 
//and would return results that look like this:
[[2, 24], [4, 9]]
// The first value is a box width and the second value is the sum of the heights of all of the boxes which have that width.






///Criteria - Using SQL Restrictions
//You can access Hibernate’s SQL Restrictions capabilities.


def c = Author.createCriteria()

def peopleWithShortFirstNames = c.list {
    sqlRestriction "char_length(name) >= 4"
}

//SQL Restrictions may be parameterized to deal with SQL injection vulnerabilities related to dynamic restrictions.

def c = Author.createCriteria()
def peopleWithShortFirstNames = c.list {
    sqlRestriction "char_length(name) < ? AND char_length(name) > ?", [maxValue, minValue]
}

//Note that the parameter is SQL. 
//eg for fullName, you should mention full_name 


//* GORM - Criteria - Querying with Eager Fetching
//%% - duplicate result 
def criteria = Author.createCriteria()
def tasks = criteria.list{
    eq "name", "Stephen King"
    join 'books'    
    order 'age', 'asc'
}

//Notice the usage of the join method: 
//it tells the criteria API to use a JOIN to fetch the named associations with the Task instances. 
//It’s probably best not to use this for one-to-many associations though, 
//because you will most likely end up with duplicate results. 

//%% - Solution of above 
//Instead, use the select fetch mode:

import org.hibernate.FetchMode as FM

def results = Author.withCriteria {
    eq "name", "Stephen King"
    fetchMode "books", FM.SELECT
}


//Although this approach triggers a second query to get the flights association, 
//you will get reliable results - even with the maxResults option.


//fetchMode and join are general settings of the query and can only be specified at the top-level, 
//i.e. you cannot use them inside projections or association constraints.  

//An important point to bear in mind is that if you include associations in the query constraints, 
//those associations will automatically be eagerly loaded.


def results = Author.withCriteria {
    eq "name", "Stephen King"
    Flight {
        like "title", "BA%"
    }
}
//the flights collection would be loaded eagerly via a join even though the fetch mode has not been explicitly set.








///*** The Spring Framework and Groovy -Advanced 

///* Spring with Groovy  -Using Grails bean- builder 
//http://docs.grails.org/3.3.5/api/grails/spring/BeanBuilder.html

///*Example of hibernate data Source 
import org.hibernate.SessionFactory
import org.apache.tomcat.jdbc.pool.DataSource
import org.apache.commons.dbcp.BasicDataSource
import org.grails.orm.hibernate.ConfigurableLocalSessionFactoryBean
import org.springframework.context.ApplicationContext
import grails.spring.BeanBuilder


BeanBuilder builder = new BeanBuilder()
builder.beans {
   dataSource(DataSource) {                  // <--- invokeMethod
      driverClassName = "org.h2.Driver"
      url = "jdbc:h2:mem:grailsDB"
      username = "sa"                            // <-- setProperty
      password = ""
      settings = [mynew:"setting"]
  }
  dataSource2(BasicDataSource) {
        driverClassName = "org.h2.Driver"
        url = "jdbc:h2:mem:grailsDB"
        username = "sa"
        password = ""
    }
  sessionFactoryNew(SessionFactory) {
         dataSource = dataSource                 // <-- getProperty for retrieving refs
  }
  myService(MyService) {
      nestedBean = { AnotherBean bean->          // <-- setProperty with closure for nested bean
              dataSource = dataSource
      }
  }
  sessionFactory(ConfigurableLocalSessionFactoryBean) {
        dataSource = ref('dataSource2')
        hibernateProperties = ["hibernate.hbm2ddl.auto": "create-drop",
                               "hibernate.show_sql":     "true"]
    }
 }

//Certain special properties related to bean management can also be set by the builder, 
sessionFactory(ConfigurableLocalSessionFactoryBean) { bean ->
    // Autowiring behaviour. The other option is 'byType'. <<autowire>>
    bean.autowire = 'byName'
    // Sets the initialisation method to 'init'. [init-method]
    bean.initMethod = 'init'
    // Sets the destruction method to 'destroy'. [destroy-method]
    bean.destroyMethod = 'destroy'
    // Sets the scope of the bean. <<scope>>
    bean.scope = 'request'
    dataSource = ref('dataSource')
    hibernateProperties = ["hibernate.hbm2ddl.auto": "create-drop",
                           "hibernate.show_sql":     "true"]
}

///Loading Bean Definitions from the File System
//to load external Groovy scripts that define beans 
//using the same path matching syntax defined here
def bb = new BeanBuilder()
bb.loadBeans("classpath:*SpringBeans.groovy")

def applicationContext = bb.createApplicationContext()

//Here the BeanBuilder loads all Groovy files on the classpath ending with SpringBeans.groovy 
//and parses them into bean definitions. 
//An example script can be seen below:

import org.apache.commons.dbcp.BasicDataSource
import org.grails.orm.hibernate.ConfigurableLocalSessionFactoryBean

beans {

    dataSource(BasicDataSource) {
        driverClassName = "org.h2.Driver"
        url = "jdbc:h2:mem:grailsDB"
        username = "sa"
        password = ""
    }

    sessionFactory(ConfigurableLocalSessionFactoryBean) {
        dataSource = dataSource
        hibernateProperties = ["hibernate.hbm2ddl.auto": "create-drop",
                               "hibernate.show_sql":     "true"]
    }
}

///Adding Variables to the Binding (Context)
//If you’re loading beans from a script you can set the binding to use by creating a Groovy Binding:

//Then you can access the maxSize and productGroup properties in your DSL files.
def binding = new Binding()
binding.maxSize = 10000
binding.productGroup = 'finance'

def bb = new BeanBuilder()
bb.binding = binding
bb.loadBeans("classpath:*SpringBeans.groovy")

def ctx = bb.createApplicationContext()


///Using Constructor Arguments
bb.beans {
    exampleBean(MyExampleBean, "firstArgument", 2) {
        someProperty = [1, 2, 3]
    }
}
//This configuration corresponds to a MyExampleBean with a constructor that looks like this:
MyExampleBean(String foo, int bar) {
   ...
}

///Configuring the BeanDefinition (Using factory methods)
//The first argument to the closure is a reference to the bean configuration instance, 
//which you can use to configure factory methods and invoke any method 
//on the AbstractBeanDefinition class:

bb.beans {
    exampleBean(MyExampleBean) { bean ->
        bean.factoryMethod = "getInstance"
        bean.singleton = false
        someProperty = [1, 2, 3]
    }
}

//As an alternative 
//you can also use the return value of the bean defining method to configure the bean:

bb.beans {
    def example = exampleBean(MyExampleBean) {
        someProperty = [1, 2, 3]
    }
    example.factoryMethod = "getInstance"
}



///Using Factory beans
//Spring defines the concept of factory beans 
//and often a bean is created not directly from a new instance of a Class, 
//but from one of these factories. 
//In this case the bean has no Class argument 
//and instead you must pass the name of the factory bean to the bean defining method:

bb.beans {

    myFactory(ExampleFactoryBean) {
        someProperty = [1, 2, 3]
    }

    myBean(myFactory) {
        name = "blah"
    }
}

//Another common approach is provide the name of the factory method 
//to call on the factory bean. 
//This can be done using Groovy’s named parameter syntax:
bb.beans {

    myFactory(ExampleFactoryBean) {
        someProperty = [1, 2, 3]
    }
//getInstance method on the ExampleFactoryBean bean will be called to create the myBean bean.
    myBean(myFactory: "getInstance") { 
        name = "blah"
    }
}


///Creating Bean References at Runtime
//Sometimes you don’t know the name of the bean to be created until runtime. 
def beanName = "example"
bb.beans {

    "${beanName}Bean"(MyExampleBean) {
        someProperty = [1, 2, 3]
    }

    anotherBean(AnotherBean) {
        example = ref("${beanName}Bean")
    }
}

//Here the example property of AnotherBean is set using a runtime reference to the exampleBean. 
//The ref method can also be used to refer to beans from a parent ApplicationContext 
//that is provided in the constructor of the BeanBuilder:

ApplicationContext parent = ...//
def bb = new BeanBuilder(parent)
bb.beans {
    anotherBean(AnotherBean) {
        example = ref("${beanName}Bean", true) //true specifies that the reference will look for the bean in the parent context.
    }
}


///Using Anonymous (Inner) Beans

//You can use anonymous inner beans by setting a property of the bean 
//to a block that takes an argument that is the bean type:

bb.beans {

    marge(Person) {
        name = "Marge"
        husband = { Person p ->
            name = "Homer"
            age = 45
            props = [overweight: true, height: "1.8m"]
        }
        children = [ref('bart'), ref('lisa')]
    }

    bart(Person) {
        name = "Bart"
        age = 11
    }

    lisa(Person) {
        name = "Lisa"
        age = 9
    }
}

//Alternatively if you have a factory bean you can omit the type 
//and just use the specified bean definition instead to setup the factory:

bb.beans {

    personFactory(PersonFactory)

    marge(Person) {
        name = "Marge"
        husband = { bean ->
            bean.factoryBean = "personFactory"
            bean.factoryMethod = "newInstance"
            name = "Homer"
            age = 45
            props = [overweight: true, height: "1.8m"]
        }
        children = [ref('bart'), ref('lisa')]
    }
}

///Abstract Beans and Parent Bean Definitions
//To create an abstract bean definition define a bean without a Class parameter:

class HolyGrailQuest {
    def start() { println "lets begin" }
}

class KnightOfTheRoundTable {

    String name
    String leader
    HolyGrailQuest quest

    KnightOfTheRoundTable(String name) {
        this.name = name
    }

    def embarkOnQuest() {
        quest.start()
    }
}

import grails.spring.BeanBuilder

def bb = new BeanBuilder()
bb.beans {
    abstractBean {
        leader = "Lancelot"
    }
    ...
}

//Here we define an abstract bean that has a leader property with the value of "Lancelot". 
//To use the abstract bean set it as the parent of the child bean:

bb.beans {
    ...
    quest(HolyGrailQuest)

    knights(KnightOfTheRoundTable, "Camelot") { bean ->
        bean.parent = abstractBean
        quest = ref('quest')
    }
}

//When using a parent bean you must set the parent property of the bean 
//before setting any other properties on the bean!

//If you want an abstract bean that has a Class specified you can do it this way:
import grails.spring.BeanBuilder

def bb = new BeanBuilder()
bb.beans {

    abstractBean(KnightOfTheRoundTable) { bean ->
        bean.'abstract' = true
        leader = "Lancelot"
    }

    quest(HolyGrailQuest)

    knights("Camelot") { bean ->
        bean.parent = abstractBean
        quest = quest
    }
}

///Using Spring Namespaces
//Since Spring 2.0, users of Spring have had easier access to key features via XML namespaces. 
//You can use a Spring namespace in BeanBuilder by declaring it with this syntax:

xmlns context:"http://www.springframework.org/schema/context"

//then invoking a method that matches the names of the Spring namespace tag 
//and its associated attributes:
context.'component-scan'('base-package': "my.company.domain")

//Example:  looking up a JNDI resource:
xmlns jee:"http://www.springframework.org/schema/jee"
jee.'jndi-lookup'(id: "dataSource", 'jndi-name': "java:comp/env/myDataSource")

///AOP support 

class Person {

    int age
    String name

    void birthday() {
        ++age;
    }
}

class BirthdayCardSender {

    List peopleSentCards = []

    void onBirthday(Person person) {
        peopleSentCards << person
    }
}

//define an aspect that uses a pointcut to detect whenever the birthday() method is called:
xmlns aop:"http://www.springframework.org/schema/aop"

fred(Person) {
    name = "Fred"
    age = 45
}

birthdayCardSenderAspect(BirthdayCardSender)

aop {
    config("proxy-target-class": true) {
        aspect(id: "sendBirthdayCard", ref: "birthdayCardSenderAspect") {
            after method: "onBirthday",
            pointcut: "execution(void ..Person.birthday()) and this(person)"
        }
    }
}






///Example - Code 
@Grab(group='org.slf4j', module='slf4j-simple', version='1.7.5')
@Grab(group='org.grails', module='grails-web', version='2.3.11')

import grails.spring.BeanBuilder
import groovy.transform.ToString

class Login {
    def authorize(User user){
        if(user.credentials.username == "John" && user.credentials.password == "Doe"){
            "${user.greetings} ${user.credentials.username}"
        }else
            "You are not ${user.greetings}"
    }
}

@ToString(includeNames=true)
class Credentials{
    String username
    String password
}

@ToString(includeNames=true)
class User{
    Credentials credentials
    String greetings
}


def bb = new BeanBuilder()

//all Bean DSL can be used 
//eg bean.autowire = 'byName', extbean=ref('extBean') etc 
bb.beans {
    login(Login)
    user(User){ bean ->
        credentials = new Credentials(username:"John", password:"Doe")
        greetings = 'Welcome!!'
    }

}

def ctx = bb.createApplicationContext()

def u = ctx.getBean("user")
println u

def l = ctx.getBean("login")
println l.authorize(u)



///OR directly with Spring Framework 

@Grab('org.springframework:spring-context:4.0.0.RELEASE')

import org.springframework.context.support.GenericApplicationContext
import org.springframework.beans.factory.groovy.GroovyBeanDefinitionReader


def ctx = new GenericApplicationContext()
def reader = new GroovyBeanDefinitionReader(ctx)

reader.beans {
    login(Login)
    user(User){
        credentials = new Credentials(username:"John", password:"Doe")
        greetings = 'Welcome!!'
    }

}

ctx.refresh()

def u = ctx.getBean("user")
println u

def l = ctx.getBean("login")
println l.authorize(u)




///* Spring AOP with Groovy  -Using Grails bean- builder 
//AOP is a hooking techniques for methods of class eg calling one hook before or after or around a method 
//AOP syntax 
execution(modifiers-pattern?  //public, private , protect 
    ret-type-pattern          //JAVATYPE or * for any 
    declaring-type-pattern?name-pattern(param-pattern) 
    throws-pattern?)

declaring-type-pattern?name-pattern     
    package_name.class_name.method_name 
    any place * mean any_name, .. means under any hierarchy
param-pattern   
    () for no arg, 
    (*) for one arg_of_any_type, 
    (..) any_number_of_arg, 
    (*,String) for two arg with first arg * and 2nd arg String , 
    so on 

•the execution of any public method:
    execution(public * *(..))
•the execution of any method with a name beginning with "set":
    execution(* set*(..))
•the execution of any method defined by the AccountService interface:
    execution(* com.xyz.service.AccountService.*(..))
•the execution of any method defined in the service package:
    execution(* com.xyz.service.*.*(..))
•the execution of any method defined in the service package or a sub-package:
    execution(* com.xyz.service..*.*(..))
•any join point (method execution only in Spring AOP) 
    within the service package:
    within(com.xyz.service.*)
•any join point (method execution only in Spring AOP) 
    within the service package or a sub-package:
    within(com.xyz.service..*)
•any join point (method execution only in Spring AOP) where the proxy implements the AccountService interface:
    this(com.xyz.service.AccountService)
•any join point (method execution only in Spring AOP) where the target object implements the AccountService interface:
    target(com.xyz.service.AccountService)
•any join point (method execution only in Spring AOP) which takes a single parameter, and where the argument passed at runtime is Serializable:
    args(java.io.Serializable)
•any join point (method execution only in Spring AOP) where the target object has an @Transactional annotation:
    @target(org.springframework.transaction.annotation.Transactional)
•any join point (method execution only in Spring AOP) where the declared type of the target object has an @Transactional annotation:
    @within(org.springframework.transaction.annotation.Transactional)
•any join point (method execution only in Spring AOP) where the executing method has an @Transactional annotation:
    @annotation(org.springframework.transaction.annotation.Transactional)
•any join point (method execution only in Spring AOP) which takes a single parameter, and where the runtime type of the argument passed has the @Classified annotation:
    @args(com.xyz.security.Classified)
•any join point (method execution only in Spring AOP) on a Spring bean named 'tradeService':
    bean(tradeService)
•any join point (method execution only in Spring AOP) on Spring beans having names that match the wildcard expression '*Service':
    bean(*Service)

///aop.* syntax and method signature 
<aop:before method="logBefore" pointcut-ref="pointCutBefore" />
public void logBefore(JoinPoint joinPoint) 


<aop:after method="logAfter" pointcut-ref="pointCutAfter" />
public void logAfter(JoinPoint joinPoint) 

<aop:after-returning method="logAfterReturning" returning="result"  pointcut-ref="pointCutAfterReturning" />
public void logAfterReturning(JoinPoint joinPoint, Object result) 

<aop:after-throwing method="logAfterThrowing" throwing="error"   pointcut-ref="pointCutAfterThrowing"  />
public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {

<aop:around method="logAround" pointcut-ref="pointCutAround"  />
public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {

//Example implementation 
Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
		println("logAround() is running!")
        println("hijacked class : " +joinPoint.target.class.name)
		println("hijacked method : " + joinPoint.signature.name)
		println("hijacked arguments : " + joinPoint.args.join(" ") )		
		println("Around before is running!")
		Object retVal = joinPoint.proceed(joinPoint.args)
		println("Around after is running!")
        return retVal
	}


//Example
@Grab(group='org.slf4j', module='slf4j-simple', version='1.7.5')
@Grab(group='org.grails', module='grails-web', version='2.3.4')
import grails.spring.BeanBuilder
import groovy.transform.ToString

import org.aspectj.lang.JoinPoint;
import java.util.Arrays;



class LoggerInterceptor {     

    public void logBefore(JoinPoint joinPoint){
        String logMessage = String.format("Beginning of each method: %s.%s(%s)",
        joinPoint.target.class.name,
        joinPoint.signature.name,
        joinPoint.args.join(" "));
        println(logMessage);
    }
}

class Pojo {
    private String one;
    private int two;
    private double three;
     
    public String getOne() { return one; }
    public void setOne(String one) { this.one = one; }
    public int getTwo() { return two; }
    public void setTwo(int two) { this.two = two; }
    public double getThree() { return three; }
    public void setThree(double three) { this.three = three; }
 
    @Override
    public String toString() {
        return "POJO [one=" + one + ", two=" + two + ", three=" + three + "]";
    }
}
 

def bb = new BeanBuilder()

//all Bean DSL can be used 
//eg bean.autowire = 'byName', extbean=ref('extBean') etc 
bb.beans  {
    xmlns aop:"http://www.springframework.org/schema/aop"
    aspectBean(LoggerInterceptor)
    pojo(Pojo){
        one = "one"
        two = 2
        three = 3.0d
    }
    aop.config("proxy-target-class":true) {    
        aop.aspect(ref: "aspectBean"){
            aop.pointcut(id: "pointCutBefore",  expression:"execution(* Pojo.set*(..))")
            aop.before(method:"logBefore", "pointcut-ref" :"pointCutBefore")
        }
       
    }
 }   


def ctx = bb.createApplicationContext()

def u = ctx.getBean("pojo")
println u

u.setOne("TWO")
u.setTwo(35)
u.setThree(34.0d)
println u





///Refreshable Bean 
//Does not have direct grails beanbuilder feature 
//Dynamic language Beans can be refreshed and new definition gets applied 

//Example 
package examples;

public interface Messenger {
    String getMessage();
}

interface BookingService{
    void setMessenger(Messenger messenger);
    void processBooking();
}

public class DefaultBookingService implements BookingService {

    private Messenger messenger;

    public void setMessenger(Messenger messenger) {
        this.messenger = messenger;
    }

    public void processBooking() {
        // use the injected Messenger object...
    }
}


//Dynamic file: resources/Messenger.groovy
//would be monitored by Spring 
package groovy.refresh;

// import the Messenger interface (written in Java) that is to be implemented
import examples

// define the implementation in Groovy
class GroovyMessenger implements Messenger {

    String message
}

//file: resources/beans.xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:lang="http://www.springframework.org/schema/lang"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-3.0.xsd http://www.springframework.org/schema/lang http://www.springframework.org/schema/lang/spring-lang-3.0.xsd">
    <!-- this is the bean definition for the Groovy-backed Messenger implementation -->
    <lang:groovy id="messenger" script-source="classpath:Messenger.groovy">
        <lang:property name="message" value="I Can Do The Frug" />
    </lang:groovy>

    <!-- an otherwise normal bean that will be injected by the Groovy-backed Messenger -->
    <bean id="bookingService" class="examples.DefaultBookingService">
        <property name="messenger" ref="messenger" />
    </bean>

</beans>

/// a dynamic-language-backed bean can monitor changes in its underlying source file resource, 
//and then reload itself when the dynamic language source file is changed 
//(for example when a developer edits and saves changes to the file on the filesystem). 
<beans>

    <!-- this bean is now 'refreshable' due to the presence of the 'refresh-check-delay' attribute -->
    <lang:groovy id="messenger"
          refresh-check-delay="5000" <!-- switches refreshing on with 5 seconds between checks -->
          script-source="classpath:Messenger.groovy">
        <lang:property name="message" value="I Can Do The Frug" />
    </lang:groovy>

    <bean id="bookingService" class="x.y.DefaultBookingService">
        <property name="messenger" ref="messenger" />
    </bean>

</beans>

//Example - Usage 
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import examples.Messenger;

public final class Boot {

    public static void main(final String[] args) throws Exception {

        ApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
        Messenger messenger = (Messenger) ctx.getBean("messenger");
        System.out.println(messenger.getMessage());
        // pause execution while I go off and make changes to the source file...
        System.in.read();
        System.out.println(messenger.getMessage());
    }
}

//Then modify the Meesanger code and then wait for refresh-check-delay to the modified ode 
package org.springframework.scripting

class GroovyMessenger implements Messenger {

    private String message = "Bingo"

    public String getMessage() {
        // change the implementation to surround the message in quotes
        return "'" + this.message + "'"
    }

    public void setMessage(String message) {
        this.message = message
    }
}


///Inline dynamic language source files
//for dynamic language source files that are embedded directly in Spring bean definitions. 

<lang:groovy id="messenger">
    <lang:inline-script>
package org.springframework.scripting.groovy;

import org.springframework.scripting.Messenger

class GroovyMessenger implements Messenger {

    String message
}
    </lang:inline-script>
    <lang:property name="message" value="I Can Do The Frug" />
</lang:groovy>



///Understanding Constructor Injection in the context of dynamic-language-backed beans
//it is not (currently) possible to supply constructor arguments to dynamic-language-backed beans 
//(and hence constructor-injection is not available for dynamic-language-backed beans). 

//the following mixture of code and configuration will not work.

// from the file 'Messenger.groovy'
package org.springframework.scripting.groovy;

import org.springframework.scripting.Messenger

class GroovyMessenger implements Messenger {

    GroovyMessenger() {}

    // this constructor is not available for Constructor Injection
    GroovyMessenger(String message) {
        this.message = message;
    }

    String message

    String anotherMessage
}

<lang:groovy id="badMessenger"
    script-source="classpath:Messenger.groovy">

    <!-- this next constructor argument will *not* be injected into the GroovyMessenger -->
    <!--     in fact, this isnot even allowed according to the schema -->
    <constructor-arg value="This will *not* work" />
    
    <!-- only property values are injected into the dynamic-language-backed object -->
    <lang:property name="anotherMessage" value="Passed straight through to the dynamic-language-backed object" />

</lang>








///*** Http rest api with HTTPBuilder - Advanced 
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
//Auto parsing 
def result = httpBin.get {
    request.uri.path = '/get'
}
    
println("Your ip address is: ${result.origin}")
//println( JsonOutput.prettyPrint(response.json.toString()) )


///The HttpBuilder class has request-related configuration methods 
//for each of the supported request verbs, GET, HEAD, DELETE, PATCH, POST, PUT, OPTIONS and TRACE. 
//Each request verb method has a synchronous and asynchronous version 
//- the asynchronous versions are suffixed with Async, (e.g. headAsync) 
//and they return a java.util.concurrent.CompletableFuture 

CompletableFuture future = HttpBuilder.configure {
    request.uri = 'http://localhost:1234/somthing'
}.getAsync()

Object result = future.get()


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

//With query 
HttpBuilder.configure {
    request.uri = 'http://localhost:9191'
}.get {
    request.uri.path = '/person'
    request.uri.query = [name: 'Bob']
}

///Request - verb(Class,Closure)
//The configuration for the request will come from the merging of the client and request configurations. 
//The response content will be cast as the specified type if possible 
//- a response parser may be required to convert the response to an appropriate type.


@Grab('io.github.http-builder-ng:http-builder-ng-core:1.0.3')

import static groovyx.net.http.HttpBuilder.configure
import groovy.json.JsonSlurper

int count = configure {
    request.uri = 'http://api.open-notify.org'
}.get(Integer){
    request.uri.path = '/astros.json'
    response.parser('application/json'){ cc, fs->
        new JsonSlurper().parse(fs.inputStream).number
    }
}

println "There are $count astronauts in space"

///Parsers
//The response body content resulting form a request is parsed based on the response content-type. 
//Content parsers may be configured using the 
//HttpConfig.Response.parser(String, Closure(ChainedHttpConfig, FromServer)) method, 
//The function (or Closure) accepts a ChainedHttpConfig object, and a FromServer instance 
//and returns the parsed Object. 
Date date = HttpBuilder.configure {
    request.uri = 'http://localhost:1234/currenttime'
}.get(Date){
    response.parser('text/date-time'){ ChainedHttpConfig cfg, FromServer fs ->
        Date.parse('MM/dd/yyyy HH:mm:ss', fs.inputStream.text)
    }
}


//Some default parsers are provided:
    HTML (when either the 'org.jsoup:jsoup:' or 'net.sourceforge.nekohtml:nekohtml:' library is on the classpath),
    JSON (when either Groovy or the com.fasterxml.jackson.core:jackson-databind:2.8.1 library is on the classpath)
    CSV (when the com.opencsv:opencsv:3.8 library is on the classpath)
    XML (without any additional libraries)
    TEXT (without any additional libraries)


///HttpConfig.Response
//The HttpConfig.Response interface provides five types of response status handlers.

//The first three are the when methods. 
//The when methods configure a handler to be called 
//when the response status matches the specified status value (as a String, Integer, or HttpConfig.Status value). 
//When the handler is called, it is executed and its return value is used as the return value for the request.
//The Closure will be called with the (FromServer Object)
//whereas Object instance will be the response body if one is present.

HttpBuilder.configure {
    request.uri = 'http://localhost:8181'
}.head {
    response.when(200){ FromServer fs ->
        'ok'
    }
}
//The other two status handlers are the success and failure methods:
    The success methods accept either a Groovy Closure(FromServer,Object) as a handler. 
    The handler is then configured as a when handler for response status code values less than 400.
    The failure methods accept either a Groovy Closure(FromServer,Object) as a handler, 
    which is then configured as a when handler for response status code values greater than or equal to 400.

HttpBuilder.configure {
    request.uri = 'http://localhost:8181'
}.head {
    response.success { FromServer fs ->
        'ok'
    }
}


///Cookies
    cookie(String name, String value) - Adds a cookie with the specified name and value to the request.
    cookie(String name, String value, Date expires) - Adds a cookie with the specified name, value and expiration date to the request (as Date).
    cookie(String name, String value, LocalDateTime expires) - Adds a cookie with the specified name, value and expiration date to the request (as LocalDateTime).

//Cookies may be configured globally or per-request:

HttpBuilder.configure {
    request.cookie('user-id', 'some-identifier')
}.head {
    request.cookie('user-session', 'SDF@$TWEFSDT', new Date()+30)
}


///Authentication
//For BASIC:

HttpBuilder.configure {
    request.uri = 'http://localhost:10101'
    request.auth.basic 'admin', 'myp@$$w0rd'
}

//For DIGEST:
HttpBuilder.configure {
    request.uri = 'http://localhost:10101'
    request.auth.digest 'admin', 'myp@$$w0rd'
}

///Content
    setAccept(Iterable<String>) and setAccept(String[]) - specifies the Accept header value.
    setContentType(String) - specifies the Content-Type header value.
    setCharset(Charset) - specifies the Accept-Charset header value.
    setBody(Object) - specifies the body content for the request.
//Note that the body content and content-type come into play when configuring the request encoders; 
//example
HttpBuilder.configure {
    request.uri = 'http://localhost:8675'
    request.contentType = 'text/plain'
    request.charset = Charsets.UTF_8
}.post {
    request.body = 'Let them eat content!'
}


///Headers
HttpBuilder.configure {
    request.headers['Global-Header'] = 'header-for-all'
}.post {
    request.headers['Custom-Header'] = 'some-custom-value'
}

///Multipart
//HttpBuilder-NG supports multipart request content such as file uploads, 
//with either the generic MultipartEncoder or one of the client-specific encoders. 

//For example, the OkHttpBuilder may use the OkHttpEncoders.&multipart encoder:
//The available multipart encoders:
    groovyx.net.http.CoreEncoders::multipart 
        a generic minimalistic multipart encoder for use with the core Java client 
        or any of the others (requires the JavaMail API).
    groovyx.net.http.OkHttpEncoders::multipart 
        the encoder using OkHttp-specific multipart encoding.
    groovyx.net.http.ApacheEncoders::multipart 
        the encoder using Apache client specific multipart encoding.
//EXample - POST the content of the file, someFile along with the specified name field 
//to the server as a multipart/form-data request. 
import groovyx.net.http.OkHttpBuilder
import groovyx.net.http.*
import static groovyx.net.http.MultipartContent.multipart

File someFile = // ...

OkHttpBuilder.configure {
    request.uri = 'http://example.com'
}.post {
    request.uri.path = '/upload'
    request.contentType = 'multipart/form-data'
    request.body = multipart {
        field 'name', 'This is my file'
        part 'file', 'myfile.txt', 'text/plain', someFile
    }
    request.encoder 'multipart/form-data', OkHttpEncoders.&multipart
}






////Sending Form Data (POST)

@Grab('io.github.http-builder-ng:http-builder-ng-core:1.0.3')
import static groovyx.net.http.HttpBuilder.configure
import static groovyx.net.http.ContentTypes.JSON
import groovyx.net.http.*
import static groovy.json.JsonOutput.prettyPrint

def result = configure {
    request.uri = 'http://httpbin.org'
    request.contentType = JSON[0]
}.post {
    request.uri.path = '/post'
    request.body = [id: '234545', label: 'something interesting']
    request.contentType = 'application/x-www-form-urlencoded'
    request.encoder 'application/x-www-form-urlencoded', NativeHandlers.Encoders.&form
}

println "You submitted: id=${result.form.id} and label=${result.form.id}"


///Sending/Receiving JSON Data (POST)

@Grab('io.github.http-builder-ng:http-builder-ng-core:1.0.3')
import static groovyx.net.http.HttpBuilder.configure
import static groovyx.net.http.ContentTypes.JSON
import groovyx.net.http.*

@groovy.transform.Canonical
class ItemScore {
    String item
    Long score
}

ItemScore itemScore = configure {
    request.uri = 'http://httpbin.org'
    request.contentType = JSON[0]
    response.parser(JSON[0]) { config, resp ->
        new ItemScore(NativeHandlers.Parsers.json(config, resp).json)
    }
}.post(ItemScore) {
    request.uri.path = '/post'
    request.body = new ItemScore('ASDFASEACV235', 90786)
}

println "Your score for item (${itemScore.item}) was (${itemScore.score})."

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






///***  Ratpack
//reactive, asynchronous, non-blocking Java 8 libraries that facilitate rapid web application development.
//Uses Netty for embedded server, hence highly scalable 
//No Servlets
//Functionality is provided via "modules"
        Core (HTTP/Execution)
        Sessions/Auth Pac4j
        Database (HikariCP)
        RxJava/Hystrix
        Templating (Groovy’s MarkupTemplateEngine, Handlebars, Thymeleaf
        Dependency Injection (Guice/Spring Boot)     

//ratpack.groovy with the following content:
@Grapes([
  @Grab('io.ratpack:ratpack-groovy:1.5.4'),
  @Grab('org.slf4j:slf4j-simple:1.7.25')
])
import static ratpack.groovy.Groovy.ratpack

ratpack {
    handlers {
        get {
            render "Hello World!"
        }
        get(":name") {
            render "Hello $pathTokens.name!"
        }
        get('foo')  {
          render "bar"
        }
    }
}

$ groovy ratpack.groovy
//The server will be available via http://localhost:5050/.

//OR using embedded Server 
import ratpack.server.RatpackServer;

public class Main {
 public static void main(String... args) throws Exception {
   RatpackServer.start(server -> server 
         .handlers(chain -> chain
           .get(ctx -> ctx.render("Hello World!"))
           .get(":name", ctx -> ctx.render("Hello " + ctx.getPathTokens().get("name") + "!"))     
         )
   );
 }
}
//Note Context ctx has access to request and respose for manipulation 
Response res = ctx.response 
Request req = ctx.request 
//https://ratpack.io/manual/current/api/ratpack/handling/Context.html
def params = req.queryParams 
//https://ratpack.io/manual/current/api/ratpack/http/Request.html
res.contentType("application/json").send("{'ok':1}")
//https://ratpack.io/manual/current/api/ratpack/http/Response.html


      
///Request Response Interaction
//to set CORS headers on each response.

@Grab('io.ratpack:ratpack-groovy:1.3.3')

import static ratpack.groovy.Groovy.ratpack
import ratpack.http.MutableHeaders

ratpack {
  handlers {
    get {
      MutableHeaders headers = response.headers 
      headers.set('Access-Control-Allow-Origin', '*') 
      headers.set('Access-Control-Allow-Headers', 'x-requested-with, origin, content-type, accept') 
      render 'Hej GR8Conf EU 2016!'
    }
  }
}

//refactor by extracting the CORS setting logic to its own handler.

@Grab('io.ratpack:ratpack-groovy:1.3.3')
import static ratpack.groovy.Groovy.ratpack
import ratpack.http.MutableHeaders

ratpack {
  handlers {
    all { 
      MutableHeaders headers = response.headers
      headers.set('Access-Control-Allow-Origin', '*')
      headers.set('Access-Control-Allow-Headers', 'x-requested-with, origin, content-type, accept')
      next() 
    }
    get {
      render 'Hej GR8Conf EU 2016!'
    }
  }
}

//to migrate handling logic to discrete classes or groups of classes 
@Grab('io.ratpack:ratpack-groovy:1.3.3')

import static ratpack.groovy.Groovy.ratpack
import ratpack.http.MutableHeaders

import groovy.transform.CompileStatic
import ratpack.groovy.handling.GroovyContext
import ratpack.groovy.handling.GroovyHandler

@CompileStatic
class CORSHandler extends GroovyHandler { 
  @Override
  protected void handle(GroovyContext context) {
    MutableHeaders headers = context.response.headers
    headers.set('Access-Control-Allow-Origin', '*')
    headers.set('Access-Control-Allow-Headers', 'x-requested-with, origin, content-type, accept')
    context.next()
  }
}

ratpack {
  handlers {
    all(new CORSHandler()) 
    get {
      render 'Hej GR8Conf EU 2016!'
    }
  }
}

///Migrating to Gradle
//build.gradle

plugins { 
  id 'io.ratpack.ratpack-groovy' version '1.3.3' 
}

repositories {
  jcenter() 
}

//also move our ratpack.groovy file to $projectDir/src/ratpack/ratpack.groovy 
//and put CORSHandler.groovy in the $projectDir/src/main/groovy location for regular Groovy classes. 
//The $projectDir/src/ratpack directory is a convention used by Ratpack 
//as a common directory for holding configuration files, templates, static assets 
//and your main Ratpack application script.

example-03-gradle
│   build.gradle
│
└───src
    ├───main
    │   └───groovy
    │           CORSHandler.groovy
    │
    └───ratpack
            ratpack.groovy

$ gradle run -t  //continuous mode 
$ curl -s localhost:5050

///Server Config 
//https://ratpack.io/manual/current/api/ratpack/server/ServerConfig.html
//https://ratpack.io/manual/current/api/ratpack/server/ServerConfigBuilder.html

//Use .embedded() for dev server, use builder() for prod server 
RatpackServer rat = RatpackServer.start{server ->  
        server.serverConfig(ServerConfig.embedded())            // base server configuration (e.g. port) - optional
        .registryOf(r -> r.add(String.class, "foo"))      // registry of supporting objects - optional
         .handlers{chain -> 
           chain.get{ctx -> ctx.render("Hello World!")}
                .get(":name"){ ctx -> ctx.render("Hello " + ctx.getPathTokens().get("name") + "!")}     
                .get("a", ctx -> ctx.render(ctx.get(String.class) + " 1")) //a , returns "foo 1"
         }
   }
//Important method of ServerCOnfig 
static ServerConfigBuilder 	builder() 
static ServerConfigBuilder 	embedded()
    Creates a builder configured for development mode and an ephemeral port.
java.net.InetAddress 	getAddress()
    The address of the interface that the application should bind to.
FileSystemBinding 	getBaseDir()
    The base dir of the application, which is also the initial FileSystemBinding.
//IMportant methods of ServerConfigBuilder
ServerConfigBuilder 	add(ConfigSource configSource)
    Adds a configuration source.
ServerConfigBuilder 	address(java.net.InetAddress address)
    Sets the address to bind to.
ServerConfigBuilder 	port(int port)
    Sets the port to listen for requests on.
ServerConfigBuilder 	baseDir(java.nio.file.Path baseDir)
    Sets the root of the filesystem for the application.
ServerConfig 	build()
    Builds the server config.

    
    
///Json handling -  Jackson
//com.fasterxml.jackson.core:jackson-databind:2.9.4
//As of Ratpack 1.5.4 is built against (and depends on) Jackson Core 2.9.4.

//The Jackson integration adds a Renderer for rendering objects as JSON.
//The Jackson.json() method can be used to wrap any object (serializable by Jackson) 
//for use with the Context.render() method.

import ratpack.test.embed.EmbeddedApp;
import ratpack.http.client.ReceivedResponse;

import static ratpack.jackson.Jackson.json;
import static org.junit.Assert.*;

public class Example {

 public static class Person {
   private final String name;
   public Person(String name) {
     this.name = name;
   }
   public String getName() {
     return name;
   }
 }

 public static void main(String... args) throws Exception {
   EmbeddedApp.of(s -> s
     .handlers(chain ->
       chain.get(ctx -> ctx.render(json(new Person("John"))))
     )
   ).test(httpClient -> {
     ReceivedResponse response = httpClient.get();
     assertEquals("{\"name\":\"John\"}", response.getBody().getText());
     assertEquals("application/json", response.getBody().getContentType().getType());
   });
 }
}

///Reading JSON requests
//The Jackson.jsonNode() and Jackson.fromJson() methods can be used 
//to create objects to be used with the Context.parse() method.

import ratpack.guice.Guice;
import ratpack.test.embed.EmbeddedApp;
import ratpack.http.client.ReceivedResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.reflect.TypeToken;

import java.util.List;

import static ratpack.util.Types.listOf;
import static ratpack.jackson.Jackson.jsonNode;
import static ratpack.jackson.Jackson.fromJson;
import static org.junit.Assert.*;

public class Example {

 public static class Person {
   private final String name;
   public Person(@JsonProperty("name") String name) {
     this.name = name;
   }
   public String getName() {
     return name;
   }
 }

 public static void main(String... args) throws Exception {
   EmbeddedApp.of(s -> s
     .handlers(chain -> chain
       .post("asNode", ctx -> {
         ctx.render(ctx.parse(jsonNode()).map(n -> n.get("name").asText()));
       })
       .post("asPerson", ctx -> {
         ctx.render(ctx.parse(fromJson(Person.class)).map(p -> p.getName()));
       })
       .post("asPersonList", ctx -> {
         ctx.render(ctx.parse(fromJson(listOf(Person.class))).map(p -> p.get(0).getName()));
       })
     )
   ).test(httpClient -> {
     ReceivedResponse response = httpClient.requestSpec(s ->
       s.body(b -> b.type("application/json").text("{\"name\":\"John\"}"))
     ).post("asNode");
     assertEquals("John", response.getBody().getText());

     response = httpClient.requestSpec(s ->
       s.body(b -> b.type("application/json").text("{\"name\":\"John\"}"))
     ).post("asPerson");
     assertEquals("John", response.getBody().getText());

     response = httpClient.requestSpec(s ->
       s.body(b -> b.type("application/json").text("[{\"name\":\"John\"}]"))
     ).post("asPersonList");
     assertEquals("John", response.getBody().getText());
   });
 }
}

//The integration adds a no opts parser, 
//which makes it possible to use the Context.parse(Class) and Context.parse(TypeToken) methods.

import ratpack.test.embed.EmbeddedApp;
import ratpack.http.client.ReceivedResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.reflect.TypeToken;

import java.util.List;

import static ratpack.util.Types.listOf;
import static org.junit.Assert.*;

public class Example {

 public static class Person {
   private final String name;
   public Person(@JsonProperty("name") String name) {
     this.name = name;
   }
   public String getName() {
     return name;
   }
 }

 public static void main(String... args) throws Exception {
   EmbeddedApp.of(s -> s
     .handlers(chain -> chain
       .post("asPerson", ctx -> {
         ctx.parse(Person.class).then(person -> ctx.render(person.getName()));
       })
       .post("asPersonList", ctx -> {
         ctx.parse(listOf(Person.class)).then(person -> ctx.render(person.get(0).getName()));
       })
     )
   ).test(httpClient -> {
     ReceivedResponse response = httpClient.requestSpec(s ->
       s.body(b -> b.type("application/json").text("{\"name\":\"John\"}"))
     ).post("asPerson");
     assertEquals("John", response.getBody().getText());

     response = httpClient.requestSpec(s ->
       s.body(b -> b.type("application/json").text("[{\"name\":\"John\"}]"))
     ).post("asPersonList");
     assertEquals("John", response.getBody().getText());
   });
 }
}
///Complex Serving 
@Grab('io.ratpack:ratpack-groovy:1.3.3')

import static ratpack.groovy.Groovy.ratpack
import static groovy.json.JsonOutput.toJson

class User { 1
  String username
  String email
}

def user1 = new User( 2
  username: "ratpack",
  email: "ratpack@ratpack.io"
)
def user2 = new User(
  username: "danveloper",
  email: "danielpwoods@gmail.com"
)

def users = [user1, user2] 3
//Within our handler, we can access the byContent method, 
//which gives us some convenience methods similar to how the handler chain works.

ratpack {
  handlers {
    get("users") {
      byContent { 4
        html { 5
          def usersHtml = users.collect { user ->
            """\
            |<div>
            |<b>Username:</b> ${user.username}
            |<b>Email:</b> ${user.email}
            |</div>
            """.stripMargin()
          }.join()

          render """\
          |<!DOCTYPE html>
          |<html>
          |<head>
          |<title>User List</title>
          |</head>
          |<body>
          |<h1>Users</h1>
          |${usersHtml}
          |</body>
          |</html>
          """.stripMargin()
        }
        json { 6
          render toJson(users)
        }
        xml { 7
          def xmlStrings = users.collect { user ->
            """
            <user>
              <username>${user.username}</username>
              <email>${user.email}</email>
            </user>
            """.toString()
          }.join()
          render "<users>${xmlStrings}</users>"
        }
      }
    }
  }
}


$ curl -H "Accept: application/json" localhost:5050/users
[{"username":"ratpack","email":"ratpack@ratpack.io"},{"username":"danveloper", ↵
"email":"danielpwoods@gmail.com"}]

//Custom content and no match 
get("users") {
  byContent {
    html {
      // ... snipped for brevity ...
    }
    json {
      // ... snipped for brevity ...
    }
    xml {
      // ... snipped for brevity ...
    }
    type("application/vnd.app.custom+json") {
      // ... snipped for brevity ...
    }
    noMatch { 1
      response.status 400
      render "negotiation not possible."
    }
  }
}

$ curl -H "Accept: application/vnd.app.custom+json" localhost:5050/users
{"some_custom_data":"my custom data","type":"custom-users", "users": ↵
[{"username":"ratpack","email":"ratpack@ratpack.io"},{"username":"danveloper", ↵
"email":"danielpwoods@gmail.com"}]}

///Request headers
//The Headers interface allows you to retrieve header information associated with the incoming request.

import ratpack.http.client.ReceivedResponse;
import ratpack.test.embed.EmbeddedApp;
import ratpack.http.Headers;

public class Example {
 public static void main(String... args) throws Exception {
   EmbeddedApp
     .fromHandler(ctx -> {
       Headers headers = ctx.getRequest().getHeaders();
       String clientHeader = headers.get("Client-Header");
       ctx.getResponse().send(clientHeader);
     })
     .test(httpClient -> {
       ReceivedResponse receivedResponse = httpClient
         .requestSpec(requestSpec ->
             requestSpec.getHeaders().set("Client-Header", "From Client")
         ).get();

       assertEquals("From Client", receivedResponse.getBody().getText());
     });
 }
}

///Response headers
//The MutableHeaders provides functionality that enables you to manipulate response headers
// via the response object Response#getHeaders().

import ratpack.http.MutableHeaders;
import ratpack.http.client.ReceivedResponse;
import ratpack.test.embed.EmbeddedApp;

public class Example {
 public static void main(String... args) throws Exception {
   EmbeddedApp
     .fromHandler(ctx -> {
       MutableHeaders headers = ctx.getResponse().getHeaders();
       headers.add("Custom-Header", "custom-header-value");
       ctx.getResponse().send("ok");
     })
     .test(httpClient -> {
       ReceivedResponse receivedResponse = httpClient.get();
       assertEquals("custom-header-value", receivedResponse.getHeaders().get("Custom-Header"));
     });
 }
}

///Cookies from an inbound request
//To retrieve the value of a cookie, you can use Request#oneCookie(String).

import ratpack.http.client.ReceivedResponse;
import ratpack.test.embed.EmbeddedApp;

import static org.junit.Assert.assertEquals;

public class Example {
 public static void main(String... args) throws Exception {
   EmbeddedApp.fromHandler(ctx -> {
     String username = ctx.getRequest().oneCookie("username");
     ctx.getResponse().send("Welcome to Ratpack, " + username + "!");
   }).test(httpClient -> {
     ReceivedResponse response = httpClient
       .requestSpec(requestSpec -> requestSpec
         .getHeaders()
         .set("Cookie", "username=hbogart1"))
       .get();

     assertEquals("Welcome to Ratpack, hbogart1!", response.getBody().getText());
   });
 }
}

//You can also retrieve a set of cookies via Request#getCookies().

import io.netty.handler.codec.http.cookie.Cookie;
import ratpack.http.client.ReceivedResponse;
import ratpack.test.embed.EmbeddedApp;

import java.util.Set;

import static org.junit.Assert.assertEquals;

public class Example {
 public static void main(String... args) throws Exception {
   EmbeddedApp.fromHandler(ctx -> {
     Set<Cookie> cookies = ctx.getRequest().getCookies();
     assertEquals(1, cookies.size());
     Cookie cookie = cookies.iterator().next();
     assertEquals("username", cookie.name());
     assertEquals("hbogart1", cookie.value());
     ctx.getResponse().send("Welcome to Ratpack, " + cookie.value() + "!");
   }).test(httpClient -> {
     ReceivedResponse response = httpClient
       .requestSpec(requestSpec -> requestSpec
         .getHeaders()
         .set("Cookie", "username=hbogart1"))
       .get();

     assertEquals("Welcome to Ratpack, hbogart1!", response.getBody().getText());
   });
 }
}

///Setting cookies for an outbound response
//You can set cookies to be sent with the response Response#cookie(String, String). 
//To retrieve the set of cookies to be set with the response you may use Response#getCookies().

import ratpack.http.client.ReceivedResponse;
import ratpack.test.embed.EmbeddedApp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Example {
 public static void main(String... args) throws Exception {
   EmbeddedApp.fromHandler(ctx -> {
     assertTrue(ctx.getResponse().getCookies().isEmpty());
     ctx.getResponse().cookie("whiskey", "make-it-rye");
     assertEquals(1, ctx.getResponse().getCookies().size());
     ctx.getResponse().send("ok");
   }).test(httpClient -> {
     ReceivedResponse response = httpClient.get();
     assertEquals("whiskey=make-it-rye", response.getHeaders().get("Set-Cookie"));
   });
 }
}

//If you want to expire a cookie, you can do so with Response#expireCookie().

import ratpack.http.client.ReceivedResponse;
import ratpack.test.embed.EmbeddedApp;

import static org.junit.Assert.assertTrue;

public class Example {
 public static void main(String... args) throws Exception {
   EmbeddedApp.fromHandler(ctx -> {
     ctx.getResponse().expireCookie("username");
     ctx.getResponse().send("ok");
   }).test(httpClient -> {
     ReceivedResponse response = httpClient
       .requestSpec(requestSpec -> requestSpec
           .getHeaders().set("Cookie", "username=lbacall1")
       )
       .get();

     String setCookie = response.getHeaders().get("Set-Cookie");
     assertTrue(setCookie.startsWith("username=; Max-Age=0"));
   });
 }
}

///Sessions -Preparation
buildscript {
  repositories {
    jcenter()
  }
  dependencies {
    classpath "io.ratpack:ratpack-gradle:1.5.0-rc-2"
  }
}

apply plugin: "io.ratpack.ratpack-groovy"

repositories {
  jcenter()
}

dependencies {
  runtime 'org.slf4j:slf4j-simple:1.7.25'
  compile group: 'io.ratpack', name: 'ratpack-session', version: '1.5.0-rc-2' // note: the version is subject to change

  testCompile "org.spockframework:spock-core:1.0-groovy-2.4"
}

///Use session
//All session operations return either a Promise or an Operation. 
//You can use those in your transformation flow as shown.

//Make sure you terminate an old session before starting a new one (see get('start')-handler). 
//This way you make sure that you actually get a new session 
//and don’t just add data to an existing session.


import ratpack.session.Session

import static ratpack.groovy.Groovy.ratpack
import ratpack.session.SessionModule

ratpack {
	bindings {
		module(SessionModule)
	}

	handlers {
		get('start') { Session session ->
			session.terminate().flatMap {
				session.set('keyForDataStoredInSession', 'Hello Session!').promise()
			}.then {
				response.send('I started a session for you.')
			}
		}
		get { Session session ->
			session.get('keyForDataStoredInSession').then {
				if (it.present) {
					response.send("Message: ${it.get()}")
				} else {
					response.send('I have nothing to say to you')
				}
			}
		}
		get('stop') { Session session ->
			session.terminate().then {
				response.send('The session is dead, dead, dead.')
			}
		}
	}
}


///*** Groovy Templates
//Included with Groovy are several template engines:
SimpleTemplateEngine 
    for basic templates
StreamingTemplateEngine 
    functionally equivalent to SimpleTemplateEngine, but can handle strings larger than 64k
GStringTemplateEngine 
    stores the template as writeable closures (useful for streaming scenarios)
XmlTemplateEngine 
    works well when the template and output are valid XML
MarkupTemplateEngine
    a very complete, optimized, template engine

    
//To use a template engine 
1.read in the static text from a file, URL or String, 
2.bind dynamic data to the template engine 
3. get the generated text

//Example 
import groovy.text.*
 
// SimpleTemplateEngine.
def simple = new SimpleTemplateEngine()
def source = '''Dear ${name.capitalize()},
Please respond to this e-mail before ${(now + 7).format("dd-MM-yyyy")}
Kind regards, mrhaki'''
def binding = [now: new Date(109, 11, 1), name: 'hubert Klein Ikkink']
def output = simple.createTemplate(source).make(binding).toString()
 
assert "Dear Hubert Klein Ikkink,\nPlease respond to this e-mail before 08-12-2009\nKind regards, mrhaki" == output
 
// GStringTemplateEngine with out variable.
def gstring = new GStringTemplateEngine()
def gsource = '''Dear <%= name %>,
Text is created for <% if (gstring) out << 'GStringTemplateEngine' else out << 'other template engine'%>.'''
def gbinding = [name: 'mrhaki', gstring: true]
def goutput = gstring.createTemplate(gsource).make(gbinding).toString()
 
assert "Dear mrhaki,\nText is created for GStringTemplateEngine." == goutput
 
// XmlTemplateEngine with gsp:scriplet and gsp:expression tags.
def xmlEngine = new XmlTemplateEngine()
def xml = '''<?xml version="1.0"?>
<users xmlns:gsp='http://groovy.codehaus.org/2005/gsp'>
    <gsp:scriptlet>users.each {</gsp:scriptlet>
        <user id="${it.id}"><gsp:expression>it.name</gsp:expression></user>
    <gsp:scriptlet>}</gsp:scriptlet>
</users>'''
//'
def xmlBinding = [users: [
    new Expando(id: 1, name: 'mrhaki'),
    new Expando(id: 2, name: 'Hubert')]
]
def xmlOutput = xmlEngine.createTemplate(xml).make(xmlBinding).toString()
 
assert '''\
<users>
  <user id='1'>
    mrhaki
  </user>
  <user id='2'>
    Hubert
  </user>
</users>
''' == xmlOutput


///* SimpleTemplateEngine
//allows to use JSP-like scriptlets , script, and EL expressions 
//Inside <%  %>,  and GSTring ${} ,any groovy code can be written 

def text = 'Dear "$firstname $lastname",\nSo nice to meet you in <% print city %>.\nSee you in ${month},\n${signed}'

def binding = ["firstname":"Sam", "lastname":"Pullara", "city":"San Francisco", "month":"December", "signed":"Groovy-Dev"]

def engine = new groovy.text.SimpleTemplateEngine()
def template = engine.createTemplate(text).make(binding)

def result = 'Dear "Sam Pullara",\nSo nice to meet you in San Francisco.\nSee you in December,\nGroovy-Dev'

assert result == template.toString()

///Note :
//For template string literal (not read from file) , To use backlash, use double backlash 
<% print city == "New York" ? "\\"The Big Apple\\"" : city %>

//For  newline in any GString expression or scriptlet 'code' that appears inside a Groovy script, use 
\\n
//Note : A normal "\n" is fine within the static template text itself or in an external template file. 

//To represent an actual backslash in text , use in an external file or in any GString expression or scriptlet 'code'
\\\\


///* StreamingTemplateEngine
//The StreamingTemplateEngine engine is functionally equivalent to the SimpleTemplateEngine, 
//but creates the template using writable closures making it more scalable for large templates. 
//Specifically this template engine can handle strings larger than 64k.

//It uses JSP style <% %> script and <%= %> expression syntax or GString style expressions. 
//The variable 'out' is bound to the writer that the template is being written to.


def text = '''\
Dear <% out.print firstname %> ${lastname},

We <% if (accepted) out.print 'are pleased' else out.print 'regret' %> \
to inform you that your paper entitled
'$title' was ${ accepted ? 'accepted' : 'rejected' }.

The conference committee.'''

def template = new groovy.text.StreamingTemplateEngine().createTemplate(text)

def binding = [
    firstname : "Grace",
    lastname  : "Hopper",
    accepted  : true,
    title     : 'Groovy for COBOL programmers'
]

String response = template.make(binding)

assert response == '''Dear Grace Hopper,

We are pleased to inform you that your paper entitled
'Groovy for COBOL programmers' was accepted.

The conference committee.'''



///* GStringTemplateEngine
//test.template

Dear "$firstname $lastname",
So nice to meet you in <% out << (city == "New York" ? "\\"The Big Apple\\"" : city) %>.
See you in ${month},
${signed}

//Use 'out' instead of 'prin't to support the streaming nature of GStringTemplateEngine.
//Because we have the template in a separate file, there is no need to escape the backslashes. 

def f = new File('test.template')
def engine = new groovy.text.GStringTemplateEngine()
def template = engine.createTemplate(f).make(binding)
println template.toString()



///* XmlTemplateEngine
//XmlTemplateEngine for use in templating scenarios where both the template source 
//and the expected output are intended to be XML. 

//Templates may use the normal ${expression} and $variable notations to insert an arbitrary expression 
// support is also provided for special tags: <gsp:scriptlet> (for inserting code fragments) 
//and <gsp:expression> (for code fragments which produce output).

//Comments and processing instructions will be removed as part of processing 
//and special XML characters such as <, >, " and ' will be escaped using the respective XML notation. 

//The output will also be indented using standard XML pretty printing.

//The xmlns namespace definition for gsp: tags will be removed 
//but other namespace definitions will be preserved (but may change to an equivalent position within the XML tree).


def binding = [firstname: 'Jochen', lastname: 'Theodorou', nickname: 'blackdrag', salutation: 'Dear']
def engine = new groovy.text.XmlTemplateEngine()
def text = '''\
    <document xmlns:gsp='http://groovy.codehaus.org/2005/gsp' xmlns:foo='baz' type='letter'>
        <gsp:scriptlet>def greeting = "${salutation}est"</gsp:scriptlet>
        <gsp:expression>greeting</gsp:expression>
        <foo:to>$firstname "$nickname" $lastname</foo:to>
        How are you today?
    </document>
'''
//'
def template = engine.createTemplate(text).make(binding)
println template.toString()

//OUTPUT 
<document type='letter'>
  Dearest
  <foo:to xmlns:foo='baz'>
    Jochen &quot;blackdrag&quot; Theodorou
  </foo:to>
  How are you today?
</document>


///* The MarkupTemplateEngine
//This template engine is a template engine primarily aimed at generating XML-like markup (XML, XHTML, HTML5, …​), 
//but that can be used to generate any text based content. 

//this one relies on a DSL that uses the builder syntax. 
//regular Groovy code can be used in the template eg cars.each 
def text = '''
xmlDeclaration()
cars {
   cars.each {
       car(make: it.make, model: it.model)
   }
}'''

class Car{
    String make 
    String model
}
model = [cars: [new Car(make: 'Peugeot', model: '508'), new Car(make: 'Toyota', model: 'Prius')]]

def engine = new groovy.text.markup.MarkupTemplateEngine()
def template = engine.createTemplate(text).make(model)
println template.toString()
//OUTPUT 
<?xml version='1.0'?>
<cars><car make='Peugeot' model='508'/><car make='Toyota' model='Prius'/></cars>

///rendering HTML code 
yieldUnescaped '<!DOCTYPE html>'                                                    
html(lang:'en') {                                                                   
    head {                                                                          
        meta('http-equiv':'"Content-Type" content="text/html; charset=utf-8"')      
        title('My page')                                                            
    }                                                                               
    body {                                                                          
        p('This is an example of HTML contents')                                    
    }                                                                               
}   
                                                                                
///Supported methods
yield
	Renders contents, but escapes it before rendering
	Template:
        yield 'Some text with <angle brackets>'
    Output:
        Some text with &lt;angle brackets&gt;
yieldUnescaped
	Renders raw contents. The argument is rendered as is, without escaping.
	Template:
        yieldUnescaped 'Some text with <angle brackets>'
    Output:
        Some text with <angle brackets>

xmlDeclaration
	Renders an XML declaration String. 
    If the encoding is specified in the configuration, it is written in the declaration.	
    Template:
        xmlDeclaration()
    Output:
        <?xml version='1.0'?>
    If TemplateConfiguration#getDeclarationEncoding is not null:
    Output:
        <?xml version='1.0' encoding='UTF-8'?>

comment
	Renders raw contents inside an XML comment
	Template:
        comment 'This is <a href="foo.html">commented out</a>'
    Output:
        <!--This is <a href="foo.html">commented out</a>-->

newLine
	Renders a new line. See also TemplateConfiguration#setAutoNewLine and TemplateConfiguration#setNewLineString.
	Template:
        p('text')
        newLine()
        p('text on new line')
    Output:
        <p>text</p>
        <p>text on new line</p>

pi
	Renders an XML processing instruction.
	Template:
        pi("xml-stylesheet":[href:"mystyle.css", type:"text/css"])
    Output:
        <?xml-stylesheet href='mystyle.css' type='text/css'?>

tryEscape
	Returns an escaped string for an object, if it is a String (or any type derived from CharSequence). 
    Otherwise returns the object itself.
	Template:
        yieldUnescaped tryEscape('Some text with <angle brackets>')
    Output:
        Some text with &lt;angle brackets&gt;

        
        
///Includes
//Including another template can be done using:
include template: 'other_template.tpl'

//Including a file as raw contents, without escaping it, can be done like this:
include unescaped: 'raw.txt'

//inclusion of text that should be escaped before rendering can be done using:
include escaped: 'to_be_escaped.txt'

//OR USe below , note name can be varible 
includeGroovy(name) 
    to include another template
includeEscaped(name) 
    to include another file with escaping
includeUnescaped(name) 
    to include another file without escaping
//Files to be included (independently of their type, template or text) are found on classpath. 
//This is one of the reasons why the MarkupTemplateEngine takes an optional ClassLoader as constructor argument 
MarkupTemplateEngine() 
MarkupTemplateEngine(java.lang.ClassLoader parentLoader, java.io.File templateDirectory, TemplateConfiguration tplConfig)
    Convenience constructor to build a template engine which searches for templates into a directory
MarkupTemplateEngine(java.lang.ClassLoader parentLoader, TemplateConfiguration tplConfig) 
MarkupTemplateEngine(java.lang.ClassLoader parentLoader, TemplateConfiguration tplConfig, TemplateResolver resolver) 
MarkupTemplateEngine(TemplateConfiguration tplConfig) 


///Fragments
//Fragments are nested templates. 
// A fragment consists of a string, the inner template, and a model, used to render this template. 
ul {
    pages.each {
        fragment "li(line)", line:it
    }
}
//The 'fragment' element creates a nested template, and renders it with a model which is specific to this template. 
//we have the li(line) fragment, where line is bound to it. And line is bound to 'it' 
//so for 
pages=['page 1' , 'Page 2']
//Output 
<ul><li>Page 1</li><li>Page 2</li></ul>

///Layouts
//Layouts, unlike fragments, refer to other templates. 
//They can be used to compose templates and share common structures.

//layout-main.tpl
//the title variable (inside the title tag) is a layout variable
//the bodyContents call will render the body
html {
    head {
        title(title)                
    }
    body {
        bodyContents()              
    }
}

//usage template 
//The call to the contents method is used to tell the template engine 
//that the block of code is  a specification of a template, instead of a helper function to be rendered directly. 

layout 'layout-main.tpl',                                   
    title: 'Layout example',                                
    bodyContents: contents { p('This is the body') }        

//OUTPUT 
<html><head><title>Layout example</title></head><body><p>This is the body</p></body></html>

//Layouts use, by default, a model which is independent from the model of the page where they are used. 
//It is possible to make them inherit from the parent model.
model = new HashMap<String,Object>();
model.put('title','Title from main model');

//template.tpl:
//note the use of true to enable model inheritance
layout 'layout-main.tpl', true,                             
    bodyContents: contents { p('This is the body') }

//Then bind 'template.tpl' with model , 
//then it is not necessary to pass the title value to the layout as in the previous example
<html><head><title>Title from main model</title></head><body><p>This is the body</p></body></html>

//But it is also possible to override a value from the parent model:
layout 'layout-main.tpl', true,                             
    title: 'overridden title',                               
    bodyContents: contents { p('This is the body') }

//then the output will be:
<html><head><title>overridden title</title></head><body><p>This is the body</p></body></html>


///Creation of a template engine
TemplateConfiguration config = new groovy.text.markup.TemplateConfiguration();         
MarkupTemplateEngine engine = new groovy.text.markup.MarkupTemplateEngine(config);     
Template template = engine.createTemplate("p('test template')");    
Map<String, Object> model = new HashMap<>();                        
Writable output = template.make(model);                             
output.writeTo(writer);                                             

///There are several possible options to parse templates:
from a String, 
    using createTemplate(String)
from a Reader, 
    using createTemplate(Reader)
from a URL, 
    using createTemplate(URL)
given a template name, 
    using createTemplateByPath(String)

//For example 
Template template = engine.createTemplateByPath("main.tpl");
Writable output = template.make(model);
output.writeTo(writer);

///Configuration options
declarationEncoding
	null (default)
	Determines the value of the encoding to be written when xmlDeclaration is called. 
    It does not influence the writer you are using as output.
    Template:
        xmlDeclaration()
    Output:
        <?xml version='1.0'?>
    If TemplateConfiguration#getDeclarationEncoding is not null:
    Output:
        <?xml version='1.0' encoding='UTF-8'?>
expandEmptyElements
    false(default)
    If true, empty tags are rendered in their expanded form.
    Template:
        p()
    Output:
        <p/>
    If expandEmptyElements is true:
    Output:
        <p></p>
useDoubleQuotes
    false(default)
    If true, use double quotes for attributes instead of simple quotes
    Template:
        tag(attr:'value')
    Output:
        <tag attr='value'/>
    If useDoubleQuotes is true:
    Output:
        <tag attr="value"/>
newLineString
    System default (system property line.separator)
    Allows to choose what string is used when a new line is rendered
    Template:
        p('foo')
        newLine()
        p('baz')
    If newLineString='BAR':
    Output:
        <p>foo</p>BAR<p>baz</p>
autoEscape
    false(default)
    If true, variables from models are automatically escaped before rendering.
autoIndent
    false(default)
    If true, performs automatic indentation after new lines
autoIndentString
    four (4) spaces(default)
    The string to be used as indent.
autoNewLine
    false(default)
    If true, performs automatic insertion of new lines
baseTemplateClass
    groovy.text.markup.BaseTemplate(default)
    Sets the super class of compiled templates. This can be used to provide application specific templates.
locale
    Default locale
    Sets the default locale for templates.

///Automatic formatting
config.setAutoNewLine(true);
config.setAutoIndent(true);

//template:
html {
    head {
        title('Title')
    }
}

//The output will now be:
<html>
    <head>
        <title>Title</title>
    </head>
</html>

//For Template 
html {
    head { title('Title')
    }
}
//And the output will reflect that:
<html>
    <head><title>Title</title>
    </head>
</html>

//New lines are only inserted where curly braces for tags are found, 
//and the insertion corresponds to where the nested content is found. 
//This means that tags in the body of another tag will not trigger new lines unless they use curly braces themselves:

html {
    head {
        meta(attr:'value')          
        title('Title')              
        newLine()                   
        meta(attr:'value2')         
    }
}
//the output will be:
<html>
    <head>
        <meta attr='value'/><title>Title</title>
        <meta attr='value2'/>
    </head>
</html>

///Automatic escaping
config.setAutoEscape(false);
model = new HashMap<String,Object>();
model.put("unsafeContents", "I am an <html> hacker.");

//template:
html {
    body {
        div(unsafeContents)
    }
}
//Output 
<html><body><div>I am an <html> hacker.</div></body></html>

//Automatic escaping will fix this:
config.setAutoEscape(true);
//output is properly escaped:
<html><body><div>I am an &lt;html&gt; hacker.</div></body></html>

//Explicit bypass of automatic escaping
html {
    body {
        div(unescaped.unsafeContents)
    }
}




///*** Groovy - Groovlets
// GroovyServlet runs Groovy scripts as Groovlets
//This feature will automatically compile  .groovy source files, turn them into bytecode, 
//load the Class and cache it until source file is changed 

//hello.groovy
if (!session) {
  session = request.getSession(true)
}

if (!session.counter) {
  session.counter = 1
}

println """
<html>
    <head>
        <title>Groovy Servlet</title>
    </head>
    <body>
        <p>
Hello, ${request.remoteHost}: ${session.counter}! ${new Date()}
        </p>
    </body>
</html>
"""
session.counter = session.counter + 1

//do the same thing using MarkupBuilder:

if (!session) {
    session = request.getSession(true)
}

if (!session.counter) {
    session.counter = 1
}

html.html { // html is implicitly bound to new MarkupBuilder(out)
  head {
      title('Groovy Servlet')
  }
  body {
    p("Hello, ${request.remoteHost}: ${session.counter}! ${new Date()}")
  }
}
session.counter = session.counter + 1

///Implicit variables
//The session variable is only set, if there was already a session object. do if (session == null) checks before checking
//These variables cannot be re-assigned inside a Groovlet. 
//They are bound on first access, allowing to e.g. calling methods on the response object before using out.
request
    ServletRequest
response
    ServletResponse
context
    ServletContext
application
    ServletContext
session
    getSession(false)
    can be null
params
    a Map object
headers
    a Map object
out
    response.getWriter()
sout
    response.getOutputStream()
html
    new MarkupBuilder(out)
json
    new StreamingJsonBuilder(out)
    
    
///Setting up groovylets
//dir-structure - here URL is  http://localhost:8080/groovy/hello.groovy
webapps  (eg apache tomcat webapps)
    groovy(Context-Root)
        WEB-INF 
            web.xml 
            lib 
                Required groovy jar eg groovy-all-xxxxx.jar
            classes 
                Any other classes 
        html_files
        hello.groovy 
    
    
//web.xml:
//URL pattern should be *.* 
<servlet>
    <servlet-name>Groovy</servlet-name>
    <servlet-class>groovy.servlet.GroovyServlet</servlet-class>
</servlet>
<servlet-mapping>
    <servlet-name>Groovy</servlet-name>
    <url-pattern>*.groovy</url-pattern>
</servlet-mapping>

//OR manually set  eg using tomcat , edit tomcat/conf/server.xml 
//Then access it with http://localhost:8080/groovy/hello.groovy
//where c:/groovy-servlet contains all files containing WEB-INF
<Context path="/groovy" docBase="c:/groovy-servlet"/>



///Example : serverinfo.groovy:

def method = request.method
 
if (!session) {
    session = request.getSession(true)
}
 
if (!session.groovlet) {
    session.groovlet = 'Groovlets rock!'
}
 
html.html {
    head {
        title 'Groovlet info'
    }
    body {
        h1 'General info'
        ul {
            li "Method: ${method}"
            li "RequestURI: ${request.requestURI}"
            li "session.groovlet: ${session.groovlet}"
            li "application.version: ${context.version}"
        }
         
        h1 'Headers'
        ul {
            headers.each {
                li "${it.key} = ${it.value}"
            }
        }
    }
}

///script to start Jetty 
//http://localhost:9090/serverinfo.groovy
import org.mortbay.jetty.Server
import org.mortbay.jetty.servlet.*
import groovy.servlet.*
 
@Grab(group='org.mortbay.jetty', module='jetty-embedded', version='6.1.14')
def startJetty() {
    def jetty = new Server(9090)
     
    def context = new Context(jetty, '/', Context.SESSIONS)  // Allow sessions.
    context.resourceBase = '.'  // Look in current dir for Groovy scripts.
    context.addServlet(GroovyServlet, '*.groovy')  // All files ending with .groovy will be served.
    context.setAttribute('version', '1.0')  // Set an context attribute.
     
    jetty.start()
}
 
println "Starting Jetty, press Ctrl+C to stop."
startJetty()

///Reference 
javax.servlet.ServletRequest
    AsyncContext getAsyncContext() 
       Gets the AsyncContext that was created or reinitialized by the most recent invocation of startAsync() or startAsync(ServletRequest,ServletResponse) on this request. 
    java.lang.Object getAttribute(java.lang.String name) 
       Returns the value of the named attribute as an Object, or null if no attribute of the given name exists. 
    java.util.Enumeration<java.lang.String> getAttributeNames() 
       Returns an Enumeration containing the names of the attributes available to this request. 
    java.lang.String getCharacterEncoding() 
       Returns the name of the character encoding used in the body of this request. 
    int getContentLength() 
       Returns the length, in bytes, of the request body and made available by the input stream, or -1 if the length is not known. 
    java.lang.String getContentType() 
       Returns the MIME type of the body of the request, or null if the type is not known. 
    DispatcherType getDispatcherType() 
       Gets the dispatcher type of this request. 
    ServletInputStream getInputStream() 
       Retrieves the body of the request as binary data using a ServletInputStream. 
    java.lang.String getLocalAddr() 
       Returns the Internet Protocol (IP) address of the interface on which the request was received. 
    java.util.Locale getLocale() 
       Returns the preferred Locale that the client will accept content in, based on the Accept-Language header. 
    java.util.Enumeration<java.util.Locale> getLocales() 
       Returns an Enumeration of Locale objects indicating, in decreasing order starting with the preferred locale, the locales that are acceptable to the client based on the Accept-Language header. 
    java.lang.String getLocalName() 
       Returns the host name of the Internet Protocol (IP) interface on which the request was received. 
    int getLocalPort() 
       Returns the Internet Protocol (IP) port number of the interface on which the request was received. 
    java.lang.String getParameter(java.lang.String name) 
       Returns the value of a request parameter as a String, or null if the parameter does not exist. 
    java.util.Map<java.lang.String,java.lang.String[]> getParameterMap() 
       Returns a java.util.Map of the parameters of this request. 
    java.util.Enumeration<java.lang.String> getParameterNames() 
       Returns an Enumeration of String objects containing the names of the parameters contained in this request. 
    java.lang.String[] getParameterValues(java.lang.String name) 
       Returns an array of String objects containing all of the values the given request parameter has, or null if the parameter does not exist. 
    java.lang.String getProtocol() 
       Returns the name and version of the protocol the request uses in the form protocol/majorVersion.minorVersion, for example, HTTP/1.1. 
    java.io.BufferedReader getReader() 
       Retrieves the body of the request as character data using a BufferedReader. 
    java.lang.String getRealPath(java.lang.String path) 
       Deprecated. As of Version 2.1 of the Java Servlet API, use ServletContext#getRealPath instead. 
    java.lang.String getRemoteAddr() 
       Returns the Internet Protocol (IP) address of the client or last proxy that sent the request. 
    java.lang.String getRemoteHost() 
       Returns the fully qualified name of the client or the last proxy that sent the request. 
    int getRemotePort() 
       Returns the Internet Protocol (IP) source port of the client or last proxy that sent the request. 
    RequestDispatcher getRequestDispatcher(java.lang.String path) 
       Returns a RequestDispatcher object that acts as a wrapper for the resource located at the given path. 
    java.lang.String getScheme() 
       Returns the name of the scheme used to make this request, for example, http, https, or ftp. 
    java.lang.String getServerName() 
       Returns the host name of the server to which the request was sent. 
    int getServerPort() 
       Returns the port number to which the request was sent. 
    ServletContext getServletContext() 
       Gets the servlet context to which this ServletRequest was last dispatched. 
    boolean isAsyncStarted() 
       Checks if this request has been put into asynchronous mode. 
    boolean isAsyncSupported() 
       Checks if this request supports asynchronous operation. 
    boolean isSecure() 
       Returns a boolean indicating whether this request was made using a secure channel, such as HTTPS. 
    void removeAttribute(java.lang.String name) 
       Removes an attribute from this request. 
    void setAttribute(java.lang.String name, java.lang.Object o) 
       Stores an attribute in this request. 
    void setCharacterEncoding(java.lang.String env) 
       Overrides the name of the character encoding used in the body of this request. 
    AsyncContext startAsync() 
       Puts this request into asynchronous mode, and initializes its AsyncContext with the original (unwrapped) ServletRequest and ServletResponse objects. 
    AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) 
       Puts this request into asynchronous mode, and initializes its AsyncContext with the given request and response objects. 
javax.servlet.ServletResponse
    void flushBuffer() 
       Forces any content in the buffer to be written to the client. 
    int getBufferSize() 
       Returns the actual buffer size used for the response. 
    java.lang.String getCharacterEncoding() 
       Returns the name of the character encoding (MIME charset) used for the body sent in this response. 
    java.lang.String getContentType() 
       Returns the content type used for the MIME body sent in this response. 
    java.util.Locale getLocale() 
       Returns the locale specified for this response using the setLocale(java.util.Locale) method. 
    ServletOutputStream getOutputStream() 
       Returns a ServletOutputStream suitable for writing binary data in the response. 
    java.io.PrintWriter getWriter() 
       Returns a PrintWriter object that can send character text to the client. 
    boolean isCommitted() 
       Returns a boolean indicating if the response has been committed. 
    void reset() 
       Clears any data that exists in the buffer as well as the status code and headers. 
    void resetBuffer() 
       Clears the content of the underlying buffer in the response without clearing headers or status code. 
    void setBufferSize(int size) 
       Sets the preferred buffer size for the body of the response. 
    void setCharacterEncoding(java.lang.String charset) 
       Sets the character encoding (MIME charset) of the response being sent to the client, for example, to UTF-8. 
    void setContentLength(int len) 
       Sets the length of the content body in the response In HTTP servlets, this method sets the HTTP Content-Length header. 
    void setContentType(java.lang.String type) 
       Sets the content type of the response being sent to the client, if the response has not been committed yet. 
    void setLocale(java.util.Locale loc) 
       Sets the locale of the response, if the response has not been committed yet. 

