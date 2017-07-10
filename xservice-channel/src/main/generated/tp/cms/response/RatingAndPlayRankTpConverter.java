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
 * Converter for {@link tp.cms.response.RatingAndPlayRankTp}.
 *
 * NOTE: This class has been automatically generated from the {@link tp.cms.response.RatingAndPlayRankTp} original class using Vert.x codegen.
 */
public class RatingAndPlayRankTpConverter {

  public static void fromJson(JsonObject json, RatingAndPlayRankTp obj) {
    if (json.getValue("cid") instanceof Number) {
      obj.setCid(((Number)json.getValue("cid")).intValue());
    }
    if (json.getValue("id") instanceof Number) {
      obj.setId(((Number)json.getValue("id")).longValue());
    }
    if (json.getValue("innerCode") instanceof String) {
      obj.setInnerCode((String)json.getValue("innerCode"));
    }
    if (json.getValue("name") instanceof String) {
      obj.setName((String)json.getValue("name"));
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
    if (json.getValue("rating") instanceof String) {
      obj.setRating((String)json.getValue("rating"));
    }
    if (json.getValue("subname") instanceof String) {
      obj.setSubname((String)json.getValue("subname"));
    }
    if (json.getValue("webUrl") instanceof String) {
      obj.setWebUrl((String)json.getValue("webUrl"));
    }
  }

  public static void toJson(RatingAndPlayRankTp obj, JsonObject json) {
    if (obj.getCid() != null) {
      json.put("cid", obj.getCid());
    }
    if (obj.getId() != null) {
      json.put("id", obj.getId());
    }
    if (obj.getInnerCode() != null) {
      json.put("innerCode", obj.getInnerCode());
    }
    if (obj.getName() != null) {
      json.put("name", obj.getName());
    }
    if (obj.getPlayPlatform() != null) {
      JsonObject map = new JsonObject();
      obj.getPlayPlatform().forEach((key,value) -> map.put(key, value));
      json.put("playPlatform", map);
    }
    if (obj.getPlaycount() != null) {
      json.put("playcount", obj.getPlaycount());
    }
    if (obj.getRating() != null) {
      json.put("rating", obj.getRating());
    }
    if (obj.getSubname() != null) {
      json.put("subname", obj.getSubname());
    }
    if (obj.getWebUrl() != null) {
      json.put("webUrl", obj.getWebUrl());
    }
  }
}