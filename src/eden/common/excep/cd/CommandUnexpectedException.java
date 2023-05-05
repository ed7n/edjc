package eden.common.excep.cd;

/**
 * Thrown when an optional command is unexpected at its place.
 *
 * @author Brendon
 */
public class CommandUnexpectedException extends BadCueSheetException {

  /** Suggested remedy. */
  protected static final String REMEDY =
    "Consult the website for command restrictions.";

  /** Makes an instance with the given line number and command label. */
  public CommandUnexpectedException(long line, String command) {
    super(line, makeUnexpectedProblem("`" + command + "` command"), REMEDY);
  }

  /** To prevent null instantiations of this class. */
  protected CommandUnexpectedException() {}
}
