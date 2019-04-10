package examples

//for query
import builders.dsl.spreadsheet.query.api.*
import builders.dsl.spreadsheet.query.poi.*
//for building
import builders.dsl.spreadsheet.api.groovy.*
import builders.dsl.spreadsheet.api.*
import java.util.*
//Gorm 
import org.grails.orm.hibernate.HibernateDatastore


//bean 
import grails.spring.BeanBuilder
import groovy.transform.ToString
//spring 
import org.springframework.context.support.GenericApplicationContext
import org.springframework.beans.factory.groovy.GroovyBeanDefinitionReader
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

//jx-rs 
import javax.xml.ws.Endpoint
import org.aspectj.lang.*;
import wslite.soap.*


//local 
import jsrx.* 

//rest 
import static groovyx.net.http.HttpBuilder.configure
import static groovyx.net.http.ContentTypes.JSON
import groovyx.net.http.*
import static groovy.json.JsonOutput.prettyPrint

//ratpack 
import ratpack.server.RatpackServer;
import ratpack.server.ServerConfig;
import static ratpack.jackson.Jackson.json;

@groovy.transform.Canonical
class Dummy {
    String name 
}


class Login {
    def authorize(User user){
        if(user.credentials.username == "John" && user.credentials.password == "Doe"){
            "${user.greetings} ${user.credentials.username}"
        }else
            "You are not ${user.greetings}"
    }
}

@ToString(includeNames=true)
class Credentials{
    String username
    String password
}

@ToString(includeNames=true)
class User{
    Credentials credentials
    String greetings
}




class LoggerInterceptor {     
    public void logBefore(JoinPoint joinPoint){
        String logMessage = String.format("Beginning of each method: %s.%s(%s)",
        joinPoint.target.class.name,
        joinPoint.signature.name,
        joinPoint.args.join(" "));
        println(logMessage);
    }
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
		println("logAround() is running!")
        println("hijacked class : " +joinPoint.target.class.name)
		println("hijacked method : " + joinPoint.signature.name)
		println("hijacked arguments : " + joinPoint.args.join(" ") )		
		println("Around before is running!")
		Object retVal = joinPoint.proceed(joinPoint.args)
		println("Around after is running!")
        return retVal
	}
}

class Pojo {
    private String one;
    private int two;
    private double three;
     
    public String getOne() { return one; }
    public void setOne(String one) { this.one = one; }
    public int getTwo() { return two; }
    public void setTwo(int two) { this.two = two; }
    public double getThree() { return three; }
    public void setThree(double three) { this.three = three; }
 
    @Override
    public String toString() {
        return "POJO [one=" + one + ", two=" + two + ", three=" + three + "]";
    }
}



class Main{
   public static void main(String[] args) {
    //xlsx()
    //gorm()
    //bbuilder(); bbuilder2()
    //aop()
    //refresh()
    //service( true )
    //restTest()
    //ratpakServer()
    ratpackAndRestTest()
  }
  static def ratpackAndRestTest(){
    def end = 'http://localhost:5050'
    def sleep = 1000*10 
    def t = Thread.start {
            println("${Thread.currentThread().name} : starting ratpack service ... $end ")
            def rat = ratpakServer()
            Thread.sleep(sleep*6)
            rat.stop()
            println("${Thread.currentThread().name} : stopped ratpack service ... $end ")
        }    
    Thread.sleep(sleep)  //sleep sometime
    def get = { path ->    
        def result = configure {
                request.uri = end
            }.get{
                request.uri.path = path       
            }       
        println(result)
     }
     get("/das")
     get("/env")
     get("/person")
   
  }  
  static RatpackServer ratpakServer(){
  //http://localhost:5050
    RatpackServer rat = RatpackServer.start{server ->  
            server.handlers{chain -> 
               chain.get{ctx -> ctx.render("Hello World!")}
                    .get("a"){ctx -> ctx.render("A" + " 1")} //a , returns "A 1"
                    .get("person"){ctx -> ctx.render(json(new Dummy("John")))}
                    .get("env"){ctx -> ctx.render(json(System.getenv()))}
                    .get(":name"){ ctx -> ctx.render("Hello " + ctx.getPathTokens().get("name") + "!")}     

             }
       }
    return rat 
  }
  
  
  static def restTest(){
        def result = configure {
            request.uri = 'http://httpbin.org'
            request.contentType = JSON[0]
        }.post {
            request.uri.path = '/post'
            request.body = [id: '234545', label: 'something interesting']
            request.contentType = 'application/x-www-form-urlencoded'
            request.encoder 'application/x-www-form-urlencoded', NativeHandlers.Encoders.&form
        }
        //{args={}, data=, files={}, form={id=234545, label=something interesting}, headers={Accept=text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2, Accept-Encoding=gzip, deflate, Connection=close, Content-Length=37, Content-Type=application/x-www-form-urlencoded; charset=utf-8, Host=httpbin.org, User-Agent=Java/1.8.0_65},json=null, origin=47.11.202.149, url=http://httpbin.org/post}


        println "You submitted: id=${result.form.id} and label=${result.form.id}"
        println( result.toString() )
    
    }
  static def service( boolean  client){
    def sleep = 1000*10
    def end1 = 'http://localhost:5555/circle'
    def end = "http://localhost:9000/book"
    def t = Thread.start {
            def endpoint2 = Endpoint.publish(end1,new Circle())
            def endpoint1 = Endpoint.publish(end, new BookService())
            println("${Thread.currentThread().name} : starting web service ... $end1\n$end :${endpoint1.isPublished()},${endpoint2.isPublished()}")
            Thread.sleep(sleep)
            endpoint1.stop(); endpoint2.stop()    
            println("${Thread.currentThread().name} : ended web service")
    }    
    if (client){
        Thread.sleep(1000)  //sleep sometime
        def cl = new SOAPClient(end1)
        def cl2 = new SOAPClient(end)
        def response , response2
        try {            
                response = cl.send(
                    """<?xml version="1.0" ?>
                    <S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/">
                        <S:Body>
                            <ns2:getArea xmlns:ns2="http://jsrx/">
                                <arg0>20.0</arg0>
                            </ns2:getArea>
                        </S:Body>
                    </S:Envelope>"""
                )
                response2 = cl2.send(
                    """<?xml version="1.0" ?>
                        <S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/">
                            <S:Body>
                                <ns2:add xmlns:ns2="http://experiment/groovy-jax/">                              
                                        <name>Das</name>
                                        <author>Das</author>                                    
                                </ns2:add>
                            </S:Body>
                        </S:Envelope>"""
                )
                //"
                
                println("Request : $response.httpRequest")
                println("Response : $response.httpResponse")
                println("Text : $response.text")
                println("Text : $response2.text")
                println(response.getAreaResponse.return.text())
                println(response.httpResponse.statusCode)
                println(response2.addResponse.text())
            } catch (SOAPFaultException sfe) {
                println sfe.message // faultcode/faultstring for 1.1 or Code/Reason for 1.2
                println sfe.text    // prints SOAP Envelope
                println sfe.httpResponse.statusCode
                println sfe.fault.detail.text() // sfe.fault is a GPathResult of Envelope/Body/Fault
            } catch (SOAPClientException sce) {
                // This indicates an error with underlying HTTP Client (i.e., 404 Not Found)
                println sce
            }
        }
        
        t.join()
  }
  
  /* 
    <definitions targetNamespace="http://jsrx/" name="CircleService">
        <types/>
        <message name="getArea"><part name="arg0" type="xsd:double"/></message>
        <message name="getAreaResponse"><part name="return" type="xsd:double"/></message>
        <portType name="Geom">
            <operation name="getArea">
                <input wsam:Action="http://jsrx/Geom/getAreaRequest" message="tns:getArea"/>
                <output wsam:Action="http://jsrx/Geom/getAreaResponse" message="tns:getAreaResponse"/>
            </operation>
        </portType>
        <binding name="CirclePortBinding" type="tns:Geom">
        <soap:binding transport="http://schemas.xmlsoap.org/soap/http" style="rpc"/>
        <operation name="getArea">
            <soap:operation soapAction=""/>
                <input><soap:body use="literal" namespace="http://jsrx/"/></input>
                <output><soap:body use="literal" namespace="http://jsrx/"/></output>
        </operation>
        </binding>
        <service name="CircleService">
            <port name="CirclePort" binding="tns:CirclePortBinding">
            <soap:address location="http://localhost:5555/circle"/></port>
        </service>
    </definitions>


    <definitions targetNamespace="http://experiment/groovy-jax/" name="BookServiceService">
    <types><xsd:schema><xsd:import namespace="http://experiment/groovy-jax/" schemaLocation="http://localhost:9000/book?xsd=1"/></xsd:schema></types>
    <message name="add"><part name="add" element="tns:add"/> </message>
    <message name="addResponse"/>
    <portType name="BookService">
        <operation name="add">
        <input wsam:Action="http://experiment/groovy-jax/BookService/addRequest" message="tns:add"/>
        <output wsam:Action="http://experiment/groovy-jax/BookService/addResponse" message="tns:addResponse"/>
        </operation>
    </portType>
    <binding name="BookServicePortBinding" type="tns:BookService">
        <soap:binding transport="http://schemas.xmlsoap.org/soap/http" style="document"/>
        <operation name="add"><soap:operation soapAction=""/>
        <input><soap:body use="literal"/></input>
        <output><soap:body use="literal"/></output>
        </operation>
    </binding>
    <service name="BookServiceService"><port name="BookServicePort" binding="tns:BookServicePortBinding">
    <soap:address location="http://localhost:9000/book"/></port></service></definitions>
      
    <xs:schema version="1.0" targetNamespace="http://experiment/groovy-jax/">
        <xs:element name="add" nillable="true" type="tns:book"/>
        <xs:element name="addResponse" nillable="true" type="xs:string"/>
        <xs:complexType name="book">
            <xs:sequence>
                <xs:element name="name" type="xs:string" minOccurs="0"/>
                <xs:element name="author" type="xs:string" minOccurs="0"/>
            </xs:sequence>
        </xs:complexType>
    </xs:schema>
    
      
      
    <?xml version="1.0" ?>
    <S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/">
        <S:Body>
            <ns2:getArea xmlns:ns2="http://jsrx/">
                <arg0>20.0</arg0>
            </ns2:getArea>
        </S:Body>
    </S:Envelope>
        

 
    <?xml version="1.0" ?>
    <S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/">
        <S:Body>
            <ns2:add xmlns:ns2="http://experiment/groovy-jax/">
                <book>
                    <name>Das</name>
                    <author>Das</author>
                </book>
            </ns2:add>
        </S:Body>
    </S:Envelope>

    <?xml version="1.0" ?>
    <S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/">
        <S:Body>
            <ns2:addResponse xmlns:ns2="http://experiment/groovy-jax/">nullnull</ns2:addResponse>
        </S:Body>
    </S:Envelope>
  
  */
  
  static def xlsx(){
     File file = new File('D:/PPT/Groovy/data/Nifty-17_Years_Data-V1.xlsx')
     SpreadsheetCriteria query = PoiSpreadsheetCriteria.FACTORY.forFile(file)                
     def content = query.query {
         println(delegate); println(this); println(owner)
         sheet('Sheet1')
     }.sheet
     println(content)
     def rows = content.rows 
     //drop header row 
     def cells = rows.drop(1).collect{it.cells}.collect{ lc -> [lc.head().read(Date.class), *lc.tail().collect{c -> c.read(Double.class)} ]}
     println(cells.size())
     println(cells.first())
     def result = cells.groupBy{ it.first().year}.collectEntries{k,v -> [k, v.collect{l -> l[1]}.max()]}
     println(result)
     def result2 = cells.groupBy{ it.first().year}.collectEntries{k,v -> [k+1900, ['max': (1..4).toList().collect{i-> v.collect{l -> l[i]}.max()} ]]}
     println(result2)  
  }
  
  static def gorm(){
  
        Map configuration = [
                'hibernate.hbm2ddl.auto':'create-drop',
                'dataSource.url':'jdbc:h2:mem:myDB',
                'dataSource.driverClassName': 'org.h2.Driver',
                'dataSource.username': 'sa',
                'dataSource.password': ''
            ]
        //def datastore1 = new HibernateDatastore(configuration, Person)
        def datastore2 = new HibernateDatastore(configuration, Person, Book, Author)
        
        Person.withTransaction { status ->  //status.setRollbackOnly()to rollback 
                new Person(firstName:"Fred", lastName:"Das").save()
            }
        //Must be always inside withTranscation 
        Person.withTransaction { status ->  //status.setRollbackOnly()to rollback 
                println(Person.list())
                println(Person.findByFirstName("Fred"))
            }
            
        Book.withTransaction{ status ->
                new Author(name:"Stephen King")
                            .addToBooks(new Book(title:"The Stand"))
                            .addToBooks(new Book(title:"The Shining"))
                            .save()  //this would save all               
                    
                    def bk = new Book(title:"Groovy in Action") 
                    bk.save()
                    def au = new Author(name:"Dierk Koenig2")
                    au.save()
                    def au2 = new Author(name:"Guillaume Laforge2")
                    au2.save()
                    bk.addToAuthors(au).addToAuthors(au2).save()
                    
                    new Author(name:"Das jr").save() 
                    new Author(name:"Das Sr").save() 
                    new Book(title:"Das First Book").save()
                    new Book(title:"Das second Book").save()       
       
       }
       Book.withTransaction{ status ->
           println(Book.list())
           println(Author.list())
       }
        //datastore1.close()
        datastore2.close()
  
  }
  
  static def bbuilder(){
          def bb = new BeanBuilder()
          
         //Create two instances= login, user 
        //all Bean DSL can be used 
        //eg bean.autowire = 'byName', extbean=ref('extBean') etc 
        bb.beans {
            login(Login)
            user(User){ bean ->
                credentials = new Credentials(username:"John", password:"Doe")
                greetings = 'Welcome!!'
            }

        }

        def ctx = bb.createApplicationContext()

        def u = ctx.getBean("user")
        println u

        def l = ctx.getBean("login")
        println l.authorize(u)

  
  }
  static def bbuilder2(){
            def ctx = new GenericApplicationContext()
            def reader = new GroovyBeanDefinitionReader(ctx)

            reader.beans {
                login(Login)
                user(User){
                    credentials = new Credentials(username:"John", password:"Doe")
                    greetings = 'Welcome!!'
                }

            }

            ctx.refresh()

            def u = ctx.getBean("user")
            println u

            def l = ctx.getBean("login")
            println l.authorize(u)
  
  }
  static def aop(){
  
          def bb = new BeanBuilder()

        //create instances 
        bb.beans  {
            xmlns aop:"http://www.springframework.org/schema/aop"
            aspectBean(LoggerInterceptor)
            pojo(Pojo){
                one = "one"
                two = 2
                three = 3.0d
            }
            aop.config("proxy-target-class":true) {    
                aop.aspect(ref: "aspectBean"){
                    aop.pointcut(id: "pointCutBefore",  expression:"execution(* examples.Pojo.set*(..))")
                    aop.before(method:"logBefore", "pointcut-ref" :"pointCutBefore")
                    
                    aop.pointcut(id: "pointCutAround",  expression:"execution(* examples.Pojo.toString())")
                    aop.around(method:"logAround", "pointcut-ref" :"pointCutAround")
                }
               
            }
         }   


        def ctx = bb.createApplicationContext()

        def u = ctx.getBean("pojo")
        println u

        u.setOne("TWO")
        u.setTwo(35)
        u.setThree(34.0d)
        println(u.getTwo())
        println u
  
  }
  
  static def refresh(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
        Messenger messenger = (Messenger) ctx.getBean("messenger");
        System.out.println(messenger.getMessage());
        // pause execution while I go off and make changes to the source file...
        System.in.read();
        println(messenger.getMessage());
  
  }
}