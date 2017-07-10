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

package service.dto.theaterpack;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;

/**
 * Converter for {@link service.dto.theaterpack.WaterMarkDeliveryInfo}.
 *
 * NOTE: This class has been automatically generated from the {@link service.dto.theaterpack.WaterMarkDeliveryInfo} original class using Vert.x codegen.
 */
public class WaterMarkDeliveryInfoConverter {

  public static void fromJson(JsonObject json, WaterMarkDeliveryInfo obj) {
    if (json.getValue("beginTime") instanceof Number) {
      obj.setBeginTime(((Number)json.getValue("beginTime")).longValue());
    }
    if (json.getValue("endTime") instanceof Number) {
      obj.setEndTime(((Number)json.getValue("endTime")).longValue());
    }
    if (json.getValue("playbillId") instanceof Number) {
      obj.setPlaybillId(((Number)json.getValue("playbillId")).intValue());
    }
    if (json.getValue("programId") instanceof String) {
      obj.setProgramId((String)json.getValue("programId"));
    }
    if (json.getValue("programType") instanceof Number) {
      obj.setProgramType(((Number)json.getValue("programType")).intValue());
    }
    if (json.getValue("splatld") instanceof String) {
      obj.setSplatld((String)json.getValue("splatld"));
    }
    if (json.getValue("type") instanceof Number) {
      obj.setType(((Number)json.getValue("type")).intValue());
    }
    if (json.getValue("waterMarkId") instanceof Number) {
      obj.setWaterMarkId(((Number)json.getValue("waterMarkId")).intValue());
    }
    if (json.getValue("waterMarkInfo") instanceof JsonObject) {
      obj.setWaterMarkInfo(new service.dto.theaterpack.WaterMarkInfo((JsonObject)json.getValue("waterMarkInfo")));
    }
  }

  public static void toJson(WaterMarkDeliveryInfo obj, JsonObject json) {
    if (obj.getBeginTime() != null) {
      json.put("beginTime", obj.getBeginTime());
    }
    if (obj.getEndTime() != null) {
      json.put("endTime", obj.getEndTime());
    }
    if (obj.getPlaybillId() != null) {
      json.put("playbillId", obj.getPlaybillId());
    }
    if (obj.getProgramId() != null) {
      json.put("programId", obj.getProgramId());
    }
    if (obj.getProgramType() != null) {
      json.put("programType", obj.getProgramType());
    }
    if (obj.getSplatld() != null) {
      json.put("splatld", obj.getSplatld());
    }
    if (obj.getType() != null) {
      json.put("type", obj.getType());
    }
    if (obj.getWaterMarkId() != null) {
      json.put("waterMarkId", obj.getWaterMarkId());
    }
    if (obj.getWaterMarkInfo() != null) {
      json.put("waterMarkInfo", obj.getWaterMarkInfo().toJson());
    }
  }
}