package si.fri.demo.is.api;

import si.fri.demo.is.api.client.authorization.provider.base.ISApiAuthProvider;
import si.fri.demo.is.api.resource.base.ISCrudResource;
import si.fri.demo.is.api.resource.custom.OrderCrudResource;
import si.fri.demo.is.api.resource.expand.ISLoginCrudResource;
import si.fri.demo.is.core.jpa.entities.Address;
import si.fri.demo.is.core.jpa.entities.Customer;
import si.fri.demo.is.core.jpa.entities.Product;
import si.fri.demo.is.core.jpa.entities.Administrator;

public class ISApi {

    private ISApiCore core;

    public ISCrudResource<Address> address;
    public ISLoginCrudResource<Customer> customer;
    public OrderCrudResource order;
    public ISCrudResource<Product> product;
    public ISLoginCrudResource<Administrator> administrator;

    public ISApi(ISApiConfiguration configuration) {
        this(configuration, null);
    }

    public ISApi(ISApiConfiguration configuration, ISApiAuthProvider provider) {
        core = new ISApiCore(configuration, provider);

        initResources();
    }

    private void initResources(){
        address = new ISCrudResource<>(core, Address.class);
        customer = new ISLoginCrudResource<>(core, Customer.class);
        order = new OrderCrudResource(core);
        product = new ISCrudResource<>(core, Product.class);
        administrator = new ISLoginCrudResource<>(core, Administrator.class);
    }

    public ISApiCore getCore() {
        return core;
    }
}
