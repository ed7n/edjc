package eden.common.model.cd;

import static eden.common.shared.Constants.EOL;
import static eden.common.shared.Constants.SPACE;
import static eden.common.shared.Constants.STRING_CAPACITY;

import eden.common.model.plaintext.LineEnding;
import eden.common.object.Nullifiable;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * Defines a Compact Disc (CD) layout object in terms of a cuesheet.
 *
 * @author Brendon
 */
public abstract class CDLayoutObject implements Nullifiable {

  /** CATALOG command. */
  public static final String CATALOG = "CATALOG";

  /** CDTEXTFILE command. */
  public static final String CDTEXTFILE = "CDTEXTFILE";

  /** FILE command. */
  public static final String FILE = "FILE";

  /** FLAGS command. */
  public static final String FLAGS = "FLAGS";

  /** INDEX command. */
  public static final String INDEX = "INDEX";

  /** ISRC command. */
  public static final String ISRC = "ISRC";

  /** PERFORMER command. */
  public static final String PERFORMER = "PERFORMER";

  /** POSTGAP command. */
  public static final String POSTGAP = "POSTGAP";

  /** PREGAP command. */
  public static final String PREGAP = "PREGAP";

  /** REM command. */
  public static final String REM = "REM";

  /** SONGWRITER command. */
  public static final String SONGWRITER = "SONGWRITER";

  /** TITLE command. */
  public static final String TITLE = "TITLE";

  /** TRACK command. */
  public static final String TRACK = "TRACK";

  /** Indentation. */
  public static final String INDENT = SPACE + SPACE;

  /** Two indentations. */
  public static final String INDENT_2 = INDENT + INDENT;

  /** Indented FLAGS cuesheet command. */
  protected static final String INDENT_FLAGS = INDENT_2 + FLAGS;

  /** Indented ISRC cuesheet command. */
  protected static final String INDENT_ISRC = INDENT_2 + ISRC;

  /** Indented REM cuesheet command. */
  protected static final String INDENT_REM = INDENT + REM;

  /** Double-indented REM cuesheet command. */
  protected static final String INDENT_2_REM = INDENT + INDENT_REM;

  /** Indented TITLE cuesheet command. */
  protected static final String INDENT_TITLE = INDENT_2 + TITLE;

  /** Indented PERFORMER cuesheet command. */
  protected static final String INDENT_PERFORMER = INDENT_2 + PERFORMER;

  /** Indented PREGAP cuesheet command. */
  protected static final String INDENT_PREGAP = INDENT_2 + PREGAP;

  /** Indented POSTGAP cuesheet command. */
  protected static final String INDENT_POSTGAP = INDENT_2 + POSTGAP;

  /** Indented SONGWRITER cuesheet command. */
  protected static final String INDENT_SONGWRITER = INDENT_2 + SONGWRITER;

  /** Custom statements. */
  protected Map<String, String> custom = new HashMap<>();

  /** REM arguments. */
  protected Deque<String> remarks = new LinkedList<>();

  /** Applies the given BiFunction to its custom statements. */
  public void forEachCustom(
      BiFunction<String, String, String> function) {
    this.custom.forEach((key, value) -> {
      String newValue = function.apply(key, value);
      if (newValue == null ? value != null : !newValue.equals(value))
        this.custom.put(key, newValue);
    });
  }

  /** Returns its list of REM arguments. */
  public Deque<String> getRems() {
    return this.remarks;
  }

  /** Returns its last REM argument. */
  public String getLastRem() {
    return getRems().peekLast();
  }

  /** Adds the given REM argument. */
  public boolean addRem(String rem) {
    return getRems().add(rem);
  }

  /** Removes then returns its last REM argument. */
  public String removeLastRem() {
    return getRems().pollLast();
  }

  /** Returns the argument of the given command of a custom statement. */
  public String getCustom(String command) {
    return this.custom.get(command);
  }

  /** Sets the given custom statement. */
  public void setCustom(String command, String argument) {
    this.custom.put(command, argument);
  }

  /** Removes the given custom statement. */
  public void unsetCustom(String command) {
    this.custom.remove(command);
  }

  /** Returns whether it has the given custom statement. */
  public boolean hasCustom(String command) {
    return this.custom.containsKey(command);
  }

  /** Returns whether it has custom statements. */
  public boolean hasCustoms() {
    return this.custom.size() > 0;
  }

  /** Returns whether it has REM arguments. */
  public boolean hasRems() {
    return !getRems().isEmpty();
  }

  /**
   * Returns its string representation as a list of cuesheet statements.
   *
   * @see #toString()
   */
  public abstract List<CueSheetStatement> toStatements();

  /** {@inheritDoc} */
  @Override
  public void nullifyObject() {
    if (isObjectNullified())
      return;
    this.custom.clear();
    this.remarks.clear();
    this.custom = null;
    this.remarks = null;
  }

  /** {@inheritDoc} */
  @Override
  public boolean isObjectNullified() {
    return this.custom == null;
  }

  /** {@inheritDoc} */
  @Override
  public abstract boolean equals(Object object);

  /** {@inheritDoc} */
  @Override
  public abstract int hashCode();

  /** {@inheritDoc} */
  @Override
  public String toString() {
    return toString(EOL);
  }

  /** Returns its string representation with the given line ending. */
  public String toString(LineEnding lineEnding) {
    return toString(lineEnding.toString());
  }

  /** Returns its string representation with the given line ending. */
  public String toString(String lineEnding) {
    if (lineEnding == null)
      lineEnding = System.lineSeparator();
    StringBuilder out = new StringBuilder(STRING_CAPACITY);
    for (CueSheetStatement statement : toStatements())
      out.append(statement.toString()).append(lineEnding);
    return out.toString();
  }
}
