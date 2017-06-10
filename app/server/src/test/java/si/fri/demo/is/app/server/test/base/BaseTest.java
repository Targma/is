package si.fri.demo.is.app.server.test.base;

import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.exporter.ZipExporter;
import org.jboss.shrinkwrap.api.importer.ZipImporter;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;

public abstract class BaseTest {

    public static final String EAR_BUILD_PATH = System.getProperty("ear.path");
    public static final String TEST_EAR_NAME = "test-" + Paths.get(EAR_BUILD_PATH).getFileName().toString();
    public static final String CONTEXT_ROOT = System.getProperty("deployment.contextRoot");

    public static final String ARQUILLIAN_BUILD_PATH = "./build/arquillian/";

    @ArquillianResource
    protected URL deploymentUrl;

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

/*
@Deployment
public static EnterpriseArchive createDeployment() {

    String path = System.getProperty(EAR_PATH);
    File f = new File(path);
    EnterpriseArchive ear = ShrinkWrap.createFromZipFile(EnterpriseArchive.class, f);

    final JavaArchive foobarEjb = ShrinkWrap.create(JavaArchive.class, "foobarEjb.jar");

    foobarEjb.addClasses(
                    MyTest1.class,
                    MyTest2.class);
    ear.addAsModule(foobarEjb);


    final WebArchive war = ShrinkWrap.create(WebArchive.class, "test.war")
            .addAsWebInfResource("WEB-INF/web.xml")
            .addAsWebResource("index.xhtml");

    ear.addAsModule(Testable.archiveToTest(war));

    modifyApplicationXML(ear);
    modifyJBossDeploymentStructure(ear);


    return ear;
}
 */
/*
WebArchive war = ear.getAsType(WebArchive.class, "/mywarname.war");
war.addClass(TestIS.class);
 */