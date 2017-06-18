package si.fri.demo.is.app.microservice.rest.resources;

import com.github.tfaga.lynx.beans.QueryParameters;
import si.fri.demo.is.core.businessLogic.database.Database;
import si.fri.demo.is.core.businessLogic.dto.Paging;
import si.fri.demo.is.core.businessLogic.exceptions.BusinessLogicTransactionException;
import si.fri.demo.is.core.jpa.entities.Product;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;


@Path("Product")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductResource {

    @Context
    protected UriInfo uriInfo;

    @PersistenceContext(unitName = "is-micro-jpa")
    private EntityManager em;

    private Database database;

    @PostConstruct
    private void init(){
        this.database = new Database(em);
    }

    @GET
    public Response getList() throws BusinessLogicTransactionException {
        QueryParameters param = QueryParameters.query(uriInfo.getRequestUri().getQuery())
                .maxLimit(50).defaultLimit(50).defaultOffset(0).build();

        Paging<Product> paging = database.get(Product.class, param);

        return buildResponse(paging);
    }


    @GET
    @Path("{id}")
    public Response get(@PathParam("id") Integer id) throws BusinessLogicTransactionException {

        Product dbEntity = database.get(Product.class, id);

        return Response.ok(dbEntity).build();
    }

    protected Response buildResponse(Paging<Product> paging){

        Response.ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
        responseBuilder.header("X-Count", paging.getCount());
        responseBuilder.entity(paging.getItems());

        return responseBuilder.build();
    }
}
