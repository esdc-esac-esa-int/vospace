package esavo.vospace.common.model.transferobjects;

import java.util.ArrayList;
import java.util.List;

import esac.archive.absi.interfaces.common.entitymodel.Attribute;
import esavo.vospace.common.entitymodel.EDataNode;
import esavo.vospace.common.entitymodel.VospaceModel;

/**
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 */
public class DataNodeTO extends NodeTO {

    /**
     * Generated serial version uid.
     */
    private static final long serialVersionUID = -9113454314592545753L;
    
    /**
     * Associated entity of this transfer object.
     */
    private EDataNode dataNode = VospaceModel.getInstance().DATA_NODE;
    
    /** Attribute. */
    private Attribute BUSY = dataNode.BUSY;
    
    /** */
    protected List<VoViewTO> acceptViewTOList;
    
    /** */
    protected List<VoViewTO> provideViewTOList;
    
    /**
     * Default constructor.
     */
    public DataNodeTO() {
        
    }
    
    /**
     * Return busy attribute
     * @return true if it's true, false if not.
     */
    public final Boolean isBusy() {
        Boolean busyValue = (Boolean) super.getAttribute(BUSY);
        if (busyValue == null)
            setBusy((Boolean)false);
        return (Boolean) super.getAttribute(BUSY);
    }
    
    /**
     * Modify busy property
     * @param busy
     */
    public final void setBusy(final Boolean busy) {
        super.setAttribute(BUSY, busy);
    }

    /**
     * @return the voViewTOList
     */
    public final List<VoViewTO> getAcceptViews() {
        if (this.acceptViewTOList == null) {
            this.acceptViewTOList = new ArrayList<VoViewTO>();
        }
        return acceptViewTOList;
    }

    /**
     * @param voViewTOList the voViewTOList to set
     */
    public final void setAcceptViews(List<VoViewTO> voViewTOList) {
        this.acceptViewTOList = voViewTOList;
    }
    
    /**
     * @return the voViewTOList
     */
    public final List<VoViewTO> getProvideViews() {
        if (this.provideViewTOList == null) {
            this.provideViewTOList = new ArrayList<VoViewTO>();
        }
        return provideViewTOList;
    }

    /**
     * @param voViewTOList the voViewTOList to set
     */
    public final void setProvideViews(List<VoViewTO> voViewTOList) {
        this.provideViewTOList = voViewTOList;
    }
    
    @Override
    public VOSpaceNodeType getType() {
        return VOSpaceNodeType.UNKNOWN;
    }

}
