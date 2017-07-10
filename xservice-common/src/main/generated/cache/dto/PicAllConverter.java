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

package cache.dto;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;

/**
 * Converter for {@link cache.dto.PicAll}.
 *
 * NOTE: This class has been automatically generated from the {@link cache.dto.PicAll} original class using Vert.x codegen.
 */
public class PicAllConverter {

  public static void fromJson(JsonObject json, PicAll obj) {
    if (json.getValue("picKey") instanceof String) {
      obj.setPicKey((String)json.getValue("picKey"));
    }
    if (json.getValue("picValue") instanceof String) {
      obj.setPicValue((String)json.getValue("picValue"));
    }
  }

  public static void toJson(PicAll obj, JsonObject json) {
    if (obj.getPicKey() != null) {
      json.put("picKey", obj.getPicKey());
    }
    if (obj.getPicValue() != null) {
      json.put("picValue", obj.getPicValue());
    }
  }
}