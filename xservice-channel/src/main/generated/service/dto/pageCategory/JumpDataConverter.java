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

package service.dto.pageCategory;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;

/**
 * Converter for {@link service.dto.pageCategory.JumpData}.
 *
 * NOTE: This class has been automatically generated from the {@link service.dto.pageCategory.JumpData} original class using Vert.x codegen.
 */
public class JumpDataConverter {

  public static void fromJson(JsonObject json, JumpData obj) {
    if (json.getValue("type") instanceof Number) {
      obj.setType(((Number)json.getValue("type")).intValue());
    }
    if (json.getValue("value") instanceof JsonObject) {
      obj.setValue(new service.dto.pageCategory.Channel((JsonObject)json.getValue("value")));
    }
  }

  public static void toJson(JumpData obj, JsonObject json) {
    if (obj.getType() != null) {
      json.put("type", obj.getType());
    }
    if (obj.getValue() != null) {
      json.put("value", obj.getValue().toJson());
    }
  }
}