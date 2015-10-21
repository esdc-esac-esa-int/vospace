package esac.archive.vospace.persistence.model.controls;

/**
 * Represents the type of Data Management operations: create, delete, move, copy, get, set and find.
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 *
 */
public enum DataMngType {
    CREATE, DELETE, MOVE, COPY, GET, SET, FIND;
    
    public static DataMngType toType (String type) {
        
        DataMngType dataMngType = null;
               
        if (type.compareTo(DataMngType.COPY.toString()) == 0) {
            dataMngType = DataMngType.COPY;
        } else if (type.compareTo(DataMngType.CREATE.toString()) == 0) {
            dataMngType = DataMngType.CREATE;
        }  else if (type.compareTo(DataMngType.DELETE.toString()) == 0) {
            dataMngType = DataMngType.DELETE;
        } else if (type.compareTo(DataMngType.FIND.toString()) == 0) {
            dataMngType = DataMngType.FIND;
        } else if (type.compareTo(DataMngType.GET.toString()) == 0) {
            dataMngType = DataMngType.GET;
        } else if (type.compareTo(DataMngType.MOVE.toString()) == 0) {
            dataMngType = DataMngType.MOVE;
        } else if (type.compareTo(DataMngType.SET.toString()) == 0) {
            dataMngType = DataMngType.SET;
        }
        
        return dataMngType;
    }
}
