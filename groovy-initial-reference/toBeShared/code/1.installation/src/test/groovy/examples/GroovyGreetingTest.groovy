package examples

import static org.junit.Assert.*
import org.junit.Test

public class GroovyGreetingTest {


  final GreetingService service = new GroovyGreetingImpl ();


@Test public void testGreet() {
  assertEquals("Hello OK. Greeting from Groovy", service.greet("OK"));
 }

@Test public void testGreetNull() {
  assertEquals("Hello stranger. Greeting from Groovy", service.greet(null));
 }
}
