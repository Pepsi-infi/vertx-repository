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

package user.commom;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;

/**
 * Converter for {@link user.commom.CommonParam}.
 *
 * NOTE: This class has been automatically generated from the {@link user.commom.CommonParam} original class using Vert.x codegen.
 */
public class CommonParamConverter {

  public static void fromJson(JsonObject json, CommonParam obj) {
    if (json.getValue("appVersion") instanceof String) {
      obj.setAppVersion((String)json.getValue("appVersion"));
    }
    if (json.getValue("bsChannel") instanceof String) {
      obj.setBsChannel((String)json.getValue("bsChannel"));
    }
    if (json.getValue("countryArea") instanceof String) {
      obj.setCountryArea((String)json.getValue("countryArea"));
    }
    if (json.getValue("devId") instanceof String) {
      obj.setDevId((String)json.getValue("devId"));
    }
    if (json.getValue("imeiArea") instanceof String) {
      obj.setImeiArea((String)json.getValue("imeiArea"));
    }
    if (json.getValue("ip") instanceof String) {
      obj.setIp((String)json.getValue("ip"));
    }
    if (json.getValue("langcode") instanceof String) {
      obj.setLangcode((String)json.getValue("langcode"));
    }
    if (json.getValue("mac") instanceof String) {
      obj.setMac((String)json.getValue("mac"));
    }
    if (json.getValue("pcode") instanceof String) {
      obj.setPcode((String)json.getValue("pcode"));
    }
    if (json.getValue("salesArea") instanceof String) {
      obj.setSalesArea((String)json.getValue("salesArea"));
    }
    if (json.getValue("terminalApplication") instanceof String) {
      obj.setTerminalApplication((String)json.getValue("terminalApplication"));
    }
    if (json.getValue("terminalBrand") instanceof String) {
      obj.setTerminalBrand((String)json.getValue("terminalBrand"));
    }
    if (json.getValue("terminalSeries") instanceof String) {
      obj.setTerminalSeries((String)json.getValue("terminalSeries"));
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

  public static void toJson(CommonParam obj, JsonObject json) {
    if (obj.getAppVersion() != null) {
      json.put("appVersion", obj.getAppVersion());
    }
    if (obj.getAreaCode() != null) {
      json.put("areaCode", obj.getAreaCode());
    }
    if (obj.getBsChannel() != null) {
      json.put("bsChannel", obj.getBsChannel());
    }
    if (obj.getCountryArea() != null) {
      json.put("countryArea", obj.getCountryArea());
    }
    if (obj.getDevId() != null) {
      json.put("devId", obj.getDevId());
    }
    if (obj.getImeiArea() != null) {
      json.put("imeiArea", obj.getImeiArea());
    }
    if (obj.getIp() != null) {
      json.put("ip", obj.getIp());
    }
    if (obj.getLangcode() != null) {
      json.put("langcode", obj.getLangcode());
    }
    if (obj.getMac() != null) {
      json.put("mac", obj.getMac());
    }
    if (obj.getPcode() != null) {
      json.put("pcode", obj.getPcode());
    }
    if (obj.getSalesArea() != null) {
      json.put("salesArea", obj.getSalesArea());
    }
    if (obj.getTerminalApplication() != null) {
      json.put("terminalApplication", obj.getTerminalApplication());
    }
    if (obj.getTerminalBrand() != null) {
      json.put("terminalBrand", obj.getTerminalBrand());
    }
    if (obj.getTerminalSeries() != null) {
      json.put("terminalSeries", obj.getTerminalSeries());
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