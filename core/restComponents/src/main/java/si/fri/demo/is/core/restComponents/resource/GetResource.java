package si.fri.demo.is.core.restComponents.resource;

import com.github.tfaga.lynx.beans.QueryParameters;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import si.fri.demo.is.core.businessLogic.database.AuthorizationManager;
import si.fri.demo.is.core.businessLogic.database.ValidationManager;
import si.fri.demo.is.core.businessLogic.dto.Paging;
import si.fri.demo.is.core.businessLogic.exceptions.BusinessLogicTransactionException;
import si.fri.demo.is.core.jpa.entities.base.BaseEntity;

import javax.annotation.PostConstruct;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

public abstract class GetResource<T extends BaseEntity> extends BaseResource {

    protected int defaultMaxLimit = 50;

    protected Class<T> type;

    protected AuthorizationManager<T> authorizationManager = null;
    protected ValidationManager<T> validationManager = null;
    protected void initManagers() {}

    public GetResource(Class<T> type) {
        this.type = type;
    }

    @PostConstruct
    private void init(){
        initManagers();
    }


    @ApiImplicitParams({
            @ApiImplicitParam(name = "limit", type = "integer", paramType = "query", format = "int32", defaultValue = "50", dataTypeClass = Integer.class, value = "Limit the amount of returned entities."),
            @ApiImplicitParam(name = "skip", type = "integer", paramType = "query", format = "int32", defaultValue = "0", dataTypeClass = Integer.class, value = "Skip amount of entities."),
            @ApiImplicitParam(name = "orderby", type = "array", paramType = "query", dataTypeClass = String.class, collectionFormat = "csv", value = "Order entities by properties.", example = "orderby={property1} {ASC, DESC},{property2} {ASC, DESC}"),
            @ApiImplicitParam(name = "where", type = "string", paramType = "query", dataTypeClass = String.class, value = "Filter entities by properties.", example = "where={property}:{comperator (eq, gt, gte, lt, lte, like, likeic)}:{value},{property}:{isnull, isnotnull}"),
            @ApiImplicitParam(name = "select", type = "array",  paramType = "query", dataTypeClass = String.class, collectionFormat = "csv", value = "Specify which property of entity you want returned.", example = "select={property1},{property2}")
    })
    @GET
    public Response getList() throws BusinessLogicTransactionException {
        QueryParameters param = QueryParameters.query(uriInfo.getRequestUri().getQuery())
                .maxLimit(defaultMaxLimit).defaultLimit(defaultMaxLimit).defaultOffset(0).build();

        Paging<T> paging = getDatabaseService().get(type, param, authorizationManager);

        return buildResponse(paging);
    }


    @GET
    @Path("{id}")
    public Response get(@PathParam("id") Integer id) throws BusinessLogicTransactionException {

        T dbEntity = getDatabaseService().get(type, id, authorizationManager);

        return buildResponse(dbEntity, true, false);
    }


    protected Response buildResponse(T dbEntity) {
        return buildResponse(dbEntity, false, false, null);
    }

    protected Response buildResponse(T dbEntity, Boolean isContentReturned) {
        return buildResponse(dbEntity, isContentReturned, false, null);
    }

    protected Response buildResponse(T dbEntity, Boolean isContentReturned, boolean locationHeader) {
        return buildResponse(dbEntity, isContentReturned, locationHeader, null);
    }

    protected Response buildResponse(T dbEntity, Boolean isContentReturned, boolean locationHeader, Response.Status status) {

        Response.ResponseBuilder responseBuilder = Response.status(Response.Status.NO_CONTENT);

        if(isContentReturned != null && isContentReturned){
            responseBuilder.entity(dbEntity);
            responseBuilder.status(Response.Status.OK);
        }

        if(status != null){
            responseBuilder.status(status);
        }

        if(locationHeader){
            String locationValue = type.getSimpleName() + "/" + dbEntity.getId();
            responseBuilder.header("Location", locationValue);
        }

        return responseBuilder.build();
    }

    protected Response buildResponse(Paging<T> paging){

        Response.ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
        responseBuilder.header("X-Count", paging.getCount());
        responseBuilder.entity(paging.getItems());

        return responseBuilder.build();
    }

    public AuthorizationManager<T> getAuthorizationManager() {
        return authorizationManager;
    }

    public ValidationManager<T> getValidationManager() {
        return validationManager;
    }
}
