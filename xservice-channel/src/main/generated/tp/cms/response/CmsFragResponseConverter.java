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

package tp.cms.response;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;

/**
 * Converter for {@link tp.cms.response.CmsFragResponse}.
 *
 * NOTE: This class has been automatically generated from the {@link tp.cms.response.CmsFragResponse} original class using Vert.x codegen.
 */
public class CmsFragResponseConverter {

  public static void fromJson(JsonObject json, CmsFragResponse obj) {
    if (json.getValue("columnEntity") instanceof JsonObject) {
      obj.setColumnEntity(new tp.cms.response.CmsColumnResponse((JsonObject)json.getValue("columnEntity")));
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
    if (json.getValue("contentPicSize") instanceof String) {
      obj.setContentPicSize((String)json.getValue("contentPicSize"));
    }
    if (json.getValue("contentRate") instanceof Number) {
      obj.setContentRate(((Number)json.getValue("contentRate")).intValue());
    }
    if (json.getValue("contentRid") instanceof String) {
      obj.setContentRid((String)json.getValue("contentRid"));
    }
    if (json.getValue("contentSort") instanceof Number) {
      obj.setContentSort(((Number)json.getValue("contentSort")).intValue());
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
    if (json.getValue("contentType") instanceof Number) {
      obj.setContentType(((Number)json.getValue("contentType")).intValue());
    }
    if (json.getValue("contentVideoType") instanceof Number) {
      obj.setContentVideoType(((Number)json.getValue("contentVideoType")).intValue());
    }
    if (json.getValue("fragId") instanceof Number) {
      obj.setFragId(((Number)json.getValue("fragId")).intValue());
    }
    if (json.getValue("fragType") instanceof Number) {
      obj.setFragType(((Number)json.getValue("fragType")).intValue());
    }
    if (json.getValue("id") instanceof Number) {
      obj.setId(((Number)json.getValue("id")).intValue());
    }
    if (json.getValue("isLock") instanceof Number) {
      obj.setIsLock(((Number)json.getValue("isLock")).intValue());
    }
    if (json.getValue("isOrder") instanceof Number) {
      obj.setIsOrder(((Number)json.getValue("isOrder")).intValue());
    }
    if (json.getValue("isPage") instanceof Number) {
      obj.setIsPage(((Number)json.getValue("isPage")).intValue());
    }
    if (json.getValue("moduleSort") instanceof String) {
      obj.setModuleSort((String)json.getValue("moduleSort"));
    }
    if (json.getValue("nameLanguageJson") instanceof JsonObject) {
      java.util.Map<String, java.lang.String> map = new java.util.LinkedHashMap<>();
      json.getJsonObject("nameLanguageJson").forEach(entry -> {
        if (entry.getValue() instanceof String)
          map.put(entry.getKey(), (String)entry.getValue());
      });
      obj.setNameLanguageJson(map);
    }
    if (json.getValue("pid") instanceof Number) {
      obj.setPid(((Number)json.getValue("pid")).intValue());
    }
    if (json.getValue("pushPlatform") instanceof String) {
      obj.setPushPlatform((String)json.getValue("pushPlatform"));
    }
    if (json.getValue("redirectCid") instanceof Number) {
      obj.setRedirectCid(((Number)json.getValue("redirectCid")).intValue());
    }
    if (json.getValue("redirectPageId") instanceof String) {
      obj.setRedirectPageId((String)json.getValue("redirectPageId"));
    }
    if (json.getValue("redirectSubCid") instanceof Number) {
      obj.setRedirectSubCid(((Number)json.getValue("redirectSubCid")).intValue());
    }
    if (json.getValue("redirectSubPageId") instanceof String) {
      obj.setRedirectSubPageId((String)json.getValue("redirectSubPageId"));
    }
    if (json.getValue("redirectSubType") instanceof Number) {
      obj.setRedirectSubType(((Number)json.getValue("redirectSubType")).intValue());
    }
    if (json.getValue("redirectSubUrl") instanceof String) {
      obj.setRedirectSubUrl((String)json.getValue("redirectSubUrl"));
    }
    if (json.getValue("redirectSubVideoType") instanceof Number) {
      obj.setRedirectSubVideoType(((Number)json.getValue("redirectSubVideoType")).intValue());
    }
    if (json.getValue("redirectType") instanceof Number) {
      obj.setRedirectType(((Number)json.getValue("redirectType")).intValue());
    }
    if (json.getValue("redirectUrl") instanceof String) {
      obj.setRedirectUrl((String)json.getValue("redirectUrl"));
    }
    if (json.getValue("redirectVideoType") instanceof Number) {
      obj.setRedirectVideoType(((Number)json.getValue("redirectVideoType")).intValue());
    }
    if (json.getValue("site") instanceof Number) {
      obj.setSite(((Number)json.getValue("site")).intValue());
    }
    if (json.getValue("subFrags") instanceof JsonArray) {
      java.util.ArrayList<tp.cms.response.CmsFragResponse> list = new java.util.ArrayList<>();
      json.getJsonArray("subFrags").forEach( item -> {
        if (item instanceof JsonObject)
          list.add(new tp.cms.response.CmsFragResponse((JsonObject)item));
      });
      obj.setSubFrags(list);
    }
    if (json.getValue("subNameLanguageJson") instanceof JsonObject) {
      java.util.Map<String, java.lang.String> map = new java.util.LinkedHashMap<>();
      json.getJsonObject("subNameLanguageJson").forEach(entry -> {
        if (entry.getValue() instanceof String)
          map.put(entry.getKey(), (String)entry.getValue());
      });
      obj.setSubNameLanguageJson(map);
    }
  }

  public static void toJson(CmsFragResponse obj, JsonObject json) {
    if (obj.getColumnEntity() != null) {
      json.put("columnEntity", obj.getColumnEntity().toJson());
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
    if (obj.getContentPicSize() != null) {
      json.put("contentPicSize", obj.getContentPicSize());
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
    if (obj.getFragId() != null) {
      json.put("fragId", obj.getFragId());
    }
    if (obj.getFragType() != null) {
      json.put("fragType", obj.getFragType());
    }
    if (obj.getId() != null) {
      json.put("id", obj.getId());
    }
    if (obj.getIsLock() != null) {
      json.put("isLock", obj.getIsLock());
    }
    if (obj.getIsOrder() != null) {
      json.put("isOrder", obj.getIsOrder());
    }
    if (obj.getIsPage() != null) {
      json.put("isPage", obj.getIsPage());
    }
    if (obj.getModuleSort() != null) {
      json.put("moduleSort", obj.getModuleSort());
    }
    if (obj.getNameLanguageJson() != null) {
      JsonObject map = new JsonObject();
      obj.getNameLanguageJson().forEach((key,value) -> map.put(key, value));
      json.put("nameLanguageJson", map);
    }
    if (obj.getPid() != null) {
      json.put("pid", obj.getPid());
    }
    if (obj.getPushPlatform() != null) {
      json.put("pushPlatform", obj.getPushPlatform());
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
    if (obj.getSite() != null) {
      json.put("site", obj.getSite());
    }
    if (obj.getSubFrags() != null) {
      JsonArray array = new JsonArray();
      obj.getSubFrags().forEach(item -> array.add(item.toJson()));
      json.put("subFrags", array);
    }
    if (obj.getSubNameLanguageJson() != null) {
      JsonObject map = new JsonObject();
      obj.getSubNameLanguageJson().forEach((key,value) -> map.put(key, value));
      json.put("subNameLanguageJson", map);
    }
  }
}