/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.projecthdata.javahstore;

import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.test.framework.JerseyTest;
import java.io.ByteArrayInputStream;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import static org.junit.Assert.*;
import org.xml.sax.SAXException;


/**
 *
 * @author ssayer
 */
public class HDataTest extends JerseyTest {
  private XPath xp;
  private DocumentBuilder db;

  protected XPath getXpath() {
    return xp;
  }

  protected DocumentBuilder getDocBuilder() {
    return db;
  }

  public HDataTest() {
    super("org.projecthdata.javahstore.resources;org.projecthdata.javahstore.representations");
    XPathFactory xpf = XPathFactory.newInstance();
    xp = xpf.newXPath();
    xp.setNamespaceContext(new DummyNamespaceContext());
    DocumentBuilderFactory dbf; dbf = DocumentBuilderFactory.newInstance();
    dbf.setNamespaceAware(true);
    try {
      db = dbf.newDocumentBuilder();
    } catch (ParserConfigurationException ex) {
      fail(ex.getMessage());
    }
  }


  protected String checkResponse(ClientResponse response, int expectedStatus) {
    return checkResponse(response, expectedStatus, null);
  }

  protected String checkResponse(ClientResponse response, int expectedStatus,
          MediaType expectedMediaType) {
    assertEquals(expectedStatus, response.getStatus());
    if (expectedMediaType!=null)
      assertEquals(expectedMediaType, response.getType());
    return response.getEntity(String.class);
  }

  protected void checkPost(WebResource resource, int expectedStatus, MultivaluedMap body) {
    ClientResponse res = resource.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE).post(ClientResponse.class, body);
    checkResponse(res, expectedStatus);
  }

  protected Document buildDocument(String entity) throws SAXException, IOException {
    return db.parse(new ByteArrayInputStream(entity.getBytes()));
  }

  protected static class DummyNamespaceContext implements NamespaceContext {

    @Override
    public String getNamespaceURI(String prefix) {
      if (prefix.equals("hd") || prefix.equals(XMLConstants.DEFAULT_NS_PREFIX))
        return "http://projecthdata.org/hdata/schemas/2009/06/core";
      else if (prefix.equals("hdm"))
        return "http://projecthdata.org/hdata/schemas/2009/11/metadata";
      else if (prefix.equals("atom"))
        return "http://www.w3.org/2005/Atom";
      else if (prefix.equals(XMLConstants.XML_NS_PREFIX))
        return XMLConstants.XML_NS_URI;
      else if (prefix.equals(XMLConstants.XMLNS_ATTRIBUTE))
        return XMLConstants.XMLNS_ATTRIBUTE_NS_URI;
      else
        return XMLConstants.NULL_NS_URI;
    }

    @Override
    public String getPrefix(String namespaceURI) {
      throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Iterator getPrefixes(String namespaceURI) {
      throw new UnsupportedOperationException("Not supported yet.");
    }

  }
}
