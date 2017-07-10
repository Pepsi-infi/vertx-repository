package tp.cms.request;

public class SubjectPackageRequest{

//    private static String REQ_URL = ApplicationUtils.get(ApplicationConstants.CMS_SUBJECT_PACKAGE_URL);

    public static String REQ_HOST="static.api.letv.com";
    private static String REQ_URL = "/cms/tj/getPackageN";
    private String pkgid;
    private String lang;

    public SubjectPackageRequest(String pkgid, String lang) {
        this.pkgid = pkgid;
        this.lang = lang;
    }

    public String build() {
        return REQ_URL + "?pkgid=" + pkgid + "&lang=" + lang;
    }

    public String getPkgid() {
        return pkgid;
    }

    public void setPkgid(String pkgid) {
        this.pkgid = pkgid;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

}
