package eden.common.excep.io;

import eden.common.excep.EDENException;

/**
 * Thrown when a file is a directory.
 *
 * @author Brendon
 */
public class FileDirectoryException extends EDENException {

  /** Problem description. */
  protected static final String PROBLEM
      = "The file is a directory.";

  /** Makes an instance with the given file label. */
  public FileDirectoryException(String file) {
    super(file, PROBLEM);
  }

  /** To prevent null instantiations of this class. */
  protected FileDirectoryException() {
  }
}
