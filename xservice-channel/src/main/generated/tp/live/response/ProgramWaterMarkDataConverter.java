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

package tp.live.response;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;

/**
 * Converter for {@link tp.live.response.ProgramWaterMarkData}.
 *
 * NOTE: This class has been automatically generated from the {@link tp.live.response.ProgramWaterMarkData} original class using Vert.x codegen.
 */
public class ProgramWaterMarkDataConverter {

  public static void fromJson(JsonObject json, ProgramWaterMarkData obj) {
    if (json.getValue("items") instanceof JsonArray) {
      java.util.ArrayList<tp.live.response.WaterMarkDeliveryTpResponse> list = new java.util.ArrayList<>();
      json.getJsonArray("items").forEach( item -> {
        if (item instanceof JsonObject)
          list.add(new tp.live.response.WaterMarkDeliveryTpResponse((JsonObject)item));
      });
      obj.setItems(list);
    }
    if (json.getValue("page") instanceof Number) {
      obj.setPage(((Number)json.getValue("page")).intValue());
    }
    if (json.getValue("pageSize") instanceof Number) {
      obj.setPageSize(((Number)json.getValue("pageSize")).intValue());
    }
    if (json.getValue("total") instanceof Number) {
      obj.setTotal(((Number)json.getValue("total")).intValue());
    }
  }

  public static void toJson(ProgramWaterMarkData obj, JsonObject json) {
    if (obj.getItems() != null) {
      JsonArray array = new JsonArray();
      obj.getItems().forEach(item -> array.add(item.toJson()));
      json.put("items", array);
    }
    if (obj.getPage() != null) {
      json.put("page", obj.getPage());
    }
    if (obj.getPageSize() != null) {
      json.put("pageSize", obj.getPageSize());
    }
    if (obj.getTotal() != null) {
      json.put("total", obj.getTotal());
    }
  }
}