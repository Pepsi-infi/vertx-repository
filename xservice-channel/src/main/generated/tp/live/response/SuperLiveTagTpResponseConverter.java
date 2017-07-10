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
 * Converter for {@link tp.live.response.SuperLiveTagTpResponse}.
 *
 * NOTE: This class has been automatically generated from the {@link tp.live.response.SuperLiveTagTpResponse} original class using Vert.x codegen.
 */
public class SuperLiveTagTpResponseConverter {

  public static void fromJson(JsonObject json, SuperLiveTagTpResponse obj) {
    if (json.getValue("id") instanceof String) {
      obj.setId((String)json.getValue("id"));
    }
    if (json.getValue("lename") instanceof String) {
      obj.setLename((String)json.getValue("lename"));
    }
    if (json.getValue("name") instanceof String) {
      obj.setName((String)json.getValue("name"));
    }
    if (json.getValue("tagIcon") instanceof String) {
      obj.setTagIcon((String)json.getValue("tagIcon"));
    }
  }

  public static void toJson(SuperLiveTagTpResponse obj, JsonObject json) {
    if (obj.getId() != null) {
      json.put("id", obj.getId());
    }
    if (obj.getLename() != null) {
      json.put("lename", obj.getLename());
    }
    if (obj.getName() != null) {
      json.put("name", obj.getName());
    }
    if (obj.getTagIcon() != null) {
      json.put("tagIcon", obj.getTagIcon());
    }
  }
}