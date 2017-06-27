package si.fri.demo.is.core.businessLogic.managers;

import si.fri.demo.is.core.businessLogic.calculation.Calculation;
import si.fri.demo.is.core.businessLogic.database.AuthorizationManager;
import si.fri.demo.is.core.businessLogic.database.Database;
import si.fri.demo.is.core.businessLogic.exceptions.BusinessLogicTransactionException;
import si.fri.demo.is.core.businessLogic.managers.base.BaseManager;
import si.fri.demo.is.core.businessLogic.validators.ISValidators;
import si.fri.demo.is.core.jpa.entities.*;

import javax.ws.rs.core.Response;
import java.util.HashSet;

public class OrderManager extends BaseManager<Order>  {

    private AuthorizationManager<ProductOnOrder> productOnOrderAuthorizationManager;

    public OrderManager(Database database) {
        super(database, null);
    }

    public Order processOrder(Order order) throws BusinessLogicTransactionException {
        try {
            validateOrder(order);
            Order dbOrder = persistOrder(order);

            return dbOrder;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new BusinessLogicTransactionException(Response.Status.INTERNAL_SERVER_ERROR, "Error validating request.");
        }
    }

    private void validateOrder(Order order) throws IllegalAccessException, BusinessLogicTransactionException {

        Customer dbCustomer = ISValidators.isEntityValid(order.getCustomer(), false, database);
        order.setCustomer(dbCustomer);

        Address dbAddress =  ISValidators.isEntityValid(order.getAddress(), false, database);
        order.setAddress(dbAddress);

        if(!dbAddress.getCustomer().getOriginId().equals(dbCustomer.getOriginId())) {
            throw new BusinessLogicTransactionException(Response.Status.BAD_REQUEST, "Address does not exist.");
        }

        HashSet<Short> orderNumbers = new HashSet<>();
        for(ProductOnOrder item : order.getProductOnOrders()) {
            orderNumbers.add(item.getOrderNumber());

            ISValidators.isBigDecimalValid(item.getQuantity(), false, null, Calculation.BD_1, "Quantity");

            Product dbProduct = ISValidators.isEntityValid(item.getProduct(), false, database);
            item.setProduct(dbProduct);
        }

        for(Short i=0; i<orderNumbers.size(); i++){
            if(!orderNumbers.contains(i)){
                throw new BusinessLogicTransactionException(Response.Status.BAD_REQUEST, "Invalid order numbers.");
            }
        }
    }

    private Order persistOrder(Order order) throws BusinessLogicTransactionException, IllegalAccessException {

        Order dbOrder = (Order) order.cloneObject();
        dbOrder.setPrice(Calculation.getTotalAmount(order));

        database.create(dbOrder, authorizationManager);

        HashSet<ProductOnOrder> productOnOrderSet = new HashSet<>();
        for(ProductOnOrder item : order.getProductOnOrders()){
            ProductOnOrder dbItem = (ProductOnOrder) item.cloneObject();
            dbItem.setOrder(dbOrder);
            database.create(dbItem, productOnOrderAuthorizationManager);

            productOnOrderSet.add(dbItem);
        }

        dbOrder.setProductOnOrders(productOnOrderSet);
        return dbOrder;
    }

}
