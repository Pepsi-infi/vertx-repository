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

package service.dto.channelPage;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;

/**
 * Converter for {@link service.dto.channelPage.ChannelPage}.
 *
 * NOTE: This class has been automatically generated from the {@link service.dto.channelPage.ChannelPage} original class using Vert.x codegen.
 */
public class ChannelPageConverter {

  public static void fromJson(JsonObject json, ChannelPage obj) {
    if (json.getValue("backUrl") instanceof String) {
      obj.setBackUrl((String)json.getValue("backUrl"));
    }
    if (json.getValue("block") instanceof JsonArray) {
      java.util.ArrayList<service.dto.channelPage.ChannelBlockDto> list = new java.util.ArrayList<>();
      json.getJsonArray("block").forEach( item -> {
        if (item instanceof JsonObject)
          list.add(new service.dto.channelPage.ChannelBlockDto((JsonObject)item));
      });
      obj.setBlock(list);
    }
    if (json.getValue("focus") instanceof JsonArray) {
      java.util.ArrayList<service.dto.channelPage.BaseDto> list = new java.util.ArrayList<>();
      json.getJsonArray("focus").forEach( item -> {
        if (item instanceof JsonObject)
          list.add(new service.dto.channelPage.BaseDto((JsonObject)item));
      });
      obj.setFocus(list);
    }
    if (json.getValue("focusDataType") instanceof String) {
      obj.setFocusDataType((String)json.getValue("focusDataType"));
    }
  }

  public static void toJson(ChannelPage obj, JsonObject json) {
    if (obj.getBackUrl() != null) {
      json.put("backUrl", obj.getBackUrl());
    }
    if (obj.getBlock() != null) {
      JsonArray array = new JsonArray();
      obj.getBlock().forEach(item -> array.add(item.toJson()));
      json.put("block", array);
    }
    if (obj.getFocus() != null) {
      JsonArray array = new JsonArray();
      obj.getFocus().forEach(item -> array.add(item.toJson()));
      json.put("focus", array);
    }
    if (obj.getFocusDataType() != null) {
      json.put("focusDataType", obj.getFocusDataType());
    }
  }
}