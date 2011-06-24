/*
 *    Copyright 2009 The MITRE Corporation
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.projecthdata.javahstore;

import org.projecthdata.javahstore.hdr.Extension;

class DummyExtensionImpl implements Extension {

  String id;
  String contentType;
  String url;
  
  public DummyExtensionImpl(String id, String contentType, String url) {
    this.id = id;
    this.contentType = contentType;
    this.url = url;
  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  public String getContentType() {
    return contentType;
  }

    @Override
    public String getUrl() {
        return url;
    }
}
