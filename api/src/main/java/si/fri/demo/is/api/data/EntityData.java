package si.fri.demo.is.api.data;

import si.fri.demo.is.api.data.base.BaseData;

import javax.ws.rs.core.Response;

public class EntityData<T> extends BaseData {

    private String location;

    private T item;


    public EntityData(Response.Status status){
        super(status);
    }

    public EntityData(Response.Status status, T item){
        super(status);
        this.item = item;
    }

    public EntityData(Response.Status status, RequestException e) {
        super(status);
        this.isApiException = e;
    }

    public T getItem() {
        return item;
    }

    public void setItem(T item) {
        this.item = item;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getLocationId(){
        if(location != null){
            try {
                int index = location.indexOf('/');
                String id = location.substring(index + 1);
                return Integer.valueOf(id);
            } catch (Exception e){}
        }

        return null;
    }
}
