package si.fri.demo.is.app.server.rest.resources.entities;


import com.github.tfaga.lynx.beans.QueryFilter;
import com.github.tfaga.lynx.beans.QueryParameters;
import com.github.tfaga.lynx.enums.FilterOperation;
import io.swagger.annotations.*;
import org.keycloak.KeycloakPrincipal;
import si.fri.demo.is.app.server.ejb.database.DatabaseServiceLocal;
import si.fri.demo.is.app.server.rest.resources.utility.AuthUtility;
import si.fri.demo.is.core.businessLogic.authentication.AuthEntity;
import si.fri.demo.is.core.businessLogic.database.AuthorizationManager;
import si.fri.demo.is.core.businessLogic.database.Database;
import si.fri.demo.is.core.businessLogic.database.DatabaseImpl;
import si.fri.demo.is.core.businessLogic.exceptions.BusinessLogicTransactionException;
import si.fri.demo.is.core.jpa.entities.Address;
import si.fri.demo.is.core.jpa.entities.Customer;
import si.fri.demo.is.core.restComponents.providers.configuration.PATCH;
import si.fri.demo.is.core.restComponents.providers.exceptions.ApiException;
import si.fri.demo.is.core.restComponents.resource.CrudVersionResource;
import si.fri.demo.is.core.restComponents.utility.QueryParamatersUtility;
import si.fri.demo.is.core.restComponents.utility.SwaggerConstants;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Api(value = "Address")
@Path("Address")
@RequestScoped
public class AddressResource extends CrudVersionResource<Address> {

    @EJB
    private DatabaseServiceLocal databaseImpl;

    @Override
    protected DatabaseImpl getDatabaseService() {
        return databaseImpl;
    }

    protected AuthEntity getAuthorizedEntity() {
        return AuthUtility.getAuthorizedEntity((KeycloakPrincipal) sc.getUserPrincipal());
    }


    public AddressResource() {
        super(Address.class);
    }


    @ApiOperation(
            value = SwaggerConstants.GET_LIST_VALUE + "addresses.",
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
                    response = Address.class,
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
    @RolesAllowed({ROLE_ADMINISTRATOR, ROLE_CUSTOMER})
    @GET
    @Override
    public Response getList() throws BusinessLogicTransactionException {
        return super.getList();
    }


    @ApiOperation(
            value = SwaggerConstants.GET_VALUE + "address.",
            notes = SwaggerConstants.GET_NOTE,
            authorizations = {
                    @Authorization(
                        value = SwaggerConstants.AUTH_VALUE,
                        scopes = {
                            @AuthorizationScope(
                                scope = ROLE_ADMINISTRATOR,
                                description = SwaggerConstants.AUTH_ROLE_ADMIN_DESC),
                            @AuthorizationScope(
                                    scope = ROLE_CUSTOMER,
                                    description = SwaggerConstants.AUTH_ROLE_CUSTOMER_DESC)
                    }
            )
            })
    @ApiResponses({
            @ApiResponse(code = HttpServletResponse.SC_OK, message = SwaggerConstants.STATUS_OK_DESC, response = Address.class),
            @ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = SwaggerConstants.STATUS_NOT_FOUND_DESC, response = ApiException.class),
            @ApiResponse(code = HttpServletResponse.SC_FORBIDDEN, message = SwaggerConstants.STATUS_FORBIDDEN_DESC)
    })
    @RolesAllowed({ROLE_ADMINISTRATOR, ROLE_CUSTOMER})
    @GET
    @Path("{id}")
    @Override
    public Response get(@PathParam("id") Integer id) throws BusinessLogicTransactionException {
        return super.get(id);
    }



    @ApiOperation(
            value = SwaggerConstants.CREATE_VERSION_VALUE + "address.",
            notes = SwaggerConstants.CREATE_VERSION_NOTE,
            authorizations = { @Authorization(
                    value = SwaggerConstants.AUTH_VALUE,
                    scopes = { @AuthorizationScope(
                                    scope = ROLE_CUSTOMER,
                                    description = SwaggerConstants.AUTH_ROLE_CUSTOMER_DESC)
                    }
            )
    })
    @ApiResponses({
            @ApiResponse(code = HttpServletResponse.SC_CREATED, message = SwaggerConstants.STATUS_CREATED_DESC, response = Address.class),
            @ApiResponse(code = HttpServletResponse.SC_NO_CONTENT, message = SwaggerConstants.STATUS_NO_CONTENT_DESC),
            @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = SwaggerConstants.STATUS_BAD_REQUEST_DESC, response = ApiException.class),
            @ApiResponse(code = HttpServletResponse.SC_FORBIDDEN, message = SwaggerConstants.STATUS_FORBIDDEN_DESC)
    })
    @RolesAllowed(ROLE_CUSTOMER)
    @POST
    @Override
    public Response create(@HeaderParam("X-Content") Boolean xContent, Address entity) throws BusinessLogicTransactionException {
        return super.create(xContent, entity);
    }



    @ApiOperation(
            value = SwaggerConstants.PUT_VERSION_VALUE + "address.",
            notes = SwaggerConstants.PUT_VERSION_NOTE,
            authorizations = { @Authorization(
                    value = SwaggerConstants.AUTH_VALUE,
                    scopes = { @AuthorizationScope(
                            scope = ROLE_CUSTOMER,
                            description = SwaggerConstants.AUTH_ROLE_CUSTOMER_DESC)
                    }
            )
    })
    @ApiResponses({
            @ApiResponse(code = HttpServletResponse.SC_CREATED, message = SwaggerConstants.STATUS_CREATED_DESC, response = Address.class),
            @ApiResponse(code = HttpServletResponse.SC_NO_CONTENT, message = SwaggerConstants.STATUS_NO_CONTENT_DESC),
            @ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = SwaggerConstants.STATUS_NOT_FOUND_DESC, response = ApiException.class),
            @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = SwaggerConstants.STATUS_BAD_REQUEST_DESC, response = ApiException.class),
            @ApiResponse(code = HttpServletResponse.SC_FORBIDDEN, message = SwaggerConstants.STATUS_FORBIDDEN_DESC)
    })
    @RolesAllowed(ROLE_CUSTOMER)
    @PUT
    @Path("{id}")
    @Override
    public Response update(@HeaderParam("X-Content") Boolean xContent, @PathParam("id") Integer id, Address entity) throws BusinessLogicTransactionException {
        return super.update(xContent, id, entity);
    }



    @ApiOperation(
            value = SwaggerConstants.PATCH_VERSION_VALUE + "address.",
            notes = SwaggerConstants.PATCH_VERSION_NOTE,
            authorizations = { @Authorization(
                    value = SwaggerConstants.AUTH_VALUE,
                    scopes = { @AuthorizationScope(
                            scope = ROLE_CUSTOMER,
                            description = SwaggerConstants.AUTH_ROLE_CUSTOMER_DESC)
                    }
            )
    })
    @ApiResponses({
            @ApiResponse(code = HttpServletResponse.SC_CREATED, message = SwaggerConstants.STATUS_CREATED_DESC, response = Address.class),
            @ApiResponse(code = HttpServletResponse.SC_NO_CONTENT, message = SwaggerConstants.STATUS_NO_CONTENT_DESC),
            @ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = SwaggerConstants.STATUS_NOT_FOUND_DESC, response = ApiException.class),
            @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = SwaggerConstants.STATUS_BAD_REQUEST_DESC, response = ApiException.class),
            @ApiResponse(code = HttpServletResponse.SC_FORBIDDEN, message = SwaggerConstants.STATUS_FORBIDDEN_DESC)
    })
    @RolesAllowed(ROLE_CUSTOMER)
    @PATCH
    @Path("{id}")
    @Override
    public Response patch(@HeaderParam("X-Content") Boolean xContent, @PathParam("id") Integer id, Address entity) throws BusinessLogicTransactionException {
        return super.patch(xContent, id, entity);
    }



    @ApiOperation(
            value = SwaggerConstants.DELETE_VERSION_VALUE + "address.",
            notes = SwaggerConstants.DELETE_VERSION_NOTE,
            authorizations = { @Authorization(
                    value = SwaggerConstants.AUTH_VALUE,
                    scopes = { @AuthorizationScope(
                            scope = ROLE_CUSTOMER,
                            description = SwaggerConstants.AUTH_ROLE_CUSTOMER_DESC)
                    }
            )
    })
    @ApiResponses({
            @ApiResponse(code = HttpServletResponse.SC_OK, message = SwaggerConstants.STATUS_OK_DESC, response = Address.class),
            @ApiResponse(code = HttpServletResponse.SC_NO_CONTENT, message = SwaggerConstants.STATUS_NO_CONTENT_DESC),
            @ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = SwaggerConstants.STATUS_NOT_FOUND_DESC, response = ApiException.class),
            @ApiResponse(code = HttpServletResponse.SC_FORBIDDEN, message = SwaggerConstants.STATUS_FORBIDDEN_DESC)
    })
    @RolesAllowed(ROLE_CUSTOMER)
    @DELETE
    @Path("{id}")
    @Override
    public Response delete(@HeaderParam("X-Content") Boolean xContent, @PathParam("id") Integer id) throws BusinessLogicTransactionException {
        return super.delete(xContent, id);
    }



    @ApiOperation(
            value = SwaggerConstants.TOGGLE_DELETE_VERSION_VALUE + "address.",
            notes = SwaggerConstants.TOGGLE_DELETE_VERSION_NOTE,
            authorizations = { @Authorization(
                    value = SwaggerConstants.AUTH_VALUE,
                    scopes = { @AuthorizationScope(
                            scope = ROLE_CUSTOMER,
                            description = SwaggerConstants.AUTH_ROLE_CUSTOMER_DESC)
                    }
            )
    })
    @ApiResponses({
            @ApiResponse(code = HttpServletResponse.SC_OK, message = SwaggerConstants.STATUS_OK_DESC, response = Address.class),
            @ApiResponse(code = HttpServletResponse.SC_NO_CONTENT, message = SwaggerConstants.STATUS_NO_CONTENT_DESC),
            @ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = SwaggerConstants.STATUS_NOT_FOUND_DESC, response = ApiException.class),
            @ApiResponse(code = HttpServletResponse.SC_FORBIDDEN, message = SwaggerConstants.STATUS_FORBIDDEN_DESC)
    })
    @RolesAllowed(ROLE_CUSTOMER)
    @PUT
    @Path("{id}/toggleIsDeleted")
    @Override
    public Response toggleIsDeleted(@HeaderParam("X-Content") Boolean xContent, @PathParam("id") Integer id) throws BusinessLogicTransactionException {
        return super.toggleIsDeleted(xContent, id);
    }



    @Override
    protected void initManagers() {
        super.initManagers();
        authorizationManager = new AuthorizationManager<Address>(getAuthorizedEntity()) {

            @Override
            public void setAuthorityFilter(QueryParameters queryParameters, Database database) throws BusinessLogicTransactionException {
                Customer customer = database.getAuthorizedCustomer(authEntity);
                QueryFilter filter = new QueryFilter("customer.id", FilterOperation.EQ, String.valueOf(customer.getId()));
                QueryParamatersUtility.addParam(queryParameters.getFilters(), filter);
            }

            @Override
            public void checkAuthority(Address entity, Database database) throws BusinessLogicTransactionException {
                if(!entity.getCustomer().getAuthenticationId().equals(authEntity.getId())){
                    throw new BusinessLogicTransactionException(Response.Status.FORBIDDEN, "Customer does not have permission.");
                }
            }

            @Override
            public void setEntityAuthority(Address entityAuthority, Database database) throws BusinessLogicTransactionException {
                Customer customer = database.getAuthorizedCustomer(authEntity);
                entityAuthority.setCustomer(customer);
            }

        };
    }

}
