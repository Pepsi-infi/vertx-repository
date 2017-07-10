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
 * Converter for {@link vip.response.Discount}.
 *
 * NOTE: This class has been automatically generated from the {@link vip.response.Discount} original class using Vert.x codegen.
 */
public class DiscountConverter {

  public static void fromJson(JsonObject json, Discount obj) {
    if (json.getValue("price") instanceof Number) {
      obj.setPrice(((Number)json.getValue("price")).doubleValue());
    }
    if (json.getValue("vipType") instanceof Number) {
      obj.setVipType(((Number)json.getValue("vipType")).intValue());
    }
  }

  public static void toJson(Discount obj, JsonObject json) {
    if (obj.getPrice() != null) {
      json.put("price", obj.getPrice());
    }
    if (obj.getVipType() != null) {
      json.put("vipType", obj.getVipType());
    }
  }
}