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
 * Converter for {@link domain.DriverMsg}.
 *
 * NOTE: This class has been automatically generated from the {@link domain.DriverMsg} original class using Vert.x codegen.
 */
public class DriverMsgConverter {

  public static void fromJson(JsonObject json, DriverMsg obj) {
    if (json.getValue("content") instanceof String) {
      obj.setContent((String)json.getValue("content"));
    }
    if (json.getValue("createdUser") instanceof String) {
      obj.setCreatedUser((String)json.getValue("createdUser"));
    }
    if (json.getValue("enabled") instanceof Number) {
      obj.setEnabled(((Number)json.getValue("enabled")).intValue());
    }
    if (json.getValue("id") instanceof Number) {
      obj.setId(((Number)json.getValue("id")).longValue());
    }
    if (json.getValue("isImportant") instanceof Number) {
      obj.setIsImportant(((Number)json.getValue("isImportant")).intValue());
    }
    if (json.getValue("isShellsScreen") instanceof Number) {
      obj.setIsShellsScreen(((Number)json.getValue("isShellsScreen")).intValue());
    }
    if (json.getValue("jumpUrl") instanceof String) {
      obj.setJumpUrl((String)json.getValue("jumpUrl"));
    }
    if (json.getValue("msgType") instanceof Number) {
      obj.setMsgType(((Number)json.getValue("msgType")).intValue());
    }
    if (json.getValue("status") instanceof Number) {
      obj.setStatus(((Number)json.getValue("status")).intValue());
    }
    if (json.getValue("synopsis") instanceof String) {
      obj.setSynopsis((String)json.getValue("synopsis"));
    }
    if (json.getValue("title") instanceof String) {
      obj.setTitle((String)json.getValue("title"));
    }
    if (json.getValue("updatedUser") instanceof String) {
      obj.setUpdatedUser((String)json.getValue("updatedUser"));
    }
  }

  public static void toJson(DriverMsg obj, JsonObject json) {
    if (obj.getContent() != null) {
      json.put("content", obj.getContent());
    }
    if (obj.getCreatedUser() != null) {
      json.put("createdUser", obj.getCreatedUser());
    }
    json.put("enabled", obj.getEnabled());
    json.put("id", obj.getId());
    json.put("isImportant", obj.getIsImportant());
    json.put("isShellsScreen", obj.getIsShellsScreen());
    if (obj.getJumpUrl() != null) {
      json.put("jumpUrl", obj.getJumpUrl());
    }
    json.put("msgType", obj.getMsgType());
    json.put("status", obj.getStatus());
    if (obj.getSynopsis() != null) {
      json.put("synopsis", obj.getSynopsis());
    }
    if (obj.getTitle() != null) {
      json.put("title", obj.getTitle());
    }
    if (obj.getUpdatedUser() != null) {
      json.put("updatedUser", obj.getUpdatedUser());
    }
  }
}