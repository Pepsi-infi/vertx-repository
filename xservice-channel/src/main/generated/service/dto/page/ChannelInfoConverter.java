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

package service.dto.page;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;

/**
 * Converter for {@link service.dto.page.ChannelInfo}.
 *
 * NOTE: This class has been automatically generated from the {@link service.dto.page.ChannelInfo} original class using Vert.x codegen.
 */
public class ChannelInfoConverter {

  public static void fromJson(JsonObject json, ChannelInfo obj) {
    if (json.getValue("addOnId") instanceof String) {
      obj.setAddOnId((String)json.getValue("addOnId"));
    }
    if (json.getValue("cmsId") instanceof String) {
      obj.setCmsId((String)json.getValue("cmsId"));
    }
    if (json.getValue("dataUrl") instanceof String) {
      obj.setDataUrl((String)json.getValue("dataUrl"));
    }
    if (json.getValue("locked") instanceof Number) {
      obj.setLocked(((Number)json.getValue("locked")).intValue());
    }
    if (json.getValue("mzcid") instanceof String) {
      obj.setMzcid((String)json.getValue("mzcid"));
    }
    if (json.getValue("name") instanceof String) {
      obj.setName((String)json.getValue("name"));
    }
    if (json.getValue("pageid") instanceof String) {
      obj.setPageid((String)json.getValue("pageid"));
    }
    if (json.getValue("pic") instanceof String) {
      obj.setPic((String)json.getValue("pic"));
    }
    if (json.getValue("pic1") instanceof String) {
      obj.setPic1((String)json.getValue("pic1"));
    }
    if (json.getValue("pic2") instanceof String) {
      obj.setPic2((String)json.getValue("pic2"));
    }
    if (json.getValue("skipType") instanceof Number) {
      obj.setSkipType(((Number)json.getValue("skipType")).intValue());
    }
    if (json.getValue("type") instanceof String) {
      obj.setType((String)json.getValue("type"));
    }
    if (json.getValue("url") instanceof String) {
      obj.setUrl((String)json.getValue("url"));
    }
  }

  public static void toJson(ChannelInfo obj, JsonObject json) {
    if (obj.getAddOnId() != null) {
      json.put("addOnId", obj.getAddOnId());
    }
    if (obj.getCmsId() != null) {
      json.put("cmsId", obj.getCmsId());
    }
    if (obj.getDataUrl() != null) {
      json.put("dataUrl", obj.getDataUrl());
    }
    if (obj.getLocked() != null) {
      json.put("locked", obj.getLocked());
    }
    if (obj.getMzcid() != null) {
      json.put("mzcid", obj.getMzcid());
    }
    if (obj.getName() != null) {
      json.put("name", obj.getName());
    }
    if (obj.getPageid() != null) {
      json.put("pageid", obj.getPageid());
    }
    if (obj.getPic() != null) {
      json.put("pic", obj.getPic());
    }
    if (obj.getPic1() != null) {
      json.put("pic1", obj.getPic1());
    }
    if (obj.getPic2() != null) {
      json.put("pic2", obj.getPic2());
    }
    if (obj.getSkipType() != null) {
      json.put("skipType", obj.getSkipType());
    }
    if (obj.getType() != null) {
      json.put("type", obj.getType());
    }
    if (obj.getUrl() != null) {
      json.put("url", obj.getUrl());
    }
  }
}