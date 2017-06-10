package si.fri.demo.is.app.server.rest.resources.entities;

import si.fri.demo.is.app.server.rest.providers.configuration.PATCH;
import si.fri.demo.is.app.server.rest.resources.base.CrudVersionResource;
import si.fri.demo.is.core.businessLogic.exceptions.BusinessLogicTransactionException;
import si.fri.demo.is.core.jpa.entities.Product;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("Product")
@RequestScoped
public class ProductResource extends CrudVersionResource<Product> {

    public ProductResource() {
        super(Product.class);
    }

    @PermitAll
    @GET
    @Override
    public Response getList() throws BusinessLogicTransactionException {
        return super.getList();
    }

    @PermitAll
    @GET
    @Path("{id}")
    @Override
    public Response get(@PathParam("id") Integer id) throws BusinessLogicTransactionException {
        return super.get(id);
    }

    @RolesAllowed(ROLE_ADMINISTRATOR)
    @PUT
    @Path("{id}")
    @Override
    public Response update(Integer id, Product newObject) throws BusinessLogicTransactionException {
        return super.update(id, newObject);
    }

    @RolesAllowed(ROLE_ADMINISTRATOR)
    @PATCH
    @Path("{id}")
    @Override
    public Response patch(Integer id, Product newObject) throws BusinessLogicTransactionException {
        return super.patch(id, newObject);
    }

    @RolesAllowed(ROLE_ADMINISTRATOR)
    @POST
    @Override
    public Response create(Product object) throws BusinessLogicTransactionException {
        return super.create(object);
    }

    @RolesAllowed(ROLE_ADMINISTRATOR)
    @DELETE
    @Path("{id}")
    @Override
    public Response delete(Integer id) throws BusinessLogicTransactionException {
        return super.delete(id);
    }

    @RolesAllowed(ROLE_ADMINISTRATOR)
    @PUT
    @Path("{id}/toggleIsDeleted")
    @Override
    public Response toggleIsDeleted(Integer id) throws BusinessLogicTransactionException {
        return super.toggleIsDeleted(id);
    }

}
