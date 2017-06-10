package si.fri.demo.is.app.server.ejb.interfaces;

import si.fri.demo.is.core.businessLogic.exceptions.BusinessLogicTransactionException;
import si.fri.demo.is.core.jpa.entities.Order;

public interface OrderServiceLocal {

    Order process(Order order) throws BusinessLogicTransactionException;

}
