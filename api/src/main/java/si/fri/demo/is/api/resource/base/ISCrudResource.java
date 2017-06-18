package si.fri.demo.is.api.resource.base;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import si.fri.demo.is.api.ISApiCore;
import si.fri.demo.is.api.data.EntityData;
import si.fri.demo.is.api.exception.ISApiException;
import si.fri.demo.is.core.jpa.entities.base.BaseEntity;

public class ISCrudResource<T extends BaseEntity> extends ISGetResource<T> {

    public ISCrudResource(ISApiCore client, Class<T> type) {
        super(client, type);
    }

    public EntityData<T> post(T item) throws ISApiException {
        return post(item, isContentHeaderAdded());
    }
    public EntityData<T> post(T item, boolean contentHeader) throws ISApiException {
        checkEntity(item, true);

        String url = endpointUrl;
        HttpPost post = new HttpPost(url);
        setHeaders(post, contentHeader);
        post.setEntity(buildJsonContent(item));

        HttpResponse response = core.getClient().execute(post);

        return buildVcgEntity(response);
    }

    public EntityData<T> put(T item) throws ISApiException {
        return put(item, isContentHeaderAdded());
    }
    public EntityData<T> put(T item, boolean contentHeader) throws ISApiException {
        checkEntity(item);

        String url = endpointUrl + "/" + item.getId();
        HttpPut put = new HttpPut(url);
        setHeaders(put, contentHeader);
        put.setEntity(buildJsonContent(item));

        HttpResponse response = core.getClient().execute(put);

        return buildVcgEntity(response);
    }

    public EntityData<T> patch(T item) throws ISApiException {
        return patch(item, isContentHeaderAdded());
    }
    public EntityData<T> patch(T item, boolean contentHeader) throws ISApiException {
        checkEntity(item);

        String url = endpointUrl + "/" + item.getId();
        HttpPatch patch = new HttpPatch(url);
        setHeaders(patch, contentHeader);
        patch.setEntity(buildJsonContent(item));

        HttpResponse response = core.getClient().execute(patch);

        return buildVcgEntity(response);
    }

    public EntityData<T> delete(Integer id) throws ISApiException {
        return delete(id, isContentHeaderAdded());
    }
    public EntityData<T> delete(Integer id, boolean contentHeader) throws ISApiException {
        checkId(id);

        String url = endpointUrl + "/" + id;
        HttpDelete delete = new HttpDelete(url);
        setHeaders(delete, contentHeader);

        HttpResponse response = core.getClient().execute(delete);

        return buildVcgEntity(response);
    }

    public EntityData<T> toggleIsDeleted(Integer id) throws ISApiException {
        return toggleIsDeleted(id, isContentHeaderAdded());
    }
    public EntityData<T> toggleIsDeleted(Integer id, boolean contentHeader) throws ISApiException {
        checkId(id);

        String url = endpointUrl + "/" + id + "/toggleIsDeleted";
        HttpPut put = new HttpPut(url);
        setHeaders(put, contentHeader);

        HttpResponse response = core.getClient().execute(put);

        return buildVcgEntity(response);
    }



    private void checkEntity(T entity) throws ISApiException{
        checkEntity(entity, false);
    }

    private void checkEntity(T entity, boolean idNullable) throws ISApiException {
        if(entity == null){
            throw new ISApiException("Entity can not be null");
        } else {
            if(!idNullable && entity.getId() == null) {
                throw new ISApiException("Entity id can not be null");
            }
        }
    }
}
