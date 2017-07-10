package tp.rec.response;

import java.util.List;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;


/**
 * 推荐接口通用返回参数
 * 参数详情参照：http://wiki.letv.cn/pages/viewpage.action?pageId=32708712
 */
@DataObject(generateConverter = true)
public class RecBaseResponse {
    private String reid; // 推荐事件id;每次推荐请求，生成的一个全局唯一的ID
    private String bucket; // 分桶测试编号;
    private String area; // 页面区域
    private List<RecommendDetail> rec; // 推荐结果的列表

    public RecBaseResponse() {
    }

    public RecBaseResponse(JsonObject json) {
        // A converter is generated to easy the conversion from and to JSON.
        RecBaseResponseConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        RecBaseResponseConverter.toJson(this, json);
        return json;
    }

    public String getReid() {
        return reid;
    }

    public void setReid(String reid) {
        this.reid = reid;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public List<RecommendDetail> getRec() {
        return rec;
    }

    public void setRec(List<RecommendDetail> rec) {
        this.rec = rec;
    }

//    public static class RecommendDetail {
//        private Long vid; // 视频id
//        private Long pid; // 专辑id
//        private Integer cid; // 频道id
//        private String title; // 标题
//        private String subtitle; // 副标题
//        private String pidname; // 专辑名称
//        private String playurl; // 播放页地址
//        private String director; // 导演
//        private String starring; // 主演
//        private String actors; // 演员
//        private String singer; // 歌手
//        private String picurl; // 图片地址
//        private String picsize;// 图片尺寸;1、表示图片是专辑图片，eg：album200*150；2、普通的单视频图片尺寸，eg：200*150，180*135等
//        private Integer isend; // 是否已完结 1:完结 0:未完结
//        private Integer vcount; // 如果isend为0，则vcount表示跟播到第几集
//        private Integer episodes; // 总集数
//        private String description; // 视频描述
//        private String createtime; // 上线时间
//        private Integer jump; // 是否可以外跳;取1或者0 ，1表示可以外跳，0表示否
//        private String picHT; // 横图地址;用于移动端，尺寸400*300
//        private String picST; // 竖图地址;用于移动端 ，尺寸150*200
//        private Integer isalbum; // 视频类型;0表示单视频，1表示专辑，该字段一定要保证可以返回
//        private Long duration; // 视频长度
//        private String is_pay; // 是否是付费视频; "1"表示付费视频，"0"表示非付费视频
//        /**
//         * 该视频的悬浮标识，可用于前端醒目显示等
//         * 取值"classical"代表是经典视频
//         * 取值"zhuanti"表示是专题视频
//         * 如果没有该字段，则表示该视频没有悬浮标识需求。
//         */
//        private String float_flag;
//
//        private String video_type; // 标识对应视频或专辑的类型
//
//        private String video_type_name;// 视频类型名称
//
//        /**
//         * 评分
//         */
//        private String score;
//        /**
//         * 专辑地区名称：中国大陆
//         */
//        private String album_area;
//
//        /**
//         * 视频地区名称：中国大陆
//         */
//        private String video_area;
//        /**
//         * 播出电视台：东方卫视
//         */
//        private String album_play_tv;
//
//        /**
//         * 专辑年份：2014-11-01
//         */
//        private String album_release_date;
//
//        private String video_release_date;
//        /**
//         * 专辑子类型:真人秀,旅游,明星,经典
//         */
//        private String album_sub_category;
//
//        private String video_sub_category;
//        /**
//         * 嘉宾
//         */
//        private String guest;
//
//        private Map<String, Map<String, String>> picCollections;
//
//        /**TV有用**/
//        private String album_sub_category_code;
//
//        private String album_play_platform;
//
//        private String video_play_platform;// 视频的版权方信息
//
//        @JsonProperty("pic400*250")
//        private String pic400_250;
//
//        @JsonProperty("pic400*300")
//        private String pic400_300;
//        /*
//         * 取固定尺寸的图片,如果没有返回空
//         */
////        public String getImageBySize(Integer width, Integer height , String terminalApplication) {
////            if(StringUtils.equals(terminalApplication, CommonConstants.TERMINALAPPLICATION.LECOM)){
////                return this.pic400_250;
////            }else if(StringUtils.equals(terminalApplication, CommonConstants.TERMINALAPPLICATION.TVLE)){
////                return this.pic400_300;
////            }else{
////                if (this.picCollections != null && width != null && height != null) {
////                    Collection<Map<String, String>> pics = this.picCollections.values();
////                    if (pics != null && pics.size() > 0) {
////                        String pic = null;
////                        for (Map<String, String> picMap : pics) {
////                            if (picMap != null) {
////                                pic = picMap.get(width + "*" + height);
////                                if (pic != null) {
////                                    return pic;
////                                }
////                            }
////                        }
////                    }
////                }
////            }
////            return null;
////        }
//
//        public String getPic400_300() {
//            return pic400_300;
//        }
//
//        public void setPic400_300(String pic400_300) {
//            this.pic400_300 = pic400_300;
//        }
//
//        public String getPic400_250() {
//            return pic400_250;
//        }
//
//
//        public void setPic400_250(String pic400_250) {
//            this.pic400_250 = pic400_250;
//        }
//
//
//        public Map<String, Map<String, String>> getPicCollections() {
//            return picCollections;
//        }
//
//        public void setPicCollections(Map<String, Map<String, String>> picCollections) {
//            this.picCollections = picCollections;
//        }
//
//        public Long getVid() {
//            return vid;
//        }
//
//        public void setVid(Long vid) {
//            this.vid = vid;
//        }
//
//        public Long getPid() {
//            return pid;
//        }
//
//        public void setPid(Long pid) {
//            this.pid = pid;
//        }
//
//        public Integer getCid() {
//            return cid;
//        }
//
//        public void setCid(Integer cid) {
//            this.cid = cid;
//        }
//
//        public String getTitle() {
//            return title;
//        }
//
//        public void setTitle(String title) {
//            this.title = title;
//        }
//
//        public String getSubtitle() {
//            return subtitle;
//        }
//
//        public void setSubtitle(String subtitle) {
//            this.subtitle = subtitle;
//        }
//
//        public String getPidname() {
//            return pidname;
//        }
//
//        public void setPidname(String pidname) {
//            this.pidname = pidname;
//        }
//
//        public String getPlayurl() {
//            return playurl;
//        }
//
//        public void setPlayurl(String playurl) {
//            this.playurl = playurl;
//        }
//
//        public String getDirector() {
//            return director;
//        }
//
//        public void setDirector(String director) {
//            this.director = director;
//        }
//
//        public String getStarring() {
//            return starring;
//        }
//
//        public void setStarring(String starring) {
//            this.starring = starring;
//        }
//
//        public String getActors() {
//            return actors;
//        }
//
//        public void setActors(String actors) {
//            this.actors = actors;
//        }
//
//        public String getSinger() {
//            return singer;
//        }
//
//        public void setSinger(String singer) {
//            this.singer = singer;
//        }
//
//        public String getPicurl() {
//            return picurl;
//        }
//
//        public void setPicurl(String picurl) {
//            this.picurl = picurl;
//        }
//
//        public String getPicsize() {
//            return picsize;
//        }
//
//        public void setPicsize(String picsize) {
//            this.picsize = picsize;
//        }
//
//        public Integer getIsend() {
//            return isend;
//        }
//
//        public void setIsend(Integer isend) {
//            this.isend = isend;
//        }
//
//        public Integer getVcount() {
//            return vcount;
//        }
//
//        public void setVcount(Integer vcount) {
//            this.vcount = vcount;
//        }
//
//        public Integer getEpisodes() {
//            return episodes;
//        }
//
//        public void setEpisodes(Integer episodes) {
//            this.episodes = episodes;
//        }
//
//        public String getDescription() {
//            return description;
//        }
//
//        public void setDescription(String description) {
//            this.description = description;
//        }
//
//        public String getCreatetime() {
//            return createtime;
//        }
//
//        public void setCreatetime(String createtime) {
//            this.createtime = createtime;
//        }
//
//        public Integer getJump() {
//            return jump;
//        }
//
//        public void setJump(Integer jump) {
//            this.jump = jump;
//        }
//
//        public String getPicHT() {
//            return picHT;
//        }
//
//        public void setPicHT(String picHT) {
//            this.picHT = picHT;
//        }
//
//        public String getPicST() {
//            return picST;
//        }
//
//        public void setPicST(String picST) {
//            this.picST = picST;
//        }
//
//        public Integer getIsalbum() {
//            return isalbum;
//        }
//
//        public void setIsalbum(Integer isalbum) {
//            this.isalbum = isalbum;
//        }
//
//        public Long getDuration() {
//            return duration;
//        }
//
//        public void setDuration(Long duration) {
//            this.duration = duration;
//        }
//
//        public String getIs_pay() {
//            return is_pay;
//        }
//
//        public void setIs_pay(String is_pay) {
//            this.is_pay = is_pay;
//        }
//
//        public String getFloat_flag() {
//            return float_flag;
//        }
//
//        public void setFloat_flag(String float_flag) {
//            this.float_flag = float_flag;
//        }
//
//        public String getVideo_type() {
//            return video_type;
//        }
//
//        public void setVideo_type(String video_type) {
//            this.video_type = video_type;
//        }
//
//        public String getScore() {
//            return score;
//        }
//
//        public void setScore(String score) {
//            this.score = score;
//        }
//
//        public String getAlbum_area() {
//            return album_area;
//        }
//
//        public void setAlbum_area(String album_area) {
//            this.album_area = album_area;
//        }
//
//        public String getVideo_area() {
//            return video_area;
//        }
//
//        public void setVideo_area(String video_area) {
//            this.video_area = video_area;
//        }
//
//        public String getAlbum_play_tv() {
//            return album_play_tv;
//        }
//
//        public void setAlbum_play_tv(String album_play_tv) {
//            this.album_play_tv = album_play_tv;
//        }
//
//        public String getAlbum_release_date() {
//            return album_release_date;
//        }
//
//        public void setAlbum_release_date(String album_release_date) {
//            this.album_release_date = album_release_date;
//        }
//
//        public String getVideo_release_date() {
//            return video_release_date;
//        }
//
//        public void setVideo_release_date(String video_release_date) {
//            this.video_release_date = video_release_date;
//        }
//
//        public String getAlbum_sub_category() {
//            return album_sub_category;
//        }
//
//        public void setAlbum_sub_category(String album_sub_category) {
//            this.album_sub_category = album_sub_category;
//        }
//
//        public String getVideo_sub_category() {
//            return video_sub_category;
//        }
//
//        public void setVideo_sub_category(String video_sub_category) {
//            this.video_sub_category = video_sub_category;
//        }
//
//        public String getGuest() {
//            return guest;
//        }
//
//        public void setGuest(String guest) {
//            this.guest = guest;
//        }
//
//        public String getVideo_type_name() {
//            return video_type_name;
//        }
//
//        public void setVideo_type_name(String video_type_name) {
//            this.video_type_name = video_type_name;
//        }
//
//        public String getAlbum_sub_category_code() {
//            return album_sub_category_code;
//        }
//
//        public void setAlbum_sub_category_code(String album_sub_category_code) {
//            this.album_sub_category_code = album_sub_category_code;
//        }
//
//        public String getAlbum_play_platform() {
//            return album_play_platform;
//        }
//
//        public void setAlbum_play_platform(String album_play_platform) {
//            this.album_play_platform = album_play_platform;
//        }
//
//        public String getVideo_play_platform() {
//            return video_play_platform;
//        }
//
//        public void setVideo_play_platform(String video_play_platform) {
//            this.video_play_platform = video_play_platform;
//        }
//    }

}
