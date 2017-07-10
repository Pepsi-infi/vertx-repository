/*
 * Copyright 2014 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package tp.rec.response;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;

/**
 * Converter for {@link tp.rec.response.RecData}.
 *
 * NOTE: This class has been automatically generated from the {@link tp.rec.response.RecData} original class using Vert.x codegen.
 */
public class RecDataConverter {

  public static void fromJson(JsonObject json, RecData obj) {
    if (json.getValue("album_area") instanceof String) {
      obj.setAlbum_area((String)json.getValue("album_area"));
    }
    if (json.getValue("album_release_date") instanceof String) {
      obj.setAlbum_release_date((String)json.getValue("album_release_date"));
    }
    if (json.getValue("album_sub_category") instanceof String) {
      obj.setAlbum_sub_category((String)json.getValue("album_sub_category"));
    }
    if (json.getValue("album_type") instanceof String) {
      obj.setAlbum_type((String)json.getValue("album_type"));
    }
    if (json.getValue("androidUrl") instanceof String) {
      obj.setAndroidUrl((String)json.getValue("androidUrl"));
    }
    if (json.getValue("area") instanceof String) {
      obj.setArea((String)json.getValue("area"));
    }
    if (json.getValue("bid") instanceof String) {
      obj.setBid((String)json.getValue("bid"));
    }
    if (json.getValue("cgidefault") instanceof String) {
      obj.setCgidefault((String)json.getValue("cgidefault"));
    }
    if (json.getValue("cid") instanceof Number) {
      obj.setCid(((Number)json.getValue("cid")).intValue());
    }
    if (json.getValue("cityLevel") instanceof String) {
      obj.setCityLevel((String)json.getValue("cityLevel"));
    }
    if (json.getValue("cityWhiteList") instanceof String) {
      obj.setCityWhiteList((String)json.getValue("cityWhiteList"));
    }
    if (json.getValue("content") instanceof String) {
      obj.setContent((String)json.getValue("content"));
    }
    if (json.getValue("createtime") instanceof String) {
      obj.setCreatetime((String)json.getValue("createtime"));
    }
    if (json.getValue("ctime") instanceof String) {
      obj.setCtime((String)json.getValue("ctime"));
    }
    if (json.getValue("director") instanceof String) {
      obj.setDirector((String)json.getValue("director"));
    }
    if (json.getValue("duration") instanceof String) {
      obj.setDuration((String)json.getValue("duration"));
    }
    if (json.getValue("endTime") instanceof String) {
      obj.setEndTime((String)json.getValue("endTime"));
    }
    if (json.getValue("episodes") instanceof String) {
      obj.setEpisodes((String)json.getValue("episodes"));
    }
    if (json.getValue("extendJson") instanceof JsonObject) {
      obj.setExtendJson(new cms.response.ExtendJson((JsonObject)json.getValue("extendJson")));
    }
    if (json.getValue("float_flag") instanceof String) {
      obj.setFloat_flag((String)json.getValue("float_flag"));
    }
    if (json.getValue("id") instanceof String) {
      obj.setId((String)json.getValue("id"));
    }
    if (json.getValue("iosUrl") instanceof String) {
      obj.setIosUrl((String)json.getValue("iosUrl"));
    }
    if (json.getValue("is_pay") instanceof String) {
      obj.setIs_pay((String)json.getValue("is_pay"));
    }
    if (json.getValue("is_rec") instanceof String) {
      obj.setIs_rec((String)json.getValue("is_rec"));
    }
    if (json.getValue("isalbum") instanceof String) {
      obj.setIsalbum((String)json.getValue("isalbum"));
    }
    if (json.getValue("isend") instanceof String) {
      obj.setIsend((String)json.getValue("isend"));
    }
    if (json.getValue("jump") instanceof String) {
      obj.setJump((String)json.getValue("jump"));
    }
    if (json.getValue("latest_auto_video_pic") instanceof String) {
      obj.setLatest_auto_video_pic((String)json.getValue("latest_auto_video_pic"));
    }
    if (json.getValue("mobilePic") instanceof String) {
      obj.setMobilePic((String)json.getValue("mobilePic"));
    }
    if (json.getValue("mtime") instanceof String) {
      obj.setMtime((String)json.getValue("mtime"));
    }
    if (json.getValue("padPic") instanceof String) {
      obj.setPadPic((String)json.getValue("padPic"));
    }
    if (json.getValue("pic1") instanceof String) {
      obj.setPic1((String)json.getValue("pic1"));
    }
    if (json.getValue("pic2") instanceof String) {
      obj.setPic2((String)json.getValue("pic2"));
    }
    if (json.getValue("pic320_200") instanceof String) {
      obj.setPic320_200((String)json.getValue("pic320_200"));
    }
    if (json.getValue("pic400_225") instanceof String) {
      obj.setPic400_225((String)json.getValue("pic400_225"));
    }
    if (json.getValue("pic400_300") instanceof String) {
      obj.setPic400_300((String)json.getValue("pic400_300"));
    }
    if (json.getValue("picCollections") instanceof JsonObject) {
      java.util.Map<String, java.lang.Object> map = new java.util.LinkedHashMap<>();
      json.getJsonObject("picCollections").forEach(entry -> {
        if (entry.getValue() instanceof Object)
          map.put(entry.getKey(), entry.getValue());
      });
      obj.setPicCollections(map);
    }
    if (json.getValue("picHT") instanceof String) {
      obj.setPicHT((String)json.getValue("picHT"));
    }
    if (json.getValue("picList") instanceof JsonObject) {
      java.util.Map<String, java.lang.String> map = new java.util.LinkedHashMap<>();
      json.getJsonObject("picList").forEach(entry -> {
        if (entry.getValue() instanceof String)
          map.put(entry.getKey(), (String)entry.getValue());
      });
      obj.setPicList(map);
    }
    if (json.getValue("picST") instanceof String) {
      obj.setPicST((String)json.getValue("picST"));
    }
    if (json.getValue("pid") instanceof Number) {
      obj.setPid(((Number)json.getValue("pid")).longValue());
    }
    if (json.getValue("pidname") instanceof String) {
      obj.setPidname((String)json.getValue("pidname"));
    }
    if (json.getValue("pidsubtitle") instanceof String) {
      obj.setPidsubtitle((String)json.getValue("pidsubtitle"));
    }
    if (json.getValue("playPlatform") instanceof JsonObject) {
      java.util.Map<String, java.lang.String> map = new java.util.LinkedHashMap<>();
      json.getJsonObject("playPlatform").forEach(entry -> {
        if (entry.getValue() instanceof String)
          map.put(entry.getKey(), (String)entry.getValue());
      });
      obj.setPlayPlatform(map);
    }
    if (json.getValue("playcount") instanceof String) {
      obj.setPlaycount((String)json.getValue("playcount"));
    }
    if (json.getValue("playurl") instanceof String) {
      obj.setPlayurl((String)json.getValue("playurl"));
    }
    if (json.getValue("position") instanceof String) {
      obj.setPosition((String)json.getValue("position"));
    }
    if (json.getValue("priority") instanceof String) {
      obj.setPriority((String)json.getValue("priority"));
    }
    if (json.getValue("pushflag") instanceof String) {
      obj.setPushflag((String)json.getValue("pushflag"));
    }
    if (json.getValue("rec_content_type") instanceof String) {
      obj.setRec_content_type((String)json.getValue("rec_content_type"));
    }
    if (json.getValue("remark") instanceof String) {
      obj.setRemark((String)json.getValue("remark"));
    }
    if (json.getValue("score") instanceof Number) {
      obj.setScore(((Number)json.getValue("score")).floatValue());
    }
    if (json.getValue("shorDesc") instanceof String) {
      obj.setShorDesc((String)json.getValue("shorDesc"));
    }
    if (json.getValue("showTag") instanceof String) {
      obj.setShowTag((String)json.getValue("showTag"));
    }
    if (json.getValue("showTagList") instanceof JsonArray) {
      java.util.ArrayList<cms.response.ShowTagList> list = new java.util.ArrayList<>();
      json.getJsonArray("showTagList").forEach( item -> {
        if (item instanceof JsonObject)
          list.add(new cms.response.ShowTagList((JsonObject)item));
      });
      obj.setShowTagList(list);
    }
    if (json.getValue("skipPage") instanceof String) {
      obj.setSkipPage((String)json.getValue("skipPage"));
    }
    if (json.getValue("skipType") instanceof String) {
      obj.setSkipType((String)json.getValue("skipType"));
    }
    if (json.getValue("skipUrl") instanceof String) {
      obj.setSkipUrl((String)json.getValue("skipUrl"));
    }
    if (json.getValue("source") instanceof Number) {
      obj.setSource(((Number)json.getValue("source")).intValue());
    }
    if (json.getValue("starring") instanceof String) {
      obj.setStarring((String)json.getValue("starring"));
    }
    if (json.getValue("startTime") instanceof String) {
      obj.setStartTime((String)json.getValue("startTime"));
    }
    if (json.getValue("subTitle") instanceof String) {
      obj.setSubTitle((String)json.getValue("subTitle"));
    }
    if (json.getValue("subtitle") instanceof String) {
      obj.setSubtitle((String)json.getValue("subtitle"));
    }
    if (json.getValue("tag") instanceof String) {
      obj.setTag((String)json.getValue("tag"));
    }
    if (json.getValue("tagUrl") instanceof String) {
      obj.setTagUrl((String)json.getValue("tagUrl"));
    }
    if (json.getValue("title") instanceof String) {
      obj.setTitle((String)json.getValue("title"));
    }
    if (json.getValue("tvPic") instanceof String) {
      obj.setTvPic((String)json.getValue("tvPic"));
    }
    if (json.getValue("tvUrl") instanceof String) {
      obj.setTvUrl((String)json.getValue("tvUrl"));
    }
    if (json.getValue("type") instanceof String) {
      obj.setType((String)json.getValue("type"));
    }
    if (json.getValue("updatetime") instanceof String) {
      obj.setUpdatetime((String)json.getValue("updatetime"));
    }
    if (json.getValue("url") instanceof String) {
      obj.setUrl((String)json.getValue("url"));
    }
    if (json.getValue("vcount") instanceof String) {
      obj.setVcount((String)json.getValue("vcount"));
    }
    if (json.getValue("versionPlatform") instanceof String) {
      obj.setVersionPlatform((String)json.getValue("versionPlatform"));
    }
    if (json.getValue("vid") instanceof Number) {
      obj.setVid(((Number)json.getValue("vid")).longValue());
    }
    if (json.getValue("vid_episode") instanceof String) {
      obj.setVid_episode((String)json.getValue("vid_episode"));
    }
    if (json.getValue("videoFollowTime") instanceof String) {
      obj.setVideoFollowTime((String)json.getValue("videoFollowTime"));
    }
    if (json.getValue("video_pic") instanceof String) {
      obj.setVideo_pic((String)json.getValue("video_pic"));
    }
    if (json.getValue("video_type") instanceof String) {
      obj.setVideo_type((String)json.getValue("video_type"));
    }
    if (json.getValue("vidsubtitle") instanceof String) {
      obj.setVidsubtitle((String)json.getValue("vidsubtitle"));
    }
    if (json.getValue("vtypeFlag") instanceof String) {
      obj.setVtypeFlag((String)json.getValue("vtypeFlag"));
    }
    if (json.getValue("zid") instanceof Number) {
      obj.setZid(((Number)json.getValue("zid")).longValue());
    }
  }

  public static void toJson(RecData obj, JsonObject json) {
    if (obj.getAlbum_area() != null) {
      json.put("album_area", obj.getAlbum_area());
    }
    if (obj.getAlbum_release_date() != null) {
      json.put("album_release_date", obj.getAlbum_release_date());
    }
    if (obj.getAlbum_sub_category() != null) {
      json.put("album_sub_category", obj.getAlbum_sub_category());
    }
    if (obj.getAlbum_type() != null) {
      json.put("album_type", obj.getAlbum_type());
    }
    if (obj.getAndroidUrl() != null) {
      json.put("androidUrl", obj.getAndroidUrl());
    }
    if (obj.getArea() != null) {
      json.put("area", obj.getArea());
    }
    if (obj.getBid() != null) {
      json.put("bid", obj.getBid());
    }
    if (obj.getCgidefault() != null) {
      json.put("cgidefault", obj.getCgidefault());
    }
    if (obj.getCid() != null) {
      json.put("cid", obj.getCid());
    }
    if (obj.getCityLevel() != null) {
      json.put("cityLevel", obj.getCityLevel());
    }
    if (obj.getCityWhiteList() != null) {
      json.put("cityWhiteList", obj.getCityWhiteList());
    }
    if (obj.getContent() != null) {
      json.put("content", obj.getContent());
    }
    if (obj.getCreatetime() != null) {
      json.put("createtime", obj.getCreatetime());
    }
    if (obj.getCtime() != null) {
      json.put("ctime", obj.getCtime());
    }
    if (obj.getDefaultPic() != null) {
      json.put("defaultPic", obj.getDefaultPic());
    }
    if (obj.getDirector() != null) {
      json.put("director", obj.getDirector());
    }
    if (obj.getDuration() != null) {
      json.put("duration", obj.getDuration());
    }
    if (obj.getEndTime() != null) {
      json.put("endTime", obj.getEndTime());
    }
    if (obj.getEpisodes() != null) {
      json.put("episodes", obj.getEpisodes());
    }
    if (obj.getExtendJson() != null) {
      json.put("extendJson", obj.getExtendJson().toJson());
    }
    if (obj.getFloat_flag() != null) {
      json.put("float_flag", obj.getFloat_flag());
    }
    if (obj.getId() != null) {
      json.put("id", obj.getId());
    }
    if (obj.getIosUrl() != null) {
      json.put("iosUrl", obj.getIosUrl());
    }
    if (obj.getIs_pay() != null) {
      json.put("is_pay", obj.getIs_pay());
    }
    if (obj.getIs_rec() != null) {
      json.put("is_rec", obj.getIs_rec());
    }
    if (obj.getIsalbum() != null) {
      json.put("isalbum", obj.getIsalbum());
    }
    if (obj.getIsend() != null) {
      json.put("isend", obj.getIsend());
    }
    if (obj.getJump() != null) {
      json.put("jump", obj.getJump());
    }
    if (obj.getLatest_auto_video_pic() != null) {
      json.put("latest_auto_video_pic", obj.getLatest_auto_video_pic());
    }
    if (obj.getMobilePic() != null) {
      json.put("mobilePic", obj.getMobilePic());
    }
    if (obj.getMtime() != null) {
      json.put("mtime", obj.getMtime());
    }
    if (obj.getPadPic() != null) {
      json.put("padPic", obj.getPadPic());
    }
    if (obj.getPic1() != null) {
      json.put("pic1", obj.getPic1());
    }
    if (obj.getPic2() != null) {
      json.put("pic2", obj.getPic2());
    }
    if (obj.getPic320_200() != null) {
      json.put("pic320_200", obj.getPic320_200());
    }
    if (obj.getPic400_225() != null) {
      json.put("pic400_225", obj.getPic400_225());
    }
    if (obj.getPic400_300() != null) {
      json.put("pic400_300", obj.getPic400_300());
    }
    if (obj.getPicCollections() != null) {
      JsonObject map = new JsonObject();
      obj.getPicCollections().forEach((key,value) -> map.put(key, value));
      json.put("picCollections", map);
    }
    if (obj.getPicHT() != null) {
      json.put("picHT", obj.getPicHT());
    }
    if (obj.getPicList() != null) {
      JsonObject map = new JsonObject();
      obj.getPicList().forEach((key,value) -> map.put(key, value));
      json.put("picList", map);
    }
    if (obj.getPicST() != null) {
      json.put("picST", obj.getPicST());
    }
    if (obj.getPid() != null) {
      json.put("pid", obj.getPid());
    }
    if (obj.getPidname() != null) {
      json.put("pidname", obj.getPidname());
    }
    if (obj.getPidsubtitle() != null) {
      json.put("pidsubtitle", obj.getPidsubtitle());
    }
    if (obj.getPlayPlatform() != null) {
      JsonObject map = new JsonObject();
      obj.getPlayPlatform().forEach((key,value) -> map.put(key, value));
      json.put("playPlatform", map);
    }
    if (obj.getPlaycount() != null) {
      json.put("playcount", obj.getPlaycount());
    }
    if (obj.getPlayurl() != null) {
      json.put("playurl", obj.getPlayurl());
    }
    if (obj.getPosition() != null) {
      json.put("position", obj.getPosition());
    }
    if (obj.getPriority() != null) {
      json.put("priority", obj.getPriority());
    }
    if (obj.getPushflag() != null) {
      json.put("pushflag", obj.getPushflag());
    }
    if (obj.getRec_content_type() != null) {
      json.put("rec_content_type", obj.getRec_content_type());
    }
    if (obj.getRemark() != null) {
      json.put("remark", obj.getRemark());
    }
    if (obj.getScore() != null) {
      json.put("score", obj.getScore());
    }
    if (obj.getShorDesc() != null) {
      json.put("shorDesc", obj.getShorDesc());
    }
    if (obj.getShowTag() != null) {
      json.put("showTag", obj.getShowTag());
    }
    if (obj.getShowTagList() != null) {
      JsonArray array = new JsonArray();
      obj.getShowTagList().forEach(item -> array.add(item.toJson()));
      json.put("showTagList", array);
    }
    if (obj.getSkipPage() != null) {
      json.put("skipPage", obj.getSkipPage());
    }
    if (obj.getSkipType() != null) {
      json.put("skipType", obj.getSkipType());
    }
    if (obj.getSkipUrl() != null) {
      json.put("skipUrl", obj.getSkipUrl());
    }
    if (obj.getSource() != null) {
      json.put("source", obj.getSource());
    }
    if (obj.getStarring() != null) {
      json.put("starring", obj.getStarring());
    }
    if (obj.getStartTime() != null) {
      json.put("startTime", obj.getStartTime());
    }
    if (obj.getSubTitle() != null) {
      json.put("subTitle", obj.getSubTitle());
    }
    if (obj.getSubtitle() != null) {
      json.put("subtitle", obj.getSubtitle());
    }
    if (obj.getTag() != null) {
      json.put("tag", obj.getTag());
    }
    if (obj.getTagUrl() != null) {
      json.put("tagUrl", obj.getTagUrl());
    }
    if (obj.getTitle() != null) {
      json.put("title", obj.getTitle());
    }
    if (obj.getTvPic() != null) {
      json.put("tvPic", obj.getTvPic());
    }
    if (obj.getTvUrl() != null) {
      json.put("tvUrl", obj.getTvUrl());
    }
    if (obj.getType() != null) {
      json.put("type", obj.getType());
    }
    if (obj.getUpdatetime() != null) {
      json.put("updatetime", obj.getUpdatetime());
    }
    if (obj.getUrl() != null) {
      json.put("url", obj.getUrl());
    }
    if (obj.getVcount() != null) {
      json.put("vcount", obj.getVcount());
    }
    if (obj.getVersionPlatform() != null) {
      json.put("versionPlatform", obj.getVersionPlatform());
    }
    if (obj.getVid() != null) {
      json.put("vid", obj.getVid());
    }
    if (obj.getVid_episode() != null) {
      json.put("vid_episode", obj.getVid_episode());
    }
    if (obj.getVideoFollowTime() != null) {
      json.put("videoFollowTime", obj.getVideoFollowTime());
    }
    if (obj.getVideo_pic() != null) {
      json.put("video_pic", obj.getVideo_pic());
    }
    if (obj.getVideo_type() != null) {
      json.put("video_type", obj.getVideo_type());
    }
    if (obj.getVidsubtitle() != null) {
      json.put("vidsubtitle", obj.getVidsubtitle());
    }
    if (obj.getVtypeFlag() != null) {
      json.put("vtypeFlag", obj.getVtypeFlag());
    }
    if (obj.getZid() != null) {
      json.put("zid", obj.getZid());
    }
  }
}