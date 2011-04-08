/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.projecthdata.javahstore;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import javax.ws.rs.core.MultivaluedMap;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ssayer
 */
public class SectionResourceTest extends HDataTest {

  @Test
  public void testCreateSubsection() {
    WebResource resource = resource().path("/1234/foo");

    checkPost(resource, 400, null);

    MultivaluedMap body = new MultivaluedMapImpl();
    body.add("extensionId", "http://example.com/hdata/ext1");
    body.add("path", "beep");
    body.add("name", "SubSection");

    checkPost(resource, 201, body);

    body.putSingle("path", "baz");

    checkPost(resource, 409, body);
  }

  @Test
  public void testGetSections() {
    WebResource resource = resource().path("/1234/foo");
    ClientResponse res = resource.get(ClientResponse.class);
    assertEquals(200, res.getStatus());
    resource = resource.path("baz");
    res = resource.get(ClientResponse.class);
    assertEquals(200, res.getStatus());
  }

}