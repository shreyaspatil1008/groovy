a = 'Hello World'
b = "Hello World"
c = """Hello 
Lets 
See
World"""
d = "Hello\nWorld"
e = a+" OK"

f = 'A' as char
g=(char) 'A'
g.class

h = /\n/
//println h
i = "\n"
h.size()
i.size()

//other methods
a = "Hello World"
a.size()
a.contains("World")
a == "OK"

//iteration
for(ch in a){

   // println ch
}

//handson

def strHandson(input){
    for(i in input){
        count = 0
        for(j in input){
            if(i == j){
                count++
            }
         }
         println "$i :: $count"   
    }
}

strHandson(a)


// indexing
a[0]
a[-1]
a[-a.size()]

//slicing
a[0..2] // 2 is inclusive
a[0..<2] // 2 is exclusive
a[-1..-a.size()] //reversing
p = "  H  l  "
p.trim()
p.split()
p.toUpperCase()
p.startsWith(" ")
p = "systeminfo".execute()
//println(p.getText())

//println(p.text)

println("http://www.google.com".toURL().text.size())