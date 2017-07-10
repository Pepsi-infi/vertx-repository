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

package service.dto.channelPage;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;

/**
 * Converter for {@link service.dto.channelPage.BlockDataDto}.
 *
 * NOTE: This class has been automatically generated from the {@link service.dto.channelPage.BlockDataDto} original class using Vert.x codegen.
 */
public class BlockDataDtoConverter {

  public static void fromJson(JsonObject json, BlockDataDto obj) {
    if (json.getValue("addOnId") instanceof Number) {
      obj.setAddOnId(((Number)json.getValue("addOnId")).intValue());
    }
    if (json.getValue("albumName") instanceof String) {
      obj.setAlbumName((String)json.getValue("albumName"));
    }
    if (json.getValue("albumType") instanceof String) {
      obj.setAlbumType((String)json.getValue("albumType"));
    }
    if (json.getValue("area") instanceof String) {
      obj.setArea((String)json.getValue("area"));
    }
    if (json.getValue("at") instanceof String) {
      obj.setAt((String)json.getValue("at"));
    }
    if (json.getValue("audioId") instanceof Number) {
      obj.setAudioId(((Number)json.getValue("audioId")).longValue());
    }
    if (json.getValue("cid") instanceof Number) {
      obj.setCid(((Number)json.getValue("cid")).intValue());
    }
    if (json.getValue("cmsid") instanceof String) {
      obj.setCmsid((String)json.getValue("cmsid"));
    }
    if (json.getValue("cname") instanceof String) {
      obj.setCname((String)json.getValue("cname"));
    }
    if (json.getValue("cornerLabel") instanceof String) {
      obj.setCornerLabel((String)json.getValue("cornerLabel"));
    }
    if (json.getValue("dataUrl") instanceof String) {
      obj.setDataUrl((String)json.getValue("dataUrl"));
    }
    if (json.getValue("defaultStream") instanceof String) {
      obj.setDefaultStream((String)json.getValue("defaultStream"));
    }
    if (json.getValue("description") instanceof String) {
      obj.setDescription((String)json.getValue("description"));
    }
    if (json.getValue("director") instanceof String) {
      obj.setDirector((String)json.getValue("director"));
    }
    if (json.getValue("duration") instanceof String) {
      obj.setDuration((String)json.getValue("duration"));
    }
    if (json.getValue("episode") instanceof String) {
      obj.setEpisode((String)json.getValue("episode"));
    }
    if (json.getValue("guest") instanceof String) {
      obj.setGuest((String)json.getValue("guest"));
    }
    if (json.getValue("guestImgUrl") instanceof String) {
      obj.setGuestImgUrl((String)json.getValue("guestImgUrl"));
    }
    if (json.getValue("homeImgUrl") instanceof String) {
      obj.setHomeImgUrl((String)json.getValue("homeImgUrl"));
    }
    if (json.getValue("id") instanceof String) {
      obj.setId((String)json.getValue("id"));
    }
    if (json.getValue("isEnd") instanceof String) {
      obj.setIsEnd((String)json.getValue("isEnd"));
    }
    if (json.getValue("issue") instanceof Number) {
      obj.setIssue(((Number)json.getValue("issue")).intValue());
    }
    if (json.getValue("liveCode") instanceof String) {
      obj.setLiveCode((String)json.getValue("liveCode"));
    }
    if (json.getValue("liveUrl") instanceof String) {
      obj.setLiveUrl((String)json.getValue("liveUrl"));
    }
    if (json.getValue("liveid") instanceof String) {
      obj.setLiveid((String)json.getValue("liveid"));
    }
    if (json.getValue("name") instanceof String) {
      obj.setName((String)json.getValue("name"));
    }
    if (json.getValue("nowEpisodes") instanceof String) {
      obj.setNowEpisodes((String)json.getValue("nowEpisodes"));
    }
    if (json.getValue("pageid") instanceof String) {
      obj.setPageid((String)json.getValue("pageid"));
    }
    if (json.getValue("pay") instanceof String) {
      obj.setPay((String)json.getValue("pay"));
    }
    if (json.getValue("pic") instanceof String) {
      obj.setPic((String)json.getValue("pic"));
    }
    if (json.getValue("picAll") instanceof JsonObject) {
      java.util.Map<String, java.lang.String> map = new java.util.LinkedHashMap<>();
      json.getJsonObject("picAll").forEach(entry -> {
        if (entry.getValue() instanceof String)
          map.put(entry.getKey(), (String)entry.getValue());
      });
      obj.setPicAll(map);
    }
    if (json.getValue("pid") instanceof Number) {
      obj.setPid(((Number)json.getValue("pid")).longValue());
    }
    if (json.getValue("playCount") instanceof String) {
      obj.setPlayCount((String)json.getValue("playCount"));
    }
    if (json.getValue("recArea") instanceof String) {
      obj.setRecArea((String)json.getValue("recArea"));
    }
    if (json.getValue("recBucket") instanceof String) {
      obj.setRecBucket((String)json.getValue("recBucket"));
    }
    if (json.getValue("recFragId") instanceof String) {
      obj.setRecFragId((String)json.getValue("recFragId"));
    }
    if (json.getValue("recReid") instanceof String) {
      obj.setRecReid((String)json.getValue("recReid"));
    }
    if (json.getValue("releaseDate") instanceof String) {
      obj.setReleaseDate((String)json.getValue("releaseDate"));
    }
    if (json.getValue("score") instanceof Number) {
      obj.setScore(((Number)json.getValue("score")).floatValue());
    }
    if (json.getValue("singer") instanceof String) {
      obj.setSinger((String)json.getValue("singer"));
    }
    if (json.getValue("skipAppInfo") instanceof String) {
      obj.setSkipAppInfo((String)json.getValue("skipAppInfo"));
    }
    if (json.getValue("source") instanceof Number) {
      obj.setSource(((Number)json.getValue("source")).intValue());
    }
    if (json.getValue("src") instanceof String) {
      obj.setSrc((String)json.getValue("src"));
    }
    if (json.getValue("subCategory") instanceof String) {
      obj.setSubCategory((String)json.getValue("subCategory"));
    }
    if (json.getValue("subTitle") instanceof String) {
      obj.setSubTitle((String)json.getValue("subTitle"));
    }
    if (json.getValue("tag") instanceof String) {
      obj.setTag((String)json.getValue("tag"));
    }
    if (json.getValue("tm") instanceof String) {
      obj.setTm((String)json.getValue("tm"));
    }
    if (json.getValue("type") instanceof String) {
      obj.setType((String)json.getValue("type"));
    }
    if (json.getValue("updateTime") instanceof Number) {
      obj.setUpdateTime(((Number)json.getValue("updateTime")).longValue());
    }
    if (json.getValue("varietyShow") instanceof String) {
      obj.setVarietyShow((String)json.getValue("varietyShow"));
    }
    if (json.getValue("vid") instanceof Number) {
      obj.setVid(((Number)json.getValue("vid")).longValue());
    }
    if (json.getValue("videoType") instanceof String) {
      obj.setVideoType((String)json.getValue("videoType"));
    }
    if (json.getValue("webUrl") instanceof String) {
      obj.setWebUrl((String)json.getValue("webUrl"));
    }
    if (json.getValue("webViewUrl") instanceof String) {
      obj.setWebViewUrl((String)json.getValue("webViewUrl"));
    }
    if (json.getValue("zid") instanceof Number) {
      obj.setZid(((Number)json.getValue("zid")).longValue());
    }
  }

  public static void toJson(BlockDataDto obj, JsonObject json) {
    if (obj.getAddOnId() != null) {
      json.put("addOnId", obj.getAddOnId());
    }
    if (obj.getAlbumName() != null) {
      json.put("albumName", obj.getAlbumName());
    }
    if (obj.getAlbumType() != null) {
      json.put("albumType", obj.getAlbumType());
    }
    if (obj.getArea() != null) {
      json.put("area", obj.getArea());
    }
    if (obj.getAt() != null) {
      json.put("at", obj.getAt());
    }
    if (obj.getAudioId() != null) {
      json.put("audioId", obj.getAudioId());
    }
    if (obj.getCid() != null) {
      json.put("cid", obj.getCid());
    }
    if (obj.getCmsid() != null) {
      json.put("cmsid", obj.getCmsid());
    }
    if (obj.getCname() != null) {
      json.put("cname", obj.getCname());
    }
    if (obj.getCornerLabel() != null) {
      json.put("cornerLabel", obj.getCornerLabel());
    }
    if (obj.getDataUrl() != null) {
      json.put("dataUrl", obj.getDataUrl());
    }
    if (obj.getDefaultStream() != null) {
      json.put("defaultStream", obj.getDefaultStream());
    }
    if (obj.getDescription() != null) {
      json.put("description", obj.getDescription());
    }
    if (obj.getDirector() != null) {
      json.put("director", obj.getDirector());
    }
    if (obj.getDuration() != null) {
      json.put("duration", obj.getDuration());
    }
    if (obj.getEpisode() != null) {
      json.put("episode", obj.getEpisode());
    }
    if (obj.getGuest() != null) {
      json.put("guest", obj.getGuest());
    }
    if (obj.getGuestImgUrl() != null) {
      json.put("guestImgUrl", obj.getGuestImgUrl());
    }
    if (obj.getHomeImgUrl() != null) {
      json.put("homeImgUrl", obj.getHomeImgUrl());
    }
    if (obj.getId() != null) {
      json.put("id", obj.getId());
    }
    if (obj.getIsEnd() != null) {
      json.put("isEnd", obj.getIsEnd());
    }
    if (obj.getIssue() != null) {
      json.put("issue", obj.getIssue());
    }
    if (obj.getLiveCode() != null) {
      json.put("liveCode", obj.getLiveCode());
    }
    if (obj.getLiveUrl() != null) {
      json.put("liveUrl", obj.getLiveUrl());
    }
    if (obj.getLiveid() != null) {
      json.put("liveid", obj.getLiveid());
    }
    if (obj.getName() != null) {
      json.put("name", obj.getName());
    }
    if (obj.getNowEpisodes() != null) {
      json.put("nowEpisodes", obj.getNowEpisodes());
    }
    if (obj.getPageid() != null) {
      json.put("pageid", obj.getPageid());
    }
    if (obj.getPay() != null) {
      json.put("pay", obj.getPay());
    }
    if (obj.getPic() != null) {
      json.put("pic", obj.getPic());
    }
    if (obj.getPicAll() != null) {
      JsonObject map = new JsonObject();
      obj.getPicAll().forEach((key,value) -> map.put(key, value));
      json.put("picAll", map);
    }
    if (obj.getPid() != null) {
      json.put("pid", obj.getPid());
    }
    if (obj.getPlayCount() != null) {
      json.put("playCount", obj.getPlayCount());
    }
    if (obj.getRecArea() != null) {
      json.put("recArea", obj.getRecArea());
    }
    if (obj.getRecBucket() != null) {
      json.put("recBucket", obj.getRecBucket());
    }
    if (obj.getRecFragId() != null) {
      json.put("recFragId", obj.getRecFragId());
    }
    if (obj.getRecReid() != null) {
      json.put("recReid", obj.getRecReid());
    }
    if (obj.getReleaseDate() != null) {
      json.put("releaseDate", obj.getReleaseDate());
    }
    if (obj.getScore() != null) {
      json.put("score", obj.getScore());
    }
    if (obj.getSinger() != null) {
      json.put("singer", obj.getSinger());
    }
    if (obj.getSkipAppInfo() != null) {
      json.put("skipAppInfo", obj.getSkipAppInfo());
    }
    json.put("source", obj.getSource());
    if (obj.getSrc() != null) {
      json.put("src", obj.getSrc());
    }
    if (obj.getSubCategory() != null) {
      json.put("subCategory", obj.getSubCategory());
    }
    if (obj.getSubTitle() != null) {
      json.put("subTitle", obj.getSubTitle());
    }
    if (obj.getTag() != null) {
      json.put("tag", obj.getTag());
    }
    if (obj.getTm() != null) {
      json.put("tm", obj.getTm());
    }
    if (obj.getType() != null) {
      json.put("type", obj.getType());
    }
    if (obj.getUpdateTime() != null) {
      json.put("updateTime", obj.getUpdateTime());
    }
    if (obj.getVarietyShow() != null) {
      json.put("varietyShow", obj.getVarietyShow());
    }
    if (obj.getVid() != null) {
      json.put("vid", obj.getVid());
    }
    if (obj.getVideoType() != null) {
      json.put("videoType", obj.getVideoType());
    }
    if (obj.getWebUrl() != null) {
      json.put("webUrl", obj.getWebUrl());
    }
    if (obj.getWebViewUrl() != null) {
      json.put("webViewUrl", obj.getWebViewUrl());
    }
    if (obj.getZid() != null) {
      json.put("zid", obj.getZid());
    }
  }
}