package service.dto.channelPage;

public class RectRetrieve {
    private String retrieveKey; // 筛选条件的key
    private String retrieveValue; // 筛选条件的value

    public String getRetrieveKey() {
        return retrieveKey;
    }

    public void setRetrieveKey(String retrieveKey) {
        this.retrieveKey = retrieveKey;
    }

    public String getRetrieveValue() {
        return retrieveValue;
    }

    public void setRetrieveValue(String retrieveValue) {
        this.retrieveValue = retrieveValue;
    }
}