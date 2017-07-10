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
 * Converter for {@link service.dto.theaterpack.TheaterPackedWapper}.
 *
 * NOTE: This class has been automatically generated from the {@link service.dto.theaterpack.TheaterPackedWapper} original class using Vert.x codegen.
 */
public class TheaterPackedWapperConverter {

  public static void fromJson(JsonObject json, TheaterPackedWapper obj) {
    if (json.getValue("data") instanceof JsonObject) {
      obj.setData(new service.dto.theaterpack.WaterMarksDto((JsonObject)json.getValue("data")));
    }
  }

  public static void toJson(TheaterPackedWapper obj, JsonObject json) {
    if (obj.getData() != null) {
      json.put("data", obj.getData().toJson());
    }
  }
}