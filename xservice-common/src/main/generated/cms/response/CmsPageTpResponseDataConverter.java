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
 * Converter for {@link cms.response.CmsPageTpResponseData}.
 *
 * NOTE: This class has been automatically generated from the {@link cms.response.CmsPageTpResponseData} original class using Vert.x codegen.
 */
public class CmsPageTpResponseDataConverter {

  public static void fromJson(JsonObject json, CmsPageTpResponseData obj) {
    if (json.getValue("frags") instanceof JsonArray) {
      java.util.ArrayList<cms.response.CmsPageTpResponseFrag> list = new java.util.ArrayList<>();
      json.getJsonArray("frags").forEach( item -> {
        if (item instanceof JsonObject)
          list.add(new cms.response.CmsPageTpResponseFrag((JsonObject)item));
      });
      obj.setFrags(list);
    }
  }

  public static void toJson(CmsPageTpResponseData obj, JsonObject json) {
    if (obj.getFrags() != null) {
      JsonArray array = new JsonArray();
      obj.getFrags().forEach(item -> array.add(item.toJson()));
      json.put("frags", array);
    }
  }
}