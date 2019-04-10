//
ExpandoMetaClass.enableGlobally()
List.metaClass.sizeDoubled << {->delegate.size() * 2}
List.metaClass.freq << {->delegate.collectEntries{e -> [e, delegate.count{it == e}]}}

def lst = [1,2,3,1,2,1]
println "${lst.size()} ${lst.sizeDoubled()}"
println "${lst.freq()}" // [1:3, 2:2, 3:1]