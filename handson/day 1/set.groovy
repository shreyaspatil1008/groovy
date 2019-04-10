set1 = [1,2,3,1,2] as Set
set2 = [3,4,5] as Set
set2 == [5,3,4] as Set ///TRUE

set2.each{
    println it
}
set2.size()

3 in set2 // check existance
set2[0]
su = set1 + set2 // union
si = set1.intersect(set2)// intersection
sd = set1 - set2 // diff



