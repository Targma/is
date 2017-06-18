package si.fri.demo.is.app.server.test.resource;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import si.fri.demo.is.api.data.EntityData;
import si.fri.demo.is.api.exception.ISApiException;
import si.fri.demo.is.api.resource.custom.OrderCrudResource;
import si.fri.demo.is.app.server.test.EntityGetter;
import si.fri.demo.is.app.server.test.resource.base.BaseResourceTest;
import si.fri.demo.is.core.businessLogic.calculation.Calculation;
import si.fri.demo.is.core.jpa.entities.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@RunWith(Arquillian.class)
public class OrderResourceTest extends BaseResourceTest<Order, OrderCrudResource> {

    @Override
    protected OrderCrudResource getResource() {
        return api.order;
    }

    private Order buildCreateEntity() {
        EntityGetter entityGetter = new EntityGetter(api);

        Customer customer = entityGetter.getCustomer();
        Assert.assertNotNull(customer);

        Address address = entityGetter.getAddress();
        Assert.assertNotNull(address);

        List<Product> productList = entityGetter.getProduct();
        Assert.assertNotNull(productList);
        Assert.assertTrue(productList.size() > 0);


        Order order = new Order();
        order.setCustomer(customer);
        order.setAddress(address);

        Set<ProductOnOrder> productOnOrderSet = new HashSet<>();
        order.setProductOnOrders(productOnOrderSet);

        Random r = new Random();

        int numOfItems = 1;
        if(productList.size() > 1){
            numOfItems = r.nextInt(productList.size() - 1) + 1;
        }

        for(Short i=0; i<numOfItems; i ++){
            int randProductIndex = r.nextInt(productList.size());
            Product randProduct = productList.get(randProductIndex);
            productList.remove(randProduct);

            ProductOnOrder productOnOrder = new ProductOnOrder();
            productOnOrder.setQuantity(new BigDecimal((int) (Math.random() * 50) + 1));
            productOnOrder.setProduct(randProduct);
            productOnOrder.setOrderNumber(i);
            productOnOrder.setDiscount(randProduct.getDiscount());
            productOnOrderSet.add(productOnOrder);
        }

        return order;
    }

    @Test
    public void testResource() throws ISApiException, IllegalAccessException {

        Order order = buildCreateEntity();

        EntityData<Order> orderEntityData = resource.process(order);
        Assert.assertTrue(orderEntityData.isStatusValid());

        Assert.assertTrue(Calculation.getTotalAmount(order).compareTo(Calculation.getTotalAmount(orderEntityData.getItem())) == 0);

    }
}
