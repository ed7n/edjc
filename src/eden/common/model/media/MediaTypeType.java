package eden.common.model.media;

/**
 * Defines a media type type.
 *
 * @author Brendon
 */
public enum MediaTypeType {

  APPLICATION("application"),
  AUDIO("audio"),
  FONT("font"),
  IMAGE("image"),
  MESSAGE("message"),
  MODEL("model"),
  MULTIPART("multipart"),
  TEXT("text"),
  VIDEO("video");

  /** String. */
  protected final String string;

  /** Makes an instance with the given string. */
  private MediaTypeType(String string) {
    this.string = string;
  }

  /** {@inheritDoc} */
  @Override
  public String toString() {
    return this.string;
  }
}
