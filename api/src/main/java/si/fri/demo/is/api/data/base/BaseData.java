package si.fri.demo.is.api.data.base;

import si.fri.demo.is.api.exception.ISApiException;

import javax.ws.rs.core.Response;

public abstract class BaseData {

    protected Response.Status status;
    protected ISApiException isApiException;

    public BaseData(Response.Status status) {
        this.status = status;
    }

    public Response.Status getStatus() {
        return status;
    }

    public void setStatus(Response.Status status) {
        this.status = status;
    }

    public boolean isStatusValid(){
        switch (status){
            case OK:
            case NO_CONTENT:
            case CREATED:
                return true;
            default:
                return false;
        }
    }

    public ISApiException getIsApiException() {
        return isApiException;
    }

    public void setIsApiException(ISApiException isApiException) {
        this.isApiException = isApiException;
    }
}
