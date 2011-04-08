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

import java.io.IOException;
import javax.xml.xpath.XPathExpressionException;
import org.w3c.dom.Document;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.xml.parsers.ParserConfigurationException;
import org.junit.Test;
import static org.junit.Assert.*;
import org.xml.sax.SAXException;

/**
 * Record level tests
 * @author marc
 */
public class RecordResourceTest extends HDataTest {

  /**
   * Tests for the root section feed
   */
  @Test
  public void testRootFeed() throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
    WebResource webResource = resource();
    ClientResponse response = webResource.path("/12345").get(ClientResponse.class);
    String entity = checkResponse(response, 200, MediaType.APPLICATION_ATOM_XML_TYPE);
    Document doc = buildDocument(entity);
    String title = getXpath().evaluate("/atom:feed/atom:title", doc);
    assertEquals("Root", title);
    String sections = getXpath().evaluate("count(/atom:feed/atom:entry[atom:category/@term='http://example.com/hdata/ext1'])", doc);
    assertEquals("1", sections);
  }

  /**
   * Tests for the root.xml.
   */
  @Test
  public void testRootXML() throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
    WebResource webResource = resource();
    ClientResponse response = webResource.path("/12345/root.xml").get(ClientResponse.class);
    String entity = checkResponse(response, 200, MediaType.APPLICATION_XML_TYPE);
    Document doc = buildDocument(entity);
    String version = getXpath().evaluate("/hd:root/hd:version", doc);
    assertEquals("1", version);
    String sections = getXpath().evaluate("count(/hd:root/hd:sections/hd:section)", doc);
    assertEquals("1", sections);
    System.out.println("CHILD: " + getXpath().evaluate("count(/hd:root/hd:sections/hd:section/hd:section)", doc));

    String extensions = getXpath().evaluate("count(/hd:root/hd:extensions/hd:extension)", doc);
    assertEquals("2", extensions);
  }

  @Test
  public void testCreateSection() {
    WebResource res = resource().path("/1234");
    MultivaluedMap body = new MultivaluedMapImpl();
    body.add("extensionId", "http://example.com/hdata/ext1");
    body.add("path", "bar");
    body.add("name", "A new section");

    checkPost(res, 201, body);

    body.putSingle("path", "foo"); //Duplicate
    checkPost(res, 409, body);

    checkPost(res, 400, new MultivaluedMapImpl());
  }

}
