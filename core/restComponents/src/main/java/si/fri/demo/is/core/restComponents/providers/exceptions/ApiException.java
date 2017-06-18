package si.fri.demo.is.core.restComponents.providers.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import si.fri.demo.is.core.businessLogic.exceptions.BusinessLogicBaseException;

import java.io.Serializable;

public class ApiException implements Serializable {

    public String message;
    public Exception exception;

    public ApiException(String message, Exception exception) {
        this.message = message;
        this.exception = exception;
    }

    @JsonIgnore
    public static ApiException build(BusinessLogicBaseException exception){
        return new ApiException(exception.getMessage(), exception);
    }

    @JsonIgnore
    public static ApiException build(String message, Exception exception){
        return new ApiException(message, exception);
    }

}
