package si.fri.demo.is.app.server.ejb.interfaces;

import si.fri.demo.is.core.businessLogic.authentication.AuthEntity;
import si.fri.demo.is.core.businessLogic.exceptions.BusinessLogicTransactionException;
import si.fri.demo.is.core.jpa.entities.Customer;

public interface CustomerServiceLocal {

    Customer get(AuthEntity authEntity) throws BusinessLogicTransactionException;

}
