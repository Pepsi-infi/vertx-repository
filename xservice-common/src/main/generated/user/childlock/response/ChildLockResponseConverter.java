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

package user.childlock.response;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;

/**
 * Converter for {@link user.childlock.response.ChildLockResponse}.
 *
 * NOTE: This class has been automatically generated from the {@link user.childlock.response.ChildLockResponse} original class using Vert.x codegen.
 */
public class ChildLockResponseConverter {

  public static void fromJson(JsonObject json, ChildLockResponse obj) {
    if (json.getValue("canPlayCRIds") instanceof JsonArray) {
      java.util.ArrayList<java.lang.String> list = new java.util.ArrayList<>();
      json.getJsonArray("canPlayCRIds").forEach( item -> {
        if (item instanceof String)
          list.add((String)item);
      });
      obj.setCanPlayCRIds(list);
    }
    if (json.getValue("lockToken") instanceof String) {
      obj.setLockToken((String)json.getValue("lockToken"));
    }
    if (json.getValue("status") instanceof Number) {
      obj.setStatus(((Number)json.getValue("status")).intValue());
    }
    if (json.getValue("tokenEffectiveDuration") instanceof Number) {
      obj.setTokenEffectiveDuration(((Number)json.getValue("tokenEffectiveDuration")).longValue());
    }
    if (json.getValue("userId") instanceof String) {
      obj.setUserId((String)json.getValue("userId"));
    }
  }

  public static void toJson(ChildLockResponse obj, JsonObject json) {
    if (obj.getCanPlayCRIds() != null) {
      JsonArray array = new JsonArray();
      obj.getCanPlayCRIds().forEach(item -> array.add(item));
      json.put("canPlayCRIds", array);
    }
    if (obj.getLockToken() != null) {
      json.put("lockToken", obj.getLockToken());
    }
    if (obj.getStatus() != null) {
      json.put("status", obj.getStatus());
    }
    if (obj.getTokenEffectiveDuration() != null) {
      json.put("tokenEffectiveDuration", obj.getTokenEffectiveDuration());
    }
    if (obj.getUserId() != null) {
      json.put("userId", obj.getUserId());
    }
  }
}