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

package inke.dto;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;

/**
 * Converter for {@link inke.dto.AnchorListWapperDto}.
 *
 * NOTE: This class has been automatically generated from the {@link inke.dto.AnchorListWapperDto} original class using Vert.x codegen.
 */
public class AnchorListWapperDtoConverter {

  public static void fromJson(JsonObject json, AnchorListWapperDto obj) {
    if (json.getValue("data") instanceof JsonArray) {
      java.util.ArrayList<inke.dto.AnchorPageDto> list = new java.util.ArrayList<>();
      json.getJsonArray("data").forEach( item -> {
        if (item instanceof JsonObject)
          list.add(new inke.dto.AnchorPageDto((JsonObject)item));
      });
      obj.setData(list);
    }
    if (json.getValue("status") instanceof Number) {
      obj.setStatus(((Number)json.getValue("status")).intValue());
    }
  }

  public static void toJson(AnchorListWapperDto obj, JsonObject json) {
    if (obj.getData() != null) {
      JsonArray array = new JsonArray();
      obj.getData().forEach(item -> array.add(item.toJson()));
      json.put("data", array);
    }
    if (obj.getStatus() != null) {
      json.put("status", obj.getStatus());
    }
  }
}