/*
 *
 * XhtmlDocument.java
 * Copyright (c) 2004 Torbj�rn Gannholm
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.	See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 *
 */

package net.homelinux.tobe;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.apache.xpath.XPathAPI;

import org.joshy.html.css.DefaultCSSMarker;

/**
 * Handles a general XML document
 *
 * @author  Torbj�rn Gannholm
 */
public class NoNamespaceHandler implements net.homelinux.tobe.renderer.NamespaceHandler {
    
    static final String _namespace = "http://www.w3.org/XML/1998/namespace";
        
    public String getNamespace() {
        return _namespace;
    }

    public String getClass(org.w3c.dom.Element e) {
System.err.println("NoNamespace class!");
        return null;
    }
    
    public String getElementStyling(org.w3c.dom.Element e) {
         return null;
    }
    
    public String getID(org.w3c.dom.Element e) {
        return null;
    }
    
    public String getLang(org.w3c.dom.Element e) {
        return e.getAttributeNS(_namespace, "lang");
    }

    public String getDocumentTitle(org.w3c.dom.Document doc) {
        return null;
    }    
    
    public String getInlineStyle(org.w3c.dom.Document doc) {
        return null;
    }
    
    public java.net.URI[] getStylesheetURIs(org.w3c.dom.Document doc) {
        java.util.List list = new java.util.ArrayList();
        //get the processing-instructions (actually for XmlDocuments)
        try {
            org.w3c.dom.NodeList nl = XPathAPI.selectNodeList(doc.getDocumentElement(), "//processing-instruction('xml-stylesheet')");
            for ( int i=0, len=nl.getLength(); i < len; i++ ) {
                org.w3c.dom.Node piNode = nl.item(i);
                String pi = piNode.getNodeValue();
                String s = pi.substring(pi.indexOf("type=")+5);
                String type = s.substring(1, s.indexOf(s.charAt(0),1));
                if(type.equals("text/css")) {
                    s = pi.substring(pi.indexOf("href=")+5);
                    String href = s.substring(1, s.indexOf(s.charAt(0),1));
                list.add(new java.net.URI(href));
                }
            }
        } catch ( Exception ex ) {
            ex.printStackTrace();   
        }
        
        java.net.URI[] uris = new java.net.URI[list.size()];
        for(int i=0; i<uris.length; i++) {
            uris[i] = (java.net.URI) list.get(i);
        }
        return uris;
    }
    
    public java.io.Reader getDefaultStylesheet() {
        
        return null;

    }
    
}
