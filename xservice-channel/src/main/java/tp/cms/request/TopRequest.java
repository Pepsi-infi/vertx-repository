package tp.cms.request;

public class TopRequest {

//    private static final String TOP_URL = ApplicationUtils.get(ApplicationConstants.TOP_RANK_URL);
    public static String REQ_HOST = "i.top.letv.com";
    private static final String TOP_URL = "/json/";
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String build() {
        // TODO Auto-generated method stub
        return TOP_URL + this.type;
    }

}
