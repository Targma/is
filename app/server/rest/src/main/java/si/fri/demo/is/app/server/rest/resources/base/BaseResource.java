package si.fri.demo.is.app.server.rest.resources.base;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import si.fri.demo.is.app.server.ejb.database.DatabaseServiceLocal;
import si.fri.demo.is.app.server.rest.providers.exceptions.ApiException;
import si.fri.demo.is.app.server.rest.utility.SwaggerConstants;
import si.fri.demo.is.core.businessLogic.authentication.AuthEntity;

import javax.ejb.EJB;
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

    @EJB
    protected DatabaseServiceLocal databaseService;

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

    protected AuthEntity getAuthorizedEntity(){
        KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal) sc.getUserPrincipal();
        AccessToken token = principal.getKeycloakSecurityContext().getToken();

        AuthEntity authEntity = new AuthEntity();
        authEntity.setId(token.getSubject());
        authEntity.setName(token.getGivenName());
        authEntity.setSurname(token.getFamilyName());
        authEntity.setEmail(token.getEmail());
        authEntity.setRoles(token.getRealmAccess().getRoles());

        return authEntity;
    }

}
