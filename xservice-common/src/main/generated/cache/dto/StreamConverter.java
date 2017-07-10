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
 * Converter for {@link cache.dto.Stream}.
 *
 * NOTE: This class has been automatically generated from the {@link cache.dto.Stream} original class using Vert.x codegen.
 */
public class StreamConverter {

  public static void fromJson(JsonObject json, Stream obj) {
    if (json.getValue("bandWidth") instanceof String) {
      obj.setBandWidth((String)json.getValue("bandWidth"));
    }
    if (json.getValue("canDown") instanceof String) {
      obj.setCanDown((String)json.getValue("canDown"));
    }
    if (json.getValue("canPlay") instanceof String) {
      obj.setCanPlay((String)json.getValue("canPlay"));
    }
    if (json.getValue("code") instanceof String) {
      obj.setCode((String)json.getValue("code"));
    }
    if (json.getValue("fileSize") instanceof Number) {
      obj.setFileSize(((Number)json.getValue("fileSize")).longValue());
    }
    if (json.getValue("ifCharge") instanceof Number) {
      obj.setIfCharge(((Number)json.getValue("ifCharge")).intValue());
    }
    if (json.getValue("isDefault") instanceof Boolean) {
      obj.setIsDefault((Boolean)json.getValue("isDefault"));
    }
    if (json.getValue("kbps") instanceof String) {
      obj.setKbps((String)json.getValue("kbps"));
    }
    if (json.getValue("liveStreams") instanceof JsonArray) {
      java.util.ArrayList<cache.dto.Stream> list = new java.util.ArrayList<>();
      json.getJsonArray("liveStreams").forEach( item -> {
        if (item instanceof JsonObject)
          list.add(new cache.dto.Stream((JsonObject)item));
      });
      obj.setLiveStreams(list);
    }
    if (json.getValue("name") instanceof String) {
      obj.setName((String)json.getValue("name"));
    }
    if (json.getValue("playStreams") instanceof JsonArray) {
      java.util.ArrayList<cache.dto.Stream> list = new java.util.ArrayList<>();
      json.getJsonArray("playStreams").forEach( item -> {
        if (item instanceof JsonObject)
          list.add(new cache.dto.Stream((JsonObject)item));
      });
      obj.setPlayStreams(list);
    }
  }

  public static void toJson(Stream obj, JsonObject json) {
    if (obj.getBandWidth() != null) {
      json.put("bandWidth", obj.getBandWidth());
    }
    if (obj.getCanDown() != null) {
      json.put("canDown", obj.getCanDown());
    }
    if (obj.getCanPlay() != null) {
      json.put("canPlay", obj.getCanPlay());
    }
    if (obj.getCode() != null) {
      json.put("code", obj.getCode());
    }
    if (obj.getFileSize() != null) {
      json.put("fileSize", obj.getFileSize());
    }
    if (obj.getIfCharge() != null) {
      json.put("ifCharge", obj.getIfCharge());
    }
    if (obj.getIsDefault() != null) {
      json.put("isDefault", obj.getIsDefault());
    }
    if (obj.getKbps() != null) {
      json.put("kbps", obj.getKbps());
    }
    if (obj.getLiveStreams() != null) {
      JsonArray array = new JsonArray();
      obj.getLiveStreams().forEach(item -> array.add(item.toJson()));
      json.put("liveStreams", array);
    }
    if (obj.getName() != null) {
      json.put("name", obj.getName());
    }
    if (obj.getPlayStreams() != null) {
      JsonArray array = new JsonArray();
      obj.getPlayStreams().forEach(item -> array.add(item.toJson()));
      json.put("playStreams", array);
    }
  }
}