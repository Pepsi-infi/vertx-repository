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
 * Converter for {@link tp.live.response.Fpic}.
 *
 * NOTE: This class has been automatically generated from the {@link tp.live.response.Fpic} original class using Vert.x codegen.
 */
public class FpicConverter {

  public static void fromJson(JsonObject json, Fpic obj) {
    if (json.getValue("pic1_746_419") instanceof String) {
      obj.setPic1_746_419((String)json.getValue("pic1_746_419"));
    }
    if (json.getValue("pic2_960_540") instanceof String) {
      obj.setPic2_960_540((String)json.getValue("pic2_960_540"));
    }
    if (json.getValue("pic5_1920_1080") instanceof String) {
      obj.setPic5_1920_1080((String)json.getValue("pic5_1920_1080"));
    }
  }

  public static void toJson(Fpic obj, JsonObject json) {
    if (obj.getPic1_746_419() != null) {
      json.put("pic1_746_419", obj.getPic1_746_419());
    }
    if (obj.getPic2_960_540() != null) {
      json.put("pic2_960_540", obj.getPic2_960_540());
    }
    if (obj.getPic5_1920_1080() != null) {
      json.put("pic5_1920_1080", obj.getPic5_1920_1080());
    }
  }
}