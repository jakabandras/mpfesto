package com.andrewsoft.mpfesto;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.*;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlToPdf {

  DocumentBuilder dBuilder;

  Document        doc;

  // AssetManager am = getAssets();

  public XmlToPdf( String template ) {

    // TODO Auto-generated constructor stub
    try {
      dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
      doc = dBuilder.parse(new File(template));
      NodeList nodes = doc.getChildNodes();
      parseNodes(nodes);
    }
    catch (ParserConfigurationException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    catch (SAXException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  private void parseNodes(NodeList nodes) {

    // TODO Auto-generated method stub

  }

}
