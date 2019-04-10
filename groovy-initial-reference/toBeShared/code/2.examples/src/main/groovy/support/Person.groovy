package support 
@groovy.transform.TupleConstructor
@groovy.transform.ToString
class Person {
    String firstName
    String lastName
	
	public static void main(String[] args) {
		 println("${args[0]}")
		 args.each { println it }
		 Person classic = new Person('Ada','Lovelace')    		//tuple constructor
		 assert "support.Person(Ada, Lovelace)" == classic.toString()
	 }
	
}