package si.fri.demo.is.api.resource.base;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;
import si.fri.demo.is.api.ISApiCore;
import si.fri.demo.is.api.client.authorization.provider.base.ISApiAuthProvider;
import si.fri.demo.is.api.data.EntityData;
import si.fri.demo.is.api.data.PagingData;
import si.fri.demo.is.api.data.RequestException;
import si.fri.demo.is.api.exception.ISApiException;
import si.fri.demo.is.core.jpa.entities.base.BaseEntity;

import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public abstract class ISResource<T extends BaseEntity> {

    private final ObjectMapper mapper = new ObjectMapper();

    protected final Class<T> type;
    protected JavaType typeReference;
    protected JavaType listTypeReference;

    protected ISApiCore core;
    protected String endpointUrl;

    protected boolean defaultContentHeader = false;

    public ISResource(ISApiCore core, Class<T> type) {
        this.type = type;
        this.core = core;
        this.endpointUrl = core.getConfiguration().hostName + "/" + type.getSimpleName();

        typeReference = mapper.getTypeFactory().constructType(type);
        listTypeReference = mapper.getTypeFactory().constructCollectionType(List.class, type);
    }

    public T getEmptyItem() throws ISApiException {
        try {
            return type.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ISApiException("Could not create new instance");
        }
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

    private String getLocationHeaderValue(HttpResponse response){
        Header[] headers = response.getHeaders("Location");
        if(headers.length == 1) {
            return headers[0].getValue();
        }
        return null;
    }

    protected <P> EntityData<P> buildVcgEntityByClass(HttpResponse response, Class<P> classType){
        return buildVcgEntityByClass(response, mapper.getTypeFactory().constructType(classType));
    }

    protected <P> EntityData<P> buildVcgEntityByClass(HttpResponse response, JavaType classType){
        Response.Status status = Response.Status.fromStatusCode(response.getStatusLine().getStatusCode());
        EntityData<P> entityData = new EntityData<P>(status);
        entityData.setLocation(getLocationHeaderValue(response));

        try {
            String content = buildContent(response);

            switch (status){
                case CREATED:
                case OK:
                    P item = mapper.readValue(content, classType);
                    entityData.setItem(item);
                    break;
                default:
                    RequestException exception = mapper.readValue(content, RequestException.class);
                    entityData.setIsApiException(exception);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return entityData;
    }

    protected PagingData<T> buildVcgPaging(HttpResponse response) throws ISApiException {
        Response.Status status = Response.Status.fromStatusCode(response.getStatusLine().getStatusCode());
        PagingData<T> pagingData = new PagingData<T>(status);

        try {
            String content = buildContent(response);

            switch (status){
                case OK:
                    int count = Integer.parseInt(response.getHeaders("X-Count")[0].getValue());
                    pagingData.setCount(count);

                    ArrayList<T> items = mapper.readValue(content, listTypeReference);
                    pagingData.setItems(items);
                    break;
                default:
                    RequestException exception = mapper.readValue(content, RequestException.class);
                    pagingData.setIsApiException(exception);
                    break;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pagingData;
    }

    protected EntityData<T> buildVcgEntity(HttpResponse response) throws ISApiException {
        Response.Status status = Response.Status.fromStatusCode(response.getStatusLine().getStatusCode());
        EntityData<T> entityData = new EntityData<T>(status);
        entityData.setLocation(getLocationHeaderValue(response));

        try {
            String content = buildContent(response);

            switch (status){
                case CREATED:
                case OK:
                    if(content.length() > 0){
                        JavaType typeReference = mapper.getTypeFactory().constructType(getType());
                        T item = mapper.readValue(content, typeReference);
                        entityData.setItem(item);
                    }
                case NO_CONTENT:
                    break;
                default:
                    RequestException exception = mapper.readValue(content, RequestException.class);
                    entityData.setIsApiException(exception);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return entityData;
    }

    protected void setHeaders(HttpRequest request, boolean contentHeader){
        request.addHeader("Accept", "application/json");
        request.addHeader("Content-Type", "application/json; charset=utf-8");

        ISApiAuthProvider provider = core.getApiAuthProvider();
        if(provider != null){
            request.addHeader("Authorization", "Bearer " + provider.getAuthorizationToken());
        }

        if(contentHeader){
            request.addHeader("X-Content", "true");
        }
    }

    protected boolean isContentHeaderAdded(){
        return defaultContentHeader || core.getDefaultCoreContentHeader();
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

    public ISApiException buildNotImplemented(){
        return new ISApiException("Not implemented.");
    }


    public Class<T> getType() {
        return type;
    }

    public ISApiCore getCore() {
        return core;
    }

    public void setCore(ISApiCore core) {
        this.core = core;
    }

    public String getEndpointUrl() {
        return endpointUrl;
    }

    public void setEndpointUrl(String endpointUrl) {
        this.endpointUrl = endpointUrl;
    }

    public boolean isDefaultContentHeader() {
        return defaultContentHeader;
    }

    public void setDefaultContentHeader(boolean defaultContentHeader) {
        this.defaultContentHeader = defaultContentHeader;
    }
}
