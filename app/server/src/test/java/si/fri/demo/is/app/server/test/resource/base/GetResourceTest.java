package si.fri.demo.is.app.server.test.resource.base;

import org.junit.Assert;
import org.junit.Test;
import si.fri.demo.is.api.client.utility.QueryParamBuilder;
import si.fri.demo.is.api.data.request.IdRequest;
import si.fri.demo.is.api.data.response.EntityResponse;
import si.fri.demo.is.api.data.response.PagingResponse;
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
        PagingResponse<T> paging = testGet(builder.buildQuery());

        List<T> items = paging.getItems();
        if(items.size() > 0){
            int randomItemIndex = new Random().nextInt(items.size());
            int itemId = items.get(randomItemIndex).getId();

            EntityResponse<T> response = testGetById(itemId);

            IdRequest request = new IdRequest(itemId, response.geteTagHeader());
            EntityResponse<T> checkResponse = testGetById(request);

            Assert.assertTrue(checkResponse.isStatusValid());
            Assert.assertNull(checkResponse.getItem());
        }

    }

    protected PagingResponse<T> testGet(String queryParam) throws ISApiException {

        PagingResponse<T> pagingData = resource.get(queryParam);

        Assert.assertTrue(pagingData.isStatusValid());

        return pagingData;
    }

    protected EntityResponse<T> testGetById(Integer id) throws ISApiException{
        return testGetById(new IdRequest(id));
    }
    protected EntityResponse<T> testGetById(IdRequest request) throws ISApiException {
        Assert.assertNotNull(request);
        Assert.assertNotNull(request.getId());

        EntityResponse<T> entityData = resource.getById(request);

        Assert.assertTrue(entityData.isStatusValid());
        Assert.assertTrue(entityData.getItem().getId().equals(request.getId()));

        latestEntity = entityData.getItem();

        return entityData;
    }


    public T getLatestEntity() {
        return latestEntity;
    }
}
