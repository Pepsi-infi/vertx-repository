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
 * Converter for {@link tp.cms.response.SubjectContent}.
 *
 * NOTE: This class has been automatically generated from the {@link tp.cms.response.SubjectContent} original class using Vert.x codegen.
 */
public class SubjectContentConverter {

  public static void fromJson(JsonObject json, SubjectContent obj) {
    if (json.getValue("ctime") instanceof String) {
      obj.setCtime((String)json.getValue("ctime"));
    }
    if (json.getValue("description") instanceof String) {
      obj.setDescription((String)json.getValue("description"));
    }
    if (json.getValue("focusMPic") instanceof String) {
      obj.setFocusMPic((String)json.getValue("focusMPic"));
    }
    if (json.getValue("innerCode") instanceof String) {
      obj.setInnerCode((String)json.getValue("innerCode"));
    }
    if (json.getValue("name") instanceof String) {
      obj.setName((String)json.getValue("name"));
    }
    if (json.getValue("packageIds") instanceof JsonArray) {
      java.util.ArrayList<java.lang.String> list = new java.util.ArrayList<>();
      json.getJsonArray("packageIds").forEach( item -> {
        if (item instanceof String)
          list.add((String)item);
      });
      obj.setPackageIds(list);
    }
    if (json.getValue("pic169") instanceof String) {
      obj.setPic169((String)json.getValue("pic169"));
    }
    if (json.getValue("pubName") instanceof String) {
      obj.setPubName((String)json.getValue("pubName"));
    }
    if (json.getValue("tag") instanceof String) {
      obj.setTag((String)json.getValue("tag"));
    }
    if (json.getValue("tjPackages") instanceof JsonArray) {
      java.util.ArrayList<tp.cms.response.ContentPackage> list = new java.util.ArrayList<>();
      json.getJsonArray("tjPackages").forEach( item -> {
        if (item instanceof JsonObject)
          list.add(new tp.cms.response.ContentPackage((JsonObject)item));
      });
      obj.setTjPackages(list);
    }
    if (json.getValue("tvPic") instanceof String) {
      obj.setTvPic((String)json.getValue("tvPic"));
    }
  }

  public static void toJson(SubjectContent obj, JsonObject json) {
    if (obj.getCtime() != null) {
      json.put("ctime", obj.getCtime());
    }
    if (obj.getDescription() != null) {
      json.put("description", obj.getDescription());
    }
    if (obj.getFocusMPic() != null) {
      json.put("focusMPic", obj.getFocusMPic());
    }
    if (obj.getInnerCode() != null) {
      json.put("innerCode", obj.getInnerCode());
    }
    if (obj.getName() != null) {
      json.put("name", obj.getName());
    }
    if (obj.getPackageIds() != null) {
      JsonArray array = new JsonArray();
      obj.getPackageIds().forEach(item -> array.add(item));
      json.put("packageIds", array);
    }
    if (obj.getPic169() != null) {
      json.put("pic169", obj.getPic169());
    }
    if (obj.getPubName() != null) {
      json.put("pubName", obj.getPubName());
    }
    if (obj.getTag() != null) {
      json.put("tag", obj.getTag());
    }
    if (obj.getTjPackages() != null) {
      JsonArray array = new JsonArray();
      obj.getTjPackages().forEach(item -> array.add(item.toJson()));
      json.put("tjPackages", array);
    }
    if (obj.getTvPic() != null) {
      json.put("tvPic", obj.getTvPic());
    }
  }
}