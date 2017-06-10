package si.fri.demo.is.app.server.rest.resources.base;

import si.fri.demo.is.app.server.rest.providers.configuration.PATCH;
import si.fri.demo.is.core.businessLogic.exceptions.BusinessLogicTransactionException;
import si.fri.demo.is.core.jpa.entities.base.BaseEntity;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;


public abstract class CrudResource<T extends BaseEntity> extends GetResource<T> {

    protected void updateValidation(Integer id, T newObject) throws BusinessLogicTransactionException { authorizationValidation(id); }
    protected void patchValidation(Integer id, T newObject) throws BusinessLogicTransactionException { authorizationValidation(id); }
    protected void createValidation(T newObject) throws BusinessLogicTransactionException { }
    protected void deleteValidation(Integer id) throws BusinessLogicTransactionException { authorizationValidation(id); }
    protected void toggleIsDeletedValidation(Integer id) throws BusinessLogicTransactionException { authorizationValidation(id); }


    public CrudResource(Class<T> type) {
        super(type);
    }


    @PUT
    @Path("{id}")
    public Response update(@PathParam("id") Integer id, T newObject) throws BusinessLogicTransactionException {
        newObject.setId(id);

        updateValidation(id, newObject);

        databaseService.getDatabase().update(newObject);

        return Response.noContent().build();
    }

    @PATCH
    @Path("{id}")
    public Response patch(@PathParam("id") Integer id, T newObject) throws BusinessLogicTransactionException {
        newObject.setId(id);

        patchValidation(id, newObject);

        databaseService.getDatabase().patch(newObject);

        return Response.noContent().build();
    }

    @POST
    public Response create(T object) throws BusinessLogicTransactionException {
        object.setId(null);

        createValidation(object);

        databaseService.getDatabase().create(object);

        return Response.status(Response.Status.CREATED).entity(object).build();
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") Integer id) throws BusinessLogicTransactionException {
        deleteValidation(id);

        databaseService.getDatabase().delete(type, id);

        return Response.noContent().build();
    }

    @PUT
    @Path("{id}/toggleIsDeleted")
    public Response toggleIsDeleted(@PathParam("id") Integer id) throws BusinessLogicTransactionException {
        toggleIsDeletedValidation(id);

        databaseService.getDatabase().toggleIsDeleted(type, id);

        return Response.noContent().build();
    }
}
