package si.fri.demo.is.app.server.ejb;

import si.fri.demo.is.app.server.ejb.base.BaseService;
import si.fri.demo.is.app.server.ejb.interfaces.CustomerServiceLocal;
import si.fri.demo.is.core.businessLogic.authentication.AuthEntity;
import si.fri.demo.is.core.businessLogic.exceptions.BusinessLogicTransactionException;
import si.fri.demo.is.core.businessLogic.managers.CustomerManager;
import si.fri.demo.is.core.jpa.entities.Customer;

import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Local;
import javax.ejb.Stateless;

@RolesAllowed(AuthEntity.ROLE_CUSTOMER)
@Stateless
@Local(CustomerServiceLocal.class)
public class CustomerService extends BaseService implements CustomerServiceLocal {

    protected CustomerManager customerManager;

    @PostConstruct
    protected void init() {
        customerManager = new CustomerManager(databaseService.getDatabase());
    }

    @Override
    public Customer get(AuthEntity authEntity) throws BusinessLogicTransactionException {
        return customerManager.get(authEntity);
    }
}
