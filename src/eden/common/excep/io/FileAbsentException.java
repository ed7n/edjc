package eden.common.excep.io;

import eden.common.excep.EDENException;

/**
 * Thrown when a file is absent.
 *
 * @author Brendon
 */
public class FileAbsentException extends EDENException {

  /** Problem description. */
  protected static final String PROBLEM = "The file is absent.";

  /** Makes an instance with the given file label. */
  public FileAbsentException(String file) {
    super(file, PROBLEM);
  }

  /** To prevent null instantiations of this class. */
  protected FileAbsentException() {}
}
