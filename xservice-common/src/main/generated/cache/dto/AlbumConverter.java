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
 * Converter for {@link cache.dto.Album}.
 *
 * NOTE: This class has been automatically generated from the {@link cache.dto.Album} original class using Vert.x codegen.
 */
public class AlbumConverter {

  public static void fromJson(JsonObject json, Album obj) {
    if (json.getValue("actor") instanceof String) {
      obj.setActor((String)json.getValue("actor"));
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
    if (json.getValue("audioInfoObj") instanceof JsonArray) {
      java.util.ArrayList<cache.dto.MusicCache> list = new java.util.ArrayList<>();
      json.getJsonArray("audioInfoObj").forEach( item -> {
        if (item instanceof JsonObject)
          list.add(new cache.dto.MusicCache((JsonObject)item));
      });
      obj.setAudioInfoObj(list);
    }
    if (json.getValue("baseType") instanceof Number) {
      obj.setBaseType(((Number)json.getValue("baseType")).intValue());
    }
    if (json.getValue("carfilmType") instanceof String) {
      obj.setCarfilmType((String)json.getValue("carfilmType"));
    }
    if (json.getValue("carfilmTypeName") instanceof String) {
      obj.setCarfilmTypeName((String)json.getValue("carfilmTypeName"));
    }
    if (json.getValue("cast") instanceof String) {
      obj.setCast((String)json.getValue("cast"));
    }
    if (json.getValue("categoryId") instanceof Number) {
      obj.setCategoryId(((Number)json.getValue("categoryId")).intValue());
    }
    if (json.getValue("categoryName") instanceof String) {
      obj.setCategoryName((String)json.getValue("categoryName"));
    }
    if (json.getValue("cibn") instanceof Number) {
      obj.setCibn(((Number)json.getValue("cibn")).intValue());
    }
    if (json.getValue("cntv") instanceof Number) {
      obj.setCntv(((Number)json.getValue("cntv")).intValue());
    }
    if (json.getValue("commentCnt") instanceof Number) {
      obj.setCommentCnt(((Number)json.getValue("commentCnt")).longValue());
    }
    if (json.getValue("compere") instanceof String) {
      obj.setCompere((String)json.getValue("compere"));
    }
    if (json.getValue("compereIds") instanceof JsonArray) {
      java.util.ArrayList<java.lang.Long> list = new java.util.ArrayList<>();
      json.getJsonArray("compereIds").forEach( item -> {
        if (item instanceof Number)
          list.add(((Number)item).longValue());
      });
      obj.setCompereIds(list);
    }
    if (json.getValue("compereObj") instanceof JsonArray) {
      java.util.ArrayList<cache.dto.ActorCache> list = new java.util.ArrayList<>();
      json.getJsonArray("compereObj").forEach( item -> {
        if (item instanceof JsonObject)
          list.add(new cache.dto.ActorCache((JsonObject)item));
      });
      obj.setCompereObj(list);
    }
    if (json.getValue("contentRatingDesc") instanceof String) {
      obj.setContentRatingDesc((String)json.getValue("contentRatingDesc"));
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
    if (json.getValue("copyrightType") instanceof Number) {
      obj.setCopyrightType(((Number)json.getValue("copyrightType")).intValue());
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
    if (json.getValue("directoryId") instanceof Number) {
      obj.setDirectoryId(((Number)json.getValue("directoryId")).intValue());
    }
    if (json.getValue("directoryIds") instanceof JsonArray) {
      java.util.ArrayList<java.lang.Long> list = new java.util.ArrayList<>();
      json.getJsonArray("directoryIds").forEach( item -> {
        if (item instanceof Number)
          list.add(((Number)item).longValue());
      });
      obj.setDirectoryIds(list);
    }
    if (json.getValue("downloadPlatform") instanceof String) {
      obj.setDownloadPlatform((String)json.getValue("downloadPlatform"));
    }
    if (json.getValue("dub") instanceof String) {
      obj.setDub((String)json.getValue("dub"));
    }
    if (json.getValue("duration") instanceof Number) {
      obj.setDuration(((Number)json.getValue("duration")).intValue());
    }
    if (json.getValue("episode") instanceof Number) {
      obj.setEpisode(((Number)json.getValue("episode")).intValue());
    }
    if (json.getValue("fid") instanceof Number) {
      obj.setFid(((Number)json.getValue("fid")).longValue());
    }
    if (json.getValue("fieldType") instanceof Number) {
      obj.setFieldType(((Number)json.getValue("fieldType")).intValue());
    }
    if (json.getValue("fieldTypeName") instanceof String) {
      obj.setFieldTypeName((String)json.getValue("fieldTypeName"));
    }
    if (json.getValue("filmBaseType") instanceof Number) {
      obj.setFilmBaseType(((Number)json.getValue("filmBaseType")).intValue());
    }
    if (json.getValue("filmBaseTypeName") instanceof String) {
      obj.setFilmBaseTypeName((String)json.getValue("filmBaseTypeName"));
    }
    if (json.getValue("firstPlayTime") instanceof String) {
      obj.setFirstPlayTime((String)json.getValue("firstPlayTime"));
    }
    if (json.getValue("fitAge") instanceof String) {
      obj.setFitAge((String)json.getValue("fitAge"));
    }
    if (json.getValue("follownum") instanceof Number) {
      obj.setFollownum(((Number)json.getValue("follownum")).intValue());
    }
    if (json.getValue("forder") instanceof Number) {
      obj.setForder(((Number)json.getValue("forder")).intValue());
    }
    if (json.getValue("hotWordIds") instanceof JsonArray) {
      java.util.ArrayList<java.lang.Long> list = new java.util.ArrayList<>();
      json.getJsonArray("hotWordIds").forEach( item -> {
        if (item instanceof Number)
          list.add(((Number)item).longValue());
      });
      obj.setHotWordIds(list);
    }
    if (json.getValue("hotWords") instanceof JsonArray) {
      java.util.ArrayList<cache.dto.HotWordsCache> list = new java.util.ArrayList<>();
      json.getJsonArray("hotWords").forEach( item -> {
        if (item instanceof JsonObject)
          list.add(new cache.dto.HotWordsCache((JsonObject)item));
      });
      obj.setHotWords(list);
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
    if (json.getValue("isEnd") instanceof Number) {
      obj.setIsEnd(((Number)json.getValue("isEnd")).intValue());
    }
    if (json.getValue("isFollow") instanceof Number) {
      obj.setIsFollow(((Number)json.getValue("isFollow")).intValue());
    }
    if (json.getValue("isHeight") instanceof Number) {
      obj.setIsHeight(((Number)json.getValue("isHeight")).intValue());
    }
    if (json.getValue("isHomemade") instanceof Number) {
      obj.setIsHomemade(((Number)json.getValue("isHomemade")).intValue());
    }
    if (json.getValue("isPay") instanceof Number) {
      obj.setIsPay(((Number)json.getValue("isPay")).intValue());
    }
    if (json.getValue("isPlayMark") instanceof Number) {
      obj.setIsPlayMark(((Number)json.getValue("isPlayMark")).intValue());
    }
    if (json.getValue("ismobile") instanceof Number) {
      obj.setIsmobile(((Number)json.getValue("ismobile")).intValue());
    }
    if (json.getValue("issueCompany") instanceof String) {
      obj.setIssueCompany((String)json.getValue("issueCompany"));
    }
    if (json.getValue("isyuanxian") instanceof Number) {
      obj.setIsyuanxian(((Number)json.getValue("isyuanxian")).intValue());
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
    if (json.getValue("letvReleaseDate") instanceof String) {
      obj.setLetvReleaseDate((String)json.getValue("letvReleaseDate"));
    }
    if (json.getValue("maker") instanceof String) {
      obj.setMaker((String)json.getValue("maker"));
    }
    if (json.getValue("makerCompany") instanceof String) {
      obj.setMakerCompany((String)json.getValue("makerCompany"));
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
    if (json.getValue("music_authors") instanceof String) {
      obj.setMusic_authors((String)json.getValue("music_authors"));
    }
    if (json.getValue("nameCn") instanceof String) {
      obj.setNameCn((String)json.getValue("nameCn"));
    }
    if (json.getValue("nonPositiveStreams") instanceof String) {
      obj.setNonPositiveStreams((String)json.getValue("nonPositiveStreams"));
    }
    if (json.getValue("nowEpisodes") instanceof String) {
      obj.setNowEpisodes((String)json.getValue("nowEpisodes"));
    }
    if (json.getValue("nowIssue") instanceof String) {
      obj.setNowIssue((String)json.getValue("nowIssue"));
    }
    if (json.getValue("originator") instanceof String) {
      obj.setOriginator((String)json.getValue("originator"));
    }
    if (json.getValue("ost") instanceof JsonArray) {
      java.util.ArrayList<cache.dto.OstCache> list = new java.util.ArrayList<>();
      json.getJsonArray("ost").forEach( item -> {
        if (item instanceof JsonObject)
          list.add(new cache.dto.OstCache((JsonObject)item));
      });
      obj.setOst(list);
    }
    if (json.getValue("payPlatform") instanceof String) {
      obj.setPayPlatform((String)json.getValue("payPlatform"));
    }
    if (json.getValue("pic") instanceof String) {
      obj.setPic((String)json.getValue("pic"));
    }
    if (json.getValue("picCollections") instanceof JsonArray) {
      java.util.ArrayList<cache.dto.PicAll> list = new java.util.ArrayList<>();
      json.getJsonArray("picCollections").forEach( item -> {
        if (item instanceof JsonObject)
          list.add(new cache.dto.PicAll((JsonObject)item));
      });
      obj.setPicCollections(list);
    }
    if (json.getValue("picOriginal") instanceof String) {
      obj.setPicOriginal((String)json.getValue("picOriginal"));
    }
    if (json.getValue("playPlatform") instanceof String) {
      obj.setPlayPlatform((String)json.getValue("playPlatform"));
    }
    if (json.getValue("playStatus") instanceof String) {
      obj.setPlayStatus((String)json.getValue("playStatus"));
    }
    if (json.getValue("playStreams") instanceof String) {
      obj.setPlayStreams((String)json.getValue("playStreams"));
    }
    if (json.getValue("playTv") instanceof String) {
      obj.setPlayTv((String)json.getValue("playTv"));
    }
    if (json.getValue("playTvName") instanceof String) {
      obj.setPlayTvName((String)json.getValue("playTvName"));
    }
    if (json.getValue("popStyle") instanceof Number) {
      obj.setPopStyle(((Number)json.getValue("popStyle")).intValue());
    }
    if (json.getValue("popStyleName") instanceof String) {
      obj.setPopStyleName((String)json.getValue("popStyleName"));
    }
    if (json.getValue("positiveStreams") instanceof String) {
      obj.setPositiveStreams((String)json.getValue("positiveStreams"));
    }
    if (json.getValue("producer") instanceof String) {
      obj.setProducer((String)json.getValue("producer"));
    }
    if (json.getValue("programStyle") instanceof Number) {
      obj.setProgramStyle(((Number)json.getValue("programStyle")).intValue());
    }
    if (json.getValue("programStyleName") instanceof String) {
      obj.setProgramStyleName((String)json.getValue("programStyleName"));
    }
    if (json.getValue("pushflag") instanceof Number) {
      obj.setPushflag(((Number)json.getValue("pushflag")).intValue());
    }
    if (json.getValue("recommLevel") instanceof Number) {
      obj.setRecommLevel(((Number)json.getValue("recommLevel")).intValue());
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
    if (json.getValue("relationAlbumId") instanceof String) {
      obj.setRelationAlbumId((String)json.getValue("relationAlbumId"));
    }
    if (json.getValue("relationId") instanceof String) {
      obj.setRelationId((String)json.getValue("relationId"));
    }
    if (json.getValue("releaseDate") instanceof String) {
      obj.setReleaseDate((String)json.getValue("releaseDate"));
    }
    if (json.getValue("school") instanceof String) {
      obj.setSchool((String)json.getValue("school"));
    }
    if (json.getValue("score") instanceof Number) {
      obj.setScore(((Number)json.getValue("score")).floatValue());
    }
    if (json.getValue("screenWriter") instanceof String) {
      obj.setScreenWriter((String)json.getValue("screenWriter"));
    }
    if (json.getValue("secondCate") instanceof String) {
      obj.setSecondCate((String)json.getValue("secondCate"));
    }
    if (json.getValue("shortDesc") instanceof String) {
      obj.setShortDesc((String)json.getValue("shortDesc"));
    }
    if (json.getValue("sportsType") instanceof Number) {
      obj.setSportsType(((Number)json.getValue("sportsType")).intValue());
    }
    if (json.getValue("sportsTypeName") instanceof String) {
      obj.setSportsTypeName((String)json.getValue("sportsTypeName"));
    }
    if (json.getValue("starIds") instanceof JsonArray) {
      java.util.ArrayList<java.lang.Long> list = new java.util.ArrayList<>();
      json.getJsonArray("starIds").forEach( item -> {
        if (item instanceof Number)
          list.add(((Number)item).longValue());
      });
      obj.setStarIds(list);
    }
    if (json.getValue("starList") instanceof JsonArray) {
      java.util.ArrayList<cache.dto.ActorCache> list = new java.util.ArrayList<>();
      json.getJsonArray("starList").forEach( item -> {
        if (item instanceof JsonObject)
          list.add(new cache.dto.ActorCache((JsonObject)item));
      });
      obj.setStarList(list);
    }
    if (json.getValue("starring") instanceof String) {
      obj.setStarring((String)json.getValue("starring"));
    }
    if (json.getValue("starringIds") instanceof JsonArray) {
      java.util.ArrayList<java.lang.Long> list = new java.util.ArrayList<>();
      json.getJsonArray("starringIds").forEach( item -> {
        if (item instanceof Number)
          list.add(((Number)item).longValue());
      });
      obj.setStarringIds(list);
    }
    if (json.getValue("starringObj") instanceof JsonArray) {
      java.util.ArrayList<cache.dto.ActorCache> list = new java.util.ArrayList<>();
      json.getJsonArray("starringObj").forEach( item -> {
        if (item instanceof JsonObject)
          list.add(new cache.dto.ActorCache((JsonObject)item));
      });
      obj.setStarringObj(list);
    }
    if (json.getValue("starringPlay") instanceof String) {
      obj.setStarringPlay((String)json.getValue("starringPlay"));
    }
    if (json.getValue("status") instanceof Number) {
      obj.setStatus(((Number)json.getValue("status")).intValue());
    }
    if (json.getValue("streams") instanceof JsonArray) {
      java.util.ArrayList<cache.dto.Stream> list = new java.util.ArrayList<>();
      json.getJsonArray("streams").forEach( item -> {
        if (item instanceof JsonObject)
          list.add(new cache.dto.Stream((JsonObject)item));
      });
      obj.setStreams(list);
    }
    if (json.getValue("style") instanceof String) {
      obj.setStyle((String)json.getValue("style"));
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
    if (json.getValue("supervise") instanceof String) {
      obj.setSupervise((String)json.getValue("supervise"));
    }
    if (json.getValue("tag") instanceof String) {
      obj.setTag((String)json.getValue("tag"));
    }
    if (json.getValue("thirdCate") instanceof String) {
      obj.setThirdCate((String)json.getValue("thirdCate"));
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
    if (json.getValue("typeName") instanceof String) {
      obj.setTypeName((String)json.getValue("typeName"));
    }
    if (json.getValue("updateTime") instanceof Number) {
      obj.setUpdateTime(((Number)json.getValue("updateTime")).longValue());
    }
    if (json.getValue("varietyShow") instanceof String) {
      obj.setVarietyShow((String)json.getValue("varietyShow"));
    }
    if (json.getValue("videoFollowTime") instanceof Number) {
      obj.setVideoFollowTime(((Number)json.getValue("videoFollowTime")).longValue());
    }
    if (json.getValue("videoId") instanceof Number) {
      obj.setVideoId(((Number)json.getValue("videoId")).longValue());
    }
    if (json.getValue("videoStreams") instanceof JsonArray) {
      java.util.ArrayList<java.lang.String> list = new java.util.ArrayList<>();
      json.getJsonArray("videoStreams").forEach( item -> {
        if (item instanceof String)
          list.add((String)item);
      });
      obj.setVideoStreams(list);
    }
    if (json.getValue("vv") instanceof Number) {
      obj.setVv(((Number)json.getValue("vv")).longValue());
    }
    if (json.getValue("wasu") instanceof Number) {
      obj.setWasu(((Number)json.getValue("wasu")).intValue());
    }
  }

  public static void toJson(Album obj, JsonObject json) {
    if (obj.getActor() != null) {
      json.put("actor", obj.getActor());
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
    if (obj.getAudioInfoObj() != null) {
      JsonArray array = new JsonArray();
      obj.getAudioInfoObj().forEach(item -> array.add(item.toJson()));
      json.put("audioInfoObj", array);
    }
    if (obj.getBaseType() != null) {
      json.put("baseType", obj.getBaseType());
    }
    if (obj.getCarfilmType() != null) {
      json.put("carfilmType", obj.getCarfilmType());
    }
    if (obj.getCarfilmTypeName() != null) {
      json.put("carfilmTypeName", obj.getCarfilmTypeName());
    }
    if (obj.getCast() != null) {
      json.put("cast", obj.getCast());
    }
    if (obj.getCategoryId() != null) {
      json.put("categoryId", obj.getCategoryId());
    }
    if (obj.getCategoryName() != null) {
      json.put("categoryName", obj.getCategoryName());
    }
    if (obj.getCibn() != null) {
      json.put("cibn", obj.getCibn());
    }
    if (obj.getCntv() != null) {
      json.put("cntv", obj.getCntv());
    }
    if (obj.getCommentCnt() != null) {
      json.put("commentCnt", obj.getCommentCnt());
    }
    if (obj.getCompere() != null) {
      json.put("compere", obj.getCompere());
    }
    if (obj.getCompereIds() != null) {
      JsonArray array = new JsonArray();
      obj.getCompereIds().forEach(item -> array.add(item));
      json.put("compereIds", array);
    }
    if (obj.getCompereObj() != null) {
      JsonArray array = new JsonArray();
      obj.getCompereObj().forEach(item -> array.add(item.toJson()));
      json.put("compereObj", array);
    }
    if (obj.getContentRatingDesc() != null) {
      json.put("contentRatingDesc", obj.getContentRatingDesc());
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
    if (obj.getDirectoryId() != null) {
      json.put("directoryId", obj.getDirectoryId());
    }
    if (obj.getDirectoryIds() != null) {
      JsonArray array = new JsonArray();
      obj.getDirectoryIds().forEach(item -> array.add(item));
      json.put("directoryIds", array);
    }
    if (obj.getDownloadPlatform() != null) {
      json.put("downloadPlatform", obj.getDownloadPlatform());
    }
    if (obj.getDub() != null) {
      json.put("dub", obj.getDub());
    }
    if (obj.getDuration() != null) {
      json.put("duration", obj.getDuration());
    }
    if (obj.getEpisode() != null) {
      json.put("episode", obj.getEpisode());
    }
    if (obj.getFid() != null) {
      json.put("fid", obj.getFid());
    }
    if (obj.getFieldType() != null) {
      json.put("fieldType", obj.getFieldType());
    }
    if (obj.getFieldTypeName() != null) {
      json.put("fieldTypeName", obj.getFieldTypeName());
    }
    if (obj.getFilmBaseType() != null) {
      json.put("filmBaseType", obj.getFilmBaseType());
    }
    if (obj.getFilmBaseTypeName() != null) {
      json.put("filmBaseTypeName", obj.getFilmBaseTypeName());
    }
    if (obj.getFirstPlayTime() != null) {
      json.put("firstPlayTime", obj.getFirstPlayTime());
    }
    if (obj.getFitAge() != null) {
      json.put("fitAge", obj.getFitAge());
    }
    if (obj.getFollownum() != null) {
      json.put("follownum", obj.getFollownum());
    }
    if (obj.getForder() != null) {
      json.put("forder", obj.getForder());
    }
    if (obj.getHotWordIds() != null) {
      JsonArray array = new JsonArray();
      obj.getHotWordIds().forEach(item -> array.add(item));
      json.put("hotWordIds", array);
    }
    if (obj.getHotWords() != null) {
      JsonArray array = new JsonArray();
      obj.getHotWords().forEach(item -> array.add(item.toJson()));
      json.put("hotWords", array);
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
    if (obj.getIsEnd() != null) {
      json.put("isEnd", obj.getIsEnd());
    }
    if (obj.getIsFollow() != null) {
      json.put("isFollow", obj.getIsFollow());
    }
    if (obj.getIsHeight() != null) {
      json.put("isHeight", obj.getIsHeight());
    }
    if (obj.getIsHomemade() != null) {
      json.put("isHomemade", obj.getIsHomemade());
    }
    if (obj.getIsPay() != null) {
      json.put("isPay", obj.getIsPay());
    }
    if (obj.getIsPlayMark() != null) {
      json.put("isPlayMark", obj.getIsPlayMark());
    }
    if (obj.getIsmobile() != null) {
      json.put("ismobile", obj.getIsmobile());
    }
    if (obj.getIssueCompany() != null) {
      json.put("issueCompany", obj.getIssueCompany());
    }
    if (obj.getIsyuanxian() != null) {
      json.put("isyuanxian", obj.getIsyuanxian());
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
    if (obj.getLetvReleaseDate() != null) {
      json.put("letvReleaseDate", obj.getLetvReleaseDate());
    }
    if (obj.getMaker() != null) {
      json.put("maker", obj.getMaker());
    }
    if (obj.getMakerCompany() != null) {
      json.put("makerCompany", obj.getMakerCompany());
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
    if (obj.getMusic_authors() != null) {
      json.put("music_authors", obj.getMusic_authors());
    }
    if (obj.getNameCn() != null) {
      json.put("nameCn", obj.getNameCn());
    }
    if (obj.getNonPositiveStreams() != null) {
      json.put("nonPositiveStreams", obj.getNonPositiveStreams());
    }
    if (obj.getNowEpisodes() != null) {
      json.put("nowEpisodes", obj.getNowEpisodes());
    }
    if (obj.getNowIssue() != null) {
      json.put("nowIssue", obj.getNowIssue());
    }
    if (obj.getOriginator() != null) {
      json.put("originator", obj.getOriginator());
    }
    if (obj.getOst() != null) {
      JsonArray array = new JsonArray();
      obj.getOst().forEach(item -> array.add(item.toJson()));
      json.put("ost", array);
    }
    if (obj.getPayPlatform() != null) {
      json.put("payPlatform", obj.getPayPlatform());
    }
    if (obj.getPic() != null) {
      json.put("pic", obj.getPic());
    }
    if (obj.getPicCollections() != null) {
      JsonArray array = new JsonArray();
      obj.getPicCollections().forEach(item -> array.add(item.toJson()));
      json.put("picCollections", array);
    }
    if (obj.getPicOriginal() != null) {
      json.put("picOriginal", obj.getPicOriginal());
    }
    if (obj.getPlayPlatform() != null) {
      json.put("playPlatform", obj.getPlayPlatform());
    }
    if (obj.getPlayStatus() != null) {
      json.put("playStatus", obj.getPlayStatus());
    }
    if (obj.getPlayStreams() != null) {
      json.put("playStreams", obj.getPlayStreams());
    }
    if (obj.getPlayTv() != null) {
      json.put("playTv", obj.getPlayTv());
    }
    if (obj.getPlayTvName() != null) {
      json.put("playTvName", obj.getPlayTvName());
    }
    if (obj.getPopStyle() != null) {
      json.put("popStyle", obj.getPopStyle());
    }
    if (obj.getPopStyleName() != null) {
      json.put("popStyleName", obj.getPopStyleName());
    }
    if (obj.getPositiveStreams() != null) {
      json.put("positiveStreams", obj.getPositiveStreams());
    }
    if (obj.getProducer() != null) {
      json.put("producer", obj.getProducer());
    }
    if (obj.getProgramStyle() != null) {
      json.put("programStyle", obj.getProgramStyle());
    }
    if (obj.getProgramStyleName() != null) {
      json.put("programStyleName", obj.getProgramStyleName());
    }
    if (obj.getPushflag() != null) {
      json.put("pushflag", obj.getPushflag());
    }
    if (obj.getRecommLevel() != null) {
      json.put("recommLevel", obj.getRecommLevel());
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
    if (obj.getRelationAlbumId() != null) {
      json.put("relationAlbumId", obj.getRelationAlbumId());
    }
    if (obj.getRelationId() != null) {
      json.put("relationId", obj.getRelationId());
    }
    if (obj.getReleaseDate() != null) {
      json.put("releaseDate", obj.getReleaseDate());
    }
    if (obj.getSchool() != null) {
      json.put("school", obj.getSchool());
    }
    if (obj.getScore() != null) {
      json.put("score", obj.getScore());
    }
    if (obj.getScreenWriter() != null) {
      json.put("screenWriter", obj.getScreenWriter());
    }
    if (obj.getSecondCate() != null) {
      json.put("secondCate", obj.getSecondCate());
    }
    if (obj.getShortDesc() != null) {
      json.put("shortDesc", obj.getShortDesc());
    }
    if (obj.getSportsType() != null) {
      json.put("sportsType", obj.getSportsType());
    }
    if (obj.getSportsTypeName() != null) {
      json.put("sportsTypeName", obj.getSportsTypeName());
    }
    if (obj.getStarIds() != null) {
      JsonArray array = new JsonArray();
      obj.getStarIds().forEach(item -> array.add(item));
      json.put("starIds", array);
    }
    if (obj.getStarList() != null) {
      JsonArray array = new JsonArray();
      obj.getStarList().forEach(item -> array.add(item.toJson()));
      json.put("starList", array);
    }
    if (obj.getStarring() != null) {
      json.put("starring", obj.getStarring());
    }
    if (obj.getStarringIds() != null) {
      JsonArray array = new JsonArray();
      obj.getStarringIds().forEach(item -> array.add(item));
      json.put("starringIds", array);
    }
    if (obj.getStarringObj() != null) {
      JsonArray array = new JsonArray();
      obj.getStarringObj().forEach(item -> array.add(item.toJson()));
      json.put("starringObj", array);
    }
    if (obj.getStarringPlay() != null) {
      json.put("starringPlay", obj.getStarringPlay());
    }
    if (obj.getStatus() != null) {
      json.put("status", obj.getStatus());
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
    if (obj.getSupervise() != null) {
      json.put("supervise", obj.getSupervise());
    }
    if (obj.getTag() != null) {
      json.put("tag", obj.getTag());
    }
    if (obj.getThirdCate() != null) {
      json.put("thirdCate", obj.getThirdCate());
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
    if (obj.getTypeName() != null) {
      json.put("typeName", obj.getTypeName());
    }
    if (obj.getUpdateTime() != null) {
      json.put("updateTime", obj.getUpdateTime());
    }
    if (obj.getVarietyShow() != null) {
      json.put("varietyShow", obj.getVarietyShow());
    }
    if (obj.getVideoFollowTime() != null) {
      json.put("videoFollowTime", obj.getVideoFollowTime());
    }
    if (obj.getVideoId() != null) {
      json.put("videoId", obj.getVideoId());
    }
    if (obj.getVideoStreams() != null) {
      JsonArray array = new JsonArray();
      obj.getVideoStreams().forEach(item -> array.add(item));
      json.put("videoStreams", array);
    }
    if (obj.getVv() != null) {
      json.put("vv", obj.getVv());
    }
    if (obj.getWasu() != null) {
      json.put("wasu", obj.getWasu());
    }
  }
}