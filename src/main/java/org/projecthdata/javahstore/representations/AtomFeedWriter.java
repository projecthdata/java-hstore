/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.projecthdata.javahstore.representations;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.MessageBodyWriter;
import org.apache.abdera.Abdera;
import org.apache.abdera.model.Feed;

/**
 *
 * @author ssayer
 */
public abstract class AtomFeedWriter<T> implements MessageBodyWriter<T> {

  // Hacky way to inspect class of T at runtime @ssayer
  
  private static Abdera abdera = null;
  private Feed feed;
  @Context UriInfo uriInfo;

  public static synchronized Abdera getAbdera() {
    if (abdera == null)
      abdera = new Abdera();
    return abdera;
  }

  public AtomFeedWriter() {
     this.feed = getAbdera().newFeed();
  }

  @Override
  public long getSize(T t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
    return -1;
  }

  @Override
  public void writeTo(T t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException{
    buildFeed(feed, t);
    feed.setId(uriInfo.getRequestUri().toString());
    feed.addLink(uriInfo.getRequestUri().toString(), "self");
    feed.writeTo(entityStream);
  }

  public abstract void buildFeed(Feed feed, T entity);
}
