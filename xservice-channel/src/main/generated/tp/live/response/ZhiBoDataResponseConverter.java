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
 * Converter for {@link tp.live.response.ZhiBoDataResponse}.
 *
 * NOTE: This class has been automatically generated from the {@link tp.live.response.ZhiBoDataResponse} original class using Vert.x codegen.
 */
public class ZhiBoDataResponseConverter {

  public static void fromJson(JsonObject json, ZhiBoDataResponse obj) {
    if (json.getValue("beginTime") instanceof String) {
      obj.setBeginTime((String)json.getValue("beginTime"));
    }
    if (json.getValue("belongArea") instanceof String) {
      obj.setBelongArea((String)json.getValue("belongArea"));
    }
    if (json.getValue("branchType") instanceof String) {
      obj.setBranchType((String)json.getValue("branchType"));
    }
    if (json.getValue("buyFlag") instanceof String) {
      obj.setBuyFlag((String)json.getValue("buyFlag"));
    }
    if (json.getValue("ch") instanceof String) {
      obj.setCh((String)json.getValue("ch"));
    }
    if (json.getValue("chatRoomNum") instanceof String) {
      obj.setChatRoomNum((String)json.getValue("chatRoomNum"));
    }
    if (json.getValue("cibnSelectId") instanceof String) {
      obj.setCibnSelectId((String)json.getValue("cibnSelectId"));
    }
    if (json.getValue("cids") instanceof JsonObject) {
      java.util.Map<String, java.lang.String> map = new java.util.LinkedHashMap<>();
      json.getJsonObject("cids").forEach(entry -> {
        if (entry.getValue() instanceof String)
          map.put(entry.getKey(), (String)entry.getValue());
      });
      obj.setCids(map);
    }
    if (json.getValue("commentaryLanguage") instanceof String) {
      obj.setCommentaryLanguage((String)json.getValue("commentaryLanguage"));
    }
    if (json.getValue("connectionLiveIds") instanceof String) {
      obj.setConnectionLiveIds((String)json.getValue("connectionLiveIds"));
    }
    if (json.getValue("description") instanceof String) {
      obj.setDescription((String)json.getValue("description"));
    }
    if (json.getValue("drmFlag") instanceof String) {
      obj.setDrmFlag((String)json.getValue("drmFlag"));
    }
    if (json.getValue("endTime") instanceof String) {
      obj.setEndTime((String)json.getValue("endTime"));
    }
    if (json.getValue("eventId") instanceof String) {
      obj.setEventId((String)json.getValue("eventId"));
    }
    if (json.getValue("focusPic") instanceof JsonObject) {
      obj.setFocusPic(new tp.live.response.Fpic((JsonObject)json.getValue("focusPic")));
    }
    if (json.getValue("guest") instanceof String) {
      obj.setGuest((String)json.getValue("guest"));
    }
    if (json.getValue("guestImgUrl") instanceof String) {
      obj.setGuestImgUrl((String)json.getValue("guestImgUrl"));
    }
    if (json.getValue("guestscore") instanceof String) {
      obj.setGuestscore((String)json.getValue("guestscore"));
    }
    if (json.getValue("home") instanceof String) {
      obj.setHome((String)json.getValue("home"));
    }
    if (json.getValue("homeImgUrl") instanceof String) {
      obj.setHomeImgUrl((String)json.getValue("homeImgUrl"));
    }
    if (json.getValue("homescore") instanceof String) {
      obj.setHomescore((String)json.getValue("homescore"));
    }
    if (json.getValue("id") instanceof String) {
      obj.setId((String)json.getValue("id"));
    }
    if (json.getValue("isChat") instanceof String) {
      obj.setIsChat((String)json.getValue("isChat"));
    }
    if (json.getValue("isDanmaku") instanceof String) {
      obj.setIsDanmaku((String)json.getValue("isDanmaku"));
    }
    if (json.getValue("isDolby") instanceof String) {
      obj.setIsDolby((String)json.getValue("isDolby"));
    }
    if (json.getValue("isPanoramicView") instanceof String) {
      obj.setIsPanoramicView((String)json.getValue("isPanoramicView"));
    }
    if (json.getValue("isPay") instanceof String) {
      obj.setIsPay((String)json.getValue("isPay"));
    }
    if (json.getValue("isVS") instanceof Number) {
      obj.setIsVS(((Number)json.getValue("isVS")).intValue());
    }
    if (json.getValue("leWord") instanceof JsonArray) {
      java.util.ArrayList<tp.live.response.SuperLiveTagTpResponse> list = new java.util.ArrayList<>();
      json.getJsonArray("leWord").forEach( item -> {
        if (item instanceof JsonObject)
          list.add(new tp.live.response.SuperLiveTagTpResponse((JsonObject)item));
      });
      obj.setLeWord(list);
    }
    if (json.getValue("level1") instanceof String) {
      obj.setLevel1((String)json.getValue("level1"));
    }
    if (json.getValue("level1Id") instanceof String) {
      obj.setLevel1Id((String)json.getValue("level1Id"));
    }
    if (json.getValue("level2") instanceof String) {
      obj.setLevel2((String)json.getValue("level2"));
    }
    if (json.getValue("level2Id") instanceof String) {
      obj.setLevel2Id((String)json.getValue("level2Id"));
    }
    if (json.getValue("liveType") instanceof String) {
      obj.setLiveType((String)json.getValue("liveType"));
    }
    if (json.getValue("match") instanceof String) {
      obj.setMatch((String)json.getValue("match"));
    }
    if (json.getValue("musicV2Screenings") instanceof String) {
      obj.setMusicV2Screenings((String)json.getValue("musicV2Screenings"));
    }
    if (json.getValue("originPrice") instanceof String) {
      obj.setOriginPrice((String)json.getValue("originPrice"));
    }
    if (json.getValue("partId") instanceof String) {
      obj.setPartId((String)json.getValue("partId"));
    }
    if (json.getValue("payPlatForm") instanceof String) {
      obj.setPayPlatForm((String)json.getValue("payPlatForm"));
    }
    if (json.getValue("payType") instanceof String) {
      obj.setPayType((String)json.getValue("payType"));
    }
    if (json.getValue("pid") instanceof Number) {
      obj.setPid(((Number)json.getValue("pid")).longValue());
    }
    if (json.getValue("preVID") instanceof String) {
      obj.setPreVID((String)json.getValue("preVID"));
    }
    if (json.getValue("price") instanceof String) {
      obj.setPrice((String)json.getValue("price"));
    }
    if (json.getValue("recordingId") instanceof String) {
      obj.setRecordingId((String)json.getValue("recordingId"));
    }
    if (json.getValue("screenings") instanceof String) {
      obj.setScreenings((String)json.getValue("screenings"));
    }
    if (json.getValue("season") instanceof String) {
      obj.setSeason((String)json.getValue("season"));
    }
    if (json.getValue("selectId") instanceof String) {
      obj.setSelectId((String)json.getValue("selectId"));
    }
    if (json.getValue("splatid") instanceof String) {
      obj.setSplatid((String)json.getValue("splatid"));
    }
    if (json.getValue("status") instanceof Number) {
      obj.setStatus(((Number)json.getValue("status")).intValue());
    }
    if (json.getValue("title") instanceof String) {
      obj.setTitle((String)json.getValue("title"));
    }
    if (json.getValue("type") instanceof Number) {
      obj.setType(((Number)json.getValue("type")).intValue());
    }
    if (json.getValue("typeName") instanceof String) {
      obj.setTypeName((String)json.getValue("typeName"));
    }
    if (json.getValue("vid") instanceof String) {
      obj.setVid((String)json.getValue("vid"));
    }
    if (json.getValue("viewPic") instanceof String) {
      obj.setViewPic((String)json.getValue("viewPic"));
    }
    if (json.getValue("vipPrice") instanceof String) {
      obj.setVipPrice((String)json.getValue("vipPrice"));
    }
    if (json.getValue("weight") instanceof Number) {
      obj.setWeight(((Number)json.getValue("weight")).intValue());
    }
  }

  public static void toJson(ZhiBoDataResponse obj, JsonObject json) {
    if (obj.getBeginTime() != null) {
      json.put("beginTime", obj.getBeginTime());
    }
    if (obj.getBelongArea() != null) {
      json.put("belongArea", obj.getBelongArea());
    }
    if (obj.getBranchType() != null) {
      json.put("branchType", obj.getBranchType());
    }
    if (obj.getBuyFlag() != null) {
      json.put("buyFlag", obj.getBuyFlag());
    }
    if (obj.getCh() != null) {
      json.put("ch", obj.getCh());
    }
    if (obj.getChatRoomNum() != null) {
      json.put("chatRoomNum", obj.getChatRoomNum());
    }
    if (obj.getCibnSelectId() != null) {
      json.put("cibnSelectId", obj.getCibnSelectId());
    }
    if (obj.getCids() != null) {
      JsonObject map = new JsonObject();
      obj.getCids().forEach((key,value) -> map.put(key, value));
      json.put("cids", map);
    }
    if (obj.getCommentaryLanguage() != null) {
      json.put("commentaryLanguage", obj.getCommentaryLanguage());
    }
    if (obj.getConnectionLiveIds() != null) {
      json.put("connectionLiveIds", obj.getConnectionLiveIds());
    }
    if (obj.getDescription() != null) {
      json.put("description", obj.getDescription());
    }
    if (obj.getDrmFlag() != null) {
      json.put("drmFlag", obj.getDrmFlag());
    }
    if (obj.getEndTime() != null) {
      json.put("endTime", obj.getEndTime());
    }
    if (obj.getEventId() != null) {
      json.put("eventId", obj.getEventId());
    }
    if (obj.getFocusPic() != null) {
      json.put("focusPic", obj.getFocusPic().toJson());
    }
    if (obj.getGuest() != null) {
      json.put("guest", obj.getGuest());
    }
    if (obj.getGuestImgUrl() != null) {
      json.put("guestImgUrl", obj.getGuestImgUrl());
    }
    if (obj.getGuestscore() != null) {
      json.put("guestscore", obj.getGuestscore());
    }
    if (obj.getHome() != null) {
      json.put("home", obj.getHome());
    }
    if (obj.getHomeImgUrl() != null) {
      json.put("homeImgUrl", obj.getHomeImgUrl());
    }
    if (obj.getHomescore() != null) {
      json.put("homescore", obj.getHomescore());
    }
    if (obj.getId() != null) {
      json.put("id", obj.getId());
    }
    if (obj.getIsChat() != null) {
      json.put("isChat", obj.getIsChat());
    }
    if (obj.getIsDanmaku() != null) {
      json.put("isDanmaku", obj.getIsDanmaku());
    }
    if (obj.getIsDolby() != null) {
      json.put("isDolby", obj.getIsDolby());
    }
    if (obj.getIsPanoramicView() != null) {
      json.put("isPanoramicView", obj.getIsPanoramicView());
    }
    if (obj.getIsPay() != null) {
      json.put("isPay", obj.getIsPay());
    }
    if (obj.getIsVS() != null) {
      json.put("isVS", obj.getIsVS());
    }
    if (obj.getLeWord() != null) {
      JsonArray array = new JsonArray();
      obj.getLeWord().forEach(item -> array.add(item.toJson()));
      json.put("leWord", array);
    }
    if (obj.getLevel1() != null) {
      json.put("level1", obj.getLevel1());
    }
    if (obj.getLevel1Id() != null) {
      json.put("level1Id", obj.getLevel1Id());
    }
    if (obj.getLevel2() != null) {
      json.put("level2", obj.getLevel2());
    }
    if (obj.getLevel2Id() != null) {
      json.put("level2Id", obj.getLevel2Id());
    }
    if (obj.getLiveType() != null) {
      json.put("liveType", obj.getLiveType());
    }
    if (obj.getMatch() != null) {
      json.put("match", obj.getMatch());
    }
    if (obj.getMusicV2Screenings() != null) {
      json.put("musicV2Screenings", obj.getMusicV2Screenings());
    }
    if (obj.getOriginPrice() != null) {
      json.put("originPrice", obj.getOriginPrice());
    }
    if (obj.getPartId() != null) {
      json.put("partId", obj.getPartId());
    }
    if (obj.getPayPlatForm() != null) {
      json.put("payPlatForm", obj.getPayPlatForm());
    }
    if (obj.getPayType() != null) {
      json.put("payType", obj.getPayType());
    }
    if (obj.getPid() != null) {
      json.put("pid", obj.getPid());
    }
    if (obj.getPreVID() != null) {
      json.put("preVID", obj.getPreVID());
    }
    if (obj.getPrice() != null) {
      json.put("price", obj.getPrice());
    }
    if (obj.getRecordingId() != null) {
      json.put("recordingId", obj.getRecordingId());
    }
    if (obj.getScreenings() != null) {
      json.put("screenings", obj.getScreenings());
    }
    if (obj.getSeason() != null) {
      json.put("season", obj.getSeason());
    }
    if (obj.getSelectId() != null) {
      json.put("selectId", obj.getSelectId());
    }
    if (obj.getSplatid() != null) {
      json.put("splatid", obj.getSplatid());
    }
    if (obj.getStatus() != null) {
      json.put("status", obj.getStatus());
    }
    if (obj.getTitle() != null) {
      json.put("title", obj.getTitle());
    }
    if (obj.getType() != null) {
      json.put("type", obj.getType());
    }
    if (obj.getTypeName() != null) {
      json.put("typeName", obj.getTypeName());
    }
    if (obj.getVid() != null) {
      json.put("vid", obj.getVid());
    }
    if (obj.getViewPic() != null) {
      json.put("viewPic", obj.getViewPic());
    }
    if (obj.getVipPrice() != null) {
      json.put("vipPrice", obj.getVipPrice());
    }
    if (obj.getWeight() != null) {
      json.put("weight", obj.getWeight());
    }
  }
}