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
 * Converter for {@link tp.live.response.LmsDataResponse}.
 *
 * NOTE: This class has been automatically generated from the {@link tp.live.response.LmsDataResponse} original class using Vert.x codegen.
 */
public class LmsDataResponseConverter {

  public static void fromJson(JsonObject json, LmsDataResponse obj) {
    if (json.getValue("anchorId") instanceof String) {
      obj.setAnchorId((String)json.getValue("anchorId"));
    }
    if (json.getValue("anchorName") instanceof String) {
      obj.setAnchorName((String)json.getValue("anchorName"));
    }
    if (json.getValue("anchorSex") instanceof String) {
      obj.setAnchorSex((String)json.getValue("anchorSex"));
    }
    if (json.getValue("chatRoomId") instanceof String) {
      obj.setChatRoomId((String)json.getValue("chatRoomId"));
    }
    if (json.getValue("ctime") instanceof String) {
      obj.setCtime((String)json.getValue("ctime"));
    }
    if (json.getValue("headPic") instanceof String) {
      obj.setHeadPic((String)json.getValue("headPic"));
    }
    if (json.getValue("id") instanceof String) {
      obj.setId((String)json.getValue("id"));
    }
    if (json.getValue("imageScala") instanceof String) {
      obj.setImageScala((String)json.getValue("imageScala"));
    }
    if (json.getValue("interactionId") instanceof String) {
      obj.setInteractionId((String)json.getValue("interactionId"));
    }
    if (json.getValue("isBarrage") instanceof String) {
      obj.setIsBarrage((String)json.getValue("isBarrage"));
    }
    if (json.getValue("isChatRoom") instanceof String) {
      obj.setIsChatRoom((String)json.getValue("isChatRoom"));
    }
    if (json.getValue("isLike") instanceof String) {
      obj.setIsLike((String)json.getValue("isLike"));
    }
    if (json.getValue("isProp") instanceof String) {
      obj.setIsProp((String)json.getValue("isProp"));
    }
    if (json.getValue("isRward") instanceof String) {
      obj.setIsRward((String)json.getValue("isRward"));
    }
    if (json.getValue("isShare") instanceof String) {
      obj.setIsShare((String)json.getValue("isShare"));
    }
    if (json.getValue("liveRoomDesc") instanceof String) {
      obj.setLiveRoomDesc((String)json.getValue("liveRoomDesc"));
    }
    if (json.getValue("liveRoomName") instanceof String) {
      obj.setLiveRoomName((String)json.getValue("liveRoomName"));
    }
    if (json.getValue("liveRoomPic") instanceof String) {
      obj.setLiveRoomPic((String)json.getValue("liveRoomPic"));
    }
    if (json.getValue("liveRoomStatus") instanceof String) {
      obj.setLiveRoomStatus((String)json.getValue("liveRoomStatus"));
    }
    if (json.getValue("liveRoomStreamId") instanceof String) {
      obj.setLiveRoomStreamId((String)json.getValue("liveRoomStreamId"));
    }
    if (json.getValue("liveRoomType") instanceof String) {
      obj.setLiveRoomType((String)json.getValue("liveRoomType"));
    }
    if (json.getValue("liveRoomWeight") instanceof String) {
      obj.setLiveRoomWeight((String)json.getValue("liveRoomWeight"));
    }
    if (json.getValue("mtime") instanceof String) {
      obj.setMtime((String)json.getValue("mtime"));
    }
    if (json.getValue("partnerRoomId") instanceof String) {
      obj.setPartnerRoomId((String)json.getValue("partnerRoomId"));
    }
    if (json.getValue("roomId") instanceof String) {
      obj.setRoomId((String)json.getValue("roomId"));
    }
    if (json.getValue("shareDoc") instanceof String) {
      obj.setShareDoc((String)json.getValue("shareDoc"));
    }
    if (json.getValue("sid") instanceof String) {
      obj.setSid((String)json.getValue("sid"));
    }
    if (json.getValue("sourceName") instanceof String) {
      obj.setSourceName((String)json.getValue("sourceName"));
    }
    if (json.getValue("status") instanceof String) {
      obj.setStatus((String)json.getValue("status"));
    }
    if (json.getValue("streamInfo") instanceof String) {
      obj.setStreamInfo((String)json.getValue("streamInfo"));
    }
    if (json.getValue("version") instanceof String) {
      obj.setVersion((String)json.getValue("version"));
    }
  }

  public static void toJson(LmsDataResponse obj, JsonObject json) {
    if (obj.getAnchorId() != null) {
      json.put("anchorId", obj.getAnchorId());
    }
    if (obj.getAnchorName() != null) {
      json.put("anchorName", obj.getAnchorName());
    }
    if (obj.getAnchorSex() != null) {
      json.put("anchorSex", obj.getAnchorSex());
    }
    if (obj.getChatRoomId() != null) {
      json.put("chatRoomId", obj.getChatRoomId());
    }
    if (obj.getCtime() != null) {
      json.put("ctime", obj.getCtime());
    }
    if (obj.getHeadPic() != null) {
      json.put("headPic", obj.getHeadPic());
    }
    if (obj.getId() != null) {
      json.put("id", obj.getId());
    }
    if (obj.getImageScala() != null) {
      json.put("imageScala", obj.getImageScala());
    }
    if (obj.getInteractionId() != null) {
      json.put("interactionId", obj.getInteractionId());
    }
    if (obj.getIsBarrage() != null) {
      json.put("isBarrage", obj.getIsBarrage());
    }
    if (obj.getIsChatRoom() != null) {
      json.put("isChatRoom", obj.getIsChatRoom());
    }
    if (obj.getIsLike() != null) {
      json.put("isLike", obj.getIsLike());
    }
    if (obj.getIsProp() != null) {
      json.put("isProp", obj.getIsProp());
    }
    if (obj.getIsRward() != null) {
      json.put("isRward", obj.getIsRward());
    }
    if (obj.getIsShare() != null) {
      json.put("isShare", obj.getIsShare());
    }
    if (obj.getLiveRoomDesc() != null) {
      json.put("liveRoomDesc", obj.getLiveRoomDesc());
    }
    if (obj.getLiveRoomName() != null) {
      json.put("liveRoomName", obj.getLiveRoomName());
    }
    if (obj.getLiveRoomPic() != null) {
      json.put("liveRoomPic", obj.getLiveRoomPic());
    }
    if (obj.getLiveRoomStatus() != null) {
      json.put("liveRoomStatus", obj.getLiveRoomStatus());
    }
    if (obj.getLiveRoomStreamId() != null) {
      json.put("liveRoomStreamId", obj.getLiveRoomStreamId());
    }
    if (obj.getLiveRoomType() != null) {
      json.put("liveRoomType", obj.getLiveRoomType());
    }
    if (obj.getLiveRoomWeight() != null) {
      json.put("liveRoomWeight", obj.getLiveRoomWeight());
    }
    if (obj.getMtime() != null) {
      json.put("mtime", obj.getMtime());
    }
    if (obj.getPartnerRoomId() != null) {
      json.put("partnerRoomId", obj.getPartnerRoomId());
    }
    if (obj.getRoomId() != null) {
      json.put("roomId", obj.getRoomId());
    }
    if (obj.getShareDoc() != null) {
      json.put("shareDoc", obj.getShareDoc());
    }
    if (obj.getSid() != null) {
      json.put("sid", obj.getSid());
    }
    if (obj.getSourceName() != null) {
      json.put("sourceName", obj.getSourceName());
    }
    if (obj.getStatus() != null) {
      json.put("status", obj.getStatus());
    }
    if (obj.getStreamInfo() != null) {
      json.put("streamInfo", obj.getStreamInfo());
    }
    if (obj.getVersion() != null) {
      json.put("version", obj.getVersion());
    }
  }
}