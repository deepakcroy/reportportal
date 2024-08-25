package net.aditri.util;

import java.io.StringReader;
import java.util.AbstractList;
import java.util.Collections;
import java.util.List;
import java.util.RandomAccess;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public final class XmlUtil {
  private XmlUtil(){}

  public static List<Node> asList(NodeList n) {
    return n.getLength()==0?
      Collections.<Node>emptyList(): new NodeListWrapper(n);
  }
  static final class NodeListWrapper extends AbstractList<Node> implements RandomAccess {
    private final NodeList list;
    NodeListWrapper(NodeList l) {
      list=l;
    }
    public Node get(int index) {
      return list.item(index);
    }
    public int size() {
      return list.getLength();
    }
  }
  public static Document convertXMLDocument (String xmlString, String rootElement) throws Exception
  {
	  String finalXMLString="";
	  String tmpXMLString="";
	  tmpXMLString = xmlString.trim().replaceAll("\n", "").replaceAll("\t", "");
	  if(!tmpXMLString.startsWith("<?xml"))
		  finalXMLString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><!DOCTYPE xml>";
	  if(!rootElement.trim().equals(""))
		  finalXMLString = finalXMLString + "<" + rootElement + ">";
	  finalXMLString = finalXMLString+tmpXMLString;
	  if(!rootElement.trim().equals(""))
		  finalXMLString = finalXMLString + "</" + rootElement + ">";
	  DocumentBuilderFactory oDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
	  DocumentBuilder oDocumentBuilder = oDocumentBuilderFactory.newDocumentBuilder();
	  InputSource oInputSource = new InputSource(new StringReader(finalXMLString));
	  return oDocumentBuilder.parse(oInputSource);
  }
}