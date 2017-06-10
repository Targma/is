package si.fri.demo.is.api.data;

import si.fri.demo.is.api.data.base.BaseData;
import si.fri.demo.is.api.exception.ISApiException;

import javax.ws.rs.core.Response;

public class EntityData<T> extends BaseData {

    private T item;

    public EntityData(Response.Status status){
        super(status);
    }

    public EntityData(Response.Status status, T item){
        super(status);
        this.item = item;
    }

    public EntityData(Response.Status status, ISApiException e) {
        super(status);
        this.isApiException = e;
    }

    public T getItem() {
        return item;
    }

    public void setItem(T item) {
        this.item = item;
    }


}
