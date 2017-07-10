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
 * Converter for {@link cms.response.CmsPageTpResponseFrag}.
 *
 * NOTE: This class has been automatically generated from the {@link cms.response.CmsPageTpResponseFrag} original class using Vert.x codegen.
 */
public class CmsPageTpResponseFragConverter {

  public static void fromJson(JsonObject json, CmsPageTpResponseFrag obj) {
    if (json.getValue("blockContents") instanceof JsonArray) {
      java.util.ArrayList<cms.response.CmsBlockContentTpResponse> list = new java.util.ArrayList<>();
      json.getJsonArray("blockContents").forEach( item -> {
        if (item instanceof JsonObject)
          list.add(new cms.response.CmsBlockContentTpResponse((JsonObject)item));
      });
      obj.setBlockContents(list);
    }
    if (json.getValue("contentManulNum") instanceof Number) {
      obj.setContentManulNum(((Number)json.getValue("contentManulNum")).intValue());
    }
    if (json.getValue("contentName") instanceof String) {
      obj.setContentName((String)json.getValue("contentName"));
    }
    if (json.getValue("contentRid") instanceof String) {
      obj.setContentRid((String)json.getValue("contentRid"));
    }
    if (json.getValue("contentStyle") instanceof String) {
      obj.setContentStyle((String)json.getValue("contentStyle"));
    }
    if (json.getValue("contentType") instanceof Number) {
      obj.setContentType(((Number)json.getValue("contentType")).intValue());
    }
    if (json.getValue("redirectPageId") instanceof String) {
      obj.setRedirectPageId((String)json.getValue("redirectPageId"));
    }
    if (json.getValue("redirectSubPageId") instanceof String) {
      obj.setRedirectSubPageId((String)json.getValue("redirectSubPageId"));
    }
    if (json.getValue("redirectSubUrl") instanceof String) {
      obj.setRedirectSubUrl((String)json.getValue("redirectSubUrl"));
    }
    if (json.getValue("redirectUrl") instanceof String) {
      obj.setRedirectUrl((String)json.getValue("redirectUrl"));
    }
  }

  public static void toJson(CmsPageTpResponseFrag obj, JsonObject json) {
    if (obj.getBlockContents() != null) {
      JsonArray array = new JsonArray();
      obj.getBlockContents().forEach(item -> array.add(item.toJson()));
      json.put("blockContents", array);
    }
    if (obj.getContentManulNum() != null) {
      json.put("contentManulNum", obj.getContentManulNum());
    }
    if (obj.getContentName() != null) {
      json.put("contentName", obj.getContentName());
    }
    if (obj.getContentRid() != null) {
      json.put("contentRid", obj.getContentRid());
    }
    if (obj.getContentStyle() != null) {
      json.put("contentStyle", obj.getContentStyle());
    }
    if (obj.getContentType() != null) {
      json.put("contentType", obj.getContentType());
    }
    if (obj.getRedirectPageId() != null) {
      json.put("redirectPageId", obj.getRedirectPageId());
    }
    if (obj.getRedirectSubPageId() != null) {
      json.put("redirectSubPageId", obj.getRedirectSubPageId());
    }
    if (obj.getRedirectSubUrl() != null) {
      json.put("redirectSubUrl", obj.getRedirectSubUrl());
    }
    if (obj.getRedirectUrl() != null) {
      json.put("redirectUrl", obj.getRedirectUrl());
    }
  }
}