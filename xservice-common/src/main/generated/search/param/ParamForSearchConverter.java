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

package search.param;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;

/**
 * Converter for {@link search.param.ParamForSearch}.
 *
 * NOTE: This class has been automatically generated from the {@link search.param.ParamForSearch} original class using Vert.x codegen.
 */
public class ParamForSearchConverter {

  public static void fromJson(JsonObject json, ParamForSearch obj) {
    if (json.getValue("areaCode") instanceof String) {
      obj.setAreaCode((String)json.getValue("areaCode"));
    }
    if (json.getValue("countryArea") instanceof String) {
      obj.setCountryArea((String)json.getValue("countryArea"));
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
    if (json.getValue("terminalApplication") instanceof String) {
      obj.setTerminalApplication((String)json.getValue("terminalApplication"));
    }
    if (json.getValue("uid") instanceof String) {
      obj.setUid((String)json.getValue("uid"));
    }
  }

  public static void toJson(ParamForSearch obj, JsonObject json) {
    if (obj.getAreaCode() != null) {
      json.put("areaCode", obj.getAreaCode());
    }
    if (obj.getCountryArea() != null) {
      json.put("countryArea", obj.getCountryArea());
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
    if (obj.getTerminalApplication() != null) {
      json.put("terminalApplication", obj.getTerminalApplication());
    }
    if (obj.getUid() != null) {
      json.put("uid", obj.getUid());
    }
  }
}