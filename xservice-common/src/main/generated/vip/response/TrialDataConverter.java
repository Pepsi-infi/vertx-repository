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

package vip.response;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;

/**
 * Converter for {@link vip.response.TrialData}.
 *
 * NOTE: This class has been automatically generated from the {@link vip.response.TrialData} original class using Vert.x codegen.
 */
public class TrialDataConverter {

  public static void fromJson(JsonObject json, TrialData obj) {
    if (json.getValue("trialDuration") instanceof Number) {
      obj.setTrialDuration(((Number)json.getValue("trialDuration")).intValue());
    }
    if (json.getValue("trialDurationName") instanceof String) {
      obj.setTrialDurationName((String)json.getValue("trialDurationName"));
    }
    if (json.getValue("trialField") instanceof String) {
      obj.setTrialField((String)json.getValue("trialField"));
    }
  }

  public static void toJson(TrialData obj, JsonObject json) {
    if (obj.getTrialDuration() != null) {
      json.put("trialDuration", obj.getTrialDuration());
    }
    if (obj.getTrialDurationName() != null) {
      json.put("trialDurationName", obj.getTrialDurationName());
    }
    if (obj.getTrialField() != null) {
      json.put("trialField", obj.getTrialField());
    }
  }
}