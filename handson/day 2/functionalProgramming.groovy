// map = collect
//filter = findall
//reduce = 

//HOF : Highter Order function
//Function takes closure as arg
// Currying: function returns a closure
// Partial application: function takes amny arguments => curry form with all except one applied
//compose: call functions one after another

// memoize: Dynamic programming, creating cache of used args
//TCo: tail call optimization, in groovy trampoline

def lst = [1,2,3,4,5]
//findall(Closure closure)
lst.findAll{it > 3}
lst.findAll{e -> e > 3}
//List collect(Closure transform)
lst.collect{it * it}

//Object inject(Object initialValue, Clousre closure)
lst.inject(0){r,e-> r+e} //actually sum
lst.inject([]){r,e-> r+[e*e]}

//List collectMany
lst.collect{e->[e*e]} // [[1],[4],[9],[16],[25]]
lst.collectMany{e->[e*e]} //  [1, 4, 9, 16, 25]
lst.collectEntries{e->[e,e*e]} //  [1:1, 2:4, 3:9, 4:16, 5:25]

//Number count
def s = "Hello World"
s.toList().collectEntries{e-> [e, s.toList().count{e1 -> e==e1}]}

s.split(/\s+/).collectEntries{e-> [e, s.split(/\s+/).count{e1 -> e==e1}]}


//Number count(Closure closure)
lst.count{e-> e > 3}
//Map  countBy(Closure closure)
lst.countBy{it == 3}
//boolean every(Closure predicate)
lst.every{e-> e == 3}
println lst.sort(false)

def lsts=['a','abc','bb','b','xyzl']
//Collection takeWhile(Closure closure)
lsts.takeWhile{e-> e<4}
lsts.takeWhile{e-> e.size()}
lsts.dropWhile{e-> e<4}
lsts.dropWhile{e-> e.size()}
//Object sum(Closure closure)
lsts.sum{it * 2}
//Number max(Closure closure)
lsts.max{e-> e == 4}
lsts.sort(false)//false means return new list


lsts.countBy{e -> e.size()}
lsts.groupBy{e -> e.size()}
lsts.groupBy{e -> e.size()}.collectEntries{e -> [e.key, e.value.size()]}

lsts.groupBy({e -> e.size()},{it.size() %2})

lsts.withIndex() // [[a, 0], [abc, 1], [bb, 2], [b, 3], [xyzl, 4]]
lsts.withIndex().collect{[ it.last(),it.head(),]} 
lsts.head() // a
lsts.tail() // [2,3,4]
lsts.last() // 4
lsts.init() //[1,2,3]
lsts.take(2) // [1,2]
lsts.drop(1).take(1) //[2]
lsts.join(",") // "1,2,3,4"

lsts.join(",").split(",").collect{it} //[1,2,3,4]

lsts = (0..10).toList()
def lst1 = lsts.stream().filter{it > 5}.map{it * it}
lst1.collect()

def m1 = [ok:1, nok:10]
m1.collect{kv -> kv.key}
m1.collectEntries{kv -> [kv.key, kv.value*kv.value]}
//findAll
m1.findAll{kv -> kv.key.size() > 2}//[nok:10]
m1.findAll{k,v -> k.size() > 2}//[nok:10]

m1.each{k,v -> println "$k $v"}
m1.inject(0){r,k,v -> r+v}//11
//sort by
m1.sort{kv->kv.value}//[ok:1,nok:10]
m1.sort{kv -> -kv.value}//[nok:10, ok:1]