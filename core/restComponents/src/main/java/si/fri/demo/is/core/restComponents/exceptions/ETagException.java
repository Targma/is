package si.fri.demo.is.core.restComponents.exceptions;

import si.fri.demo.is.core.businessLogic.exceptions.BusinessLogicTransactionException;

import javax.ws.rs.core.Response;

public class ETagException extends BusinessLogicTransactionException {

    private Response.ResponseBuilder responseBuilder;

    public ETagException(Response.Status status, String message, Response.ResponseBuilder responseBuilder) {
        super(status, message);
        this.responseBuilder = responseBuilder;
    }

    public Response.ResponseBuilder getResponseBuilder() {
        return responseBuilder;
    }
}
