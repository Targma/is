package si.fri.demo.is.app.server.ejb.interfaces;

import si.fri.demo.is.core.businessLogic.authentication.AuthEntity;
import si.fri.demo.is.core.businessLogic.exceptions.BusinessLogicTransactionException;
import si.fri.demo.is.core.jpa.entities.Order;

public interface OrderServiceLocal {

    Order process(AuthEntity authEntity, Order order) throws BusinessLogicTransactionException;

}
