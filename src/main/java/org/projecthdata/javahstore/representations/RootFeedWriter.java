/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
import org.projecthdata.javahstore.hdr.HDR;
import org.projecthdata.javahstore.hdr.Section;

/**
 *
 * @author ssayer
 */
@Provider
@Produces(MediaType.APPLICATION_ATOM_XML)
public class RootFeedWriter extends AtomFeedWriter<HDR> {

  @Override
  public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
    return HDR.class.isAssignableFrom(type);
  }

  @Override
  public void buildFeed(Feed feed, HDR entity) {
    feed.setTitle("Root");
    for (Section section: entity.getRootDocument().getRootSections()) {
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


  }

}
