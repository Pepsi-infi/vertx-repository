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
 * Converter for {@link tp.live.request.PlayBillCurrentRequest}.
 *
 * NOTE: This class has been automatically generated from the {@link tp.live.request.PlayBillCurrentRequest} original class using Vert.x codegen.
 */
public class PlayBillCurrentRequestConverter {

  public static void fromJson(JsonObject json, PlayBillCurrentRequest obj) {
    if (json.getValue("channelIds") instanceof Number) {
      obj.setChannelIds(((Number)json.getValue("channelIds")).intValue());
    }
    if (json.getValue("clientId") instanceof Number) {
      obj.setClientId(((Number)json.getValue("clientId")).intValue());
    }
  }

  public static void toJson(PlayBillCurrentRequest obj, JsonObject json) {
    if (obj.getChannelIds() != null) {
      json.put("channelIds", obj.getChannelIds());
    }
    json.put("clientId", obj.getClientId());
  }
}