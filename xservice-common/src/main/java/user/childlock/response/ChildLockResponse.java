package user.childlock.response;

import java.util.List;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import user.commom.CommonUserConstants;

@DataObject(generateConverter = true)
public class ChildLockResponse {

    private String userId;

    /**
     * -1--never set PIN, the lock default off; 0--PIN ever set, now off,
     * 1--PIN set, now on; others are illegal, regard as -1;
     */
    private Integer status = -1;

    // private String mac;

    /**
     * the key to make updating PIN operation legal
     */
    private String lockToken;

    /**
     * lockToken effective duration in milliseconds, -1 means effective forever
     */
    private Long tokenEffectiveDuration = CommonUserConstants.ChildLock.USER_CHILD_LOCK_SET_TOKEN_EXPIRE_TIME;

    /**
     * content rating ids that are allowed to play
     */
    private List<String> canPlayCRIds;

    
    public ChildLockResponse() {
        super();
    }

    public ChildLockResponse(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        ChildLockResponseConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        ChildLockResponseConverter.toJson(this, json);
        return json;
    }
    
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getLockToken() {
        return lockToken;
    }

    public void setLockToken(String lockToken) {
        this.lockToken = lockToken;
    }

    public Long getTokenEffectiveDuration() {
        return tokenEffectiveDuration;
    }

    public void setTokenEffectiveDuration(Long tokenEffectiveDuration) {
        this.tokenEffectiveDuration = tokenEffectiveDuration;
    }

    public List<String> getCanPlayCRIds() {
        return canPlayCRIds;
    }

    public void setCanPlayCRIds(List<String> canPlayCRIds) {
        this.canPlayCRIds = canPlayCRIds;
    }

}
