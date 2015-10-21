package esavo.vospace.xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.log4j.Logger;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;

import esavo.vospace.common.model.transferobjects.CapabilityParamTO;
import esavo.vospace.common.model.transferobjects.ContainerNodeTO;
import esavo.vospace.common.model.transferobjects.GroupNodePropertyTO;
import esavo.vospace.common.model.transferobjects.GroupTO;
import esavo.vospace.common.model.transferobjects.LinkNodeTO;
import esavo.vospace.common.model.transferobjects.NodePropertyTO;
import esavo.vospace.common.model.transferobjects.NodeTO;
import esavo.vospace.common.model.transferobjects.ProtocolParamTO;
import esavo.vospace.common.model.transferobjects.SearchTO;
import esavo.vospace.common.model.transferobjects.StructuredDataNodeTO;
import esavo.vospace.common.model.transferobjects.TransferTO;
import esavo.vospace.common.model.transferobjects.UnstructuredDataNodeTO;
import esavo.vospace.common.model.transferobjects.UserNodePropertyTO;
import esavo.vospace.common.model.transferobjects.UserTO;
import esavo.vospace.common.model.transferobjects.VOSpaceNodeType;
import esavo.vospace.common.model.transferobjects.VOSpaceURI;
import esavo.vospace.common.model.transferobjects.ViewParamTO;
import esavo.vospace.common.model.transferobjects.VoCapabilityTO;
import esavo.vospace.common.model.transferobjects.VoPropertyTO;
import esavo.vospace.common.model.transferobjects.VoProtocolTO;
import esavo.vospace.common.model.transferobjects.VoViewTO;
import esavo.vospace.exceptions.VOSpaceException;

/**
 * @author Inaki Ortiz de Landaluce Copyright (c) 2013- European Space Agency
 * 
 */
public class XMLWriter {

  private static final Namespace XMLNS = VOSpaceNS.VOSPACE.getNamespace();
  private static final Namespace VOSNS = VOSpaceNS.VOSPACE.getNamespace();
  private static final Namespace XSINS = VOSpaceNS.SCHEMA_INSTANCE
      .getNamespace();
  private static Logger log = Logger.getLogger(XMLWriter.class);

  public static void writeGetProperties(List<VoPropertyTO> accepts,
      List<VoPropertyTO> provides, List<VoPropertyTO> contains, File f)
      throws VOSpaceException {
    try {
      writeGetProperties(accepts, provides, contains, new FileOutputStream(f));
    } catch (FileNotFoundException e) {
      throw new XMLWritingException("File " + f.getAbsolutePath() + "not found");
    }
  }

  public static void writeGetProperties(List<VoPropertyTO> accepts,
      List<VoPropertyTO> provides, List<VoPropertyTO> contains, OutputStream out)
      throws VOSpaceException {
    Element element = buildGetProperties(accepts, provides, contains);
    Document document = new Document(element);
    XMLUtils.write(document, out);
  }

  public static void writeGetProtocols(List<VoProtocolTO> accepts,
      List<VoProtocolTO> provides, File f) throws VOSpaceException {
    try {
      writeGetProtocols(accepts, provides, new FileOutputStream(f));
    } catch (FileNotFoundException e) {
      throw new XMLWritingException("File " + f.getAbsolutePath() + "not found");
    }
  }

  public static void writeGetProtocols(List<VoProtocolTO> accepts,
      List<VoProtocolTO> provides, OutputStream out) throws VOSpaceException {
    Element element = buildGetProtocols(accepts, provides);
    Document document = new Document(element);
    XMLUtils.write(document, out);
  }

  public static void writeGetViews(List<VoViewTO> accepts, List<VoViewTO> provides,
      File f) throws VOSpaceException {
    try {
      writeGetViews(accepts, provides, new FileOutputStream(f));
    } catch (FileNotFoundException e) {
      throw new XMLWritingException("File " + f.getAbsolutePath() + "not found");
    }
  }

  public static void writeGetViews(List<VoViewTO> accepts, List<VoViewTO> provides,
      OutputStream out) throws VOSpaceException {
    Element element = buildGetViews(accepts, provides);
    Document document = new Document(element);
    XMLUtils.write(document, out);
  }

  public static void writeNode(NodeTO node, File f) throws VOSpaceException {
    try {
      writeNode(node, new FileOutputStream(f));
    } catch (FileNotFoundException e) {
      throw new XMLWritingException("File " + f.getAbsolutePath() + "not found");
    }
  }

  public static void writeNode(NodeTO node, OutputStream out)
      throws VOSpaceException {
    Element element = buildNodeElement(node);
    Document document = new Document(element);
    XMLUtils.write(document, out);
  }

  public static void writeSearch(SearchTO search, OutputStream out)
      throws VOSpaceException {
    Element element = buildSearchElement(search);
    Document document = new Document(element);
    XMLUtils.write(document, out);
  }
  
  public static void writeSearch(SearchTO search, File f)
      throws VOSpaceException {
    try {
      writeSearch(search, new FileOutputStream(f));
    } catch (FileNotFoundException e) {
      throw new XMLWritingException("File " + f.getAbsolutePath() + "not found");
    }
  }  

  public static void writeTransfer(TransferTO transfer, OutputStream out)
      throws VOSpaceException {
    Element element = buildTransferElement(transfer);
    Document document = new Document(element);
    XMLUtils.write(document, out);
  }

  public static void writeTransfer(TransferTO transfer, File f)
      throws VOSpaceException {
    try {
      writeTransfer(transfer, new FileOutputStream(f));
    } catch (FileNotFoundException e) {
      throw new XMLWritingException("File " + f.getAbsolutePath() + "not found");
    }
  }

  private static Element buildGetProperties(List<VoPropertyTO> accepts,
      List<VoPropertyTO> provides, List<VoPropertyTO> contains) throws VOSpaceException {
    Element properties = new Element(XMLConstants.PROPERTIES);
    properties.setNamespace(XMLNS);
    // add properties to accepts
    Element acceptsElm = new Element(XMLConstants.ACCEPTS);
    for (VoPropertyTO prop : accepts) {
      addProperty(acceptsElm, prop);
    }
    // add properties to provides
    Element providesElm = new Element(XMLConstants.PROVIDES);
    for (VoPropertyTO prop : provides) {
      addProperty(providesElm, prop);
    }
    // add properties to contains
    Element containsElm = new Element(XMLConstants.CONTAINS);
    for (VoPropertyTO prop : contains) {
      addProperty(containsElm, prop);
    }
    properties.addContent(acceptsElm);
    properties.addContent(providesElm);
    properties.addContent(containsElm);
    return properties;
  }

  private static Element buildGetProtocols(List<VoProtocolTO> accepts,
      List<VoProtocolTO> provides) throws VOSpaceException {
    Element protocols = new Element(XMLConstants.PROTOCOLS);
    protocols.setNamespace(XMLNS);
    // add views to accepts
    Element acceptsElm = new Element(XMLConstants.ACCEPTS);
    for (VoProtocolTO p : accepts) {
      addProtocol(acceptsElm, p);
    }
    // add views to provides
    Element providesElm = new Element(XMLConstants.PROVIDES);
    for (VoProtocolTO p : provides) {
      addProtocol(providesElm, p);
    }
    protocols.addContent(acceptsElm);
    protocols.addContent(providesElm);
    return protocols;
  }

  private static Element buildGetViews(List<VoViewTO> accepts, List<VoViewTO> provides)
      throws VOSpaceException {
    Element views = new Element(XMLConstants.VIEWS);
    views.setNamespace(XMLNS);
    // add views to accepts
    Element acceptsElm = new Element(XMLConstants.ACCEPTS);
    for (VoViewTO view : accepts) {
      addView(acceptsElm, view);
    }
    // add views to provides
    Element providesElm = new Element(XMLConstants.PROVIDES);
    for (VoViewTO view : provides) {
      addView(providesElm, view);
    }
    views.addContent(acceptsElm);
    views.addContent(providesElm);
    return views;
  }

  private static Element buildNodeElement(NodeTO node) throws VOSpaceException {
      // identify node type
      VOSpaceNodeType type;
      if (node instanceof ContainerNodeTO) {
          type = ((ContainerNodeTO) node).getType();
      } else if (node instanceof StructuredDataNodeTO) {
          type = ((StructuredDataNodeTO) node).getType();
      } else if (node instanceof UnstructuredDataNodeTO) {
          type = ((UnstructuredDataNodeTO) node).getType();
      } else if (node instanceof LinkNodeTO) {
          type = ((LinkNodeTO) node).getType();
      } else 
          throw new InvalidNodeException("Invalid node ");
    // create empty node element
    Element element = createEmptyNodeElement(type);

    // set Node attributes
    setNodeCapabilities(element, node.getCapabilities());
    setNodeProperties(element, node.getNodeProperties());
    setVOSpaceURI(element, node.getURI());
    
    
    switch(type) {
        case CONTAINER_NODE:
            ContainerNodeTO container = ((ContainerNodeTO) node);
            setNodeChildrenNodes(element, container.getNodeList());
            break;
        case STRUCTURED_DATA_NODE:
            StructuredDataNodeTO sDataNode = ((StructuredDataNodeTO) node);
            setNodeAcceptViews(element, sDataNode.getAcceptViews());
            setNodeProvideViews(element, sDataNode.getProvideViews());
            setNodeBusy(element, sDataNode.isBusy());
            break;
        case UNSTRUCTURED_DATA_NODE:
            UnstructuredDataNodeTO uDataNode = ((UnstructuredDataNodeTO) node);
            setNodeAcceptViews(element, uDataNode.getAcceptViews());
            setNodeProvideViews(element, uDataNode.getProvideViews());
            setNodeBusy(element, uDataNode.isBusy());
            break;
        case LINK_NODE:
            LinkNodeTO link = ((LinkNodeTO) node);
            setTarget(element, link.getTarget());
            break;
        default:
            throw new InvalidNodeException("Invalid node ");
    }    
    return element;
  }

  private static Element buildSearchElement(SearchTO search)
      throws VOSpaceException {
    Element searchElm = new Element(XMLConstants.SEARCH);
    searchElm.setNamespace(XMLNS);
    searchElm.addNamespaceDeclaration(VOSNS);
    // add uri (optional)
    VOSpaceURI uri = search.getURI();
    if (uri != null) {
      Element uriElm = new Element(XMLConstants.URI, XMLNS);
      uriElm.setText(uri.toString());
      searchElm.addContent(uriElm);
    }
    // add limit (optional)
    int limit = search.getLimit();
    if (limit > 0) {
      Element limitElm = new Element(XMLConstants.LIMIT, XMLNS);
      limitElm.setText(String.valueOf(limit));
      searchElm.addContent(limitElm);
    }
    // add detail
    SearchTO.Detail detail = search.getDetail();
    Element detailElm = new Element(XMLConstants.DETAIL, XMLNS);
    detailElm.setText(detail.toString());
    searchElm.addContent(detailElm);
    // add matches (optional)
    String matches = search.getMatches();
    if (matches != null && matches.trim().length() > 0) {
      Element matchesElm = new Element(XMLConstants.MATCHES, XMLNS);
      matchesElm.setText(matches);
      searchElm.addContent(matchesElm);
    }
    // add node uri (optional)
    VOSpaceURI nodeUri = search.getURI();
    if (nodeUri != null) {
      Element nodeUriElm = new Element(XMLConstants.NODE, XMLNS);
      nodeUriElm.setText(nodeUri.toString());
      searchElm.addContent(nodeUriElm);
    }    
    return searchElm;
  }
  
  private static Element buildTransferElement(TransferTO transfer)
      throws VOSpaceException {
    Element transferElm = new Element(XMLConstants.TRANSFER);
    transferElm.setNamespace(XMLNS);
    transferElm.addNamespaceDeclaration(VOSNS);
    // add target
    VOSpaceURI targetURI = transfer.getTarget();
    if (targetURI != null) {
      Element targetElm = new Element(XMLConstants.TARGET, XMLNS);
      targetElm.setText(targetURI.toString());
      transferElm.addContent(targetElm);
    }
    // add direction
    String direction = transfer.getDirection();
    if (direction != null && direction.length() > 0) {
      Element directionElm = new Element(XMLConstants.DIRECTION, XMLNS);
      directionElm.setText(direction);
      transferElm.addContent(directionElm);
    }
    // add view
    addView(transferElm, transfer.getView());
    // add protocol
    VoProtocolTO protocol = transfer.getProtocol();
    if (protocol != null) {
      // protocol URI
      Element protocolElm = new Element(XMLConstants.PROTOCOL, XMLNS);
      if (protocol.getURI() != null) {
        protocolElm.setAttribute(new Attribute(XMLConstants.URI, protocol
            .getURI().toString()));
      }
      // protocol end point
      if (protocol.getEndPoint() != null) {
        Element endpointElm = new Element(XMLConstants.ENDPOINT, XMLNS);
        endpointElm.setText(protocol.getEndPoint().toString());
        protocolElm.addContent(endpointElm);
      }
      // protocol param
      List<ProtocolParamTO> params = protocol.getParams();
      for(ProtocolParamTO param: params) {
        Element paramElm = new Element(XMLConstants.PARAM, XMLNS);
        paramElm.setAttribute(new Attribute(XMLConstants.URI, param.getURI()
            .toString()));
        paramElm.setText(param.getValue());
        protocolElm.addContent(paramElm);
      }
      transferElm.addContent(protocolElm);
    }
    // add keep bytes
    if (transfer.isKeepBytes()) {
      Element keepBytesElem = new Element(XMLConstants.KEEP_BYTES, XMLNS);
      keepBytesElem.setText(String.valueOf(transfer.isKeepBytes()));
      transferElm.addContent(keepBytesElem);
    }
    return transferElm;
  }

  private static Element createEmptyNodeElement(VOSpaceNodeType type) {
    // create document
    // Document document = new Document();
    // create root element and declare name spaces
    Element node = new Element(XMLConstants.NODE);
    node.setNamespace(XMLNS);
    node.addNamespaceDeclaration(XSINS);
    node.addNamespaceDeclaration(VOSNS);
    // set node type on root element
    node.setAttribute(new Attribute(XMLConstants.NODE_TYPE, XMLUtils.qualify(
        type.toString(), VOSNS), XSINS));
    // set root element to document
    // document.setRootElement(node);
    return node;
  }

  private static Element addCapability(Element parent, VoCapabilityTO c) {
    Element capability = new Element(XMLConstants.CAPABILITY, XMLNS);
    // set URI
    VOSpaceURI uri = c.getURI();
    if (uri != null) {
      capability.setAttribute(new Attribute(XMLConstants.URI, uri.toString()));
    }
    // set end point
    Element endpoint = new Element(XMLConstants.ENDPOINT, XMLNS);
    endpoint.setText(c.getEndPoint().toString());
    capability.addContent(endpoint);
    // set parameters
    List<CapabilityParamTO> parameters = c.getParams();
    if (parameters != null) {
      for (CapabilityParamTO param : parameters) {
        Element paramElm = new Element(XMLConstants.PARAM, XMLNS);
        paramElm.setAttribute(new Attribute(XMLConstants.URI, param.getURI()
            .toString()));
        paramElm.setText(param.getValue());
        capability.addContent(paramElm);
      }
    }
    return parent.addContent(capability);
  }

  private static Element addChildNode(Element parent, NodeTO child)
      throws VOSpaceException {
    Element childNode = buildNodeElement(child);
    return parent.addContent(childNode);
  }
  
  private static Element addProperty(Element parent, VoPropertyTO p) {
      Element property = new Element(XMLConstants.PROPERTY, XMLNS);
      String uri = p.getURI();
      if (uri != null) {
        property.setAttribute(new Attribute(XMLConstants.URI, uri));
      }
      property.setAttribute(new Attribute(XMLConstants.READ_ONLY, Boolean
          .toString(p.getReadOnly())));

      return parent.addContent(property);
    }

  private static Element addProperty(Element parent, NodePropertyTO p) {
    Element property = new Element(XMLConstants.PROPERTY, XMLNS);
    String uri = p.getVoPropertyTO().getURI();
    if (uri != null) {
      property.setAttribute(new Attribute(XMLConstants.URI, uri.toString()));
    }
    property.setAttribute(new Attribute(XMLConstants.READ_ONLY, Boolean
        .toString(p.getVoPropertyTO().getReadOnly())));
    
    String value = "";
    //VOSpaceURI uriProperty = new VOSpaceURI(uri);
    //String fragment = uriProperty.getFragment();
    if (p instanceof GroupNodePropertyTO) {
       
       List<GroupTO> groupList = ((GroupNodePropertyTO)p).getGroupList();
       for (GroupTO groupTO : groupList) {
           if (value.isEmpty())
               value += groupTO.getName();
           else
               value += "," + groupTO.getName();
       }
        
    } else if (p instanceof UserNodePropertyTO) {
        
        List<UserTO> userList = ((UserNodePropertyTO) p).getUserList();
        for (UserTO userTO : userList) {
            if (value.isEmpty())
                value += userTO.getName();
            else
                value += "," + userTO.getName();
        }
    } else {
        value = p.getValue();
    }
    
    if (value != null && !value.isEmpty()) {
        property.setText(value);
    }

    return parent.addContent(property);
  }

  private static Element addProtocol(Element parent, VoProtocolTO p) {
    Element protocol = new Element(XMLConstants.PROTOCOL, XMLNS);
    VOSpaceURI uri = p.getURI();
    if (uri != null) {
      protocol.setAttribute(new Attribute(XMLConstants.URI, uri.toString()));
    }
    return parent.addContent(protocol);
  }

  private static Element addView(Element parent, VoViewTO v) {
    Element view = new Element(XMLConstants.VIEW, XMLNS);
    // set URI
    VOSpaceURI uri = v.getURI();
    if (uri != null) {
      view.setAttribute(new Attribute(XMLConstants.URI, uri.toString()));
    }
    
    // set original
    view.setAttribute(new Attribute(XMLConstants.ORIGINAL, Boolean.toString(v.getOriginal())));
    // set parameters
    List<ViewParamTO> parameters = v.getParams();
    if (parameters != null) {
      for (ViewParamTO param : parameters) {
        Element paramElm = new Element(XMLConstants.PARAM, XMLNS);
        paramElm.setAttribute(new Attribute(XMLConstants.URI, param.getURI()
            .toString()));
        paramElm.setText(param.getValue());
        view.addContent(paramElm);
      }
    }
    return parent.addContent(view);
  }

  private static void setNodeAcceptViews(Element node, List<VoViewTO> accepts) {
    if (accepts != null && accepts.size() > 0) {
      // add accepts parent tag if needed
      Element parent = addChildElement(node, XMLConstants.ACCEPTS);
      // add accepts to parent tag
      for (VoViewTO view : accepts) {
        addView(parent, view);
      }
    }
  }

  private static void setNodeBusy(Element node, boolean isBusy) {
    node.setAttribute(new Attribute(XMLConstants.BUSY, Boolean.toString(isBusy)));
  }

  private static void setNodeCapabilities(Element node,
      List<VoCapabilityTO> capabilities) {
    if (capabilities != null && capabilities.size() > 0) {
      // add capabilities parent tag if needed
      Element parent = addChildElement(node, XMLConstants.CAPABILITIES);
      // add capabilities to parent tag
      for (VoCapabilityTO capability : capabilities) {
        addCapability(parent, capability);
      }
    }
  }

  private static void setNodeChildrenNodes(Element node, List<NodeTO> children)
      throws VOSpaceException {
    // add nodes parent tag
    Element parent = addChildElement(node, XMLConstants.NODES);    
    // add children nodes to parent tag
    for (NodeTO child : children) {
      addChildNode(parent, child);
    }
  }

  private static void setNodeProperties(Element node, List<NodePropertyTO> properties) {
    if (properties != null && properties.size() > 0) {
      // add properties tag if needed
      Element parent = addChildElement(node, XMLConstants.PROPERTIES);
      // add properties to parent tag
      for (NodePropertyTO property : properties) {
          if (!property.getNill()) {
              addProperty(parent, property);
          }
      }
    }
  }

  private static void setNodeProvideViews(Element node, List<VoViewTO> provides) {
    if (provides != null && provides.size() > 0) {
      // add provides parent tag if needed
      Element parent = addChildElement(node, XMLConstants.PROVIDES);
      // add properties to parent tag
      for (VoViewTO view : provides) {
        addView(parent, view);
      }
    }
  }

  private static void setTarget(Element parent, VOSpaceURI uri) {
    if (uri != null) {
      // add target tag if needed
      Element target = addChildElement(parent, XMLConstants.TARGET);
      // set target value
      target.setText(uri.toString());
    }
  }

  private static void setVOSpaceURI(Element element, VOSpaceURI uri) {
    if (uri != null) {
      element.setAttribute(new Attribute(XMLConstants.URI, uri.toString()));
    }
  }

  private static Element addChildElement(Element parent, String name) {
    Element child = parent.getChild(name, VOSNS);
    if (child == null) {
      child = new Element(name, VOSNS);
      parent.addContent(child);
      log.debug(name + " child created");
    }
    return child;
  }
}
