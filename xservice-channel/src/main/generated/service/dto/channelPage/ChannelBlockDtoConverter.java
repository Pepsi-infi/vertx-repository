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

package service.dto.channelPage;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;

/**
 * Converter for {@link service.dto.channelPage.ChannelBlockDto}.
 *
 * NOTE: This class has been automatically generated from the {@link service.dto.channelPage.ChannelBlockDto} original class using Vert.x codegen.
 */
public class ChannelBlockDtoConverter {

  public static void fromJson(JsonObject json, ChannelBlockDto obj) {
    if (json.getValue("blockType") instanceof String) {
      obj.setBlockType((String)json.getValue("blockType"));
    }
    if (json.getValue("blockid") instanceof String) {
      obj.setBlockid((String)json.getValue("blockid"));
    }
    if (json.getValue("cid") instanceof String) {
      obj.setCid((String)json.getValue("cid"));
    }
    if (json.getValue("curPage") instanceof Number) {
      obj.setCurPage(((Number)json.getValue("curPage")).intValue());
    }
    if (json.getValue("dataUrl") instanceof String) {
      obj.setDataUrl((String)json.getValue("dataUrl"));
    }
    if (json.getValue("list") instanceof JsonArray) {
      java.util.ArrayList<service.dto.channelPage.BaseDto> list = new java.util.ArrayList<>();
      json.getJsonArray("list").forEach( item -> {
        if (item instanceof JsonObject)
          list.add(new service.dto.channelPage.BaseDto((JsonObject)item));
      });
      obj.setList(list);
    }
    if (json.getValue("name") instanceof String) {
      obj.setName((String)json.getValue("name"));
    }
    if (json.getValue("nextPage") instanceof Number) {
      obj.setNextPage(((Number)json.getValue("nextPage")).intValue());
    }
    if (json.getValue("recArea") instanceof String) {
      obj.setRecArea((String)json.getValue("recArea"));
    }
    if (json.getValue("recBucket") instanceof String) {
      obj.setRecBucket((String)json.getValue("recBucket"));
    }
    if (json.getValue("recFragId") instanceof String) {
      obj.setRecFragId((String)json.getValue("recFragId"));
    }
    if (json.getValue("recReid") instanceof String) {
      obj.setRecReid((String)json.getValue("recReid"));
    }
    if (json.getValue("recSrcType") instanceof String) {
      obj.setRecSrcType((String)json.getValue("recSrcType"));
    }
    if (json.getValue("rectCName") instanceof String) {
      obj.setRectCName((String)json.getValue("rectCName"));
    }
    if (json.getValue("rectCid") instanceof String) {
      obj.setRectCid((String)json.getValue("rectCid"));
    }
    if (json.getValue("rectPageId") instanceof String) {
      obj.setRectPageId((String)json.getValue("rectPageId"));
    }
    if (json.getValue("rectType") instanceof String) {
      obj.setRectType((String)json.getValue("rectType"));
    }
    if (json.getValue("rectUrl") instanceof String) {
      obj.setRectUrl((String)json.getValue("rectUrl"));
    }
    if (json.getValue("rectVt") instanceof String) {
      obj.setRectVt((String)json.getValue("rectVt"));
    }
    if (json.getValue("style") instanceof String) {
      obj.setStyle((String)json.getValue("style"));
    }
    if (json.getValue("total") instanceof Number) {
      obj.setTotal(((Number)json.getValue("total")).intValue());
    }
  }

  public static void toJson(ChannelBlockDto obj, JsonObject json) {
    if (obj.getBlockType() != null) {
      json.put("blockType", obj.getBlockType());
    }
    if (obj.getBlockid() != null) {
      json.put("blockid", obj.getBlockid());
    }
    if (obj.getCid() != null) {
      json.put("cid", obj.getCid());
    }
    if (obj.getCurPage() != null) {
      json.put("curPage", obj.getCurPage());
    }
    if (obj.getDataUrl() != null) {
      json.put("dataUrl", obj.getDataUrl());
    }
    if (obj.getList() != null) {
      JsonArray array = new JsonArray();
      obj.getList().forEach(item -> array.add(item.toJson()));
      json.put("list", array);
    }
    if (obj.getName() != null) {
      json.put("name", obj.getName());
    }
    if (obj.getNextPage() != null) {
      json.put("nextPage", obj.getNextPage());
    }
    if (obj.getRecArea() != null) {
      json.put("recArea", obj.getRecArea());
    }
    if (obj.getRecBucket() != null) {
      json.put("recBucket", obj.getRecBucket());
    }
    if (obj.getRecFragId() != null) {
      json.put("recFragId", obj.getRecFragId());
    }
    if (obj.getRecReid() != null) {
      json.put("recReid", obj.getRecReid());
    }
    if (obj.getRecSrcType() != null) {
      json.put("recSrcType", obj.getRecSrcType());
    }
    if (obj.getRectCName() != null) {
      json.put("rectCName", obj.getRectCName());
    }
    if (obj.getRectCid() != null) {
      json.put("rectCid", obj.getRectCid());
    }
    if (obj.getRectPageId() != null) {
      json.put("rectPageId", obj.getRectPageId());
    }
    if (obj.getRectType() != null) {
      json.put("rectType", obj.getRectType());
    }
    if (obj.getRectUrl() != null) {
      json.put("rectUrl", obj.getRectUrl());
    }
    if (obj.getRectVt() != null) {
      json.put("rectVt", obj.getRectVt());
    }
    if (obj.getStyle() != null) {
      json.put("style", obj.getStyle());
    }
    if (obj.getTotal() != null) {
      json.put("total", obj.getTotal());
    }
  }
}