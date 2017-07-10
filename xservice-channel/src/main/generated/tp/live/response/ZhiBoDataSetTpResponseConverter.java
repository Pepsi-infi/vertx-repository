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
 * Converter for {@link tp.live.response.ZhiBoDataSetTpResponse}.
 *
 * NOTE: This class has been automatically generated from the {@link tp.live.response.ZhiBoDataSetTpResponse} original class using Vert.x codegen.
 */
public class ZhiBoDataSetTpResponseConverter {

  public static void fromJson(JsonObject json, ZhiBoDataSetTpResponse obj) {
    if (json.getValue("rows") instanceof JsonArray) {
      java.util.ArrayList<tp.live.response.ZhiBoDataResponse> list = new java.util.ArrayList<>();
      json.getJsonArray("rows").forEach( item -> {
        if (item instanceof JsonObject)
          list.add(new tp.live.response.ZhiBoDataResponse((JsonObject)item));
      });
      obj.setRows(list);
    }
  }

  public static void toJson(ZhiBoDataSetTpResponse obj, JsonObject json) {
    if (obj.getRows() != null) {
      JsonArray array = new JsonArray();
      obj.getRows().forEach(item -> array.add(item.toJson()));
      json.put("rows", array);
    }
  }
}