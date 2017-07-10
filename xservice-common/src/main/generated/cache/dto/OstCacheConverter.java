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
 * Converter for {@link cache.dto.OstCache}.
 *
 * NOTE: This class has been automatically generated from the {@link cache.dto.OstCache} original class using Vert.x codegen.
 */
public class OstCacheConverter {

  public static void fromJson(JsonObject json, OstCache obj) {
    if (json.getValue("albumTypeCode") instanceof String) {
      obj.setAlbumTypeCode((String)json.getValue("albumTypeCode"));
    }
    if (json.getValue("albumTypeName") instanceof String) {
      obj.setAlbumTypeName((String)json.getValue("albumTypeName"));
    }
    if (json.getValue("areaCode") instanceof String) {
      obj.setAreaCode((String)json.getValue("areaCode"));
    }
    if (json.getValue("areaName") instanceof String) {
      obj.setAreaName((String)json.getValue("areaName"));
    }
    if (json.getValue("artist") instanceof String) {
      obj.setArtist((String)json.getValue("artist"));
    }
    if (json.getValue("categoryId") instanceof Number) {
      obj.setCategoryId(((Number)json.getValue("categoryId")).intValue());
    }
    if (json.getValue("categoryName") instanceof String) {
      obj.setCategoryName((String)json.getValue("categoryName"));
    }
    if (json.getValue("copyrightTypeCode") instanceof String) {
      obj.setCopyrightTypeCode((String)json.getValue("copyrightTypeCode"));
    }
    if (json.getValue("copyrightTypeName") instanceof String) {
      obj.setCopyrightTypeName((String)json.getValue("copyrightTypeName"));
    }
    if (json.getValue("description") instanceof String) {
      obj.setDescription((String)json.getValue("description"));
    }
    if (json.getValue("downloadPlatform") instanceof String) {
      obj.setDownloadPlatform((String)json.getValue("downloadPlatform"));
    }
    if (json.getValue("episode") instanceof Number) {
      obj.setEpisode(((Number)json.getValue("episode")).intValue());
    }
    if (json.getValue("id") instanceof Number) {
      obj.setId(((Number)json.getValue("id")).longValue());
    }
    if (json.getValue("img") instanceof String) {
      obj.setImg((String)json.getValue("img"));
    }
    if (json.getValue("issueCompany") instanceof String) {
      obj.setIssueCompany((String)json.getValue("issueCompany"));
    }
    if (json.getValue("key") instanceof String) {
      obj.setKey((String)json.getValue("key"));
    }
    if (json.getValue("languageCode") instanceof String) {
      obj.setLanguageCode((String)json.getValue("languageCode"));
    }
    if (json.getValue("languageName") instanceof String) {
      obj.setLanguageName((String)json.getValue("languageName"));
    }
    if (json.getValue("name") instanceof String) {
      obj.setName((String)json.getValue("name"));
    }
    if (json.getValue("picCollections") instanceof JsonArray) {
      java.util.ArrayList<cache.dto.PicAll> list = new java.util.ArrayList<>();
      json.getJsonArray("picCollections").forEach( item -> {
        if (item instanceof JsonObject)
          list.add(new cache.dto.PicAll((JsonObject)item));
      });
      obj.setPicCollections(list);
    }
    if (json.getValue("playPlatform") instanceof String) {
      obj.setPlayPlatform((String)json.getValue("playPlatform"));
    }
    if (json.getValue("releaseDate") instanceof String) {
      obj.setReleaseDate((String)json.getValue("releaseDate"));
    }
    if (json.getValue("sourceCode") instanceof String) {
      obj.setSourceCode((String)json.getValue("sourceCode"));
    }
    if (json.getValue("sourceName") instanceof String) {
      obj.setSourceName((String)json.getValue("sourceName"));
    }
    if (json.getValue("subTitle") instanceof String) {
      obj.setSubTitle((String)json.getValue("subTitle"));
    }
    if (json.getValue("type") instanceof String) {
      obj.setType((String)json.getValue("type"));
    }
    if (json.getValue("xiamiId") instanceof Number) {
      obj.setXiamiId(((Number)json.getValue("xiamiId")).intValue());
    }
  }

  public static void toJson(OstCache obj, JsonObject json) {
    if (obj.getAlbumTypeCode() != null) {
      json.put("albumTypeCode", obj.getAlbumTypeCode());
    }
    if (obj.getAlbumTypeName() != null) {
      json.put("albumTypeName", obj.getAlbumTypeName());
    }
    if (obj.getAreaCode() != null) {
      json.put("areaCode", obj.getAreaCode());
    }
    if (obj.getAreaName() != null) {
      json.put("areaName", obj.getAreaName());
    }
    if (obj.getArtist() != null) {
      json.put("artist", obj.getArtist());
    }
    if (obj.getCategoryId() != null) {
      json.put("categoryId", obj.getCategoryId());
    }
    if (obj.getCategoryName() != null) {
      json.put("categoryName", obj.getCategoryName());
    }
    if (obj.getCopyrightTypeCode() != null) {
      json.put("copyrightTypeCode", obj.getCopyrightTypeCode());
    }
    if (obj.getCopyrightTypeName() != null) {
      json.put("copyrightTypeName", obj.getCopyrightTypeName());
    }
    if (obj.getDescription() != null) {
      json.put("description", obj.getDescription());
    }
    if (obj.getDownloadPlatform() != null) {
      json.put("downloadPlatform", obj.getDownloadPlatform());
    }
    if (obj.getEpisode() != null) {
      json.put("episode", obj.getEpisode());
    }
    if (obj.getId() != null) {
      json.put("id", obj.getId());
    }
    if (obj.getImg() != null) {
      json.put("img", obj.getImg());
    }
    if (obj.getIssueCompany() != null) {
      json.put("issueCompany", obj.getIssueCompany());
    }
    if (obj.getKey() != null) {
      json.put("key", obj.getKey());
    }
    if (obj.getLanguageCode() != null) {
      json.put("languageCode", obj.getLanguageCode());
    }
    if (obj.getLanguageName() != null) {
      json.put("languageName", obj.getLanguageName());
    }
    if (obj.getName() != null) {
      json.put("name", obj.getName());
    }
    if (obj.getPicCollections() != null) {
      JsonArray array = new JsonArray();
      obj.getPicCollections().forEach(item -> array.add(item.toJson()));
      json.put("picCollections", array);
    }
    if (obj.getPlayPlatform() != null) {
      json.put("playPlatform", obj.getPlayPlatform());
    }
    if (obj.getReleaseDate() != null) {
      json.put("releaseDate", obj.getReleaseDate());
    }
    if (obj.getSourceCode() != null) {
      json.put("sourceCode", obj.getSourceCode());
    }
    if (obj.getSourceName() != null) {
      json.put("sourceName", obj.getSourceName());
    }
    if (obj.getSubTitle() != null) {
      json.put("subTitle", obj.getSubTitle());
    }
    if (obj.getType() != null) {
      json.put("type", obj.getType());
    }
    if (obj.getXiamiId() != null) {
      json.put("xiamiId", obj.getXiamiId());
    }
  }
}