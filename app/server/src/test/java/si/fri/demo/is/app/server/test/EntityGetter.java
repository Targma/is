package si.fri.demo.is.app.server.test;

import org.junit.Assert;
import si.fri.demo.is.api.ISApi;
import si.fri.demo.is.api.client.utility.QueryParamBuilder;
import si.fri.demo.is.api.data.request.EntityRequest;
import si.fri.demo.is.api.data.response.EntityResponse;
import si.fri.demo.is.api.data.response.PagingResponse;
import si.fri.demo.is.api.exception.ISApiException;
import si.fri.demo.is.app.server.test.resource.AddressResourceTest;
import si.fri.demo.is.app.server.test.resource.ProductResourceTest;
import si.fri.demo.is.core.jpa.entities.Address;
import si.fri.demo.is.core.jpa.entities.Customer;
import si.fri.demo.is.core.jpa.entities.Product;

import java.util.ArrayList;
import java.util.List;

public class EntityGetter {

    private ISApi api;

    public EntityGetter(ISApi api) {
        this.api = api;
    }

    public Customer getCustomer(){
        try {
            EntityResponse<Customer> entityData = api.customer.login();
            Assert.assertTrue(entityData.isStatusValid());
            return entityData.getItem();
        } catch (ISApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Address getAddress(){
        Customer customer = getCustomer();
        if(customer != null){
            try {
                QueryParamBuilder queryParamBuilder = new QueryParamBuilder();
                queryParamBuilder.addEqCond("customer.originId", String.valueOf(customer.getOriginId()));

                PagingResponse<Address> entityData = api.address.get(queryParamBuilder.buildQuery());

                Assert.assertTrue(entityData.isStatusValid());
                if(entityData.getCount() > 0){
                    return entityData.getItems().get(0);
                } else {
                    EntityRequest<Address> request = new EntityRequest<Address>(AddressResourceTest.buildAddress(), true);
                    EntityResponse<Address> addressEntityData = api.address.post(request);
                    return addressEntityData.getItem();
                }
            } catch (ISApiException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public List<Product> getProduct() {
        try {
            QueryParamBuilder queryParamBuilder = new QueryParamBuilder();
            PagingResponse<Product> entityData = api.product.get(queryParamBuilder.buildQuery());

            Assert.assertTrue(entityData.isStatusValid());
            if(entityData.getCount() > 0) {
                return entityData.getItems();
            } else {
                EntityRequest<Product> request = new EntityRequest<Product>(ProductResourceTest.buildProduct(), true);
                EntityResponse<Product> productEntityData = api.product.post(request);
                List<Product> productList = new ArrayList<Product>();
                productList.add(productEntityData.getItem());
                return productList;
            }
        } catch (ISApiException e) {
            e.printStackTrace();
        }
        return null;
    }
}
