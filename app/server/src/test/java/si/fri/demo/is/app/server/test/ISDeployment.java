package si.fri.demo.is.app.server.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.exporter.ZipExporter;
import org.jboss.shrinkwrap.api.importer.ZipImporter;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;

public abstract class ISDeployment {

    public static final String EAR_BUILD_PATH = System.getProperty("ear.path");
    public static final String TEST_EAR_NAME = "test-" + Paths.get(EAR_BUILD_PATH).getFileName().toString();
    public static final String CONTEXT_ROOT = System.getProperty("deployment.contextRoot");
    public static final String ARQUILLIAN_BUILD_PATH = "./build/arquillian/";

    public static final ObjectMapper mapper = new ObjectMapper();

    @ArquillianResource
    protected URL deploymentUrl;

    @Deployment(testable = false)
    public static EnterpriseArchive deployment() {
        return createDeployment();
    }

    public static EnterpriseArchive createDeployment() {
        EnterpriseArchive ear = ShrinkWrap
                .create(ZipImporter.class, TEST_EAR_NAME)
                .importFrom(new File(EAR_BUILD_PATH))
                .as(EnterpriseArchive.class);


        JavaArchive jpaJar = ear.getAsType(JavaArchive.class, "/APP-INF/lib/jpa-1.0-SNAPSHOT.jar");

        jpaJar.delete("/META-INF/persistence.xml");
        File persistenceDescriptor = new File("/META-INF/persistence.xml");
        jpaJar.addAsResource(persistenceDescriptor,"/META-INF/persistence.xml");

        File buildPath = new File(ARQUILLIAN_BUILD_PATH);
        if(!buildPath.exists()){
            buildPath.mkdirs();
        }

        /* // Not able to append new persistence file in jar
        ear.as(ExplodedExporter.class).exportExploded(buildPath);
         */

        File earPath = new File(ARQUILLIAN_BUILD_PATH + TEST_EAR_NAME);
        ear.as(ZipExporter.class).exportTo(earPath, true);

        return ear;
    }

}