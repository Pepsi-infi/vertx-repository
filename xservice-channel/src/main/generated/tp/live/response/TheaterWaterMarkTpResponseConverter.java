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
 * Converter for {@link tp.live.response.TheaterWaterMarkTpResponse}.
 *
 * NOTE: This class has been automatically generated from the {@link tp.live.response.TheaterWaterMarkTpResponse} original class using Vert.x codegen.
 */
public class TheaterWaterMarkTpResponseConverter {

  public static void fromJson(JsonObject json, TheaterWaterMarkTpResponse obj) {
    if (json.getValue("data") instanceof JsonArray) {
      java.util.ArrayList<tp.live.response.WaterMarkDeliveryTpResponse> list = new java.util.ArrayList<>();
      json.getJsonArray("data").forEach( item -> {
        if (item instanceof JsonObject)
          list.add(new tp.live.response.WaterMarkDeliveryTpResponse((JsonObject)item));
      });
      obj.setData(list);
    }
    if (json.getValue("result") instanceof Number) {
      obj.setResult(((Number)json.getValue("result")).intValue());
    }
    if (json.getValue("statusCode") instanceof String) {
      obj.setStatusCode((String)json.getValue("statusCode"));
    }
  }

  public static void toJson(TheaterWaterMarkTpResponse obj, JsonObject json) {
    if (obj.getData() != null) {
      JsonArray array = new JsonArray();
      obj.getData().forEach(item -> array.add(item.toJson()));
      json.put("data", array);
    }
    json.put("result", obj.getResult());
    if (obj.getStatusCode() != null) {
      json.put("statusCode", obj.getStatusCode());
    }
  }
}