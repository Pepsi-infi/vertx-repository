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

package tp.cms.response;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;

/**
 * Converter for {@link tp.cms.response.ContentItem}.
 *
 * NOTE: This class has been automatically generated from the {@link tp.cms.response.ContentItem} original class using Vert.x codegen.
 */
public class ContentItemConverter {

  public static void fromJson(JsonObject json, ContentItem obj) {
    if (json.getValue("actor") instanceof String) {
      obj.setActor((String)json.getValue("actor"));
    }
    if (json.getValue("actorDesc") instanceof JsonArray) {
      java.util.ArrayList<java.lang.Object> list = new java.util.ArrayList<>();
      json.getJsonArray("actorDesc").forEach( item -> {
        if (item instanceof Object)
          list.add(item);
      });
      obj.setActorDesc(list);
    }
    if (json.getValue("actorPlay") instanceof String) {
      obj.setActorPlay((String)json.getValue("actorPlay"));
    }
    if (json.getValue("actorPlayPic") instanceof String) {
      obj.setActorPlayPic((String)json.getValue("actorPlayPic"));
    }
    if (json.getValue("albumType") instanceof JsonObject) {
      java.util.Map<String, java.lang.String> map = new java.util.LinkedHashMap<>();
      json.getJsonObject("albumType").forEach(entry -> {
        if (entry.getValue() instanceof String)
          map.put(entry.getKey(), (String)entry.getValue());
      });
      obj.setAlbumType(map);
    }
    if (json.getValue("alias") instanceof String) {
      obj.setAlias((String)json.getValue("alias"));
    }
    if (json.getValue("allowForgienPlatform") instanceof String) {
      obj.setAllowForgienPlatform((String)json.getValue("allowForgienPlatform"));
    }
    if (json.getValue("area") instanceof String) {
      obj.setArea((String)json.getValue("area"));
    }
    if (json.getValue("category") instanceof JsonObject) {
      java.util.Map<String, java.lang.String> map = new java.util.LinkedHashMap<>();
      json.getJsonObject("category").forEach(entry -> {
        if (entry.getValue() instanceof String)
          map.put(entry.getKey(), (String)entry.getValue());
      });
      obj.setCategory(map);
    }
    if (json.getValue("description") instanceof String) {
      obj.setDescription((String)json.getValue("description"));
    }
    if (json.getValue("directory") instanceof JsonObject) {
      java.util.Map<String, java.lang.String> map = new java.util.LinkedHashMap<>();
      json.getJsonObject("directory").forEach(entry -> {
        if (entry.getValue() instanceof String)
          map.put(entry.getKey(), (String)entry.getValue());
      });
      obj.setDirectory(map);
    }
    if (json.getValue("downloadPlatform") instanceof JsonObject) {
      java.util.Map<String, java.lang.String> map = new java.util.LinkedHashMap<>();
      json.getJsonObject("downloadPlatform").forEach(entry -> {
        if (entry.getValue() instanceof String)
          map.put(entry.getKey(), (String)entry.getValue());
      });
      obj.setDownloadPlatform(map);
    }
    if (json.getValue("duration") instanceof Number) {
      obj.setDuration(((Number)json.getValue("duration")).longValue());
    }
    if (json.getValue("episode") instanceof String) {
      obj.setEpisode((String)json.getValue("episode"));
    }
    if (json.getValue("id") instanceof String) {
      obj.setId((String)json.getValue("id"));
    }
    if (json.getValue("isEnd") instanceof String) {
      obj.setIsEnd((String)json.getValue("isEnd"));
    }
    if (json.getValue("isHomemade") instanceof String) {
      obj.setIsHomemade((String)json.getValue("isHomemade"));
    }
    if (json.getValue("issue") instanceof String) {
      obj.setIssue((String)json.getValue("issue"));
    }
    if (json.getValue("mid") instanceof String) {
      obj.setMid((String)json.getValue("mid"));
    }
    if (json.getValue("nameCn") instanceof String) {
      obj.setNameCn((String)json.getValue("nameCn"));
    }
    if (json.getValue("nameEn") instanceof String) {
      obj.setNameEn((String)json.getValue("nameEn"));
    }
    if (json.getValue("nowEpisodes") instanceof String) {
      obj.setNowEpisodes((String)json.getValue("nowEpisodes"));
    }
    if (json.getValue("nowIssue") instanceof String) {
      obj.setNowIssue((String)json.getValue("nowIssue"));
    }
    if (json.getValue("officialUrl") instanceof String) {
      obj.setOfficialUrl((String)json.getValue("officialUrl"));
    }
    if (json.getValue("pic43") instanceof String) {
      obj.setPic43((String)json.getValue("pic43"));
    }
    if (json.getValue("picAll") instanceof JsonObject) {
      java.util.Map<String, java.lang.String> map = new java.util.LinkedHashMap<>();
      json.getJsonObject("picAll").forEach(entry -> {
        if (entry.getValue() instanceof String)
          map.put(entry.getKey(), (String)entry.getValue());
      });
      obj.setPicAll(map);
    }
    if (json.getValue("picCollections") instanceof JsonObject) {
      java.util.Map<String, java.lang.String> map = new java.util.LinkedHashMap<>();
      json.getJsonObject("picCollections").forEach(entry -> {
        if (entry.getValue() instanceof String)
          map.put(entry.getKey(), (String)entry.getValue());
      });
      obj.setPicCollections(map);
    }
    if (json.getValue("pid") instanceof Number) {
      obj.setPid(((Number)json.getValue("pid")).longValue());
    }
    if (json.getValue("platformVideoInfo") instanceof JsonObject) {
      java.util.Map<String, java.lang.String> map = new java.util.LinkedHashMap<>();
      json.getJsonObject("platformVideoInfo").forEach(entry -> {
        if (entry.getValue() instanceof String)
          map.put(entry.getKey(), (String)entry.getValue());
      });
      obj.setPlatformVideoInfo(map);
    }
    if (json.getValue("platformVideoNum") instanceof JsonObject) {
      java.util.Map<String, java.lang.String> map = new java.util.LinkedHashMap<>();
      json.getJsonObject("platformVideoNum").forEach(entry -> {
        if (entry.getValue() instanceof String)
          map.put(entry.getKey(), (String)entry.getValue());
      });
      obj.setPlatformVideoNum(map);
    }
    if (json.getValue("porder") instanceof String) {
      obj.setPorder((String)json.getValue("porder"));
    }
    if (json.getValue("relationId") instanceof String) {
      obj.setRelationId((String)json.getValue("relationId"));
    }
    if (json.getValue("releaseDate") instanceof String) {
      obj.setReleaseDate((String)json.getValue("releaseDate"));
    }
    if (json.getValue("rid") instanceof String) {
      obj.setRid((String)json.getValue("rid"));
    }
    if (json.getValue("rname") instanceof String) {
      obj.setRname((String)json.getValue("rname"));
    }
    if (json.getValue("singer") instanceof String) {
      obj.setSinger((String)json.getValue("singer"));
    }
    if (json.getValue("subCategory") instanceof JsonObject) {
      java.util.Map<String, java.lang.String> map = new java.util.LinkedHashMap<>();
      json.getJsonObject("subCategory").forEach(entry -> {
        if (entry.getValue() instanceof String)
          map.put(entry.getKey(), (String)entry.getValue());
      });
      obj.setSubCategory(map);
    }
    if (json.getValue("subTitle") instanceof String) {
      obj.setSubTitle((String)json.getValue("subTitle"));
    }
    if (json.getValue("tag") instanceof String) {
      obj.setTag((String)json.getValue("tag"));
    }
    if (json.getValue("transCodePrefix") instanceof String) {
      obj.setTransCodePrefix((String)json.getValue("transCodePrefix"));
    }
    if (json.getValue("videoType") instanceof JsonObject) {
      java.util.Map<String, java.lang.String> map = new java.util.LinkedHashMap<>();
      json.getJsonObject("videoType").forEach(entry -> {
        if (entry.getValue() instanceof String)
          map.put(entry.getKey(), (String)entry.getValue());
      });
      obj.setVideoType(map);
    }
  }

  public static void toJson(ContentItem obj, JsonObject json) {
    if (obj.getActor() != null) {
      json.put("actor", obj.getActor());
    }
    if (obj.getActorDesc() != null) {
      JsonArray array = new JsonArray();
      obj.getActorDesc().forEach(item -> array.add(item));
      json.put("actorDesc", array);
    }
    if (obj.getActorPlay() != null) {
      json.put("actorPlay", obj.getActorPlay());
    }
    if (obj.getActorPlayPic() != null) {
      json.put("actorPlayPic", obj.getActorPlayPic());
    }
    if (obj.getAlbumType() != null) {
      JsonObject map = new JsonObject();
      obj.getAlbumType().forEach((key,value) -> map.put(key, value));
      json.put("albumType", map);
    }
    if (obj.getAlias() != null) {
      json.put("alias", obj.getAlias());
    }
    if (obj.getAllowForgienPlatform() != null) {
      json.put("allowForgienPlatform", obj.getAllowForgienPlatform());
    }
    if (obj.getArea() != null) {
      json.put("area", obj.getArea());
    }
    if (obj.getCategory() != null) {
      JsonObject map = new JsonObject();
      obj.getCategory().forEach((key,value) -> map.put(key, value));
      json.put("category", map);
    }
    if (obj.getDescription() != null) {
      json.put("description", obj.getDescription());
    }
    if (obj.getDirectory() != null) {
      JsonObject map = new JsonObject();
      obj.getDirectory().forEach((key,value) -> map.put(key, value));
      json.put("directory", map);
    }
    if (obj.getDownloadPlatform() != null) {
      JsonObject map = new JsonObject();
      obj.getDownloadPlatform().forEach((key,value) -> map.put(key, value));
      json.put("downloadPlatform", map);
    }
    if (obj.getDuration() != null) {
      json.put("duration", obj.getDuration());
    }
    if (obj.getEpisode() != null) {
      json.put("episode", obj.getEpisode());
    }
    if (obj.getId() != null) {
      json.put("id", obj.getId());
    }
    if (obj.getIsEnd() != null) {
      json.put("isEnd", obj.getIsEnd());
    }
    if (obj.getIsHomemade() != null) {
      json.put("isHomemade", obj.getIsHomemade());
    }
    if (obj.getIssue() != null) {
      json.put("issue", obj.getIssue());
    }
    if (obj.getMid() != null) {
      json.put("mid", obj.getMid());
    }
    if (obj.getNameCn() != null) {
      json.put("nameCn", obj.getNameCn());
    }
    if (obj.getNameEn() != null) {
      json.put("nameEn", obj.getNameEn());
    }
    if (obj.getNowEpisodes() != null) {
      json.put("nowEpisodes", obj.getNowEpisodes());
    }
    if (obj.getNowIssue() != null) {
      json.put("nowIssue", obj.getNowIssue());
    }
    if (obj.getOfficialUrl() != null) {
      json.put("officialUrl", obj.getOfficialUrl());
    }
    if (obj.getPic43() != null) {
      json.put("pic43", obj.getPic43());
    }
    if (obj.getPicAll() != null) {
      JsonObject map = new JsonObject();
      obj.getPicAll().forEach((key,value) -> map.put(key, value));
      json.put("picAll", map);
    }
    if (obj.getPicCollections() != null) {
      JsonObject map = new JsonObject();
      obj.getPicCollections().forEach((key,value) -> map.put(key, value));
      json.put("picCollections", map);
    }
    if (obj.getPid() != null) {
      json.put("pid", obj.getPid());
    }
    if (obj.getPlatformVideoInfo() != null) {
      JsonObject map = new JsonObject();
      obj.getPlatformVideoInfo().forEach((key,value) -> map.put(key, value));
      json.put("platformVideoInfo", map);
    }
    if (obj.getPlatformVideoNum() != null) {
      JsonObject map = new JsonObject();
      obj.getPlatformVideoNum().forEach((key,value) -> map.put(key, value));
      json.put("platformVideoNum", map);
    }
    if (obj.getPorder() != null) {
      json.put("porder", obj.getPorder());
    }
    if (obj.getRelationId() != null) {
      json.put("relationId", obj.getRelationId());
    }
    if (obj.getReleaseDate() != null) {
      json.put("releaseDate", obj.getReleaseDate());
    }
    if (obj.getRid() != null) {
      json.put("rid", obj.getRid());
    }
    if (obj.getRname() != null) {
      json.put("rname", obj.getRname());
    }
    if (obj.getSinger() != null) {
      json.put("singer", obj.getSinger());
    }
    if (obj.getSubCategory() != null) {
      JsonObject map = new JsonObject();
      obj.getSubCategory().forEach((key,value) -> map.put(key, value));
      json.put("subCategory", map);
    }
    if (obj.getSubTitle() != null) {
      json.put("subTitle", obj.getSubTitle());
    }
    if (obj.getTag() != null) {
      json.put("tag", obj.getTag());
    }
    if (obj.getTransCodePrefix() != null) {
      json.put("transCodePrefix", obj.getTransCodePrefix());
    }
    if (obj.getVideoType() != null) {
      JsonObject map = new JsonObject();
      obj.getVideoType().forEach((key,value) -> map.put(key, value));
      json.put("videoType", map);
    }
  }
}