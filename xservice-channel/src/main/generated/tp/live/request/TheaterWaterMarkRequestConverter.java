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

package tp.live.request;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;

/**
 * Converter for {@link tp.live.request.TheaterWaterMarkRequest}.
 *
 * NOTE: This class has been automatically generated from the {@link tp.live.request.TheaterWaterMarkRequest} original class using Vert.x codegen.
 */
public class TheaterWaterMarkRequestConverter {

  public static void fromJson(JsonObject json, TheaterWaterMarkRequest obj) {
    if (json.getValue("channelId") instanceof Number) {
      obj.setChannelId(((Number)json.getValue("channelId")).intValue());
    }
    if (json.getValue("platform") instanceof String) {
      obj.setPlatform((String)json.getValue("platform"));
    }
    if (json.getValue("playDate") instanceof String) {
      obj.setPlayDate((String)json.getValue("playDate"));
    }
  }

  public static void toJson(TheaterWaterMarkRequest obj, JsonObject json) {
    if (obj.getChannelId() != null) {
      json.put("channelId", obj.getChannelId());
    }
    if (obj.getPlatform() != null) {
      json.put("platform", obj.getPlatform());
    }
    if (obj.getPlayDate() != null) {
      json.put("playDate", obj.getPlayDate());
    }
  }
}