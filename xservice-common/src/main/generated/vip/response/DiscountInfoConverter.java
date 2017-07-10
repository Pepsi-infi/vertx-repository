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
 * Converter for {@link vip.response.DiscountInfo}.
 *
 * NOTE: This class has been automatically generated from the {@link vip.response.DiscountInfo} original class using Vert.x codegen.
 */
public class DiscountInfoConverter {

  public static void fromJson(JsonObject json, DiscountInfo obj) {
    if (json.getValue("price") instanceof Number) {
      obj.setPrice(((Number)json.getValue("price")).doubleValue());
    }
    if (json.getValue("vipId") instanceof Number) {
      obj.setVipId(((Number)json.getValue("vipId")).intValue());
    }
  }

  public static void toJson(DiscountInfo obj, JsonObject json) {
    if (obj.getPrice() != null) {
      json.put("price", obj.getPrice());
    }
    if (obj.getVipId() != null) {
      json.put("vipId", obj.getVipId());
    }
  }
}