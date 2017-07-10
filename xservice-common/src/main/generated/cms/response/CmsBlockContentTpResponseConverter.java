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

package cms.response;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;

/**
 * Converter for {@link cms.response.CmsBlockContentTpResponse}.
 *
 * NOTE: This class has been automatically generated from the {@link cms.response.CmsBlockContentTpResponse} original class using Vert.x codegen.
 */
public class CmsBlockContentTpResponseConverter {

  public static void fromJson(JsonObject json, CmsBlockContentTpResponse obj) {
    if (json.getValue("androidUrl") instanceof String) {
      obj.setAndroidUrl((String)json.getValue("androidUrl"));
    }
    if (json.getValue("bid") instanceof String) {
      obj.setBid((String)json.getValue("bid"));
    }
    if (json.getValue("content") instanceof String) {
      obj.setContent((String)json.getValue("content"));
    }
    if (json.getValue("endTime") instanceof String) {
      obj.setEndTime((String)json.getValue("endTime"));
    }
    if (json.getValue("extends_QRcodescript") instanceof String) {
      obj.setExtends_QRcodescript((String)json.getValue("extends_QRcodescript"));
    }
    if (json.getValue("extends_buttonscript") instanceof String) {
      obj.setExtends_buttonscript((String)json.getValue("extends_buttonscript"));
    }
    if (json.getValue("extends_cardscript1") instanceof String) {
      obj.setExtends_cardscript1((String)json.getValue("extends_cardscript1"));
    }
    if (json.getValue("extends_cardscript2") instanceof String) {
      obj.setExtends_cardscript2((String)json.getValue("extends_cardscript2"));
    }
    if (json.getValue("extends_extendPicAll") instanceof String) {
      obj.setExtends_extendPicAll((String)json.getValue("extends_extendPicAll"));
    }
    if (json.getValue("iosUrl") instanceof String) {
      obj.setIosUrl((String)json.getValue("iosUrl"));
    }
    if (json.getValue("mobilePic") instanceof String) {
      obj.setMobilePic((String)json.getValue("mobilePic"));
    }
    if (json.getValue("nameCn") instanceof String) {
      obj.setNameCn((String)json.getValue("nameCn"));
    }
    if (json.getValue("padPic") instanceof String) {
      obj.setPadPic((String)json.getValue("padPic"));
    }
    if (json.getValue("pic1") instanceof String) {
      obj.setPic1((String)json.getValue("pic1"));
    }
    if (json.getValue("pic2") instanceof String) {
      obj.setPic2((String)json.getValue("pic2"));
    }
    if (json.getValue("playPlatform") instanceof JsonObject) {
      java.util.Map<String, java.lang.String> map = new java.util.LinkedHashMap<>();
      json.getJsonObject("playPlatform").forEach(entry -> {
        if (entry.getValue() instanceof String)
          map.put(entry.getKey(), (String)entry.getValue());
      });
      obj.setPlayPlatform(map);
    }
    if (json.getValue("position") instanceof String) {
      obj.setPosition((String)json.getValue("position"));
    }
    if (json.getValue("priority") instanceof String) {
      obj.setPriority((String)json.getValue("priority"));
    }
    if (json.getValue("pushflag") instanceof String) {
      obj.setPushflag((String)json.getValue("pushflag"));
    }
    if (json.getValue("remark") instanceof String) {
      obj.setRemark((String)json.getValue("remark"));
    }
    if (json.getValue("shorDesc") instanceof String) {
      obj.setShorDesc((String)json.getValue("shorDesc"));
    }
    if (json.getValue("shortDesc") instanceof String) {
      obj.setShortDesc((String)json.getValue("shortDesc"));
    }
    if (json.getValue("skipPage") instanceof String) {
      obj.setSkipPage((String)json.getValue("skipPage"));
    }
    if (json.getValue("skipType") instanceof String) {
      obj.setSkipType((String)json.getValue("skipType"));
    }
    if (json.getValue("skipUrl") instanceof String) {
      obj.setSkipUrl((String)json.getValue("skipUrl"));
    }
    if (json.getValue("startTime") instanceof String) {
      obj.setStartTime((String)json.getValue("startTime"));
    }
    if (json.getValue("subTitle") instanceof String) {
      obj.setSubTitle((String)json.getValue("subTitle"));
    }
    if (json.getValue("tag") instanceof String) {
      obj.setTag((String)json.getValue("tag"));
    }
    if (json.getValue("tagUrl") instanceof String) {
      obj.setTagUrl((String)json.getValue("tagUrl"));
    }
    if (json.getValue("title") instanceof String) {
      obj.setTitle((String)json.getValue("title"));
    }
    if (json.getValue("tvPic") instanceof String) {
      obj.setTvPic((String)json.getValue("tvPic"));
    }
    if (json.getValue("tvUrl") instanceof String) {
      obj.setTvUrl((String)json.getValue("tvUrl"));
    }
    if (json.getValue("type") instanceof Number) {
      obj.setType(((Number)json.getValue("type")).intValue());
    }
    if (json.getValue("url") instanceof String) {
      obj.setUrl((String)json.getValue("url"));
    }
  }

  public static void toJson(CmsBlockContentTpResponse obj, JsonObject json) {
    if (obj.getAndroidUrl() != null) {
      json.put("androidUrl", obj.getAndroidUrl());
    }
    if (obj.getBid() != null) {
      json.put("bid", obj.getBid());
    }
    if (obj.getContent() != null) {
      json.put("content", obj.getContent());
    }
    if (obj.getEndTime() != null) {
      json.put("endTime", obj.getEndTime());
    }
    if (obj.getExtends_QRcodescript() != null) {
      json.put("extends_QRcodescript", obj.getExtends_QRcodescript());
    }
    if (obj.getExtends_buttonscript() != null) {
      json.put("extends_buttonscript", obj.getExtends_buttonscript());
    }
    if (obj.getExtends_cardscript1() != null) {
      json.put("extends_cardscript1", obj.getExtends_cardscript1());
    }
    if (obj.getExtends_cardscript2() != null) {
      json.put("extends_cardscript2", obj.getExtends_cardscript2());
    }
    if (obj.getExtends_extendPicAll() != null) {
      json.put("extends_extendPicAll", obj.getExtends_extendPicAll());
    }
    if (obj.getIosUrl() != null) {
      json.put("iosUrl", obj.getIosUrl());
    }
    if (obj.getMobilePic() != null) {
      json.put("mobilePic", obj.getMobilePic());
    }
    if (obj.getNameCn() != null) {
      json.put("nameCn", obj.getNameCn());
    }
    if (obj.getPadPic() != null) {
      json.put("padPic", obj.getPadPic());
    }
    if (obj.getPic1() != null) {
      json.put("pic1", obj.getPic1());
    }
    if (obj.getPic2() != null) {
      json.put("pic2", obj.getPic2());
    }
    if (obj.getPlayPlatform() != null) {
      JsonObject map = new JsonObject();
      obj.getPlayPlatform().forEach((key,value) -> map.put(key, value));
      json.put("playPlatform", map);
    }
    if (obj.getPosition() != null) {
      json.put("position", obj.getPosition());
    }
    if (obj.getPriority() != null) {
      json.put("priority", obj.getPriority());
    }
    if (obj.getPushflag() != null) {
      json.put("pushflag", obj.getPushflag());
    }
    if (obj.getRemark() != null) {
      json.put("remark", obj.getRemark());
    }
    if (obj.getShorDesc() != null) {
      json.put("shorDesc", obj.getShorDesc());
    }
    if (obj.getShortDesc() != null) {
      json.put("shortDesc", obj.getShortDesc());
    }
    if (obj.getSkipPage() != null) {
      json.put("skipPage", obj.getSkipPage());
    }
    if (obj.getSkipType() != null) {
      json.put("skipType", obj.getSkipType());
    }
    if (obj.getSkipUrl() != null) {
      json.put("skipUrl", obj.getSkipUrl());
    }
    if (obj.getStartTime() != null) {
      json.put("startTime", obj.getStartTime());
    }
    if (obj.getSubTitle() != null) {
      json.put("subTitle", obj.getSubTitle());
    }
    if (obj.getTag() != null) {
      json.put("tag", obj.getTag());
    }
    if (obj.getTagUrl() != null) {
      json.put("tagUrl", obj.getTagUrl());
    }
    if (obj.getTitle() != null) {
      json.put("title", obj.getTitle());
    }
    if (obj.getTvPic() != null) {
      json.put("tvPic", obj.getTvPic());
    }
    if (obj.getTvUrl() != null) {
      json.put("tvUrl", obj.getTvUrl());
    }
    if (obj.getType() != null) {
      json.put("type", obj.getType());
    }
    if (obj.getUrl() != null) {
      json.put("url", obj.getUrl());
    }
  }
}