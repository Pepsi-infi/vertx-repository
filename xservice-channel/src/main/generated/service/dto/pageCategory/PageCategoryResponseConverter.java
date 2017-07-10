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

package service.dto.pageCategory;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;

/**
 * Converter for {@link service.dto.pageCategory.PageCategoryResponse}.
 *
 * NOTE: This class has been automatically generated from the {@link service.dto.pageCategory.PageCategoryResponse} original class using Vert.x codegen.
 */
public class PageCategoryResponseConverter {

  public static void fromJson(JsonObject json, PageCategoryResponse obj) {
    if (json.getValue("currentIndex") instanceof Number) {
      obj.setCurrentIndex(((Number)json.getValue("currentIndex")).intValue());
    }
    if (json.getValue("data") instanceof JsonArray) {
      java.util.ArrayList<service.dto.pageCategory.ChannelData> list = new java.util.ArrayList<>();
      json.getJsonArray("data").forEach( item -> {
        if (item instanceof JsonObject)
          list.add(new service.dto.pageCategory.ChannelData((JsonObject)item));
      });
      obj.setData(list);
    }
    if (json.getValue("nextIndex") instanceof Number) {
      obj.setNextIndex(((Number)json.getValue("nextIndex")).intValue());
    }
    if (json.getValue("plus") instanceof JsonObject) {
      java.util.Map<String, java.lang.Object> map = new java.util.LinkedHashMap<>();
      json.getJsonObject("plus").forEach(entry -> {
        if (entry.getValue() instanceof Object)
          map.put(entry.getKey(), entry.getValue());
      });
      obj.setPlus(map);
    }
    if (json.getValue("totalCount") instanceof Number) {
      obj.setTotalCount(((Number)json.getValue("totalCount")).intValue());
    }
  }

  public static void toJson(PageCategoryResponse obj, JsonObject json) {
    if (obj.getCurrentIndex() != null) {
      json.put("currentIndex", obj.getCurrentIndex());
    }
    if (obj.getData() != null) {
      JsonArray array = new JsonArray();
      obj.getData().forEach(item -> array.add(item.toJson()));
      json.put("data", array);
    }
    if (obj.getNextIndex() != null) {
      json.put("nextIndex", obj.getNextIndex());
    }
    if (obj.getPlus() != null) {
      JsonObject map = new JsonObject();
      obj.getPlus().forEach((key,value) -> map.put(key, value));
      json.put("plus", map);
    }
    if (obj.getTotalCount() != null) {
      json.put("totalCount", obj.getTotalCount());
    }
  }
}