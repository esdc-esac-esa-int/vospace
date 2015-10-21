package esavo.vospace.xml;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.URL;
import java.util.List;

import org.apache.log4j.Logger;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.filter.Filters;
import org.jdom2.input.SAXBuilder;
import org.jdom2.input.sax.XMLReaderXSDFactory;
import org.jdom2.input.sax.XMLReaders;
import org.jdom2.output.XMLOutputter;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;

/**
 * @author Inaki Ortiz de Landaluce Copyright (c) 2013- European Space Agency
 * 
 */
public class XMLUtils {

  private static Logger log = Logger.getLogger(XMLUtils.class);

  public static Attribute evaluateXPathFirstAttribute(String xPath, Element root,
      Namespace ns) {
    log.debug("XPath " + xPath);
    XPathExpression<Attribute> expression = XPathFactory.instance().compile(
        xPath, Filters.attribute(), null, ns);
    return expression.evaluateFirst(root);
  }

  public static Element evaluateXPathFirstElement(String xPath, Element root,
      Namespace ns) {
    log.debug("XPath " + xPath);
    XPathExpression<Element> expression = XPathFactory.instance().compile(
        xPath, Filters.element(), null, ns);
    return expression.evaluateFirst(root);
  }

  public static List<Element> evaluateXPathElements(String xPath, Element root,
      Namespace ns) {
    log.debug("XPath " + xPath);
    XPathExpression<Element> expression = XPathFactory.instance().compile(
        xPath, Filters.element(), null, ns);
    return expression.evaluate(root);
  }

  public static String getAttributeValue(Element element, String attname,
      Namespace ns, boolean qualified) {
    String value = element.getAttributeValue(attname, ns);
    if (value != null) {
      if (!qualified) {
        String[] values = value.split(":");
        if (values.length > 1)
          value = values[1];
      }
    }
    return value;
  }

  public static String qualify(String name,Namespace ns) {
    return ns.getPrefix() + ":" + name;
  }   
  
  public static Document read(InputStream in, URL... schema)
          throws XMLParsingException {
        Document doc;
        try {
          XMLReaderXSDFactory xsdReader = new XMLReaderXSDFactory(schema);
          doc = new SAXBuilder(xsdReader).build(in);
        } catch (Exception e) {
          throw new XMLParsingException(e);
        }
        return doc;
      }

  public static Document read(String in, URL... schema)
          throws XMLParsingException {
        Document doc;
        try {
          XMLReaderXSDFactory xsdReader = new XMLReaderXSDFactory(schema);
          doc = new SAXBuilder(xsdReader).build(new StringReader(in));
        } catch (Exception e) {
          throw new XMLParsingException(e);
        }
        return doc;
      }

  public static Document read(InputStream in, boolean schemaValidation)
      throws XMLParsingException {
    if (schemaValidation) {
      return read(in, XMLReaders.XSDVALIDATING);
    } else {
      return read(in, XMLReaders.NONVALIDATING);
    }
  }

  public static Document read(InputStream in, XMLReaders reader)
      throws XMLParsingException {
    Document doc;
    try {
      doc = new SAXBuilder(reader).build(in);
    } catch (Exception e) {
      throw new XMLParsingException(e);
    }
    return doc;
  }

  public static void write(Element element, OutputStream out)
      throws XMLWritingException {
    XMLOutputter outputter = new XMLOutputter();
    try {
      outputter.output(element, out);
    } catch (IOException e) {
      throw new XMLWritingException(e);
    }
  }

  public static void write(Document doc, OutputStream out)
      throws XMLWritingException {
    XMLOutputter outputter = new XMLOutputter();
    try {
      outputter.output(doc, out);
    } catch (IOException e) {
      throw new XMLWritingException(e);
    }
  }
}