package si.fri.demo.is.app.microservice.rest;

import io.swagger.annotations.Contact;
import io.swagger.annotations.Info;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.jaxrs.config.BeanConfig;
import si.fri.demo.is.app.microservice.rest.providers.JSONConfiguration;
import si.fri.demo.is.app.microservice.rest.resources.ProductResource;
import si.fri.demo.is.core.restComponents.providers.configuration.CORSConfiguration;
import si.fri.demo.is.core.restComponents.providers.exceptions.BusinessLogicOperationExceptionMapper;
import si.fri.demo.is.core.restComponents.providers.exceptions.BusinessLogicTransactionExceptionMapper;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@SwaggerDefinition(
        info = @Info(
                description = "Demo project to demonstrate REST integration as microservice.",
                version = "v1",
                title = "IS Demo",
                contact = @Contact(
                        name = "Jan Gasperlin",
                        email = "jg1724@student.uni-lj.si"
                )
        ),
        consumes = {"application/json"},
        produces = {"application/json"}
)

@ApplicationPath("/api/v1/")
public class ISApplicationV1Micro extends Application {

    public ISApplicationV1Micro() {
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion("v1");
        beanConfig.setSchemes(new String[]{"http"});
        beanConfig.setHost("localhost:8080");
        beanConfig.setBasePath("/api/v1");
        beanConfig.setResourcePackage("si.fri.demo.is.app.microservice.rest.resources");
        beanConfig.setScan(true);
    }

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet();

        resources.add(ProductResource.class);

        resources.add(CORSConfiguration.class);
        resources.add(JSONConfiguration.class);

        resources.add(BusinessLogicOperationExceptionMapper.class);
        resources.add(BusinessLogicTransactionExceptionMapper.class);

        resources.add(io.swagger.jaxrs.listing.ApiListingResource.class);
        resources.add(io.swagger.jaxrs.listing.SwaggerSerializers.class);

        return resources;
    }
}
