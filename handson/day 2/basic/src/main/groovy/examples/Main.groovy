package examples

import com.sun.javafx.property.adapter.JavaBeanPropertyBuilderHelper
import grails.spring.BeanBuilder

@groovy.transform.ToString(includeNames = true)
class Login{
    def authorie(User user){
        println user
    }
}
@groovy.transform.Canonical
@groovy.transform.ToString(includeNames = true)
class User{
    String name
    Credentials cr
}
@groovy.transform.Canonical
@groovy.transform.ToString(includeNames = true)
class Credentials{
    String username
    String password
}

class Main{
    static void main(String[] args){
		//def p = new Person(name:"XYZ")
		//println p
		//println 3.triple
        beanDemo()
	}

    static void beanDemo(){
        def bb = new BeanBuilder()
        bb.beans{
            login(Login)
            user(User){
                bean ->
                    cr = new Credentials("me","guesss")
                    name = "Xya yb"
            }
        }

        def ctx = bb.createApplicationContext()
        def l = ctx.getBean("login")
        def u = ctx.getBean("user")
        l.authorie(u)
    }
}
