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
 * Converter for {@link cache.dto.LinesCache}.
 *
 * NOTE: This class has been automatically generated from the {@link cache.dto.LinesCache} original class using Vert.x codegen.
 */
public class LinesCacheConverter {

  public static void fromJson(JsonObject json, LinesCache obj) {
    if (json.getValue("ctime") instanceof String) {
      obj.setCtime((String)json.getValue("ctime"));
    }
    if (json.getValue("episode") instanceof String) {
      obj.setEpisode((String)json.getValue("episode"));
    }
    if (json.getValue("headImg") instanceof String) {
      obj.setHeadImg((String)json.getValue("headImg"));
    }
    if (json.getValue("id") instanceof String) {
      obj.setId((String)json.getValue("id"));
    }
    if (json.getValue("leids") instanceof JsonArray) {
      java.util.ArrayList<java.lang.Long> list = new java.util.ArrayList<>();
      json.getJsonArray("leids").forEach( item -> {
        if (item instanceof Number)
          list.add(((Number)item).longValue());
      });
      obj.setLeids(list);
    }
    if (json.getValue("lines") instanceof String) {
      obj.setLines((String)json.getValue("lines"));
    }
    if (json.getValue("mid") instanceof Number) {
      obj.setMid(((Number)json.getValue("mid")).longValue());
    }
    if (json.getValue("mtime") instanceof String) {
      obj.setMtime((String)json.getValue("mtime"));
    }
    if (json.getValue("pid") instanceof Number) {
      obj.setPid(((Number)json.getValue("pid")).longValue());
    }
    if (json.getValue("ringCount") instanceof Number) {
      obj.setRingCount(((Number)json.getValue("ringCount")).intValue());
    }
    if (json.getValue("ringName") instanceof String) {
      obj.setRingName((String)json.getValue("ringName"));
    }
    if (json.getValue("roleLeId") instanceof Number) {
      obj.setRoleLeId(((Number)json.getValue("roleLeId")).longValue());
    }
    if (json.getValue("roleName") instanceof String) {
      obj.setRoleName((String)json.getValue("roleName"));
    }
    if (json.getValue("starLeId") instanceof Number) {
      obj.setStarLeId(((Number)json.getValue("starLeId")).longValue());
    }
    if (json.getValue("startPlayTime") instanceof Number) {
      obj.setStartPlayTime(((Number)json.getValue("startPlayTime")).longValue());
    }
    if (json.getValue("type") instanceof Number) {
      obj.setType(((Number)json.getValue("type")).intValue());
    }
    if (json.getValue("videovid") instanceof Number) {
      obj.setVideovid(((Number)json.getValue("videovid")).longValue());
    }
  }

  public static void toJson(LinesCache obj, JsonObject json) {
    if (obj.getCtime() != null) {
      json.put("ctime", obj.getCtime());
    }
    if (obj.getEpisode() != null) {
      json.put("episode", obj.getEpisode());
    }
    if (obj.getHeadImg() != null) {
      json.put("headImg", obj.getHeadImg());
    }
    if (obj.getId() != null) {
      json.put("id", obj.getId());
    }
    if (obj.getLeids() != null) {
      JsonArray array = new JsonArray();
      obj.getLeids().forEach(item -> array.add(item));
      json.put("leids", array);
    }
    if (obj.getLines() != null) {
      json.put("lines", obj.getLines());
    }
    if (obj.getMid() != null) {
      json.put("mid", obj.getMid());
    }
    if (obj.getMtime() != null) {
      json.put("mtime", obj.getMtime());
    }
    if (obj.getPid() != null) {
      json.put("pid", obj.getPid());
    }
    if (obj.getRingCount() != null) {
      json.put("ringCount", obj.getRingCount());
    }
    if (obj.getRingName() != null) {
      json.put("ringName", obj.getRingName());
    }
    if (obj.getRoleLeId() != null) {
      json.put("roleLeId", obj.getRoleLeId());
    }
    if (obj.getRoleName() != null) {
      json.put("roleName", obj.getRoleName());
    }
    if (obj.getStarLeId() != null) {
      json.put("starLeId", obj.getStarLeId());
    }
    if (obj.getStartPlayTime() != null) {
      json.put("startPlayTime", obj.getStartPlayTime());
    }
    if (obj.getType() != null) {
      json.put("type", obj.getType());
    }
    if (obj.getVideovid() != null) {
      json.put("videovid", obj.getVideovid());
    }
  }
}