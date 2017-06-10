package si.fri.demo.is.core.businessLogic.managers;

import si.fri.demo.is.core.businessLogic.database.Database;
import si.fri.demo.is.core.businessLogic.managers.base.BaseManager;
import si.fri.demo.is.core.jpa.entities.Order;

public class OrderManager extends BaseManager<Order>  {

    public OrderManager(Database database) {
        super(database);
    }

    public Order processOrder(Order order){


        return order;
    }

    private void validateOrderItems(Order order) {



    }

    private void persistOrder(Order order) {



    }

}
