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
 * Converter for {@link tp.live.response.ChannelResponse}.
 *
 * NOTE: This class has been automatically generated from the {@link tp.live.response.ChannelResponse} original class using Vert.x codegen.
 */
public class ChannelResponseConverter {

  public static void fromJson(JsonObject json, ChannelResponse obj) {
    if (json.getValue("beginTime") instanceof String) {
      obj.setBeginTime((String)json.getValue("beginTime"));
    }
    if (json.getValue("belongArea") instanceof String) {
      obj.setBelongArea((String)json.getValue("belongArea"));
    }
    if (json.getValue("belongBrand") instanceof String) {
      obj.setBelongBrand((String)json.getValue("belongBrand"));
    }
    if (json.getValue("buyFlag") instanceof String) {
      obj.setBuyFlag((String)json.getValue("buyFlag"));
    }
    if (json.getValue("ch") instanceof String) {
      obj.setCh((String)json.getValue("ch"));
    }
    if (json.getValue("channelClass") instanceof String) {
      obj.setChannelClass((String)json.getValue("channelClass"));
    }
    if (json.getValue("channelDesc") instanceof String) {
      obj.setChannelDesc((String)json.getValue("channelDesc"));
    }
    if (json.getValue("channelEname") instanceof String) {
      obj.setChannelEname((String)json.getValue("channelEname"));
    }
    if (json.getValue("channelId") instanceof String) {
      obj.setChannelId((String)json.getValue("channelId"));
    }
    if (json.getValue("channelName") instanceof String) {
      obj.setChannelName((String)json.getValue("channelName"));
    }
    if (json.getValue("chatRoomNum") instanceof String) {
      obj.setChatRoomNum((String)json.getValue("chatRoomNum"));
    }
    if (json.getValue("childLock") instanceof String) {
      obj.setChildLock((String)json.getValue("childLock"));
    }
    if (json.getValue("cibnChannelName") instanceof String) {
      obj.setCibnChannelName((String)json.getValue("cibnChannelName"));
    }
    if (json.getValue("cibnWatermarkUrl") instanceof String) {
      obj.setCibnWatermarkUrl((String)json.getValue("cibnWatermarkUrl"));
    }
    if (json.getValue("copyright") instanceof String) {
      obj.setCopyright((String)json.getValue("copyright"));
    }
    if (json.getValue("defaultLogo") instanceof JsonObject) {
      java.util.Map<String, java.lang.String> map = new java.util.LinkedHashMap<>();
      json.getJsonObject("defaultLogo").forEach(entry -> {
        if (entry.getValue() instanceof String)
          map.put(entry.getKey(), (String)entry.getValue());
      });
      obj.setDefaultLogo(map);
    }
    if (json.getValue("demandId") instanceof String) {
      obj.setDemandId((String)json.getValue("demandId"));
    }
    if (json.getValue("drmFlag") instanceof String) {
      obj.setDrmFlag((String)json.getValue("drmFlag"));
    }
    if (json.getValue("endTime") instanceof String) {
      obj.setEndTime((String)json.getValue("endTime"));
    }
    if (json.getValue("is3D") instanceof String) {
      obj.setIs3D((String)json.getValue("is3D"));
    }
    if (json.getValue("is4K") instanceof String) {
      obj.setIs4K((String)json.getValue("is4K"));
    }
    if (json.getValue("isChat") instanceof String) {
      obj.setIsChat((String)json.getValue("isChat"));
    }
    if (json.getValue("isCollect") instanceof String) {
      obj.setIsCollect((String)json.getValue("isCollect"));
    }
    if (json.getValue("isCopyRight") instanceof String) {
      obj.setIsCopyRight((String)json.getValue("isCopyRight"));
    }
    if (json.getValue("isDanmaku") instanceof String) {
      obj.setIsDanmaku((String)json.getValue("isDanmaku"));
    }
    if (json.getValue("isPay") instanceof String) {
      obj.setIsPay((String)json.getValue("isPay"));
    }
    if (json.getValue("isPicCollections") instanceof String) {
      obj.setIsPicCollections((String)json.getValue("isPicCollections"));
    }
    if (json.getValue("isRecommend") instanceof String) {
      obj.setIsRecommend((String)json.getValue("isRecommend"));
    }
    if (json.getValue("numericKeys") instanceof String) {
      obj.setNumericKeys((String)json.getValue("numericKeys"));
    }
    if (json.getValue("orderNo") instanceof String) {
      obj.setOrderNo((String)json.getValue("orderNo"));
    }
    if (json.getValue("partId") instanceof String) {
      obj.setPartId((String)json.getValue("partId"));
    }
    if (json.getValue("pcWatermarkUrl") instanceof String) {
      obj.setPcWatermarkUrl((String)json.getValue("pcWatermarkUrl"));
    }
    if (json.getValue("postH3") instanceof String) {
      obj.setPostH3((String)json.getValue("postH3"));
    }
    if (json.getValue("postOrigin") instanceof String) {
      obj.setPostOrigin((String)json.getValue("postOrigin"));
    }
    if (json.getValue("postS1") instanceof String) {
      obj.setPostS1((String)json.getValue("postS1"));
    }
    if (json.getValue("postS2") instanceof String) {
      obj.setPostS2((String)json.getValue("postS2"));
    }
    if (json.getValue("postS3") instanceof String) {
      obj.setPostS3((String)json.getValue("postS3"));
    }
    if (json.getValue("postS4") instanceof String) {
      obj.setPostS4((String)json.getValue("postS4"));
    }
    if (json.getValue("postS5") instanceof String) {
      obj.setPostS5((String)json.getValue("postS5"));
    }
    if (json.getValue("programSource") instanceof String) {
      obj.setProgramSource((String)json.getValue("programSource"));
    }
    if (json.getValue("relaId") instanceof String) {
      obj.setRelaId((String)json.getValue("relaId"));
    }
    if (json.getValue("satelliteTvType") instanceof String) {
      obj.setSatelliteTvType((String)json.getValue("satelliteTvType"));
    }
    if (json.getValue("signal") instanceof String) {
      obj.setSignal((String)json.getValue("signal"));
    }
    if (json.getValue("sourceId") instanceof String) {
      obj.setSourceId((String)json.getValue("sourceId"));
    }
    if (json.getValue("splatid") instanceof String) {
      obj.setSplatid((String)json.getValue("splatid"));
    }
    if (json.getValue("src_id") instanceof String) {
      obj.setSrc_id((String)json.getValue("src_id"));
    }
    if (json.getValue("streamUrl") instanceof String) {
      obj.setStreamUrl((String)json.getValue("streamUrl"));
    }
    if (json.getValue("subLiveType") instanceof String) {
      obj.setSubLiveType((String)json.getValue("subLiveType"));
    }
    if (json.getValue("subLiveTypeName") instanceof String) {
      obj.setSubLiveTypeName((String)json.getValue("subLiveTypeName"));
    }
    if (json.getValue("watermarkUrl") instanceof String) {
      obj.setWatermarkUrl((String)json.getValue("watermarkUrl"));
    }
  }

  public static void toJson(ChannelResponse obj, JsonObject json) {
    if (obj.getBeginTime() != null) {
      json.put("beginTime", obj.getBeginTime());
    }
    if (obj.getBelongArea() != null) {
      json.put("belongArea", obj.getBelongArea());
    }
    if (obj.getBelongBrand() != null) {
      json.put("belongBrand", obj.getBelongBrand());
    }
    if (obj.getBuyFlag() != null) {
      json.put("buyFlag", obj.getBuyFlag());
    }
    if (obj.getCh() != null) {
      json.put("ch", obj.getCh());
    }
    if (obj.getChannelClass() != null) {
      json.put("channelClass", obj.getChannelClass());
    }
    if (obj.getChannelDesc() != null) {
      json.put("channelDesc", obj.getChannelDesc());
    }
    if (obj.getChannelEname() != null) {
      json.put("channelEname", obj.getChannelEname());
    }
    if (obj.getChannelId() != null) {
      json.put("channelId", obj.getChannelId());
    }
    if (obj.getChannelName() != null) {
      json.put("channelName", obj.getChannelName());
    }
    if (obj.getChatRoomNum() != null) {
      json.put("chatRoomNum", obj.getChatRoomNum());
    }
    if (obj.getChildLock() != null) {
      json.put("childLock", obj.getChildLock());
    }
    if (obj.getCibnChannelName() != null) {
      json.put("cibnChannelName", obj.getCibnChannelName());
    }
    if (obj.getCibnWatermarkUrl() != null) {
      json.put("cibnWatermarkUrl", obj.getCibnWatermarkUrl());
    }
    if (obj.getCopyright() != null) {
      json.put("copyright", obj.getCopyright());
    }
    if (obj.getDefaultLogo() != null) {
      JsonObject map = new JsonObject();
      obj.getDefaultLogo().forEach((key,value) -> map.put(key, value));
      json.put("defaultLogo", map);
    }
    if (obj.getDemandId() != null) {
      json.put("demandId", obj.getDemandId());
    }
    if (obj.getDrmFlag() != null) {
      json.put("drmFlag", obj.getDrmFlag());
    }
    if (obj.getEndTime() != null) {
      json.put("endTime", obj.getEndTime());
    }
    if (obj.getIs3D() != null) {
      json.put("is3D", obj.getIs3D());
    }
    if (obj.getIs4K() != null) {
      json.put("is4K", obj.getIs4K());
    }
    if (obj.getIsChat() != null) {
      json.put("isChat", obj.getIsChat());
    }
    if (obj.getIsCollect() != null) {
      json.put("isCollect", obj.getIsCollect());
    }
    if (obj.getIsCopyRight() != null) {
      json.put("isCopyRight", obj.getIsCopyRight());
    }
    if (obj.getIsDanmaku() != null) {
      json.put("isDanmaku", obj.getIsDanmaku());
    }
    if (obj.getIsPay() != null) {
      json.put("isPay", obj.getIsPay());
    }
    if (obj.getIsPicCollections() != null) {
      json.put("isPicCollections", obj.getIsPicCollections());
    }
    if (obj.getIsRecommend() != null) {
      json.put("isRecommend", obj.getIsRecommend());
    }
    if (obj.getNumericKeys() != null) {
      json.put("numericKeys", obj.getNumericKeys());
    }
    if (obj.getOrderNo() != null) {
      json.put("orderNo", obj.getOrderNo());
    }
    if (obj.getPartId() != null) {
      json.put("partId", obj.getPartId());
    }
    if (obj.getPcWatermarkUrl() != null) {
      json.put("pcWatermarkUrl", obj.getPcWatermarkUrl());
    }
    if (obj.getPostH3() != null) {
      json.put("postH3", obj.getPostH3());
    }
    if (obj.getPostOrigin() != null) {
      json.put("postOrigin", obj.getPostOrigin());
    }
    if (obj.getPostS1() != null) {
      json.put("postS1", obj.getPostS1());
    }
    if (obj.getPostS2() != null) {
      json.put("postS2", obj.getPostS2());
    }
    if (obj.getPostS3() != null) {
      json.put("postS3", obj.getPostS3());
    }
    if (obj.getPostS4() != null) {
      json.put("postS4", obj.getPostS4());
    }
    if (obj.getPostS5() != null) {
      json.put("postS5", obj.getPostS5());
    }
    if (obj.getProgramSource() != null) {
      json.put("programSource", obj.getProgramSource());
    }
    if (obj.getRelaId() != null) {
      json.put("relaId", obj.getRelaId());
    }
    if (obj.getSatelliteTvType() != null) {
      json.put("satelliteTvType", obj.getSatelliteTvType());
    }
    if (obj.getSignal() != null) {
      json.put("signal", obj.getSignal());
    }
    if (obj.getSourceId() != null) {
      json.put("sourceId", obj.getSourceId());
    }
    if (obj.getSplatid() != null) {
      json.put("splatid", obj.getSplatid());
    }
    if (obj.getSrc_id() != null) {
      json.put("src_id", obj.getSrc_id());
    }
    if (obj.getStreamUrl() != null) {
      json.put("streamUrl", obj.getStreamUrl());
    }
    if (obj.getSubLiveType() != null) {
      json.put("subLiveType", obj.getSubLiveType());
    }
    if (obj.getSubLiveTypeName() != null) {
      json.put("subLiveTypeName", obj.getSubLiveTypeName());
    }
    if (obj.getWatermarkUrl() != null) {
      json.put("watermarkUrl", obj.getWatermarkUrl());
    }
  }
}