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

package cache.dto;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;

/**
 * Converter for {@link cache.dto.Video}.
 *
 * NOTE: This class has been automatically generated from the {@link cache.dto.Video} original class using Vert.x codegen.
 */
public class VideoConverter {

  public static void fromJson(JsonObject json, Video obj) {
    if (json.getValue("actor") instanceof String) {
      obj.setActor((String)json.getValue("actor"));
    }
    if (json.getValue("actorPlay") instanceof String) {
      obj.setActorPlay((String)json.getValue("actorPlay"));
    }
    if (json.getValue("albumId") instanceof Number) {
      obj.setAlbumId(((Number)json.getValue("albumId")).longValue());
    }
    if (json.getValue("alias") instanceof String) {
      obj.setAlias((String)json.getValue("alias"));
    }
    if (json.getValue("area") instanceof String) {
      obj.setArea((String)json.getValue("area"));
    }
    if (json.getValue("areaName") instanceof String) {
      obj.setAreaName((String)json.getValue("areaName"));
    }
    if (json.getValue("attr") instanceof Number) {
      obj.setAttr(((Number)json.getValue("attr")).intValue());
    }
    if (json.getValue("btime") instanceof Number) {
      obj.setBtime(((Number)json.getValue("btime")).intValue());
    }
    if (json.getValue("carfilmType") instanceof String) {
      obj.setCarfilmType((String)json.getValue("carfilmType"));
    }
    if (json.getValue("carfilmTypeName") instanceof String) {
      obj.setCarfilmTypeName((String)json.getValue("carfilmTypeName"));
    }
    if (json.getValue("cartoonStyle") instanceof String) {
      obj.setCartoonStyle((String)json.getValue("cartoonStyle"));
    }
    if (json.getValue("cartoonStyleName") instanceof String) {
      obj.setCartoonStyleName((String)json.getValue("cartoonStyleName"));
    }
    if (json.getValue("categoryId") instanceof Number) {
      obj.setCategoryId(((Number)json.getValue("categoryId")).intValue());
    }
    if (json.getValue("categoryName") instanceof String) {
      obj.setCategoryName((String)json.getValue("categoryName"));
    }
    if (json.getValue("commentCnt") instanceof Number) {
      obj.setCommentCnt(((Number)json.getValue("commentCnt")).longValue());
    }
    if (json.getValue("compere") instanceof String) {
      obj.setCompere((String)json.getValue("compere"));
    }
    if (json.getValue("contentRatingId") instanceof String) {
      obj.setContentRatingId((String)json.getValue("contentRatingId"));
    }
    if (json.getValue("contentRatingValue") instanceof String) {
      obj.setContentRatingValue((String)json.getValue("contentRatingValue"));
    }
    if (json.getValue("coopPlatform") instanceof String) {
      obj.setCoopPlatform((String)json.getValue("coopPlatform"));
    }
    if (json.getValue("copyrightCompany") instanceof String) {
      obj.setCopyrightCompany((String)json.getValue("copyrightCompany"));
    }
    if (json.getValue("copyrightEnd") instanceof String) {
      obj.setCopyrightEnd((String)json.getValue("copyrightEnd"));
    }
    if (json.getValue("copyrightStart") instanceof String) {
      obj.setCopyrightStart((String)json.getValue("copyrightStart"));
    }
    if (json.getValue("copyrightType") instanceof String) {
      obj.setCopyrightType((String)json.getValue("copyrightType"));
    }
    if (json.getValue("copyrightTypeName") instanceof String) {
      obj.setCopyrightTypeName((String)json.getValue("copyrightTypeName"));
    }
    if (json.getValue("createTime") instanceof Number) {
      obj.setCreateTime(((Number)json.getValue("createTime")).longValue());
    }
    if (json.getValue("deleted") instanceof Number) {
      obj.setDeleted(((Number)json.getValue("deleted")).intValue());
    }
    if (json.getValue("description") instanceof String) {
      obj.setDescription((String)json.getValue("description"));
    }
    if (json.getValue("directory") instanceof String) {
      obj.setDirectory((String)json.getValue("directory"));
    }
    if (json.getValue("disableType") instanceof String) {
      obj.setDisableType((String)json.getValue("disableType"));
    }
    if (json.getValue("disableTypeName") instanceof String) {
      obj.setDisableTypeName((String)json.getValue("disableTypeName"));
    }
    if (json.getValue("dolbyStreams") instanceof JsonArray) {
      java.util.ArrayList<cache.dto.Stream> list = new java.util.ArrayList<>();
      json.getJsonArray("dolbyStreams").forEach( item -> {
        if (item instanceof JsonObject)
          list.add(new cache.dto.Stream((JsonObject)item));
      });
      obj.setDolbyStreams(list);
    }
    if (json.getValue("downloadPlatform") instanceof String) {
      obj.setDownloadPlatform((String)json.getValue("downloadPlatform"));
    }
    if (json.getValue("drmFlagId") instanceof String) {
      obj.setDrmFlagId((String)json.getValue("drmFlagId"));
    }
    if (json.getValue("dtsStreams") instanceof JsonArray) {
      java.util.ArrayList<cache.dto.Stream> list = new java.util.ArrayList<>();
      json.getJsonArray("dtsStreams").forEach( item -> {
        if (item instanceof JsonObject)
          list.add(new cache.dto.Stream((JsonObject)item));
      });
      obj.setDtsStreams(list);
    }
    if (json.getValue("duration") instanceof Number) {
      obj.setDuration(((Number)json.getValue("duration")).intValue());
    }
    if (json.getValue("episode") instanceof String) {
      obj.setEpisode((String)json.getValue("episode"));
    }
    if (json.getValue("etime") instanceof Number) {
      obj.setEtime(((Number)json.getValue("etime")).intValue());
    }
    if (json.getValue("fieldType") instanceof Number) {
      obj.setFieldType(((Number)json.getValue("fieldType")).intValue());
    }
    if (json.getValue("fieldTypeName") instanceof String) {
      obj.setFieldTypeName((String)json.getValue("fieldTypeName"));
    }
    if (json.getValue("firstPlayTime") instanceof String) {
      obj.setFirstPlayTime((String)json.getValue("firstPlayTime"));
    }
    if (json.getValue("guest") instanceof String) {
      obj.setGuest((String)json.getValue("guest"));
    }
    if (json.getValue("guestIds") instanceof JsonArray) {
      java.util.ArrayList<java.lang.Long> list = new java.util.ArrayList<>();
      json.getJsonArray("guestIds").forEach( item -> {
        if (item instanceof Number)
          list.add(((Number)item).longValue());
      });
      obj.setGuestIds(list);
    }
    if (json.getValue("guestList") instanceof JsonArray) {
      java.util.ArrayList<cache.dto.ActorCache> list = new java.util.ArrayList<>();
      json.getJsonArray("guestList").forEach( item -> {
        if (item instanceof JsonObject)
          list.add(new cache.dto.ActorCache((JsonObject)item));
      });
      obj.setGuestList(list);
    }
    if (json.getValue("id") instanceof Number) {
      obj.setId(((Number)json.getValue("id")).longValue());
    }
    if (json.getValue("instructor") instanceof String) {
      obj.setInstructor((String)json.getValue("instructor"));
    }
    if (json.getValue("isDanmaku") instanceof Number) {
      obj.setIsDanmaku(((Number)json.getValue("isDanmaku")).intValue());
    }
    if (json.getValue("isPlayLock") instanceof Number) {
      obj.setIsPlayLock(((Number)json.getValue("isPlayLock")).intValue());
    }
    if (json.getValue("ismobile") instanceof Number) {
      obj.setIsmobile(((Number)json.getValue("ismobile")).intValue());
    }
    if (json.getValue("issue") instanceof Number) {
      obj.setIssue(((Number)json.getValue("issue")).intValue());
    }
    if (json.getValue("issueCompany") instanceof String) {
      obj.setIssueCompany((String)json.getValue("issueCompany"));
    }
    if (json.getValue("key") instanceof String) {
      obj.setKey((String)json.getValue("key"));
    }
    if (json.getValue("language") instanceof Number) {
      obj.setLanguage(((Number)json.getValue("language")).intValue());
    }
    if (json.getValue("languageName") instanceof String) {
      obj.setLanguageName((String)json.getValue("languageName"));
    }
    if (json.getValue("letvMakeStyle") instanceof Number) {
      obj.setLetvMakeStyle(((Number)json.getValue("letvMakeStyle")).intValue());
    }
    if (json.getValue("letvMakeStyleName") instanceof String) {
      obj.setLetvMakeStyleName((String)json.getValue("letvMakeStyleName"));
    }
    if (json.getValue("letvProduceStyle") instanceof Number) {
      obj.setLetvProduceStyle(((Number)json.getValue("letvProduceStyle")).intValue());
    }
    if (json.getValue("letvProduceStyleName") instanceof String) {
      obj.setLetvProduceStyleName((String)json.getValue("letvProduceStyleName"));
    }
    if (json.getValue("logonum") instanceof Number) {
      obj.setLogonum(((Number)json.getValue("logonum")).intValue());
    }
    if (json.getValue("maker") instanceof String) {
      obj.setMaker((String)json.getValue("maker"));
    }
    if (json.getValue("mid") instanceof String) {
      obj.setMid((String)json.getValue("mid"));
    }
    if (json.getValue("midL") instanceof Number) {
      obj.setMidL(((Number)json.getValue("midL")).longValue());
    }
    if (json.getValue("midStreams") instanceof String) {
      obj.setMidStreams((String)json.getValue("midStreams"));
    }
    if (json.getValue("musicAuthors") instanceof String) {
      obj.setMusicAuthors((String)json.getValue("musicAuthors"));
    }
    if (json.getValue("musicCompose") instanceof String) {
      obj.setMusicCompose((String)json.getValue("musicCompose"));
    }
    if (json.getValue("musicStyle") instanceof String) {
      obj.setMusicStyle((String)json.getValue("musicStyle"));
    }
    if (json.getValue("musicStyleName") instanceof String) {
      obj.setMusicStyleName((String)json.getValue("musicStyleName"));
    }
    if (json.getValue("nameCn") instanceof String) {
      obj.setNameCn((String)json.getValue("nameCn"));
    }
    if (json.getValue("normalStreams") instanceof JsonArray) {
      java.util.ArrayList<cache.dto.Stream> list = new java.util.ArrayList<>();
      json.getJsonArray("normalStreams").forEach( item -> {
        if (item instanceof JsonObject)
          list.add(new cache.dto.Stream((JsonObject)item));
      });
      obj.setNormalStreams(list);
    }
    if (json.getValue("page") instanceof Number) {
      obj.setPage(((Number)json.getValue("page")).intValue());
    }
    if (json.getValue("payPlatform") instanceof String) {
      obj.setPayPlatform((String)json.getValue("payPlatform"));
    }
    if (json.getValue("pic") instanceof String) {
      obj.setPic((String)json.getValue("pic"));
    }
    if (json.getValue("picAll") instanceof JsonArray) {
      java.util.ArrayList<cache.dto.PicAll> list = new java.util.ArrayList<>();
      json.getJsonArray("picAll").forEach( item -> {
        if (item instanceof JsonObject)
          list.add(new cache.dto.PicAll((JsonObject)item));
      });
      obj.setPicAll(list);
    }
    if (json.getValue("picOriginal") instanceof String) {
      obj.setPicOriginal((String)json.getValue("picOriginal"));
    }
    if (json.getValue("playPlatform") instanceof String) {
      obj.setPlayPlatform((String)json.getValue("playPlatform"));
    }
    if (json.getValue("playStreams") instanceof String) {
      obj.setPlayStreams((String)json.getValue("playStreams"));
    }
    if (json.getValue("popStyle") instanceof Number) {
      obj.setPopStyle(((Number)json.getValue("popStyle")).intValue());
    }
    if (json.getValue("popStyleName") instanceof String) {
      obj.setPopStyleName((String)json.getValue("popStyleName"));
    }
    if (json.getValue("porder") instanceof Number) {
      obj.setPorder(((Number)json.getValue("porder")).intValue());
    }
    if (json.getValue("pre") instanceof Number) {
      obj.setPre(((Number)json.getValue("pre")).intValue());
    }
    if (json.getValue("preName") instanceof String) {
      obj.setPreName((String)json.getValue("preName"));
    }
    if (json.getValue("recordCompany") instanceof String) {
      obj.setRecordCompany((String)json.getValue("recordCompany"));
    }
    if (json.getValue("recreationType") instanceof String) {
      obj.setRecreationType((String)json.getValue("recreationType"));
    }
    if (json.getValue("recreationTypeName") instanceof String) {
      obj.setRecreationTypeName((String)json.getValue("recreationTypeName"));
    }
    if (json.getValue("relativeContent") instanceof String) {
      obj.setRelativeContent((String)json.getValue("relativeContent"));
    }
    if (json.getValue("releaseDate") instanceof String) {
      obj.setReleaseDate((String)json.getValue("releaseDate"));
    }
    if (json.getValue("remark") instanceof String) {
      obj.setRemark((String)json.getValue("remark"));
    }
    if (json.getValue("school") instanceof String) {
      obj.setSchool((String)json.getValue("school"));
    }
    if (json.getValue("score") instanceof Number) {
      obj.setScore(((Number)json.getValue("score")).floatValue());
    }
    if (json.getValue("segmentIds") instanceof JsonArray) {
      java.util.ArrayList<java.lang.Long> list = new java.util.ArrayList<>();
      json.getJsonArray("segmentIds").forEach( item -> {
        if (item instanceof Number)
          list.add(((Number)item).longValue());
      });
      obj.setSegmentIds(list);
    }
    if (json.getValue("shortDesc") instanceof String) {
      obj.setShortDesc((String)json.getValue("shortDesc"));
    }
    if (json.getValue("singer") instanceof String) {
      obj.setSinger((String)json.getValue("singer"));
    }
    if (json.getValue("singerType") instanceof String) {
      obj.setSingerType((String)json.getValue("singerType"));
    }
    if (json.getValue("singerTypeName") instanceof String) {
      obj.setSingerTypeName((String)json.getValue("singerTypeName"));
    }
    if (json.getValue("singleName") instanceof String) {
      obj.setSingleName((String)json.getValue("singleName"));
    }
    if (json.getValue("sportsType") instanceof Number) {
      obj.setSportsType(((Number)json.getValue("sportsType")).intValue());
    }
    if (json.getValue("sportsTypeName") instanceof String) {
      obj.setSportsTypeName((String)json.getValue("sportsTypeName"));
    }
    if (json.getValue("starring") instanceof String) {
      obj.setStarring((String)json.getValue("starring"));
    }
    if (json.getValue("status") instanceof Number) {
      obj.setStatus(((Number)json.getValue("status")).intValue());
    }
    if (json.getValue("statusCode") instanceof String) {
      obj.setStatusCode((String)json.getValue("statusCode"));
    }
    if (json.getValue("streams") instanceof JsonArray) {
      java.util.ArrayList<cache.dto.Stream> list = new java.util.ArrayList<>();
      json.getJsonArray("streams").forEach( item -> {
        if (item instanceof JsonObject)
          list.add(new cache.dto.Stream((JsonObject)item));
      });
      obj.setStreams(list);
    }
    if (json.getValue("style") instanceof Number) {
      obj.setStyle(((Number)json.getValue("style")).intValue());
    }
    if (json.getValue("styleName") instanceof String) {
      obj.setStyleName((String)json.getValue("styleName"));
    }
    if (json.getValue("subCategory") instanceof String) {
      obj.setSubCategory((String)json.getValue("subCategory"));
    }
    if (json.getValue("subCategoryName") instanceof String) {
      obj.setSubCategoryName((String)json.getValue("subCategoryName"));
    }
    if (json.getValue("subTitle") instanceof String) {
      obj.setSubTitle((String)json.getValue("subTitle"));
    }
    if (json.getValue("tag") instanceof String) {
      obj.setTag((String)json.getValue("tag"));
    }
    if (json.getValue("team") instanceof String) {
      obj.setTeam((String)json.getValue("team"));
    }
    if (json.getValue("theatreStreams") instanceof JsonArray) {
      java.util.ArrayList<cache.dto.Stream> list = new java.util.ArrayList<>();
      json.getJsonArray("theatreStreams").forEach( item -> {
        if (item instanceof JsonObject)
          list.add(new cache.dto.Stream((JsonObject)item));
      });
      obj.setTheatreStreams(list);
    }
    if (json.getValue("threeDStreams") instanceof JsonArray) {
      java.util.ArrayList<cache.dto.Stream> list = new java.util.ArrayList<>();
      json.getJsonArray("threeDStreams").forEach( item -> {
        if (item instanceof JsonObject)
          list.add(new cache.dto.Stream((JsonObject)item));
      });
      obj.setThreeDStreams(list);
    }
    if (json.getValue("transCodePrefix") instanceof String) {
      obj.setTransCodePrefix((String)json.getValue("transCodePrefix"));
    }
    if (json.getValue("travelTheme") instanceof Number) {
      obj.setTravelTheme(((Number)json.getValue("travelTheme")).intValue());
    }
    if (json.getValue("travelThemeName") instanceof String) {
      obj.setTravelThemeName((String)json.getValue("travelThemeName"));
    }
    if (json.getValue("travelType") instanceof Number) {
      obj.setTravelType(((Number)json.getValue("travelType")).intValue());
    }
    if (json.getValue("travelTypeName") instanceof String) {
      obj.setTravelTypeName((String)json.getValue("travelTypeName"));
    }
    if (json.getValue("type") instanceof Number) {
      obj.setType(((Number)json.getValue("type")).intValue());
    }
    if (json.getValue("updateTime") instanceof Number) {
      obj.setUpdateTime(((Number)json.getValue("updateTime")).longValue());
    }
    if (json.getValue("vRstreams") instanceof JsonArray) {
      java.util.ArrayList<cache.dto.Stream> list = new java.util.ArrayList<>();
      json.getJsonArray("vRstreams").forEach( item -> {
        if (item instanceof JsonObject)
          list.add(new cache.dto.Stream((JsonObject)item));
      });
      obj.setVRstreams(list);
    }
    if (json.getValue("videoStreams") instanceof JsonArray) {
      java.util.ArrayList<java.lang.String> list = new java.util.ArrayList<>();
      json.getJsonArray("videoStreams").forEach( item -> {
        if (item instanceof String)
          list.add((String)item);
      });
      obj.setVideoStreams(list);
    }
    if (json.getValue("vtypeFlag") instanceof String) {
      obj.setVtypeFlag((String)json.getValue("vtypeFlag"));
    }
    if (json.getValue("vv") instanceof Number) {
      obj.setVv(((Number)json.getValue("vv")).longValue());
    }
    if (json.getValue("watchingFocus") instanceof JsonArray) {
      java.util.ArrayList<cache.dto.WatchingFocusCache> list = new java.util.ArrayList<>();
      json.getJsonArray("watchingFocus").forEach( item -> {
        if (item instanceof JsonObject)
          list.add(new cache.dto.WatchingFocusCache((JsonObject)item));
      });
      obj.setWatchingFocus(list);
    }
  }

  public static void toJson(Video obj, JsonObject json) {
    if (obj.getActor() != null) {
      json.put("actor", obj.getActor());
    }
    if (obj.getActorPlay() != null) {
      json.put("actorPlay", obj.getActorPlay());
    }
    if (obj.getAlbumId() != null) {
      json.put("albumId", obj.getAlbumId());
    }
    if (obj.getAlias() != null) {
      json.put("alias", obj.getAlias());
    }
    if (obj.getArea() != null) {
      json.put("area", obj.getArea());
    }
    if (obj.getAreaName() != null) {
      json.put("areaName", obj.getAreaName());
    }
    if (obj.getAttr() != null) {
      json.put("attr", obj.getAttr());
    }
    if (obj.getBtime() != null) {
      json.put("btime", obj.getBtime());
    }
    if (obj.getCarfilmType() != null) {
      json.put("carfilmType", obj.getCarfilmType());
    }
    if (obj.getCarfilmTypeName() != null) {
      json.put("carfilmTypeName", obj.getCarfilmTypeName());
    }
    if (obj.getCartoonStyle() != null) {
      json.put("cartoonStyle", obj.getCartoonStyle());
    }
    if (obj.getCartoonStyleName() != null) {
      json.put("cartoonStyleName", obj.getCartoonStyleName());
    }
    if (obj.getCategoryId() != null) {
      json.put("categoryId", obj.getCategoryId());
    }
    if (obj.getCategoryName() != null) {
      json.put("categoryName", obj.getCategoryName());
    }
    if (obj.getCommentCnt() != null) {
      json.put("commentCnt", obj.getCommentCnt());
    }
    if (obj.getCompere() != null) {
      json.put("compere", obj.getCompere());
    }
    if (obj.getContentRatingId() != null) {
      json.put("contentRatingId", obj.getContentRatingId());
    }
    if (obj.getContentRatingValue() != null) {
      json.put("contentRatingValue", obj.getContentRatingValue());
    }
    if (obj.getCoopPlatform() != null) {
      json.put("coopPlatform", obj.getCoopPlatform());
    }
    if (obj.getCopyrightCompany() != null) {
      json.put("copyrightCompany", obj.getCopyrightCompany());
    }
    if (obj.getCopyrightEnd() != null) {
      json.put("copyrightEnd", obj.getCopyrightEnd());
    }
    if (obj.getCopyrightStart() != null) {
      json.put("copyrightStart", obj.getCopyrightStart());
    }
    if (obj.getCopyrightType() != null) {
      json.put("copyrightType", obj.getCopyrightType());
    }
    if (obj.getCopyrightTypeName() != null) {
      json.put("copyrightTypeName", obj.getCopyrightTypeName());
    }
    if (obj.getCreateTime() != null) {
      json.put("createTime", obj.getCreateTime());
    }
    if (obj.getDeleted() != null) {
      json.put("deleted", obj.getDeleted());
    }
    if (obj.getDescription() != null) {
      json.put("description", obj.getDescription());
    }
    if (obj.getDirectory() != null) {
      json.put("directory", obj.getDirectory());
    }
    if (obj.getDisableType() != null) {
      json.put("disableType", obj.getDisableType());
    }
    if (obj.getDisableTypeName() != null) {
      json.put("disableTypeName", obj.getDisableTypeName());
    }
    if (obj.getDolbyStreams() != null) {
      JsonArray array = new JsonArray();
      obj.getDolbyStreams().forEach(item -> array.add(item.toJson()));
      json.put("dolbyStreams", array);
    }
    if (obj.getDownloadPlatform() != null) {
      json.put("downloadPlatform", obj.getDownloadPlatform());
    }
    if (obj.getDrmFlagId() != null) {
      json.put("drmFlagId", obj.getDrmFlagId());
    }
    if (obj.getDtsStreams() != null) {
      JsonArray array = new JsonArray();
      obj.getDtsStreams().forEach(item -> array.add(item.toJson()));
      json.put("dtsStreams", array);
    }
    if (obj.getDuration() != null) {
      json.put("duration", obj.getDuration());
    }
    if (obj.getEpisode() != null) {
      json.put("episode", obj.getEpisode());
    }
    if (obj.getEtime() != null) {
      json.put("etime", obj.getEtime());
    }
    if (obj.getFieldType() != null) {
      json.put("fieldType", obj.getFieldType());
    }
    if (obj.getFieldTypeName() != null) {
      json.put("fieldTypeName", obj.getFieldTypeName());
    }
    if (obj.getFirstPlayTime() != null) {
      json.put("firstPlayTime", obj.getFirstPlayTime());
    }
    if (obj.getGuest() != null) {
      json.put("guest", obj.getGuest());
    }
    if (obj.getGuestIds() != null) {
      JsonArray array = new JsonArray();
      obj.getGuestIds().forEach(item -> array.add(item));
      json.put("guestIds", array);
    }
    if (obj.getGuestList() != null) {
      JsonArray array = new JsonArray();
      obj.getGuestList().forEach(item -> array.add(item.toJson()));
      json.put("guestList", array);
    }
    if (obj.getId() != null) {
      json.put("id", obj.getId());
    }
    if (obj.getInstructor() != null) {
      json.put("instructor", obj.getInstructor());
    }
    if (obj.getIsDanmaku() != null) {
      json.put("isDanmaku", obj.getIsDanmaku());
    }
    if (obj.getIsPlayLock() != null) {
      json.put("isPlayLock", obj.getIsPlayLock());
    }
    if (obj.getIsmobile() != null) {
      json.put("ismobile", obj.getIsmobile());
    }
    if (obj.getIssue() != null) {
      json.put("issue", obj.getIssue());
    }
    if (obj.getIssueCompany() != null) {
      json.put("issueCompany", obj.getIssueCompany());
    }
    if (obj.getKey() != null) {
      json.put("key", obj.getKey());
    }
    if (obj.getLanguage() != null) {
      json.put("language", obj.getLanguage());
    }
    if (obj.getLanguageName() != null) {
      json.put("languageName", obj.getLanguageName());
    }
    if (obj.getLetvMakeStyle() != null) {
      json.put("letvMakeStyle", obj.getLetvMakeStyle());
    }
    if (obj.getLetvMakeStyleName() != null) {
      json.put("letvMakeStyleName", obj.getLetvMakeStyleName());
    }
    if (obj.getLetvProduceStyle() != null) {
      json.put("letvProduceStyle", obj.getLetvProduceStyle());
    }
    if (obj.getLetvProduceStyleName() != null) {
      json.put("letvProduceStyleName", obj.getLetvProduceStyleName());
    }
    if (obj.getLogonum() != null) {
      json.put("logonum", obj.getLogonum());
    }
    if (obj.getMaker() != null) {
      json.put("maker", obj.getMaker());
    }
    if (obj.getMid() != null) {
      json.put("mid", obj.getMid());
    }
    if (obj.getMidL() != null) {
      json.put("midL", obj.getMidL());
    }
    if (obj.getMidStreams() != null) {
      json.put("midStreams", obj.getMidStreams());
    }
    if (obj.getMusicAuthors() != null) {
      json.put("musicAuthors", obj.getMusicAuthors());
    }
    if (obj.getMusicCompose() != null) {
      json.put("musicCompose", obj.getMusicCompose());
    }
    if (obj.getMusicStyle() != null) {
      json.put("musicStyle", obj.getMusicStyle());
    }
    if (obj.getMusicStyleName() != null) {
      json.put("musicStyleName", obj.getMusicStyleName());
    }
    if (obj.getNameCn() != null) {
      json.put("nameCn", obj.getNameCn());
    }
    if (obj.getNormalStreams() != null) {
      JsonArray array = new JsonArray();
      obj.getNormalStreams().forEach(item -> array.add(item.toJson()));
      json.put("normalStreams", array);
    }
    if (obj.getPage() != null) {
      json.put("page", obj.getPage());
    }
    if (obj.getPayPlatform() != null) {
      json.put("payPlatform", obj.getPayPlatform());
    }
    if (obj.getPic() != null) {
      json.put("pic", obj.getPic());
    }
    if (obj.getPicAll() != null) {
      JsonArray array = new JsonArray();
      obj.getPicAll().forEach(item -> array.add(item.toJson()));
      json.put("picAll", array);
    }
    if (obj.getPicOriginal() != null) {
      json.put("picOriginal", obj.getPicOriginal());
    }
    if (obj.getPlayPlatform() != null) {
      json.put("playPlatform", obj.getPlayPlatform());
    }
    if (obj.getPlayStreams() != null) {
      json.put("playStreams", obj.getPlayStreams());
    }
    if (obj.getPopStyle() != null) {
      json.put("popStyle", obj.getPopStyle());
    }
    if (obj.getPopStyleName() != null) {
      json.put("popStyleName", obj.getPopStyleName());
    }
    if (obj.getPorder() != null) {
      json.put("porder", obj.getPorder());
    }
    if (obj.getPre() != null) {
      json.put("pre", obj.getPre());
    }
    if (obj.getPreName() != null) {
      json.put("preName", obj.getPreName());
    }
    if (obj.getRecordCompany() != null) {
      json.put("recordCompany", obj.getRecordCompany());
    }
    if (obj.getRecreationType() != null) {
      json.put("recreationType", obj.getRecreationType());
    }
    if (obj.getRecreationTypeName() != null) {
      json.put("recreationTypeName", obj.getRecreationTypeName());
    }
    if (obj.getRelativeContent() != null) {
      json.put("relativeContent", obj.getRelativeContent());
    }
    if (obj.getReleaseDate() != null) {
      json.put("releaseDate", obj.getReleaseDate());
    }
    if (obj.getRemark() != null) {
      json.put("remark", obj.getRemark());
    }
    if (obj.getSchool() != null) {
      json.put("school", obj.getSchool());
    }
    if (obj.getScore() != null) {
      json.put("score", obj.getScore());
    }
    if (obj.getSegmentIds() != null) {
      JsonArray array = new JsonArray();
      obj.getSegmentIds().forEach(item -> array.add(item));
      json.put("segmentIds", array);
    }
    if (obj.getShortDesc() != null) {
      json.put("shortDesc", obj.getShortDesc());
    }
    if (obj.getSinger() != null) {
      json.put("singer", obj.getSinger());
    }
    if (obj.getSingerType() != null) {
      json.put("singerType", obj.getSingerType());
    }
    if (obj.getSingerTypeName() != null) {
      json.put("singerTypeName", obj.getSingerTypeName());
    }
    if (obj.getSingleName() != null) {
      json.put("singleName", obj.getSingleName());
    }
    if (obj.getSportsType() != null) {
      json.put("sportsType", obj.getSportsType());
    }
    if (obj.getSportsTypeName() != null) {
      json.put("sportsTypeName", obj.getSportsTypeName());
    }
    if (obj.getStarring() != null) {
      json.put("starring", obj.getStarring());
    }
    if (obj.getStatus() != null) {
      json.put("status", obj.getStatus());
    }
    if (obj.getStatusCode() != null) {
      json.put("statusCode", obj.getStatusCode());
    }
    if (obj.getStreams() != null) {
      JsonArray array = new JsonArray();
      obj.getStreams().forEach(item -> array.add(item.toJson()));
      json.put("streams", array);
    }
    if (obj.getStyle() != null) {
      json.put("style", obj.getStyle());
    }
    if (obj.getStyleName() != null) {
      json.put("styleName", obj.getStyleName());
    }
    if (obj.getSubCategory() != null) {
      json.put("subCategory", obj.getSubCategory());
    }
    if (obj.getSubCategoryName() != null) {
      json.put("subCategoryName", obj.getSubCategoryName());
    }
    if (obj.getSubTitle() != null) {
      json.put("subTitle", obj.getSubTitle());
    }
    if (obj.getTag() != null) {
      json.put("tag", obj.getTag());
    }
    if (obj.getTeam() != null) {
      json.put("team", obj.getTeam());
    }
    if (obj.getTheatreStreams() != null) {
      JsonArray array = new JsonArray();
      obj.getTheatreStreams().forEach(item -> array.add(item.toJson()));
      json.put("theatreStreams", array);
    }
    if (obj.getThreeDStreams() != null) {
      JsonArray array = new JsonArray();
      obj.getThreeDStreams().forEach(item -> array.add(item.toJson()));
      json.put("threeDStreams", array);
    }
    if (obj.getTransCodePrefix() != null) {
      json.put("transCodePrefix", obj.getTransCodePrefix());
    }
    if (obj.getTravelTheme() != null) {
      json.put("travelTheme", obj.getTravelTheme());
    }
    if (obj.getTravelThemeName() != null) {
      json.put("travelThemeName", obj.getTravelThemeName());
    }
    if (obj.getTravelType() != null) {
      json.put("travelType", obj.getTravelType());
    }
    if (obj.getTravelTypeName() != null) {
      json.put("travelTypeName", obj.getTravelTypeName());
    }
    if (obj.getType() != null) {
      json.put("type", obj.getType());
    }
    if (obj.getUpdateTime() != null) {
      json.put("updateTime", obj.getUpdateTime());
    }
    if (obj.getVRstreams() != null) {
      JsonArray array = new JsonArray();
      obj.getVRstreams().forEach(item -> array.add(item.toJson()));
      json.put("vRstreams", array);
    }
    if (obj.getVideoStreams() != null) {
      JsonArray array = new JsonArray();
      obj.getVideoStreams().forEach(item -> array.add(item));
      json.put("videoStreams", array);
    }
    if (obj.getVtypeFlag() != null) {
      json.put("vtypeFlag", obj.getVtypeFlag());
    }
    if (obj.getVv() != null) {
      json.put("vv", obj.getVv());
    }
    if (obj.getWatchingFocus() != null) {
      JsonArray array = new JsonArray();
      obj.getWatchingFocus().forEach(item -> array.add(item.toJson()));
      json.put("watchingFocus", array);
    }
  }
}