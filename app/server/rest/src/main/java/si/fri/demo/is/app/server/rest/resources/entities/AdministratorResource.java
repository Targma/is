package si.fri.demo.is.app.server.rest.resources.entities;

import io.swagger.annotations.*;
import org.keycloak.KeycloakPrincipal;
import si.fri.demo.is.app.server.ejb.database.DatabaseServiceLocal;
import si.fri.demo.is.app.server.ejb.interfaces.AdministratorServiceLocal;
import si.fri.demo.is.app.server.rest.resources.utility.AuthUtility;
import si.fri.demo.is.core.businessLogic.authentication.AuthEntity;
import si.fri.demo.is.core.businessLogic.database.AuthorizationManager;
import si.fri.demo.is.core.businessLogic.database.Database;
import si.fri.demo.is.core.businessLogic.database.DatabaseImpl;
import si.fri.demo.is.core.businessLogic.exceptions.BusinessLogicTransactionException;
import si.fri.demo.is.core.jpa.entities.Administrator;
import si.fri.demo.is.core.restComponents.providers.configuration.PATCH;
import si.fri.demo.is.core.restComponents.providers.exceptions.ApiException;
import si.fri.demo.is.core.restComponents.resource.CrudResource;
import si.fri.demo.is.core.restComponents.utility.SwaggerConstants;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Api(value = " Administrator")
@Path("Administrator")
@RequestScoped
public class AdministratorResource extends CrudResource<Administrator> {

    @EJB
    private DatabaseServiceLocal databaseImpl;

    @Override
    protected DatabaseImpl getDatabaseService() {
        return databaseImpl;
    }

    protected AuthEntity getAuthorizedEntity() {
        return AuthUtility.getAuthorizedEntity((KeycloakPrincipal) sc.getUserPrincipal());
    }


    @EJB
    private AdministratorServiceLocal userService;

    public AdministratorResource() {
        super(Administrator.class);
    }



    @ApiOperation(
            value = SwaggerConstants.LOGIN_VALUE + "administrators.",
            notes = SwaggerConstants.LOGIN_NOTE,
            authorizations = {
                    @Authorization(
                            value = SwaggerConstants.AUTH_VALUE,
                            scopes = {
                                    @AuthorizationScope(
                                            scope = ROLE_ADMINISTRATOR,
                                            description = SwaggerConstants.AUTH_ROLE_ADMIN_DESC)
                            }
                    )
            })
    @ApiResponses({
            @ApiResponse(code = HttpServletResponse.SC_OK, message = SwaggerConstants.STATUS_OK_DESC, response = Administrator.class),
            @ApiResponse(code = HttpServletResponse.SC_FORBIDDEN, message = SwaggerConstants.STATUS_FORBIDDEN_DESC)
    })
    @RolesAllowed(ROLE_ADMINISTRATOR)
    @GET
    @Path("login")
    public Response loginUserInfo() throws BusinessLogicTransactionException {
        Administrator administrator = userService.get(getAuthorizedEntity());
        return buildResponse(administrator, true, true).build();
    }


    @ApiOperation(
            value = SwaggerConstants.GET_LIST_VALUE + "administrator.",
            notes = SwaggerConstants.GET_LIST_NOTE,
            authorizations = { @Authorization(
                    value = SwaggerConstants.AUTH_VALUE,
                    scopes = { @AuthorizationScope(
                            scope = ROLE_ADMINISTRATOR,
                            description = SwaggerConstants.AUTH_ROLE_ADMIN_DESC ),
                            @AuthorizationScope(
                                    scope = ROLE_CUSTOMER,
                                    description = SwaggerConstants.AUTH_ROLE_CUSTOMER_DESC )
                    }
            )
            })
    @ApiResponses({
            @ApiResponse(
                    code = HttpServletResponse.SC_OK,
                    message = SwaggerConstants.STATUS_OK_DESC,
                    response = Administrator.class,
                    responseContainer = SwaggerConstants.GET_LIST_CONTAINER,
                    responseHeaders = @ResponseHeader(
                            name = SwaggerConstants.GET_LIST_HEAD_COUNT,
                            description = SwaggerConstants.GET_LIST_HEAD_COUNT_DESC,
                            response = Integer.class
                    )
            ),
            @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = SwaggerConstants.STATUS_BAD_REQUEST_DESC, response = ApiException.class),
            @ApiResponse(code = HttpServletResponse.SC_FORBIDDEN, message = SwaggerConstants.STATUS_FORBIDDEN_DESC)
    })
    @RolesAllowed(ROLE_ADMINISTRATOR)
    @GET
    @Override
    public Response getList() throws BusinessLogicTransactionException {
        return super.getList();
    }



    @ApiOperation(
            value = SwaggerConstants.GET_VALUE + "administrator.",
            notes = SwaggerConstants.GET_NOTE,
            authorizations = {
                @Authorization(
                    value = SwaggerConstants.AUTH_VALUE,
                    scopes = {
                        @AuthorizationScope(
                            scope = ROLE_ADMINISTRATOR,
                            description = SwaggerConstants.AUTH_ROLE_ADMIN_DESC)
                        }
                )
            })
    @ApiResponses({
            @ApiResponse(code = HttpServletResponse.SC_OK, message = SwaggerConstants.STATUS_OK_DESC, response = Administrator.class),
            @ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = SwaggerConstants.STATUS_NOT_FOUND_DESC, response = ApiException.class),
            @ApiResponse(code = HttpServletResponse.SC_FORBIDDEN, message = SwaggerConstants.STATUS_FORBIDDEN_DESC)
    })
    @RolesAllowed(ROLE_ADMINISTRATOR)
    @GET
    @Path("{id}")
    @Override
    public Response get(@PathParam("id") Integer id) throws BusinessLogicTransactionException {
        return super.get(id);
    }


    @ApiOperation(
            value = SwaggerConstants.CREATE_VALUE + "administrator.",
            notes = SwaggerConstants.CREATE_NOTE,
            authorizations = { @Authorization(
                    value = SwaggerConstants.AUTH_VALUE,
                    scopes = { @AuthorizationScope(
                            scope = ROLE_ADMINISTRATOR,
                            description = SwaggerConstants.AUTH_ROLE_ADMIN_DESC)
                    }
            )
            })
    @ApiResponses({
            @ApiResponse(code = HttpServletResponse.SC_CREATED, message = SwaggerConstants.STATUS_CREATED_DESC, response = Administrator.class),
            @ApiResponse(code = HttpServletResponse.SC_NO_CONTENT, message = SwaggerConstants.STATUS_NO_CONTENT_DESC),
            @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = SwaggerConstants.STATUS_BAD_REQUEST_DESC, response = ApiException.class),
            @ApiResponse(code = HttpServletResponse.SC_FORBIDDEN, message = SwaggerConstants.STATUS_FORBIDDEN_DESC)
    })
    @RolesAllowed(ROLE_ADMINISTRATOR)
    @POST
    @Override
    public Response create(@HeaderParam("X-Content") Boolean xContent, Administrator entity) throws BusinessLogicTransactionException {
        throw BusinessLogicTransactionException.buildNotImplemented();
    }


    @ApiOperation(
            value = SwaggerConstants.PUT_VALUE + "administrator.",
            notes = SwaggerConstants.PUT_NOTE,
            authorizations = { @Authorization(
                    value = SwaggerConstants.AUTH_VALUE,
                    scopes = { @AuthorizationScope(
                            scope = ROLE_ADMINISTRATOR,
                            description = SwaggerConstants.AUTH_ROLE_ADMIN_DESC)
                    }
            )
            })
    @ApiResponses({
            @ApiResponse(code = HttpServletResponse.SC_CREATED, message = SwaggerConstants.STATUS_CREATED_DESC, response = Administrator.class),
            @ApiResponse(code = HttpServletResponse.SC_NO_CONTENT, message = SwaggerConstants.STATUS_NO_CONTENT_DESC),
            @ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = SwaggerConstants.STATUS_NOT_FOUND_DESC, response = ApiException.class),
            @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = SwaggerConstants.STATUS_BAD_REQUEST_DESC, response = ApiException.class),
            @ApiResponse(code = HttpServletResponse.SC_FORBIDDEN, message = SwaggerConstants.STATUS_FORBIDDEN_DESC)
    })
    @RolesAllowed(ROLE_ADMINISTRATOR)
    @PUT
    @Path("{id}")
    @Override
    public Response update(@HeaderParam("X-Content") Boolean xContent,
                           @PathParam("id") Integer id, Administrator newObject) throws BusinessLogicTransactionException {
        return super.update(xContent, id, newObject);
    }


    @ApiOperation(
            value = SwaggerConstants.PATCH_VALUE + "administrator.",
            notes = SwaggerConstants.PATCH_NOTE,
            authorizations = { @Authorization(
                    value = SwaggerConstants.AUTH_VALUE,
                    scopes = { @AuthorizationScope(
                            scope = ROLE_ADMINISTRATOR,
                            description = SwaggerConstants.AUTH_ROLE_ADMIN_DESC)
                    }
            )
            })
    @ApiResponses({
            @ApiResponse(code = HttpServletResponse.SC_CREATED, message = SwaggerConstants.STATUS_CREATED_DESC, response = Administrator.class),
            @ApiResponse(code = HttpServletResponse.SC_NO_CONTENT, message = SwaggerConstants.STATUS_NO_CONTENT_DESC),
            @ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = SwaggerConstants.STATUS_NOT_FOUND_DESC, response = ApiException.class),
            @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = SwaggerConstants.STATUS_BAD_REQUEST_DESC, response = ApiException.class),
            @ApiResponse(code = HttpServletResponse.SC_FORBIDDEN, message = SwaggerConstants.STATUS_FORBIDDEN_DESC)
    })
    @RolesAllowed(ROLE_ADMINISTRATOR)
    @PATCH
    @Path("{id}")
    @Override
    public Response patch(@HeaderParam("X-Content") Boolean xContent,
                          @PathParam("id") Integer id, Administrator entity) throws BusinessLogicTransactionException {
        return super.patch(xContent, id, entity);
    }



    @ApiOperation(
            value = SwaggerConstants.DELETE_VALUE + "administrator.",
            notes = SwaggerConstants.DELETE_NOTE,
            authorizations = { @Authorization(
                    value = SwaggerConstants.AUTH_VALUE,
                    scopes = { @AuthorizationScope(
                            scope = ROLE_ADMINISTRATOR,
                            description = SwaggerConstants.AUTH_ROLE_ADMIN_DESC)
                    }
            )
            })
    @ApiResponses({
            @ApiResponse(code = HttpServletResponse.SC_OK, message = SwaggerConstants.STATUS_OK_DESC, response = Administrator.class),
            @ApiResponse(code = HttpServletResponse.SC_NO_CONTENT, message = SwaggerConstants.STATUS_NO_CONTENT_DESC),
            @ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = SwaggerConstants.STATUS_NOT_FOUND_DESC, response = ApiException.class),
            @ApiResponse(code = HttpServletResponse.SC_FORBIDDEN, message = SwaggerConstants.STATUS_FORBIDDEN_DESC)
    })
    @RolesAllowed(ROLE_ADMINISTRATOR)
    @DELETE
    @Path("{id}")
    @Override
    public Response delete(@HeaderParam("X-Content") Boolean xContent,
                           @PathParam("id") Integer id) throws BusinessLogicTransactionException {
        return super.delete(xContent, id);
    }



    @ApiOperation(
            value = SwaggerConstants.TOGGLE_DELETE_VALUE + "administrator.",
            notes = SwaggerConstants.TOGGLE_DELETE_NOTE,
            authorizations = { @Authorization(
                    value = SwaggerConstants.AUTH_VALUE,
                    scopes = { @AuthorizationScope(
                            scope = ROLE_ADMINISTRATOR,
                            description = SwaggerConstants.AUTH_ROLE_ADMIN_DESC)
                    }
            )
            })
    @ApiResponses({
            @ApiResponse(code = HttpServletResponse.SC_OK, message = SwaggerConstants.STATUS_OK_DESC, response = Administrator.class),
            @ApiResponse(code = HttpServletResponse.SC_NO_CONTENT, message = SwaggerConstants.STATUS_NO_CONTENT_DESC),
            @ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = SwaggerConstants.STATUS_NOT_FOUND_DESC, response = ApiException.class),
            @ApiResponse(code = HttpServletResponse.SC_FORBIDDEN, message = SwaggerConstants.STATUS_FORBIDDEN_DESC)
    })
    @RolesAllowed(ROLE_ADMINISTRATOR)
    @PUT
    @Path("{id}/toggleIsDeleted")
    @Override
    public Response toggleIsDeleted(@HeaderParam("X-Content") Boolean xContent,
                                    @PathParam("id") Integer id) throws BusinessLogicTransactionException {
        return super.toggleIsDeleted(xContent, id);
    }

    @Override
    protected AuthorizationManager<Administrator> initAuthorizationManager() {
        return new AuthorizationManager<Administrator>(getAuthorizedEntity()) {

            @Override
            public void checkAuthority(Administrator entity, Database database) throws BusinessLogicTransactionException {
                if(!entity.getAuthenticationId().equals(authEntity.getId())){
                    throw new BusinessLogicTransactionException(Response.Status.FORBIDDEN, "Administrator does not have permission.");
                }
            }

        };
    }
}
