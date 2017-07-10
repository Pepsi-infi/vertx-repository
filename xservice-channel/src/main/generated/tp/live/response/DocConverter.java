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
 * Converter for {@link tp.live.response.Doc}.
 *
 * NOTE: This class has been automatically generated from the {@link tp.live.response.Doc} original class using Vert.x codegen.
 */
public class DocConverter {

  public static void fromJson(JsonObject json, Doc obj) {
    if (json.getValue("document") instanceof String) {
      obj.setDocument((String)json.getValue("document"));
    }
  }

  public static void toJson(Doc obj, JsonObject json) {
    if (obj.getDocument() != null) {
      json.put("document", obj.getDocument());
    }
  }
}