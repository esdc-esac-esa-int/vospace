package esavo.vospace.dl.querymanager.cmd.controls;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import esac.archive.absi.interfaces.common.model.exceptions.ArchiveNestedException;
import esac.archive.absi.interfaces.dl.querymanager.cmd.IQueryManagerCommand;
import esac.archive.absi.persistence.DAOServiceProvider;
import esac.archive.vospace.persistence.IVospaceDaoService;
import esac.archive.vospace.persistence.model.controls.User;
import esavo.vospace.common.model.transferobjects.control.UserControlTO;

/**
 * Command which will ingest an UserControl into the Control database.
 * @author snieto - ESAC/ESA - Madrid, Spain Copyright (c) 2014
 *
 */
public class IngestUserControlCommand implements IQueryManagerCommand {

    /**
     * Commons-logging variable.
     */
    private static Log log = LogFactory.getLog(IngestUserControlCommand.class);
    
    private IVospaceDaoService IDaoService = (IVospaceDaoService) DAOServiceProvider.getDaoService();
    
    private UserControlTO userControlTO;
    
    /**
     * Constructor.
     */
    public IngestUserControlCommand (UserControlTO userControlTO) {
        log.debug("Into IngestUserControlCommand()");
        this.userControlTO = userControlTO;
    }
    
    @Override
    public void execute() throws ArchiveNestedException {
        log.debug("Into IngestUserControlCommand().execute()");
        
        if (this.userControlTO == null) {
            throw new IllegalArgumentException("IngestUserControlCommand.execute() -> "
                    + "Input userControlTO cannot be null.");
        }
        
        User user = IDaoService.getUserControlDao().getUserControlByUname(this.userControlTO.getName());
        if (user != null) {
            throw new UnsupportedOperationException("UserControl has already been ingested.");
        } else {
            user = IDaoService.getUserControlDao().getEmptyUserControl();
        }
        
        user.setAddress1(this.userControlTO.getAddress1());
        user.setAddress2(this.userControlTO.getAddress2());
        user.setCountry(this.userControlTO.getCountry());
        user.setFax(this.userControlTO.getFax());
        user.setInstitute(this.userControlTO.getInstitute());
        user.setMail(this.userControlTO.getMail());
        user.setPhoneNumber(this.userControlTO.getPhone());
        //user.setRegisterDate(this.userControlTO.getRegisterDate());
        user.setState(this.userControlTO.getState());
        user.setSurname(this.userControlTO.getSurname());
        user.setTown(this.userControlTO.getTown());
        user.setUname(this.userControlTO.getName());
        user.setZipCode(this.userControlTO.getZipCode());
        
        IDaoService.getUserControlDao().insertOrUpdate(user);
        
        log.debug("End of IngestUserControlCommand().execute()");
    }

    @Override
    public Object getResult() {
        return null;
    }

}
