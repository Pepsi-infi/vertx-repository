package service.param;

import java.util.Map;

/**
 * Created by zhushenghao1 on 16/12/30.
 */
public class ParseDataParam {
    private String pageid;
    private Boolean supportNavigationLabel;
    private Boolean supportCMSPic169;
    private String terminalApplication;
    private String contentstyle;
    private Map<String, Object> caches;
    private Integer cid;
    private Integer cms_num;

    public String getPageid() {
        return pageid;
    }

    public void setPageid(String pageid) {
        this.pageid = pageid;
    }

    public Boolean getSupportNavigationLabel() {
        return supportNavigationLabel;
    }

    public void setSupportNavigationLabel(Boolean supportNavigationLabel) {
        this.supportNavigationLabel = supportNavigationLabel;
    }

    public Boolean getSupportCMSPic169() {
        return supportCMSPic169;
    }

    public void setSupportCMSPic169(Boolean supportCMSPic169) {
        this.supportCMSPic169 = supportCMSPic169;
    }

    public String getTerminalApplication() {
        return terminalApplication;
    }

    public void setTerminalApplication(String terminalApplication) {
        this.terminalApplication = terminalApplication;
    }

    public String getContentstyle() {
        return contentstyle;
    }

    public void setContentstyle(String contentstyle) {
        this.contentstyle = contentstyle;
    }

    public Map<String, Object> getCaches() {
        return caches;
    }

    public void setCaches(Map<String, Object> caches) {
        this.caches = caches;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getCms_num() {
        return cms_num;
    }

    public void setCms_num(Integer cms_num) {
        this.cms_num = cms_num;
    }
}
