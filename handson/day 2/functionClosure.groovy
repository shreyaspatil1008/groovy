def fun(x){
    x*x
}

def sq = this.&fun  // Closure
println(sq(2)) // 4
def str = "hello world"
def upper = str.&toUpperCase
println upper()

//safe navigation
String x = null
println x?.size() // no null exception

//elvis operation
def user = null
def name = user != null ? user : "no name"
//or
name = user ?: "no name"
println name


//sprread operation
@groovy.transform.Canonical 
class Person{
    String name
    def getAge(){
        20
    }
    def upper(){
        name.toUpperCase()
    }
}

def lst = [new Person(name:"XYZ"),new Person(name:"ABC")]

lst*.getAge() // spread with function
lst*.age //spread
lst.age //Graph, only for props and getPropXXX
//[20,20]
//
def func(int x, int y, int z){

    x+y+z
}
lst = [1,2,3]
assert func(*lst) == func(1,2,3)

//more spread
lst1 = [*lst, 2,3, *lst]
map1 = [c:3, d:4]
map2 = [a:2, *:map1]

