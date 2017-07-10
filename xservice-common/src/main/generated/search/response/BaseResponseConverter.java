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

package search.response;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;

/**
 * Converter for {@link search.response.BaseResponse}.
 *
 * NOTE: This class has been automatically generated from the {@link search.response.BaseResponse} original class using Vert.x codegen.
 */
public class BaseResponseConverter {

  public static void fromJson(JsonObject json, BaseResponse obj) {
    if (json.getValue("innerCode") instanceof String) {
      obj.setInnerCode((String)json.getValue("innerCode"));
    }
  }

  public static void toJson(BaseResponse obj, JsonObject json) {
    if (obj.getInnerCode() != null) {
      json.put("innerCode", obj.getInnerCode());
    }
  }
}