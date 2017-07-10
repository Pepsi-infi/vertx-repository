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

package service.dto.theaterpack;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;

/**
 * Converter for {@link service.dto.theaterpack.MaterialInfo}.
 *
 * NOTE: This class has been automatically generated from the {@link service.dto.theaterpack.MaterialInfo} original class using Vert.x codegen.
 */
public class MaterialInfoConverter {

  public static void fromJson(JsonObject json, MaterialInfo obj) {
    if (json.getValue("duration") instanceof Number) {
      obj.setDuration(((Number)json.getValue("duration")).intValue());
    }
    if (json.getValue("height") instanceof Number) {
      obj.setHeight(((Number)json.getValue("height")).intValue());
    }
    if (json.getValue("pic") instanceof String) {
      obj.setPic((String)json.getValue("pic"));
    }
    if (json.getValue("textList") instanceof JsonArray) {
      java.util.ArrayList<java.lang.String> list = new java.util.ArrayList<>();
      json.getJsonArray("textList").forEach( item -> {
        if (item instanceof String)
          list.add((String)item);
      });
      obj.setTextList(list);
    }
    if (json.getValue("width") instanceof Number) {
      obj.setWidth(((Number)json.getValue("width")).intValue());
    }
  }

  public static void toJson(MaterialInfo obj, JsonObject json) {
    if (obj.getDuration() != null) {
      json.put("duration", obj.getDuration());
    }
    if (obj.getHeight() != null) {
      json.put("height", obj.getHeight());
    }
    if (obj.getPic() != null) {
      json.put("pic", obj.getPic());
    }
    if (obj.getTextList() != null) {
      JsonArray array = new JsonArray();
      obj.getTextList().forEach(item -> array.add(item));
      json.put("textList", array);
    }
    if (obj.getWidth() != null) {
      json.put("width", obj.getWidth());
    }
  }
}