package si.fri.demo.is.app.server.rest.resources.base;

import si.fri.demo.is.app.server.rest.providers.configuration.PATCH;
import si.fri.demo.is.core.businessLogic.exceptions.BusinessLogicTransactionException;
import si.fri.demo.is.core.jpa.entities.base.BaseEntityVersion;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

public abstract class CrudVersionResource<T extends BaseEntityVersion> extends GetResource {

    protected void updateValidation(Integer id, T newObject) throws BusinessLogicTransactionException { authorizationValidation(id); }
    protected void patchValidation(Integer id, T newObject) throws BusinessLogicTransactionException { authorizationValidation(id); }
    protected void createValidation(T newObject) throws BusinessLogicTransactionException { }
    protected void deleteValidation(Integer id) throws BusinessLogicTransactionException { authorizationValidation(id); }
    protected void toggleIsDeletedValidation(Integer id) throws BusinessLogicTransactionException { authorizationValidation(id); }

    protected boolean useCustomCreate = false;
    protected T customCreate(T newEntity) throws BusinessLogicTransactionException { throw  BusinessLogicTransactionException.buildNotImplemented(); }

    protected boolean useCustomUpdate = false;
    protected T customUpdate(int id, T newEntity) throws BusinessLogicTransactionException { throw BusinessLogicTransactionException.buildNotImplemented(); }

    protected boolean useCustomPatch = false;
    protected T customPatch(int id, T newEntity) throws BusinessLogicTransactionException { throw BusinessLogicTransactionException.buildNotImplemented(); }


    public CrudVersionResource(Class type) {
        super(type);
    }


    @PUT
    @Path("{id}")
    public Response update(@PathParam("id") Integer id, T newObject) throws BusinessLogicTransactionException {
        newObject.setId(id);

        updateValidation(id, newObject);

        if(useCustomUpdate){
            customUpdate(id, newObject);
        } else {
            databaseService.updateVersion(id, newObject);
        }

        return Response.noContent().build();
    }

    @PATCH
    @Path("{id}")
    public Response patch(@PathParam("id") Integer id, T newObject) throws BusinessLogicTransactionException {
        newObject.setId(id);

        patchValidation(id, newObject);

        if(useCustomPatch){
            customPatch(id, newObject);
        } else {
            databaseService.patchVersion(id, newObject);
        }

        return Response.noContent().build();
    }

    @POST
    public Response create(T object) throws BusinessLogicTransactionException {
        object.setId(null);

        createValidation(object);

        if(useCustomCreate){
            customCreate(object);
        } else {
            databaseService.createVersion(object);
        }

        return Response.status(Response.Status.CREATED).entity(object).build();
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") Integer id) throws BusinessLogicTransactionException {
        deleteValidation(id);

        databaseService.delete(type, id);

        return Response.noContent().build();
    }

    @PUT
    @Path("{id}/toggleIsDeleted")
    public Response toggleIsDeleted(@PathParam("id") Integer id) throws BusinessLogicTransactionException {
        toggleIsDeletedValidation(id);

        databaseService.toggleIsDeleted(type, id);

        return Response.noContent().build();
    }
}
