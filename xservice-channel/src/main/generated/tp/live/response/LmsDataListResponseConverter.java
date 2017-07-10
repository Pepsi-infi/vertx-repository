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
 * Converter for {@link tp.live.response.LmsDataListResponse}.
 *
 * NOTE: This class has been automatically generated from the {@link tp.live.response.LmsDataListResponse} original class using Vert.x codegen.
 */
public class LmsDataListResponseConverter {

  public static void fromJson(JsonObject json, LmsDataListResponse obj) {
    if (json.getValue("code") instanceof String) {
      obj.setCode((String)json.getValue("code"));
    }
    if (json.getValue("data") instanceof JsonArray) {
      java.util.ArrayList<tp.live.response.LmsDataResponse> list = new java.util.ArrayList<>();
      json.getJsonArray("data").forEach( item -> {
        if (item instanceof JsonObject)
          list.add(new tp.live.response.LmsDataResponse((JsonObject)item));
      });
      obj.setData(list);
    }
    if (json.getValue("message") instanceof String) {
      obj.setMessage((String)json.getValue("message"));
    }
  }

  public static void toJson(LmsDataListResponse obj, JsonObject json) {
    if (obj.getCode() != null) {
      json.put("code", obj.getCode());
    }
    if (obj.getData() != null) {
      JsonArray array = new JsonArray();
      obj.getData().forEach(item -> array.add(item.toJson()));
      json.put("data", array);
    }
    if (obj.getMessage() != null) {
      json.put("message", obj.getMessage());
    }
  }
}