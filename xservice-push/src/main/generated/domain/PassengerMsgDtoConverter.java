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
 * Converter for {@link domain.PassengerMsgDto}.
 *
 * NOTE: This class has been automatically generated from the {@link domain.PassengerMsgDto} original class using Vert.x codegen.
 */
public class PassengerMsgDtoConverter {

  public static void fromJson(JsonObject json, PassengerMsgDto obj) {
    if (json.getValue("action") instanceof Number) {
      obj.setAction(((Number)json.getValue("action")).intValue());
    }
    if (json.getValue("content") instanceof String) {
      obj.setContent((String)json.getValue("content"));
    }
    if (json.getValue("expireTime") instanceof String) {
      obj.setExpireTime((String)json.getValue("expireTime"));
    }
    if (json.getValue("id") instanceof Number) {
      obj.setId(((Number)json.getValue("id")).intValue());
    }
    if (json.getValue("inMsgCenter") instanceof Number) {
      obj.setInMsgCenter(((Number)json.getValue("inMsgCenter")).intValue());
    }
    if (json.getValue("msgCenterImgUrl") instanceof String) {
      obj.setMsgCenterImgUrl((String)json.getValue("msgCenterImgUrl"));
    }
    if (json.getValue("openType") instanceof Number) {
      obj.setOpenType(((Number)json.getValue("openType")).intValue());
    }
    if (json.getValue("openUrl") instanceof String) {
      obj.setOpenUrl((String)json.getValue("openUrl"));
    }
    if (json.getValue("sendTime") instanceof String) {
      obj.setSendTime((String)json.getValue("sendTime"));
    }
    if (json.getValue("sendType") instanceof Number) {
      obj.setSendType(((Number)json.getValue("sendType")).intValue());
    }
    if (json.getValue("status") instanceof Number) {
      obj.setStatus(((Number)json.getValue("status")).intValue());
    }
    if (json.getValue("title") instanceof String) {
      obj.setTitle((String)json.getValue("title"));
    }
  }

  public static void toJson(PassengerMsgDto obj, JsonObject json) {
    if (obj.getAction() != null) {
      json.put("action", obj.getAction());
    }
    if (obj.getContent() != null) {
      json.put("content", obj.getContent());
    }
    if (obj.getExpireTime() != null) {
      json.put("expireTime", obj.getExpireTime());
    }
    if (obj.getId() != null) {
      json.put("id", obj.getId());
    }
    if (obj.getInMsgCenter() != null) {
      json.put("inMsgCenter", obj.getInMsgCenter());
    }
    if (obj.getMsgCenterImgUrl() != null) {
      json.put("msgCenterImgUrl", obj.getMsgCenterImgUrl());
    }
    if (obj.getOpenType() != null) {
      json.put("openType", obj.getOpenType());
    }
    if (obj.getOpenUrl() != null) {
      json.put("openUrl", obj.getOpenUrl());
    }
    if (obj.getSendTime() != null) {
      json.put("sendTime", obj.getSendTime());
    }
    if (obj.getSendType() != null) {
      json.put("sendType", obj.getSendType());
    }
    if (obj.getStatus() != null) {
      json.put("status", obj.getStatus());
    }
    if (obj.getTitle() != null) {
      json.put("title", obj.getTitle());
    }
  }
}