package si.fri.demo.is.app.server.test.resource;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.runner.RunWith;
import si.fri.demo.is.api.resource.expand.ISLoginCrudResource;
import si.fri.demo.is.app.server.test.resource.base.LoginResourceTest;
import si.fri.demo.is.core.jpa.entities.Customer;

@RunWith(Arquillian.class)
public class CustomerResourceTest extends LoginResourceTest<Customer, ISLoginCrudResource<Customer>> {

    @Override
    protected Customer buildCreateEntity() {
        return null;
    }

    @Override
    protected Customer buildPutEntity(Customer dbCustomer) {
        Customer customer = (Customer) dbCustomer.cloneObject();
        customer.setName(getMaxLength(dbCustomer.getName(), NAME_STR_LEN) + " PUT " + (int)(Math.random() * 100));
        return customer;
    }

    @Override
    protected Customer buildPatchEntity(Customer dbCustomer) {
        Customer customer = new Customer();
        customer.setId(dbCustomer.getId());
        customer.setName(getMaxLength(dbCustomer.getName(), NAME_STR_LEN) + " PATCH" + (int)(Math.random() * 100));
        return customer;
    }

    @Override
    protected ISLoginCrudResource getResource() {
        return api.customer;
    }

}
