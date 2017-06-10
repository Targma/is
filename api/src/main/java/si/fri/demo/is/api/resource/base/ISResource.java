package si.fri.demo.is.api.resource.base;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;
import si.fri.demo.is.api.ISApiConfiguration;
import si.fri.demo.is.api.client.ISClient;
import si.fri.demo.is.api.data.EntityData;
import si.fri.demo.is.api.data.PagingData;
import si.fri.demo.is.api.exception.ISApiException;
import si.fri.demo.is.core.jpa.entities.base.BaseEntity;

import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public abstract class ISResource<T extends BaseEntity> {
    protected Class<T> type;

    protected ISClient client;
    protected ISApiConfiguration configuration;

    protected String endpointUrl;

    private final ObjectMapper mapper = new ObjectMapper();

    public ISResource(ISClient client, Class<T> type) {
        this.client = client;
        this.configuration = client.getConfiguration();

        this.type = type;
        this.endpointUrl = configuration.hostName + "/" + type.getSimpleName();
    }

    public T getEmptyItem() throws ISApiException {
        try {
            return type.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ISApiException("Could not create new instance");
        }
    }

    protected <P> EntityData<P> buildVcgEntityByClass(HttpResponse response, Class<P> classType){
        Response.Status status = Response.Status.fromStatusCode(response.getStatusLine().getStatusCode());
        try {
            if(status == Response.Status.NO_CONTENT) {
                return new EntityData<P>(status);
            }

            String content = buildContent(response);

            if(status == Response.Status.OK || status == Response.Status.CREATED){
                P item = mapper.readValue(content, classType);
                return new EntityData<P>(status, item);
            } else {
                try {
                    return new EntityData<P>(status, mapper.readValue(content, ISApiException.class));
                } catch (Exception e){
                    return new EntityData<P>(status);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new EntityData<P>(status);
    }

    protected PagingData<T> buildVcgPaging(HttpResponse response) throws ISApiException {
        Response.Status status = Response.Status.fromStatusCode(response.getStatusLine().getStatusCode());

        try {
            String content = buildContent(response);

            if(status == Response.Status.OK || status == Response.Status.CREATED){
                int count = Integer.parseInt(response.getHeaders("X-Count")[0].getValue());
                JavaType typeReference = mapper.getTypeFactory().constructCollectionType(List.class, getType());
                ArrayList<T> items = mapper.readValue(content, typeReference);
                return new PagingData<T>(status, items, count);
            } else {
                try {
                    return new PagingData<T>(status, mapper.readValue(content, ISApiException.class));
                } catch (Exception e){
                    e.printStackTrace();
                    return new PagingData<T>(status, new ArrayList<T>(), 0);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new PagingData<T>(status, null, 0);
    }

    private String buildContent(HttpResponse response) throws IOException {
        HttpEntity entity = response.getEntity();
        BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
        StringBuffer sb = new StringBuffer();

        String inputLine;
        while ((inputLine = br.readLine()) != null) {
            sb.append(inputLine + "\r\n");
        }

        return sb.toString();
    }

    protected EntityData<T> buildVcgEntity(HttpResponse response) throws ISApiException {
        Response.Status status = Response.Status.fromStatusCode(response.getStatusLine().getStatusCode());
        try {
            if(status == Response.Status.NO_CONTENT) {
                return new EntityData<T>(status);
            }

            String content = buildContent(response);

            if(status == Response.Status.OK || status == Response.Status.CREATED){
                JavaType typeReference = mapper.getTypeFactory().constructType(getType());
                T item = mapper.readValue(content, typeReference);
                return new EntityData<T>(status, item);
            } else {
                try {
                    return new EntityData<T>(status, mapper.readValue(content, ISApiException.class));
                } catch (Exception e){
                    return new EntityData<T>(status);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new EntityData<T>(status);
    }

    protected void setHeaders(HttpRequest httpRequest){
        httpRequest.addHeader("Content-Type", "application/json");
    }

    protected StringEntity buildJsonContent(T item) throws ISApiException {
        try {
            return new StringEntity(mapper.writeValueAsString(item), "UTF-8");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        throw new ISApiException("Could not write object as JSON");
    }

    protected StringEntity buildJsonContent(List<T> item) throws ISApiException {
        try {
            return new StringEntity(mapper.writeValueAsString(item), "UTF-8");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        throw new ISApiException("Could not write object as JSON");
    }

    public abstract PagingData<T> get(String queryParams) throws ISApiException;
    public abstract EntityData<T> getById(int id) throws ISApiException;
    public abstract EntityData<T> post(T item) throws ISApiException;
    public abstract EntityData<T> put(T item) throws ISApiException;
    public abstract EntityData<T> patch(T item) throws ISApiException;
    public abstract EntityData<T> delete(int id) throws ISApiException;
    public abstract EntityData<T> toggleIsDeleted(int id) throws ISApiException;

    public ISApiException buildNotImplemented(){
        return new ISApiException("Not implemented.");
    }

    public Class<T> getType() {
        return type;
    }

    public void setType(Class<T> type) {
        this.type = type;
    }

    public ISClient getClient() {
        return client;
    }

    public void setClient(ISClient client) {
        this.client = client;
    }

    public ISApiConfiguration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(ISApiConfiguration configuration) {
        this.configuration = configuration;
    }

    public String getEndpointUrl() {
        return endpointUrl;
    }

    public void setEndpointUrl(String endpointUrl) {
        this.endpointUrl = endpointUrl;
    }

}
