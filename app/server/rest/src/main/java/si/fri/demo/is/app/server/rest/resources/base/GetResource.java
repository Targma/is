package si.fri.demo.is.app.server.rest.resources.base;

import com.github.tfaga.lynx.beans.QueryParameters;
import si.fri.demo.is.core.businessLogic.dto.Paging;
import si.fri.demo.is.core.businessLogic.exceptions.BusinessLogicTransactionException;
import si.fri.demo.is.core.jpa.entities.base.BaseEntity;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

public class GetResource<T extends BaseEntity> extends BaseResource {

    protected void authorizationValidation(QueryParameters queryParameters) throws BusinessLogicTransactionException {}
    protected void authorizationValidation(Integer id) throws BusinessLogicTransactionException {}

    protected void getListValidation(QueryParameters queryParameters) throws BusinessLogicTransactionException { authorizationValidation(queryParameters); }
    protected void getValidation(Integer id) throws BusinessLogicTransactionException { authorizationValidation(id); }

    protected Class<T> type;

    public GetResource(Class<T> type) {
        this.type = type;
    }

    @GET
    public Response getList() throws BusinessLogicTransactionException {
        QueryParameters param = QueryParameters.query(uriInfo.getRequestUri().getQuery())
                .defaultLimit(50).defaultOffset(0).build();

        getListValidation(param);

        Paging<T> paging = databaseService.get(type, param);

        return Response.ok().entity(paging.getItems()).header("X-Count", paging.getCount()).build();
    }

    @GET
    @Path("{id}")
    public Response get(@PathParam("id") Integer id) throws BusinessLogicTransactionException {
        getValidation(id);

        T object = databaseService.get(type, id);

        if (object == null)
            throw new BusinessLogicTransactionException(Response.Status.NOT_FOUND, type.getSimpleName() + " " + id);

        return Response.ok().entity(object).build();
    }

}
