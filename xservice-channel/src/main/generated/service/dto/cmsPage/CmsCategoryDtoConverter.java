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

package service.dto.cmsPage;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;

/**
 * Converter for {@link service.dto.cmsPage.CmsCategoryDto}.
 *
 * NOTE: This class has been automatically generated from the {@link service.dto.cmsPage.CmsCategoryDto} original class using Vert.x codegen.
 */
public class CmsCategoryDtoConverter {

  public static void fromJson(JsonObject json, CmsCategoryDto obj) {
    if (json.getValue("categoryId") instanceof String) {
      obj.setCategoryId((String)json.getValue("categoryId"));
    }
    if (json.getValue("categoryName") instanceof String) {
      obj.setCategoryName((String)json.getValue("categoryName"));
    }
    if (json.getValue("channelList") instanceof JsonArray) {
      java.util.ArrayList<service.dto.cmsPage.CmsChannelDto> list = new java.util.ArrayList<>();
      json.getJsonArray("channelList").forEach( item -> {
        if (item instanceof JsonObject)
          list.add(new service.dto.cmsPage.CmsChannelDto((JsonObject)item));
      });
      obj.setChannelList(list);
    }
    if (json.getValue("dataSource") instanceof Number) {
      obj.setDataSource(((Number)json.getValue("dataSource")).intValue());
    }
    if (json.getValue("dataType") instanceof Number) {
      obj.setDataType(((Number)json.getValue("dataType")).intValue());
    }
    if (json.getValue("id") instanceof Number) {
      obj.setId(((Number)json.getValue("id")).intValue());
    }
    if (json.getValue("isPersonalizedSort") instanceof Number) {
      obj.setIsPersonalizedSort(((Number)json.getValue("isPersonalizedSort")).intValue());
    }
  }

  public static void toJson(CmsCategoryDto obj, JsonObject json) {
    if (obj.getCategoryId() != null) {
      json.put("categoryId", obj.getCategoryId());
    }
    if (obj.getCategoryName() != null) {
      json.put("categoryName", obj.getCategoryName());
    }
    if (obj.getChannelList() != null) {
      JsonArray array = new JsonArray();
      obj.getChannelList().forEach(item -> array.add(item.toJson()));
      json.put("channelList", array);
    }
    if (obj.getDataSource() != null) {
      json.put("dataSource", obj.getDataSource());
    }
    if (obj.getDataType() != null) {
      json.put("dataType", obj.getDataType());
    }
    if (obj.getId() != null) {
      json.put("id", obj.getId());
    }
    if (obj.getIsPersonalizedSort() != null) {
      json.put("isPersonalizedSort", obj.getIsPersonalizedSort());
    }
  }
}