package tp.cms.request;

public class SubjectContentUrl{
//    private static String REQ_URL = ApplicationUtils.get(ApplicationConstants.CMS_SUBJECT_CONTENT_URL);

    public static String REQ_HOST = "static.api.letv.com";
    private static String REQ_URL = "/cms/tj/getTjS";
    private String zid;

    public String getZid() {
        return zid;
    }

    public void setZid(String zid) {
        this.zid = zid;
    }

    public String build() {
        return REQ_URL+ "?zid=" + this.zid;
    }

}
