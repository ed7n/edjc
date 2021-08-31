package eden.common.model.media;

import static eden.common.shared.Constants.NUL_STRING;

import eden.common.object.Nullifiable;
import eden.common.util.Strings;
import java.io.File;
import java.util.Objects;

/**
 * Defines a media file.
 *
 * @author Brendon
 */
public abstract class MediaFile implements Nullifiable {

  /** File. */
  protected File file;

  /** Title. */
  protected String title;

  /** Returns its media type. */
  public abstract MediaType getType();

  /** Returns its file. */
  public File getFile() {
    return this.file;
  }

  /** Sets its file. */
  public void setFile(File file) {
    this.file = file;
  }

  /** Unsets its file. */
  public void unsetFile() {
    setFile(null);
  }

  /** Returns its title. */
  public String getTitle() {
    return this.title;
  }

  /** Sets its title. */
  public void setTitle(String title) {
    this.title = title;
  }

  /** Sets its title to that of its file. */
  public void setTitleToFile() {
    setTitle(hasFile() ? getFile().getName() : NUL_STRING);
  }

  /** Unsets its title. */
  public void unsetTitle() {
    setTitle(NUL_STRING);
  }

  /** Returns whether it has a file. */
  public boolean hasFile() {
    return getFile() != null;
  }

  /** Returns whether its title is set. */
  public boolean hasTitle() {
    return !Strings.isNullOrEmpty(getTitle());
  }

  /** {@inheritDoc} */
  @Override
  public void nullifyObject() {
    if (isObjectNullified())
      return;
    this.file = null;
    this.title = null;
  }

  /** {@inheritDoc} */
  @Override
  public boolean isObjectNullified() {
    return this.file == null && this.title == null;
  }

  /** {@inheritDoc} */
  @Override
  public boolean equals(Object object) {
    return object == this || (object != null
        && object.getClass() == getClass() && equals((MediaFile) object));
  }

  /** Returns whether the given instance is equal to it. */
  protected boolean equals(MediaFile instance) {
    return instance.hashCode() == hashCode();
  }

  /** {@inheritDoc} */
  @Override
  public int hashCode() {
    return Objects.hash(this.file, this.title);
  }

  /** {@inheritDoc} */
  @Override
  public String toString() {
    return hasFile() ? getFile().getAbsolutePath() : getTitle();
  }
}
