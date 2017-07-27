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
 * Converter for {@link statistic.service.dto.DeviceDto}.
 *
 * NOTE: This class has been automatically generated from the {@link statistic.service.dto.DeviceDto} original class using Vert.x codegen.
 */
public class DeviceDtoConverter {

  public static void fromJson(JsonObject json, DeviceDto obj) {
    if (json.getValue("appCode") instanceof Number) {
      obj.setAppCode(((Number)json.getValue("appCode")).intValue());
    }
    if (json.getValue("appVersion") instanceof String) {
      obj.setAppVersion((String)json.getValue("appVersion"));
    }
    if (json.getValue("deviceToken") instanceof String) {
      obj.setDeviceToken((String)json.getValue("deviceToken"));
    }
    if (json.getValue("deviceType") instanceof String) {
      obj.setDeviceType((String)json.getValue("deviceType"));
    }
    if (json.getValue("imei") instanceof String) {
      obj.setImei((String)json.getValue("imei"));
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
    if (json.getValue("userType") instanceof Number) {
      obj.setUserType(((Number)json.getValue("userType")).intValue());
    }
  }

  public static void toJson(DeviceDto obj, JsonObject json) {
    if (obj.getAppCode() != null) {
      json.put("appCode", obj.getAppCode());
    }
    if (obj.getAppVersion() != null) {
      json.put("appVersion", obj.getAppVersion());
    }
    if (obj.getDeviceToken() != null) {
      json.put("deviceToken", obj.getDeviceToken());
    }
    if (obj.getDeviceType() != null) {
      json.put("deviceType", obj.getDeviceType());
    }
    if (obj.getImei() != null) {
      json.put("imei", obj.getImei());
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
    if (obj.getUserType() != null) {
      json.put("userType", obj.getUserType());
    }
  }
}