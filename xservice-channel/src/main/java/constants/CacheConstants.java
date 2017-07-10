package constants;

public class CacheConstants {

    public static final String CMS_PAGE_ = "cms_page_";
    public static final String CMS_COLUMN_ = "cms_column_";
    public static final String CMS_PAGE_COLUMN_ = "cms_page_column_";
    public static final String CMS_SEARCHURL_ = "cms_searchurl_";

    public static final String getCMSColumnsByPageId(String pageId) {
        return new StringBuffer().append(CMS_PAGE_).append(pageId).toString();
    }

    public static final String getCMSChannelDtosByColumnId(String columnId) {
        return new StringBuffer().append(CMS_COLUMN_).append(columnId).toString();
    }

    public static final String getProgramListByPageAndColumnId(String pageId, String columnId) {
        return new StringBuffer().append(CMS_PAGE_COLUMN_).append(pageId).append("_").append(columnId).toString();
    }

    public static final String getCMSColumnListByColumnId(String columnId) {
        return new StringBuffer().append(CMS_COLUMN_).append(columnId).toString();
    }

    public static final String getCMSDataByPageId(String pageId) {
        return new StringBuffer().append(CMS_PAGE_).append(pageId).toString();
    }

    public static final String getCMSDataByTypeAndSearchUrl(String type, String url) {
        return new StringBuffer().append(CMS_SEARCHURL_).append(type).append("_").append(url).toString();
    }

}
