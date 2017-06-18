package si.fri.demo.is.app.server.ejb;

import si.fri.demo.is.app.server.ejb.base.BaseService;
import si.fri.demo.is.app.server.ejb.interfaces.OrderServiceLocal;
import si.fri.demo.is.core.businessLogic.authentication.AuthEntity;
import si.fri.demo.is.core.businessLogic.exceptions.BusinessLogicTransactionException;
import si.fri.demo.is.core.businessLogic.managers.OrderManager;
import si.fri.demo.is.core.jpa.entities.Order;

import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Local;
import javax.ejb.Stateless;

@RolesAllowed(AuthEntity.ROLE_CUSTOMER)
@Stateless
@Local(OrderServiceLocal.class)
public class OrderService extends BaseService implements OrderServiceLocal {

    private OrderManager orderManager;

    @PostConstruct
    private void init(){
        orderManager = new OrderManager(databaseService.getDatabase());
    }

    @Override
    public Order process(Order order) throws BusinessLogicTransactionException {
        return orderManager.processOrder(order);
    }

}
