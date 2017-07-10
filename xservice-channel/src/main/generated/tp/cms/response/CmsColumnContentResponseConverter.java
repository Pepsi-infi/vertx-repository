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

package tp.cms.response;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;

/**
 * Converter for {@link tp.cms.response.CmsColumnContentResponse}.
 *
 * NOTE: This class has been automatically generated from the {@link tp.cms.response.CmsColumnContentResponse} original class using Vert.x codegen.
 */
public class CmsColumnContentResponseConverter {

  public static void fromJson(JsonObject json, CmsColumnContentResponse obj) {
    if (json.getValue("columnId") instanceof Number) {
      obj.setColumnId(((Number)json.getValue("columnId")).intValue());
    }
    if (json.getValue("corner1") instanceof String) {
      obj.setCorner1((String)json.getValue("corner1"));
    }
    if (json.getValue("dataId") instanceof String) {
      obj.setDataId((String)json.getValue("dataId"));
    }
    if (json.getValue("dataName") instanceof String) {
      obj.setDataName((String)json.getValue("dataName"));
    }
    if (json.getValue("id") instanceof Number) {
      obj.setId(((Number)json.getValue("id")).longValue());
    }
    if (json.getValue("isRecommend") instanceof Number) {
      obj.setIsRecommend(((Number)json.getValue("isRecommend")).intValue());
    }
    if (json.getValue("liveType") instanceof Number) {
      obj.setLiveType(((Number)json.getValue("liveType")).intValue());
    }
    if (json.getValue("orderr") instanceof Number) {
      obj.setOrderr(((Number)json.getValue("orderr")).intValue());
    }
    if (json.getValue("outputType") instanceof String) {
      obj.setOutputType((String)json.getValue("outputType"));
    }
    if (json.getValue("pic") instanceof String) {
      obj.setPic((String)json.getValue("pic"));
    }
    if (json.getValue("skipType") instanceof Number) {
      obj.setSkipType(((Number)json.getValue("skipType")).intValue());
    }
    if (json.getValue("skipValue") instanceof String) {
      obj.setSkipValue((String)json.getValue("skipValue"));
    }
  }

  public static void toJson(CmsColumnContentResponse obj, JsonObject json) {
    if (obj.getColumnId() != null) {
      json.put("columnId", obj.getColumnId());
    }
    if (obj.getCorner1() != null) {
      json.put("corner1", obj.getCorner1());
    }
    if (obj.getDataId() != null) {
      json.put("dataId", obj.getDataId());
    }
    if (obj.getDataName() != null) {
      json.put("dataName", obj.getDataName());
    }
    if (obj.getId() != null) {
      json.put("id", obj.getId());
    }
    if (obj.getIsRecommend() != null) {
      json.put("isRecommend", obj.getIsRecommend());
    }
    if (obj.getLiveType() != null) {
      json.put("liveType", obj.getLiveType());
    }
    if (obj.getOrderr() != null) {
      json.put("orderr", obj.getOrderr());
    }
    if (obj.getOutputType() != null) {
      json.put("outputType", obj.getOutputType());
    }
    if (obj.getPic() != null) {
      json.put("pic", obj.getPic());
    }
    if (obj.getSkipType() != null) {
      json.put("skipType", obj.getSkipType());
    }
    if (obj.getSkipValue() != null) {
      json.put("skipValue", obj.getSkipValue());
    }
  }
}