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
 * Converter for {@link vip.response.VipInfo}.
 *
 * NOTE: This class has been automatically generated from the {@link vip.response.VipInfo} original class using Vert.x codegen.
 */
public class VipInfoConverter {

  public static void fromJson(JsonObject json, VipInfo obj) {
    if (json.getValue("createTime") instanceof Number) {
      obj.setCreateTime(((Number)json.getValue("createTime")).longValue());
    }
    if (json.getValue("endTime") instanceof Number) {
      obj.setEndTime(((Number)json.getValue("endTime")).longValue());
    }
    if (json.getValue("isSubscribe") instanceof Number) {
      obj.setIsSubscribe(((Number)json.getValue("isSubscribe")).intValue());
    }
    if (json.getValue("name") instanceof String) {
      obj.setName((String)json.getValue("name"));
    }
    if (json.getValue("productId") instanceof Number) {
      obj.setProductId(((Number)json.getValue("productId")).intValue());
    }
    if (json.getValue("tryEndTime") instanceof Number) {
      obj.setTryEndTime(((Number)json.getValue("tryEndTime")).longValue());
    }
    if (json.getValue("typeGroup") instanceof Number) {
      obj.setTypeGroup(((Number)json.getValue("typeGroup")).intValue());
    }
    if (json.getValue("uid") instanceof Number) {
      obj.setUid(((Number)json.getValue("uid")).intValue());
    }
  }

  public static void toJson(VipInfo obj, JsonObject json) {
    if (obj.getCreateTime() != null) {
      json.put("createTime", obj.getCreateTime());
    }
    if (obj.getEndTime() != null) {
      json.put("endTime", obj.getEndTime());
    }
    if (obj.getIsSubscribe() != null) {
      json.put("isSubscribe", obj.getIsSubscribe());
    }
    if (obj.getName() != null) {
      json.put("name", obj.getName());
    }
    if (obj.getProductId() != null) {
      json.put("productId", obj.getProductId());
    }
    if (obj.getTryEndTime() != null) {
      json.put("tryEndTime", obj.getTryEndTime());
    }
    if (obj.getTypeGroup() != null) {
      json.put("typeGroup", obj.getTypeGroup());
    }
    if (obj.getUid() != null) {
      json.put("uid", obj.getUid());
    }
  }
}