package si.fri.demo.is.app.server.test.resource;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.runner.RunWith;
import si.fri.demo.is.api.resource.expand.ISLoginCrudResource;
import si.fri.demo.is.app.server.test.resource.base.LoginResourceTest;
import si.fri.demo.is.core.jpa.entities.User;

@RunWith(Arquillian.class)
public class UserResourceTest extends LoginResourceTest<User, ISLoginCrudResource<User>> {

    @Override
    protected  ISLoginCrudResource<User> getResource() {
        return api.user;
    }

    @Override
    protected User buildCreateEntity() {
        return null;
    }

    @Override
    protected User buildPutEntity(User dbUser) {
        User user = (User) dbUser.cloneObject();
        user.setName(getMaxLength(dbUser.getName(), NAME_STR_LEN) +" PUT");
        return user;
    }

    @Override
    protected User buildPatchEntity(User dbUser) {
        User user = new User();
        user.setId(dbUser.getId());
        user.setName(getMaxLength(dbUser.getName(), NAME_STR_LEN) + " PATCH");
        return user;
    }

}
