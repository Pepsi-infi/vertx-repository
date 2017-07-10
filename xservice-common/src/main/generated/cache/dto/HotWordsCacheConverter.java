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
 * Converter for {@link cache.dto.HotWordsCache}.
 *
 * NOTE: This class has been automatically generated from the {@link cache.dto.HotWordsCache} original class using Vert.x codegen.
 */
public class HotWordsCacheConverter {

  public static void fromJson(JsonObject json, HotWordsCache obj) {
    if (json.getValue("attention") instanceof String) {
      obj.setAttention((String)json.getValue("attention"));
    }
    if (json.getValue("categoryType") instanceof String) {
      obj.setCategoryType((String)json.getValue("categoryType"));
    }
    if (json.getValue("id") instanceof Number) {
      obj.setId(((Number)json.getValue("id")).longValue());
    }
    if (json.getValue("img") instanceof String) {
      obj.setImg((String)json.getValue("img"));
    }
    if (json.getValue("name") instanceof String) {
      obj.setName((String)json.getValue("name"));
    }
    if (json.getValue("type") instanceof Number) {
      obj.setType(((Number)json.getValue("type")).intValue());
    }
  }

  public static void toJson(HotWordsCache obj, JsonObject json) {
    if (obj.getAttention() != null) {
      json.put("attention", obj.getAttention());
    }
    if (obj.getCategoryType() != null) {
      json.put("categoryType", obj.getCategoryType());
    }
    if (obj.getId() != null) {
      json.put("id", obj.getId());
    }
    if (obj.getImg() != null) {
      json.put("img", obj.getImg());
    }
    if (obj.getName() != null) {
      json.put("name", obj.getName());
    }
    if (obj.getType() != null) {
      json.put("type", obj.getType());
    }
  }
}