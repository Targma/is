package si.fri.demo.is.api.resource.expand;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import si.fri.demo.is.api.client.ISClient;
import si.fri.demo.is.api.data.EntityData;
import si.fri.demo.is.api.exception.ISApiException;
import si.fri.demo.is.api.resource.base.ISCrudResource;
import si.fri.demo.is.core.jpa.entities.base.BaseEntity;

public class LoginCrudResource<T extends BaseEntity> extends ISCrudResource<T> {

    public LoginCrudResource(ISClient vcgClient, Class<T> type) {
        super(vcgClient, type);
    }

    public EntityData<T> login() throws ISApiException {
        String url = endpointUrl + "/login";
        HttpGet get = new HttpGet(url);
        setHeaders(get);

        HttpResponse response = client.execute(get);

        return buildVcgEntity(response);
    }
}
