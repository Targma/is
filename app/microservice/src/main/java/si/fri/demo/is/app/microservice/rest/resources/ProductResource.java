package si.fri.demo.is.app.microservice.rest.resources;

import io.swagger.annotations.*;
import si.fri.demo.is.core.businessLogic.authentication.AuthEntity;
import si.fri.demo.is.core.businessLogic.database.Database;
import si.fri.demo.is.core.businessLogic.database.DatabaseImpl;
import si.fri.demo.is.core.businessLogic.exceptions.BusinessLogicTransactionException;
import si.fri.demo.is.core.jpa.entities.Product;
import si.fri.demo.is.core.restComponents.providers.exceptions.ApiException;
import si.fri.demo.is.core.restComponents.resource.GetResource;
import si.fri.demo.is.core.restComponents.utility.SwaggerConstants;

import javax.annotation.PostConstruct;
import javax.annotation.security.PermitAll;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Api(value = "Product")
@Path("Product")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductResource extends GetResource<Product> {

    @PersistenceContext(unitName = "is-micro-jpa")
    private EntityManager em;

    private Database database;

    @Override
    protected DatabaseImpl getDatabaseService() {
        return database;
    }

    @Override
    protected AuthEntity getAuthorizedEntity() {
        return null;
    }


    public ProductResource() {
        super(Product.class);
    }


    @PostConstruct
    private void init(){
        this.database = new Database(em);
    }


    @ApiOperation(
            value = SwaggerConstants.GET_LIST_VALUE + "products.",
            notes = SwaggerConstants.GET_LIST_NOTE)
    @ApiResponses({
            @ApiResponse(
                    code = HttpServletResponse.SC_OK,
                    message = SwaggerConstants.STATUS_OK_DESC,
                    response = Product.class,
                    responseContainer = SwaggerConstants.GET_LIST_CONTAINER,
                    responseHeaders = @ResponseHeader(
                            name = SwaggerConstants.GET_LIST_HEAD_COUNT,
                            description = SwaggerConstants.GET_LIST_HEAD_COUNT_DESC,
                            response = Integer.class
                    )
            ),
            @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = SwaggerConstants.STATUS_BAD_REQUEST_DESC, response = ApiException.class),
            @ApiResponse(code = HttpServletResponse.SC_FORBIDDEN, message = SwaggerConstants.STATUS_FORBIDDEN_DESC)
    })
    @PermitAll
    @GET
    @Override
    public Response getList() throws BusinessLogicTransactionException {
        return super.getList();
    }


    @ApiOperation(
            value = SwaggerConstants.GET_VALUE + "products.",
            notes = SwaggerConstants.GET_NOTE)
    @ApiResponses({
            @ApiResponse(code = HttpServletResponse.SC_OK, message = SwaggerConstants.STATUS_OK_DESC, response = Product.class),
            @ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = SwaggerConstants.STATUS_NOT_FOUND_DESC, response = ApiException.class),
            @ApiResponse(code = HttpServletResponse.SC_FORBIDDEN, message = SwaggerConstants.STATUS_FORBIDDEN_DESC)
    })
    @PermitAll
    @GET
    @Path("{id}")
    @Override
    public Response get(@PathParam("id") Integer id) throws BusinessLogicTransactionException {
        return super.get(id);
    }
}
