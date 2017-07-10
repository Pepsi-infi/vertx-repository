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
 * Converter for {@link cache.dto.SeriesCache}.
 *
 * NOTE: This class has been automatically generated from the {@link cache.dto.SeriesCache} original class using Vert.x codegen.
 */
public class SeriesCacheConverter {

  public static void fromJson(JsonObject json, SeriesCache obj) {
    if (json.getValue("key") instanceof String) {
      obj.setKey((String)json.getValue("key"));
    }
    if (json.getValue("vidList") instanceof JsonArray) {
      java.util.ArrayList<java.lang.Long> list = new java.util.ArrayList<>();
      json.getJsonArray("vidList").forEach( item -> {
        if (item instanceof Number)
          list.add(((Number)item).longValue());
      });
      obj.setVidList(list);
    }
  }

  public static void toJson(SeriesCache obj, JsonObject json) {
    if (obj.getKey() != null) {
      json.put("key", obj.getKey());
    }
    if (obj.getVidList() != null) {
      JsonArray array = new JsonArray();
      obj.getVidList().forEach(item -> array.add(item));
      json.put("vidList", array);
    }
  }
}