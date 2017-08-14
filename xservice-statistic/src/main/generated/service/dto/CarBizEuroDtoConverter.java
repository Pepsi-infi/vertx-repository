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
 * Converter for {@link service.dto.CarBizEuroDto}.
 *
 * NOTE: This class has been automatically generated from the {@link service.dto.CarBizEuroDto} original class using Vert.x codegen.
 */
public class CarBizEuroDtoConverter {

  public static void fromJson(JsonObject json, CarBizEuroDto obj) {
    if (json.getValue("customerId") instanceof Number) {
      obj.setCustomerId(((Number)json.getValue("customerId")).longValue());
    }
    if (json.getValue("deviceToken") instanceof String) {
      obj.setDeviceToken((String)json.getValue("deviceToken"));
    }
    if (json.getValue("id") instanceof Number) {
      obj.setId(((Number)json.getValue("id")).longValue());
    }
    if (json.getValue("phone") instanceof String) {
      obj.setPhone((String)json.getValue("phone"));
    }
  }

  public static void toJson(CarBizEuroDto obj, JsonObject json) {
    if (obj.getCustomerId() != null) {
      json.put("customerId", obj.getCustomerId());
    }
    if (obj.getDeviceToken() != null) {
      json.put("deviceToken", obj.getDeviceToken());
    }
    if (obj.getId() != null) {
      json.put("id", obj.getId());
    }
    if (obj.getPhone() != null) {
      json.put("phone", obj.getPhone());
    }
  }
}