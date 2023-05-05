package eden.common.excep.media;

/**
 * Thrown when a media type has no facet name but its subtype name contains dot
 * characters (.).
 *
 * @author Brendon
 */
public class BadMediaTypeSubtypeException extends BadMediaTypeException {

  /** Problem description. */
  protected static final String PROBLEM =
    "The media type has no facet name but its subtype name contains dot " +
    "characters (.).";
  /** Suggested remedy. */
  protected static final String REMEDY =
    "Erase all dot characters from the subtype name.";

  /** Makes an instance with the given media type label. */
  public BadMediaTypeSubtypeException(String mediaType) {
    super(mediaType, PROBLEM, REMEDY);
  }

  /** To prevent null instantiations of this class. */
  protected BadMediaTypeSubtypeException() {}
}
