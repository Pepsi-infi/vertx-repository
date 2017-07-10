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

package service.dto.pageCategory;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;

/**
 * Converter for {@link service.dto.pageCategory.BaseChannel}.
 *
 * NOTE: This class has been automatically generated from the {@link service.dto.pageCategory.BaseChannel} original class using Vert.x codegen.
 */
public class BaseChannelConverter {

  public static void fromJson(JsonObject json, BaseChannel obj) {
    if (json.getValue("configInfo") instanceof String) {
      obj.setConfigInfo((String)json.getValue("configInfo"));
    }
    if (json.getValue("cpCategoryId") instanceof String) {
      obj.setCpCategoryId((String)json.getValue("cpCategoryId"));
    }
    if (json.getValue("cpId") instanceof String) {
      obj.setCpId((String)json.getValue("cpId"));
    }
    if (json.getValue("dataType") instanceof Number) {
      obj.setDataType(((Number)json.getValue("dataType")).intValue());
    }
    if (json.getValue("dataUrl") instanceof String) {
      obj.setDataUrl((String)json.getValue("dataUrl"));
    }
    if (json.getValue("globalId") instanceof String) {
      obj.setGlobalId((String)json.getValue("globalId"));
    }
    if (json.getValue("iconType") instanceof String) {
      obj.setIconType((String)json.getValue("iconType"));
    }
    if (json.getValue("jump") instanceof JsonObject) {
      obj.setJump(new service.dto.pageCategory.JumpData((JsonObject)json.getValue("jump")));
    }
    if (json.getValue("titleDataType") instanceof Number) {
      obj.setTitleDataType(((Number)json.getValue("titleDataType")).intValue());
    }
  }

  public static void toJson(BaseChannel obj, JsonObject json) {
    if (obj.getConfigInfo() != null) {
      json.put("configInfo", obj.getConfigInfo());
    }
    if (obj.getCpCategoryId() != null) {
      json.put("cpCategoryId", obj.getCpCategoryId());
    }
    if (obj.getCpId() != null) {
      json.put("cpId", obj.getCpId());
    }
    json.put("dataType", obj.getDataType());
    if (obj.getDataUrl() != null) {
      json.put("dataUrl", obj.getDataUrl());
    }
    if (obj.getGlobalId() != null) {
      json.put("globalId", obj.getGlobalId());
    }
    if (obj.getIconType() != null) {
      json.put("iconType", obj.getIconType());
    }
    if (obj.getJump() != null) {
      json.put("jump", obj.getJump().toJson());
    }
    if (obj.getTitleDataType() != null) {
      json.put("titleDataType", obj.getTitleDataType());
    }
  }
}