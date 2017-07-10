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
 * Converter for {@link tp.rec.response.RecBaseResponse}.
 *
 * NOTE: This class has been automatically generated from the {@link tp.rec.response.RecBaseResponse} original class using Vert.x codegen.
 */
public class RecBaseResponseConverter {

  public static void fromJson(JsonObject json, RecBaseResponse obj) {
    if (json.getValue("area") instanceof String) {
      obj.setArea((String)json.getValue("area"));
    }
    if (json.getValue("bucket") instanceof String) {
      obj.setBucket((String)json.getValue("bucket"));
    }
    if (json.getValue("rec") instanceof JsonArray) {
      java.util.ArrayList<tp.rec.response.RecommendDetail> list = new java.util.ArrayList<>();
      json.getJsonArray("rec").forEach( item -> {
        if (item instanceof JsonObject)
          list.add(new tp.rec.response.RecommendDetail((JsonObject)item));
      });
      obj.setRec(list);
    }
    if (json.getValue("reid") instanceof String) {
      obj.setReid((String)json.getValue("reid"));
    }
  }

  public static void toJson(RecBaseResponse obj, JsonObject json) {
    if (obj.getArea() != null) {
      json.put("area", obj.getArea());
    }
    if (obj.getBucket() != null) {
      json.put("bucket", obj.getBucket());
    }
    if (obj.getRec() != null) {
      JsonArray array = new JsonArray();
      obj.getRec().forEach(item -> array.add(item.toJson()));
      json.put("rec", array);
    }
    if (obj.getReid() != null) {
      json.put("reid", obj.getReid());
    }
  }
}