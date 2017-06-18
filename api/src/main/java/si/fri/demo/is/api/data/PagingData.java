package si.fri.demo.is.api.data;

import si.fri.demo.is.api.data.base.BaseData;

import javax.ws.rs.core.Response;
import java.util.List;

public class PagingData<T> extends BaseData {

    private Integer count = 0;
    private List<T> items;

    public PagingData(Response.Status status){
        super(status);
    }

    public PagingData(Response.Status status, List<T> items, Integer count){
        super(status);
        this.items = items;
        this.count = count;
    }

    public PagingData(Response.Status status, RequestException e){
        super(status);
        this.isApiException = e;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

}
