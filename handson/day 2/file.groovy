//File
File f = new File(/first2.groovy/) //writing
f.text = "First line"
f << "\n2nd line\n" << "3rd line\n" << "kjlksajflkjasdlfkjaslkdfjlaskdjflkasdjflaksdjfl;aksdjf;laskdjf"


//reading
f.text
f.eachLine{
    line -> println line
}

//copying
def f2 = new File("first2.groovy")
def f3 = new File("first2.groovy.bk")
lineList = []
f3.withPrintWriter{
    
    wr-> f2.eachLine{
        line -> lineList << line.size()
    }
    lineList
}


////////////////////////////////////
println f.readLines().withIndex().collect{
    line,index -> [line.size(),index+1]
}.sort(false){si-> si[0]}[-1][-1] // line 10

println f.readLines().withIndex().collect{
    line,index -> [line.size(),index+1]
}.sort(false){si-> si[0]}.last().last()


//////////////////////////////////////////////


def lines = "systeminfo".execute().in.readLines()
kbLines = []
lines.findAll{it.contains('KB')}
.collect{ it.split("KB").last().toInteger()}
.max()

///

//total dir size given a dir


