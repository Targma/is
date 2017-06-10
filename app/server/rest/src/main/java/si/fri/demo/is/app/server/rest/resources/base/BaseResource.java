package si.fri.demo.is.app.server.rest.resources.base;

import si.fri.demo.is.app.server.ejb.database.DatabaseServiceLocal;
import si.fri.demo.is.core.businessLogic.authentication.KeycloakAuthProvider;
import si.fri.demo.is.core.businessLogic.authentication.base.AuthProvider;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public abstract class BaseResource {

    public static final String ROLE_CUSTOMER = "CUSTOMER";
    public static final String ROLE_ADMINISTRATOR = "ADMINISTRATOR";


    @EJB
    protected DatabaseServiceLocal databaseService;

    @Context
    protected SecurityContext sc;

    @Context
    protected Request request;

    @Context
    protected UriInfo uriInfo;

    @Context
    protected HttpServletRequest httpServletRequest;

    protected AuthProvider authProvider = new KeycloakAuthProvider();

}
