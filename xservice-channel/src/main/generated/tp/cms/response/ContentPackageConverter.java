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
 * Converter for {@link tp.cms.response.ContentPackage}.
 *
 * NOTE: This class has been automatically generated from the {@link tp.cms.response.ContentPackage} original class using Vert.x codegen.
 */
public class ContentPackageConverter {

  public static void fromJson(JsonObject json, ContentPackage obj) {
    if (json.getValue("dataList") instanceof JsonArray) {
      java.util.ArrayList<tp.cms.response.ContentItem> list = new java.util.ArrayList<>();
      json.getJsonArray("dataList").forEach( item -> {
        if (item instanceof JsonObject)
          list.add(new tp.cms.response.ContentItem((JsonObject)item));
      });
      obj.setDataList(list);
    }
    if (json.getValue("id") instanceof String) {
      obj.setId((String)json.getValue("id"));
    }
    if (json.getValue("innerCode") instanceof String) {
      obj.setInnerCode((String)json.getValue("innerCode"));
    }
    if (json.getValue("name") instanceof String) {
      obj.setName((String)json.getValue("name"));
    }
    if (json.getValue("porder") instanceof String) {
      obj.setPorder((String)json.getValue("porder"));
    }
    if (json.getValue("ptype") instanceof String) {
      obj.setPtype((String)json.getValue("ptype"));
    }
  }

  public static void toJson(ContentPackage obj, JsonObject json) {
    if (obj.getDataList() != null) {
      JsonArray array = new JsonArray();
      obj.getDataList().forEach(item -> array.add(item.toJson()));
      json.put("dataList", array);
    }
    if (obj.getId() != null) {
      json.put("id", obj.getId());
    }
    if (obj.getInnerCode() != null) {
      json.put("innerCode", obj.getInnerCode());
    }
    if (obj.getName() != null) {
      json.put("name", obj.getName());
    }
    if (obj.getPorder() != null) {
      json.put("porder", obj.getPorder());
    }
    if (obj.getPtype() != null) {
      json.put("ptype", obj.getPtype());
    }
  }
}