package eden.common.model.cd;

import static eden.common.shared.Constants.EOL;
import static eden.common.shared.Constants.STRING_CAPACITY;

import eden.common.model.plaintext.PlainText;
import eden.common.object.Nullifiable;
import java.util.List;
import java.util.Objects;

/**
 * A definition of a Compact Disc (CD) session layout. An instance is defined by
 * a single session and its accompanying text file, thus this class does not
 * support multi-session layouts. Some applications got over this limitation
 * with REM statements.
 *
 * @author Brendon
 * @see eden.common.model.cd.Session
 * @see eden.common.model.plaintext.PlainText
 */
public class CueSheet implements Nullifiable {

  /** Session. */
  protected Session session;
  /** Text file. */
  protected PlainText file;

  /** Makes an instance with a new session. */
  public CueSheet() {
    this(new Session(), null);
  }

  /** Makes an instance with the given session. */
  public CueSheet(Session session) {
    this(session, null);
  }

  /** Makes an instance with the given session and file. */
  public CueSheet(Session session, PlainText file) {
    this.session = Objects.requireNonNull(session, "session");
    this.file = file;
  }

  /** Returns its session. */
  public Session getSession() {
    return this.session;
  }

  /** Returns its file. */
  public PlainText getFile() {
    return this.file;
  }

  /** Sets its file. */
  public void setFile(PlainText file) {
    this.file = file;
  }

  /** Returns the list of tracks in its session. */
  public List<Track> getTracks() {
    return getSession().getTracks();
  }

  /** Returns the track at the given index in its session. */
  public Track getTrack(int index) {
    return getSession().getTrack(index);
  }

  /** Returns whether it has a file. */
  public boolean hasFile() {
    return getFile() != null;
  }

  /** {@inheritDoc} */
  @Override
  public void nullifyObject() {
    if (isObjectNullified()) {
      return;
    }
    this.file = null;
    this.session = null;
  }

  /** {@inheritDoc} */
  @Override
  public boolean isObjectNullified() {
    return getSession() == null;
  }

  /** {@inheritDoc} */
  @Override
  public boolean equals(Object object) {
    return (
      object == this ||
      (
        object != null &&
        object.getClass() == getClass() &&
        equals((CueSheet) object)
      )
    );
  }

  /** Returns whether the given instance is equal to it. */
  protected boolean equals(CueSheet instance) {
    return instance.hashCode() == hashCode();
  }

  /** {@inheritDoc} */
  @Override
  public int hashCode() {
    return Objects.hash(this.file, this.session);
  }

  /** {@inheritDoc} */
  @Override
  public String toString() {
    StringBuilder out = new StringBuilder(STRING_CAPACITY);
    if (hasFile()) {
      out.append(getFile().toString()).append(":").append(EOL);
      if (getFile().hasLineEnding()) {
        out.append(getSession().toString(getFile().getLineEnding()));
      } else {
        out.append(getSession().toString());
      }
    } else {
      out.append(getSession().toString());
    }
    return out.toString();
  }
}
