package si.fri.demo.is.api.client;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import si.fri.demo.is.api.ISApiConfiguration;
import si.fri.demo.is.api.client.authorization.provider.base.ISApiAuthProvider;
import si.fri.demo.is.api.exception.ISApiException;

import java.io.IOException;

public class ISClient {

    private HttpClient client;
    private ISApiAuthProvider provider;
    private ISApiConfiguration configuration;

    public ISClient(ISApiConfiguration configuration) {
        this(configuration, null);
    }

    public ISClient(ISApiConfiguration configuration, ISApiAuthProvider provider){
        this.configuration = configuration;
        this.client = HttpClientBuilder.create().build();
        this.provider = provider;
    }

    private void addHeaders(HttpUriRequest request){
        request.addHeader("Accept", "application/json");
        request.addHeader("Content-Type", "application/json; charset=utf-8");

        if(provider != null){
            request.addHeader("Authorization", provider.getAuthorizationToken());
        }
    }

    public HttpResponse execute(HttpUriRequest request) throws ISApiException {
        addHeaders(request);

        HttpResponse response = null;
        try {
            response = client.execute(request);
        } catch (IOException e) {
            throw new ISApiException("Could not getInfo response from server");
        }
        return response;
    }

    public HttpClient getClient() {
        return client;
    }

    public void setClient(HttpClient client) {
        this.client = client;
    }

    public ISApiAuthProvider getProvider() {
        return provider;
    }

    public void setProvider(ISApiAuthProvider provider) {
        this.provider = provider;
    }

    public ISApiConfiguration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(ISApiConfiguration configuration) {
        this.configuration = configuration;
    }
}
