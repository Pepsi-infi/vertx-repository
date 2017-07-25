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

package utils;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;

/**
 * Converter for {@link utils.BaseResponse}.
 *
 * NOTE: This class has been automatically generated from the {@link utils.BaseResponse} original class using Vert.x codegen.
 */
public class BaseResponseConverter {

  public static void fromJson(JsonObject json, BaseResponse obj) {
    if (json.getValue("errorCode") instanceof String) {
      obj.setErrorCode((String)json.getValue("errorCode"));
    }
    if (json.getValue("errorMessage") instanceof String) {
      obj.setErrorMessage((String)json.getValue("errorMessage"));
    }
    if (json.getValue("resultStatus") instanceof Number) {
      obj.setResultStatus(((Number)json.getValue("resultStatus")).intValue());
    }
    if (json.getValue("status") instanceof Number) {
      obj.setStatus(((Number)json.getValue("status")).intValue());
    }
  }

  public static void toJson(BaseResponse obj, JsonObject json) {
    if (obj.getErrorCode() != null) {
      json.put("errorCode", obj.getErrorCode());
    }
    if (obj.getErrorMessage() != null) {
      json.put("errorMessage", obj.getErrorMessage());
    }
    if (obj.getResultStatus() != null) {
      json.put("resultStatus", obj.getResultStatus());
    }
    if (obj.getStatus() != null) {
      json.put("status", obj.getStatus());
    }
  }
}