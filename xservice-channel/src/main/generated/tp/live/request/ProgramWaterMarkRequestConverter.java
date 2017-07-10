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
 * Converter for {@link tp.live.request.ProgramWaterMarkRequest}.
 *
 * NOTE: This class has been automatically generated from the {@link tp.live.request.ProgramWaterMarkRequest} original class using Vert.x codegen.
 */
public class ProgramWaterMarkRequestConverter {

  public static void fromJson(JsonObject json, ProgramWaterMarkRequest obj) {
    if (json.getValue("channelId") instanceof Number) {
      obj.setChannelId(((Number)json.getValue("channelId")).intValue());
    }
    if (json.getValue("curPage") instanceof Number) {
      obj.setCurPage(((Number)json.getValue("curPage")).intValue());
    }
    if (json.getValue("pageSize") instanceof Number) {
      obj.setPageSize(((Number)json.getValue("pageSize")).intValue());
    }
    if (json.getValue("platform") instanceof String) {
      obj.setPlatform((String)json.getValue("platform"));
    }
    if (json.getValue("playDate") instanceof String) {
      obj.setPlayDate((String)json.getValue("playDate"));
    }
  }

  public static void toJson(ProgramWaterMarkRequest obj, JsonObject json) {
    if (obj.getChannelId() != null) {
      json.put("channelId", obj.getChannelId());
    }
    if (obj.getCurPage() != null) {
      json.put("curPage", obj.getCurPage());
    }
    if (obj.getPageSize() != null) {
      json.put("pageSize", obj.getPageSize());
    }
    if (obj.getPlatform() != null) {
      json.put("platform", obj.getPlatform());
    }
    if (obj.getPlayDate() != null) {
      json.put("playDate", obj.getPlayDate());
    }
  }
}