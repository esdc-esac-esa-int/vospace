package esavo.vospace.storage;

import java.io.InputStream;
import java.net.URI;
import java.util.List;

import esavo.vospace.exceptions.VOSpaceException;

/**
 * Interface for communicating with back-end storage
 */
public interface StorageManager {

  /**
   * Create a container at the specified location in the current back-end
   * storage
   * 
   * @param location
   *          The location of the container
   */
  public void createContainer(URI location) throws VOSpaceException;
  

  /**
   * Create a node link at the specified location in the current back-end
   * storage
   * 
   * @param linkLocation
   *          the location of the symbolic link to create
   * @param target
   *          the target of the symbolic link
   */  
  public void createLink(URI linkLocation, URI targetLocation)
      throws VOSpaceException;

  /**
   * Move the bytes from the specified old location to the specified new
   * location in the current back-end storage
   * 
   * @param oldLocation
   *          The old location of the bytes
   * @param newLocation
   *          The new location of the bytes
   */
  public void moveBytes(URI oldLocation, URI newLocation)
      throws VOSpaceException;

  /**
   * Copy the bytes from the specified old location to the specified new
   * location in the current back-end storage
   * 
   * @param oldLocation
   *          The old location of the bytes
   * @param newLocation
   *          The new location of the bytes
   */
  public void copyBytes(URI oldLocation, URI newLocation)
      throws VOSpaceException;

  /**
   * Put the bytes from the specified input stream at the specified location in
   * the current back-end storage
   * 
   * @param location
   *          The location for the bytes
   * @param stream
   *          The stream containing the bytes
   */
  public void putBytes(URI location, InputStream stream)
      throws VOSpaceException;

  /**
   * Get the bytes from the specified location in the current back-end storage
   * 
   * @param location
   *          The location of the bytes
   * @return a stream containing the requested bytes
   */
  public InputStream getBytes(URI location) throws VOSpaceException;

  /**
   * Remove the bytes at the specified location in the current back-end storage
   * 
   * @param location
   *          The location of the bytes
   * @return true if and only if the file or directory is successfully deleted;
   *         false otherwise
   */
  public boolean removeBytes(URI location) throws VOSpaceException;

  /**
   * Retrieve when the bytes at the specified location in the current back-end
   * storage were last modified. A response of -1 indicates that the information
   * is not available.
   * 
   * @param location
   *          The location to check
   * @return when the location was last modified
   */
  public long lastModified(URI location) throws VOSpaceException;

  /**
   * Returns a list of URIs denoting the files in the container
   * denoted by the location.
   * 
   * If the location does not denote a container, then this method returns null. 
   * Otherwise a list of URIs is returned, one for each file or container in 
   * the container.
   *  
   * @param containerLocation
   *          The location of the container
   * @return the list of files in the container
   */  
  public List<URI> listFiles(URI containerLocation) throws VOSpaceException;
  
}