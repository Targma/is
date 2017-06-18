package si.fri.demo.is.app.server.rest.resources;

import io.swagger.annotations.*;
import io.swagger.jaxrs.config.BeanConfig;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@SwaggerDefinition(
        info = @Info(
                description = "Demo project to demonstrate REST integration.",
                version = "v1",
                title = "IS Demo",
                contact = @Contact(
                        name = "Jan Gasperlin",
                        email = "jg1724@student.uni-lj.si"
                )
        ),
        //basePath = "/api/v1",
        //host = "localhost:8080",
        consumes = {"application/json"},
        produces = {"application/json"},
        //schemes = {SwaggerDefinition.Scheme.HTTP},
        securityDefinition = @SecurityDefinition (
                oAuth2Definitions = @OAuth2Definition (
                        authorizationUrl = "http://localhost:8081/auth/realms/is/protocol/openid-connect/auth",
                        flow = OAuth2Definition.Flow.IMPLICIT,
                        scopes = {
                                @Scope(name = "CUSTOMER", description = "Customer role."),
                                @Scope(name = "ADMINISTRATOR", description = "Administrator role.")
                        },
                        description = "Keycloak authentication using OAUTH 2.0 .",
                        key = "o2auth"
                )
        )
)
@ApplicationPath("/api/v1")
public class SIApplicationV1 extends Application {

    public SIApplicationV1() {
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion("v1");
        beanConfig.setSchemes(new String[]{"http"});
        beanConfig.setHost("localhost:8080");
        beanConfig.setBasePath("/api/v1");
        beanConfig.setResourcePackage("si.fri.demo.is.app.server.rest.resources");
        beanConfig.setScan(true);
    }
}
