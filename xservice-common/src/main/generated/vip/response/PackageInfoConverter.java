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
 * Converter for {@link vip.response.PackageInfo}.
 *
 * NOTE: This class has been automatically generated from the {@link vip.response.PackageInfo} original class using Vert.x codegen.
 */
public class PackageInfoConverter {

  public static void fromJson(JsonObject json, PackageInfo obj) {
    if (json.getValue("autoRenew") instanceof Number) {
      obj.setAutoRenew(((Number)json.getValue("autoRenew")).intValue());
    }
    if (json.getValue("autoRenewPeriod") instanceof Number) {
      obj.setAutoRenewPeriod(((Number)json.getValue("autoRenewPeriod")).intValue());
    }
    if (json.getValue("country") instanceof Number) {
      obj.setCountry(((Number)json.getValue("country")).intValue());
    }
    if (json.getValue("desc") instanceof String) {
      obj.setDesc((String)json.getValue("desc"));
    }
    if (json.getValue("duration") instanceof Number) {
      obj.setDuration(((Number)json.getValue("duration")).intValue());
    }
    if (json.getValue("durationName") instanceof String) {
      obj.setDurationName((String)json.getValue("durationName"));
    }
    if (json.getValue("durationType") instanceof Number) {
      obj.setDurationType(((Number)json.getValue("durationType")).intValue());
    }
    if (json.getValue("endTime") instanceof Number) {
      obj.setEndTime(((Number)json.getValue("endTime")).longValue());
    }
    if (json.getValue("field") instanceof String) {
      obj.setField((String)json.getValue("field"));
    }
    if (json.getValue("id") instanceof Number) {
      obj.setId(((Number)json.getValue("id")).intValue());
    }
    if (json.getValue("name") instanceof String) {
      obj.setName((String)json.getValue("name"));
    }
    if (json.getValue("originalPrice") instanceof Number) {
      obj.setOriginalPrice(((Number)json.getValue("originalPrice")).doubleValue());
    }
    if (json.getValue("pic") instanceof String) {
      obj.setPic((String)json.getValue("pic"));
    }
    if (json.getValue("pic2") instanceof String) {
      obj.setPic2((String)json.getValue("pic2"));
    }
    if (json.getValue("pic3") instanceof String) {
      obj.setPic3((String)json.getValue("pic3"));
    }
    if (json.getValue("price") instanceof Number) {
      obj.setPrice(((Number)json.getValue("price")).doubleValue());
    }
    if (json.getValue("productId") instanceof Number) {
      obj.setProductId(((Number)json.getValue("productId")).intValue());
    }
    if (json.getValue("status") instanceof Number) {
      obj.setStatus(((Number)json.getValue("status")).intValue());
    }
    if (json.getValue("subscribeStatus") instanceof Number) {
      obj.setSubscribeStatus(((Number)json.getValue("subscribeStatus")).intValue());
    }
    if (json.getValue("terminal") instanceof String) {
      obj.setTerminal((String)json.getValue("terminal"));
    }
    if (json.getValue("typeGroup") instanceof Number) {
      obj.setTypeGroup(((Number)json.getValue("typeGroup")).intValue());
    }
    if (json.getValue("vipDiscountPriceInfo") instanceof JsonArray) {
      java.util.ArrayList<vip.response.DiscountInfo> list = new java.util.ArrayList<>();
      json.getJsonArray("vipDiscountPriceInfo").forEach( item -> {
        if (item instanceof JsonObject)
          list.add(new vip.response.DiscountInfo((JsonObject)item));
      });
      obj.setVipDiscountPriceInfo(list);
    }
    if (json.getValue("weight") instanceof Number) {
      obj.setWeight(((Number)json.getValue("weight")).intValue());
    }
  }

  public static void toJson(PackageInfo obj, JsonObject json) {
    if (obj.getAutoRenew() != null) {
      json.put("autoRenew", obj.getAutoRenew());
    }
    if (obj.getAutoRenewPeriod() != null) {
      json.put("autoRenewPeriod", obj.getAutoRenewPeriod());
    }
    if (obj.getCountry() != null) {
      json.put("country", obj.getCountry());
    }
    if (obj.getDesc() != null) {
      json.put("desc", obj.getDesc());
    }
    if (obj.getDuration() != null) {
      json.put("duration", obj.getDuration());
    }
    if (obj.getDurationName() != null) {
      json.put("durationName", obj.getDurationName());
    }
    if (obj.getDurationType() != null) {
      json.put("durationType", obj.getDurationType());
    }
    if (obj.getEndTime() != null) {
      json.put("endTime", obj.getEndTime());
    }
    if (obj.getField() != null) {
      json.put("field", obj.getField());
    }
    if (obj.getId() != null) {
      json.put("id", obj.getId());
    }
    if (obj.getName() != null) {
      json.put("name", obj.getName());
    }
    if (obj.getOriginalPrice() != null) {
      json.put("originalPrice", obj.getOriginalPrice());
    }
    if (obj.getPic() != null) {
      json.put("pic", obj.getPic());
    }
    if (obj.getPic2() != null) {
      json.put("pic2", obj.getPic2());
    }
    if (obj.getPic3() != null) {
      json.put("pic3", obj.getPic3());
    }
    if (obj.getPrice() != null) {
      json.put("price", obj.getPrice());
    }
    if (obj.getProductId() != null) {
      json.put("productId", obj.getProductId());
    }
    if (obj.getStatus() != null) {
      json.put("status", obj.getStatus());
    }
    if (obj.getSubscribeStatus() != null) {
      json.put("subscribeStatus", obj.getSubscribeStatus());
    }
    if (obj.getTerminal() != null) {
      json.put("terminal", obj.getTerminal());
    }
    if (obj.getTypeGroup() != null) {
      json.put("typeGroup", obj.getTypeGroup());
    }
    if (obj.getVipDiscountPriceInfo() != null) {
      JsonArray array = new JsonArray();
      obj.getVipDiscountPriceInfo().forEach(item -> array.add(item.toJson()));
      json.put("vipDiscountPriceInfo", array);
    }
    if (obj.getWeight() != null) {
      json.put("weight", obj.getWeight());
    }
  }
}