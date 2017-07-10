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

package sysconfig.whitelist.request;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;

/**
 * Converter for {@link sysconfig.whitelist.request.WhiteListRequest}.
 *
 * NOTE: This class has been automatically generated from the {@link sysconfig.whitelist.request.WhiteListRequest} original class using Vert.x codegen.
 */
public class WhiteListRequestConverter {

  public static void fromJson(JsonObject json, WhiteListRequest obj) {
    if (json.getValue("devId") instanceof String) {
      obj.setDevId((String)json.getValue("devId"));
    }
    if (json.getValue("moduleId") instanceof String) {
      obj.setModuleId((String)json.getValue("moduleId"));
    }
    if (json.getValue("operator") instanceof String) {
      obj.setOperator((String)json.getValue("operator"));
    }
    if (json.getValue("status") instanceof Number) {
      obj.setStatus(((Number)json.getValue("status")).intValue());
    }
    if (json.getValue("time") instanceof String) {
      obj.setTime((String)json.getValue("time"));
    }
  }

  public static void toJson(WhiteListRequest obj, JsonObject json) {
    if (obj.getDevId() != null) {
      json.put("devId", obj.getDevId());
    }
    if (obj.getModuleId() != null) {
      json.put("moduleId", obj.getModuleId());
    }
    if (obj.getOperator() != null) {
      json.put("operator", obj.getOperator());
    }
    json.put("status", obj.getStatus());
    if (obj.getTime() != null) {
      json.put("time", obj.getTime());
    }
  }
}