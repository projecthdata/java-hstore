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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;
import org.projecthdata.javahstore.hdr.RootDocument;
import org.projecthdata.javahstore.hdr.Extension;
import org.projecthdata.javahstore.hdr.Section;

/**
 * JAXB classes for root.xml
 * @author marc
 */
@XmlRootElement(name="root")
public class RootXMLRepresentation {

  @XmlElement(name="id") String id;
  @XmlElement(name="version") final static String version = "1";

  public Date getCreated() {
    return created;
  }

  public List<ExtensionRepresentation> getExtensions() {
    return extensions;
  }

  public String getId() {
    return id;
  }

  public Date getLastModified() {
    return lastModified;
  }

  public List<SectionRepresentation> getSections() {
    return sections;
  }

  public static String getVersion() {
    return version;
  }
  @XmlElement(name="created") Date created;
  @XmlElement(name="lastModified") Date lastModified;
  @XmlElement(name="extension") @XmlElementWrapper(name="extensions")
  List<ExtensionRepresentation> extensions;
  @XmlElement(name="section") @XmlElementWrapper(name="sections")
  List<SectionRepresentation> sections;

  public RootXMLRepresentation() {} // keep JAXB happy

  public RootXMLRepresentation(RootDocument doc) {
    this.id = doc.getId();
    this.created = doc.getCreated();
    this.lastModified = doc.getLastModified();
    this.extensions = new ArrayList<ExtensionRepresentation>();
    for (Extension e: doc.getExtensions()) {
      this.extensions.add(new ExtensionRepresentation(e));
    }
    this.sections = new ArrayList<SectionRepresentation>();
    for (Section s: doc.getRootSections()) {
      this.sections.add(new SectionRepresentation(s));
    }
  }

  public static class SectionRepresentation {
    
    @XmlAttribute(name="path") String path;
    @XmlAttribute(name="extensionId") String extensionId;
    @XmlAttribute(name="name") String name;
    @XmlElement(name="section") List<SectionRepresentation> sections;
    
    public SectionRepresentation() {} // keep JAXB happy

    public SectionRepresentation(String path, String name, String extensionId) {
      this.path = path;
      this.name = name;
      this.extensionId = extensionId;
    }

    public SectionRepresentation(Section section) {
      path = section.getPath();
      extensionId=section.getExtension().getId();
      name=section.getName();
      this.sections = new ArrayList<SectionRepresentation>();
      for (Section sec : section.getChildSections()) {
        this.sections.add(new SectionRepresentation(sec));
      }
      
    }
   
    public String getExtensionId() {
      return extensionId;
    }

    public String getName() {
      return name;
    }

    public String getPath() {
      return path;
    }

  }

  public static class ExtensionRepresentation {

    @XmlValue String id;
    @XmlAttribute(name="contentType") String contentType;

    public ExtensionRepresentation() {} // keep JAXB happy

    public ExtensionRepresentation(String id, String contentType) {
      this.id = id;
      this.contentType = contentType;
    }

    public ExtensionRepresentation(Extension extension) {
      id = extension.getId();
      contentType = extension.getContentType();
    }

    public String getContentType() {
      return contentType;
    }

    public String getId() {
      return id;
    }

  }

}
