package esac.archive.vospace.persistence.model.controls;

/**
 * Represents the following vospace operations: 'pushToVoSpace', 'pullToVoSpace',
 *  'pullFromVoSpace' and 'pushFromVoSpace.
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 *
 */
public enum TransferOperType {
    PUSH_TO_VOSPACE, UPLOAD, PULL_TO_VOSPACE, PULL_FROM_VOSPACE_POST, PULL_FROM_VOSPACE_GET, DOWNLOAD, PUSH_FROM_VOSPACE;
    
    public static TransferOperType toType (String type) {
        
        if (type.compareTo(TransferOperType.PULL_FROM_VOSPACE_POST.toString()) == 0) {
            return TransferOperType.PULL_FROM_VOSPACE_POST;
        } else if (type.compareTo(TransferOperType.PULL_FROM_VOSPACE_GET.toString()) == 0) {
            return TransferOperType.PULL_FROM_VOSPACE_GET;
        } else if (type.compareTo(TransferOperType.PULL_TO_VOSPACE.toString()) == 0) {
            return TransferOperType.PULL_TO_VOSPACE;
        }  else if (type.compareTo(TransferOperType.PUSH_FROM_VOSPACE.toString()) == 0) {
            return TransferOperType.PUSH_FROM_VOSPACE;
        } else if (type.compareTo(TransferOperType.PUSH_TO_VOSPACE.toString()) == 0) {
            return TransferOperType.PUSH_TO_VOSPACE;
        } else if (type.compareTo(TransferOperType.UPLOAD.toString()) == 0) {
            return TransferOperType.UPLOAD;
        } else if (type.compareTo(TransferOperType.DOWNLOAD.toString()) == 0) {
            return TransferOperType.DOWNLOAD;
        } else {
            return null;
        }
    }
}
