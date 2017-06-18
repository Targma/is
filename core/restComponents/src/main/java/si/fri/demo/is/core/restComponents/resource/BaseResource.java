package si.fri.demo.is.core.restComponents.resource;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import si.fri.demo.is.core.businessLogic.authentication.AuthEntity;
import si.fri.demo.is.core.businessLogic.database.DatabaseImpl;
import si.fri.demo.is.core.restComponents.providers.exceptions.ApiException;
import si.fri.demo.is.core.restComponents.utility.SwaggerConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;

@ApiResponses({
        @ApiResponse(
                code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                message = SwaggerConstants.STATUS_INTERNAL_SERVER_ERROR_DESC,
                response = ApiException.class)
})
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public abstract class BaseResource {

    protected static final String ROLE_CUSTOMER = AuthEntity.ROLE_CUSTOMER;
    protected static final String ROLE_ADMINISTRATOR = AuthEntity.ROLE_ADMINISTRATOR;

    @Context
    protected SecurityContext sc;

    @Context
    protected Request request;

    @Context
    protected HttpHeaders headers;

    @Context
    protected UriInfo uriInfo;

    @Context
    protected HttpServletRequest httpServletRequest;

    protected abstract AuthEntity getAuthorizedEntity();

    protected abstract DatabaseImpl getDatabaseService();
}
