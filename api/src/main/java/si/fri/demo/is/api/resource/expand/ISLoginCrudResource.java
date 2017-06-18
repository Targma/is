package si.fri.demo.is.api.resource.expand;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import si.fri.demo.is.api.ISApiCore;
import si.fri.demo.is.api.data.EntityData;
import si.fri.demo.is.api.exception.ISApiException;
import si.fri.demo.is.api.resource.base.ISCrudResource;
import si.fri.demo.is.core.jpa.entities.base.BaseEntity;

public class ISLoginCrudResource<T extends BaseEntity> extends ISCrudResource<T> {

    public ISLoginCrudResource(ISApiCore core, Class<T> type) {
        super(core, type);
    }

    public EntityData<T> login() throws ISApiException {
        String url = endpointUrl + "/login";
        HttpGet get = new HttpGet(url);
        setHeaders(get, false);

        HttpResponse response = core.getClient().execute(get);

        return buildVcgEntity(response);
    }
}
