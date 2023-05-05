package eden.common.model.plaintext;

import eden.common.model.media.MediaFile;
import eden.common.model.media.MediaType;
import eden.common.model.media.MediaTypeType;
import eden.common.object.Nullifiable;
import eden.common.util.Strings;
import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * Defines a human-readable text file.
 *
 * @author Brendon
 */
public class PlainText extends MediaFile implements Nullifiable {

  /** Default character set. */
  public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
  /** Byte order mark. */
  public static final char BOM = '\uFEFF';
  /** Media type. */
  public static final MediaType MEDIA_TYPE = new MediaType(
    MediaTypeType.TEXT,
    "plain"
  );
  /** Character set. */
  protected Charset charset;
  /** Line ending. */
  protected LineEnding lineEnding;

  /**
   * Makes an instance with the default character set and system line ending.
   */
  public PlainText() {
    this(null, null, DEFAULT_CHARSET, LineEnding.SYSTEM);
  }

  /**
   * Makes an instance with the given title, default character set, and system
   * line ending.
   */
  public PlainText(String title) {
    this(null, title, DEFAULT_CHARSET, LineEnding.SYSTEM);
  }

  /**
   * Makes an instance with the given file, default character set, and system
   * line ending.
   */
  public PlainText(File file) {
    this(file, file.getName(), DEFAULT_CHARSET, LineEnding.SYSTEM);
  }

  /** Makes an instance with the given character set and line ending. */
  public PlainText(Charset charset, LineEnding lineEnding) {
    this(null, null, charset, lineEnding);
  }

  /** Makes an instance with the given title, character set, and line ending. */
  public PlainText(String title, Charset charset, LineEnding lineEnding) {
    this(null, title, charset, lineEnding);
  }

  /** Makes an instance with the given file, character set, and line ending. */
  public PlainText(File file, Charset charset, LineEnding lineEnding) {
    this(file, file.getName(), charset, lineEnding);
  }

  /**
   * Makes an instance with the given title, file, character set, and line
   * ending.
   */
  public PlainText(
    File file,
    String title,
    Charset charset,
    LineEnding lineEnding
  ) {
    this.charset = charset;
    this.file = file;
    this.lineEnding = lineEnding;
    this.title = Strings.emptyOrAsIs(title);
  }

  /** Sets its file and its title to those of the given file. */
  public void setFileAndTitle(File file) {
    setFile(file);
    setTitleToFile();
  }

  /** Returns its character set. */
  public Charset getCharset() {
    return this.charset;
  }

  /** Sets its character set. */
  public void setCharset(Charset charset) {
    this.charset = charset;
  }

  /** Returns its line ending. */
  public LineEnding getLineEnding() {
    return this.lineEnding;
  }

  /** Sets its line ending. */
  public void setLineEnding(LineEnding lineEnding) {
    this.lineEnding = lineEnding;
  }

  /** Returns whether its character set is set. */
  public boolean hasCharset() {
    return getCharset() != null;
  }

  /** Returns whether its line ending is set. */
  public boolean hasLineEnding() {
    return getLineEnding() != null;
  }

  /** {@inheritDoc} */
  @Override
  public MediaType getType() {
    return MEDIA_TYPE;
  }

  /** {@inheritDoc} */
  @Override
  public void nullifyObject() {
    if (isObjectNullified()) {
      return;
    }
    this.charset = null;
    this.lineEnding = null;
    super.nullifyObject();
  }

  /** {@inheritDoc} */
  @Override
  public boolean equals(Object object) {
    return (
      object == this ||
      (
        object != null &&
        object.getClass() == getClass() &&
        equals((PlainText) object)
      )
    );
  }

  /** Returns whether the given instance is equal to it. */
  protected boolean equals(PlainText instance) {
    return instance.hashCode() == hashCode();
  }

  /** {@inheritDoc} */
  @Override
  public int hashCode() {
    return Objects.hash(this.charset, this.file, this.lineEnding, this.title);
  }
}
