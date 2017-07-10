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

package cms.response;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;

/**
 * Converter for {@link cms.response.CmsBlockTpResponse}.
 *
 * NOTE: This class has been automatically generated from the {@link cms.response.CmsBlockTpResponse} original class using Vert.x codegen.
 */
public class CmsBlockTpResponseConverter {

  public static void fromJson(JsonObject json, CmsBlockTpResponse obj) {
    if (json.getValue("blockContent") instanceof JsonArray) {
      java.util.ArrayList<cms.response.CmsBlockContent> list = new java.util.ArrayList<>();
      json.getJsonArray("blockContent").forEach( item -> {
        if (item instanceof JsonObject)
          list.add(new cms.response.CmsBlockContent((JsonObject)item));
      });
      obj.setBlockContent(list);
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
    if (json.getValue("pid") instanceof String) {
      obj.setPid((String)json.getValue("pid"));
    }
    if (json.getValue("subBlockList") instanceof JsonArray) {
      java.util.ArrayList<java.lang.Integer> list = new java.util.ArrayList<>();
      json.getJsonArray("subBlockList").forEach( item -> {
        if (item instanceof Number)
          list.add(((Number)item).intValue());
      });
      obj.setSubBlockList(list);
    }
    if (json.getValue("subBlocks") instanceof JsonArray) {
      java.util.ArrayList<cms.response.CmsBlockTpResponse> list = new java.util.ArrayList<>();
      json.getJsonArray("subBlocks").forEach( item -> {
        if (item instanceof JsonObject)
          list.add(new cms.response.CmsBlockTpResponse((JsonObject)item));
      });
      obj.setSubBlocks(list);
    }
  }

  public static void toJson(CmsBlockTpResponse obj, JsonObject json) {
    if (obj.getBlockContent() != null) {
      JsonArray array = new JsonArray();
      obj.getBlockContent().forEach(item -> array.add(item.toJson()));
      json.put("blockContent", array);
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
    if (obj.getPid() != null) {
      json.put("pid", obj.getPid());
    }
    if (obj.getSubBlockList() != null) {
      JsonArray array = new JsonArray();
      obj.getSubBlockList().forEach(item -> array.add(item));
      json.put("subBlockList", array);
    }
    if (obj.getSubBlocks() != null) {
      JsonArray array = new JsonArray();
      obj.getSubBlocks().forEach(item -> array.add(item.toJson()));
      json.put("subBlocks", array);
    }
  }
}