package si.fri.demo.is.api.resource.custom;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import si.fri.demo.is.api.ISApiCore;
import si.fri.demo.is.api.data.EntityData;
import si.fri.demo.is.api.exception.ISApiException;
import si.fri.demo.is.api.resource.base.ISGetResource;
import si.fri.demo.is.core.jpa.entities.Order;

public class OrderCrudResource extends ISGetResource<Order> {

    public OrderCrudResource(ISApiCore core) {
        super(core, Order.class);
    }

    public EntityData<Order> process(Order item) throws ISApiException{
        return process(item, isContentHeaderAdded());
    }
    public EntityData<Order> process(Order item, boolean contentHeader) throws ISApiException {
        String url = endpointUrl + "/process";
        HttpPost post = new HttpPost(url);
        setHeaders(post, contentHeader);
        post.setEntity(buildJsonContent(item));

        HttpResponse response = core.getClient().execute(post);

        return buildVcgEntity(response);
    }
}
