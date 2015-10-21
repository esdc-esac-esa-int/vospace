package esavo.vospace.dl.querymanager.security;

import java.util.Set;

import org.apache.log4j.Logger;

import esac.archive.absi.interfaces.common.entitymodel.Attribute;
import esac.archive.absi.interfaces.common.model.exceptions.ArchiveNestedException;
import esac.archive.absi.interfaces.common.transferobjects.controls.UserTO;
import esac.archive.absi.interfaces.dl.querymanager.security.IAuthorizationService;

public class VospaceAuthorizationServiceImpl implements IAuthorizationService {

    /**
     * Logger.
     */
    private static Logger logger = Logger.getLogger(VospaceAuthorizationServiceImpl.class);

    @Override
    public final void validateUserAuthorization(final UserTO userTO,
            final Set<Attribute> inputAttributes) throws ArchiveNestedException {
        logger.debug("Into VospaceAuthorizationServiceImpl.validateUserAuthorization(" + userTO + ","
                + inputAttributes + ")");
        // Authorise all users. Do nothing.
    }

}
