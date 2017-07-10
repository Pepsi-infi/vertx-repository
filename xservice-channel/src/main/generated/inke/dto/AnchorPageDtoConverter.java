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

package inke.dto;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;

/**
 * Converter for {@link inke.dto.AnchorPageDto}.
 *
 * NOTE: This class has been automatically generated from the {@link inke.dto.AnchorPageDto} original class using Vert.x codegen.
 */
public class AnchorPageDtoConverter {

  public static void fromJson(JsonObject json, AnchorPageDto obj) {
    if (json.getValue("banner") instanceof JsonArray) {
      java.util.ArrayList<inke.dto.BannerDto> list = new java.util.ArrayList<>();
      json.getJsonArray("banner").forEach( item -> {
        if (item instanceof JsonObject)
          list.add(new inke.dto.BannerDto((JsonObject)item));
      });
      obj.setBanner(list);
    }
    if (json.getValue("content") instanceof JsonArray) {
      java.util.ArrayList<inke.dto.AnchorDto> list = new java.util.ArrayList<>();
      json.getJsonArray("content").forEach( item -> {
        if (item instanceof JsonObject)
          list.add(new inke.dto.AnchorDto((JsonObject)item));
      });
      obj.setContent(list);
    }
    if (json.getValue("id") instanceof Number) {
      obj.setId(((Number)json.getValue("id")).intValue());
    }
    if (json.getValue("name") instanceof String) {
      obj.setName((String)json.getValue("name"));
    }
    if (json.getValue("page") instanceof Number) {
      obj.setPage(((Number)json.getValue("page")).intValue());
    }
    if (json.getValue("pageSize") instanceof Number) {
      obj.setPageSize(((Number)json.getValue("pageSize")).intValue());
    }
  }

  public static void toJson(AnchorPageDto obj, JsonObject json) {
    if (obj.getBanner() != null) {
      JsonArray array = new JsonArray();
      obj.getBanner().forEach(item -> array.add(item.toJson()));
      json.put("banner", array);
    }
    if (obj.getContent() != null) {
      JsonArray array = new JsonArray();
      obj.getContent().forEach(item -> array.add(item.toJson()));
      json.put("content", array);
    }
    if (obj.getId() != null) {
      json.put("id", obj.getId());
    }
    if (obj.getName() != null) {
      json.put("name", obj.getName());
    }
    if (obj.getPage() != null) {
      json.put("page", obj.getPage());
    }
    if (obj.getPageSize() != null) {
      json.put("pageSize", obj.getPageSize());
    }
  }
}