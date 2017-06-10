package si.fri.demo.is.api;

import si.fri.demo.is.api.client.ISClient;
import si.fri.demo.is.api.client.authorization.provider.base.ISApiAuthProvider;
import si.fri.demo.is.api.resource.base.ISCrudResource;
import si.fri.demo.is.api.resource.base.ISResource;
import si.fri.demo.is.api.resource.custom.OrderCrudResource;
import si.fri.demo.is.api.resource.expand.LoginCrudResource;
import si.fri.demo.is.core.jpa.entities.Address;
import si.fri.demo.is.core.jpa.entities.Customer;
import si.fri.demo.is.core.jpa.entities.Product;
import si.fri.demo.is.core.jpa.entities.User;

public class ISApi {

    private ISApiConfiguration configuration;
    private ISClient client;
    private ISApiAuthProvider provider;

    public ISResource<Address> address;
    public LoginCrudResource<Customer> customer;
    public OrderCrudResource order;
    public ISResource<Product> product;
    public LoginCrudResource<User> user;

    public ISApi(ISApiConfiguration configuration) {
        this(configuration, null);
    }

    public ISApi(ISApiConfiguration configuration, ISApiAuthProvider provider) {
        this.configuration = configuration;
        this.provider = provider;
        this.client = new ISClient(configuration, provider);

        initResources();
    }

    private void initResources(){
        address = new ISCrudResource<>(client, Address.class);
        customer = new LoginCrudResource(client, Customer.class);
        order = new OrderCrudResource(client);
        product = new ISCrudResource<>(client, Product.class);
        user = new LoginCrudResource<>(client, User.class);
    }
}
