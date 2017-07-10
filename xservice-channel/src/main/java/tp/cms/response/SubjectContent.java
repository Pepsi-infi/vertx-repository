package tp.cms.response;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

import java.util.List;

/**
 * 定义第三方接口返回专题实体类
 */
@DataObject(generateConverter = true,inheritConverter = true)
public class SubjectContent extends BaseResponse {
    private static final long serialVersionUID = 8953285642660578700L;
    private String description;// 专题简介
    private String name;// 专题名称
    private String pubName;// 发布名称
    private String tag;// 标签
    private String tvPic = "";// 背景图地址
    private String focusMPic;
    private String pic169;
    private String ctime;// 创建时间
    private List<ContentPackage> tjPackages;
    private List<String> packageIds;

    public SubjectContent() {

    }

    public SubjectContent(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        SubjectContentConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        SubjectContentConverter.toJson(this, json);
        return json;
    }

    public List<String> getPackageIds() {
        return packageIds;
    }

    public void setPackageIds(List<String> packageIds) {
        this.packageIds = packageIds;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPubName() {
        return pubName;
    }

    public void setPubName(String pubName) {
        this.pubName = pubName;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTvPic() {
        return tvPic;
    }

    public void setTvPic(String tvPic) {
        this.tvPic = tvPic;
    }

    public List<ContentPackage> getTjPackages() {
        return tjPackages;
    }

    public void setTjPackages(List<ContentPackage> tjPackages) {
        this.tjPackages = tjPackages;
    }

    public String getFocusMPic() {
        return focusMPic;
    }

    public void setFocusMPic(String focusMPic) {
        this.focusMPic = focusMPic;
    }

    public String getPic169() {
        return pic169;
    }

    public void setPic169(String pic169) {
        this.pic169 = pic169;
    }

    @Override
    public String toString() {
        return "SubjectContent{" +
                "description='" + description + '\'' +
                ", name='" + name + '\'' +
                ", pubName='" + pubName + '\'' +
                ", tag='" + tag + '\'' +
                ", tvPic='" + tvPic + '\'' +
                ", focusMPic='" + focusMPic + '\'' +
                ", pic169='" + pic169 + '\'' +
                ", ctime='" + ctime + '\'' +
                ", tjPackages=" + tjPackages +
                ", packageIds=" + packageIds +
                '}';
    }
}
