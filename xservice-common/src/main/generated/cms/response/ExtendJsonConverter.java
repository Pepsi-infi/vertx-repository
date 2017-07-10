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
 * Converter for {@link cms.response.ExtendJson}.
 *
 * NOTE: This class has been automatically generated from the {@link cms.response.ExtendJson} original class using Vert.x codegen.
 */
public class ExtendJsonConverter {

  public static void fromJson(JsonObject json, ExtendJson obj) {
    if (json.getValue("extendCid") instanceof String) {
      obj.setExtendCid((String)json.getValue("extendCid"));
    }
    if (json.getValue("extendPage") instanceof String) {
      obj.setExtendPage((String)json.getValue("extendPage"));
    }
    if (json.getValue("extendPicAll") instanceof JsonObject) {
      java.util.Map<String, java.lang.String> map = new java.util.LinkedHashMap<>();
      json.getJsonObject("extendPicAll").forEach(entry -> {
        if (entry.getValue() instanceof String)
          map.put(entry.getKey(), (String)entry.getValue());
      });
      obj.setExtendPicAll(map);
    }
    if (json.getValue("extendPid") instanceof String) {
      obj.setExtendPid((String)json.getValue("extendPid"));
    }
    if (json.getValue("extendRange") instanceof String) {
      obj.setExtendRange((String)json.getValue("extendRange"));
    }
  }

  public static void toJson(ExtendJson obj, JsonObject json) {
    if (obj.getExtendCid() != null) {
      json.put("extendCid", obj.getExtendCid());
    }
    if (obj.getExtendPage() != null) {
      json.put("extendPage", obj.getExtendPage());
    }
    if (obj.getExtendPicAll() != null) {
      JsonObject map = new JsonObject();
      obj.getExtendPicAll().forEach((key,value) -> map.put(key, value));
      json.put("extendPicAll", map);
    }
    if (obj.getExtendPid() != null) {
      json.put("extendPid", obj.getExtendPid());
    }
    if (obj.getExtendRange() != null) {
      json.put("extendRange", obj.getExtendRange());
    }
  }
}