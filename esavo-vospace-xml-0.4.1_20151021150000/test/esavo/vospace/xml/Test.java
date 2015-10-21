package esavo.vospace.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.filter.Filters;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;

import esavo.vospace.common.model.transferobjects.ContainerNodeTO;
import esavo.vospace.common.model.transferobjects.DataNodeTO;
import esavo.vospace.common.model.transferobjects.LinkNodeTO;
import esavo.vospace.common.model.transferobjects.NodePropertyTO;
import esavo.vospace.common.model.transferobjects.NodeTO;
import esavo.vospace.common.model.transferobjects.SearchTO;
import esavo.vospace.common.model.transferobjects.StructuredDataNodeTO;
import esavo.vospace.common.model.transferobjects.TransferTO;
import esavo.vospace.common.model.transferobjects.TransferTO.Direction;
import esavo.vospace.common.model.transferobjects.UnstructuredDataNodeTO;
import esavo.vospace.common.model.transferobjects.VOSpaceURI;
import esavo.vospace.common.model.transferobjects.VoCapabilityTO;
import esavo.vospace.common.model.transferobjects.VoPropertyTO;
import esavo.vospace.common.model.transferobjects.VoProtocolTO;
import esavo.vospace.common.model.transferobjects.VoViewTO;
/*import esavo.vospace.model.Capability;
import esavo.vospace.model.ContainerNode;
import esavo.vospace.model.DataNode;
import esavo.vospace.model.LinkNode;
import esavo.vospace.model.Node;
import esavo.vospace.model.Property;
import esavo.vospace.model.Protocol;
import esavo.vospace.model.Search;
import esavo.vospace.model.StructuredDataNode;
import esavo.vospace.model.Transfer;
import esavo.vospace.model.UnstructuredDataNode;
import esavo.vospace.model.VOSpaceURI;
import esavo.vospace.model.View;
import esavo.vospace.model.Transfer.Direction;*/
import esavo.vospace.xml.VOSpaceNS;
import esavo.vospace.xml.XMLReader;
import esavo.vospace.xml.XMLWriter;

/**
 * @author Inaki Ortiz de Landaluce Copyright (c) 2013- European Space Agency
 * 
 */
public class Test {

  private static Logger log = Logger.getLogger(Test.class);

  public static List<NodePropertyTO> createProperties1() {
    List<NodePropertyTO> props = new ArrayList<NodePropertyTO>();
    NodePropertyTO p1 = new NodePropertyTO();
    p1.getVoPropertyTO().setURI("ivo://ivoa.net/vospace/core#title");
    NodePropertyTO p2 = new NodePropertyTO();
    p2.getVoPropertyTO().setURI("ivo://ivoa.net/vospace/core#creator");
    NodePropertyTO p3 = new NodePropertyTO();
    p3.getVoPropertyTO().setURI("ivo://ivoa.net/vospace/core#description");
    props.add(p1);
    props.add(p2);
    props.add(p3);
    return props;
  }
  
  public static List<NodePropertyTO> createProperties2() {
    List<NodePropertyTO> props = new ArrayList<NodePropertyTO>();
    NodePropertyTO p0 = new NodePropertyTO();
    p0.getVoPropertyTO().setURI("ivo://ivoa.net/vospace/core#availableSpace");
    NodePropertyTO p1 = new NodePropertyTO();
    p1.getVoPropertyTO().setURI("ivo://ivoa.net/vospace/core#httpput");
    props.add(p0);
    props.add(p1);
    return props;
  }

  public static List<NodePropertyTO> createProperties3() {
    List<NodePropertyTO> props = new ArrayList<NodePropertyTO>();
    NodePropertyTO p0 = new NodePropertyTO();
    p0.getVoPropertyTO().setURI("ivo://ivoa.net/vospace/core#availableSpace");    
    NodePropertyTO p1 = new NodePropertyTO();
    p1.getVoPropertyTO().setURI("ivo://ivoa.net/vospace/core#title");
    NodePropertyTO p2 = new NodePropertyTO();
    p2.getVoPropertyTO().setURI("ivo://ivoa.net/vospace/core#creator");
    NodePropertyTO p3 = new NodePropertyTO();
    p3.getVoPropertyTO().setURI("ivo://ivoa.net/vospace/core#description");
    props.add(p0);
    props.add(p1);
    props.add(p2);
    props.add(p3);
    return props;
  }
  
  public static List<VoProtocolTO> createProtocols1() {
    List<VoProtocolTO> protocols = new ArrayList<VoProtocolTO>();
    VoProtocolTO p0 = new VoProtocolTO();
    p0.setURI(new VOSpaceURI("ivo://ivoa.net/vospace/core#httpget"));    
    VoProtocolTO p1 = new VoProtocolTO();
    p1.setURI(new VOSpaceURI("ivo://ivoa.net/vospace/core#httpput"));
    protocols.add(p0);
    protocols.add(p1);
    return protocols;
  }
  
  public static List<VoViewTO> createViews1() {
    List<VoViewTO> views = new ArrayList<VoViewTO>();
    VoViewTO v0 = new VoViewTO();
    v0.setURI(new VOSpaceURI("ivo://ivoa.net/vospace/core#anyView"));    
    views.add(v0);
    return views;    
  }

  public static List<VoViewTO> createViews2() {
    List<VoViewTO> views = new ArrayList<VoViewTO>();
    VoViewTO v0 = new VoViewTO();
    v0.setURI(new VOSpaceURI("ivo://ivoa.net/vospace/core#defaultView"));    
    views.add(v0);
    return views;    
  }
  

  public static NodeTO createUnstructuredNode() {
    DataNodeTO node = new UnstructuredDataNodeTO();
    try {
      NodePropertyTO property1 = new NodePropertyTO();
      property1.getVoPropertyTO().setURI("ivo://ivoa.net/vospace/core#mimetype");
      property1.setValue("text/xml");

      NodePropertyTO property2 = new NodePropertyTO();
      property2
          .getVoPropertyTO().setURI("ivo://ivoa.net/vospace/core#description");
      property2.setValue("My results");

      List<NodePropertyTO> properties = new ArrayList<NodePropertyTO>();
      properties.add(property1);
      properties.add(property2);
      node.setNodeProperties(properties);
      
      VoCapabilityTO capability1 = new VoCapabilityTO();
      capability1.setURI(new VOSpaceURI("ivo://ivoa.net/vospace/core#vospace-2.0"));
      
      List<VoViewTO> accepts = new ArrayList<VoViewTO>();
      VoViewTO view = new VoViewTO();
      view.setURI(new VOSpaceURI("ivo://ivoa.net/vospace/core#anyview"));
      accepts.add(view);
      
      node.setAcceptViews(accepts);

      node.setUri(new VOSpaceURI("esavo!vospace", "/mydata1"));

      return node;
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return node;
  }

  public static NodeTO createStructuredNode() {
    StructuredDataNodeTO node = new StructuredDataNodeTO();
    try {
      NodePropertyTO property1 = new NodePropertyTO();
      property1.getVoPropertyTO().setURI("ivo://ivoa.net/vospace/core#mimetype");
      property1.setValue("text/xml");

      NodePropertyTO property2 = new NodePropertyTO();
      property2
          .getVoPropertyTO().setURI("ivo://ivoa.net/vospace/core#description");
      property2.setValue("This is a Test node");

      List<NodePropertyTO> properties = new ArrayList<NodePropertyTO>();
      properties.add(property1);
      properties.add(property2);
      node.setNodeProperties(properties);

      node.setUri(new VOSpaceURI("esavo!vospace", "/mytable"));

      return node;
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return node;
  }

  public static NodeTO createContainerNode() {
    ContainerNodeTO node = new ContainerNodeTO();
    try {
      NodePropertyTO property1 = new NodePropertyTO();
      property1.getVoPropertyTO().setURI("ivo://ivoa.net/vospace/core#mimetype");
      property1.setValue("text/xml");

      NodePropertyTO property2 = new NodePropertyTO();
      property2
          .getVoPropertyTO().setURI("ivo://ivoa.net/vospace/core#description");
      property2.setValue("This is a Test node");

      
      List<NodePropertyTO> properties = new ArrayList<NodePropertyTO>();
      properties.add(property1);
      properties.add(property2);
      node.setNodeProperties(properties);

      node.setUri(new VOSpaceURI("esavo!vospace", "/snieto/ContainerTest"));

      List<NodeTO> nodes = new ArrayList<NodeTO>();
      //nodes.add(createStructuredNode());
      //nodes.add(createUnstructuredNode());
      //nodes.add(createLinkNode());
      
      node.setNodeList(nodes);
      return node;
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return node;
  }
  
  public static NodeTO createLinkNode() {
    LinkNodeTO node = new LinkNodeTO();
    try {
      NodePropertyTO property1 = new NodePropertyTO();
      property1.getVoPropertyTO().setURI("ivo://ivoa.net/vospace/core#mimetype");
      property1.setValue("text/xml");

      NodePropertyTO property2 = new NodePropertyTO();
      property2
          .getVoPropertyTO().setURI("ivo://ivoa.net/vospace/core#description");
      property2.setValue("This is a Test node");

      List<NodePropertyTO> properties = new ArrayList<NodePropertyTO>();
      properties.add(property1);
      properties.add(property2);
      node.setNodeProperties(properties);

      node.setUri(new VOSpaceURI("esavo!vospace", "/mylink"));
      node.setTarget(new VOSpaceURI("esavo!vospace", "/mytable"));
      
      return node;
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return node;
  }
  
  public static File writeGetProperties(List<VoPropertyTO> accepts,
      List<VoPropertyTO> provides, List<VoPropertyTO> contains) {
    File f = new File("/home/iortiz/properties.xml");
    if (f.exists())
      f.delete();
    try {
      XMLWriter.writeGetProperties(accepts, provides, contains, f);
      log.info("File " + f.toString() + " created");
    } catch (Exception e) {
      e.printStackTrace();
    }
    return f;
  }

  public static File writeGetProtocols(List<VoProtocolTO> accepts,
      List<VoProtocolTO> provides) {
    File f = new File("/home/iortiz/protocols.xml");
    if (f.exists())
      f.delete();
    try {
      XMLWriter.writeGetProtocols(accepts, provides, f);
      log.info("File " + f.toString() + " created");
    } catch (Exception e) {
      e.printStackTrace();
    }
    return f;
  }

  public static File writeGetViews(List<VoViewTO> accepts,
      List<VoViewTO> provides) {
    File f = new File("/home/iortiz/views.xml");
    if (f.exists())
      f.delete();
    try {
      XMLWriter.writeGetViews(accepts, provides, f);
      log.info("File " + f.toString() + " created");
    } catch (Exception e) {
      e.printStackTrace();
    }
    return f;
  }

  public static File writeNode(NodeTO node) {
    File f = new File("/home/snieto/link.xml");
    if (f.exists())
      f.delete();
    try {
      XMLWriter.writeNode(node, f);
      log.info("File " + f.toString() + " created");
    } catch (Exception e) {
      e.printStackTrace();
    }
    return f;
  }

  public static File writeSearch(SearchTO search) {
    File f = new File("/home/snieto/search.xml");
    if (f.exists())
      f.delete();
    try {
      XMLWriter.writeSearch(search, f);
      log.info("File " + f.toString() + " created");
    } catch (Exception e) {
      e.printStackTrace();
    }
    return f;
  }

  public static File writeTransfer(TransferTO transfer) {
    File f = new File("/home/snieto/Desktop/test/transfer.xml");
    if (f.exists())
      f.delete();
    try {
      XMLWriter.writeTransfer(transfer, f);
      log.info("File " + f.toString() + " created");
    } catch (Exception e) {
      e.printStackTrace();
    }
    return f;
  }

  public static void readNode(File f) {
    log.debug("Reading file..." + f.getPath());
    try {
      NodeTO node = XMLReader.readNode(f);
      node.setUri(new VOSpaceURI("esavo!vospace", "/mytable2"));
      File f2 = new File("/home/snieto/node2.xml");
      if (f2.exists())
        f2.delete();
      XMLWriter.writeNode(node, f2);
      log.info("File " + f2.toString() + " created");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void readSearch(File f) {
    try {
      SearchTO search = XMLReader.readSearch(f);
      File f2 = new File("/home/iortiz/search2.xml");
      if (f2.exists())
        f2.delete();
      XMLWriter.writeSearch(search, f2);
      log.info("File " + f2.toString() + " created");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void readTransfer(File f) {
    try {
      TransferTO transfer = XMLReader.readTransfer(f);
      transfer.setDirection(Direction.PUSH_FROM_VOSPACE);
      VoViewTO view = new VoViewTO();
      view.setURI(new VOSpaceURI("ivo://ivoa.net/vospace/core#votable"));      
      transfer.setView(view);
      File f2 = new File("/home/snieto/Desktop/test/transfer2.xml");
      if (f2.exists())
        f2.delete();
      XMLWriter.writeTransfer(transfer, f2);
      log.info("File " + f2.toString() + " created");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void echoNodeGetters(NodeTO node) {
    log.debug("Node type=" + node.getType());
    log.debug("Node uri=" + node.getURI());
    log.debug("Node properties=" + node.getNodeProperties());
  }

  public static void evaluateXPathElement(Document document, String xpath) {
    XPathExpression<Element> expression = XPathFactory.instance().compile(
        xpath, Filters.element(), null, VOSpaceNS.VOSPACE.getNamespace());
    Element emt = expression.evaluateFirst(document);
    if (emt != null) {
      System.out.println("XPath has result: " + emt.getName());
    } else {
      System.out.println("Bad luck!");
    }
  }

  public static void evaluateXPathAttribute(Document document, String xpath) {
    XPathExpression<Attribute> expression = XPathFactory.instance().compile(
        xpath, Filters.attribute(), null, VOSpaceNS.VOSPACE.getNamespace());
    Attribute attr = expression.evaluateFirst(document);
    if (attr != null) {
      System.out.println("XPath has result: " + attr.getValue());
    } else {
      System.out.println("Bad luck!");
    }
  }

  public static TransferTO createTransfer() {
    TransferTO transfer = new TransferTO();
    transfer.setTarget(new VOSpaceURI("vos://esavo!vospace/snieto/ContainerTest/Hijo"));
    transfer.setDirection(new VOSpaceURI("vos://esavo!vospace/snieto"));
    VoViewTO view = new VoViewTO();
    view.setURI(new VOSpaceURI("vos://esavo!vospace/vospace/core#fits"));
    transfer.setView(view);
    VoProtocolTO protocol = new VoProtocolTO();
    protocol.setURI(new VOSpaceURI("ivo://ivoa.net/vospace/core#httpget"));
    protocol.setEndPoint(new VOSpaceURI("http://some.server.com/here/is/the/data"));
    transfer.setProtocol(protocol);
    return transfer;
  }
  
  public static SearchTO createSearch() {
    SearchTO search = new SearchTO();
    search.setURI(new VOSpaceURI("vos://esavo!vospace/mydata1"));
    search.setLimit(1000);
    search.setDetail(SearchTO.Detail.min);
    search.setMatches("ivo://ivoa.net/vospace/core#description='galax'");
    search.setNode(new VOSpaceURI("vos://esavo!vospace/mystartingnode"));
    return search;
  }
  
  public static void main(String[] args) throws Exception {
    log.debug("Start!");
    //readSearch(writeSearch(createSearch()));
//    readNode(writeNode(createContainerNode()));    
//    createLinkNode();
    //readNode(writeNode(createLinkNode()));
    readNode(new File("/home/snieto/Desktop/test/container.xml"));
    //readTransfer(new File("/home/snieto/Desktop/test/transfer.xml"));
      //writeNode(createUnstructuredNode());
//    readNode(writeNode(createUnstructuredNode()));
//    echoNodeGetters(createUnstructuredNode());
//    evaluateXPathElement(
//        new SAXBuilder().build(new FileInputStream("/home/iortiz/test.xml")),
//        "/vos:node/vos:properties");
//    evaluateXPathAttribute(
//        new SAXBuilder().build(new FileInputStream("/home/iortiz/test.xml")),
//        "/vos:node/@uri");
//    createTransfer();
//    writeTransfer(createTransfer());
//    readTransfer(writeTransfer(createTransfer()));
//    writeGetProperties(createProperties1(), createProperties2(), createProperties3());
//    writeGetProtocols(createProtocols1(), createProtocols1());
//    writeGetViews(createViews1(), createViews2());
    log.debug("Done!");
  }
}
