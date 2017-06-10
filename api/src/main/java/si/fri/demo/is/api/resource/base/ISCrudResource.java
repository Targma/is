package si.fri.demo.is.api.resource.base;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.*;
import si.fri.demo.is.api.client.ISClient;
import si.fri.demo.is.api.data.EntityData;
import si.fri.demo.is.api.data.PagingData;
import si.fri.demo.is.api.exception.ISApiException;
import si.fri.demo.is.core.jpa.entities.base.BaseEntity;

public class ISCrudResource<T extends BaseEntity> extends ISGetResource<T> {

    public ISCrudResource(ISClient client, Class<T> type) {
        super(client, type);
    }

    public PagingData<T> get(String queryParams) throws ISApiException {
        String url = endpointUrl + queryParams;
        HttpGet get = new HttpGet(url);
        HttpResponse response = client.execute(get);

        return buildVcgPaging(response);
    }

    public EntityData<T> getById(int id) throws ISApiException {
        String url = endpointUrl + "/" + id;
        HttpGet get = new HttpGet(url);
        HttpResponse response = client.execute(get);

        return buildVcgEntity(response);
    }

    public EntityData<T> post(T item) throws ISApiException {
        String url = endpointUrl;
        HttpPost post = new HttpPost(url);
        setHeaders(post);
        post.setEntity(buildJsonContent(item));

        HttpResponse response = client.execute(post);

        return buildVcgEntity(response);
    }

    public EntityData<T> put(T item) throws ISApiException {
        String url = endpointUrl + "/" + item.getId();
        HttpPut put = new HttpPut(url);
        setHeaders(put);
        put.setEntity(buildJsonContent(item));

        HttpResponse response = client.execute(put);

        return buildVcgEntity(response);
    }

    public EntityData<T> patch(T item) throws ISApiException {
        String url = endpointUrl + "/" + item.getId();
        HttpPatch patch = new HttpPatch(url);
        setHeaders(patch);
        patch.setEntity(buildJsonContent(item));

        HttpResponse response = client.execute(patch);

        return buildVcgEntity(response);
    }

    public EntityData<T> delete(int id) throws ISApiException {
        String url = endpointUrl + "/" + id;
        HttpDelete delete = new HttpDelete(url);
        HttpResponse response = client.execute(delete);

        return buildVcgEntity(response);
    }

    public EntityData<T> toggleIsDeleted(int id) throws ISApiException {
        String url = endpointUrl + "/" + id + "/toggleIsDeleted";
        HttpPut put = new HttpPut(url);
        HttpResponse response = client.execute(put);

        return buildVcgEntity(response);
    }
}
