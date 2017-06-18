package si.fri.demo.is.app.microservice.rest;

import si.fri.demo.is.core.businessLogic.authentication.AuthEntity;
import si.fri.demo.is.core.businessLogic.database.Database;
import si.fri.demo.is.core.businessLogic.database.DatabaseImpl;
import si.fri.demo.is.core.jpa.entities.Product;
import si.fri.demo.is.core.restComponents.resource.GetResource;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


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

}
