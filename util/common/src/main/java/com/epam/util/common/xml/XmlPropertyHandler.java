/*
 * ******************************************************************************
 *  *
 *  * Pentaho Big Data
 *  *
 *  * Copyright (C) 2002-2018 by Hitachi Vantara : http://www.pentaho.com
 *  *
 *  *******************************************************************************
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with
 *  * the License. You may obtain a copy of the License at
 *  *
 *  *    http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *  *
 *  *****************************************************************************
 */

package com.epam.util.common.xml;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

public class XmlPropertyHandler {

  private final static Logger logger = Logger.getLogger( XmlPropertyHandler.class );

  public static String readXmlPropertyValue( String pathToFile, String property ) {
    // Read *-site.xml file and return property value, return null if property was not found
    try {
      NodeList list = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse( pathToFile )
        .getElementsByTagName( "property" );
      for ( int i = 0; i < list.getLength(); i++ ) {
        Node node = list.item( i );

        int itemNumber = findPropertyIndex( node );
        if ( itemNumber != -1 && property
          .equals( node.getChildNodes().item( itemNumber ).getFirstChild().getNodeValue() ) ) {
          return node.getChildNodes().item( itemNumber + 2 ).getFirstChild().getNodeValue();
        }
      }
    } catch ( ParserConfigurationException | IOException | SAXException pce ) {
      logger.error( pce );
    }

    return null;
  }

  public static void addPropertyToFile( String pathToFile, String name, String value ) {
    try {
      DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
      Document doc = docBuilder.parse( pathToFile );

      Node configuration = doc.getElementsByTagName( "configuration" ).item( 0 );
      configuration.appendChild( createProperty( doc, name, value ) );

      // write the content into xml file
      Transformer transformer = TransformerFactory.newInstance().newTransformer();
      transformer.setOutputProperty( OutputKeys.INDENT, "yes" );
      DOMSource source = new DOMSource( doc );
      StreamResult result = new StreamResult( new File( pathToFile ) );
      transformer.transform( source, result );
      logger.info( "Add property - " + name + " value -  " + value + " . To file - " + pathToFile );
    } catch ( ParserConfigurationException | TransformerException | IOException | SAXException pce ) {
      logger.error( pce.getMessage() );
    }
  }

  public static void modifyPropertyInFile( String pathToFile, String name, String value ) {
    try {
      Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse( pathToFile );
      NodeList list = doc.getElementsByTagName( "property" );
      for ( int i = 0; i < list.getLength(); i++ ) {
        Node node = list.item( i );

        int itemNumber = findPropertyIndex( node );
        if ( itemNumber != -1 && name
          .equals( node.getChildNodes().item( itemNumber ).getFirstChild().getNodeValue() ) ) {
          node.getChildNodes().item( itemNumber + 2 ).getFirstChild().setNodeValue( value );
        }
      }

      Transformer transformer = TransformerFactory.newInstance().newTransformer();
      transformer.setOutputProperty( OutputKeys.INDENT, "yes" );
      DOMSource source = new DOMSource( doc );
      StreamResult result = new StreamResult( new File( pathToFile ) );
      transformer.transform( source, result );
      logger.info( "Modify property - " + name + " value -  " + value + " . To file - " + pathToFile );
    } catch ( ParserConfigurationException | IOException | SAXException | TransformerException pce ) {
      logger.error( pce );
    }
  }

  public static void deletePropertyInFile( String pathToFile, String name ) {
    try {
      Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse( pathToFile );
      NodeList list = doc.getElementsByTagName( "property" );
      for ( int i = 0; i < list.getLength(); i++ ) {
        Node node = list.item( i );

        int itemNumber = findPropertyIndex( node );
        if ( itemNumber != -1 && name
          .equals( node.getChildNodes().item( itemNumber ).getFirstChild().getNodeValue() ) ) {
          doc.getElementsByTagName( "configuration" ).item( 0 ).removeChild( node );
        }
      }

      Transformer transformer = TransformerFactory.newInstance().newTransformer();
      transformer.setOutputProperty( OutputKeys.INDENT, "yes" );
      DOMSource source = new DOMSource( doc );
      StreamResult result = new StreamResult( new File( pathToFile ) );
      transformer.transform( source, result );
      logger.info( "Remove property - " + name + " . From file - " + pathToFile );
    } catch ( ParserConfigurationException | IOException | SAXException | TransformerException pce ) {
      logger.error( pce );
    }
  }

  private static Node createProperty( Document doc, String name, String value ) {
    Element property = doc.createElement( "property" );
    property.appendChild( createPropertyElements( doc, property, "name", name ) );
    property.appendChild( createPropertyElements( doc, property, "value", value ) );
    return property;
  }

  // utility method to create text node
  private static Node createPropertyElements( Document doc, Element element, String name, String value ) {
    Element node = doc.createElement( name );
    node.appendChild( doc.createTextNode( value ) );
    return node;
  }

  private static int findPropertyIndex( Node node ) {
    int i = 0;
    boolean find = false;
    while ( !find && i < node.getChildNodes().getLength() ) {
      if ( node.getChildNodes().item( i ).getFirstChild() != null ) {
        find = true;
      } else {
        i++;
      }
    }

    if ( !find ) {
      i = -1;
    }

    return i;
  }
}
