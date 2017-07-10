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

package search.response;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;

/**
 * Converter for {@link search.response.SearchMixResultTp}.
 *
 * NOTE: This class has been automatically generated from the {@link search.response.SearchMixResultTp} original class using Vert.x codegen.
 */
public class SearchMixResultTpConverter {

  public static void fromJson(JsonObject json, SearchMixResultTp obj) {
    if (json.getValue("album_count") instanceof Number) {
      obj.setAlbum_count(((Number)json.getValue("album_count")).intValue());
    }
    if (json.getValue("category_count_list") instanceof JsonArray) {
      java.util.ArrayList<search.response.SearchCategory> list = new java.util.ArrayList<>();
      json.getJsonArray("category_count_list").forEach( item -> {
        if (item instanceof JsonObject)
          list.add(new search.response.SearchCategory((JsonObject)item));
      });
      obj.setCategory_count_list(list);
    }
    if (json.getValue("data_count") instanceof Number) {
      obj.setData_count(((Number)json.getValue("data_count")).intValue());
    }
    if (json.getValue("data_list") instanceof JsonArray) {
      java.util.ArrayList<search.response.SearchMixResult> list = new java.util.ArrayList<>();
      json.getJsonArray("data_list").forEach( item -> {
        if (item instanceof JsonObject)
          list.add(new search.response.SearchMixResult((JsonObject)item));
      });
      obj.setData_list(list);
    }
    if (json.getValue("innerCode") instanceof String) {
      obj.setInnerCode((String)json.getValue("innerCode"));
    }
    if (json.getValue("response_time") instanceof Number) {
      obj.setResponse_time(((Number)json.getValue("response_time")).longValue());
    }
    if (json.getValue("star_count") instanceof Number) {
      obj.setStar_count(((Number)json.getValue("star_count")).intValue());
    }
    if (json.getValue("video_count") instanceof Number) {
      obj.setVideo_count(((Number)json.getValue("video_count")).intValue());
    }
  }

  public static void toJson(SearchMixResultTp obj, JsonObject json) {
    if (obj.getAlbum_count() != null) {
      json.put("album_count", obj.getAlbum_count());
    }
    if (obj.getCategory_count_list() != null) {
      JsonArray array = new JsonArray();
      obj.getCategory_count_list().forEach(item -> array.add(item.toJson()));
      json.put("category_count_list", array);
    }
    if (obj.getData_count() != null) {
      json.put("data_count", obj.getData_count());
    }
    if (obj.getData_list() != null) {
      JsonArray array = new JsonArray();
      obj.getData_list().forEach(item -> array.add(item.toJson()));
      json.put("data_list", array);
    }
    if (obj.getInnerCode() != null) {
      json.put("innerCode", obj.getInnerCode());
    }
    if (obj.getResponse_time() != null) {
      json.put("response_time", obj.getResponse_time());
    }
    if (obj.getStar_count() != null) {
      json.put("star_count", obj.getStar_count());
    }
    if (obj.getVideo_count() != null) {
      json.put("video_count", obj.getVideo_count());
    }
  }
}