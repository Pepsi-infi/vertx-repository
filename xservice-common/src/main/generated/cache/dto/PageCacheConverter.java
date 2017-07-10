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
 * Converter for {@link cache.dto.PageCache}.
 *
 * NOTE: This class has been automatically generated from the {@link cache.dto.PageCache} original class using Vert.x codegen.
 */
public class PageCacheConverter {

  public static void fromJson(JsonObject json, PageCache obj) {
    if (json.getValue("index") instanceof JsonArray) {
      java.util.ArrayList<cache.dto.Page> list = new java.util.ArrayList<>();
      json.getJsonArray("index").forEach( item -> {
        if (item instanceof JsonObject)
          list.add(new cache.dto.Page((JsonObject)item));
      });
      obj.setIndex(list);
    }
    if (json.getValue("key") instanceof String) {
      obj.setKey((String)json.getValue("key"));
    }
  }

  public static void toJson(PageCache obj, JsonObject json) {
    if (obj.getIndex() != null) {
      JsonArray array = new JsonArray();
      obj.getIndex().forEach(item -> array.add(item.toJson()));
      json.put("index", array);
    }
    if (obj.getKey() != null) {
      json.put("key", obj.getKey());
    }
  }
}