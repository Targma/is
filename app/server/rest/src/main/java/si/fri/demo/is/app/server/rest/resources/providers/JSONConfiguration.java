package si.fri.demo.is.app.server.rest.resources.providers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import si.fri.demo.is.core.restComponents.utility.JSONObjectMapper;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

@Provider
public class JSONConfiguration implements ContextResolver<ObjectMapper> {

    public static final ObjectMapper jsonMapper =
           JSONObjectMapper.buildDefault().registerModule(new Hibernate4Module());

    @Override
    public ObjectMapper getContext(Class<?> type) {
        return jsonMapper;
    }
}