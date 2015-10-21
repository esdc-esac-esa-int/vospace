package esavo.vospace.storage;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import esavo.vospace.exceptions.VOSpaceException;

public class LocalFileSystemStorageManager implements StorageManager {

  @Override
  public void createContainer(URI location) throws VOSpaceException {
    try {
      boolean success = new File(location).mkdir();
      if (!success)
        throw new VOSpaceException("Container cannot be created");
    } catch (Exception e) {
      throw new VOSpaceException(e);
    }
  }

  @Override
  public void createLink(URI linkLocation, URI targetLocation) throws VOSpaceException {
    // File NIO API (Java 7) allows creating a symbolic link easily
    /*try {
        Files.createSymbolicLink(linkLocation.getPath(), targetLocation.getPath());
    } catch (Exception e) {
        throw new VOSpaceException(e);
    }*/    
    try {
      String cmd = "ln -s " + targetLocation.getPath() + " " + linkLocation.getPath();
      Runtime runtime = Runtime.getRuntime();      
      runtime.exec(cmd);
    } catch (IOException e) {
      throw new VOSpaceException(e);
    }      
  }
  
  @Override
  public void moveBytes(URI oldLocation, URI newLocation)
      throws VOSpaceException {
    try {
      File oldFile = new File(oldLocation);
      if (oldFile.isFile()) {
        FileUtils.moveFile(oldFile, new File(newLocation));
      } else {
        FileUtils.moveDirectory(oldFile, new File(newLocation));
      }
    } catch (Exception e) {
      throw new VOSpaceException(e);
    }
  }

  @Override
  public void copyBytes(URI oldLocation, URI newLocation)
      throws VOSpaceException {
    try {
      File oldFile = new File(oldLocation);
      if (oldFile.isFile()) {
        FileUtils.copyFile(oldFile, new File(newLocation));
      } else {
        FileUtils.copyDirectory(oldFile, new File(newLocation));
      }
    } catch (Exception e) {
      throw new VOSpaceException(e);
    }
  }

  @Override
  public void putBytes(URI location, InputStream stream)
      throws VOSpaceException {
    try {
      int bufferSize = 8192;
      BufferedInputStream bis = new BufferedInputStream(stream);
      FileOutputStream fos = new FileOutputStream(location.getPath());
      byte[] buffer = new byte[bufferSize];
      int count = bis.read(buffer);
      while (count != -1 && count <= bufferSize) {
        fos.write(buffer, 0, count);
        count = bis.read(buffer);
      }
      if (count != -1) {
        fos.write(buffer, 0, count);
      }
      fos.close();
      bis.close();
    } catch (Exception e) {
      throw new VOSpaceException(e);
    }
  }

  @Override
  public InputStream getBytes(URI location) throws VOSpaceException {
    try {
      return new FileInputStream(location.getPath());
    } catch (Exception e) {
      throw new VOSpaceException(e);
    }
  }

  @Override
  public boolean removeBytes(URI location) throws VOSpaceException {
    try {
      return new File(location).delete();
    } catch (Exception e) {
      throw new VOSpaceException(e);
    }
  }

  @Override
  public long lastModified(URI location) throws VOSpaceException {
    try {
      return new File(location).lastModified();
    } catch (Exception e) {
      throw new VOSpaceException(e);
    }
  }

  @Override
  public List<URI> listFiles(URI containerLocation) throws VOSpaceException {
    try {
     List<URI> files = new ArrayList<URI>();
     File[] pathNames = new File(containerLocation).listFiles();
     
     if(pathNames == null) { // is not a directory
       return null;
     }
     // add URIs to list
     for(int i=0; i < pathNames.length; i++) {
       files.add(pathNames[i].toURI());
     }     
     return files;
     
    } catch (Exception e) {
      throw new VOSpaceException(e);
    }
  }  
}