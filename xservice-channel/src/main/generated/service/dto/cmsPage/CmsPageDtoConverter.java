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
 * Converter for {@link service.dto.cmsPage.CmsPageDto}.
 *
 * NOTE: This class has been automatically generated from the {@link service.dto.cmsPage.CmsPageDto} original class using Vert.x codegen.
 */
public class CmsPageDtoConverter {

  public static void fromJson(JsonObject json, CmsPageDto obj) {
    if (json.getValue("categoryList") instanceof JsonArray) {
      java.util.ArrayList<service.dto.cmsPage.CmsCategoryDto> list = new java.util.ArrayList<>();
      json.getJsonArray("categoryList").forEach( item -> {
        if (item instanceof JsonObject)
          list.add(new service.dto.cmsPage.CmsCategoryDto((JsonObject)item));
      });
      obj.setCategoryList(list);
    }
    if (json.getValue("pageId") instanceof String) {
      obj.setPageId((String)json.getValue("pageId"));
    }
  }

  public static void toJson(CmsPageDto obj, JsonObject json) {
    if (obj.getCategoryList() != null) {
      JsonArray array = new JsonArray();
      obj.getCategoryList().forEach(item -> array.add(item.toJson()));
      json.put("categoryList", array);
    }
    if (obj.getPageId() != null) {
      json.put("pageId", obj.getPageId());
    }
  }
}