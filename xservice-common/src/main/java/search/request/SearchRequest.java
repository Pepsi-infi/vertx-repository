package search.request;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;


/**
 * 搜索通用参数
 */
@DataObject(generateConverter = true)
public class SearchRequest {
//    private static final String REQ_URL = ApplicationUtils.get(ApplicationConstants.SEARCH_RESULT_URL);
    public static String REQ_HOST="le.so.letv.com";
    private static final String REQ_URL = "/interface";
    public final static String SEARCH_PARAM_SRC_LETV = "1"; // src参数 乐视版权的内容
    public final static String SEARCH_PARAM_SRC_WEB = "2"; // src参数 外网版权的内容
    public final static String SEARCH_PARAM_STT_ASC = "0"; // stt参数，按照升序排序
    public final static String SEARCH_PARAM_STT_DSC = "1"; // stt参数，按照倒序排序

    private String extraParam; // 考虑到搜索参数扩展，本class未定义到的参数可以传入到此字段中 字段格式为
                               // param1=value1&param2=value2&param3=value3.....

    private Integer pn = 1; // 分页，第几页
    private Integer ps = 30; // 分页，页大小

    private String dt; // 请求数据类型&1:专辑 2:视频 3:明星 4:专题
    private String wd; // 根据关键字进行搜索
    private String stype = "1"; // 搜索类型
                                // 1:有query搜索，如果根据wd进行搜索，则不能传入此参数.根据检索条件进行过滤时，此参数传入1
                                // setWd()函数中，如果wd不为空，会将此字段置为1
    private String sf; // 搜索域，搜索传入wd时，可以指定搜索域，搜索会在这个域中进行wd搜索
    private String hl = "0"; // 是否漂红 0:不漂红 1:漂红
                             // 前端展示时，字体会有变色，加粗这种特效，搜索返回的字段中会有特殊标识
                             // 移动端不需要这种特效，设置为0，搜索返回原有数据格式，不加特殊标识符
    private String or; // 搜索排序方式 例如 按照创建时间排序 按照更新时间排序
    private String stt = SEARCH_PARAM_STT_DSC; // 排序规则 1:倒序 0:升序
    private String ph; // 推送平台过滤 例如420003_1 420003
    private String jf = "1"; // 搜索返回展示格式 //0:分散显示模式 1:PC中data_list方式 2：ranking模式
                             // 3：明星详情模式
                             // 4：doc_info和ds全打印模式
    private String mix = "1"; // 搜索结果时候混合到一块
                              // 如果混合，结果中的专辑，视频，明星，专题会统一放在一个data_list中

    private Integer cg; // 频道id
    private String src; // 数据来源 1:乐视网视频 为空代表全网搜索
    private String sc; // 子分类检索
    private String area; // 地区检索
    private String releaseYearDecade; // 年份检索
    private String fitAge; // 按照年龄检索
    private String vtp; // 视频类型检索
    private String popStyle; // 专辑风格检索
    private String tvid; // 根据电视台检索
    private String playStreamFeatures; // 按照码流检索
    private String isEnd; // 是否完结检索
    private String language; // 根据语言检索
    private String singerType; // 根据歌手类型检索
    private String isHomemade; // 是否自制检索
    private String tag; // 根据标签检索
    private String style; // 根据风格检索
    private String dur; // 根据视频时长检索 1：1-10分钟 2：10-30分钟 3：30-60分钟 4：60分钟以上
    private String coopPlatform; // 根据合作平台进行检索

    private String region = "cn"; // 区域 例如 cn hk
    private String lang = "zh_cn"; // 语言 例如zh_cn zh_hk

    private String from = "mobile_04"; // 搜索统计参数，搜索来源 mobile_04代表超级手机
    private String lc; // 搜索统计使用参数，客户端的唯一标识
    private String uid; // 搜索统计使用参数,用户的uid
    private String version; // 搜索统计使用参数，客户端的版本
    private String client_ip; // 搜索统计使用参数，客户端的ip地址
    private String splatid; //超级live子平台id 内网1036,外网1048，领先版1046
    private String token; // 用户登录的token
    /**
     * city_info= country_provinceid_districtid_citylevel
     Country:国家代码
     provinceid:省代码
     Districtid:区代码
     Citylevel:城市等级
     比如：city_info=CN_0_0_1  表示中国的一线城市。CN_0_0_2 表示二线城市。
     */
    private String city_info;

    // vipIds:productid以逗号隔开,lecom的addon新增以下四个参数
    private String vipIds;
    private String sales_area;
    private String user_setting_country;
    private Integer repo_type;
    private Integer displayAppId;
    private Integer displayPlatformId;


    public SearchRequest() {
    }

    public SearchRequest(JsonObject json) {
        SearchRequestConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        SearchRequestConverter.toJson(this, json);
        return json;
    }

    /**
     * 将本对象的变量拼接成url参数列表的字符串
     * 字符串开头不包含&符号
     * @return
     */
//    @Override
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
                    subUrl.append("&");
                    // extraParam较为特殊，里面等格式就是拼接好的搜索参数字符串
                    boolean bool = "extraParam".equals(field.getName());
                    if (!bool) {
                        subUrl.append(field.getName()).append("=");
                    }
                    subUrl.append(value);
                }
            }
            String param = subUrl.toString();
            if (param != null && param.startsWith("&")) {
                param = param.substring(1);
            }
            return REQ_URL + "?" + param;
        } catch (Exception e) {
            return REQ_URL + "?" + this.toString();
        }
    }

    @Override
    public String toString() {
        return "extraParam=" + extraParam + "&pn=" + pn + "&ps=" + ps + "&dt=" + dt + "&wd=" + wd + "&stype=" + stype
                + "&sf=" + sf + "&hl=" + hl + "&or=" + or + "&stt=" + stt + "&ph=" + ph + "&jf=" + jf + "&mix=" + mix
                + "&cg=" + cg + "&src=" + src + "&sc=" + sc + "&area=" + area + "&releaseYearDecade="
                + releaseYearDecade + "&fitAge=" + fitAge + "&vtp=" + vtp + "&popStyle=" + popStyle + "&tvid=" + tvid
                + "&playStreamFeatures=" + playStreamFeatures + "&isEnd=" + isEnd + "&language=" + language
                + "&singerType=" + singerType + "&isHomemade=" + isHomemade + "&tag=" + tag + "&style=" + style
                + "&dur=" + dur + "&coopPlatform=" + coopPlatform + "&region=" + region + "&lang=" + lang + "&from="
                + from + "&lc=" + lc + "&uid=" + uid + "&version=" + version + "&client_ip=" + client_ip + "]";
    }

    public String getExtraParam() {
        return extraParam;
    }

    public void setExtraParam(String extraParam) {
        this.extraParam = extraParam;
    }

    public Integer getPn() {
        return pn;
    }

    public void setPn(Integer pn) {
        this.pn = pn;
    }

    public String getSplatid() {
        return splatid;
    }

    public void setSplatid(String splatid) {
        this.splatid = splatid;
    }

    public Integer getPs() {
        return ps;
    }

    public void setPs(Integer ps) {
        this.ps = ps;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public String getWd() {
        return wd;
    }

    public void setWd(String wd) {
        this.wd = wd;
        // wd为空，则搜索需要按照条件过滤，属于有query检索，stype需要置为1
        if (wd != null && !"".equals(wd)) {
            this.stype = null;
        }
    }

    public String getStype() {
        return stype;
    }

    public void setStype(String stype) {
        this.stype = stype;
    }

    public String getSf() {
        return sf;
    }

    public void setSf(String sf) {
        this.sf = sf;
    }

    public String getHl() {
        return hl;
    }

    public void setHl(String hl) {
        this.hl = hl;
    }

    public String getOr() {
        return or;
    }

    public void setOr(String or) {
        this.or = or;
    }

    public String getStt() {
        return stt;
    }

    public void setStt(String stt) {
        this.stt = stt;
    }

    public String getPh() {
        return ph;
    }

    public void setPh(String ph) {
        this.ph = ph;
    }

    public String getJf() {
        return jf;
    }

    public void setJf(String jf) {
        this.jf = jf;
    }

    public String getMix() {
        return mix;
    }

    public void setMix(String mix) {
        this.mix = mix;
    }

    public Integer getCg() {
        return cg;
    }

    public void setCg(Integer cg) {
        this.cg = cg;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getSc() {
        return sc;
    }

    public void setSc(String sc) {
        this.sc = sc;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getReleaseYearDecade() {
        return releaseYearDecade;
    }

    public void setReleaseYearDecade(String releaseYearDecade) {
        this.releaseYearDecade = releaseYearDecade;
    }

    public String getFitAge() {
        return fitAge;
    }

    public void setFitAge(String fitAge) {
        this.fitAge = fitAge;
    }

    public String getVtp() {
        return vtp;
    }

    public void setVtp(String vtp) {
        this.vtp = vtp;
    }

    public String getPopStyle() {
        return popStyle;
    }

    public void setPopStyle(String popStyle) {
        this.popStyle = popStyle;
    }

    public String getTvid() {
        return tvid;
    }

    public void setTvid(String tvid) {
        this.tvid = tvid;
    }

    public String getPlayStreamFeatures() {
        return playStreamFeatures;
    }

    public void setPlayStreamFeatures(String playStreamFeatures) {
        this.playStreamFeatures = playStreamFeatures;
    }

    public String getIsEnd() {
        return isEnd;
    }

    public void setIsEnd(String isEnd) {
        this.isEnd = isEnd;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getSingerType() {
        return singerType;
    }

    public void setSingerType(String singerType) {
        this.singerType = singerType;
    }

    public String getIsHomemade() {
        return isHomemade;
    }

    public void setIsHomemade(String isHomemade) {
        this.isHomemade = isHomemade;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getDur() {
        return dur;
    }

    public void setDur(String dur) {
        this.dur = dur;
    }

    public String getCoopPlatform() {
        return coopPlatform;
    }

    public void setCoopPlatform(String coopPlatform) {
        this.coopPlatform = coopPlatform;
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

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getLc() {
        return lc;
    }

    public void setLc(String lc) {
        this.lc = lc;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getClient_ip() {
        return client_ip;
    }

    public void setClient_ip(String client_ip) {
        this.client_ip = client_ip;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCity_info() {
        return city_info;
    }

    public void setCity_info(String city_info) {
        this.city_info = city_info;
    }


    public String getSales_area() {
        return sales_area;
    }

    public void setSales_area(String sales_area) {
        this.sales_area = sales_area;
    }

    public String getUser_setting_country() {
        return user_setting_country;
    }

    public void setUser_setting_country(String user_setting_country) {
        this.user_setting_country = user_setting_country;
    }

    public String getVipIds() {
        return vipIds;
    }

    public void setVipIds(String vipIds) {
        this.vipIds = vipIds;
    }

    public Integer getRepo_type() {
        return repo_type;
    }

    public void setRepo_type(Integer repo_type) {
        this.repo_type = repo_type;
    }

    public Integer getDisplayAppId() {
        return displayAppId;
    }

    public void setDisplayAppId(Integer displayAppId) {
        this.displayAppId = displayAppId;
    }

    public Integer getDisplayPlatformId() {
        return displayPlatformId;
    }

    public void setDisplayPlatformId(Integer displayPlatformId) {
        this.displayPlatformId = displayPlatformId;
    }


    public Map<String,String> getParamsMap() {
        try {
            Map<String,String> filter_param = new HashMap<>();
            Field[] fields = this.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ((field.getModifiers() & Modifier.STATIC) == Modifier.STATIC
                        || (field.getModifiers() & Modifier.FINAL) == Modifier.FINAL) {
                    continue;
                }
                Object value = field.get(this);
                if (value != null && !"".equals(value.toString())) {
                    boolean bool = "extraParam".equals(field.getName());
                    if (!bool) {
                        filter_param.put(field.getName(),value.toString());
                    } else {
                        String [] extraParamM = value.toString().split("&");
                        for(String param : extraParamM){
                            String[] subparam = param.split("=");
                            if(subparam.length == 2 && !"null".equals(subparam[1])){
                                filter_param.put(subparam[0],subparam[1]);
                            }
                        }
                    }
                }
            }

            return filter_param;
        } catch (Exception e) {
            return null;
        }
    }

}
