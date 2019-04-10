package examples

import com.xlson.groovycsv.CsvIterator
import org.grails.datastore.gorm.GormEntity

@groovy.transform.Canonical
@groovy.transform.ToString(includeNames = true)
class Iris {
    Date date
    Double open
    def toList(){
        [this.date, this.open]
    }
}

class CsvIteratorToNiftyIterator{
    static  Iterator toIris(CsvIterator self){
        return [
                hasNext : self.&hasNext,
                next: {->
                    def ln = self.next()
                    new Iris(
                            ln.Date.toDate(),
                            ln.Open.toDouble()
                    )
                }
        ] as Iterator
    }
}
