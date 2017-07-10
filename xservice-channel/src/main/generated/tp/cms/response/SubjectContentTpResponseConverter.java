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

package tp.cms.response;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;

/**
 * Converter for {@link tp.cms.response.SubjectContentTpResponse}.
 *
 * NOTE: This class has been automatically generated from the {@link tp.cms.response.SubjectContentTpResponse} original class using Vert.x codegen.
 */
public class SubjectContentTpResponseConverter {

  public static void fromJson(JsonObject json, SubjectContentTpResponse obj) {
    if (json.getValue("code") instanceof Number) {
      obj.setCode(((Number)json.getValue("code")).intValue());
    }
    if (json.getValue("data") instanceof JsonObject) {
      obj.setData(new tp.cms.response.SubjectContent((JsonObject)json.getValue("data")));
    }
    if (json.getValue("innerCode") instanceof String) {
      obj.setInnerCode((String)json.getValue("innerCode"));
    }
    if (json.getValue("msg") instanceof String) {
      obj.setMsg((String)json.getValue("msg"));
    }
  }

  public static void toJson(SubjectContentTpResponse obj, JsonObject json) {
    if (obj.getCode() != null) {
      json.put("code", obj.getCode());
    }
    if (obj.getData() != null) {
      json.put("data", obj.getData().toJson());
    }
    if (obj.getInnerCode() != null) {
      json.put("innerCode", obj.getInnerCode());
    }
    if (obj.getMsg() != null) {
      json.put("msg", obj.getMsg());
    }
  }
}