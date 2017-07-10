package tp.cms.request;

public class BlockUrl {
//    private static String REQ_URL = ApplicationUtils.get(ApplicationConstants.CMS_BLOCK_URL);
    public static String REQ_HOST="static.api.letv.com";
    private static String REQ_URL = "/cmsdata/block/{blockid}.json";

    private String blockid;

    public String getBlockid() {
        return blockid;
    }

    public void setBlockid(String blockid) {
        this.blockid = blockid;
    }

    public String build() {
        String url = REQ_URL;
        if(this.blockid != null){
            url = REQ_URL.replace("{blockid}",this.blockid);
        }
        return url;
    }


}
