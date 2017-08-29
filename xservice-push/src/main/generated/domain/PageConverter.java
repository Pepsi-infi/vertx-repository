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

package domain;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;

/**
 * Converter for {@link domain.Page}.
 *
 * NOTE: This class has been automatically generated from the {@link domain.Page} original class using Vert.x codegen.
 */
public class PageConverter {

  public static void fromJson(JsonObject json, Page obj) {
    if (json.getValue("hasNextPage") instanceof Boolean) {
      obj.setHasNextPage((Boolean)json.getValue("hasNextPage"));
    }
    if (json.getValue("list") instanceof JsonArray) {
      java.util.ArrayList<domain.DriverMsg> list = new java.util.ArrayList<>();
      json.getJsonArray("list").forEach( item -> {
        if (item instanceof JsonObject)
          list.add(new domain.DriverMsg((JsonObject)item));
      });
      obj.setList(list);
    }
    if (json.getValue("page") instanceof Number) {
      obj.setPage(((Number)json.getValue("page")).intValue());
    }
    if (json.getValue("pageSize") instanceof Number) {
      obj.setPageSize(((Number)json.getValue("pageSize")).intValue());
    }
    if (json.getValue("total") instanceof Number) {
      obj.setTotal(((Number)json.getValue("total")).longValue());
    }
  }

  public static void toJson(Page obj, JsonObject json) {
    json.put("hasNextPage", obj.isHasNextPage());
    if (obj.getList() != null) {
      JsonArray array = new JsonArray();
      obj.getList().forEach(item -> array.add(item.toJson()));
      json.put("list", array);
    }
    json.put("page", obj.getPage());
    json.put("pageSize", obj.getPageSize());
    if (obj.getTotal() != null) {
      json.put("total", obj.getTotal());
    }
  }
}