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
 * Converter for {@link search.response.SearchCategory}.
 *
 * NOTE: This class has been automatically generated from the {@link search.response.SearchCategory} original class using Vert.x codegen.
 */
public class SearchCategoryConverter {

  public static void fromJson(JsonObject json, SearchCategory obj) {
    if (json.getValue("category") instanceof Number) {
      obj.setCategory(((Number)json.getValue("category")).intValue());
    }
    if (json.getValue("category_name") instanceof String) {
      obj.setCategory_name((String)json.getValue("category_name"));
    }
    if (json.getValue("count") instanceof Number) {
      obj.setCount(((Number)json.getValue("count")).intValue());
    }
    if (json.getValue("dataType") instanceof Number) {
      obj.setDataType(((Number)json.getValue("dataType")).intValue());
    }
    if (json.getValue("innerCode") instanceof String) {
      obj.setInnerCode((String)json.getValue("innerCode"));
    }
  }

  public static void toJson(SearchCategory obj, JsonObject json) {
    if (obj.getCategory() != null) {
      json.put("category", obj.getCategory());
    }
    if (obj.getCategory_name() != null) {
      json.put("category_name", obj.getCategory_name());
    }
    if (obj.getCount() != null) {
      json.put("count", obj.getCount());
    }
    if (obj.getDataType() != null) {
      json.put("dataType", obj.getDataType());
    }
    if (obj.getInnerCode() != null) {
      json.put("innerCode", obj.getInnerCode());
    }
  }
}