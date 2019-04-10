package examples

import com.xlson.groovycsv.CsvIterator
import org.grails.datastore.gorm.GormEntity

@groovy.transform.Canonical
@groovy.transform.ToString(includeNames = true)
class Iris {
    Double sl
    Double sw
    Double pl
    Double pw
    String name
    def toList(){
        [this.sl, this.sw, this.pl, this.pw, this.name]
    }
}

class CsvIteratorToIrisIterator{
    static  Iterator toIris(CsvIterator self){
        return [
                hasNext : self.&hasNext,
                next: {->
                    def ln = self.next()
                    new Iris(
                            ln.SepalLength.toDouble(),
                            ln.SepalWidth.toDouble(),
                            ln.PetalLength.toDouble(),
                            ln.PetalWidth.toDouble(),
                            ln.Name
                    )
                }
        ] as Iterator
    }
}

@grails.gorm.annotation.Entity
@groovy.transform.Canonical
@groovy.transform.ToString(includeNames = true)
class IrisG implements GormEntity<IrisG>{
    Double sl
    Double sw
    Double pl
    Double pw
    String name
    def toList(){
        [this.sl, this.sw, this.pl, this.pw, this.name]
    }
}

