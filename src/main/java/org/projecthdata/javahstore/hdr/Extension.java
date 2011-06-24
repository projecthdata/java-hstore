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
package org.projecthdata.javahstore.hdr;

/**
 * Represents an extension. Each {@link Section} is associated with an
 * extension and there may be multiple sections of the same extension type.
 * @author marc
 */
public interface Extension {

  /**
   * The extension identifier
   * @return
   */
  String getId();

  /**
   * The default content type for document in sections of this type
   * @return
   */
  String getContentType();
  
    /**
   * The url of the XML schema (for application/xml content types) or a RDDL
   * document
   * @return
   */
  String getUrl();
}
