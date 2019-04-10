
def sendEmail(){
    email{
        from "from@gmail.com"
        to "to@gmail.com"
        subject "hello"
        test "testing","testing","testing","testing"
        //header "X=Y","Z=Z"
        body {
            header()
            p "Hi, how are you?"
            h1 "Bro...."
            footer()
        }
    }
}

def email(Closure cl){

    def del = new EmailSpec()
    def nc = cl.rehydrate(del,null,null)  //basically doing cl.delegate = del
    nc.resolveStrategy = Closure.DELEGATE_ONLY
    nc()
}

class EmailSpec{

    def from(String arg){println "From:$arg"}
    def to(String arg){println "to:$arg"}
    def subject(String arg){println "subject:$arg"}
    def body(Closure cl){
                
                    def del = new BodySpec()
                    def nc = cl.rehydrate(del,null,null)  //basically doing cl.delegate = del
                    nc.resolveStrategy = Closure.DELEGATE_ONLY
                    nc()
                }
    def methodMissing(String name,args)
        {println "${name.toUpperCase()} ${args.join(',')}"}
    
}

class BodySpec{

    //def p(String arg){println "<p>$arg</p>"}
    //def h1(String arg){println "<h1>$arg</h1>"}
    def header(){println "<html><body>"}
    def footer(){println "</body></html>"}
    def methodMissing(String name,args)
        {println "<${name}> ${args.join(',')} </${name}>"}
    
}
sendEmail()

