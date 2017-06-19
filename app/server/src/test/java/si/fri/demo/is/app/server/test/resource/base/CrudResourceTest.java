package si.fri.demo.is.app.server.test.resource.base;

import org.junit.Assert;
import org.junit.Test;
import si.fri.demo.is.api.data.response.EntityResponse;
import si.fri.demo.is.api.exception.ISApiException;
import si.fri.demo.is.api.resource.base.ISCrudResource;
import si.fri.demo.is.core.jpa.entities.base.BaseEntity;

public abstract class CrudResourceTest<T extends BaseEntity, B extends ISCrudResource<T>> extends GetResourceTest<T, B> {

    protected abstract T buildCreateEntity();
    protected abstract T buildPutEntity(T entity);
    protected abstract T buildPatchEntity(T entity);

    protected void setEntity(T source, T dest){
        dest.setId(source.getId());
    }

    @Test
    public void testResource() throws ISApiException, IllegalAccessException {
        // Test POST method
        T entity = buildCreateEntity();
        EntityResponse<T> entityData = testPost(entity);

        // Test PUT method
        T entityPut = buildPutEntity(entityData.getItem());
        EntityResponse<T> putEntityData = testPut(entityPut);
        Assert.assertFalse(putEntityData.getItem().isUpdateDifferent(entityPut));
        Assert.assertTrue(putEntityData.getItem().isUpdateDifferent(entityData.getItem()));

        // Test PATCH method
        T entityPatch = buildPatchEntity(putEntityData.getItem());
        EntityResponse<T> patchEntityData = testPatch(entityPatch);
        Assert.assertFalse(patchEntityData.getItem().isPatchDifferent(entityPatch));
        Assert.assertTrue(patchEntityData.getItem().isPatchDifferent(putEntityData.getItem()));

        // Test DELETE method
        EntityResponse<T> deleteEntityData = testDelete(patchEntityData.getItem().getId());
        Assert.assertTrue(deleteEntityData.getItem().getIsDeleted());

        // Test toggle DELETE method
        EntityResponse<T> toggleDeleteEntityData = testToggleDelete(patchEntityData.getItem().getId());

        Assert.assertTrue(toggleDeleteEntityData.getItem().getIsDeleted() != deleteEntityData.getItem().getIsDeleted());

        super.testResource();
    }

    public EntityResponse<T> testPost(T entity) throws ISApiException, IllegalAccessException {

        EntityResponse<T> entityData = resource.post(entity);

        Assert.assertTrue(entityData.isStatusValid());
        Assert.assertNotNull(entityData.getItem().getId());

        setEntity(entityData.getItem(), entity);
        Assert.assertFalse(entityData.getItem().isUpdateDifferent(entity));

        return entityData;
    }

    public EntityResponse<T> testPut(T entity) throws ISApiException, IllegalAccessException {
        Assert.assertNotNull(entity.getId());

        EntityResponse<T> entityData = resource.put(entity);

        Assert.assertTrue(entityData.isStatusValid());
        Assert.assertNotNull(entityData.getItem());

        entity.setId(entityData.getItem().getId());
        Assert.assertFalse(entityData.getItem().isUpdateDifferent(entity));

        return entityData;
    }

    public EntityResponse<T> testPatch(T entity) throws ISApiException, IllegalAccessException {
        Assert.assertNotNull(entity.getId());

        EntityResponse<T> entityData = resource.patch(entity);

        Assert.assertTrue(entityData.isStatusValid());
        Assert.assertNotNull(entityData.getItem().getId());

        entity.setId(entityData.getItem().getId());
        Assert.assertFalse(entityData.getItem().isPatchDifferent(entity));

        return entityData;
    }

    public EntityResponse<T> testDelete(Integer id) throws ISApiException {
        Assert.assertNotNull(id);

        EntityResponse<T> entityData = resource.delete(id);

        Assert.assertTrue(entityData.isStatusValid());

        return entityData;
    }

    public EntityResponse<T> testToggleDelete(Integer id) throws ISApiException {
        Assert.assertNotNull(id);

        EntityResponse<T> entityData = resource.toggleIsDeleted(id);

        Assert.assertTrue(entityData.isStatusValid());

        return entityData;
    }

}
