package eden.common.excep.media;

import eden.common.excep.EDENRuntimeException;

/**
 * Thrown when something in a media type is malformed.
 *
 * @author Brendon
 * @see eden.common.model.media.MediaType
 */
public class BadMediaTypeException extends EDENRuntimeException {

  /** Makes an instance with the given subject, problem, and remedy. */
  protected BadMediaTypeException(
    String subject,
    String problem,
    String remedy
  ) {
    super(subject, problem, remedy);
  }

  /** To prevent null instantiations of this class. */
  protected BadMediaTypeException() {}
}
