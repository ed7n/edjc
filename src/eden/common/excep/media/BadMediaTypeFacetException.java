package eden.common.excep.media;

/**
 * Thrown when a media type facet name contains dot characters (.).
 *
 * @author Brendon
 */
public class BadMediaTypeFacetException extends BadMediaTypeException {

  /** Problem description. */
  protected static final String PROBLEM
      = "The media type facet name contains dot characters (.).";

  /** Suggested remedy. */
  protected static final String REMEDY
      = "Erase all dot characters from the facet name.";

  /** Makes an instance with the given media type label. */
  public BadMediaTypeFacetException(String mediaType) {
    super(mediaType, PROBLEM, REMEDY);
  }

  /** To prevent null instantiations of this class. */
  protected BadMediaTypeFacetException() {
  }
}
