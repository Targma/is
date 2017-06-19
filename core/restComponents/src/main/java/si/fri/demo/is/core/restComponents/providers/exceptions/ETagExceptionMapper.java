package si.fri.demo.is.core.restComponents.providers.exceptions;

import si.fri.demo.is.core.restComponents.exceptions.ETagException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ETagExceptionMapper implements ExceptionMapper<ETagException>{

    @Override
    public Response toResponse(ETagException exception) {
        return exception.getResponseBuilder().build();
    }

}
