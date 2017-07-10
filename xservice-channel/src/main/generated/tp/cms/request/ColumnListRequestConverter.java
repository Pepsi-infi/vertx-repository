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

package tp.cms.request;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;

/**
 * Converter for {@link tp.cms.request.ColumnListRequest}.
 *
 * NOTE: This class has been automatically generated from the {@link tp.cms.request.ColumnListRequest} original class using Vert.x codegen.
 */
public class ColumnListRequestConverter {

  public static void fromJson(JsonObject json, ColumnListRequest obj) {
    if (json.getValue("lang") instanceof String) {
      obj.setLang((String)json.getValue("lang"));
    }
    if (json.getValue("pid") instanceof Number) {
      obj.setPid(((Number)json.getValue("pid")).intValue());
    }
    if (json.getValue("platform") instanceof String) {
      obj.setPlatform((String)json.getValue("platform"));
    }
  }

  public static void toJson(ColumnListRequest obj, JsonObject json) {
    if (obj.getLang() != null) {
      json.put("lang", obj.getLang());
    }
    if (obj.getPid() != null) {
      json.put("pid", obj.getPid());
    }
    if (obj.getPlatform() != null) {
      json.put("platform", obj.getPlatform());
    }
  }
}