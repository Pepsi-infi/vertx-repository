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

package cache.dto;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;

/**
 * Converter for {@link cache.dto.AudioCodes}.
 *
 * NOTE: This class has been automatically generated from the {@link cache.dto.AudioCodes} original class using Vert.x codegen.
 */
public class AudioCodesConverter {

  public static void fromJson(JsonObject json, AudioCodes obj) {
    if (json.getValue("status") instanceof String) {
      obj.setStatus((String)json.getValue("status"));
    }
    if (json.getValue("stream") instanceof String) {
      obj.setStream((String)json.getValue("stream"));
    }
  }

  public static void toJson(AudioCodes obj, JsonObject json) {
    if (obj.getStatus() != null) {
      json.put("status", obj.getStatus());
    }
    if (obj.getStream() != null) {
      json.put("stream", obj.getStream());
    }
  }
}