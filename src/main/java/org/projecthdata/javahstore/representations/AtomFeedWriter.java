/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.projecthdata.javahstore.representations;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.net.URI;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.MessageBodyWriter;
import org.apache.abdera.Abdera;
import org.apache.abdera.model.Category;
import org.apache.abdera.model.Entry;
import org.apache.abdera.model.Feed;
import org.apache.abdera.model.Link;
import org.projecthdata.javahstore.hdr.Section;

/**
 *
 * @author ssayer
 */
public abstract class AtomFeedWriter<T> implements MessageBodyWriter<T> {

  // Hacky way to inspect class of T at runtime @ssayer
  
  private static Abdera abdera = null;
  @Context UriInfo uriInfo;

  public static synchronized Abdera getAbdera() {
    if (abdera == null)
      abdera = new Abdera();
    return abdera;
  }

  @Override
  public long getSize(T t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
    return -1;
  }

  @Override
  public void writeTo(T t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException{
    
    Feed feed = getAbdera().newFeed();
    buildFeed(feed, t);

    feed.writeTo(entityStream);
  }

  public void writeSection(Feed feed, Section section) {
      Entry entry = feed.addEntry();
      URI sectionUri = uriInfo.getAbsolutePathBuilder().path(section.getPath()).build();
      Link sectionLink = entry.addLink(sectionUri.toString());
      sectionLink.setMimeType(MediaType.APPLICATION_ATOM_XML);
      sectionLink.setRel("alternate");
      entry.setId(sectionUri.toString());
      entry.setTitle(sectionUri.getPath());
      if (section.getExtension() != null) {
        Category category = entry.addCategory(section.getExtension().getId());
        category.setScheme(Constants.HDATA_XML_NS);
      }
  }


  public abstract void buildFeed(Feed feed, T entity);
}
