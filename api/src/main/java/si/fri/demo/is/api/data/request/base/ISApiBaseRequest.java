package si.fri.demo.is.api.data.request.base;

public class ISApiBaseRequest {

    protected String eTagHeader = null;
    protected boolean xContentHeader = false;

    public String geteTagHeader() {
        return eTagHeader;
    }

    public void seteTagHeader(String eTagHeader) {
        this.eTagHeader = eTagHeader;
    }

    public boolean isxContentHeader() {
        return xContentHeader;
    }

    public void setxContentHeader(boolean xContentHeader) {
        this.xContentHeader = xContentHeader;
    }
}
