package service.dto.channelPage;

import constants.ChannelConstants;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.List;

/**
 * Created by zhushenghao1 on 16/12/27.
 */
@DataObject(generateConverter = true,inheritConverter = true)
public class ChannelBlockDto {

    public static final String CHANNEL_BLOCK_SKIP_FILTR = "1";//检索
    public static final String CHANNLE_BLOCK_SKIP_PAGE = "2";//首页

    private String name; // 模块名称
    private String cid; // 模块频道id
    private String blockid; //内容版块id

    private String rectCid; // 模块跳转，需要跳转的频道id
    private String rectCName; // 模块跳转,需要跳转的频道名称
    private String rectPageId; // 模块跳转，需要跳转到的pageid(每个导航标签都有一个pageid)
    private String rectType; // 模块跳转类型 1: 跳转条件筛选 2: 跳转导航标签
    private String rectUrl; // 模块跳转，需要跳转到的url
    private String rectVt;
    private String dataUrl; // 跳转频道的URL
    private List<RectRetrieve> rectField; // 跳转条件检索对应的检索条件
    /**
     * 模块展现样式，客户端会根据这个字段，决定以何种样式展示模块数据
     * 1. 焦点图
     * 2. 直播通栏
     * 3. 无标题推荐
     * 4. 有标题有更多推荐
     * 5. 有标题无更多推荐
     * 6. 乐看搜索
     * 7. 图片频道墙
     * 8. icon频道墙
     * 9. 二级导航
     * 10. 视频列表
     * 11. 检索表
     * 12. 检索视频列表
     * 13. 重磅
     * 14. 热词
     * 26. 1大图4小图，有更多---领先版使用
     * 27. 1大图4小图，无更多---领先版使用
     * 28. 图文列表---领先版使用
     * 29. 4张小图，有更多---领先版使用
     * 30. 4张小图，无更多---领先版使用
     */
    private String style;
    private List<BaseDto> list; // 模块下的默认数据
    private Integer total;
    private Integer curPage;
    private Integer nextPage;

    private String recFragId;// 推荐数据上报字段
    private String recReid; // 推荐数据上报字段
    private String recArea;// 推荐数据上报字段
    private String recBucket;// 推荐数据上报字段
    private String recSrcType;//推荐数据上报字段，数据来自何种推荐 【人工推荐：editor； 自动推荐：auto；混合推荐：mix】
    private String blockType;

    public ChannelBlockDto(){}

    public ChannelBlockDto(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        ChannelBlockDtoConverter.fromJson(json, this);
        if (json.getValue("list") instanceof JsonArray) {
            java.util.ArrayList<BaseDto> list = new java.util.ArrayList<>();
            json.getJsonArray("list").forEach( item -> {
                if (item instanceof JsonObject){
                    switch (json.getString("blockType")){
                        case ChannelConstants.DATA_TYPE_CHANNEL:
                            list.add(new BlockDataDto((JsonObject)item));
                            break;
                        case ChannelConstants.DATA_TYPE_ADDON:
                            list.add(new AddOnInfoDto((JsonObject)item));
                            break;
                        default:
                            list.add(new BaseDto((JsonObject)item));
                            break;
                    }
                }
            });
            this.setList(list);
        }
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        ChannelBlockDtoConverter.toJson(this, json);
        return json;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getBlockid() {
        return blockid;
    }

    public void setBlockid(String blockid) {
        this.blockid = blockid;
    }

    public String getRectCid() {
        return rectCid;
    }

    public void setRectCid(String rectCid) {
        this.rectCid = rectCid;
    }

    public String getRectCName() {
        return rectCName;
    }

    public void setRectCName(String rectCName) {
        this.rectCName = rectCName;
    }

    public String getRectPageId() {
        return rectPageId;
    }

    public void setRectPageId(String rectPageId) {
        this.rectPageId = rectPageId;
    }

    public String getRectType() {
        return rectType;
    }

    public void setRectType(String rectType) {
        this.rectType = rectType;
    }

    public String getRectUrl() {
        return rectUrl;
    }

    public void setRectUrl(String rectUrl) {
        this.rectUrl = rectUrl;
    }

    public String getRectVt() {
        return rectVt;
    }

    public void setRectVt(String rectVt) {
        this.rectVt = rectVt;
    }

    public String getDataUrl() {
        return dataUrl;
    }

    public void setDataUrl(String dataUrl) {
        this.dataUrl = dataUrl;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getRecFragId() {
        return recFragId;
    }

    public void setRecFragId(String recFragId) {
        this.recFragId = recFragId;
    }

    public String getRecReid() {
        return recReid;
    }

    public void setRecReid(String recReid) {
        this.recReid = recReid;
    }

    public String getRecArea() {
        return recArea;
    }

    public void setRecArea(String recArea) {
        this.recArea = recArea;
    }

    public String getRecBucket() {
        return recBucket;
    }

    public void setRecBucket(String recBucket) {
        this.recBucket = recBucket;
    }

    public String getRecSrcType() {
        return recSrcType;
    }

    public void setRecSrcType(String recSrcType) {
        this.recSrcType = recSrcType;
    }

    public List<BaseDto> getList() {
        return list;
    }

    public void setList(List<BaseDto> list) {
        this.list = list;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getCurPage() {
        return curPage;
    }

    public void setCurPage(Integer curPage) {
        this.curPage = curPage;
    }

    public Integer getNextPage() {
        return nextPage;
    }

    public void setNextPage(Integer nextPage) {
        this.nextPage = nextPage;
    }

    public String getBlockType() {
        return blockType;
    }

    public void setBlockType(String blockType) {
        this.blockType = blockType;
    }

    public List<RectRetrieve> getRectField() {
        return rectField;
    }

    public void setRectField(List<RectRetrieve> rectField) {
        this.rectField = rectField;
    }
}

