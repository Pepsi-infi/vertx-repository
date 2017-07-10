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

package service.dto.search;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;

/**
 * Converter for {@link service.dto.search.FilterResultDto}.
 *
 * NOTE: This class has been automatically generated from the {@link service.dto.search.FilterResultDto} original class using Vert.x codegen.
 */
public class FilterResultDtoConverter {

  public static void fromJson(JsonObject json, FilterResultDto obj) {
    if (json.getValue("curPage") instanceof Number) {
      obj.setCurPage(((Number)json.getValue("curPage")).intValue());
    }
    if (json.getValue("nextPage") instanceof Number) {
      obj.setNextPage(((Number)json.getValue("nextPage")).intValue());
    }
    if (json.getValue("result") instanceof JsonArray) {
      java.util.ArrayList<service.dto.search.AlbumInfoDto> list = new java.util.ArrayList<>();
      json.getJsonArray("result").forEach( item -> {
        if (item instanceof JsonObject)
          list.add(new service.dto.search.AlbumInfoDto((JsonObject)item));
      });
      obj.setResult(list);
    }
    if (json.getValue("total") instanceof Number) {
      obj.setTotal(((Number)json.getValue("total")).intValue());
    }
  }

  public static void toJson(FilterResultDto obj, JsonObject json) {
    if (obj.getCurPage() != null) {
      json.put("curPage", obj.getCurPage());
    }
    if (obj.getNextPage() != null) {
      json.put("nextPage", obj.getNextPage());
    }
    if (obj.getResult() != null) {
      JsonArray array = new JsonArray();
      obj.getResult().forEach(item -> array.add(item.toJson()));
      json.put("result", array);
    }
    if (obj.getTotal() != null) {
      json.put("total", obj.getTotal());
    }
  }
}