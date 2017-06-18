package si.fri.demo.is.app.server.test.resource;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.runner.RunWith;
import si.fri.demo.is.api.resource.base.ISCrudResource;
import si.fri.demo.is.app.server.test.resource.base.CrudResourceTest;
import si.fri.demo.is.core.jpa.entities.Product;

import java.math.BigDecimal;

@RunWith(Arquillian.class)
public class ProductResourceTest extends CrudResourceTest<Product, ISCrudResource<Product>> {

    @Override
    protected ISCrudResource<Product> getResource() {
        return api.product;
    }

    public static Product buildProduct(){
        Product product = new Product();
        product.setTitle("Test POST");
        product.setDescription("Test desc POST");
        product.setPrice(new BigDecimal("11.4"));
        product.setDiscount(new BigDecimal("5"));
        return product;
    }

    @Override
    protected Product buildCreateEntity() {
        return buildProduct();
    }

    @Override
    protected Product buildPutEntity(Product dbProduct) {
        Product product = (Product) dbProduct.cloneObject();
        product.setTitle(dbProduct.getTitle() + " PUT");
        product.setDescription(dbProduct.getDescription() + " PUT");
        product.setPrice(dbProduct.getPrice().add(new BigDecimal("2")));
        return product;
    }

    @Override
    protected Product buildPatchEntity(Product dbProduct) {
        Product product = new Product();
        product.setId(dbProduct.getId());
        product.setDescription(dbProduct.getDescription() + " Patch");
        return product;
    }

}
