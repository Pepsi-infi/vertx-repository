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

package service.dto;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;

/**
 * Converter for {@link service.dto.MsgStatResultPage}.
 *
 * NOTE: This class has been automatically generated from the {@link service.dto.MsgStatResultPage} original class using Vert.x codegen.
 */
public class MsgStatResultPageConverter {

  public static void fromJson(JsonObject json, MsgStatResultPage obj) {
    if (json.getValue("list") instanceof JsonArray) {
      java.util.ArrayList<service.dto.MsgStatResultDto> list = new java.util.ArrayList<>();
      json.getJsonArray("list").forEach( item -> {
        if (item instanceof JsonObject)
          list.add(new service.dto.MsgStatResultDto((JsonObject)item));
      });
      obj.setList(list);
    }
    if (json.getValue("page") instanceof Number) {
      obj.setPage(((Number)json.getValue("page")).intValue());
    }
    if (json.getValue("size") instanceof Number) {
      obj.setSize(((Number)json.getValue("size")).intValue());
    }
  }

  public static void toJson(MsgStatResultPage obj, JsonObject json) {
    if (obj.getList() != null) {
      JsonArray array = new JsonArray();
      obj.getList().forEach(item -> array.add(item.toJson()));
      json.put("list", array);
    }
    json.put("page", obj.getPage());
    json.put("size", obj.getSize());
  }
}