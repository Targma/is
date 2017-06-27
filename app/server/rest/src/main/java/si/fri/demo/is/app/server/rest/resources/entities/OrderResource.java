package si.fri.demo.is.app.server.rest.resources.entities;

import com.github.tfaga.lynx.beans.QueryFilter;
import com.github.tfaga.lynx.beans.QueryParameters;
import com.github.tfaga.lynx.enums.FilterOperation;
import io.swagger.annotations.*;
import org.keycloak.KeycloakPrincipal;
import si.fri.demo.is.app.server.ejb.database.DatabaseServiceLocal;
import si.fri.demo.is.app.server.ejb.interfaces.OrderServiceLocal;
import si.fri.demo.is.app.server.rest.resources.utility.AuthUtility;
import si.fri.demo.is.core.businessLogic.authentication.AuthEntity;
import si.fri.demo.is.core.businessLogic.database.AuthorizationManager;
import si.fri.demo.is.core.businessLogic.database.Database;
import si.fri.demo.is.core.businessLogic.database.DatabaseImpl;
import si.fri.demo.is.core.businessLogic.exceptions.BusinessLogicTransactionException;
import si.fri.demo.is.core.jpa.entities.Order;
import si.fri.demo.is.core.restComponents.providers.exceptions.ApiException;
import si.fri.demo.is.core.restComponents.resource.GetResource;
import si.fri.demo.is.core.restComponents.utility.QueryParamatersUtility;
import si.fri.demo.is.core.restComponents.utility.SwaggerConstants;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Api(value = "Order")

@Path("Order")
@RequestScoped
public class OrderResource extends GetResource<Order> {

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
    private OrderServiceLocal orderService;


    public OrderResource() {
        super(Order.class);
        defaultMaxLimit = 15;
    }


    @ApiOperation(
            value = SwaggerConstants.GET_LIST_VALUE + "orders.",
            notes = SwaggerConstants.GET_LIST_NOTE,
            authorizations = { @Authorization(
                    value = SwaggerConstants.AUTH_VALUE,
                    scopes = {
                            @AuthorizationScope(
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
                    response = Order.class,
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
            value = SwaggerConstants.GET_VALUE + "order.",
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
            @ApiResponse(code = HttpServletResponse.SC_OK, message = SwaggerConstants.STATUS_OK_DESC, response = Order.class),
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
            value = "Process order.",
            notes = "Order must include at least one product item.",
            authorizations = { @Authorization(
                    value = SwaggerConstants.AUTH_VALUE,
                    scopes = { @AuthorizationScope(
                            scope = ROLE_CUSTOMER,
                            description = SwaggerConstants.AUTH_ROLE_CUSTOMER_DESC)
                    }
            )
            })
    @ApiResponses({
            @ApiResponse(code = HttpServletResponse.SC_CREATED, message = SwaggerConstants.STATUS_CREATED_DESC, response = Order.class),
            @ApiResponse(code = HttpServletResponse.SC_NO_CONTENT, message = SwaggerConstants.STATUS_NO_CONTENT_DESC),
            @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = SwaggerConstants.STATUS_BAD_REQUEST_DESC, response = ApiException.class),
            @ApiResponse(code = HttpServletResponse.SC_FORBIDDEN, message = SwaggerConstants.STATUS_FORBIDDEN_DESC)
    })
    @RolesAllowed(ROLE_CUSTOMER)
    @POST
    @Path("process")
    public Response process(@HeaderParam("X-Content") Boolean xContent, Order order) throws BusinessLogicTransactionException {

        Order dbEntity = orderService.process(order);

        return buildResponse(dbEntity, xContent, false, Response.Status.CREATED).build();
    }

    @Override
    protected void initManagers() {
        super.initManagers();
        authorizationManager = new AuthorizationManager<Order>(getAuthorizedEntity()) {

            @Override
            public void setAuthorityFilter(QueryParameters queryParameters, Database database) throws BusinessLogicTransactionException {
                QueryFilter filter = new QueryFilter("customer.authenticationId", FilterOperation.EQ, authEntity.getId());
                QueryParamatersUtility.addParam(queryParameters.getFilters(), filter);
            }

            @Override
            public void checkAuthority(Order entity, Database database) throws BusinessLogicTransactionException {
                if(!entity.getCustomer().getAuthenticationId().equals(authEntity.getId())){
                    throw new BusinessLogicTransactionException(Response.Status.FORBIDDEN, "Account does not have permission.");
                }
            }
        };
    }

}
