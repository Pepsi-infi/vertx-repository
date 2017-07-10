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

package tp.rec.response;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;

/**
 * Converter for {@link tp.rec.response.RecommendDetail}.
 *
 * NOTE: This class has been automatically generated from the {@link tp.rec.response.RecommendDetail} original class using Vert.x codegen.
 */
public class RecommendDetailConverter {

  public static void fromJson(JsonObject json, RecommendDetail obj) {
    if (json.getValue("actors") instanceof String) {
      obj.setActors((String)json.getValue("actors"));
    }
    if (json.getValue("album_area") instanceof String) {
      obj.setAlbum_area((String)json.getValue("album_area"));
    }
    if (json.getValue("album_play_platform") instanceof String) {
      obj.setAlbum_play_platform((String)json.getValue("album_play_platform"));
    }
    if (json.getValue("album_play_tv") instanceof String) {
      obj.setAlbum_play_tv((String)json.getValue("album_play_tv"));
    }
    if (json.getValue("album_release_date") instanceof String) {
      obj.setAlbum_release_date((String)json.getValue("album_release_date"));
    }
    if (json.getValue("album_sub_category") instanceof String) {
      obj.setAlbum_sub_category((String)json.getValue("album_sub_category"));
    }
    if (json.getValue("album_sub_category_code") instanceof String) {
      obj.setAlbum_sub_category_code((String)json.getValue("album_sub_category_code"));
    }
    if (json.getValue("cid") instanceof Number) {
      obj.setCid(((Number)json.getValue("cid")).intValue());
    }
    if (json.getValue("createtime") instanceof String) {
      obj.setCreatetime((String)json.getValue("createtime"));
    }
    if (json.getValue("description") instanceof String) {
      obj.setDescription((String)json.getValue("description"));
    }
    if (json.getValue("director") instanceof String) {
      obj.setDirector((String)json.getValue("director"));
    }
    if (json.getValue("duration") instanceof Number) {
      obj.setDuration(((Number)json.getValue("duration")).longValue());
    }
    if (json.getValue("episodes") instanceof Number) {
      obj.setEpisodes(((Number)json.getValue("episodes")).intValue());
    }
    if (json.getValue("float_flag") instanceof String) {
      obj.setFloat_flag((String)json.getValue("float_flag"));
    }
    if (json.getValue("guest") instanceof String) {
      obj.setGuest((String)json.getValue("guest"));
    }
    if (json.getValue("is_pay") instanceof String) {
      obj.setIs_pay((String)json.getValue("is_pay"));
    }
    if (json.getValue("isalbum") instanceof Number) {
      obj.setIsalbum(((Number)json.getValue("isalbum")).intValue());
    }
    if (json.getValue("isend") instanceof Number) {
      obj.setIsend(((Number)json.getValue("isend")).intValue());
    }
    if (json.getValue("jump") instanceof Number) {
      obj.setJump(((Number)json.getValue("jump")).intValue());
    }
    if (json.getValue("pic400_250") instanceof String) {
      obj.setPic400_250((String)json.getValue("pic400_250"));
    }
    if (json.getValue("pic400_300") instanceof String) {
      obj.setPic400_300((String)json.getValue("pic400_300"));
    }
    if (json.getValue("picHT") instanceof String) {
      obj.setPicHT((String)json.getValue("picHT"));
    }
    if (json.getValue("picST") instanceof String) {
      obj.setPicST((String)json.getValue("picST"));
    }
    if (json.getValue("picsize") instanceof String) {
      obj.setPicsize((String)json.getValue("picsize"));
    }
    if (json.getValue("picurl") instanceof String) {
      obj.setPicurl((String)json.getValue("picurl"));
    }
    if (json.getValue("pid") instanceof Number) {
      obj.setPid(((Number)json.getValue("pid")).longValue());
    }
    if (json.getValue("pidname") instanceof String) {
      obj.setPidname((String)json.getValue("pidname"));
    }
    if (json.getValue("playurl") instanceof String) {
      obj.setPlayurl((String)json.getValue("playurl"));
    }
    if (json.getValue("score") instanceof String) {
      obj.setScore((String)json.getValue("score"));
    }
    if (json.getValue("singer") instanceof String) {
      obj.setSinger((String)json.getValue("singer"));
    }
    if (json.getValue("starring") instanceof String) {
      obj.setStarring((String)json.getValue("starring"));
    }
    if (json.getValue("subtitle") instanceof String) {
      obj.setSubtitle((String)json.getValue("subtitle"));
    }
    if (json.getValue("title") instanceof String) {
      obj.setTitle((String)json.getValue("title"));
    }
    if (json.getValue("vcount") instanceof Number) {
      obj.setVcount(((Number)json.getValue("vcount")).intValue());
    }
    if (json.getValue("vid") instanceof Number) {
      obj.setVid(((Number)json.getValue("vid")).longValue());
    }
    if (json.getValue("video_area") instanceof String) {
      obj.setVideo_area((String)json.getValue("video_area"));
    }
    if (json.getValue("video_play_platform") instanceof String) {
      obj.setVideo_play_platform((String)json.getValue("video_play_platform"));
    }
    if (json.getValue("video_release_date") instanceof String) {
      obj.setVideo_release_date((String)json.getValue("video_release_date"));
    }
    if (json.getValue("video_sub_category") instanceof String) {
      obj.setVideo_sub_category((String)json.getValue("video_sub_category"));
    }
    if (json.getValue("video_type") instanceof String) {
      obj.setVideo_type((String)json.getValue("video_type"));
    }
    if (json.getValue("video_type_name") instanceof String) {
      obj.setVideo_type_name((String)json.getValue("video_type_name"));
    }
  }

  public static void toJson(RecommendDetail obj, JsonObject json) {
    if (obj.getActors() != null) {
      json.put("actors", obj.getActors());
    }
    if (obj.getAlbum_area() != null) {
      json.put("album_area", obj.getAlbum_area());
    }
    if (obj.getAlbum_play_platform() != null) {
      json.put("album_play_platform", obj.getAlbum_play_platform());
    }
    if (obj.getAlbum_play_tv() != null) {
      json.put("album_play_tv", obj.getAlbum_play_tv());
    }
    if (obj.getAlbum_release_date() != null) {
      json.put("album_release_date", obj.getAlbum_release_date());
    }
    if (obj.getAlbum_sub_category() != null) {
      json.put("album_sub_category", obj.getAlbum_sub_category());
    }
    if (obj.getAlbum_sub_category_code() != null) {
      json.put("album_sub_category_code", obj.getAlbum_sub_category_code());
    }
    if (obj.getCid() != null) {
      json.put("cid", obj.getCid());
    }
    if (obj.getCreatetime() != null) {
      json.put("createtime", obj.getCreatetime());
    }
    if (obj.getDescription() != null) {
      json.put("description", obj.getDescription());
    }
    if (obj.getDirector() != null) {
      json.put("director", obj.getDirector());
    }
    if (obj.getDuration() != null) {
      json.put("duration", obj.getDuration());
    }
    if (obj.getEpisodes() != null) {
      json.put("episodes", obj.getEpisodes());
    }
    if (obj.getFloat_flag() != null) {
      json.put("float_flag", obj.getFloat_flag());
    }
    if (obj.getGuest() != null) {
      json.put("guest", obj.getGuest());
    }
    if (obj.getIs_pay() != null) {
      json.put("is_pay", obj.getIs_pay());
    }
    if (obj.getIsalbum() != null) {
      json.put("isalbum", obj.getIsalbum());
    }
    if (obj.getIsend() != null) {
      json.put("isend", obj.getIsend());
    }
    if (obj.getJump() != null) {
      json.put("jump", obj.getJump());
    }
    if (obj.getPic400_250() != null) {
      json.put("pic400_250", obj.getPic400_250());
    }
    if (obj.getPic400_300() != null) {
      json.put("pic400_300", obj.getPic400_300());
    }
    if (obj.getPicHT() != null) {
      json.put("picHT", obj.getPicHT());
    }
    if (obj.getPicST() != null) {
      json.put("picST", obj.getPicST());
    }
    if (obj.getPicsize() != null) {
      json.put("picsize", obj.getPicsize());
    }
    if (obj.getPicurl() != null) {
      json.put("picurl", obj.getPicurl());
    }
    if (obj.getPid() != null) {
      json.put("pid", obj.getPid());
    }
    if (obj.getPidname() != null) {
      json.put("pidname", obj.getPidname());
    }
    if (obj.getPlayurl() != null) {
      json.put("playurl", obj.getPlayurl());
    }
    if (obj.getScore() != null) {
      json.put("score", obj.getScore());
    }
    if (obj.getSinger() != null) {
      json.put("singer", obj.getSinger());
    }
    if (obj.getStarring() != null) {
      json.put("starring", obj.getStarring());
    }
    if (obj.getSubtitle() != null) {
      json.put("subtitle", obj.getSubtitle());
    }
    if (obj.getTitle() != null) {
      json.put("title", obj.getTitle());
    }
    if (obj.getVcount() != null) {
      json.put("vcount", obj.getVcount());
    }
    if (obj.getVid() != null) {
      json.put("vid", obj.getVid());
    }
    if (obj.getVideo_area() != null) {
      json.put("video_area", obj.getVideo_area());
    }
    if (obj.getVideo_play_platform() != null) {
      json.put("video_play_platform", obj.getVideo_play_platform());
    }
    if (obj.getVideo_release_date() != null) {
      json.put("video_release_date", obj.getVideo_release_date());
    }
    if (obj.getVideo_sub_category() != null) {
      json.put("video_sub_category", obj.getVideo_sub_category());
    }
    if (obj.getVideo_type() != null) {
      json.put("video_type", obj.getVideo_type());
    }
    if (obj.getVideo_type_name() != null) {
      json.put("video_type_name", obj.getVideo_type_name());
    }
  }
}