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

package service.dto.cmsPage;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;

/**
 * Converter for {@link service.dto.cmsPage.CmsChannelDto}.
 *
 * NOTE: This class has been automatically generated from the {@link service.dto.cmsPage.CmsChannelDto} original class using Vert.x codegen.
 */
public class CmsChannelDtoConverter {

  public static void fromJson(JsonObject json, CmsChannelDto obj) {
    if (json.getValue("avator") instanceof String) {
      obj.setAvator((String)json.getValue("avator"));
    }
    if (json.getValue("branchType") instanceof String) {
      obj.setBranchType((String)json.getValue("branchType"));
    }
    if (json.getValue("channelBigPic") instanceof String) {
      obj.setChannelBigPic((String)json.getValue("channelBigPic"));
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
    if (json.getValue("channelPic") instanceof String) {
      obj.setChannelPic((String)json.getValue("channelPic"));
    }
    if (json.getValue("columnId") instanceof String) {
      obj.setColumnId((String)json.getValue("columnId"));
    }
    if (json.getValue("corner") instanceof String) {
      obj.setCorner((String)json.getValue("corner"));
    }
    if (json.getValue("cpName") instanceof String) {
      obj.setCpName((String)json.getValue("cpName"));
    }
    if (json.getValue("fav") instanceof String) {
      obj.setFav((String)json.getValue("fav"));
    }
    if (json.getValue("is3D") instanceof String) {
      obj.setIs3D((String)json.getValue("is3D"));
    }
    if (json.getValue("is4K") instanceof String) {
      obj.setIs4K((String)json.getValue("is4K"));
    }
    if (json.getValue("isAnchor") instanceof Number) {
      obj.setIsAnchor(((Number)json.getValue("isAnchor")).intValue());
    }
    if (json.getValue("isArtificialRecommend") instanceof String) {
      obj.setIsArtificialRecommend((String)json.getValue("isArtificialRecommend"));
    }
    if (json.getValue("isDolby") instanceof String) {
      obj.setIsDolby((String)json.getValue("isDolby"));
    }
    if (json.getValue("isDrm") instanceof String) {
      obj.setIsDrm((String)json.getValue("isDrm"));
    }
    if (json.getValue("isLiveFromTV") instanceof String) {
      obj.setIsLiveFromTV((String)json.getValue("isLiveFromTV"));
    }
    if (json.getValue("isPanoramicView") instanceof String) {
      obj.setIsPanoramicView((String)json.getValue("isPanoramicView"));
    }
    if (json.getValue("isPay") instanceof String) {
      obj.setIsPay((String)json.getValue("isPay"));
    }
    if (json.getValue("liveType") instanceof String) {
      obj.setLiveType((String)json.getValue("liveType"));
    }
    if (json.getValue("nickName") instanceof String) {
      obj.setNickName((String)json.getValue("nickName"));
    }
    if (json.getValue("numericKeys") instanceof String) {
      obj.setNumericKeys((String)json.getValue("numericKeys"));
    }
    if (json.getValue("orderNo") instanceof String) {
      obj.setOrderNo((String)json.getValue("orderNo"));
    }
    if (json.getValue("selfCopyRight") instanceof String) {
      obj.setSelfCopyRight((String)json.getValue("selfCopyRight"));
    }
    if (json.getValue("signal") instanceof String) {
      obj.setSignal((String)json.getValue("signal"));
    }
    if (json.getValue("src") instanceof String) {
      obj.setSrc((String)json.getValue("src"));
    }
    if (json.getValue("streamTips") instanceof String) {
      obj.setStreamTips((String)json.getValue("streamTips"));
    }
    if (json.getValue("thirdLiveId") instanceof String) {
      obj.setThirdLiveId((String)json.getValue("thirdLiveId"));
    }
    if (json.getValue("type") instanceof String) {
      obj.setType((String)json.getValue("type"));
    }
    if (json.getValue("waterLogo") instanceof String) {
      obj.setWaterLogo((String)json.getValue("waterLogo"));
    }
    if (json.getValue("webViewType") instanceof String) {
      obj.setWebViewType((String)json.getValue("webViewType"));
    }
    if (json.getValue("weight") instanceof Number) {
      obj.setWeight(((Number)json.getValue("weight")).intValue());
    }
  }

  public static void toJson(CmsChannelDto obj, JsonObject json) {
    if (obj.getAvator() != null) {
      json.put("avator", obj.getAvator());
    }
    if (obj.getBranchType() != null) {
      json.put("branchType", obj.getBranchType());
    }
    if (obj.getChannelBigPic() != null) {
      json.put("channelBigPic", obj.getChannelBigPic());
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
    if (obj.getChannelPic() != null) {
      json.put("channelPic", obj.getChannelPic());
    }
    if (obj.getColumnId() != null) {
      json.put("columnId", obj.getColumnId());
    }
    if (obj.getCorner() != null) {
      json.put("corner", obj.getCorner());
    }
    if (obj.getCpName() != null) {
      json.put("cpName", obj.getCpName());
    }
    if (obj.getFav() != null) {
      json.put("fav", obj.getFav());
    }
    if (obj.getIs3D() != null) {
      json.put("is3D", obj.getIs3D());
    }
    if (obj.getIs4K() != null) {
      json.put("is4K", obj.getIs4K());
    }
    if (obj.getIsAnchor() != null) {
      json.put("isAnchor", obj.getIsAnchor());
    }
    if (obj.getIsArtificialRecommend() != null) {
      json.put("isArtificialRecommend", obj.getIsArtificialRecommend());
    }
    if (obj.getIsDolby() != null) {
      json.put("isDolby", obj.getIsDolby());
    }
    if (obj.getIsDrm() != null) {
      json.put("isDrm", obj.getIsDrm());
    }
    if (obj.getIsLiveFromTV() != null) {
      json.put("isLiveFromTV", obj.getIsLiveFromTV());
    }
    if (obj.getIsPanoramicView() != null) {
      json.put("isPanoramicView", obj.getIsPanoramicView());
    }
    if (obj.getIsPay() != null) {
      json.put("isPay", obj.getIsPay());
    }
    if (obj.getLiveType() != null) {
      json.put("liveType", obj.getLiveType());
    }
    if (obj.getNickName() != null) {
      json.put("nickName", obj.getNickName());
    }
    if (obj.getNumericKeys() != null) {
      json.put("numericKeys", obj.getNumericKeys());
    }
    if (obj.getOrderNo() != null) {
      json.put("orderNo", obj.getOrderNo());
    }
    if (obj.getSelfCopyRight() != null) {
      json.put("selfCopyRight", obj.getSelfCopyRight());
    }
    if (obj.getSignal() != null) {
      json.put("signal", obj.getSignal());
    }
    if (obj.getSrc() != null) {
      json.put("src", obj.getSrc());
    }
    if (obj.getStreamTips() != null) {
      json.put("streamTips", obj.getStreamTips());
    }
    if (obj.getThirdLiveId() != null) {
      json.put("thirdLiveId", obj.getThirdLiveId());
    }
    if (obj.getType() != null) {
      json.put("type", obj.getType());
    }
    if (obj.getWaterLogo() != null) {
      json.put("waterLogo", obj.getWaterLogo());
    }
    if (obj.getWebViewType() != null) {
      json.put("webViewType", obj.getWebViewType());
    }
    if (obj.getWeight() != null) {
      json.put("weight", obj.getWeight());
    }
  }
}