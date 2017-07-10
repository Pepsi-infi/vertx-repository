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

package tp.rec.request;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;

/**
 * Converter for {@link tp.rec.request.RecBaseRequest}.
 *
 * NOTE: This class has been automatically generated from the {@link tp.rec.request.RecBaseRequest} original class using Vert.x codegen.
 */
public class RecBaseRequestConverter {

  public static void fromJson(JsonObject json, RecBaseRequest obj) {
    if (json.getValue("action") instanceof String) {
      obj.setAction((String)json.getValue("action"));
    }
    if (json.getValue("area") instanceof String) {
      obj.setArea((String)json.getValue("area"));
    }
    if (json.getValue("bc") instanceof Number) {
      obj.setBc(((Number)json.getValue("bc")).intValue());
    }
    if (json.getValue("cid") instanceof Number) {
      obj.setCid(((Number)json.getValue("cid")).intValue());
    }
    if (json.getValue("city") instanceof String) {
      obj.setCity((String)json.getValue("city"));
    }
    if (json.getValue("citylevel") instanceof String) {
      obj.setCitylevel((String)json.getValue("citylevel"));
    }
    if (json.getValue("disable_record_exposure") instanceof Number) {
      obj.setDisable_record_exposure(((Number)json.getValue("disable_record_exposure")).intValue());
    }
    if (json.getValue("feedback") instanceof Number) {
      obj.setFeedback(((Number)json.getValue("feedback")).intValue());
    }
    if (json.getValue("history") instanceof String) {
      obj.setHistory((String)json.getValue("history"));
    }
    if (json.getValue("is_rec") instanceof Boolean) {
      obj.setIs_rec((Boolean)json.getValue("is_rec"));
    }
    if (json.getValue("jsonp") instanceof String) {
      obj.setJsonp((String)json.getValue("jsonp"));
    }
    if (json.getValue("lang") instanceof String) {
      obj.setLang((String)json.getValue("lang"));
    }
    if (json.getValue("lc") instanceof String) {
      obj.setLc((String)json.getValue("lc"));
    }
    if (json.getValue("mpt") instanceof String) {
      obj.setMpt((String)json.getValue("mpt"));
    }
    if (json.getValue("num") instanceof Number) {
      obj.setNum(((Number)json.getValue("num")).intValue());
    }
    if (json.getValue("page_num") instanceof Number) {
      obj.setPage_num(((Number)json.getValue("page_num")).intValue());
    }
    if (json.getValue("pageid") instanceof String) {
      obj.setPageid((String)json.getValue("pageid"));
    }
    if (json.getValue("pid") instanceof Number) {
      obj.setPid(((Number)json.getValue("pid")).longValue());
    }
    if (json.getValue("playtime") instanceof Number) {
      obj.setPlaytime(((Number)json.getValue("playtime")).longValue());
    }
    if (json.getValue("pt") instanceof String) {
      obj.setPt((String)json.getValue("pt"));
    }
    if (json.getValue("random") instanceof Number) {
      obj.setRandom(((Number)json.getValue("random")).doubleValue());
    }
    if (json.getValue("region") instanceof String) {
      obj.setRegion((String)json.getValue("region"));
    }
    if (json.getValue("rom_country") instanceof String) {
      obj.setRom_country((String)json.getValue("rom_country"));
    }
    if (json.getValue("serverTerminal") instanceof String) {
      obj.setServerTerminal((String)json.getValue("serverTerminal"));
    }
    if (json.getValue("totaltime") instanceof Number) {
      obj.setTotaltime(((Number)json.getValue("totaltime")).longValue());
    }
    if (json.getValue("type") instanceof String) {
      obj.setType((String)json.getValue("type"));
    }
    if (json.getValue("uid") instanceof String) {
      obj.setUid((String)json.getValue("uid"));
    }
    if (json.getValue("user_country") instanceof String) {
      obj.setUser_country((String)json.getValue("user_country"));
    }
    if (json.getValue("version") instanceof String) {
      obj.setVersion((String)json.getValue("version"));
    }
    if (json.getValue("versiontype") instanceof String) {
      obj.setVersiontype((String)json.getValue("versiontype"));
    }
    if (json.getValue("vid") instanceof Number) {
      obj.setVid(((Number)json.getValue("vid")).longValue());
    }
  }

  public static void toJson(RecBaseRequest obj, JsonObject json) {
    if (obj.getAction() != null) {
      json.put("action", obj.getAction());
    }
    if (obj.getArea() != null) {
      json.put("area", obj.getArea());
    }
    if (obj.getBc() != null) {
      json.put("bc", obj.getBc());
    }
    if (obj.getCid() != null) {
      json.put("cid", obj.getCid());
    }
    if (obj.getCity() != null) {
      json.put("city", obj.getCity());
    }
    if (obj.getCitylevel() != null) {
      json.put("citylevel", obj.getCitylevel());
    }
    if (obj.getDisable_record_exposure() != null) {
      json.put("disable_record_exposure", obj.getDisable_record_exposure());
    }
    if (obj.getFeedback() != null) {
      json.put("feedback", obj.getFeedback());
    }
    if (obj.getHistory() != null) {
      json.put("history", obj.getHistory());
    }
    if (obj.getIs_rec() != null) {
      json.put("is_rec", obj.getIs_rec());
    }
    if (obj.getJsonp() != null) {
      json.put("jsonp", obj.getJsonp());
    }
    if (obj.getLang() != null) {
      json.put("lang", obj.getLang());
    }
    if (obj.getLc() != null) {
      json.put("lc", obj.getLc());
    }
    if (obj.getMpt() != null) {
      json.put("mpt", obj.getMpt());
    }
    if (obj.getNum() != null) {
      json.put("num", obj.getNum());
    }
    if (obj.getPage_num() != null) {
      json.put("page_num", obj.getPage_num());
    }
    if (obj.getPageid() != null) {
      json.put("pageid", obj.getPageid());
    }
    if (obj.getPid() != null) {
      json.put("pid", obj.getPid());
    }
    if (obj.getPlaytime() != null) {
      json.put("playtime", obj.getPlaytime());
    }
    if (obj.getPt() != null) {
      json.put("pt", obj.getPt());
    }
    if (obj.getRandom() != null) {
      json.put("random", obj.getRandom());
    }
    if (obj.getRegion() != null) {
      json.put("region", obj.getRegion());
    }
    if (obj.getRom_country() != null) {
      json.put("rom_country", obj.getRom_country());
    }
    if (obj.getServerTerminal() != null) {
      json.put("serverTerminal", obj.getServerTerminal());
    }
    if (obj.getTotaltime() != null) {
      json.put("totaltime", obj.getTotaltime());
    }
    if (obj.getType() != null) {
      json.put("type", obj.getType());
    }
    if (obj.getUid() != null) {
      json.put("uid", obj.getUid());
    }
    if (obj.getUser_country() != null) {
      json.put("user_country", obj.getUser_country());
    }
    if (obj.getVersion() != null) {
      json.put("version", obj.getVersion());
    }
    if (obj.getVersiontype() != null) {
      json.put("versiontype", obj.getVersiontype());
    }
    if (obj.getVid() != null) {
      json.put("vid", obj.getVid());
    }
  }
}