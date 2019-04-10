package examples


class GroovyGreetingImpl implements GreetingService {
String greet(String name) {
  "Hello ${name ?: 'stranger'}. Greeting from Groovy"
 }
}