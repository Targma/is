package si.fri.demo.is.api.resource.base;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import si.fri.demo.is.api.client.ISClient;
import si.fri.demo.is.api.data.EntityData;
import si.fri.demo.is.api.data.PagingData;
import si.fri.demo.is.api.exception.ISApiException;
import si.fri.demo.is.core.jpa.entities.base.BaseEntity;


public class ISGetResource<T extends BaseEntity> extends ISResource<T> {

    public ISGetResource(ISClient client, Class<T> type) {
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

    @Override
    public EntityData<T> post(T item) throws ISApiException {
        throw buildNotImplemented();
    }

    @Override
    public EntityData<T> put(T item) throws ISApiException {
        throw buildNotImplemented();
    }

    @Override
    public EntityData<T> patch(T item) throws ISApiException {
        throw buildNotImplemented();
    }

    @Override
    public EntityData<T> delete(int id) throws ISApiException {
        throw buildNotImplemented();
    }

    @Override
    public EntityData<T> toggleIsDeleted(int id) throws ISApiException {
        throw buildNotImplemented();
    }


}
