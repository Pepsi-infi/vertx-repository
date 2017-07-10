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
 * Converter for {@link tp.rec.response.SubFrag}.
 *
 * NOTE: This class has been automatically generated from the {@link tp.rec.response.SubFrag} original class using Vert.x codegen.
 */
public class SubFragConverter {

  public static void fromJson(JsonObject json, SubFrag obj) {
    if (json.getValue("blockContents") instanceof JsonArray) {
      java.util.ArrayList<tp.rec.response.RecData> list = new java.util.ArrayList<>();
      json.getJsonArray("blockContents").forEach( item -> {
        if (item instanceof JsonObject)
          list.add(new tp.rec.response.RecData((JsonObject)item));
      });
      obj.setBlockContents(list);
    }
    if (json.getValue("contentStyle") instanceof String) {
      obj.setContentStyle((String)json.getValue("contentStyle"));
    }
  }

  public static void toJson(SubFrag obj, JsonObject json) {
    if (obj.getBlockContents() != null) {
      JsonArray array = new JsonArray();
      obj.getBlockContents().forEach(item -> array.add(item.toJson()));
      json.put("blockContents", array);
    }
    if (obj.getContentStyle() != null) {
      json.put("contentStyle", obj.getContentStyle());
    }
  }
}