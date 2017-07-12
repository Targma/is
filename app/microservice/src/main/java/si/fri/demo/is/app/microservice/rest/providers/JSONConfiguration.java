package si.fri.demo.is.app.microservice.rest.providers;

import com.fasterxml.jackson.databind.ObjectMapper;
import si.fri.demo.is.core.restComponents.utility.JSONObjectMapper;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

@Provider
public class JSONConfiguration implements ContextResolver<ObjectMapper> {

    public static final ObjectMapper jsonMapper =
            JSONObjectMapper.buildDefault();

    @Override
    public ObjectMapper getContext(Class<?> type) {
        return jsonMapper;
    }
}