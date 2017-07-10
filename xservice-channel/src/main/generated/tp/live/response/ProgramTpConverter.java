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
 * Converter for {@link tp.live.response.ProgramTp}.
 *
 * NOTE: This class has been automatically generated from the {@link tp.live.response.ProgramTp} original class using Vert.x codegen.
 */
public class ProgramTpConverter {

  public static void fromJson(JsonObject json, ProgramTp obj) {
    if (json.getValue("aid") instanceof String) {
      obj.setAid((String)json.getValue("aid"));
    }
    if (json.getValue("duration") instanceof String) {
      obj.setDuration((String)json.getValue("duration"));
    }
    if (json.getValue("endTime") instanceof String) {
      obj.setEndTime((String)json.getValue("endTime"));
    }
    if (json.getValue("id") instanceof Number) {
      obj.setId(((Number)json.getValue("id")).longValue());
    }
    if (json.getValue("isRecorder") instanceof Number) {
      obj.setIsRecorder(((Number)json.getValue("isRecorder")).intValue());
    }
    if (json.getValue("playTime") instanceof String) {
      obj.setPlayTime((String)json.getValue("playTime"));
    }
    if (json.getValue("programType") instanceof Number) {
      obj.setProgramType(((Number)json.getValue("programType")).intValue());
    }
    if (json.getValue("theaterIco") instanceof JsonObject) {
      obj.setTheaterIco(new tp.live.response.TheaterIcoTp((JsonObject)json.getValue("theaterIco")));
    }
    if (json.getValue("title") instanceof String) {
      obj.setTitle((String)json.getValue("title"));
    }
    if (json.getValue("vid") instanceof String) {
      obj.setVid((String)json.getValue("vid"));
    }
    if (json.getValue("viewPic") instanceof String) {
      obj.setViewPic((String)json.getValue("viewPic"));
    }
  }

  public static void toJson(ProgramTp obj, JsonObject json) {
    if (obj.getAid() != null) {
      json.put("aid", obj.getAid());
    }
    if (obj.getDuration() != null) {
      json.put("duration", obj.getDuration());
    }
    if (obj.getEndTime() != null) {
      json.put("endTime", obj.getEndTime());
    }
    if (obj.getId() != null) {
      json.put("id", obj.getId());
    }
    if (obj.getIsRecorder() != null) {
      json.put("isRecorder", obj.getIsRecorder());
    }
    if (obj.getPlayTime() != null) {
      json.put("playTime", obj.getPlayTime());
    }
    if (obj.getProgramType() != null) {
      json.put("programType", obj.getProgramType());
    }
    if (obj.getTheaterIco() != null) {
      json.put("theaterIco", obj.getTheaterIco().toJson());
    }
    if (obj.getTitle() != null) {
      json.put("title", obj.getTitle());
    }
    if (obj.getVid() != null) {
      json.put("vid", obj.getVid());
    }
    if (obj.getViewPic() != null) {
      json.put("viewPic", obj.getViewPic());
    }
  }
}