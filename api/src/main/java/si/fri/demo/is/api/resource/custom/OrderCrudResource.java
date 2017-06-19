package si.fri.demo.is.api.resource.custom;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import si.fri.demo.is.api.ISApiCore;
import si.fri.demo.is.api.data.request.EntityRequest;
import si.fri.demo.is.api.data.response.EntityResponse;
import si.fri.demo.is.api.exception.ISApiException;
import si.fri.demo.is.api.resource.base.ISGetResource;
import si.fri.demo.is.core.jpa.entities.Order;

public class OrderCrudResource extends ISGetResource<Order> {

    public OrderCrudResource(ISApiCore core) {
        super(core, Order.class);
    }

    public EntityResponse<Order> process(Order item) throws ISApiException{
        return process(new EntityRequest<Order>(item, isContentHeaderAdded()));
    }
    public EntityResponse<Order> process(EntityRequest<Order> orderEntityRequest) throws ISApiException {
        Order item = orderEntityRequest.getEntity();
        checkEntity(item, true);

        String url = endpointUrl + "/process";
        HttpPost post = new HttpPost(url);
        setHeaders(post, orderEntityRequest);
        post.setEntity(buildJsonContent(item));

        HttpResponse response = core.getClient().execute(post);

        return buildVcgEntity(response);
    }
}
