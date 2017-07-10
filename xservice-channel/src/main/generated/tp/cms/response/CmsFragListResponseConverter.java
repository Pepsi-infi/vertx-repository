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
 * Converter for {@link tp.cms.response.CmsFragListResponse}.
 *
 * NOTE: This class has been automatically generated from the {@link tp.cms.response.CmsFragListResponse} original class using Vert.x codegen.
 */
public class CmsFragListResponseConverter {

  public static void fromJson(JsonObject json, CmsFragListResponse obj) {
    if (json.getValue("cacheTime") instanceof Number) {
      obj.setCacheTime(((Number)json.getValue("cacheTime")).longValue());
    }
    if (json.getValue("frags") instanceof JsonArray) {
      java.util.ArrayList<tp.cms.response.CmsFragResponse> list = new java.util.ArrayList<>();
      json.getJsonArray("frags").forEach( item -> {
        if (item instanceof JsonObject)
          list.add(new tp.cms.response.CmsFragResponse((JsonObject)item));
      });
      obj.setFrags(list);
    }
    if (json.getValue("pageId") instanceof Number) {
      obj.setPageId(((Number)json.getValue("pageId")).intValue());
    }
  }

  public static void toJson(CmsFragListResponse obj, JsonObject json) {
    json.put("cacheTime", obj.getCacheTime());
    if (obj.getFrags() != null) {
      JsonArray array = new JsonArray();
      obj.getFrags().forEach(item -> array.add(item.toJson()));
      json.put("frags", array);
    }
    if (obj.getPageId() != null) {
      json.put("pageId", obj.getPageId());
    }
  }
}