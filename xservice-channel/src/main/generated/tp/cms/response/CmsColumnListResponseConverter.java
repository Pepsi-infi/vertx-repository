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

package tp.cms.response;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;

/**
 * Converter for {@link tp.cms.response.CmsColumnListResponse}.
 *
 * NOTE: This class has been automatically generated from the {@link tp.cms.response.CmsColumnListResponse} original class using Vert.x codegen.
 */
public class CmsColumnListResponseConverter {

  public static void fromJson(JsonObject json, CmsColumnListResponse obj) {
    if (json.getValue("data") instanceof JsonArray) {
      java.util.ArrayList<tp.cms.response.CmsColumnResponse> list = new java.util.ArrayList<>();
      json.getJsonArray("data").forEach( item -> {
        if (item instanceof JsonObject)
          list.add(new tp.cms.response.CmsColumnResponse((JsonObject)item));
      });
      obj.setData(list);
    }
    if (json.getValue("msg") instanceof String) {
      obj.setMsg((String)json.getValue("msg"));
    }
    if (json.getValue("statusCode") instanceof Number) {
      obj.setStatusCode(((Number)json.getValue("statusCode")).intValue());
    }
  }

  public static void toJson(CmsColumnListResponse obj, JsonObject json) {
    if (obj.getData() != null) {
      JsonArray array = new JsonArray();
      obj.getData().forEach(item -> array.add(item.toJson()));
      json.put("data", array);
    }
    if (obj.getMsg() != null) {
      json.put("msg", obj.getMsg());
    }
    if (obj.getStatusCode() != null) {
      json.put("statusCode", obj.getStatusCode());
    }
  }
}