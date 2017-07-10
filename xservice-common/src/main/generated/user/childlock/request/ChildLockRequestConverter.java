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

package user.childlock.request;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;

/**
 * Converter for {@link user.childlock.request.ChildLockRequest}.
 *
 * NOTE: This class has been automatically generated from the {@link user.childlock.request.ChildLockRequest} original class using Vert.x codegen.
 */
public class ChildLockRequestConverter {

  public static void fromJson(JsonObject json, ChildLockRequest obj) {
    if (json.getValue("actionType") instanceof Number) {
      obj.setActionType(((Number)json.getValue("actionType")).intValue());
    }
    if (json.getValue("langcode") instanceof String) {
      obj.setLangcode((String)json.getValue("langcode"));
    }
    if (json.getValue("lockToken") instanceof String) {
      obj.setLockToken((String)json.getValue("lockToken"));
    }
    if (json.getValue("mac") instanceof String) {
      obj.setMac((String)json.getValue("mac"));
    }
    if (json.getValue("pin") instanceof String) {
      obj.setPin((String)json.getValue("pin"));
    }
    if (json.getValue("salesArea") instanceof String) {
      obj.setSalesArea((String)json.getValue("salesArea"));
    }
    if (json.getValue("status") instanceof Number) {
      obj.setStatus(((Number)json.getValue("status")).intValue());
    }
    if (json.getValue("terminalApplication") instanceof String) {
      obj.setTerminalApplication((String)json.getValue("terminalApplication"));
    }
    if (json.getValue("terminalBrand") instanceof String) {
      obj.setTerminalBrand((String)json.getValue("terminalBrand"));
    }
    if (json.getValue("ticket") instanceof String) {
      obj.setTicket((String)json.getValue("ticket"));
    }
    if (json.getValue("token") instanceof String) {
      obj.setToken((String)json.getValue("token"));
    }
    if (json.getValue("uid") instanceof String) {
      obj.setUid((String)json.getValue("uid"));
    }
    if (json.getValue("wcode") instanceof String) {
      obj.setWcode((String)json.getValue("wcode"));
    }
  }

  public static void toJson(ChildLockRequest obj, JsonObject json) {
    if (obj.getActionType() != null) {
      json.put("actionType", obj.getActionType());
    }
    if (obj.getLangcode() != null) {
      json.put("langcode", obj.getLangcode());
    }
    if (obj.getLockToken() != null) {
      json.put("lockToken", obj.getLockToken());
    }
    if (obj.getMac() != null) {
      json.put("mac", obj.getMac());
    }
    if (obj.getPin() != null) {
      json.put("pin", obj.getPin());
    }
    if (obj.getSalesArea() != null) {
      json.put("salesArea", obj.getSalesArea());
    }
    if (obj.getStatus() != null) {
      json.put("status", obj.getStatus());
    }
    if (obj.getTerminalApplication() != null) {
      json.put("terminalApplication", obj.getTerminalApplication());
    }
    if (obj.getTerminalBrand() != null) {
      json.put("terminalBrand", obj.getTerminalBrand());
    }
    if (obj.getTicket() != null) {
      json.put("ticket", obj.getTicket());
    }
    if (obj.getToken() != null) {
      json.put("token", obj.getToken());
    }
    if (obj.getUid() != null) {
      json.put("uid", obj.getUid());
    }
    if (obj.getWcode() != null) {
      json.put("wcode", obj.getWcode());
    }
  }
}