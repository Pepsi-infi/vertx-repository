package service.dto.cmsPage;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

import java.util.List;

@DataObject(generateConverter = true)
public class CmsCategoryDto {
    private Integer id;
    private String categoryId; // 分类id
    private String categoryName; // 分类名称
    private Integer dataSource;// 数据来源：1cms ,2直播部门
    private Integer dataType;// 1直播数据、2频道数据,3.直播、频道混合
    private Integer isPersonalizedSort = 0; //是够个性化排序（0：非个性化排序 1：个性化排序），如果为0即非个性化排序时需要自排序
    private List<CmsChannelDto> channelList; // 分类下的频道列表

//    private String categoryType; // 分类数据类型
//    private String parentCgId; // 上级分类id;
//    private String parentCgName; // 上级分类名称
//    private String categoryClient; // 分类所属的版权方
//    private Map<String, String> categoryMultiTitle; // 分类名称的多语言
//    private String categoryPic; // 分类图片
//    private String color; // 分类的背景色
//    private String placeHolder; // 是否为占位分类
//    private Integer liveCounts;// 如果是直播分类，显示正在直播数
//    private Integer startIndex; // 首屏数据index(直播分类)
//    private Integer contentManulNum; //手动条数
//    private Integer contentTotal; //总条数
//    private Integer manualNum; //手动条数
//    private Integer templateId;//模板ID(CMS栏目的输出样式)
//    private String categoryOnFocusPic; //栏目选中时的图片
//    private Integer contentType; //栏目配置方式

    public CmsCategoryDto() {
    }

    public CmsCategoryDto(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        CmsCategoryDtoConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        CmsCategoryDtoConverter.toJson(this, json);
        return json;
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public Integer getDataSource() {
        return dataSource;
    }

    public void setDataSource(Integer dataSource) {
        this.dataSource = dataSource;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }


    public List<CmsChannelDto> getChannelList() {
        return channelList;
    }

    public void setChannelList(List<CmsChannelDto> channelList) {
        this.channelList = channelList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIsPersonalizedSort() {
        return isPersonalizedSort;
    }

    public void setIsPersonalizedSort(Integer isPersonalizedSort) {
        this.isPersonalizedSort = isPersonalizedSort;
    }


    @Override
    public String toString() {
        return "CmsCategoryDto [id=" + id + ", categoryId=" + categoryId + ", categoryName=" + categoryName
                + ", dataSource=" + dataSource + ", dataType=" + dataType + ", isPersonalizedSort=" + isPersonalizedSort
                + ", channelList=" + channelList + "]";
    }
    
    

}
