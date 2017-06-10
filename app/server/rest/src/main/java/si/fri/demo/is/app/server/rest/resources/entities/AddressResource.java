package si.fri.demo.is.app.server.rest.resources.entities;


import com.github.tfaga.lynx.beans.QueryFilter;
import com.github.tfaga.lynx.beans.QueryParameters;
import com.github.tfaga.lynx.enums.FilterOperation;
import si.fri.demo.is.app.server.rest.providers.configuration.PATCH;
import si.fri.demo.is.app.server.rest.resources.base.CrudVersionResource;
import si.fri.demo.is.app.server.rest.utility.QueryParamatersUtility;
import si.fri.demo.is.core.businessLogic.authentication.base.AuthEntity;
import si.fri.demo.is.core.businessLogic.exceptions.BusinessLogicTransactionException;
import si.fri.demo.is.core.jpa.entities.Address;
import si.fri.demo.is.core.jpa.entities.Customer;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("Address")
@RequestScoped
public class AddressResource extends CrudVersionResource<Address> {

    public AddressResource() {
        super(Address.class);
    }

    @RolesAllowed(ROLE_CUSTOMER)
    @PUT
    @Path("{id}")
    @Override
    public Response update(Integer id, Address newObject) throws BusinessLogicTransactionException {
        return super.update(id, newObject);
    }

    @RolesAllowed(ROLE_CUSTOMER)
    @PATCH
    @Path("{id}")
    @Override
    public Response patch(Integer id, Address newObject) throws BusinessLogicTransactionException {
        return super.patch(id, newObject);
    }

    @RolesAllowed(ROLE_CUSTOMER)
    @POST
    @Override
    public Response create(Address object) throws BusinessLogicTransactionException {
        return super.create(object);
    }

    @RolesAllowed(ROLE_CUSTOMER)
    @DELETE
    @Path("{id}")
    @Override
    public Response delete(Integer id) throws BusinessLogicTransactionException {
        return super.delete(id);
    }

    @RolesAllowed(ROLE_CUSTOMER)
    @PUT
    @Path("{id}/toggleIsDeleted")
    @Override
    public Response toggleIsDeleted(Integer id) throws BusinessLogicTransactionException {
        return super.toggleIsDeleted(id);
    }

    @Override
    protected void authorizationValidation(QueryParameters queryParameters) throws BusinessLogicTransactionException {
        AuthEntity authEntity = authProvider.generateAuthEntity(sc.getUserPrincipal());
        Customer customer = databaseService.getDatabase().getAuthorizedCustomer(authEntity);

        QueryFilter filter = new QueryFilter("customerId", FilterOperation.EQ, String.valueOf(customer.getId()));
        QueryParamatersUtility.addParam(queryParameters.getFilters(), filter);
    }

    @Override
    protected void authorizationValidation(Integer id) throws BusinessLogicTransactionException {
        AuthEntity authEntity = authProvider.generateAuthEntity(sc.getUserPrincipal());
        Customer customer = databaseService.getDatabase().getAuthorizedCustomer(authEntity);

        Address address = databaseService.getDatabase().get(Address.class, id);
        if(address != null){
            if(!address.getCustomer().getId().equals(customer.getId())){
                throw new BusinessLogicTransactionException(Response.Status.FORBIDDEN, "Customer does not have permission.");
            }
        }
    }
}
