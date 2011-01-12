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
package org.projecthdata.javahstore.representations;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.net.URI;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;
import org.apache.abdera.model.Category;
import org.apache.abdera.model.Entry;
import org.apache.abdera.model.Feed;
import org.apache.abdera.model.Link;
import org.projecthdata.javahstore.hdr.Section;
import org.projecthdata.javahstore.hdr.SectionDocument;

/**
 * JAX-RS message body writer that renders an Atom feed of a section's
 * contents.
 * @author marc
 */
@Produces(MediaType.APPLICATION_ATOM_XML)
@Provider
public class SectionFeedWriter extends AtomFeedWriter<Section> {

  @Override
  public void buildFeed(Feed feed, Section t) {

    feed.setTitle(t.getName());
    for (Section section: t.getChildSections()) {
      Entry entry = feed.addEntry();
      URI sectionUri = uriInfo.getAbsolutePathBuilder().path(section.getPath()).build();
      Link sectionLink = entry.addLink(sectionUri.toString());
      sectionLink.setMimeType(MediaType.APPLICATION_ATOM_XML);
      sectionLink.setRel("alternate");
      entry.setId(sectionUri.toString());
      entry.setTitle(section.getName());
      if (section.getExtension() != null) {
        Category category = entry.addCategory(section.getExtension().getId());
        category.setScheme(Constants.HDATA_XML_NS);
      }
    }
    for (SectionDocument document: t.getChildDocuments()) {
      Entry entry = feed.addEntry();
      URI sectionUri = uriInfo.getAbsolutePathBuilder().path(document.getPath()).build();
      Link sectionLink = entry.addLink(sectionUri.toString());
      sectionLink.setMimeType(document.getMediaType());
      sectionLink.setRel("alternate");
      entry.setEdited(document.getLastUpdated());
      entry.setUpdated(document.getLastUpdated());
      entry.setId(sectionUri.toString());
      entry.setTitle(document.getPath());
      if (document.getMetadata() != null) {
        entry.addAuthor(document.getMetadata().getAuthor());
        entry.setContent(document.getMetadata().getXml(), MediaType.APPLICATION_XML);
      }
    }
  }

  @Override
  public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
    return Section.class.isAssignableFrom(type);
  }

}
