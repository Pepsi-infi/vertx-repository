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
 * Converter for {@link tp.live.response.WaterMarkDeliveryTpResponse}.
 *
 * NOTE: This class has been automatically generated from the {@link tp.live.response.WaterMarkDeliveryTpResponse} original class using Vert.x codegen.
 */
public class WaterMarkDeliveryTpResponseConverter {

  public static void fromJson(JsonObject json, WaterMarkDeliveryTpResponse obj) {
    if (json.getValue("beginTime") instanceof Number) {
      obj.setBeginTime(((Number)json.getValue("beginTime")).longValue());
    }
    if (json.getValue("dataId") instanceof String) {
      obj.setDataId((String)json.getValue("dataId"));
    }
    if (json.getValue("endTime") instanceof Number) {
      obj.setEndTime(((Number)json.getValue("endTime")).longValue());
    }
    if (json.getValue("id") instanceof Number) {
      obj.setId(((Number)json.getValue("id")).intValue());
    }
    if (json.getValue("playbillItemId") instanceof Number) {
      obj.setPlaybillItemId(((Number)json.getValue("playbillItemId")).intValue());
    }
    if (json.getValue("posx") instanceof Number) {
      obj.setPosx(((Number)json.getValue("posx")).intValue());
    }
    if (json.getValue("posy") instanceof Number) {
      obj.setPosy(((Number)json.getValue("posy")).intValue());
    }
    if (json.getValue("programType") instanceof Number) {
      obj.setProgramType(((Number)json.getValue("programType")).intValue());
    }
    if (json.getValue("splatId") instanceof String) {
      obj.setSplatId((String)json.getValue("splatId"));
    }
    if (json.getValue("type") instanceof Number) {
      obj.setType(((Number)json.getValue("type")).intValue());
    }
    if (json.getValue("wartermark") instanceof JsonObject) {
      obj.setWartermark(new tp.live.response.WarterMarkTpResponse((JsonObject)json.getValue("wartermark")));
    }
    if (json.getValue("watermarkId") instanceof Number) {
      obj.setWatermarkId(((Number)json.getValue("watermarkId")).intValue());
    }
  }

  public static void toJson(WaterMarkDeliveryTpResponse obj, JsonObject json) {
    if (obj.getBeginTime() != null) {
      json.put("beginTime", obj.getBeginTime());
    }
    if (obj.getDataId() != null) {
      json.put("dataId", obj.getDataId());
    }
    if (obj.getEndTime() != null) {
      json.put("endTime", obj.getEndTime());
    }
    if (obj.getId() != null) {
      json.put("id", obj.getId());
    }
    if (obj.getPlaybillItemId() != null) {
      json.put("playbillItemId", obj.getPlaybillItemId());
    }
    if (obj.getPosx() != null) {
      json.put("posx", obj.getPosx());
    }
    if (obj.getPosy() != null) {
      json.put("posy", obj.getPosy());
    }
    if (obj.getProgramType() != null) {
      json.put("programType", obj.getProgramType());
    }
    if (obj.getSplatId() != null) {
      json.put("splatId", obj.getSplatId());
    }
    if (obj.getType() != null) {
      json.put("type", obj.getType());
    }
    if (obj.getWartermark() != null) {
      json.put("wartermark", obj.getWartermark().toJson());
    }
    if (obj.getWatermarkId() != null) {
      json.put("watermarkId", obj.getWatermarkId());
    }
  }
}