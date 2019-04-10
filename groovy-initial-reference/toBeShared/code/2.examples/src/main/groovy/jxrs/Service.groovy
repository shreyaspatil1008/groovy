package jsrx

import javax.jws.soap.*
import javax.jws.*
import javax.xml.ws.*
import javax.xml.bind.annotation.*
import javax.jws.soap.SOAPBinding.Style

@WebService
@SOAPBinding(style=Style.RPC)
//@SOAPBinding(parameterStyle=SOAPBinding.ParameterStyle.BARE)
interface Geom {
	@WebMethod double getArea(double val)
    @WebMethod double getSquare(double val)
}


@WebService(endpointInterface='jsrx.Geom')
class Circle implements Geom {
	double getArea(double r) { return 3.14*r*r }
    double getSquare(double val) { return val*val}
}




