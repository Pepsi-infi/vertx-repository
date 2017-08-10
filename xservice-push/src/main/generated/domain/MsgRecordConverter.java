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
 * Converter for {@link domain.MsgRecord}.
 *
 * NOTE: This class has been automatically generated from the {@link domain.MsgRecord} original class using Vert.x codegen.
 */
public class MsgRecordConverter {

  public static void fromJson(JsonObject json, MsgRecord obj) {
    if (json.getValue("amqpMsgId") instanceof String) {
      obj.setAmqpMsgId((String)json.getValue("amqpMsgId"));
    }
    if (json.getValue("channel") instanceof Number) {
      obj.setChannel(((Number)json.getValue("channel")).intValue());
    }
    if (json.getValue("id") instanceof Number) {
      obj.setId(((Number)json.getValue("id")).longValue());
    }
    if (json.getValue("msgBody") instanceof String) {
      obj.setMsgBody((String)json.getValue("msgBody"));
    }
    if (json.getValue("status") instanceof Number) {
      obj.setStatus(((Number)json.getValue("status")).intValue());
    }
  }

  public static void toJson(MsgRecord obj, JsonObject json) {
    if (obj.getAmqpMsgId() != null) {
      json.put("amqpMsgId", obj.getAmqpMsgId());
    }
    if (obj.getChannel() != null) {
      json.put("channel", obj.getChannel());
    }
    json.put("id", obj.getId());
    if (obj.getMsgBody() != null) {
      json.put("msgBody", obj.getMsgBody());
    }
    json.put("status", obj.getStatus());
  }
}