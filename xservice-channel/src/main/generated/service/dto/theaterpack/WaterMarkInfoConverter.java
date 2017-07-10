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

package service.dto.theaterpack;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;

/**
 * Converter for {@link service.dto.theaterpack.WaterMarkInfo}.
 *
 * NOTE: This class has been automatically generated from the {@link service.dto.theaterpack.WaterMarkInfo} original class using Vert.x codegen.
 */
public class WaterMarkInfoConverter {

  public static void fromJson(JsonObject json, WaterMarkInfo obj) {
    if (json.getValue("circleCount") instanceof Number) {
      obj.setCircleCount(((Number)json.getValue("circleCount")).intValue());
    }
    if (json.getValue("displayFrequency") instanceof Number) {
      obj.setDisplayFrequency(((Number)json.getValue("displayFrequency")).intValue());
    }
    if (json.getValue("displayType") instanceof Number) {
      obj.setDisplayType(((Number)json.getValue("displayType")).intValue());
    }
    if (json.getValue("id") instanceof Number) {
      obj.setId(((Number)json.getValue("id")).intValue());
    }
    if (json.getValue("materialList") instanceof JsonArray) {
      java.util.ArrayList<service.dto.theaterpack.MaterialInfo> list = new java.util.ArrayList<>();
      json.getJsonArray("materialList").forEach( item -> {
        if (item instanceof JsonObject)
          list.add(new service.dto.theaterpack.MaterialInfo((JsonObject)item));
      });
      obj.setMaterialList(list);
    }
    if (json.getValue("name") instanceof String) {
      obj.setName((String)json.getValue("name"));
    }
    if (json.getValue("position") instanceof Number) {
      obj.setPosition(((Number)json.getValue("position")).intValue());
    }
    if (json.getValue("preDuration") instanceof Number) {
      obj.setPreDuration(((Number)json.getValue("preDuration")).intValue());
    }
    if (json.getValue("status") instanceof Number) {
      obj.setStatus(((Number)json.getValue("status")).intValue());
    }
    if (json.getValue("style") instanceof Number) {
      obj.setStyle(((Number)json.getValue("style")).intValue());
    }
    if (json.getValue("type") instanceof Number) {
      obj.setType(((Number)json.getValue("type")).intValue());
    }
  }

  public static void toJson(WaterMarkInfo obj, JsonObject json) {
    if (obj.getCircleCount() != null) {
      json.put("circleCount", obj.getCircleCount());
    }
    if (obj.getDisplayFrequency() != null) {
      json.put("displayFrequency", obj.getDisplayFrequency());
    }
    if (obj.getDisplayType() != null) {
      json.put("displayType", obj.getDisplayType());
    }
    if (obj.getId() != null) {
      json.put("id", obj.getId());
    }
    if (obj.getMaterialList() != null) {
      JsonArray array = new JsonArray();
      obj.getMaterialList().forEach(item -> array.add(item.toJson()));
      json.put("materialList", array);
    }
    if (obj.getName() != null) {
      json.put("name", obj.getName());
    }
    if (obj.getPosition() != null) {
      json.put("position", obj.getPosition());
    }
    if (obj.getPreDuration() != null) {
      json.put("preDuration", obj.getPreDuration());
    }
    if (obj.getStatus() != null) {
      json.put("status", obj.getStatus());
    }
    if (obj.getStyle() != null) {
      json.put("style", obj.getStyle());
    }
    if (obj.getType() != null) {
      json.put("type", obj.getType());
    }
  }
}