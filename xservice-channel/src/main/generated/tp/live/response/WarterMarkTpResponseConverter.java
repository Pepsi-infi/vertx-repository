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

package tp.live.response;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;

/**
 * Converter for {@link tp.live.response.WarterMarkTpResponse}.
 *
 * NOTE: This class has been automatically generated from the {@link tp.live.response.WarterMarkTpResponse} original class using Vert.x codegen.
 */
public class WarterMarkTpResponseConverter {

  public static void fromJson(JsonObject json, WarterMarkTpResponse obj) {
    if (json.getValue("cycleNum") instanceof Number) {
      obj.setCycleNum(((Number)json.getValue("cycleNum")).intValue());
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
    if (json.getValue("name") instanceof String) {
      obj.setName((String)json.getValue("name"));
    }
    if (json.getValue("pos") instanceof Number) {
      obj.setPos(((Number)json.getValue("pos")).intValue());
    }
    if (json.getValue("preDuration") instanceof Number) {
      obj.setPreDuration(((Number)json.getValue("preDuration")).intValue());
    }
    if (json.getValue("resourceItemList") instanceof JsonArray) {
      java.util.ArrayList<tp.live.response.ResourceItem> list = new java.util.ArrayList<>();
      json.getJsonArray("resourceItemList").forEach( item -> {
        if (item instanceof JsonObject)
          list.add(new tp.live.response.ResourceItem((JsonObject)item));
      });
      obj.setResourceItemList(list);
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

  public static void toJson(WarterMarkTpResponse obj, JsonObject json) {
    if (obj.getCycleNum() != null) {
      json.put("cycleNum", obj.getCycleNum());
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
    if (obj.getName() != null) {
      json.put("name", obj.getName());
    }
    if (obj.getPos() != null) {
      json.put("pos", obj.getPos());
    }
    if (obj.getPreDuration() != null) {
      json.put("preDuration", obj.getPreDuration());
    }
    if (obj.getResourceItemList() != null) {
      JsonArray array = new JsonArray();
      obj.getResourceItemList().forEach(item -> array.add(item.toJson()));
      json.put("resourceItemList", array);
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