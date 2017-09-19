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

package domain;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;

/**
 * Converter for {@link domain.DriverMsgDto}.
 *
 * NOTE: This class has been automatically generated from the {@link domain.DriverMsgDto} original class using Vert.x codegen.
 */
public class DriverMsgDtoConverter {

  public static void fromJson(JsonObject json, DriverMsgDto obj) {
    if (json.getValue("msgType") instanceof Number) {
      obj.setMsgType(((Number)json.getValue("msgType")).intValue());
    }
    if (json.getValue("pageNumber") instanceof Number) {
      obj.setPageNumber(((Number)json.getValue("pageNumber")).intValue());
    }
    if (json.getValue("pageSize") instanceof Number) {
      obj.setPageSize(((Number)json.getValue("pageSize")).intValue());
    }
    if (json.getValue("title") instanceof String) {
      obj.setTitle((String)json.getValue("title"));
    }
  }

  public static void toJson(DriverMsgDto obj, JsonObject json) {
    if (obj.getMsgType() != null) {
      json.put("msgType", obj.getMsgType());
    }
    if (obj.getPageNumber() != null) {
      json.put("pageNumber", obj.getPageNumber());
    }
    if (obj.getPageSize() != null) {
      json.put("pageSize", obj.getPageSize());
    }
    if (obj.getTitle() != null) {
      json.put("title", obj.getTitle());
    }
  }
}