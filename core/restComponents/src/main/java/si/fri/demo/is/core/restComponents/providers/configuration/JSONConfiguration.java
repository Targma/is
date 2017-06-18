package si.fri.demo.is.core.restComponents.providers.configuration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

@Provider
public class JSONConfiguration implements ContextResolver<ObjectMapper> {

    public static final ObjectMapper jsonMapper =
            new ObjectMapper()
                    .enable(SerializationFeature.INDENT_OUTPUT)
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                    .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                    .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                    .registerModule(new Hibernate4Module());

    @Override
    public ObjectMapper getContext(Class<?> type) {
        return jsonMapper;
    }
}