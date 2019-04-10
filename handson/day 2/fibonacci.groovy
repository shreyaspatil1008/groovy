//Iterator-fib
class Fib implements Iterator<BigInteger>{
    BigInteger a = 0G
    BigInteger b = 1G
    boolean hasNext(){
        return true
    }
    
    BigInteger next(){
        (a,b) = [b,a+b]
        return a
    }

}

def fib = new Fib()
fib.take(10).each{println it}
fib.drop(1000000).take(1).collect{it}.head().toString().size()