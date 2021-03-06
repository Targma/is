package si.fri.demo.is.core.restComponents.resource;

import si.fri.demo.is.core.businessLogic.exceptions.BusinessLogicTransactionException;
import si.fri.demo.is.core.jpa.entities.base.BaseEntity;
import si.fri.demo.is.core.restComponents.providers.configuration.PATCH;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;


public abstract class CrudResource<T extends BaseEntity> extends GetResource<T> {


    public CrudResource(Class<T> type) {
        super(type);
    }

    @POST
    public Response create(@HeaderParam("X-Content") Boolean xContent, T entity) throws BusinessLogicTransactionException {
        entity.setId(null);

        T dbEntity = getDatabaseService().create(entity, authorizationManager, validationManager);

        return buildResponse(dbEntity, xContent, false, Response.Status.CREATED).build();
    }

    @PUT
    @Path("{id}")
    public Response update(@HeaderParam("X-Content") Boolean xContent,
                           @PathParam("id") Integer id, T newObject) throws BusinessLogicTransactionException {
        newObject.setId(id);

        T dbEntity = getDatabaseService().update(newObject, authorizationManager, validationManager);

        return buildResponse(dbEntity, xContent).build();
    }

    @PATCH
    @Path("{id}")
    public Response patch(@HeaderParam("X-Content") Boolean xContent,
                          @PathParam("id") Integer id, T entity) throws BusinessLogicTransactionException {
        entity.setId(id);

        T dbEntity = getDatabaseService().patch(entity, authorizationManager, validationManager);

        return buildResponse(dbEntity, xContent).build();
    }

    @DELETE
    @Path("{id}")
    public Response delete(@HeaderParam("X-Content") Boolean xContent,
                           @PathParam("id") Integer id) throws BusinessLogicTransactionException {

        T dbEntity = getDatabaseService().delete(type, id, authorizationManager, validationManager);

        return buildResponse(dbEntity, xContent).build();
    }

    @PUT
    @Path("{id}/toggleIsDeleted")
    public Response toggleIsDeleted(@HeaderParam("X-Content") Boolean xContent,
                                    @PathParam("id") Integer id) throws BusinessLogicTransactionException {

        T dbEntity = getDatabaseService().toggleIsDeleted(type, id, authorizationManager, validationManager);

        return buildResponse(dbEntity, xContent).build();
    }

}
