em = [:]
map = ["a":1, "b":2]
println map.a
println map.isEmpty()
map.keySet()
map.values()
map.entrySet()

map<<['new':2]

map1 = map - ['new':2]

map['new1'] = 20
map['ok'] = 20

map.size()

println map['ok']

println (map.ok)

"ok" in map

map.each{
k,v -> println "$k $v"
}

map.each{
kv -> println "${kv.key} ${kv.value}"
}
map['hkj']//null

map.get('ab')

map.get('ab',"default")
map.asImmutable()
map.asSynchronized()
//println map



def match(x){

    switch(x){
        case "ABC" : println("$x::::ABC")
                     break
        case 2..9 : println("$x::::range")
                     break
        case 'a'..'f' : println("$x::::a to f")
                        break        
        case [10,20,30] : println("$x::::in list")
                          break
        case [ok:2, nok:3] : println("$x::::in map keys")
                             break
        case BigDecimal : println("$x::::BigDecimal")
                          break       
        case ~/dr.*/ : println("$x::::pattern matched")
                          break
        case { it== 1G} :   // if closure returns true, this is matched, G = BigInteger
                             println("$x::::Closure returned true")
                             break
        default : println("$x::::default")
                      // break       optional
    }
}


//match("ABC")
//match(2)
//match('e')
//match(20)
//match("OK")
//match(1.2)
//match("drown")
//match(1G)
//match(1.2f)







def getWordMap(s){
    map = [:]
    s.split(" ").each{
        if(it in map){
            map[it] = map[it]+1
        }else{
            map[it] = 1
        }
    }
    map
}

def getWordCount(word,s){
    s = s.toLowerCase()
    map = getWordMap(s)
    map[word.toLowerCase()]    
}
m = "Groovy and Groovy console are used for groovy"
word = "Groovy"
println getWordCount( "Groovy",m)
println getWordCount( "and",m)
