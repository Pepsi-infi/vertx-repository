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

package service.dto.pageCategory;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;

/**
 * Converter for {@link service.dto.pageCategory.ChannelData}.
 *
 * NOTE: This class has been automatically generated from the {@link service.dto.pageCategory.ChannelData} original class using Vert.x codegen.
 */
public class ChannelDataConverter {

  public static void fromJson(JsonObject json, ChannelData obj) {
    if (json.getValue("advertisementImg") instanceof String) {
      obj.setAdvertisementImg((String)json.getValue("advertisementImg"));
    }
    if (json.getValue("area") instanceof String) {
      obj.setArea((String)json.getValue("area"));
    }
    if (json.getValue("blockType") instanceof String) {
      obj.setBlockType((String)json.getValue("blockType"));
    }
    if (json.getValue("bucket") instanceof String) {
      obj.setBucket((String)json.getValue("bucket"));
    }
    if (json.getValue("channelId") instanceof Number) {
      obj.setChannelId(((Number)json.getValue("channelId")).intValue());
    }
    if (json.getValue("dataList") instanceof JsonArray) {
      java.util.ArrayList<service.dto.pageCategory.Channel> list = new java.util.ArrayList<>();
      json.getJsonArray("dataList").forEach( item -> {
        if (item instanceof JsonObject)
          list.add(new service.dto.pageCategory.Channel((JsonObject)item));
      });
      obj.setDataList(list);
    }
    if (json.getValue("fragId") instanceof String) {
      obj.setFragId((String)json.getValue("fragId"));
    }
    if (json.getValue("gmt") instanceof Number) {
      obj.setGmt(((Number)json.getValue("gmt")).longValue());
    }
    if (json.getValue("img") instanceof String) {
      obj.setImg((String)json.getValue("img"));
    }
    if (json.getValue("reid") instanceof String) {
      obj.setReid((String)json.getValue("reid"));
    }
    if (json.getValue("subscribed") instanceof Number) {
      obj.setSubscribed(((Number)json.getValue("subscribed")).intValue());
    }
    if (json.getValue("title") instanceof String) {
      obj.setTitle((String)json.getValue("title"));
    }
    if (json.getValue("titleAlbumId") instanceof Number) {
      obj.setTitleAlbumId(((Number)json.getValue("titleAlbumId")).intValue());
    }
    if (json.getValue("titleBgColor") instanceof String) {
      obj.setTitleBgColor((String)json.getValue("titleBgColor"));
    }
    if (json.getValue("titleChannelId") instanceof Number) {
      obj.setTitleChannelId(((Number)json.getValue("titleChannelId")).intValue());
    }
    if (json.getValue("titleSearchCondition") instanceof String) {
      obj.setTitleSearchCondition((String)json.getValue("titleSearchCondition"));
    }
    if (json.getValue("uiPlateType") instanceof Number) {
      obj.setUiPlateType(((Number)json.getValue("uiPlateType")).intValue());
    }
  }

  public static void toJson(ChannelData obj, JsonObject json) {
    if (obj.getAdvertisementImg() != null) {
      json.put("advertisementImg", obj.getAdvertisementImg());
    }
    if (obj.getArea() != null) {
      json.put("area", obj.getArea());
    }
    if (obj.getBlockType() != null) {
      json.put("blockType", obj.getBlockType());
    }
    if (obj.getBucket() != null) {
      json.put("bucket", obj.getBucket());
    }
    if (obj.getChannelId() != null) {
      json.put("channelId", obj.getChannelId());
    }
    if (obj.getDataList() != null) {
      JsonArray array = new JsonArray();
      obj.getDataList().forEach(item -> array.add(item.toJson()));
      json.put("dataList", array);
    }
    if (obj.getFragId() != null) {
      json.put("fragId", obj.getFragId());
    }
    if (obj.getGmt() != null) {
      json.put("gmt", obj.getGmt());
    }
    if (obj.getImg() != null) {
      json.put("img", obj.getImg());
    }
    if (obj.getReid() != null) {
      json.put("reid", obj.getReid());
    }
    if (obj.getSubscribed() != null) {
      json.put("subscribed", obj.getSubscribed());
    }
    if (obj.getTitle() != null) {
      json.put("title", obj.getTitle());
    }
    if (obj.getTitleAlbumId() != null) {
      json.put("titleAlbumId", obj.getTitleAlbumId());
    }
    if (obj.getTitleBgColor() != null) {
      json.put("titleBgColor", obj.getTitleBgColor());
    }
    if (obj.getTitleChannelId() != null) {
      json.put("titleChannelId", obj.getTitleChannelId());
    }
    if (obj.getTitleSearchCondition() != null) {
      json.put("titleSearchCondition", obj.getTitleSearchCondition());
    }
    if (obj.getUiPlateType() != null) {
      json.put("uiPlateType", obj.getUiPlateType());
    }
  }
}