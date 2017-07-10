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

package cache.dto;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;

/**
 * Converter for {@link cache.dto.MusicCache}.
 *
 * NOTE: This class has been automatically generated from the {@link cache.dto.MusicCache} original class using Vert.x codegen.
 */
public class MusicCacheConverter {

  public static void fromJson(JsonObject json, MusicCache obj) {
    if (json.getValue("areaCode") instanceof String) {
      obj.setAreaCode((String)json.getValue("areaCode"));
    }
    if (json.getValue("areaName") instanceof String) {
      obj.setAreaName((String)json.getValue("areaName"));
    }
    if (json.getValue("arranger") instanceof String) {
      obj.setArranger((String)json.getValue("arranger"));
    }
    if (json.getValue("audioFile") instanceof String) {
      obj.setAudioFile((String)json.getValue("audioFile"));
    }
    if (json.getValue("authors") instanceof String) {
      obj.setAuthors((String)json.getValue("authors"));
    }
    if (json.getValue("category") instanceof String) {
      obj.setCategory((String)json.getValue("category"));
    }
    if (json.getValue("compose") instanceof String) {
      obj.setCompose((String)json.getValue("compose"));
    }
    if (json.getValue("compressMode") instanceof Number) {
      obj.setCompressMode(((Number)json.getValue("compressMode")).intValue());
    }
    if (json.getValue("copyrightTypeCode") instanceof String) {
      obj.setCopyrightTypeCode((String)json.getValue("copyrightTypeCode"));
    }
    if (json.getValue("copyrightTypeName") instanceof String) {
      obj.setCopyrightTypeName((String)json.getValue("copyrightTypeName"));
    }
    if (json.getValue("desc") instanceof String) {
      obj.setDesc((String)json.getValue("desc"));
    }
    if (json.getValue("downloadPlatform") instanceof String) {
      obj.setDownloadPlatform((String)json.getValue("downloadPlatform"));
    }
    if (json.getValue("duration") instanceof Number) {
      obj.setDuration(((Number)json.getValue("duration")).intValue());
    }
    if (json.getValue("key") instanceof String) {
      obj.setKey((String)json.getValue("key"));
    }
    if (json.getValue("languageCode") instanceof String) {
      obj.setLanguageCode((String)json.getValue("languageCode"));
    }
    if (json.getValue("languageName") instanceof String) {
      obj.setLanguageName((String)json.getValue("languageName"));
    }
    if (json.getValue("lyricFile") instanceof String) {
      obj.setLyricFile((String)json.getValue("lyricFile"));
    }
    if (json.getValue("lyricText") instanceof String) {
      obj.setLyricText((String)json.getValue("lyricText"));
    }
    if (json.getValue("makerCompany") instanceof String) {
      obj.setMakerCompany((String)json.getValue("makerCompany"));
    }
    if (json.getValue("mid") instanceof Number) {
      obj.setMid(((Number)json.getValue("mid")).longValue());
    }
    if (json.getValue("mmsAudioCodes") instanceof JsonArray) {
      java.util.ArrayList<cache.dto.AudioCodes> list = new java.util.ArrayList<>();
      json.getJsonArray("mmsAudioCodes").forEach( item -> {
        if (item instanceof JsonObject)
          list.add(new cache.dto.AudioCodes((JsonObject)item));
      });
      obj.setMmsAudioCodes(list);
    }
    if (json.getValue("ostId") instanceof Number) {
      obj.setOstId(((Number)json.getValue("ostId")).longValue());
    }
    if (json.getValue("playPlatform") instanceof String) {
      obj.setPlayPlatform((String)json.getValue("playPlatform"));
    }
    if (json.getValue("porder") instanceof Number) {
      obj.setPorder(((Number)json.getValue("porder")).intValue());
    }
    if (json.getValue("releaseDate") instanceof String) {
      obj.setReleaseDate((String)json.getValue("releaseDate"));
    }
    if (json.getValue("singerName") instanceof String) {
      obj.setSingerName((String)json.getValue("singerName"));
    }
    if (json.getValue("songId") instanceof String) {
      obj.setSongId((String)json.getValue("songId"));
    }
    if (json.getValue("songName") instanceof String) {
      obj.setSongName((String)json.getValue("songName"));
    }
    if (json.getValue("songTypeId") instanceof Number) {
      obj.setSongTypeId(((Number)json.getValue("songTypeId")).intValue());
    }
    if (json.getValue("songTypeName") instanceof String) {
      obj.setSongTypeName((String)json.getValue("songTypeName"));
    }
    if (json.getValue("sourceCode") instanceof String) {
      obj.setSourceCode((String)json.getValue("sourceCode"));
    }
    if (json.getValue("sourceName") instanceof String) {
      obj.setSourceName((String)json.getValue("sourceName"));
    }
    if (json.getValue("style") instanceof String) {
      obj.setStyle((String)json.getValue("style"));
    }
    if (json.getValue("subTitle") instanceof String) {
      obj.setSubTitle((String)json.getValue("subTitle"));
    }
    if (json.getValue("type") instanceof Number) {
      obj.setType(((Number)json.getValue("type")).intValue());
    }
    if (json.getValue("xiamiId") instanceof String) {
      obj.setXiamiId((String)json.getValue("xiamiId"));
    }
  }

  public static void toJson(MusicCache obj, JsonObject json) {
    if (obj.getAreaCode() != null) {
      json.put("areaCode", obj.getAreaCode());
    }
    if (obj.getAreaName() != null) {
      json.put("areaName", obj.getAreaName());
    }
    if (obj.getArranger() != null) {
      json.put("arranger", obj.getArranger());
    }
    if (obj.getAudioFile() != null) {
      json.put("audioFile", obj.getAudioFile());
    }
    if (obj.getAuthors() != null) {
      json.put("authors", obj.getAuthors());
    }
    if (obj.getCategory() != null) {
      json.put("category", obj.getCategory());
    }
    if (obj.getCompose() != null) {
      json.put("compose", obj.getCompose());
    }
    if (obj.getCompressMode() != null) {
      json.put("compressMode", obj.getCompressMode());
    }
    if (obj.getCopyrightTypeCode() != null) {
      json.put("copyrightTypeCode", obj.getCopyrightTypeCode());
    }
    if (obj.getCopyrightTypeName() != null) {
      json.put("copyrightTypeName", obj.getCopyrightTypeName());
    }
    if (obj.getDesc() != null) {
      json.put("desc", obj.getDesc());
    }
    if (obj.getDownloadPlatform() != null) {
      json.put("downloadPlatform", obj.getDownloadPlatform());
    }
    if (obj.getDuration() != null) {
      json.put("duration", obj.getDuration());
    }
    if (obj.getKey() != null) {
      json.put("key", obj.getKey());
    }
    if (obj.getLanguageCode() != null) {
      json.put("languageCode", obj.getLanguageCode());
    }
    if (obj.getLanguageName() != null) {
      json.put("languageName", obj.getLanguageName());
    }
    if (obj.getLyricFile() != null) {
      json.put("lyricFile", obj.getLyricFile());
    }
    if (obj.getLyricText() != null) {
      json.put("lyricText", obj.getLyricText());
    }
    if (obj.getMakerCompany() != null) {
      json.put("makerCompany", obj.getMakerCompany());
    }
    if (obj.getMid() != null) {
      json.put("mid", obj.getMid());
    }
    if (obj.getMmsAudioCodes() != null) {
      JsonArray array = new JsonArray();
      obj.getMmsAudioCodes().forEach(item -> array.add(item.toJson()));
      json.put("mmsAudioCodes", array);
    }
    if (obj.getOstId() != null) {
      json.put("ostId", obj.getOstId());
    }
    if (obj.getPlayPlatform() != null) {
      json.put("playPlatform", obj.getPlayPlatform());
    }
    if (obj.getPorder() != null) {
      json.put("porder", obj.getPorder());
    }
    if (obj.getReleaseDate() != null) {
      json.put("releaseDate", obj.getReleaseDate());
    }
    if (obj.getSingerName() != null) {
      json.put("singerName", obj.getSingerName());
    }
    if (obj.getSongId() != null) {
      json.put("songId", obj.getSongId());
    }
    if (obj.getSongName() != null) {
      json.put("songName", obj.getSongName());
    }
    if (obj.getSongTypeId() != null) {
      json.put("songTypeId", obj.getSongTypeId());
    }
    if (obj.getSongTypeName() != null) {
      json.put("songTypeName", obj.getSongTypeName());
    }
    if (obj.getSourceCode() != null) {
      json.put("sourceCode", obj.getSourceCode());
    }
    if (obj.getSourceName() != null) {
      json.put("sourceName", obj.getSourceName());
    }
    if (obj.getStyle() != null) {
      json.put("style", obj.getStyle());
    }
    if (obj.getSubTitle() != null) {
      json.put("subTitle", obj.getSubTitle());
    }
    if (obj.getType() != null) {
      json.put("type", obj.getType());
    }
    if (obj.getXiamiId() != null) {
      json.put("xiamiId", obj.getXiamiId());
    }
  }
}