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
 * Converter for {@link inke.dto.AnchorDto}.
 *
 * NOTE: This class has been automatically generated from the {@link inke.dto.AnchorDto} original class using Vert.x codegen.
 */
public class AnchorDtoConverter {

  public static void fromJson(JsonObject json, AnchorDto obj) {
    if (json.getValue("city") instanceof String) {
      obj.setCity((String)json.getValue("city"));
    }
    if (json.getValue("id") instanceof String) {
      obj.setId((String)json.getValue("id"));
    }
    if (json.getValue("liveId") instanceof String) {
      obj.setLiveId((String)json.getValue("liveId"));
    }
    if (json.getValue("nick") instanceof String) {
      obj.setNick((String)json.getValue("nick"));
    }
    if (json.getValue("onlineNum") instanceof Number) {
      obj.setOnlineNum(((Number)json.getValue("onlineNum")).longValue());
    }
    if (json.getValue("portrait") instanceof String) {
      obj.setPortrait((String)json.getValue("portrait"));
    }
    if (json.getValue("source") instanceof Number) {
      obj.setSource(((Number)json.getValue("source")).intValue());
    }
  }

  public static void toJson(AnchorDto obj, JsonObject json) {
    if (obj.getCity() != null) {
      json.put("city", obj.getCity());
    }
    if (obj.getId() != null) {
      json.put("id", obj.getId());
    }
    if (obj.getLiveId() != null) {
      json.put("liveId", obj.getLiveId());
    }
    if (obj.getNick() != null) {
      json.put("nick", obj.getNick());
    }
    if (obj.getOnlineNum() != null) {
      json.put("onlineNum", obj.getOnlineNum());
    }
    if (obj.getPortrait() != null) {
      json.put("portrait", obj.getPortrait());
    }
    if (obj.getSource() != null) {
      json.put("source", obj.getSource());
    }
  }
}