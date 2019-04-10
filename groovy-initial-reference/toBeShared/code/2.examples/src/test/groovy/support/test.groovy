package support

import static org.junit.Assert.*
import org.junit.Test

public class GroovyGreetingTest {

	Person classic = new Person('Ada','Lovelace')    		//tuple constructor
	Person list = ['Ada','Lovelace']    					// list constructors
	Person map = [firstName:'Ada', lastName:'Lovelace']  	// map constructor
	Person de  = new Person()                                     //default constructor
	Person mapLike = new Person (firstName:'Ada', lastName:'Lovelace') //map like constructor

	@Test public void testGreet() {
	  assertEquals("support.Person(Ada, Lovelace)", classic.toString());
	 }

}
//check build\reports\tests\index.html