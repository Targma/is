package si.fri.demo.is.app.server.rest.resources.entities;

import com.github.tfaga.lynx.beans.QueryFilter;
import com.github.tfaga.lynx.beans.QueryParameters;
import com.github.tfaga.lynx.enums.FilterOperation;
import si.fri.demo.is.app.server.ejb.interfaces.OrderServiceLocal;
import si.fri.demo.is.app.server.rest.resources.base.GetResource;
import si.fri.demo.is.app.server.rest.utility.QueryParamatersUtility;
import si.fri.demo.is.core.businessLogic.authentication.base.AuthEntity;
import si.fri.demo.is.core.businessLogic.exceptions.BusinessLogicTransactionException;
import si.fri.demo.is.core.jpa.entities.Customer;
import si.fri.demo.is.core.jpa.entities.Order;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("Order")
@RequestScoped
public class OrderResource extends GetResource<Order> {

    @EJB
    private OrderServiceLocal orderService;

    public OrderResource() {
        super(Order.class);
    }

    @RolesAllowed({ROLE_ADMINISTRATOR, ROLE_CUSTOMER})
    @GET
    @Override
    public Response getList() throws BusinessLogicTransactionException {
        return super.getList();
    }

    @RolesAllowed({ROLE_ADMINISTRATOR, ROLE_CUSTOMER})
    @GET
    @Path("{id}")
    @Override
    public Response get(@PathParam("id") Integer id) throws BusinessLogicTransactionException {
        return super.get(id);
    }

    @RolesAllowed(ROLE_CUSTOMER)
    @POST
    public Response process(Order order) throws BusinessLogicTransactionException {
        orderService.process(order);

        Order resOrder = new Order();
        resOrder.setId(order.getId());
        return Response.status(Response.Status.CREATED).entity(resOrder).build();
    }

    @Override
    protected void authorizationValidation(QueryParameters queryParameters) throws BusinessLogicTransactionException {
        AuthEntity authEntity = authProvider.generateAuthEntity(sc.getUserPrincipal());
        if(authEntity.getAccountType() == AuthEntity.AccountType.CUSTOMER){
            Customer customer = databaseService.getDatabase().getAuthorizedCustomer(authEntity);

            QueryFilter filter = new QueryFilter("customerId", FilterOperation.EQ, String.valueOf(customer.getId()));
            QueryParamatersUtility.addParam(queryParameters.getFilters(), filter);
        }
    }

    @Override
    protected void authorizationValidation(Integer id) throws BusinessLogicTransactionException {
        AuthEntity authEntity = authProvider.generateAuthEntity(sc.getUserPrincipal());
        if(authEntity.getAccountType() == AuthEntity.AccountType.CUSTOMER){

        }
    }
}
