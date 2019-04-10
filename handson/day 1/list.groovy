//list - mutable, insertion order, duplicate, indexing
def lst = [1,"OK", 1.2, [4,5,6]]
le = []

println le.empty 

lst[-1][-1] = 30
//append
le<< 2 << 3
le+= [7,8]
le.add(40)

//println(le)

//conct
l2 = le + [1,2,3]

// le remains same
//l2 is new list

//println lst

lst[0..<0] = ["prepend"]

//println lst
lst[lst.size()..lst.size()] = ["prepend"]

//println lst
lst[2..<2] = ["insert"]
//println lst
lst[0..3] = ["updated"]
//println lst
// fp iteration
lst.each{
    //println it
}.each{ e -> 
    //println e + e
}

//beyond size
//println(lst.size())
lst[100]==null // true
//println(lst.size())
lst[20] = "OK"
//println(lst.size())
lst[100]=null
//println(lst.size())
//intermediate values are null
lst=[a,2,3]//
//arr = lst as int[] // int[]
set = [1,1,1,1,2] as Set // duplicates are removed

stsck = lst as Stack
quque = lst as Queue



//map pattern

input = [1,5,6,10]
output = [1,25,36,100]


def square(input){
    output = []
    input.each{
        output<< it*it
    }
    output
}

//println square([3,4,5,6])

inputString = "[1,2,3,4]"

def convert(inputString){
    listval = inputString.split(",")
    output = []
    listval[1..<listval.size()-1].each{
        output<< it
    }
    output
}
println (convert(inputString))

//transpose = zip

[['a','b','c'],[10,20,30,40]].transpose()