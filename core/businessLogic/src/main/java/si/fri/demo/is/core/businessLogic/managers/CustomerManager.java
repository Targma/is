package si.fri.demo.is.core.businessLogic.managers;

import si.fri.demo.is.core.businessLogic.authentication.AuthEntity;
import si.fri.demo.is.core.businessLogic.database.Database;
import si.fri.demo.is.core.businessLogic.exceptions.BusinessLogicTransactionException;
import si.fri.demo.is.core.businessLogic.managers.base.BaseManager;
import si.fri.demo.is.core.jpa.entities.Customer;

import javax.ws.rs.core.Response;
import java.util.List;

import static si.fri.demo.is.core.businessLogic.authentication.AuthEntity.ROLE_CUSTOMER;

public class CustomerManager extends BaseManager<Customer> {

    public CustomerManager(Database database) {
        super(database, null);
    }

    public Customer get(AuthEntity authEntity) throws BusinessLogicTransactionException {
        if(authEntity.isInRole(ROLE_CUSTOMER)){
            final String authId = authEntity.getId();
            List<Customer> customerList = database.getStream(Customer.class)
                    .where(e -> e.getAuthenticationId().equals(authId) && e.getIsLatest() && !e.getIsDeleted())
                    .toList();

            Customer customer;
            if(customerList.isEmpty()){
                customer = generate(authEntity);
                database.createVersion(customer, authorizationManager);
            } else {
                customer = customerList.get(0);
            }

            return customer;
        } else {
            throw new BusinessLogicTransactionException(Response.Status.BAD_REQUEST, "Account is not of customer type.");
        }
    }

    private Customer generate(AuthEntity authEntity) throws BusinessLogicTransactionException {
        Customer customer = new Customer();
        customer.setAuthenticationId(authEntity.getId());
        customer.setEmail(authEntity.getEmail());
        customer.setName(authEntity.getName());
        customer.setSurname(authEntity.getSurname());

        return customer;
    }

}
