package jsrx;

import javax.jws.*;
import javax.xml.ws.*;
import javax.xml.bind.annotation.*;
import javax.jws.soap.*;


@WebService (targetNamespace="http://experiment/groovy-jax/")
@SOAPBinding(parameterStyle=SOAPBinding.ParameterStyle.BARE)
public class BookService{
	@WebMethod
	public String add(Book book){
		System.out.println("Name of the book: "+ book.name);
        return book.name + book.author;
	}
}

