package eden.common.model.plaintext;

import static eden.common.shared.Constants.EOL;

import java.io.IOException;
import java.io.Reader;
import java.util.Objects;

/**
 * Defines a line ending.
 *
 * @author Brendon
 */
public class LineEnding {

  /** Carriage Return (CR, "Macintosh"). */
  public static final LineEnding CR
      = new LineEnding("\r", "CR", "Carriage Return", "Macintosh");

  /** Carriage Return Line Feed (CRLF, "Windows"). */
  public static final LineEnding CRLF
      = new LineEnding("\r\n", "CRLF", "Carriage Return Line Feed", "Windows");

  /** Line Feed (LF, "Unix"). */
  public static final LineEnding LF
      = new LineEnding("\n", "LF", "Line Feed", "Unix");

  /** System line ending from {@code System.lineSeparator()}. */
  public static final LineEnding SYSTEM = parse(EOL);

  /** Parses a line ending from the given line. */
  public static LineEnding parse(String line) {
    if (line == null)
      return null;
    if (line.endsWith(CRLF.toString()))
      return CRLF;
    if (line.endsWith(LF.toString()))
      return LF;
    if (line.endsWith(CR.toString()))
      return CR;
    return null;
  }

  /** Parses a line ending from the given reader. */
  public static LineEnding parse(Reader reader) throws IOException {
    int character;
    while (true) {
      character = reader.read();
      if (character == '\r')
        return reader.read() == '\n' ? CRLF : CR;
      if (character == '\n')
        return LF;
    }
  }

  /** Parses a line ending from the given name. */
  public static LineEnding parseName(String name) {
    if (name == null)
      return null;
    if (name.equalsIgnoreCase(CRLF.getName())
        || name.equalsIgnoreCase(CRLF.getLongName())
        || name.equalsIgnoreCase(CRLF.getSystemName()))
      return CRLF;
    if (name.equalsIgnoreCase(LF.getName())
        || name.equalsIgnoreCase(LF.getLongName())
        || name.equalsIgnoreCase(LF.getSystemName()))
      return LF;
    if (name.equalsIgnoreCase(CR.getName())
        || name.equalsIgnoreCase(CR.getLongName())
        || name.equalsIgnoreCase(CR.getSystemName()))
      return CR;
    return null;
  }

  /** String. */
  protected final String string;

  /** Abbreviated name. */
  protected final String name;

  /** Long name. */
  protected final String longName;

  /** System name. */
  protected final String systemName;

  /**
   * Makes an instance with the given string, abbreviated, long, and system
   * names.
   */
  protected LineEnding(
      String string, String name, String longName, String systemName) {
    this.string = string;
    this.name = name;
    this.longName = longName;
    this.systemName = systemName;
  }

  /** To prevent null instantiations of this class. */
  protected LineEnding() {
    this.string = null;
    this.name = null;
    this.longName = null;
    this.systemName = null;
  }

  /** Returns its abbreviated name ("LF"). */
  public String getName() {
    return this.name;
  }

  /** Returns its long name ("Line Feed"). */
  public String getLongName() {
    return this.longName;
  }

  /** Returns its system name ("Unix"). */
  public String getSystemName() {
    return this.systemName;
  }

  /** {@inheritDoc} */
  @Override
  public boolean equals(Object object) {
    return object == this || (object != null
        && object.getClass() == getClass() && equals((LineEnding) object));
  }

  /** Returns whether the given instance is equal to it. */
  protected boolean equals(LineEnding instance) {
    return instance.hashCode() == hashCode();
  }

  /** {@inheritDoc} */
  @Override
  public int hashCode() {
    return Objects.hash(this.longName, this.name, this.string, this.systemName);
  }

  /** Returns its string ("\n"). */
  @Override
  public String toString() {
    return this.string;
  }
}
