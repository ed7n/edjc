package eden.common.excep.media;

import eden.common.excep.EDENExceptions;

/**
 * Thrown when a media type is misformatted.
 *
 * @author Brendon
 */
public class MediaTypeMisformatException extends BadMediaTypeException {

  /** Problem description. */
  protected static final String PROBLEM
      = EDENExceptions.makeMisformatProblem("media type");

  /** Suggested remedy. */
  protected static final String REMEDY
      = "See template: type/facet.exempli.gratia.subtype+syntax;parameter";

  /** Makes an instance with the given media type label. */
  public MediaTypeMisformatException(String mediaType) {
    super(mediaType, PROBLEM, REMEDY);
  }

  /** To prevent null instantiations of this class. */
  protected MediaTypeMisformatException() {
  }
}
