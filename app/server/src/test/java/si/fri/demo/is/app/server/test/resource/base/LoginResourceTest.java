package si.fri.demo.is.app.server.test.resource.base;

import org.junit.Assert;
import si.fri.demo.is.api.data.response.EntityResponse;
import si.fri.demo.is.api.exception.ISApiException;
import si.fri.demo.is.api.resource.base.ISCrudResource;
import si.fri.demo.is.api.resource.expand.ISLoginCrudResource;
import si.fri.demo.is.core.jpa.entities.base.BaseEntity;

public abstract class LoginResourceTest<T extends BaseEntity, B extends ISCrudResource<T>> extends CrudResourceTest<T, B> {

    protected static int NAME_STR_LEN = 10;

    @Override
    public EntityResponse<T> testPost(T entity) throws ISApiException, IllegalAccessException {
        return testLogin();
    }

    public EntityResponse<T> testLogin() throws ISApiException {

        EntityResponse<T> entityData = ((ISLoginCrudResource) resource).login();

        Assert.assertTrue(entityData.isStatusValid());

        return entityData;
    }

    protected String getMaxLength(String string, int maxLen){
        if(string != null && string.length() > maxLen){
            return string.substring(0, maxLen - 1);
        }
        return string;
    }

}
