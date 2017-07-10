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

package cms.response;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;

/**
 * Converter for {@link cms.response.ShowTagList}.
 *
 * NOTE: This class has been automatically generated from the {@link cms.response.ShowTagList} original class using Vert.x codegen.
 */
public class ShowTagListConverter {

  public static void fromJson(JsonObject json, ShowTagList obj) {
    if (json.getValue("id") instanceof String) {
      obj.setId((String)json.getValue("id"));
    }
    if (json.getValue("typeId") instanceof String) {
      obj.setTypeId((String)json.getValue("typeId"));
    }
    if (json.getValue("value") instanceof String) {
      obj.setValue((String)json.getValue("value"));
    }
  }

  public static void toJson(ShowTagList obj, JsonObject json) {
    if (obj.getId() != null) {
      json.put("id", obj.getId());
    }
    if (obj.getTypeId() != null) {
      json.put("typeId", obj.getTypeId());
    }
    if (obj.getValue() != null) {
      json.put("value", obj.getValue());
    }
  }
}