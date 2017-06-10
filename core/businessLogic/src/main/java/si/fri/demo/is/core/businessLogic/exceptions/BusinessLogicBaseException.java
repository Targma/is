package si.fri.demo.is.core.businessLogic.exceptions;

import javax.ws.rs.core.Response;

public abstract class BusinessLogicBaseException extends Exception {

    private Response.Status status;
    private String message;
    private Exception exception;

    public BusinessLogicBaseException(Response.Status status, String message) {
        super(message);
        this.message = message;
        this.status = status;
    }

    public BusinessLogicBaseException(Response.Status status, String message, Exception exception) {
        super(message, exception);
        this.message = message;
        this.status = status;
        this.exception = exception;
    }

    public String toJson() {
        if(status == Response.Status.NO_CONTENT)
            return "";

        StringBuilder sb = new StringBuilder("{");

        sb.append("\"message\":\"" + message + "\"");

        if(exception != null)
            sb.append(",\"exception\":\"" + exception.toString() + "\"");

        return sb.append("}").toString();
    }

    public Response.Status getStatus() {
        return status;
    }

    public void setStatus(Response.Status status) {
        this.status = status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
