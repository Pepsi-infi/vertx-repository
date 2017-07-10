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
 * Converter for {@link tp.live.response.PlayBillCurrentTpRows}.
 *
 * NOTE: This class has been automatically generated from the {@link tp.live.response.PlayBillCurrentTpRows} original class using Vert.x codegen.
 */
public class PlayBillCurrentTpRowsConverter {

  public static void fromJson(JsonObject json, PlayBillCurrentTpRows obj) {
    if (json.getValue("channelId") instanceof String) {
      obj.setChannelId((String)json.getValue("channelId"));
    }
    if (json.getValue("cur") instanceof JsonObject) {
      obj.setCur(new tp.live.response.ProgramTp((JsonObject)json.getValue("cur")));
    }
    if (json.getValue("errMsg") instanceof String) {
      obj.setErrMsg((String)json.getValue("errMsg"));
    }
    if (json.getValue("next") instanceof JsonObject) {
      obj.setNext(new tp.live.response.ProgramTp((JsonObject)json.getValue("next")));
    }
    if (json.getValue("pre") instanceof JsonObject) {
      obj.setPre(new tp.live.response.ProgramTp((JsonObject)json.getValue("pre")));
    }
  }

  public static void toJson(PlayBillCurrentTpRows obj, JsonObject json) {
    if (obj.getChannelId() != null) {
      json.put("channelId", obj.getChannelId());
    }
    if (obj.getCur() != null) {
      json.put("cur", obj.getCur().toJson());
    }
    if (obj.getErrMsg() != null) {
      json.put("errMsg", obj.getErrMsg());
    }
    if (obj.getNext() != null) {
      json.put("next", obj.getNext().toJson());
    }
    if (obj.getPre() != null) {
      json.put("pre", obj.getPre().toJson());
    }
  }
}