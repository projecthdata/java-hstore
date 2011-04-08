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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.projecthdata.javahstore.hdr.Extension;
import org.projecthdata.javahstore.hdr.RootDocument;
import org.projecthdata.javahstore.hdr.Section;

public class DummyRootDocumentImpl implements RootDocument {

  String id;
  Date created, lastModified;
  Map<String, Extension> extensions;
  Collection<Section> rootSections;

  public DummyRootDocumentImpl() {
    id = "12345";
    created = new Date();
    lastModified = new Date();
    extensions = new HashMap<String,Extension>();
    rootSections = new ArrayList<Section>();
    final String extId1 = "http://example.com/hdata/ext1";
    Extension ext1 = new DummyExtensionImpl(extId1, "application/foobar+xml");
    final String extId2 = "http://example.com/hdata/ext2";
    Extension ext2 = new DummyExtensionImpl(extId2, "application/foobarbaz+xml");
    extensions.put(extId1, ext1);
    extensions.put(extId2, ext2);
    Section section = new DummySectionImpl(ext1);
    section.getChildSections().add(new DummySectionImpl(ext2, "baz", "Subsection"));
    section.getChildDocuments().add(new DummySectionDocumentImpl());
    this.rootSections.add(section);
  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  public Date getCreated() {
    return created;
  }

  @Override
  public Date getLastModified() {
    return lastModified;
  }

  @Override
  public Collection<Extension> getExtensions() {
    return extensions.values();
  }

  @Override
  public Extension getExtension(String id) {
    Extension e = extensions.get(id);
    if (e==null)
      throw new IllegalArgumentException("Unknown extension ID: "+id);
    return e;
  }

  @Override
  public Collection<Section> getRootSections() {
   return rootSections;
  }

  @Override
  public void createChildSection(Extension e, String path, String name) {
   this.rootSections.add(new DummySectionImpl(e, path, name));
  }

  @Override
  public Section getChildSection(String segment) {
    for (Section sec : rootSections) {
      if (sec.getPath().equals(segment)) {
        return sec;
      }
    }
    return null;
  }

}
