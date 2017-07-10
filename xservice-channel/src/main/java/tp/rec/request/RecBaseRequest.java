package tp.rec.request;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * 调用推荐接口的基本参数列表
 * 参数详细说说明参见http://wiki.letv.cn/pages/viewpage.action?pageId=32708712
 * @author hongqin
 */
@DataObject(generateConverter = true)//对于那些不支持的数据类型，必须使用 @DataObject 注解作为约束
public class RecBaseRequest {
    public static String REQ_HOST;
    private static final String REQ_URL="";
    private String uid; // 登录用户id
    private String lc; // 匿名用户id
    private Integer cid;// 频道id
    private String type; // 限定推荐的视频类型
    private Long pid; // 专辑id
    private Long vid; // 视频id
    /**
     * pc网站：0001，
     * pc客户端：0002，
     * 移动端：0003，
     * tv端：0004，
     * pc端百度视频合作：0005，
     * M站：0006，
     * 超级手机：0007
     * ，pad端：0008
     */
    private String pt = "0003";// 播放平台
    private String pageid; // 页面id
    private String area; // 页面区域
    private String jsonp; // 回调函数名称;
    private Double random; // 随机数
    private Integer feedback; // 用户反馈记录;1表示正反馈，加心操作等；0表示用户未反馈；-1表示负反馈，如用户点击了不喜欢标志等。
    private Long playtime; // 观看时长;轮播台推荐在使用该字段,表示用户对当前视频的观看时间
    private Long totaltime;// 视频总时长;轮播台推荐在使用该字段,表示当前视频总时长
    /**
     * 传递用户最近观看的10个视频，按历史时刻顺序，vid1是最新观看的视频，格式如下：
     * "vid1-vid2-vid3-vid4-vid5-vid6-vid7-vid8-vid9-vid10"
     */
    private String history; // 用户观看历史;
    private Integer num;// 指定返回的推荐结果的条数
    private String region; // 地区标识; cn
    private String lang; // 语言标识; zh_cn
    private String citylevel; // 标识用户所在城市的级别，例如：1，2，3等等
    private String city; // 同时传递省和城市的编号，用小横杠"-"连接，例如：1-0，1标识省编号，0标识城市编号
    private Integer bc; // 传递播控平台编号，其中 letv = 0;cntv= 1;cibn= 2;wasu= 3;
    private String mpt = "420003_1"; // 区分移动端Android和IOS，例如:420003 420003_1 420003_2(超级手机)等等
    private Boolean is_rec; // 视频是否为推荐数据;true为推荐数据，非推荐数据无此字段
    private String version = "mobile_lead"; // 用于给推荐标识调用者为领先版

    private Integer page_num;// 分页页码

    private String rom_country;//rom里烧制的地区
    private String user_country;//用户选择的地区

    //以下三个参数lecom必传,详见LecomRecRequest
    private String versiontype;
    private String action;
    private Integer disable_record_exposure;

    private String serverTerminal;//终端应用

    public RecBaseRequest() {
    }


    public RecBaseRequest(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        RecBaseRequestConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        RecBaseRequestConverter.toJson(this, json);
        return json;
    }

    /**
     * 将本对象的变量拼接成url参数列表的字符串
     * 字符串开头不包含&符号
     * @return
     */


    public String build() {
        try {
            StringBuilder subUrl = new StringBuilder();
            Field[] fields = this.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ((field.getModifiers() & Modifier.STATIC) == Modifier.STATIC
                        || (field.getModifiers() & Modifier.FINAL) == Modifier.FINAL) {
                    continue;
                }
                Object value = field.get(this);
                if (value != null && !"".equals(value.toString())) {
                    // 推荐只有zh_hk的数据，无论语言是什么，香港版的lang字段都只能传zh_hk
                    if ("lang".equals(field.getName()) && "hk".equals(this.region)) {
                        subUrl.append("&lang=zh_hk");
                    } else {
                        subUrl.append("&").append(field.getName()).append("=").append(value);
                    }
                }
            }
            String param = subUrl.toString();
            if (param != null && param.startsWith("&")) {
                param = param.substring(1);
            }
//            return param;
            return REQ_URL + "?" + param;
        } catch (Exception e) {
            return REQ_URL + "?" + this.toString();
//            return this.toString();
        }
    }

    @Override
    public String toString() {
        return "uid=" + uid + "&lc=" + lc + "&cid=" + cid + "&type=" + type + "&pid=" + pid + "&vid=" + vid + "&pt="
                + pt + "&pageid=" + pageid + "&area=" + area + "&jsonp=" + jsonp + "&random=" + random + "&feedback="
                + feedback + "&playtime=" + playtime + "&totaltime=" + totaltime + "&history=" + history + "&num="
                + num + "&region=" + region + "&lang=" + lang + "&citylevel=" + citylevel + "&city=" + city + "&bc="
                + bc + "&mpt=" + mpt + "&is_rec=" + is_rec;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getLc() {
        return lc;
    }

    public void setLc(String lc) {
        this.lc = lc;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public Long getVid() {
        return vid;
    }

    public void setVid(Long vid) {
        this.vid = vid;
    }

    public String getPt() {
        return pt;
    }

    public void setPt(String pt) {
        this.pt = pt;
    }

    public String getPageid() {
        return pageid;
    }

    public void setPageid(String pageid) {
        this.pageid = pageid;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getJsonp() {
        return jsonp;
    }

    public void setJsonp(String jsonp) {
        this.jsonp = jsonp;
    }

    public Double getRandom() {
        return random;
    }

    public void setRandom(Double random) {
        this.random = random;
    }

    public Integer getFeedback() {
        return feedback;
    }

    public void setFeedback(Integer feedback) {
        this.feedback = feedback;
    }

    public Long getPlaytime() {
        return playtime;
    }

    public void setPlaytime(Long playtime) {
        this.playtime = playtime;
    }

    public Long getTotaltime() {
        return totaltime;
    }

    public void setTotaltime(Long totaltime) {
        this.totaltime = totaltime;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getCitylevel() {
        return citylevel;
    }

    public void setCitylevel(String citylevel) {
        this.citylevel = citylevel;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getBc() {
        return bc;
    }

    public void setBc(Integer bc) {
        this.bc = bc;
    }

    public String getMpt() {
        return mpt;
    }

    public void setMpt(String mpt) {
        this.mpt = mpt;
    }

    public Boolean getIs_rec() {
        return is_rec;
    }

    public void setIs_rec(Boolean is_rec) {
        this.is_rec = is_rec;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Integer getPage_num() {
        return page_num;
    }

    public void setPage_num(Integer page_num) {
        this.page_num = page_num;
    }

    public String getRom_country() {
        return rom_country;
    }

    public void setRom_country(String rom_country) {
        this.rom_country = rom_country;
    }

    public String getUser_country() {
        return user_country;
    }

    public void setUser_country(String user_country) {
        this.user_country = user_country;
    }

    public String getVersiontype() {
        return versiontype;
    }

    public void setVersiontype(String versiontype) {
        this.versiontype = versiontype;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Integer getDisable_record_exposure() {
        return disable_record_exposure;
    }

    public void setDisable_record_exposure(Integer disable_record_exposure) {
        this.disable_record_exposure = disable_record_exposure;
    }

    public String getServerTerminal() {
        return serverTerminal;
    }

    public void setServerTerminal(String serverTerminal) {
        this.serverTerminal = serverTerminal;
    }

    //    @Override
//    public String sign() {
//        return REQ_URL;
//    }

}
