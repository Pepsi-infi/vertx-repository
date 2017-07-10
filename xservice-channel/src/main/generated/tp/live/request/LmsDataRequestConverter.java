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

package tp.live.request;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;

/**
 * Converter for {@link tp.live.request.LmsDataRequest}.
 *
 * NOTE: This class has been automatically generated from the {@link tp.live.request.LmsDataRequest} original class using Vert.x codegen.
 */
public class LmsDataRequestConverter {

  public static void fromJson(JsonObject json, LmsDataRequest obj) {
    if (json.getValue("fetchSize") instanceof Number) {
      obj.setFetchSize(((Number)json.getValue("fetchSize")).intValue());
    }
    if (json.getValue("offSet") instanceof Number) {
      obj.setOffSet(((Number)json.getValue("offSet")).intValue());
    }
    if (json.getValue("paramMap") instanceof JsonObject) {
      java.util.Map<String, java.lang.Object> map = new java.util.LinkedHashMap<>();
      json.getJsonObject("paramMap").forEach(entry -> {
        if (entry.getValue() instanceof Object)
          map.put(entry.getKey(), entry.getValue());
      });
      obj.setParamMap(map);
    }
    if (json.getValue("sourceId") instanceof String) {
      obj.setSourceId((String)json.getValue("sourceId"));
    }
    if (json.getValue("splatid") instanceof String) {
      obj.setSplatid((String)json.getValue("splatid"));
    }
    if (json.getValue("url") instanceof String) {
      obj.setUrl((String)json.getValue("url"));
    }
  }

  public static void toJson(LmsDataRequest obj, JsonObject json) {
    json.put("fetchSize", obj.getFetchSize());
    json.put("offSet", obj.getOffSet());
    if (obj.getParamMap() != null) {
      JsonObject map = new JsonObject();
      obj.getParamMap().forEach((key,value) -> map.put(key, value));
      json.put("paramMap", map);
    }
    if (obj.getSourceId() != null) {
      json.put("sourceId", obj.getSourceId());
    }
    if (obj.getSplatid() != null) {
      json.put("splatid", obj.getSplatid());
    }
    if (obj.getUrl() != null) {
      json.put("url", obj.getUrl());
    }
  }
}