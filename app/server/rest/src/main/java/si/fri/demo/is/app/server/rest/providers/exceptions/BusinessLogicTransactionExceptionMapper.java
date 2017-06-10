package si.fri.demo.is.app.server.rest.providers.exceptions;


import si.fri.demo.is.core.businessLogic.exceptions.BusinessLogicTransactionException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.logging.Level;
import java.util.logging.Logger;

@Provider
public class BusinessLogicTransactionExceptionMapper implements ExceptionMapper<BusinessLogicTransactionException> {

    static final Logger LOG = Logger.getLogger(BusinessLogicTransactionExceptionMapper.class.getSimpleName());

    @Context
    protected HttpServletRequest httpServletRequest;

    @Override
    public Response toResponse(BusinessLogicTransactionException e) {
        LOG.log(Level.SEVERE, String.format("[%s][%s][%s}", "VcgServerTransactionException", httpServletRequest.getRequestURI(), httpServletRequest.getRemoteAddr()), e);
        return Response.status(e.getStatus()).entity(e.toJson()).build();
    }
}
