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

package service.dto;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;

/**
 * Converter for {@link service.dto.MsgStatResultPageWrapper}.
 *
 * NOTE: This class has been automatically generated from the {@link service.dto.MsgStatResultPageWrapper} original class using Vert.x codegen.
 */
public class MsgStatResultPageWrapperConverter {

  public static void fromJson(JsonObject json, MsgStatResultPageWrapper obj) {
    if (json.getValue("code") instanceof Number) {
      obj.setCode(((Number)json.getValue("code")).intValue());
    }
    if (json.getValue("data") instanceof JsonObject) {
      obj.setData(new service.dto.MsgStatResultPage((JsonObject)json.getValue("data")));
    }
    if (json.getValue("msg") instanceof String) {
      obj.setMsg((String)json.getValue("msg"));
    }
    if (json.getValue("time") instanceof Number) {
      obj.setTime(((Number)json.getValue("time")).longValue());
    }
  }

  public static void toJson(MsgStatResultPageWrapper obj, JsonObject json) {
    json.put("code", obj.getCode());
    if (obj.getData() != null) {
      json.put("data", obj.getData().toJson());
    }
    if (obj.getMsg() != null) {
      json.put("msg", obj.getMsg());
    }
    json.put("time", obj.getTime());
  }
}