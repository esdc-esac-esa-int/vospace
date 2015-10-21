package esac.archive.vospace.persistence.model.controls;

/**
 * Represents the type of Metadata Retrieval operations: protocols, views and properties.
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 *
 */
public enum MetadataRetrievalType {
    PROTOCOLS, VIEWS, PROPERTIES;
    
    public static MetadataRetrievalType toType (String type) {
               
        if (type.compareTo(MetadataRetrievalType.PROPERTIES.toString()) == 0) {
            return MetadataRetrievalType.PROPERTIES;
        } else if (type.compareTo(MetadataRetrievalType.PROTOCOLS.toString()) == 0) {
            return MetadataRetrievalType.PROTOCOLS;
        }  else if (type.compareTo(MetadataRetrievalType.VIEWS.toString()) == 0) {
            return MetadataRetrievalType.VIEWS;
        } return null;
    }
}
