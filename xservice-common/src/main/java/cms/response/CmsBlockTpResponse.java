package cms.response;

import java.util.List;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true,inheritConverter = true)
public class CmsBlockTpResponse extends BaseResponse {

    private static final long serialVersionUID = 7304312135751508919L;
    private String id; // CMS版块id
    private String name; // 版块名称
    private String pid; // 父版块id
    private List<CmsBlockContent> blockContent;
    private List<CmsBlockTpResponse> subBlocks; // 子版块
    private List<Integer> subBlockList; //子版块ID

    public CmsBlockTpResponse(){
        
    }
    
    public CmsBlockTpResponse(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        CmsBlockTpResponseConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        CmsBlockTpResponseConverter.toJson(this, json);
        return json;
    }
    
    public List<Integer> getSubBlockList() {
        return subBlockList;
    }

    public void setSubBlockList(List<Integer> subBlockList) {
        this.subBlockList = subBlockList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public List<CmsBlockContent> getBlockContent() {
        return blockContent;
    }

    public void setBlockContent(List<CmsBlockContent> blockContent) {
        this.blockContent = blockContent;
    }

    public List<CmsBlockTpResponse> getSubBlocks() {
        return subBlocks;
    }

    public void setSubBlocks(List<CmsBlockTpResponse> subBlocks) {
        this.subBlocks = subBlocks;
    }

    @Override
    public String toString() {
        return "CmsBlockTpResponse{" +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", pid='" + pid + '\'' +
                ", blockContent=" + blockContent +
                ", subBlocks=" + subBlocks +
                ", subBlockList=" + subBlockList +
                '}';
    }

}
