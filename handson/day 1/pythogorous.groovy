def pythogorous(limit){
     count = 0
    1.upto(limit){ a ->
                a.upto(limit){
                    b -> b.upto(limit){
                        c-> 
                            if( c*c == a*a + b*b){
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