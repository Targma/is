package si.fri.demo.is.app.server.rest.resources.entities;

import io.swagger.annotations.*;
import si.fri.demo.is.app.server.ejb.interfaces.UserServiceLocal;
import si.fri.demo.is.app.server.rest.providers.configuration.PATCH;
import si.fri.demo.is.app.server.rest.providers.exceptions.ApiException;
import si.fri.demo.is.app.server.rest.resources.base.CrudResource;
import si.fri.demo.is.app.server.rest.utility.SwaggerConstants;
import si.fri.demo.is.core.businessLogic.database.AuthorizationManager;
import si.fri.demo.is.core.businessLogic.database.Database;
import si.fri.demo.is.core.businessLogic.exceptions.BusinessLogicTransactionException;
import si.fri.demo.is.core.jpa.entities.User;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Api(value = "User")

@Path("User")
@RequestScoped
public class UserResource extends CrudResource<User> {

    @EJB
    private UserServiceLocal userService;

    public UserResource() {
        super(User.class);
    }

    @RolesAllowed(ROLE_ADMINISTRATOR)
    @GET
    @Path("login")
    public Response loginUserInfo() throws BusinessLogicTransactionException {
        User user = userService.get(getAuthorizedEntity());
        return buildResponse(user, false, true);
    }


    @ApiOperation(
            value = SwaggerConstants.GET_LIST_VALUE + "user.",
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
                    response = User.class,
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
            value = SwaggerConstants.GET_VALUE + "user.",
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
            @ApiResponse(code = HttpServletResponse.SC_OK, message = SwaggerConstants.STATUS_OK_DESC, response = User.class),
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
            value = SwaggerConstants.CREATE_VALUE + "user.",
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
            @ApiResponse(code = HttpServletResponse.SC_CREATED, message = SwaggerConstants.STATUS_CREATED_DESC, response = User.class),
            @ApiResponse(code = HttpServletResponse.SC_NO_CONTENT, message = SwaggerConstants.STATUS_NO_CONTENT_DESC),
            @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = SwaggerConstants.STATUS_BAD_REQUEST_DESC, response = ApiException.class),
            @ApiResponse(code = HttpServletResponse.SC_FORBIDDEN, message = SwaggerConstants.STATUS_FORBIDDEN_DESC)
    })
    @RolesAllowed(ROLE_ADMINISTRATOR)
    @POST
    @Override
    public Response create(@HeaderParam("X-Content") Boolean xContent, User entity) throws BusinessLogicTransactionException {
        throw BusinessLogicTransactionException.buildNotImplemented();
    }


    @ApiOperation(
            value = SwaggerConstants.PUT_VALUE + "user.",
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
            @ApiResponse(code = HttpServletResponse.SC_CREATED, message = SwaggerConstants.STATUS_CREATED_DESC, response = User.class),
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
                           @PathParam("id") Integer id, User newObject) throws BusinessLogicTransactionException {
        return super.update(xContent, id, newObject);
    }


    @ApiOperation(
            value = SwaggerConstants.PATCH_VALUE + "user.",
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
            @ApiResponse(code = HttpServletResponse.SC_CREATED, message = SwaggerConstants.STATUS_CREATED_DESC, response = User.class),
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
                          @PathParam("id") Integer id, User entity) throws BusinessLogicTransactionException {
        return super.patch(xContent, id, entity);
    }



    @ApiOperation(
            value = SwaggerConstants.DELETE_VALUE + "user.",
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
            @ApiResponse(code = HttpServletResponse.SC_OK, message = SwaggerConstants.STATUS_OK_DESC, response = User.class),
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
            value = SwaggerConstants.TOGGLE_DELETE_VALUE + "user.",
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
            @ApiResponse(code = HttpServletResponse.SC_OK, message = SwaggerConstants.STATUS_OK_DESC, response = User.class),
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
    protected void initManagers() {
        authorizationManager = new AuthorizationManager<User>(getAuthorizedEntity()) {

            @Override
            public void checkAuthority(User entity, Database database) throws BusinessLogicTransactionException {
                if(!entity.getAuthenticationId().equals(authEntity.getId())){
                    throw new BusinessLogicTransactionException(Response.Status.FORBIDDEN, "User does not have permission.");
                }
            }

        };
    }
}
