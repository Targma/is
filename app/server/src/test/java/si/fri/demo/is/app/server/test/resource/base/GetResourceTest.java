package si.fri.demo.is.app.server.test.resource.base;

import org.junit.Assert;
import org.junit.Test;
import si.fri.demo.is.api.client.utility.QueryParamBuilder;
import si.fri.demo.is.api.data.EntityData;
import si.fri.demo.is.api.data.PagingData;
import si.fri.demo.is.api.exception.ISApiException;
import si.fri.demo.is.api.resource.base.ISGetResource;
import si.fri.demo.is.core.jpa.entities.base.BaseEntity;

import java.util.List;
import java.util.Random;

public abstract class GetResourceTest<T extends BaseEntity, B extends ISGetResource<T>> extends BaseResourceTest<T, B> {

    protected T latestEntity;

    @Test
    public void testResource() throws ISApiException, IllegalAccessException {
        super.testResource();
        testGetResource();
    }

    protected void testGetResource() throws ISApiException {

        QueryParamBuilder builder = new QueryParamBuilder();
        PagingData<T> paging = testGet(builder.buildQuery());

        List<T> items = paging.getItems();
        if(items.size() > 0){
            int randomItemIndex = new Random().nextInt(items.size());
            int itemId = items.get(randomItemIndex).getId();

            testGetById(itemId);
        }

    }

    protected PagingData<T> testGet(String queryParam) throws ISApiException {

        PagingData<T> pagingData = resource.get(queryParam);

        Assert.assertTrue(pagingData.isStatusValid());

        return pagingData;
    }

    protected EntityData<T> testGetById(int id) throws ISApiException {

        EntityData<T> entityData = resource.getById(id);

        Assert.assertTrue(entityData.isStatusValid());
        Assert.assertTrue(entityData.getItem().getId().equals(id));

        latestEntity = entityData.getItem();

        return entityData;
    }


    public T getLatestEntity() {
        return latestEntity;
    }
}
