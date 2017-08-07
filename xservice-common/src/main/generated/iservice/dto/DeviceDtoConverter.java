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

package iservice.dto;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;

/**
 * Converter for {@link iservice.dto.DeviceDto}.
 *
 * NOTE: This class has been automatically generated from the {@link iservice.dto.DeviceDto} original class using Vert.x codegen.
 */
public class DeviceDtoConverter {

  public static void fromJson(JsonObject json, DeviceDto obj) {
    if (json.getValue("antFingerprint") instanceof String) {
      obj.setAntFingerprint((String)json.getValue("antFingerprint"));
    }
    if (json.getValue("apnsToken") instanceof String) {
      obj.setApnsToken((String)json.getValue("apnsToken"));
    }
    if (json.getValue("appCode") instanceof Number) {
      obj.setAppCode(((Number)json.getValue("appCode")).intValue());
    }
    if (json.getValue("appVersion") instanceof String) {
      obj.setAppVersion((String)json.getValue("appVersion"));
    }
    if (json.getValue("deviceType") instanceof String) {
      obj.setDeviceType((String)json.getValue("deviceType"));
    }
    if (json.getValue("gcmToken") instanceof String) {
      obj.setGcmToken((String)json.getValue("gcmToken"));
    }
    if (json.getValue("id") instanceof Number) {
      obj.setId(((Number)json.getValue("id")).longValue());
    }
    if (json.getValue("imei") instanceof String) {
      obj.setImei((String)json.getValue("imei"));
    }
    if (json.getValue("isAcceptPush") instanceof Number) {
      obj.setIsAcceptPush(((Number)json.getValue("isAcceptPush")).intValue());
    }
    if (json.getValue("miToken") instanceof String) {
      obj.setMiToken((String)json.getValue("miToken"));
    }
    if (json.getValue("osType") instanceof Number) {
      obj.setOsType(((Number)json.getValue("osType")).intValue());
    }
    if (json.getValue("osVersion") instanceof String) {
      obj.setOsVersion((String)json.getValue("osVersion"));
    }
    if (json.getValue("phone") instanceof String) {
      obj.setPhone((String)json.getValue("phone"));
    }
    if (json.getValue("uid") instanceof Number) {
      obj.setUid(((Number)json.getValue("uid")).longValue());
    }
  }

  public static void toJson(DeviceDto obj, JsonObject json) {
    if (obj.getAntFingerprint() != null) {
      json.put("antFingerprint", obj.getAntFingerprint());
    }
    if (obj.getApnsToken() != null) {
      json.put("apnsToken", obj.getApnsToken());
    }
    if (obj.getAppCode() != null) {
      json.put("appCode", obj.getAppCode());
    }
    if (obj.getAppVersion() != null) {
      json.put("appVersion", obj.getAppVersion());
    }
    if (obj.getDeviceType() != null) {
      json.put("deviceType", obj.getDeviceType());
    }
    if (obj.getGcmToken() != null) {
      json.put("gcmToken", obj.getGcmToken());
    }
    if (obj.getId() != null) {
      json.put("id", obj.getId());
    }
    if (obj.getImei() != null) {
      json.put("imei", obj.getImei());
    }
    if (obj.getIsAcceptPush() != null) {
      json.put("isAcceptPush", obj.getIsAcceptPush());
    }
    if (obj.getMiToken() != null) {
      json.put("miToken", obj.getMiToken());
    }
    if (obj.getOsType() != null) {
      json.put("osType", obj.getOsType());
    }
    if (obj.getOsVersion() != null) {
      json.put("osVersion", obj.getOsVersion());
    }
    if (obj.getPhone() != null) {
      json.put("phone", obj.getPhone());
    }
    if (obj.getUid() != null) {
      json.put("uid", obj.getUid());
    }
  }
}