package si.fri.demo.is.app.server.rest.resources.entities;

import si.fri.demo.is.app.server.ejb.interfaces.CustomerServiceLocal;
import si.fri.demo.is.app.server.rest.providers.configuration.PATCH;
import si.fri.demo.is.app.server.rest.resources.base.CrudVersionResource;
import si.fri.demo.is.core.businessLogic.authentication.base.AuthEntity;
import si.fri.demo.is.core.businessLogic.exceptions.BusinessLogicTransactionException;
import si.fri.demo.is.core.jpa.entities.Customer;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.security.Principal;

@Path("Customer")
@RequestScoped
public class CustomerResource extends CrudVersionResource<Customer> {

    @EJB
    private CustomerServiceLocal customerService;

    public CustomerResource() {
        super(Customer.class);
    }

    @RolesAllowed(ROLE_CUSTOMER)
    @GET
    @Path("login")
    public Response loginUserInfo() throws BusinessLogicTransactionException {
        Principal principal = sc.getUserPrincipal();
        AuthEntity authEntity = authProvider.generateAuthEntity(principal);
        Customer customer = customerService.get(authEntity);
        return Response.ok(customer).build();
    }

    @RolesAllowed(ROLE_CUSTOMER)
    @PUT
    @Path("{id}")
    @Override
    public Response update(Integer id, Customer newObject) throws BusinessLogicTransactionException {
        return super.update(id, newObject);
    }

    @RolesAllowed(ROLE_CUSTOMER)
    @PATCH
    @Path("{id}")
    @Override
    public Response patch(Integer id, Customer newObject) throws BusinessLogicTransactionException {
        return super.patch(id, newObject);
    }

    @RolesAllowed(ROLE_ADMINISTRATOR)
    @Override
    public Response getList() throws BusinessLogicTransactionException {
        return super.getList();
    }

    @RolesAllowed(ROLE_ADMINISTRATOR)
    @Override
    public Response get(Integer id) throws BusinessLogicTransactionException {
        return super.get(id);
    }

    @RolesAllowed(ROLE_CUSTOMER)
    @POST
    @Override
    public Response create(Customer object) throws BusinessLogicTransactionException {
        throw BusinessLogicTransactionException.buildNotImplemented();
    }

    @RolesAllowed(ROLE_ADMINISTRATOR)
    @DELETE
    @Path("{id}")
    @Override
    public Response delete(Integer id) throws BusinessLogicTransactionException {
        return super.delete(id);
    }

    @RolesAllowed({ROLE_CUSTOMER})
    @PUT
    @Path("{id}/toggleIsDeleted")
    @Override
    public Response toggleIsDeleted(Integer id) throws BusinessLogicTransactionException {
        return super.toggleIsDeleted(id);
    }

    @Override
    protected void authorizationValidation(Integer id) throws BusinessLogicTransactionException {
        AuthEntity authEntity = authProvider.generateAuthEntity(sc.getUserPrincipal());
        if(authEntity.getAccountType() == AuthEntity.AccountType.CUSTOMER){
            Customer customer = databaseService.getDatabase().getAuthorizedCustomer(authEntity);
            if(customer.getId() != id){
                throw new BusinessLogicTransactionException(Response.Status.FORBIDDEN, "Customer does not have permission.");
            }
        }
    }
}
