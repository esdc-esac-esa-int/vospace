package esavo.vospace.service.cmd.oper;

import esavo.vospace.exceptions.VOSpaceException;

public interface IVospaceCommand { 

    public void execute () 
            throws VOSpaceException;
    
    public Object getResult();
}
