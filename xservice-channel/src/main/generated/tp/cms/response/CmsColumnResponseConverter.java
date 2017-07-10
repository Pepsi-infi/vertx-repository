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
 * Converter for {@link tp.cms.response.CmsColumnResponse}.
 *
 * NOTE: This class has been automatically generated from the {@link tp.cms.response.CmsColumnResponse} original class using Vert.x codegen.
 */
public class CmsColumnResponseConverter {

  public static void fromJson(JsonObject json, CmsColumnResponse obj) {
    if (json.getValue("area") instanceof String) {
      obj.setArea((String)json.getValue("area"));
    }
    if (json.getValue("columnColor") instanceof String) {
      obj.setColumnColor((String)json.getValue("columnColor"));
    }
    if (json.getValue("columnContent") instanceof JsonArray) {
      java.util.ArrayList<tp.cms.response.CmsColumnContentResponse> list = new java.util.ArrayList<>();
      json.getJsonArray("columnContent").forEach( item -> {
        if (item instanceof JsonObject)
          list.add(new tp.cms.response.CmsColumnContentResponse((JsonObject)item));
      });
      obj.setColumnContent(list);
    }
    if (json.getValue("columnName") instanceof String) {
      obj.setColumnName((String)json.getValue("columnName"));
    }
    if (json.getValue("columnType") instanceof Number) {
      obj.setColumnType(((Number)json.getValue("columnType")).intValue());
    }
    if (json.getValue("fontColor") instanceof String) {
      obj.setFontColor((String)json.getValue("fontColor"));
    }
    if (json.getValue("fontSize") instanceof String) {
      obj.setFontSize((String)json.getValue("fontSize"));
    }
    if (json.getValue("hasDataSearchAPI") instanceof Number) {
      obj.setHasDataSearchAPI(((Number)json.getValue("hasDataSearchAPI")).intValue());
    }
    if (json.getValue("icon") instanceof String) {
      obj.setIcon((String)json.getValue("icon"));
    }
    if (json.getValue("icon2") instanceof String) {
      obj.setIcon2((String)json.getValue("icon2"));
    }
    if (json.getValue("id") instanceof Number) {
      obj.setId(((Number)json.getValue("id")).intValue());
    }
    if (json.getValue("orderr") instanceof String) {
      obj.setOrderr((String)json.getValue("orderr"));
    }
    if (json.getValue("pid") instanceof Number) {
      obj.setPid(((Number)json.getValue("pid")).intValue());
    }
    if (json.getValue("product") instanceof Number) {
      obj.setProduct(((Number)json.getValue("product")).intValue());
    }
    if (json.getValue("searchUrl") instanceof String) {
      obj.setSearchUrl((String)json.getValue("searchUrl"));
    }
    if (json.getValue("site") instanceof Number) {
      obj.setSite(((Number)json.getValue("site")).intValue());
    }
    if (json.getValue("status") instanceof Number) {
      obj.setStatus(((Number)json.getValue("status")).intValue());
    }
  }

  public static void toJson(CmsColumnResponse obj, JsonObject json) {
    if (obj.getColumnColor() != null) {
      json.put("columnColor", obj.getColumnColor());
    }
    if (obj.getColumnContent() != null) {
      JsonArray array = new JsonArray();
      obj.getColumnContent().forEach(item -> array.add(item.toJson()));
      json.put("columnContent", array);
    }
    if (obj.getColumnName() != null) {
      json.put("columnName", obj.getColumnName());
    }
    if (obj.getColumnType() != null) {
      json.put("columnType", obj.getColumnType());
    }
    if (obj.getFontColor() != null) {
      json.put("fontColor", obj.getFontColor());
    }
    if (obj.getFontSize() != null) {
      json.put("fontSize", obj.getFontSize());
    }
    if (obj.getHasDataSearchAPI() != null) {
      json.put("hasDataSearchAPI", obj.getHasDataSearchAPI());
    }
    if (obj.getIcon() != null) {
      json.put("icon", obj.getIcon());
    }
    if (obj.getIcon2() != null) {
      json.put("icon2", obj.getIcon2());
    }
    if (obj.getId() != null) {
      json.put("id", obj.getId());
    }
    if (obj.getOrderr() != null) {
      json.put("orderr", obj.getOrderr());
    }
    if (obj.getPid() != null) {
      json.put("pid", obj.getPid());
    }
    if (obj.getProduct() != null) {
      json.put("product", obj.getProduct());
    }
    if (obj.getSearchUrl() != null) {
      json.put("searchUrl", obj.getSearchUrl());
    }
    if (obj.getSite() != null) {
      json.put("site", obj.getSite());
    }
    if (obj.getStatus() != null) {
      json.put("status", obj.getStatus());
    }
  }
}