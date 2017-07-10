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
 * Converter for {@link service.dto.UserAddOnsInfo}.
 *
 * NOTE: This class has been automatically generated from the {@link service.dto.UserAddOnsInfo} original class using Vert.x codegen.
 */
public class UserAddOnsInfoConverter {

  public static void fromJson(JsonObject json, UserAddOnsInfo obj) {
    if (json.getValue("listNotEnd") instanceof JsonArray) {
      java.util.ArrayList<vip.response.VipInfo> list = new java.util.ArrayList<>();
      json.getJsonArray("listNotEnd").forEach( item -> {
        if (item instanceof JsonObject)
          list.add(new vip.response.VipInfo((JsonObject)item));
      });
      obj.setListNotEnd(list);
    }
    if (json.getValue("listSub") instanceof JsonArray) {
      java.util.ArrayList<vip.response.VipInfo> list = new java.util.ArrayList<>();
      json.getJsonArray("listSub").forEach( item -> {
        if (item instanceof JsonObject)
          list.add(new vip.response.VipInfo((JsonObject)item));
      });
      obj.setListSub(list);
    }
    if (json.getValue("other") instanceof JsonArray) {
      java.util.ArrayList<vip.response.VipInfo> list = new java.util.ArrayList<>();
      json.getJsonArray("other").forEach( item -> {
        if (item instanceof JsonObject)
          list.add(new vip.response.VipInfo((JsonObject)item));
      });
      obj.setOther(list);
    }
  }

  public static void toJson(UserAddOnsInfo obj, JsonObject json) {
    if (obj.getListNotEnd() != null) {
      JsonArray array = new JsonArray();
      obj.getListNotEnd().forEach(item -> array.add(item.toJson()));
      json.put("listNotEnd", array);
    }
    if (obj.getListSub() != null) {
      JsonArray array = new JsonArray();
      obj.getListSub().forEach(item -> array.add(item.toJson()));
      json.put("listSub", array);
    }
    if (obj.getOther() != null) {
      JsonArray array = new JsonArray();
      obj.getOther().forEach(item -> array.add(item.toJson()));
      json.put("other", array);
    }
  }
}