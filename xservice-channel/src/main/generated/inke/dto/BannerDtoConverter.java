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
 * Converter for {@link inke.dto.BannerDto}.
 *
 * NOTE: This class has been automatically generated from the {@link inke.dto.BannerDto} original class using Vert.x codegen.
 */
public class BannerDtoConverter {

  public static void fromJson(JsonObject json, BannerDto obj) {
    if (json.getValue("img") instanceof String) {
      obj.setImg((String)json.getValue("img"));
    }
    if (json.getValue("jump") instanceof Number) {
      obj.setJump(((Number)json.getValue("jump")).intValue());
    }
    if (json.getValue("roomId") instanceof String) {
      obj.setRoomId((String)json.getValue("roomId"));
    }
    if (json.getValue("url") instanceof String) {
      obj.setUrl((String)json.getValue("url"));
    }
  }

  public static void toJson(BannerDto obj, JsonObject json) {
    if (obj.getImg() != null) {
      json.put("img", obj.getImg());
    }
    if (obj.getJump() != null) {
      json.put("jump", obj.getJump());
    }
    if (obj.getRoomId() != null) {
      json.put("roomId", obj.getRoomId());
    }
    if (obj.getUrl() != null) {
      json.put("url", obj.getUrl());
    }
  }
}