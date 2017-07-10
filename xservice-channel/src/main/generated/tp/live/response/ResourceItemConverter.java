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

package tp.live.response;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;

/**
 * Converter for {@link tp.live.response.ResourceItem}.
 *
 * NOTE: This class has been automatically generated from the {@link tp.live.response.ResourceItem} original class using Vert.x codegen.
 */
public class ResourceItemConverter {

  public static void fromJson(JsonObject json, ResourceItem obj) {
    if (json.getValue("docItemList") instanceof JsonArray) {
      java.util.ArrayList<tp.live.response.Doc> list = new java.util.ArrayList<>();
      json.getJsonArray("docItemList").forEach( item -> {
        if (item instanceof JsonObject)
          list.add(new tp.live.response.Doc((JsonObject)item));
      });
      obj.setDocItemList(list);
    }
    if (json.getValue("document") instanceof String) {
      obj.setDocument((String)json.getValue("document"));
    }
    if (json.getValue("duration") instanceof Number) {
      obj.setDuration(((Number)json.getValue("duration")).intValue());
    }
    if (json.getValue("pxHeight") instanceof Number) {
      obj.setPxHeight(((Number)json.getValue("pxHeight")).intValue());
    }
    if (json.getValue("pxWidth") instanceof Number) {
      obj.setPxWidth(((Number)json.getValue("pxWidth")).intValue());
    }
    if (json.getValue("url") instanceof String) {
      obj.setUrl((String)json.getValue("url"));
    }
  }

  public static void toJson(ResourceItem obj, JsonObject json) {
    if (obj.getDocItemList() != null) {
      JsonArray array = new JsonArray();
      obj.getDocItemList().forEach(item -> array.add(item.toJson()));
      json.put("docItemList", array);
    }
    if (obj.getDocument() != null) {
      json.put("document", obj.getDocument());
    }
    if (obj.getDuration() != null) {
      json.put("duration", obj.getDuration());
    }
    if (obj.getPxHeight() != null) {
      json.put("pxHeight", obj.getPxHeight());
    }
    if (obj.getPxWidth() != null) {
      json.put("pxWidth", obj.getPxWidth());
    }
    if (obj.getUrl() != null) {
      json.put("url", obj.getUrl());
    }
  }
}