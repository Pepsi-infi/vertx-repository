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

package tp.rec.response;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;

/**
 * Converter for {@link tp.rec.response.RecommendTpResponse}.
 *
 * NOTE: This class has been automatically generated from the {@link tp.rec.response.RecommendTpResponse} original class using Vert.x codegen.
 */
public class RecommendTpResponseConverter {

  public static void fromJson(JsonObject json, RecommendTpResponse obj) {
    if (json.getValue("area") instanceof String) {
      obj.setArea((String)json.getValue("area"));
    }
    if (json.getValue("blockname") instanceof String) {
      obj.setBlockname((String)json.getValue("blockname"));
    }
    if (json.getValue("bucket") instanceof String) {
      obj.setBucket((String)json.getValue("bucket"));
    }
    if (json.getValue("cid") instanceof Number) {
      obj.setCid(((Number)json.getValue("cid")).intValue());
    }
    if (json.getValue("cityLevel") instanceof String) {
      obj.setCityLevel((String)json.getValue("cityLevel"));
    }
    if (json.getValue("cityWhiteList") instanceof String) {
      obj.setCityWhiteList((String)json.getValue("cityWhiteList"));
    }
    if (json.getValue("cms_num") instanceof Number) {
      obj.setCms_num(((Number)json.getValue("cms_num")).intValue());
    }
    if (json.getValue("conFieldDetailList") instanceof String) {
      obj.setConFieldDetailList((String)json.getValue("conFieldDetailList"));
    }
    if (json.getValue("conFieldTypeList") instanceof String) {
      obj.setConFieldTypeList((String)json.getValue("conFieldTypeList"));
    }
    if (json.getValue("contentCid") instanceof Number) {
      obj.setContentCid(((Number)json.getValue("contentCid")).intValue());
    }
    if (json.getValue("contentId") instanceof String) {
      obj.setContentId((String)json.getValue("contentId"));
    }
    if (json.getValue("contentManulNum") instanceof Number) {
      obj.setContentManulNum(((Number)json.getValue("contentManulNum")).intValue());
    }
    if (json.getValue("contentName") instanceof String) {
      obj.setContentName((String)json.getValue("contentName"));
    }
    if (json.getValue("contentRate") instanceof String) {
      obj.setContentRate((String)json.getValue("contentRate"));
    }
    if (json.getValue("contentRid") instanceof String) {
      obj.setContentRid((String)json.getValue("contentRid"));
    }
    if (json.getValue("contentSort") instanceof String) {
      obj.setContentSort((String)json.getValue("contentSort"));
    }
    if (json.getValue("contentStyle") instanceof String) {
      obj.setContentStyle((String)json.getValue("contentStyle"));
    }
    if (json.getValue("contentSubName") instanceof String) {
      obj.setContentSubName((String)json.getValue("contentSubName"));
    }
    if (json.getValue("contentTotal") instanceof Number) {
      obj.setContentTotal(((Number)json.getValue("contentTotal")).intValue());
    }
    if (json.getValue("contentType") instanceof String) {
      obj.setContentType((String)json.getValue("contentType"));
    }
    if (json.getValue("contentVideoType") instanceof String) {
      obj.setContentVideoType((String)json.getValue("contentVideoType"));
    }
    if (json.getValue("fragEndTime") instanceof String) {
      obj.setFragEndTime((String)json.getValue("fragEndTime"));
    }
    if (json.getValue("fragId") instanceof String) {
      obj.setFragId((String)json.getValue("fragId"));
    }
    if (json.getValue("fragStartTime") instanceof String) {
      obj.setFragStartTime((String)json.getValue("fragStartTime"));
    }
    if (json.getValue("id") instanceof String) {
      obj.setId((String)json.getValue("id"));
    }
    if (json.getValue("moduleSort") instanceof String) {
      obj.setModuleSort((String)json.getValue("moduleSort"));
    }
    if (json.getValue("num") instanceof Number) {
      obj.setNum(((Number)json.getValue("num")).intValue());
    }
    if (json.getValue("rec") instanceof JsonArray) {
      java.util.ArrayList<tp.rec.response.RecData> list = new java.util.ArrayList<>();
      json.getJsonArray("rec").forEach( item -> {
        if (item instanceof JsonObject)
          list.add(new tp.rec.response.RecData((JsonObject)item));
      });
      obj.setRec(list);
    }
    if (json.getValue("redFieldDetailList") instanceof String) {
      obj.setRedFieldDetailList((String)json.getValue("redFieldDetailList"));
    }
    if (json.getValue("redFieldTypeList") instanceof String) {
      obj.setRedFieldTypeList((String)json.getValue("redFieldTypeList"));
    }
    if (json.getValue("redSubFieldDetailList") instanceof String) {
      obj.setRedSubFieldDetailList((String)json.getValue("redSubFieldDetailList"));
    }
    if (json.getValue("redSubFieldTypeList") instanceof String) {
      obj.setRedSubFieldTypeList((String)json.getValue("redSubFieldTypeList"));
    }
    if (json.getValue("redirectCid") instanceof String) {
      obj.setRedirectCid((String)json.getValue("redirectCid"));
    }
    if (json.getValue("redirectPageId") instanceof String) {
      obj.setRedirectPageId((String)json.getValue("redirectPageId"));
    }
    if (json.getValue("redirectSubCid") instanceof String) {
      obj.setRedirectSubCid((String)json.getValue("redirectSubCid"));
    }
    if (json.getValue("redirectSubPageId") instanceof String) {
      obj.setRedirectSubPageId((String)json.getValue("redirectSubPageId"));
    }
    if (json.getValue("redirectSubType") instanceof String) {
      obj.setRedirectSubType((String)json.getValue("redirectSubType"));
    }
    if (json.getValue("redirectSubUrl") instanceof String) {
      obj.setRedirectSubUrl((String)json.getValue("redirectSubUrl"));
    }
    if (json.getValue("redirectSubVideoType") instanceof String) {
      obj.setRedirectSubVideoType((String)json.getValue("redirectSubVideoType"));
    }
    if (json.getValue("redirectType") instanceof String) {
      obj.setRedirectType((String)json.getValue("redirectType"));
    }
    if (json.getValue("redirectUrl") instanceof String) {
      obj.setRedirectUrl((String)json.getValue("redirectUrl"));
    }
    if (json.getValue("redirectVideoType") instanceof String) {
      obj.setRedirectVideoType((String)json.getValue("redirectVideoType"));
    }
    if (json.getValue("reid") instanceof String) {
      obj.setReid((String)json.getValue("reid"));
    }
    if (json.getValue("subFrags") instanceof JsonArray) {
      java.util.ArrayList<tp.rec.response.SubFrag> list = new java.util.ArrayList<>();
      json.getJsonArray("subFrags").forEach( item -> {
        if (item instanceof JsonObject)
          list.add(new tp.rec.response.SubFrag((JsonObject)item));
      });
      obj.setSubFrags(list);
    }
    if (json.getValue("type") instanceof String) {
      obj.setType((String)json.getValue("type"));
    }
    if (json.getValue("typeid") instanceof String) {
      obj.setTypeid((String)json.getValue("typeid"));
    }
  }

  public static void toJson(RecommendTpResponse obj, JsonObject json) {
    if (obj.getArea() != null) {
      json.put("area", obj.getArea());
    }
    if (obj.getBlockname() != null) {
      json.put("blockname", obj.getBlockname());
    }
    if (obj.getBucket() != null) {
      json.put("bucket", obj.getBucket());
    }
    if (obj.getCid() != null) {
      json.put("cid", obj.getCid());
    }
    if (obj.getCityLevel() != null) {
      json.put("cityLevel", obj.getCityLevel());
    }
    if (obj.getCityWhiteList() != null) {
      json.put("cityWhiteList", obj.getCityWhiteList());
    }
    if (obj.getCms_num() != null) {
      json.put("cms_num", obj.getCms_num());
    }
    if (obj.getConFieldDetailList() != null) {
      json.put("conFieldDetailList", obj.getConFieldDetailList());
    }
    if (obj.getConFieldTypeList() != null) {
      json.put("conFieldTypeList", obj.getConFieldTypeList());
    }
    if (obj.getContentCid() != null) {
      json.put("contentCid", obj.getContentCid());
    }
    if (obj.getContentId() != null) {
      json.put("contentId", obj.getContentId());
    }
    if (obj.getContentManulNum() != null) {
      json.put("contentManulNum", obj.getContentManulNum());
    }
    if (obj.getContentName() != null) {
      json.put("contentName", obj.getContentName());
    }
    if (obj.getContentRate() != null) {
      json.put("contentRate", obj.getContentRate());
    }
    if (obj.getContentRid() != null) {
      json.put("contentRid", obj.getContentRid());
    }
    if (obj.getContentSort() != null) {
      json.put("contentSort", obj.getContentSort());
    }
    if (obj.getContentStyle() != null) {
      json.put("contentStyle", obj.getContentStyle());
    }
    if (obj.getContentSubName() != null) {
      json.put("contentSubName", obj.getContentSubName());
    }
    if (obj.getContentTotal() != null) {
      json.put("contentTotal", obj.getContentTotal());
    }
    if (obj.getContentType() != null) {
      json.put("contentType", obj.getContentType());
    }
    if (obj.getContentVideoType() != null) {
      json.put("contentVideoType", obj.getContentVideoType());
    }
    if (obj.getFragEndTime() != null) {
      json.put("fragEndTime", obj.getFragEndTime());
    }
    if (obj.getFragId() != null) {
      json.put("fragId", obj.getFragId());
    }
    if (obj.getFragStartTime() != null) {
      json.put("fragStartTime", obj.getFragStartTime());
    }
    if (obj.getId() != null) {
      json.put("id", obj.getId());
    }
    if (obj.getModuleSort() != null) {
      json.put("moduleSort", obj.getModuleSort());
    }
    if (obj.getNum() != null) {
      json.put("num", obj.getNum());
    }
    if (obj.getRec() != null) {
      JsonArray array = new JsonArray();
      obj.getRec().forEach(item -> array.add(item.toJson()));
      json.put("rec", array);
    }
    if (obj.getRedFieldDetailList() != null) {
      json.put("redFieldDetailList", obj.getRedFieldDetailList());
    }
    if (obj.getRedFieldTypeList() != null) {
      json.put("redFieldTypeList", obj.getRedFieldTypeList());
    }
    if (obj.getRedSubFieldDetailList() != null) {
      json.put("redSubFieldDetailList", obj.getRedSubFieldDetailList());
    }
    if (obj.getRedSubFieldTypeList() != null) {
      json.put("redSubFieldTypeList", obj.getRedSubFieldTypeList());
    }
    if (obj.getRedirectCid() != null) {
      json.put("redirectCid", obj.getRedirectCid());
    }
    if (obj.getRedirectPageId() != null) {
      json.put("redirectPageId", obj.getRedirectPageId());
    }
    if (obj.getRedirectSubCid() != null) {
      json.put("redirectSubCid", obj.getRedirectSubCid());
    }
    if (obj.getRedirectSubPageId() != null) {
      json.put("redirectSubPageId", obj.getRedirectSubPageId());
    }
    if (obj.getRedirectSubType() != null) {
      json.put("redirectSubType", obj.getRedirectSubType());
    }
    if (obj.getRedirectSubUrl() != null) {
      json.put("redirectSubUrl", obj.getRedirectSubUrl());
    }
    if (obj.getRedirectSubVideoType() != null) {
      json.put("redirectSubVideoType", obj.getRedirectSubVideoType());
    }
    if (obj.getRedirectType() != null) {
      json.put("redirectType", obj.getRedirectType());
    }
    if (obj.getRedirectUrl() != null) {
      json.put("redirectUrl", obj.getRedirectUrl());
    }
    if (obj.getRedirectVideoType() != null) {
      json.put("redirectVideoType", obj.getRedirectVideoType());
    }
    if (obj.getReid() != null) {
      json.put("reid", obj.getReid());
    }
    if (obj.getSubFrags() != null) {
      JsonArray array = new JsonArray();
      obj.getSubFrags().forEach(item -> array.add(item.toJson()));
      json.put("subFrags", array);
    }
    if (obj.getType() != null) {
      json.put("type", obj.getType());
    }
    if (obj.getTypeid() != null) {
      json.put("typeid", obj.getTypeid());
    }
  }
}