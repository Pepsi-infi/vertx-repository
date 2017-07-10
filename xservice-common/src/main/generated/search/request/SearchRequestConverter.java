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

package search.request;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;

/**
 * Converter for {@link search.request.SearchRequest}.
 *
 * NOTE: This class has been automatically generated from the {@link search.request.SearchRequest} original class using Vert.x codegen.
 */
public class SearchRequestConverter {

  public static void fromJson(JsonObject json, SearchRequest obj) {
    if (json.getValue("area") instanceof String) {
      obj.setArea((String)json.getValue("area"));
    }
    if (json.getValue("cg") instanceof Number) {
      obj.setCg(((Number)json.getValue("cg")).intValue());
    }
    if (json.getValue("city_info") instanceof String) {
      obj.setCity_info((String)json.getValue("city_info"));
    }
    if (json.getValue("client_ip") instanceof String) {
      obj.setClient_ip((String)json.getValue("client_ip"));
    }
    if (json.getValue("coopPlatform") instanceof String) {
      obj.setCoopPlatform((String)json.getValue("coopPlatform"));
    }
    if (json.getValue("displayAppId") instanceof Number) {
      obj.setDisplayAppId(((Number)json.getValue("displayAppId")).intValue());
    }
    if (json.getValue("displayPlatformId") instanceof Number) {
      obj.setDisplayPlatformId(((Number)json.getValue("displayPlatformId")).intValue());
    }
    if (json.getValue("dt") instanceof String) {
      obj.setDt((String)json.getValue("dt"));
    }
    if (json.getValue("dur") instanceof String) {
      obj.setDur((String)json.getValue("dur"));
    }
    if (json.getValue("extraParam") instanceof String) {
      obj.setExtraParam((String)json.getValue("extraParam"));
    }
    if (json.getValue("fitAge") instanceof String) {
      obj.setFitAge((String)json.getValue("fitAge"));
    }
    if (json.getValue("from") instanceof String) {
      obj.setFrom((String)json.getValue("from"));
    }
    if (json.getValue("hl") instanceof String) {
      obj.setHl((String)json.getValue("hl"));
    }
    if (json.getValue("isEnd") instanceof String) {
      obj.setIsEnd((String)json.getValue("isEnd"));
    }
    if (json.getValue("isHomemade") instanceof String) {
      obj.setIsHomemade((String)json.getValue("isHomemade"));
    }
    if (json.getValue("jf") instanceof String) {
      obj.setJf((String)json.getValue("jf"));
    }
    if (json.getValue("lang") instanceof String) {
      obj.setLang((String)json.getValue("lang"));
    }
    if (json.getValue("language") instanceof String) {
      obj.setLanguage((String)json.getValue("language"));
    }
    if (json.getValue("lc") instanceof String) {
      obj.setLc((String)json.getValue("lc"));
    }
    if (json.getValue("mix") instanceof String) {
      obj.setMix((String)json.getValue("mix"));
    }
    if (json.getValue("or") instanceof String) {
      obj.setOr((String)json.getValue("or"));
    }
    if (json.getValue("paramsMap") instanceof JsonObject) {
    }
    if (json.getValue("ph") instanceof String) {
      obj.setPh((String)json.getValue("ph"));
    }
    if (json.getValue("playStreamFeatures") instanceof String) {
      obj.setPlayStreamFeatures((String)json.getValue("playStreamFeatures"));
    }
    if (json.getValue("pn") instanceof Number) {
      obj.setPn(((Number)json.getValue("pn")).intValue());
    }
    if (json.getValue("popStyle") instanceof String) {
      obj.setPopStyle((String)json.getValue("popStyle"));
    }
    if (json.getValue("ps") instanceof Number) {
      obj.setPs(((Number)json.getValue("ps")).intValue());
    }
    if (json.getValue("region") instanceof String) {
      obj.setRegion((String)json.getValue("region"));
    }
    if (json.getValue("releaseYearDecade") instanceof String) {
      obj.setReleaseYearDecade((String)json.getValue("releaseYearDecade"));
    }
    if (json.getValue("repo_type") instanceof Number) {
      obj.setRepo_type(((Number)json.getValue("repo_type")).intValue());
    }
    if (json.getValue("sales_area") instanceof String) {
      obj.setSales_area((String)json.getValue("sales_area"));
    }
    if (json.getValue("sc") instanceof String) {
      obj.setSc((String)json.getValue("sc"));
    }
    if (json.getValue("sf") instanceof String) {
      obj.setSf((String)json.getValue("sf"));
    }
    if (json.getValue("singerType") instanceof String) {
      obj.setSingerType((String)json.getValue("singerType"));
    }
    if (json.getValue("splatid") instanceof String) {
      obj.setSplatid((String)json.getValue("splatid"));
    }
    if (json.getValue("src") instanceof String) {
      obj.setSrc((String)json.getValue("src"));
    }
    if (json.getValue("stt") instanceof String) {
      obj.setStt((String)json.getValue("stt"));
    }
    if (json.getValue("style") instanceof String) {
      obj.setStyle((String)json.getValue("style"));
    }
    if (json.getValue("stype") instanceof String) {
      obj.setStype((String)json.getValue("stype"));
    }
    if (json.getValue("tag") instanceof String) {
      obj.setTag((String)json.getValue("tag"));
    }
    if (json.getValue("token") instanceof String) {
      obj.setToken((String)json.getValue("token"));
    }
    if (json.getValue("tvid") instanceof String) {
      obj.setTvid((String)json.getValue("tvid"));
    }
    if (json.getValue("uid") instanceof String) {
      obj.setUid((String)json.getValue("uid"));
    }
    if (json.getValue("user_setting_country") instanceof String) {
      obj.setUser_setting_country((String)json.getValue("user_setting_country"));
    }
    if (json.getValue("version") instanceof String) {
      obj.setVersion((String)json.getValue("version"));
    }
    if (json.getValue("vipIds") instanceof String) {
      obj.setVipIds((String)json.getValue("vipIds"));
    }
    if (json.getValue("vtp") instanceof String) {
      obj.setVtp((String)json.getValue("vtp"));
    }
    if (json.getValue("wd") instanceof String) {
      obj.setWd((String)json.getValue("wd"));
    }
  }

  public static void toJson(SearchRequest obj, JsonObject json) {
    if (obj.getArea() != null) {
      json.put("area", obj.getArea());
    }
    if (obj.getCg() != null) {
      json.put("cg", obj.getCg());
    }
    if (obj.getCity_info() != null) {
      json.put("city_info", obj.getCity_info());
    }
    if (obj.getClient_ip() != null) {
      json.put("client_ip", obj.getClient_ip());
    }
    if (obj.getCoopPlatform() != null) {
      json.put("coopPlatform", obj.getCoopPlatform());
    }
    if (obj.getDisplayAppId() != null) {
      json.put("displayAppId", obj.getDisplayAppId());
    }
    if (obj.getDisplayPlatformId() != null) {
      json.put("displayPlatformId", obj.getDisplayPlatformId());
    }
    if (obj.getDt() != null) {
      json.put("dt", obj.getDt());
    }
    if (obj.getDur() != null) {
      json.put("dur", obj.getDur());
    }
    if (obj.getExtraParam() != null) {
      json.put("extraParam", obj.getExtraParam());
    }
    if (obj.getFitAge() != null) {
      json.put("fitAge", obj.getFitAge());
    }
    if (obj.getFrom() != null) {
      json.put("from", obj.getFrom());
    }
    if (obj.getHl() != null) {
      json.put("hl", obj.getHl());
    }
    if (obj.getIsEnd() != null) {
      json.put("isEnd", obj.getIsEnd());
    }
    if (obj.getIsHomemade() != null) {
      json.put("isHomemade", obj.getIsHomemade());
    }
    if (obj.getJf() != null) {
      json.put("jf", obj.getJf());
    }
    if (obj.getLang() != null) {
      json.put("lang", obj.getLang());
    }
    if (obj.getLanguage() != null) {
      json.put("language", obj.getLanguage());
    }
    if (obj.getLc() != null) {
      json.put("lc", obj.getLc());
    }
    if (obj.getMix() != null) {
      json.put("mix", obj.getMix());
    }
    if (obj.getOr() != null) {
      json.put("or", obj.getOr());
    }
    if (obj.getParamsMap() != null) {
      JsonObject map = new JsonObject();
      obj.getParamsMap().forEach((key,value) -> map.put(key, value));
      json.put("paramsMap", map);
    }
    if (obj.getPh() != null) {
      json.put("ph", obj.getPh());
    }
    if (obj.getPlayStreamFeatures() != null) {
      json.put("playStreamFeatures", obj.getPlayStreamFeatures());
    }
    if (obj.getPn() != null) {
      json.put("pn", obj.getPn());
    }
    if (obj.getPopStyle() != null) {
      json.put("popStyle", obj.getPopStyle());
    }
    if (obj.getPs() != null) {
      json.put("ps", obj.getPs());
    }
    if (obj.getRegion() != null) {
      json.put("region", obj.getRegion());
    }
    if (obj.getReleaseYearDecade() != null) {
      json.put("releaseYearDecade", obj.getReleaseYearDecade());
    }
    if (obj.getRepo_type() != null) {
      json.put("repo_type", obj.getRepo_type());
    }
    if (obj.getSales_area() != null) {
      json.put("sales_area", obj.getSales_area());
    }
    if (obj.getSc() != null) {
      json.put("sc", obj.getSc());
    }
    if (obj.getSf() != null) {
      json.put("sf", obj.getSf());
    }
    if (obj.getSingerType() != null) {
      json.put("singerType", obj.getSingerType());
    }
    if (obj.getSplatid() != null) {
      json.put("splatid", obj.getSplatid());
    }
    if (obj.getSrc() != null) {
      json.put("src", obj.getSrc());
    }
    if (obj.getStt() != null) {
      json.put("stt", obj.getStt());
    }
    if (obj.getStyle() != null) {
      json.put("style", obj.getStyle());
    }
    if (obj.getStype() != null) {
      json.put("stype", obj.getStype());
    }
    if (obj.getTag() != null) {
      json.put("tag", obj.getTag());
    }
    if (obj.getToken() != null) {
      json.put("token", obj.getToken());
    }
    if (obj.getTvid() != null) {
      json.put("tvid", obj.getTvid());
    }
    if (obj.getUid() != null) {
      json.put("uid", obj.getUid());
    }
    if (obj.getUser_setting_country() != null) {
      json.put("user_setting_country", obj.getUser_setting_country());
    }
    if (obj.getVersion() != null) {
      json.put("version", obj.getVersion());
    }
    if (obj.getVipIds() != null) {
      json.put("vipIds", obj.getVipIds());
    }
    if (obj.getVtp() != null) {
      json.put("vtp", obj.getVtp());
    }
    if (obj.getWd() != null) {
      json.put("wd", obj.getWd());
    }
  }
}