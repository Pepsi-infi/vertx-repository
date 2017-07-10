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
 * Converter for {@link cache.dto.WatchingFocusCache}.
 *
 * NOTE: This class has been automatically generated from the {@link cache.dto.WatchingFocusCache} original class using Vert.x codegen.
 */
public class WatchingFocusCacheConverter {

  public static void fromJson(JsonObject json, WatchingFocusCache obj) {
    if (json.getValue("desc") instanceof String) {
      obj.setDesc((String)json.getValue("desc"));
    }
    if (json.getValue("id") instanceof Number) {
      obj.setId(((Number)json.getValue("id")).intValue());
    }
    if (json.getValue("time") instanceof Number) {
      obj.setTime(((Number)json.getValue("time")).longValue());
    }
  }

  public static void toJson(WatchingFocusCache obj, JsonObject json) {
    if (obj.getDesc() != null) {
      json.put("desc", obj.getDesc());
    }
    if (obj.getId() != null) {
      json.put("id", obj.getId());
    }
    if (obj.getTime() != null) {
      json.put("time", obj.getTime());
    }
  }
}