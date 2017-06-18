package si.fri.demo.is.app.server.rest.resources.base;

import si.fri.demo.is.app.server.rest.providers.configuration.PATCH;
import si.fri.demo.is.core.businessLogic.exceptions.BusinessLogicTransactionException;
import si.fri.demo.is.core.jpa.entities.base.BaseEntity;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;


public abstract class CrudResource<T extends BaseEntity> extends GetResource<T> {


    public CrudResource(Class<T> type) {
        super(type);
    }

    @POST
    public Response create(@HeaderParam("X-Content") Boolean xContent, T entity) throws BusinessLogicTransactionException {
        entity.setId(null);

        T dbEntity = databaseService.create(entity, authorizationManager, validationManager);

        return buildResponse(dbEntity, xContent, false, Response.Status.CREATED);
    }

    @PUT
    @Path("{id}")
    public Response update(@HeaderParam("X-Content") Boolean xContent,
                           @PathParam("id") Integer id, T newObject) throws BusinessLogicTransactionException {
        newObject.setId(id);

        T dbEntity = databaseService.update(newObject, authorizationManager, validationManager);

        return buildResponse(dbEntity, xContent);
    }

    @PATCH
    @Path("{id}")
    public Response patch(@HeaderParam("X-Content") Boolean xContent,
                          @PathParam("id") Integer id, T entity) throws BusinessLogicTransactionException {
        entity.setId(id);

        T dbEntity = databaseService.patch(entity, authorizationManager, validationManager);

        return buildResponse(dbEntity, xContent);
    }

    @DELETE
    @Path("{id}")
    public Response delete(@HeaderParam("X-Content") Boolean xContent,
                           @PathParam("id") Integer id) throws BusinessLogicTransactionException {

        T dbEntity = databaseService.delete(type, id, authorizationManager, validationManager);

        return buildResponse(dbEntity, xContent);
    }

    @PUT
    @Path("{id}/toggleIsDeleted")
    public Response toggleIsDeleted(@HeaderParam("X-Content") Boolean xContent,
                                    @PathParam("id") Integer id) throws BusinessLogicTransactionException {

        T dbEntity = databaseService.toggleIsDeleted(type, id, authorizationManager, validationManager);

        return buildResponse(dbEntity, xContent);
    }
}
