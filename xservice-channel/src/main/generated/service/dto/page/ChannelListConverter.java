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

package service.dto.page;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;

/**
 * Converter for {@link service.dto.page.ChannelList}.
 *
 * NOTE: This class has been automatically generated from the {@link service.dto.page.ChannelList} original class using Vert.x codegen.
 */
public class ChannelListConverter {

  public static void fromJson(JsonObject json, ChannelList obj) {
    if (json.getValue("channels") instanceof JsonArray) {
      java.util.ArrayList<service.dto.page.ChannelInfo> list = new java.util.ArrayList<>();
      json.getJsonArray("channels").forEach( item -> {
        if (item instanceof JsonObject)
          list.add(new service.dto.page.ChannelInfo((JsonObject)item));
      });
      obj.setChannels(list);
    }
    if (json.getValue("name") instanceof String) {
      obj.setName((String)json.getValue("name"));
    }
  }

  public static void toJson(ChannelList obj, JsonObject json) {
    if (obj.getChannels() != null) {
      JsonArray array = new JsonArray();
      obj.getChannels().forEach(item -> array.add(item.toJson()));
      json.put("channels", array);
    }
    if (obj.getName() != null) {
      json.put("name", obj.getName());
    }
  }
}