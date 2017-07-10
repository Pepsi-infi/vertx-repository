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

package service.dto.channelPage;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;

/**
 * Converter for {@link service.dto.channelPage.AddOnInfoDto}.
 *
 * NOTE: This class has been automatically generated from the {@link service.dto.channelPage.AddOnInfoDto} original class using Vert.x codegen.
 */
public class AddOnInfoDtoConverter {

  public static void fromJson(JsonObject json, AddOnInfoDto obj) {
    if (json.getValue("addOnBackPic") instanceof String) {
      obj.setAddOnBackPic((String)json.getValue("addOnBackPic"));
    }
    if (json.getValue("addOnDesc") instanceof String) {
      obj.setAddOnDesc((String)json.getValue("addOnDesc"));
    }
    if (json.getValue("addOnId") instanceof Number) {
      obj.setAddOnId(((Number)json.getValue("addOnId")).intValue());
    }
    if (json.getValue("addOnLogoPic") instanceof String) {
      obj.setAddOnLogoPic((String)json.getValue("addOnLogoPic"));
    }
    if (json.getValue("addOnName") instanceof String) {
      obj.setAddOnName((String)json.getValue("addOnName"));
    }
    if (json.getValue("addOnPrice") instanceof String) {
      obj.setAddOnPrice((String)json.getValue("addOnPrice"));
    }
    if (json.getValue("addOnSubscribeStatus") instanceof Number) {
      obj.setAddOnSubscribeStatus(((Number)json.getValue("addOnSubscribeStatus")).intValue());
    }
    if (json.getValue("addOnTotalContentNum") instanceof Number) {
      obj.setAddOnTotalContentNum(((Number)json.getValue("addOnTotalContentNum")).intValue());
    }
    if (json.getValue("endTime") instanceof Number) {
      obj.setEndTime(((Number)json.getValue("endTime")).longValue());
    }
    if (json.getValue("productId") instanceof Number) {
      obj.setProductId(((Number)json.getValue("productId")).intValue());
    }
  }

  public static void toJson(AddOnInfoDto obj, JsonObject json) {
    if (obj.getAddOnBackPic() != null) {
      json.put("addOnBackPic", obj.getAddOnBackPic());
    }
    if (obj.getAddOnDesc() != null) {
      json.put("addOnDesc", obj.getAddOnDesc());
    }
    if (obj.getAddOnId() != null) {
      json.put("addOnId", obj.getAddOnId());
    }
    if (obj.getAddOnLogoPic() != null) {
      json.put("addOnLogoPic", obj.getAddOnLogoPic());
    }
    if (obj.getAddOnName() != null) {
      json.put("addOnName", obj.getAddOnName());
    }
    if (obj.getAddOnPrice() != null) {
      json.put("addOnPrice", obj.getAddOnPrice());
    }
    if (obj.getAddOnSubscribeStatus() != null) {
      json.put("addOnSubscribeStatus", obj.getAddOnSubscribeStatus());
    }
    if (obj.getAddOnTotalContentNum() != null) {
      json.put("addOnTotalContentNum", obj.getAddOnTotalContentNum());
    }
    if (obj.getEndTime() != null) {
      json.put("endTime", obj.getEndTime());
    }
    if (obj.getProductId() != null) {
      json.put("productId", obj.getProductId());
    }
  }
}