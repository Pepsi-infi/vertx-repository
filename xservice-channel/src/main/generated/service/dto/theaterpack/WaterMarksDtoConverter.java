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
 * Converter for {@link service.dto.theaterpack.WaterMarksDto}.
 *
 * NOTE: This class has been automatically generated from the {@link service.dto.theaterpack.WaterMarksDto} original class using Vert.x codegen.
 */
public class WaterMarksDtoConverter {

  public static void fromJson(JsonObject json, WaterMarksDto obj) {
    if (json.getValue("programWaterMarkList") instanceof JsonArray) {
      java.util.ArrayList<service.dto.theaterpack.WaterMarkDeliveryInfo> list = new java.util.ArrayList<>();
      json.getJsonArray("programWaterMarkList").forEach( item -> {
        if (item instanceof JsonObject)
          list.add(new service.dto.theaterpack.WaterMarkDeliveryInfo((JsonObject)item));
      });
      obj.setProgramWaterMarkList(list);
    }
    if (json.getValue("theaterWaterMarkList") instanceof JsonArray) {
      java.util.ArrayList<service.dto.theaterpack.WaterMarkDeliveryInfo> list = new java.util.ArrayList<>();
      json.getJsonArray("theaterWaterMarkList").forEach( item -> {
        if (item instanceof JsonObject)
          list.add(new service.dto.theaterpack.WaterMarkDeliveryInfo((JsonObject)item));
      });
      obj.setTheaterWaterMarkList(list);
    }
  }

  public static void toJson(WaterMarksDto obj, JsonObject json) {
    if (obj.getProgramWaterMarkList() != null) {
      JsonArray array = new JsonArray();
      obj.getProgramWaterMarkList().forEach(item -> array.add(item.toJson()));
      json.put("programWaterMarkList", array);
    }
    if (obj.getTheaterWaterMarkList() != null) {
      JsonArray array = new JsonArray();
      obj.getTheaterWaterMarkList().forEach(item -> array.add(item.toJson()));
      json.put("theaterWaterMarkList", array);
    }
  }
}