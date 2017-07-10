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

package search.response;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;

/**
 * Converter for {@link search.response.SearchMixResult}.
 *
 * NOTE: This class has been automatically generated from the {@link search.response.SearchMixResult} original class using Vert.x codegen.
 */
public class SearchMixResultConverter {

  public static void fromJson(JsonObject json, SearchMixResult obj) {
    if (json.getValue("aid") instanceof Number) {
      obj.setAid(((Number)json.getValue("aid")).longValue());
    }
    if (json.getValue("albumSrc") instanceof String) {
      obj.setAlbumSrc((String)json.getValue("albumSrc"));
    }
    if (json.getValue("area") instanceof String) {
      obj.setArea((String)json.getValue("area"));
    }
    if (json.getValue("areaName") instanceof String) {
      obj.setAreaName((String)json.getValue("areaName"));
    }
    if (json.getValue("birthday") instanceof String) {
      obj.setBirthday((String)json.getValue("birthday"));
    }
    if (json.getValue("category") instanceof Number) {
      obj.setCategory(((Number)json.getValue("category")).intValue());
    }
    if (json.getValue("categoryName") instanceof String) {
      obj.setCategoryName((String)json.getValue("categoryName"));
    }
    if (json.getValue("ctime") instanceof String) {
      obj.setCtime((String)json.getValue("ctime"));
    }
    if (json.getValue("dataType") instanceof Number) {
      obj.setDataType(((Number)json.getValue("dataType")).intValue());
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
    if (json.getValue("display") instanceof String) {
      obj.setDisplay((String)json.getValue("display"));
    }
    if (json.getValue("display_tag") instanceof String) {
      obj.setDisplay_tag((String)json.getValue("display_tag"));
    }
    if (json.getValue("duration") instanceof String) {
      obj.setDuration((String)json.getValue("duration"));
    }
    if (json.getValue("englishName") instanceof String) {
      obj.setEnglishName((String)json.getValue("englishName"));
    }
    if (json.getValue("episodes") instanceof String) {
      obj.setEpisodes((String)json.getValue("episodes"));
    }
    if (json.getValue("firstWord") instanceof String) {
      obj.setFirstWord((String)json.getValue("firstWord"));
    }
    if (json.getValue("gender") instanceof String) {
      obj.setGender((String)json.getValue("gender"));
    }
    if (json.getValue("images") instanceof JsonObject) {
      java.util.Map<String, java.lang.String> map = new java.util.LinkedHashMap<>();
      json.getJsonObject("images").forEach(entry -> {
        if (entry.getValue() instanceof String)
          map.put(entry.getKey(), (String)entry.getValue());
      });
      obj.setImages(map);
    }
    if (json.getValue("innerCode") instanceof String) {
      obj.setInnerCode((String)json.getValue("innerCode"));
    }
    if (json.getValue("isActor") instanceof String) {
      obj.setIsActor((String)json.getValue("isActor"));
    }
    if (json.getValue("isAvail") instanceof String) {
      obj.setIsAvail((String)json.getValue("isAvail"));
    }
    if (json.getValue("isDirector") instanceof String) {
      obj.setIsDirector((String)json.getValue("isDirector"));
    }
    if (json.getValue("isEnd") instanceof String) {
      obj.setIsEnd((String)json.getValue("isEnd"));
    }
    if (json.getValue("ispay") instanceof String) {
      obj.setIspay((String)json.getValue("ispay"));
    }
    if (json.getValue("list_order") instanceof String) {
      obj.setList_order((String)json.getValue("list_order"));
    }
    if (json.getValue("mtime") instanceof String) {
      obj.setMtime((String)json.getValue("mtime"));
    }
    if (json.getValue("name") instanceof String) {
      obj.setName((String)json.getValue("name"));
    }
    if (json.getValue("nameJianpin") instanceof String) {
      obj.setNameJianpin((String)json.getValue("nameJianpin"));
    }
    if (json.getValue("nameQuanpin") instanceof String) {
      obj.setNameQuanpin((String)json.getValue("nameQuanpin"));
    }
    if (json.getValue("nowEpisodes") instanceof String) {
      obj.setNowEpisodes((String)json.getValue("nowEpisodes"));
    }
    if (json.getValue("otherName") instanceof String) {
      obj.setOtherName((String)json.getValue("otherName"));
    }
    if (json.getValue("playCount") instanceof Number) {
      obj.setPlayCount(((Number)json.getValue("playCount")).longValue());
    }
    if (json.getValue("postH1") instanceof String) {
      obj.setPostH1((String)json.getValue("postH1"));
    }
    if (json.getValue("postH2") instanceof String) {
      obj.setPostH2((String)json.getValue("postH2"));
    }
    if (json.getValue("postOrgin") instanceof String) {
      obj.setPostOrgin((String)json.getValue("postOrgin"));
    }
    if (json.getValue("postS1") instanceof String) {
      obj.setPostS1((String)json.getValue("postS1"));
    }
    if (json.getValue("postS2") instanceof String) {
      obj.setPostS2((String)json.getValue("postS2"));
    }
    if (json.getValue("postS3") instanceof String) {
      obj.setPostS3((String)json.getValue("postS3"));
    }
    if (json.getValue("pushFlag") instanceof JsonObject) {
      java.util.Map<String, java.lang.String> map = new java.util.LinkedHashMap<>();
      json.getJsonObject("pushFlag").forEach(entry -> {
        if (entry.getValue() instanceof String)
          map.put(entry.getKey(), (String)entry.getValue());
      });
      obj.setPushFlag(map);
    }
    if (json.getValue("rating") instanceof String) {
      obj.setRating((String)json.getValue("rating"));
    }
    if (json.getValue("releaseDate") instanceof String) {
      obj.setReleaseDate((String)json.getValue("releaseDate"));
    }
    if (json.getValue("sid") instanceof Number) {
      obj.setSid(((Number)json.getValue("sid")).longValue());
    }
    if (json.getValue("subCategoryName") instanceof String) {
      obj.setSubCategoryName((String)json.getValue("subCategoryName"));
    }
    if (json.getValue("subname") instanceof String) {
      obj.setSubname((String)json.getValue("subname"));
    }
    if (json.getValue("title") instanceof String) {
      obj.setTitle((String)json.getValue("title"));
    }
    if (json.getValue("trueName") instanceof String) {
      obj.setTrueName((String)json.getValue("trueName"));
    }
    if (json.getValue("url") instanceof String) {
      obj.setUrl((String)json.getValue("url"));
    }
    if (json.getValue("vid") instanceof Number) {
      obj.setVid(((Number)json.getValue("vid")).longValue());
    }
    if (json.getValue("videoType") instanceof String) {
      obj.setVideoType((String)json.getValue("videoType"));
    }
  }

  public static void toJson(SearchMixResult obj, JsonObject json) {
    if (obj.getAid() != null) {
      json.put("aid", obj.getAid());
    }
    if (obj.getAlbumSrc() != null) {
      json.put("albumSrc", obj.getAlbumSrc());
    }
    if (obj.getArea() != null) {
      json.put("area", obj.getArea());
    }
    if (obj.getAreaName() != null) {
      json.put("areaName", obj.getAreaName());
    }
    if (obj.getBirthday() != null) {
      json.put("birthday", obj.getBirthday());
    }
    if (obj.getCategory() != null) {
      json.put("category", obj.getCategory());
    }
    if (obj.getCategoryName() != null) {
      json.put("categoryName", obj.getCategoryName());
    }
    if (obj.getCtime() != null) {
      json.put("ctime", obj.getCtime());
    }
    if (obj.getDataType() != null) {
      json.put("dataType", obj.getDataType());
    }
    if (obj.getDescription() != null) {
      json.put("description", obj.getDescription());
    }
    if (obj.getDirector() != null) {
      json.put("director", obj.getDirector());
    }
    if (obj.getDirectory() != null) {
      JsonObject map = new JsonObject();
      obj.getDirectory().forEach((key,value) -> map.put(key, value));
      json.put("directory", map);
    }
    if (obj.getDisplay() != null) {
      json.put("display", obj.getDisplay());
    }
    if (obj.getDisplay_tag() != null) {
      json.put("display_tag", obj.getDisplay_tag());
    }
    if (obj.getDuration() != null) {
      json.put("duration", obj.getDuration());
    }
    if (obj.getEnglishName() != null) {
      json.put("englishName", obj.getEnglishName());
    }
    if (obj.getEpisodes() != null) {
      json.put("episodes", obj.getEpisodes());
    }
    if (obj.getFirstWord() != null) {
      json.put("firstWord", obj.getFirstWord());
    }
    if (obj.getGender() != null) {
      json.put("gender", obj.getGender());
    }
    if (obj.getImages() != null) {
      JsonObject map = new JsonObject();
      obj.getImages().forEach((key,value) -> map.put(key, value));
      json.put("images", map);
    }
    if (obj.getInnerCode() != null) {
      json.put("innerCode", obj.getInnerCode());
    }
    if (obj.getIsActor() != null) {
      json.put("isActor", obj.getIsActor());
    }
    if (obj.getIsAvail() != null) {
      json.put("isAvail", obj.getIsAvail());
    }
    if (obj.getIsDirector() != null) {
      json.put("isDirector", obj.getIsDirector());
    }
    if (obj.getIsEnd() != null) {
      json.put("isEnd", obj.getIsEnd());
    }
    if (obj.getIspay() != null) {
      json.put("ispay", obj.getIspay());
    }
    if (obj.getList_order() != null) {
      json.put("list_order", obj.getList_order());
    }
    if (obj.getMtime() != null) {
      json.put("mtime", obj.getMtime());
    }
    if (obj.getName() != null) {
      json.put("name", obj.getName());
    }
    if (obj.getNameJianpin() != null) {
      json.put("nameJianpin", obj.getNameJianpin());
    }
    if (obj.getNameQuanpin() != null) {
      json.put("nameQuanpin", obj.getNameQuanpin());
    }
    if (obj.getNowEpisodes() != null) {
      json.put("nowEpisodes", obj.getNowEpisodes());
    }
    if (obj.getOtherName() != null) {
      json.put("otherName", obj.getOtherName());
    }
    if (obj.getPlayCount() != null) {
      json.put("playCount", obj.getPlayCount());
    }
    if (obj.getPostH1() != null) {
      json.put("postH1", obj.getPostH1());
    }
    if (obj.getPostH2() != null) {
      json.put("postH2", obj.getPostH2());
    }
    if (obj.getPostOrgin() != null) {
      json.put("postOrgin", obj.getPostOrgin());
    }
    if (obj.getPostS1() != null) {
      json.put("postS1", obj.getPostS1());
    }
    if (obj.getPostS2() != null) {
      json.put("postS2", obj.getPostS2());
    }
    if (obj.getPostS3() != null) {
      json.put("postS3", obj.getPostS3());
    }
    if (obj.getPushFlag() != null) {
      JsonObject map = new JsonObject();
      obj.getPushFlag().forEach((key,value) -> map.put(key, value));
      json.put("pushFlag", map);
    }
    if (obj.getRating() != null) {
      json.put("rating", obj.getRating());
    }
    if (obj.getReleaseDate() != null) {
      json.put("releaseDate", obj.getReleaseDate());
    }
    if (obj.getSid() != null) {
      json.put("sid", obj.getSid());
    }
    if (obj.getStars() != null) {
      json.put("stars", obj.getStars());
    }
    if (obj.getSubCategoryName() != null) {
      json.put("subCategoryName", obj.getSubCategoryName());
    }
    if (obj.getSubname() != null) {
      json.put("subname", obj.getSubname());
    }
    if (obj.getTitle() != null) {
      json.put("title", obj.getTitle());
    }
    if (obj.getTrueName() != null) {
      json.put("trueName", obj.getTrueName());
    }
    if (obj.getUrl() != null) {
      json.put("url", obj.getUrl());
    }
    if (obj.getVid() != null) {
      json.put("vid", obj.getVid());
    }
    if (obj.getVideoType() != null) {
      json.put("videoType", obj.getVideoType());
    }
  }
}