package examples 
import org.grails.datastore.gorm.*
import java.util.*

@grails.gorm.annotation.Entity 
class Person implements GormEntity<Person> { 
    String firstName
    String lastName
    static constraints = {
        firstName blank:false
        lastName blank:false
    }
    /*
    //check http://docs.grails.org/3.0.17/ref/Database%20Mapping/Usage.html
    static mapping = {
        table 'my_table'
        id column: "Mgr_id"
        version false
    }
    */
}

@grails.gorm.annotation.Entity 
//@groovy.transform.EqualsAndHashCode(includes='title,isbn')
class Book implements GormEntity<Book> {
    String title 
    String isbn
    Date releaseDate  = new Date() - 3   //java.util.Date(int year, int month, int date), with GDK enhancements like +,- etc
    
    static belongsTo = Author
    static hasMany = [authors:Author]
    
    static constraints = {
        isbn nullable: true      //by default all are not nullable, except association link
    }
    
    String toString() { "${this.title} : ${this.id} " }
    static mapping = {
        authors batchSize: 10        
    }
}

//Author owner - CUD is cascaded to owned side as there is 'belongsTo' on owned side 
//Save the object hierarchy through these 
@grails.gorm.annotation.Entity 
//@groovy.transform.EqualsAndHashCode(includes='name,fullName')
class Author implements GormEntity<Author>{
    static hasMany = [books:Book]  //books is set 
    String name
    String fullName = "UnKnown"
    Integer age = 50
    String toString() { "${this.name} : ${this.id}" }
    
    static mapping = {
        books batchSize: 10
    }
}