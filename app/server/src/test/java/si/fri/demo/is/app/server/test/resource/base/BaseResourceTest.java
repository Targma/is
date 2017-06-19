package si.fri.demo.is.app.server.test.resource.base;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import si.fri.demo.is.api.ISApi;
import si.fri.demo.is.api.ISApiConfiguration;
import si.fri.demo.is.api.client.authorization.provider.KeycloakISAuthProvider;
import si.fri.demo.is.api.client.authorization.provider.base.KeycloakISConfiguration;
import si.fri.demo.is.api.data.response.EntityResponse;
import si.fri.demo.is.api.exception.ISApiException;
import si.fri.demo.is.api.resource.base.ISResource;
import si.fri.demo.is.app.server.test.ISDeployment;
import si.fri.demo.is.core.jpa.entities.Customer;
import si.fri.demo.is.core.jpa.entities.User;
import si.fri.demo.is.core.jpa.entities.base.BaseEntity;

import java.io.File;
import java.io.IOException;

public abstract class BaseResourceTest<T extends BaseEntity, B extends ISResource<T>> extends ISDeployment {

    protected ISApi api;
    protected B resource;

    @Before
    public void initObjects() throws IOException {
        ISApiConfiguration configuration = new ISApiConfiguration(deploymentUrl.toString() + "api/v1");
        KeycloakISConfiguration authConfiguration = mapper.readValue(new File("src/test/resources/keycloakConfig.json"), KeycloakISConfiguration.class);

        api = new ISApi(configuration, new KeycloakISAuthProvider(authConfiguration));
        api.getCore().setDefaultCoreContentHeader(true);

        resource = getResource();
    }

    protected abstract B getResource();

    @Test
    public void isInitValid(){
        Assert.assertNotNull(api.getCore().getConfiguration());
        Assert.assertNotNull(api.getCore().getApiAuthProvider());
        Assert.assertNotNull(resource);
    }

    @Test
    public void testResource() throws ISApiException, IllegalAccessException {
        initUserAndCustomer();
    }

    protected void initUserAndCustomer(){
        try {
            EntityResponse<User> userEntityData = api.user.login();
            Assert.assertTrue(userEntityData.isStatusValid());

            EntityResponse<Customer> customerEntityData = api.customer.login();
            Assert.assertTrue(customerEntityData.isStatusValid());

        } catch (ISApiException e) {
            e.printStackTrace();
        }
    }
}
