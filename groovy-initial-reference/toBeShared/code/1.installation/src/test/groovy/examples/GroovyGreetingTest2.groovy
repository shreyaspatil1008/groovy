package examples

public class GroovyGreetingTest2 extends GroovyTestCase {
 
 def service

  void setUp() {
   service = new GroovyGreetingImpl ();
   }

void testGreet() {
  assert "Hello OK. Greeting from Groovy" == service.greet("OK")
 }

void testFail() {
    def numbers = [1,2,3,4]
    shouldFail {
        numbers.get(4)
    }

 }
 void testGreetNull() {
  assert "Hello stranger. Greeting from Groovy" == service.greet(null)
 }
}