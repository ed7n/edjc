package eden.common.model.cd;

import static eden.common.shared.Constants.NUL_STRING;
import static eden.common.shared.Constants.SPACE;

import eden.common.object.Nullifiable;
import java.util.Arrays;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Consists of a list of argument strings and a method that turns it into a
 * space-delimited string.
 *
 * @author Brendon
 */
public class CueSheetStatement implements Nullifiable {

  /**
   * Length of the longest fix-width cuesheet statement. It is the SONGWRITER
   * command, up to 80 characters plus quotation marks and a space delimiter.
   * Note that FILE and REM statement can be longer than this.
   */
  public static final int LINE_WIDTH = 10 + 80 + 2 + 1;

  /** List of arguments. */
  protected Deque<String> arguments;

  /** Makes an instance with the given arguments. */
  public CueSheetStatement(String... arguments) {
    this.arguments = arguments != null
        ? new LinkedList<>(Arrays.asList(arguments))
        : new LinkedList<>();
  }

  /** Returns its list of arguments. */
  public Deque<String> getArguments() {
    return this.arguments;
  }

  /** Returns its last argument. */
  public String getLastArgument() {
    return getArguments().peekLast();
  }

  /** Adds the given argument. */
  public boolean addArgument(String argument) {
    return getArguments().add(argument);
  }

  /** Removes then returns its last argument. */
  public String removeLastArgument() {
    return getArguments().pollLast();
  }

  /** Returns whether it has arguments. */
  public boolean hasArguments() {
    return !getArguments().isEmpty();
  }

  /** {@inheritDoc} */
  @Override
  public void nullifyObject() {
    if (isObjectNullified())
      return;
    this.arguments.clear();
    this.arguments = null;
  }

  /** {@inheritDoc} */
  @Override
  public boolean isObjectNullified() {
    return getArguments() == null;
  }

  /** {@inheritDoc} */
  @Override
  public boolean equals(Object object) {
    return object == this || (object != null
        && object.getClass() == getClass()
        && equals((CueSheetStatement) object));
  }

  /** Returns whether the given instance is equal to it. */
  protected boolean equals(CueSheetStatement instance) {
    return instance.hashCode() == hashCode();
  }

  /** {@inheritDoc} */
  @Override
  public int hashCode() {
    return this.arguments.hashCode();
  }

  /** {@inheritDoc} */
  @Override
  public String toString() {
    if (!hasArguments())
      return NUL_STRING;
    StringBuilder builder = new StringBuilder(LINE_WIDTH);
    Iterator<String> iterator = getArguments().iterator();
    String argument;
    while (true) {
      argument = iterator.next();
      if (argument != null)
        builder.append(argument);
      if (!iterator.hasNext())
        break;
      builder.append(SPACE);
    }
    return builder.toString();
  }
}
