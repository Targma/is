package si.fri.demo.is.app.server.rest.resources.base;

import si.fri.demo.is.app.server.rest.providers.configuration.PATCH;
import si.fri.demo.is.core.businessLogic.exceptions.BusinessLogicTransactionException;
import si.fri.demo.is.core.jpa.entities.base.BaseEntityVersion;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

public abstract class CrudVersionResource<T extends BaseEntityVersion> extends GetResource<T> {

    public CrudVersionResource(Class<T> type) {
        super(type);
    }

    @POST
    public Response create(@HeaderParam("X-Content") Boolean xContent, T entity) throws BusinessLogicTransactionException {
        entity.setId(null);

        T dbEntity = databaseService.createVersion(entity, authorizationManager, validationManager);

        return buildResponse(dbEntity, xContent, true, Response.Status.CREATED);
    }

    @PUT
    @Path("{id}")
    public Response update(@HeaderParam("X-Content") Boolean xContent,
                           @PathParam("id") Integer id, T entity) throws BusinessLogicTransactionException {
        entity.setId(id);

        T dbEntity = databaseService.updateVersion(id, entity, authorizationManager, validationManager);

        return buildResponse(dbEntity, xContent, true, Response.Status.CREATED);
    }

    @PATCH
    @Path("{id}")
    public Response patch(@HeaderParam("X-Content") Boolean xContent,
                          @PathParam("id") Integer id, T entity) throws BusinessLogicTransactionException {
        entity.setId(id);

        T dbEntity = databaseService.patchVersion(id, entity, authorizationManager, validationManager);

        return buildResponse(dbEntity, xContent, true, Response.Status.CREATED);
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
