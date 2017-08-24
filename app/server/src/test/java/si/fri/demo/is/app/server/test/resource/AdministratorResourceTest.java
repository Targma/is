package si.fri.demo.is.app.server.test.resource;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.runner.RunWith;
import si.fri.demo.is.api.resource.expand.ISLoginCrudResource;
import si.fri.demo.is.app.server.test.resource.base.LoginResourceTest;
import si.fri.demo.is.core.jpa.entities.Administrator;

@RunWith(Arquillian.class)
public class AdministratorResourceTest extends LoginResourceTest<Administrator, ISLoginCrudResource<Administrator>> {

    @Override
    protected  ISLoginCrudResource<Administrator> getResource() {
        return api.administrator;
    }

    @Override
    protected Administrator buildCreateEntity() {
        return null;
    }

    @Override
    protected Administrator buildPutEntity(Administrator dbAdministrator) {
        Administrator administrator = (Administrator) dbAdministrator.cloneObject();
        administrator.setName(getMaxLength(dbAdministrator.getName(), NAME_STR_LEN) +" PUT"  + (int)(Math.random() * 100));
        return administrator;
    }

    @Override
    protected Administrator buildPatchEntity(Administrator dbAdministrator) {
        Administrator administrator = new Administrator();
        administrator.setId(dbAdministrator.getId());
        administrator.setName(getMaxLength(dbAdministrator.getName(), NAME_STR_LEN) + " PATCH"  + (int)(Math.random() * 100));
        return administrator;
    }

}
