package si.fri.demo.is.api.resource.custom;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import si.fri.demo.is.api.client.ISClient;
import si.fri.demo.is.api.data.EntityData;
import si.fri.demo.is.api.exception.ISApiException;
import si.fri.demo.is.api.resource.base.ISGetResource;
import si.fri.demo.is.core.jpa.entities.Order;

public class OrderCrudResource extends ISGetResource<Order> {

    public OrderCrudResource(ISClient isClient) {
        super(isClient, Order.class);
    }

    public EntityData<Order> process(Order item) throws ISApiException {
        String url = endpointUrl + "/process";
        HttpPost post = new HttpPost(url);
        setHeaders(post);
        post.setEntity(buildJsonContent(item));

        HttpResponse response = client.execute(post);

        return buildVcgEntity(response);
    }
}
