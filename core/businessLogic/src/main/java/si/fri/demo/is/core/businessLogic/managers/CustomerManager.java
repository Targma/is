package si.fri.demo.is.core.businessLogic.managers;

import si.fri.demo.is.core.businessLogic.authentication.base.AuthEntity;
import si.fri.demo.is.core.businessLogic.database.Database;
import si.fri.demo.is.core.businessLogic.exceptions.BusinessLogicTransactionException;
import si.fri.demo.is.core.businessLogic.managers.base.BaseManager;
import si.fri.demo.is.core.jpa.entities.Customer;

import javax.ws.rs.core.Response;
import java.util.List;

public class CustomerManager extends BaseManager<Customer> {

    public CustomerManager(Database database) {
        super(database);
    }

    public Customer get(AuthEntity authEntity) throws BusinessLogicTransactionException {
        if(authEntity.getAccountType() == AuthEntity.AccountType.CUSTOMER){
            final String authId = authEntity.getId();
            List<Customer> customerList = database.getStream(Customer.class)
                    .where(e -> e.getAuthenticationId().equals(authId))
                    .toList();

            Customer customer;
            if(customerList.isEmpty()){
                customer = generate(authEntity);
                database.create(customer);
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

        database.create(customer);

        return customer;
    }

}
