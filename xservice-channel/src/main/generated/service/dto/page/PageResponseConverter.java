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
 * Converter for {@link service.dto.page.PageResponse}.
 *
 * NOTE: This class has been automatically generated from the {@link service.dto.page.PageResponse} original class using Vert.x codegen.
 */
public class PageResponseConverter {

  public static void fromJson(JsonObject json, PageResponse obj) {
    if (json.getValue("currentIndex") instanceof Number) {
      obj.setCurrentIndex(((Number)json.getValue("currentIndex")).intValue());
    }
    if (json.getValue("data") instanceof JsonArray) {
      java.util.ArrayList<service.dto.page.ChannelList> list = new java.util.ArrayList<>();
      json.getJsonArray("data").forEach( item -> {
        if (item instanceof JsonObject)
          list.add(new service.dto.page.ChannelList((JsonObject)item));
      });
      obj.setData(list);
    }
    if (json.getValue("errorCode") instanceof String) {
      obj.setErrorCode((String)json.getValue("errorCode"));
    }
    if (json.getValue("errorMessage") instanceof String) {
      obj.setErrorMessage((String)json.getValue("errorMessage"));
    }
    if (json.getValue("nextIndex") instanceof Number) {
      obj.setNextIndex(((Number)json.getValue("nextIndex")).intValue());
    }
    if (json.getValue("resultStatus") instanceof Number) {
      obj.setResultStatus(((Number)json.getValue("resultStatus")).intValue());
    }
    if (json.getValue("status") instanceof Number) {
      obj.setStatus(((Number)json.getValue("status")).intValue());
    }
    if (json.getValue("totalCount") instanceof Number) {
      obj.setTotalCount(((Number)json.getValue("totalCount")).intValue());
    }
  }

  public static void toJson(PageResponse obj, JsonObject json) {
    if (obj.getCurrentIndex() != null) {
      json.put("currentIndex", obj.getCurrentIndex());
    }
    if (obj.getData() != null) {
      JsonArray array = new JsonArray();
      obj.getData().forEach(item -> array.add(item.toJson()));
      json.put("data", array);
    }
    if (obj.getErrorCode() != null) {
      json.put("errorCode", obj.getErrorCode());
    }
    if (obj.getErrorMessage() != null) {
      json.put("errorMessage", obj.getErrorMessage());
    }
    if (obj.getNextIndex() != null) {
      json.put("nextIndex", obj.getNextIndex());
    }
    if (obj.getResultStatus() != null) {
      json.put("resultStatus", obj.getResultStatus());
    }
    if (obj.getStatus() != null) {
      json.put("status", obj.getStatus());
    }
    if (obj.getTotalCount() != null) {
      json.put("totalCount", obj.getTotalCount());
    }
  }
}