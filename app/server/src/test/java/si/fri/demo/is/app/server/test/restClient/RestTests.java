package si.fri.demo.is.app.server.test.restClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import si.fri.demo.is.api.ISApi;
import si.fri.demo.is.api.ISApiConfiguration;
import si.fri.demo.is.api.client.authorization.provider.KeycloakISAuthProvider;
import si.fri.demo.is.api.client.authorization.provider.base.KeycloakISConfiguration;
import si.fri.demo.is.api.client.utility.QueryParamBuilder;
import si.fri.demo.is.api.data.PagingData;
import si.fri.demo.is.app.server.test.base.BaseTest;
import si.fri.demo.is.core.jpa.entities.Product;

import java.io.File;
import java.math.BigDecimal;
import java.util.logging.Logger;

@RunWith(Arquillian.class)
public class RestTests extends BaseTest {

    private static final Logger LOG = Logger.getLogger(RestTests.class.getSimpleName());

    @Deployment(testable = false)
    public static EnterpriseArchive deployment() {
        return createDeployment();
    }

    @Test
    public void testRest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        ISApiConfiguration configuration = new ISApiConfiguration(deploymentUrl.toString() + "api/v1");
        KeycloakISConfiguration authConfiguration = mapper.readValue(new File("./keycloakConfig.json"), KeycloakISConfiguration.class);

        ISApi api = new ISApi(configuration, new KeycloakISAuthProvider(authConfiguration));

        QueryParamBuilder queryParamBuilder = new QueryParamBuilder();
        PagingData<Product> productPagingData = api.product.get(queryParamBuilder.buildQuery());

        Product product = new Product();
        product.setPrice(new BigDecimal("2.0"));
        product.setTitle("Test product");

        assert productPagingData.isStatusValid();
    }

}
