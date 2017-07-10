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
 * Converter for {@link service.dto.pageCategory.Channel}.
 *
 * NOTE: This class has been automatically generated from the {@link service.dto.pageCategory.Channel} original class using Vert.x codegen.
 */
public class ChannelConverter {

  public static void fromJson(JsonObject json, Channel obj) {
    if (json.getValue("albumId") instanceof Number) {
      obj.setAlbumId(((Number)json.getValue("albumId")).intValue());
    }
    if (json.getValue("categoryId") instanceof Number) {
      obj.setCategoryId(((Number)json.getValue("categoryId")).intValue());
    }
    if (json.getValue("channelCode") instanceof String) {
      obj.setChannelCode((String)json.getValue("channelCode"));
    }
    if (json.getValue("channelId") instanceof Number) {
      obj.setChannelId(((Number)json.getValue("channelId")).intValue());
    }
    if (json.getValue("channelName") instanceof String) {
      obj.setChannelName((String)json.getValue("channelName"));
    }
    if (json.getValue("configInfo") instanceof String) {
      obj.setConfigInfo((String)json.getValue("configInfo"));
    }
    if (json.getValue("cpCategoryId") instanceof String) {
      obj.setCpCategoryId((String)json.getValue("cpCategoryId"));
    }
    if (json.getValue("cpId") instanceof String) {
      obj.setCpId((String)json.getValue("cpId"));
    }
    if (json.getValue("dataType") instanceof Number) {
      obj.setDataType(((Number)json.getValue("dataType")).intValue());
    }
    if (json.getValue("dataUrl") instanceof String) {
      obj.setDataUrl((String)json.getValue("dataUrl"));
    }
    if (json.getValue("defaultStream") instanceof String) {
      obj.setDefaultStream((String)json.getValue("defaultStream"));
    }
    if (json.getValue("defaultStreamName") instanceof String) {
      obj.setDefaultStreamName((String)json.getValue("defaultStreamName"));
    }
    if (json.getValue("globalId") instanceof String) {
      obj.setGlobalId((String)json.getValue("globalId"));
    }
    if (json.getValue("iconType") instanceof String) {
      obj.setIconType((String)json.getValue("iconType"));
    }
    if (json.getValue("img") instanceof String) {
      obj.setImg((String)json.getValue("img"));
    }
    if (json.getValue("isSelected") instanceof Number) {
      obj.setIsSelected(((Number)json.getValue("isSelected")).intValue());
    }
    if (json.getValue("jump") instanceof JsonObject) {
      obj.setJump(new service.dto.pageCategory.JumpData((JsonObject)json.getValue("jump")));
    }
    if (json.getValue("name") instanceof String) {
      obj.setName((String)json.getValue("name"));
    }
    if (json.getValue("pageId") instanceof Number) {
      obj.setPageId(((Number)json.getValue("pageId")).intValue());
    }
    if (json.getValue("parentChannelId") instanceof Number) {
      obj.setParentChannelId(((Number)json.getValue("parentChannelId")).intValue());
    }
    if (json.getValue("pic1") instanceof String) {
      obj.setPic1((String)json.getValue("pic1"));
    }
    if (json.getValue("pic2") instanceof String) {
      obj.setPic2((String)json.getValue("pic2"));
    }
    if (json.getValue("pid") instanceof Number) {
      obj.setPid(((Number)json.getValue("pid")).intValue());
    }
    if (json.getValue("productId") instanceof Number) {
      obj.setProductId(((Number)json.getValue("productId")).intValue());
    }
    if (json.getValue("productName") instanceof String) {
      obj.setProductName((String)json.getValue("productName"));
    }
    if (json.getValue("subName") instanceof String) {
      obj.setSubName((String)json.getValue("subName"));
    }
    if (json.getValue("subType") instanceof Number) {
      obj.setSubType(((Number)json.getValue("subType")).intValue());
    }
    if (json.getValue("titleBgColor") instanceof String) {
      obj.setTitleBgColor((String)json.getValue("titleBgColor"));
    }
    if (json.getValue("titleDataType") instanceof Number) {
      obj.setTitleDataType(((Number)json.getValue("titleDataType")).intValue());
    }
    if (json.getValue("titleFocus1") instanceof String) {
      obj.setTitleFocus1((String)json.getValue("titleFocus1"));
    }
    if (json.getValue("titleFocus2") instanceof String) {
      obj.setTitleFocus2((String)json.getValue("titleFocus2"));
    }
    if (json.getValue("titleIcon") instanceof String) {
      obj.setTitleIcon((String)json.getValue("titleIcon"));
    }
  }

  public static void toJson(Channel obj, JsonObject json) {
    if (obj.getAlbumId() != null) {
      json.put("albumId", obj.getAlbumId());
    }
    if (obj.getCategoryId() != null) {
      json.put("categoryId", obj.getCategoryId());
    }
    if (obj.getChannelCode() != null) {
      json.put("channelCode", obj.getChannelCode());
    }
    if (obj.getChannelId() != null) {
      json.put("channelId", obj.getChannelId());
    }
    if (obj.getChannelName() != null) {
      json.put("channelName", obj.getChannelName());
    }
    if (obj.getConfigInfo() != null) {
      json.put("configInfo", obj.getConfigInfo());
    }
    if (obj.getCpCategoryId() != null) {
      json.put("cpCategoryId", obj.getCpCategoryId());
    }
    if (obj.getCpId() != null) {
      json.put("cpId", obj.getCpId());
    }
    json.put("dataType", obj.getDataType());
    if (obj.getDataUrl() != null) {
      json.put("dataUrl", obj.getDataUrl());
    }
    if (obj.getDefaultStream() != null) {
      json.put("defaultStream", obj.getDefaultStream());
    }
    if (obj.getDefaultStreamName() != null) {
      json.put("defaultStreamName", obj.getDefaultStreamName());
    }
    if (obj.getGlobalId() != null) {
      json.put("globalId", obj.getGlobalId());
    }
    if (obj.getIconType() != null) {
      json.put("iconType", obj.getIconType());
    }
    if (obj.getImg() != null) {
      json.put("img", obj.getImg());
    }
    if (obj.getIsSelected() != null) {
      json.put("isSelected", obj.getIsSelected());
    }
    if (obj.getJump() != null) {
      json.put("jump", obj.getJump().toJson());
    }
    if (obj.getName() != null) {
      json.put("name", obj.getName());
    }
    if (obj.getPageId() != null) {
      json.put("pageId", obj.getPageId());
    }
    if (obj.getParentChannelId() != null) {
      json.put("parentChannelId", obj.getParentChannelId());
    }
    if (obj.getPic1() != null) {
      json.put("pic1", obj.getPic1());
    }
    if (obj.getPic2() != null) {
      json.put("pic2", obj.getPic2());
    }
    if (obj.getPid() != null) {
      json.put("pid", obj.getPid());
    }
    if (obj.getProductId() != null) {
      json.put("productId", obj.getProductId());
    }
    if (obj.getProductName() != null) {
      json.put("productName", obj.getProductName());
    }
    if (obj.getSubName() != null) {
      json.put("subName", obj.getSubName());
    }
    if (obj.getSubType() != null) {
      json.put("subType", obj.getSubType());
    }
    if (obj.getTitleBgColor() != null) {
      json.put("titleBgColor", obj.getTitleBgColor());
    }
    if (obj.getTitleDataType() != null) {
      json.put("titleDataType", obj.getTitleDataType());
    }
    if (obj.getTitleFocus1() != null) {
      json.put("titleFocus1", obj.getTitleFocus1());
    }
    if (obj.getTitleFocus2() != null) {
      json.put("titleFocus2", obj.getTitleFocus2());
    }
    if (obj.getTitleIcon() != null) {
      json.put("titleIcon", obj.getTitleIcon());
    }
  }
}