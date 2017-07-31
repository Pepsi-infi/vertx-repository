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
 * Converter for {@link service.dto.MsgStatResultDto}.
 *
 * NOTE: This class has been automatically generated from the {@link service.dto.MsgStatResultDto} original class using Vert.x codegen.
 */
public class MsgStatResultDtoConverter {

  public static void fromJson(JsonObject json, MsgStatResultDto obj) {
    if (json.getValue("arriveAndroidSum") instanceof Number) {
      obj.setArriveAndroidSum(((Number)json.getValue("arriveAndroidSum")).longValue());
    }
    if (json.getValue("arriveGcmSum") instanceof Number) {
      obj.setArriveGcmSum(((Number)json.getValue("arriveGcmSum")).longValue());
    }
    if (json.getValue("arriveIosSum") instanceof Number) {
      obj.setArriveIosSum(((Number)json.getValue("arriveIosSum")).longValue());
    }
    if (json.getValue("arriveMiSum") instanceof Number) {
      obj.setArriveMiSum(((Number)json.getValue("arriveMiSum")).longValue());
    }
    if (json.getValue("arriveSockSum") instanceof Number) {
      obj.setArriveSockSum(((Number)json.getValue("arriveSockSum")).longValue());
    }
    if (json.getValue("arriveSum") instanceof Number) {
      obj.setArriveSum(((Number)json.getValue("arriveSum")).longValue());
    }
    if (json.getValue("channel") instanceof Number) {
      obj.setChannel(((Number)json.getValue("channel")).intValue());
    }
    if (json.getValue("msgId") instanceof String) {
      obj.setMsgId((String)json.getValue("msgId"));
    }
    if (json.getValue("sendAndroidSum") instanceof Number) {
      obj.setSendAndroidSum(((Number)json.getValue("sendAndroidSum")).longValue());
    }
    if (json.getValue("sendGcmSum") instanceof Number) {
      obj.setSendGcmSum(((Number)json.getValue("sendGcmSum")).longValue());
    }
    if (json.getValue("sendIosSum") instanceof Number) {
      obj.setSendIosSum(((Number)json.getValue("sendIosSum")).longValue());
    }
    if (json.getValue("sendMiSum") instanceof Number) {
      obj.setSendMiSum(((Number)json.getValue("sendMiSum")).longValue());
    }
    if (json.getValue("sendSockSum") instanceof Number) {
      obj.setSendSockSum(((Number)json.getValue("sendSockSum")).longValue());
    }
    if (json.getValue("sendSum") instanceof Number) {
      obj.setSendSum(((Number)json.getValue("sendSum")).longValue());
    }
    if (json.getValue("sendTime") instanceof String) {
      obj.setSendTime((String)json.getValue("sendTime"));
    }
    if (json.getValue("statTime") instanceof String) {
      obj.setStatTime((String)json.getValue("statTime"));
    }
  }

  public static void toJson(MsgStatResultDto obj, JsonObject json) {
    if (obj.getArriveAndroidSum() != null) {
      json.put("arriveAndroidSum", obj.getArriveAndroidSum());
    }
    if (obj.getArriveGcmSum() != null) {
      json.put("arriveGcmSum", obj.getArriveGcmSum());
    }
    if (obj.getArriveIosSum() != null) {
      json.put("arriveIosSum", obj.getArriveIosSum());
    }
    if (obj.getArriveMiSum() != null) {
      json.put("arriveMiSum", obj.getArriveMiSum());
    }
    if (obj.getArriveSockSum() != null) {
      json.put("arriveSockSum", obj.getArriveSockSum());
    }
    if (obj.getArriveSum() != null) {
      json.put("arriveSum", obj.getArriveSum());
    }
    if (obj.getChannel() != null) {
      json.put("channel", obj.getChannel());
    }
    if (obj.getMsgId() != null) {
      json.put("msgId", obj.getMsgId());
    }
    if (obj.getSendAndroidSum() != null) {
      json.put("sendAndroidSum", obj.getSendAndroidSum());
    }
    if (obj.getSendGcmSum() != null) {
      json.put("sendGcmSum", obj.getSendGcmSum());
    }
    if (obj.getSendIosSum() != null) {
      json.put("sendIosSum", obj.getSendIosSum());
    }
    if (obj.getSendMiSum() != null) {
      json.put("sendMiSum", obj.getSendMiSum());
    }
    if (obj.getSendSockSum() != null) {
      json.put("sendSockSum", obj.getSendSockSum());
    }
    if (obj.getSendSum() != null) {
      json.put("sendSum", obj.getSendSum());
    }
    if (obj.getSendTime() != null) {
      json.put("sendTime", obj.getSendTime());
    }
    if (obj.getStatTime() != null) {
      json.put("statTime", obj.getStatTime());
    }
  }
}