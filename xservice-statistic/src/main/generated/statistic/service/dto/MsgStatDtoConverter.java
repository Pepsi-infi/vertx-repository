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

package statistic.service.dto;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;

/**
 * Converter for {@link statistic.service.dto.MsgStatDto}.
 *
 * NOTE: This class has been automatically generated from the {@link statistic.service.dto.MsgStatDto} original class using Vert.x codegen.
 */
public class MsgStatDtoConverter {

  public static void fromJson(JsonObject json, MsgStatDto obj) {
    if (json.getValue("action") instanceof Number) {
      obj.setAction(((Number)json.getValue("action")).intValue());
    }
    if (json.getValue("channel") instanceof Number) {
      obj.setChannel(((Number)json.getValue("channel")).intValue());
    }
    if (json.getValue("isAcceptPush") instanceof Number) {
      obj.setIsAcceptPush(((Number)json.getValue("isAcceptPush")).intValue());
    }
    if (json.getValue("msgId") instanceof String) {
      obj.setMsgId((String)json.getValue("msgId"));
    }
    if (json.getValue("osType") instanceof Number) {
      obj.setOsType(((Number)json.getValue("osType")).intValue());
    }
    if (json.getValue("sendTime") instanceof String) {
      obj.setSendTime((String)json.getValue("sendTime"));
    }
  }

  public static void toJson(MsgStatDto obj, JsonObject json) {
    if (obj.getAction() != null) {
      json.put("action", obj.getAction());
    }
    if (obj.getChannel() != null) {
      json.put("channel", obj.getChannel());
    }
    if (obj.getIsAcceptPush() != null) {
      json.put("isAcceptPush", obj.getIsAcceptPush());
    }
    if (obj.getMsgId() != null) {
      json.put("msgId", obj.getMsgId());
    }
    if (obj.getOsType() != null) {
      json.put("osType", obj.getOsType());
    }
    if (obj.getSendTime() != null) {
      json.put("sendTime", obj.getSendTime());
    }
  }
}