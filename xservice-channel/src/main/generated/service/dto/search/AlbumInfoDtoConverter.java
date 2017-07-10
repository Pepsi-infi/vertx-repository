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

package service.dto.search;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;

/**
 * Converter for {@link service.dto.search.AlbumInfoDto}.
 *
 * NOTE: This class has been automatically generated from the {@link service.dto.search.AlbumInfoDto} original class using Vert.x codegen.
 */
public class AlbumInfoDtoConverter {

  public static void fromJson(JsonObject json, AlbumInfoDto obj) {
    if (json.getValue("area") instanceof String) {
      obj.setArea((String)json.getValue("area"));
    }
    if (json.getValue("cid") instanceof Number) {
      obj.setCid(((Number)json.getValue("cid")).intValue());
    }
    if (json.getValue("cornerLabel") instanceof String) {
      obj.setCornerLabel((String)json.getValue("cornerLabel"));
    }
    if (json.getValue("dataType") instanceof Number) {
      obj.setDataType(((Number)json.getValue("dataType")).intValue());
    }
    if (json.getValue("desc") instanceof String) {
      obj.setDesc((String)json.getValue("desc"));
    }
    if (json.getValue("director") instanceof String) {
      obj.setDirector((String)json.getValue("director"));
    }
    if (json.getValue("duration") instanceof String) {
      obj.setDuration((String)json.getValue("duration"));
    }
    if (json.getValue("episodes") instanceof String) {
      obj.setEpisodes((String)json.getValue("episodes"));
    }
    if (json.getValue("images") instanceof JsonObject) {
      java.util.Map<String, java.lang.String> map = new java.util.LinkedHashMap<>();
      json.getJsonObject("images").forEach(entry -> {
        if (entry.getValue() instanceof String)
          map.put(entry.getKey(), (String)entry.getValue());
      });
      obj.setImages(map);
    }
    if (json.getValue("isEnd") instanceof String) {
      obj.setIsEnd((String)json.getValue("isEnd"));
    }
    if (json.getValue("name") instanceof String) {
      obj.setName((String)json.getValue("name"));
    }
    if (json.getValue("nowEpisodes") instanceof String) {
      obj.setNowEpisodes((String)json.getValue("nowEpisodes"));
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
    if (json.getValue("playCount") instanceof Number) {
      obj.setPlayCount(((Number)json.getValue("playCount")).longValue());
    }
    if (json.getValue("releaseDate") instanceof String) {
      obj.setReleaseDate((String)json.getValue("releaseDate"));
    }
    if (json.getValue("score") instanceof Number) {
      obj.setScore(((Number)json.getValue("score")).floatValue());
    }
    if (json.getValue("star") instanceof String) {
      obj.setStar((String)json.getValue("star"));
    }
    if (json.getValue("subCategory") instanceof String) {
      obj.setSubCategory((String)json.getValue("subCategory"));
    }
    if (json.getValue("subTitle") instanceof String) {
      obj.setSubTitle((String)json.getValue("subTitle"));
    }
    if (json.getValue("updateTime") instanceof Number) {
      obj.setUpdateTime(((Number)json.getValue("updateTime")).longValue());
    }
    if (json.getValue("url") instanceof String) {
      obj.setUrl((String)json.getValue("url"));
    }
    if (json.getValue("vid") instanceof Number) {
      obj.setVid(((Number)json.getValue("vid")).longValue());
    }
    if (json.getValue("zid") instanceof Number) {
      obj.setZid(((Number)json.getValue("zid")).longValue());
    }
  }

  public static void toJson(AlbumInfoDto obj, JsonObject json) {
    if (obj.getArea() != null) {
      json.put("area", obj.getArea());
    }
    if (obj.getCid() != null) {
      json.put("cid", obj.getCid());
    }
    if (obj.getCornerLabel() != null) {
      json.put("cornerLabel", obj.getCornerLabel());
    }
    if (obj.getDataType() != null) {
      json.put("dataType", obj.getDataType());
    }
    if (obj.getDesc() != null) {
      json.put("desc", obj.getDesc());
    }
    if (obj.getDirector() != null) {
      json.put("director", obj.getDirector());
    }
    if (obj.getDuration() != null) {
      json.put("duration", obj.getDuration());
    }
    if (obj.getEpisodes() != null) {
      json.put("episodes", obj.getEpisodes());
    }
    if (obj.getImages() != null) {
      JsonObject map = new JsonObject();
      obj.getImages().forEach((key,value) -> map.put(key, value));
      json.put("images", map);
    }
    if (obj.getIsEnd() != null) {
      json.put("isEnd", obj.getIsEnd());
    }
    if (obj.getName() != null) {
      json.put("name", obj.getName());
    }
    if (obj.getNowEpisodes() != null) {
      json.put("nowEpisodes", obj.getNowEpisodes());
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
    if (obj.getReleaseDate() != null) {
      json.put("releaseDate", obj.getReleaseDate());
    }
    if (obj.getScore() != null) {
      json.put("score", obj.getScore());
    }
    if (obj.getStar() != null) {
      json.put("star", obj.getStar());
    }
    if (obj.getSubCategory() != null) {
      json.put("subCategory", obj.getSubCategory());
    }
    if (obj.getSubTitle() != null) {
      json.put("subTitle", obj.getSubTitle());
    }
    if (obj.getUpdateTime() != null) {
      json.put("updateTime", obj.getUpdateTime());
    }
    if (obj.getUrl() != null) {
      json.put("url", obj.getUrl());
    }
    if (obj.getVid() != null) {
      json.put("vid", obj.getVid());
    }
    if (obj.getZid() != null) {
      json.put("zid", obj.getZid());
    }
  }
}