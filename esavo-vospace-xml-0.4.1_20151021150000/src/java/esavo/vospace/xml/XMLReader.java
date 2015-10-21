package esavo.vospace.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jdom2.Attribute;
import org.jdom2.Element;
import org.jdom2.Namespace;

import esavo.vospace.exceptions.VOSpaceException;
import esavo.vospace.common.model.transferobjects.CapabilityParamTO;
import esavo.vospace.common.model.transferobjects.GroupNodePropertyTO;
import esavo.vospace.common.model.transferobjects.GroupTO;
import esavo.vospace.common.model.transferobjects.NodePropertyTO;
import esavo.vospace.common.model.transferobjects.ProtocolParamTO;
import esavo.vospace.common.model.transferobjects.SearchTO;
import esavo.vospace.common.model.transferobjects.TransferTO;
import esavo.vospace.common.model.transferobjects.UserNodePropertyTO;
import esavo.vospace.common.model.transferobjects.UserTO;
import esavo.vospace.common.model.transferobjects.VOSpaceURI;
import esavo.vospace.common.model.transferobjects.ViewParamTO;
import esavo.vospace.common.model.transferobjects.VoCapabilityTO;
import esavo.vospace.common.model.transferobjects.ContainerNodeTO;
import esavo.vospace.common.model.transferobjects.DataNodeTO;
import esavo.vospace.common.model.transferobjects.LinkNodeTO;
import esavo.vospace.common.model.transferobjects.NodeTO;
import esavo.vospace.common.model.transferobjects.VoPropertyTO;
import esavo.vospace.common.model.transferobjects.VoProtocolTO;
import esavo.vospace.common.model.transferobjects.StructuredDataNodeTO;
import esavo.vospace.common.model.transferobjects.UnstructuredDataNodeTO;
import esavo.vospace.common.model.transferobjects.VoViewTO;
import esavo.vospace.common.model.transferobjects.VOSpaceNodeType;
//import esavo.vospace.model.Param;
//import esavo.vospace.model.SearchTO;
//import esavo.vospace.model.TransferTO;
//import esavo.vospace.model.VOSpaceNodeType;
//import esavo.vospace.model.VOSpaceURI;

/**
 * @author Inaki Ortiz de Landaluce Copyright (c) 2013- European Space Agency
 * 
 */
public class XMLReader {

  private static final Namespace VOSNS = VOSpaceNS.VOSPACE.getNamespace();
  private static final Namespace XSINS = VOSpaceNS.SCHEMA_INSTANCE
      .getNamespace();
  private static final URL SCHEMA_URL = XMLUtils.class.getClassLoader()
      .getResource("VOSpace-2.0.xsd");

  private static Logger log = Logger.getLogger(XMLUtils.class);

  public static NodeTO readNode(File f) throws VOSpaceException {
    try {
      return readNode(new FileInputStream(f));
    } catch (FileNotFoundException e) {
      throw new VOSpaceException(e, "File not found");
    }
  }

  public static NodeTO readNode(InputStream in) throws VOSpaceException {
      log.debug("VOSpace schema location: " + SCHEMA_URL.toString());
      Element element = XMLUtils.read(in, SCHEMA_URL).getRootElement();
      return buildNode(element);
    }

  public static NodeTO readNode(String in) throws VOSpaceException {
      log.debug("VOSpace schema location: " + SCHEMA_URL.toString());
      Element element = XMLUtils.read(in, SCHEMA_URL).getRootElement();
      return buildNode(element);
    }

  public static SearchTO readSearch(File f) throws VOSpaceException {
    try {
      return readSearch(new FileInputStream(f));
    } catch (FileNotFoundException e) {
      throw new VOSpaceException(e, "File not found");
    }
  }

  public static SearchTO readSearch(InputStream in) throws VOSpaceException {
    log.debug("VOSpace schema location: " + SCHEMA_URL.toString());
    // parse input stream
    Element element = XMLUtils.read(in, SCHEMA_URL).getRootElement();
    // build SearchTO object from DOM
    return buildSearchTO(element);
  }

  public static TransferTO readTransfer(File f) throws VOSpaceException {
    try {
      return readTransferTO(new FileInputStream(f));
    } catch (FileNotFoundException e) {
      throw new VOSpaceException(e, "File not found");
    }
  }

  public static TransferTO readTransferTO(InputStream in) throws VOSpaceException {
    log.debug("VOSpace schema location: " + SCHEMA_URL.toString());
    // parse input stream
    Element element = XMLUtils.read(in, SCHEMA_URL).getRootElement();
    // build TransferTO object from DOM
    return buildTransferTO(element);
  }

  private static NodeTO buildNode(Element element) throws VOSpaceException {
    NodeTO node = null;
    // identify node type
    String attValue = XMLUtils.getAttributeValue(element,
        XMLConstants.NODE_TYPE, XSINS, false);
    log.debug("Node type (unqualified): " + attValue);
    VOSpaceNodeType type = VOSpaceNodeType.toObject(attValue);

    // create instance using relevant subclass
    switch (type) {
    case CONTAINER_NODE:
      node = new ContainerNodeTO();
      ((ContainerNodeTO) node).setNodeList(getNodeChildrenNodes(element));
      break;
    case LINK_NODE:
      node = new LinkNodeTO();
      ((LinkNodeTO) node).setTarget(getTarget(element));
      break;
    case STRUCTURED_DATA_NODE:
      node = new StructuredDataNodeTO();
      ((DataNodeTO) node).setAcceptViews(getNodeAcceptViews(element));
      ((DataNodeTO) node).setProvideViews(getNodeProvideViews(element));
      if (isNodeBusy(element)) {
        ((DataNodeTO) node).setBusy(true);
      }
      break;
    case UNSTRUCTURED_DATA_NODE:
      node = new UnstructuredDataNodeTO();
      ((DataNodeTO) node).setAcceptViews(getNodeAcceptViews(element));
      ((DataNodeTO) node).setProvideViews(getNodeProvideViews(element));
      if (isNodeBusy(element)) {
        ((DataNodeTO) node).setBusy(true);
      }
      break;
    case UNKNOWN:
      throw new InvalidNodeException("Invalid node ");
    }

    // set Node properties
    node.setCapabilities(getNodeCapabilities(element));
    node.setNodeProperties(getNodeProperties(element));
    node.setUri(getVOSpaceURI(element));
    return node;
  }

  private static SearchTO buildSearchTO(Element element) throws VOSpaceException {
    SearchTO SearchTO = new SearchTO();
    if (!element.getName().equals(XMLConstants.SEARCH)) {
      throw new InvalidTransferException("Element " + element.getName()
          + " is not a " + XMLConstants.SEARCH);
    }
    // get uri
    Element uriElm = element.getChild(XMLConstants.URI, VOSNS);
    if(uriElm != null) {
      SearchTO.setURI(new VOSpaceURI(uriElm.getText()));
    }
    // get limit
    Element limitElm = element.getChild(XMLConstants.LIMIT, VOSNS);
    if(limitElm != null) {
      SearchTO.setLimit(Integer.valueOf(limitElm.getText()).intValue());
    }
    // get detail
    Element detailElm = element.getChild(XMLConstants.DETAIL, VOSNS);
    if(detailElm != null) {
      try {
        SearchTO.setDetail(detailElm.getText());
      } catch (IllegalArgumentException e) {
        throw new VOSpaceException(e);
      }
    }    
    // get detail
    Element matchesElm = element.getChild(XMLConstants.MATCHES, VOSNS);
    if(matchesElm != null) {
      SearchTO.setMatches(matchesElm.getText());
    }    
    // get node
    Element nodeUriElm = element.getChild(XMLConstants.NODE, VOSNS);
    if(nodeUriElm != null) {
      SearchTO.setNode(new VOSpaceURI(nodeUriElm.getText()));
    }        
    return SearchTO;
  }
  
  private static TransferTO buildTransferTO(Element element) throws VOSpaceException {
    TransferTO TransferTO = new TransferTO();
    if (!element.getName().equals(XMLConstants.TRANSFER)) {
      throw new InvalidTransferException("Element " + element.getName()
          + " is not a " + XMLConstants.TRANSFER);
    }
    // get target
    TransferTO.setTarget(getTarget(element));
    // get direction
    Element directionElm = element.getChild(XMLConstants.DIRECTION, VOSNS);
    if (directionElm != null) {
      TransferTO.setDirection(directionElm.getText());
    }
    // get view
    Element viewElm = element.getChild(XMLConstants.VIEW, VOSNS);
    if (viewElm != null) {
      TransferTO.setView(getView(viewElm));
    }
    // get protocol
    Element protocolElm = element.getChild(XMLConstants.PROTOCOL, VOSNS);
    if (protocolElm != null) {
      log.debug("Protocol found");
      VoProtocolTO protocol = new VoProtocolTO();
      // protocol URI
      String uri = protocolElm.getAttributeValue(XMLConstants.URI);
      if (uri != null && uri.length() > 0) {
        log.debug("Protocol URI found");
        protocol.setURI(new VOSpaceURI(uri));
      }
      // protocol endpoint
      String endpoint = protocolElm.getChildText(XMLConstants.ENDPOINT, VOSNS);
      if (endpoint != null && endpoint.length() > 0) {
        log.debug("Protocol Endpoint found");
        protocol.setEndPoint(new VOSpaceURI(endpoint));
      }
      // protocol param
      Element paramElm = protocolElm.getChild(XMLConstants.PARAM, VOSNS);
      if (paramElm != null) {
        log.debug("Protocol Param found");
        ProtocolParamTO param = new ProtocolParamTO();
        String paramURI = paramElm.getAttributeValue(XMLConstants.URI);
        if (paramURI != null) {
          param.setURI(new VOSpaceURI(paramURI));
        }
        param.setValue(paramElm.getText());
      }
      TransferTO.setProtocol(protocol);
    }
    // get keepBytes
    Element keepBytes = element.getChild(XMLConstants.KEEP_BYTES, VOSNS);
    if (keepBytes != null) {
      log.debug("Protocol Keep-Bytes found");
      TransferTO.setKeepBytes(Boolean.valueOf(keepBytes.getText()));
    }
    return TransferTO;
  }

  private static List<VoViewTO> getNodeAcceptViews(Element node) {
    return getNodeViews(node, XMLConstants.ACCEPTS);
  }

  private static List<VoCapabilityTO> getNodeCapabilities(Element node) {
    List<VoCapabilityTO> capabilities = new ArrayList<VoCapabilityTO>();
    String xPath = "/" + XMLUtils.qualify(XMLConstants.NODE, VOSNS) + "/"
        + XMLUtils.qualify(XMLConstants.CAPABILITIES, VOSNS) + "/"
        + XMLUtils.qualify(XMLConstants.CAPABILITY, VOSNS);
    List<Element> elements = XMLUtils.evaluateXPathElements(xPath, node, VOSNS);
    for (Element elm : elements) {
        VoCapabilityTO capability = new VoCapabilityTO();
      // get/set URI
      capability
          .setURI(new VOSpaceURI(elm.getAttributeValue(XMLConstants.URI)));
      // get/set end point
      Element endpointElm = elm.getChild(XMLConstants.ENDPOINT, VOSNS);
      if (endpointElm != null) {
        VOSpaceURI uri = new VOSpaceURI(
            endpointElm.getAttributeValue(XMLConstants.URI));
        capability.setEndPoint(uri);
      }
      // get/set parameters
      List<Element> paramsElm = elm.getChildren(XMLConstants.PARAM, VOSNS);
      List<CapabilityParamTO> params = new ArrayList<CapabilityParamTO>();
      CapabilityParamTO param;
      for (Element paramElm : paramsElm) {
          param = new CapabilityParamTO();
          param.setURI((new VOSpaceURI(
                  paramElm.getAttributeValue(XMLConstants.URI))));
          param.setValue(paramElm.getText());
          params.add(param);
      }
      capability.setParams(params);
      // add capability
      capabilities.add(capability);
    }
    return capabilities;
  }

  private static List<NodeTO> getNodeChildrenNodes(Element node)
      throws VOSpaceException {
    List<NodeTO> childrenNodes = new ArrayList<NodeTO>();
    String xPath = "/" + XMLUtils.qualify(XMLConstants.NODE, VOSNS) + "/"
        + XMLUtils.qualify(XMLConstants.NODES, VOSNS) + "/"
        + XMLUtils.qualify(XMLConstants.NODE, VOSNS);
    List<Element> elements = XMLUtils.evaluateXPathElements(xPath, node, VOSNS);
    for (Element elm : elements) {
      childrenNodes.add(buildNode(elm));
    }
    return childrenNodes;
  }

  private static List<NodePropertyTO> getNodeProperties(Element node) {
    List<NodePropertyTO> properties = new ArrayList<NodePropertyTO>();
    String xPath = "/" + XMLUtils.qualify(XMLConstants.NODE, VOSNS) + "/"
        + XMLUtils.qualify(XMLConstants.PROPERTIES, VOSNS) + "/"
        + XMLUtils.qualify(XMLConstants.PROPERTY, VOSNS);
    List<Element> elements = XMLUtils.evaluateXPathElements(xPath, node, VOSNS);
    for (Element elm : elements) {
        VoPropertyTO voProperty = new VoPropertyTO();
        String uri = elm.getAttributeValue(XMLConstants.URI);
        VOSpaceURI uriProperty = new VOSpaceURI(uri);
        voProperty.setURI(uri);
        voProperty.setReadOnly(Boolean.parseBoolean(elm.getAttributeValue(XMLConstants.READ_ONLY)));
        
        //NodeProperty
        NodePropertyTO propertyTO;
        // retrieve Groups/Users if property is related to sharing
        String fragment = uriProperty.getFragment();
        if (fragment != null && (fragment.compareTo(XMLConstants.GROUP_READ) == 0 ||
                fragment.compareTo(XMLConstants.GROUP_WRITE) == 0)) {
            
            propertyTO = new GroupNodePropertyTO();
            String value = elm.getText();
            String[] groups = new String[0];
            if (value != null && !value.isEmpty()) {
                groups = value.split(",");
            }
            
            List<GroupTO> groupList = new ArrayList<GroupTO>();
            for (String groupName : groups) {
                GroupTO group = new GroupTO();
                group.setName(groupName.trim());
                groupList.add(group);
            }
            
            ((GroupNodePropertyTO) propertyTO).setGroupList(groupList);
            
        } else if (fragment != null && (fragment.compareTo(XMLConstants.USER_READ) == 0 ||
                fragment.compareTo(XMLConstants.USER_WRITE) == 0)) {
            
            propertyTO = new UserNodePropertyTO();
            String value = elm.getText();
            String[] users = new String[0];
            if (value != null && !value.isEmpty()) {
                users = value.split(",");
            }
            
            List<UserTO> userList = new ArrayList<UserTO>();
            for (String userName : users) {
                UserTO user = new UserTO();
                user.setName(userName.trim());
                userList.add(user);
            }
            
            ((UserNodePropertyTO)propertyTO).setUserList(userList);
        } else {
            propertyTO = new NodePropertyTO();
            propertyTO.setValue(elm.getText());
        }
        
        propertyTO.setVoPropertyTO(voProperty);
        boolean nillable = Boolean.parseBoolean(elm.getAttributeValue(XMLConstants.NIL, XSINS));
        propertyTO.setNill(nillable);
        properties.add(propertyTO);
    }
    return properties;
  }

  private static List<VoViewTO> getNodeProvideViews(Element node) {
    return getNodeViews(node, XMLConstants.PROVIDES);
  }

  private static List<VoViewTO> getNodeViews(Element node, String parent) {
    List<VoViewTO> views = new ArrayList<VoViewTO>();
    String xPath = "/" + XMLUtils.qualify(XMLConstants.NODE, VOSNS) + "/"
        + XMLUtils.qualify(parent, VOSNS) + "/"
        + XMLUtils.qualify(XMLConstants.VIEW, VOSNS);
    List<Element> elements = XMLUtils.evaluateXPathElements(xPath, node, VOSNS);
    for (Element elm : elements) {
      views.add(getView(elm));
    }
    return views;
  }

  private static VOSpaceURI getTarget(Element parent) {
    String uri = null;
    Element target = parent.getChild(XMLConstants.TARGET, VOSNS);
    if (target != null) {
      uri = target.getText();
    }
    return (uri == null || uri.length() == 0) ? null : new VOSpaceURI(uri);
  }

  private static VoViewTO getView(Element viewElm) {
    VoViewTO view = new VoViewTO();
    // get/set URI
    view.setURI(new VOSpaceURI(viewElm.getAttributeValue(XMLConstants.URI)));
    // get/set original
    view.setOriginal(Boolean.getBoolean(viewElm
        .getAttributeValue(XMLConstants.ORIGINAL)));
    // get/set parameters
    List<Element> paramsElm = viewElm.getChildren(XMLConstants.PARAM, VOSNS);
    List<ViewParamTO> params = new ArrayList<ViewParamTO>();
    ViewParamTO param;
    for (Element paramElm : paramsElm) {
        param = new ViewParamTO();
        param.setURI(new VOSpaceURI(
          paramElm.getAttributeValue(XMLConstants.URI)));
        param.setValue(paramElm.getText());
        params.add(param);
    }
    view.setParams(params);
    return view;
  }

  private static VOSpaceURI getVOSpaceURI(Element element) {
    String uri = element.getAttributeValue(XMLConstants.URI);
    return (uri == null) ? null : new VOSpaceURI(uri);
  }

  private static boolean isNodeBusy(Element node) {
    boolean busy = false;
    Attribute attr = node.getAttribute(XMLConstants.BUSY, VOSNS);
    if (attr != null) {
      busy = Boolean.getBoolean(attr.getValue());
    }
    return busy;
  }
}