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

package vip.response;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;

/**
 * Converter for {@link vip.response.GetVipInfoResponse}.
 *
 * NOTE: This class has been automatically generated from the {@link vip.response.GetVipInfoResponse} original class using Vert.x codegen.
 */
public class GetVipInfoResponseConverter {

  public static void fromJson(JsonObject json, GetVipInfoResponse obj) {
    if (json.getValue("code") instanceof Number) {
      obj.setCode(((Number)json.getValue("code")).intValue());
    }
    if (json.getValue("data") instanceof JsonArray) {
      java.util.ArrayList<vip.response.VipInfo> list = new java.util.ArrayList<>();
      json.getJsonArray("data").forEach( item -> {
        if (item instanceof JsonObject)
          list.add(new vip.response.VipInfo((JsonObject)item));
      });
      obj.setData(list);
    }
  }

  public static void toJson(GetVipInfoResponse obj, JsonObject json) {
    if (obj.getCode() != null) {
      json.put("code", obj.getCode());
    }
    if (obj.getData() != null) {
      JsonArray array = new JsonArray();
      obj.getData().forEach(item -> array.add(item.toJson()));
      json.put("data", array);
    }
  }
}