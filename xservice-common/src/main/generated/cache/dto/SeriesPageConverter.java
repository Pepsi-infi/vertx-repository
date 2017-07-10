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
 * Converter for {@link cache.dto.SeriesPage}.
 *
 * NOTE: This class has been automatically generated from the {@link cache.dto.SeriesPage} original class using Vert.x codegen.
 */
public class SeriesPageConverter {

  public static void fromJson(JsonObject json, SeriesPage obj) {
    if (json.getValue("page") instanceof Number) {
      obj.setPage(((Number)json.getValue("page")).intValue());
    }
    if (json.getValue("pageSize") instanceof Number) {
      obj.setPageSize(((Number)json.getValue("pageSize")).intValue());
    }
    if (json.getValue("positiveSeries") instanceof JsonArray) {
      java.util.ArrayList<cache.dto.Video> list = new java.util.ArrayList<>();
      json.getJsonArray("positiveSeries").forEach( item -> {
        if (item instanceof JsonObject)
          list.add(new cache.dto.Video((JsonObject)item));
      });
      obj.setPositiveSeries(list);
    }
  }

  public static void toJson(SeriesPage obj, JsonObject json) {
    if (obj.getPage() != null) {
      json.put("page", obj.getPage());
    }
    if (obj.getPageSize() != null) {
      json.put("pageSize", obj.getPageSize());
    }
    if (obj.getPositiveSeries() != null) {
      JsonArray array = new JsonArray();
      obj.getPositiveSeries().forEach(item -> array.add(item.toJson()));
      json.put("positiveSeries", array);
    }
  }
}