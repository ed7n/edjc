package eden.common.excep.cd;

import static eden.common.shared.Constants.SPACE;

import eden.common.excep.EDENRuntimeException;
import eden.common.model.cd.CDLayoutObject;
import eden.common.util.Numbers;

/**
 * Thrown when something in a cuesheet is malformed.
 *
 * @author Brendon
 * @see eden.common.model.cd.CueSheet
 */
public class BadCueSheetException extends EDENRuntimeException {

  /** Subject label: at line. */
  protected static final String AT_LINE = "at line";

  /** Subject label: line. */
  protected static final String LINE = "Line";

  /** Makes a subject string with the given track number. */
  public static String makeSubject(int track) {
    return CDLayoutObject.TRACK + SPACE + Numbers.toString2Digits(track);
  }

  /** Makes a subject string with the given line number. */
  public static String makeSubject(long line) {
    return LINE + SPACE + Long.toString(line);
  }

  /** Makes a subject string with the given track and index numbers. */
  public static String makeSubject(int track, int index) {
    return CDLayoutObject.TRACK + SPACE + Numbers.toString2Digits(track) + SPACE
        + CDLayoutObject.INDEX + SPACE + Numbers.toString2Digits(index);
  }

  /** Makes a subject string with the given track and line numbers. */
  public static String makeSubject(int track, long line) {
    return CDLayoutObject.TRACK + SPACE + Numbers.toString2Digits(track) + SPACE
        + AT_LINE + SPACE + Long.toString(line);
  }

  /** Makes a subject string with the given track, index, and line numbers. */
  public static String makeSubject(int track, int index, long line) {
    return CDLayoutObject.TRACK + SPACE + Numbers.toString2Digits(track) + SPACE
        + CDLayoutObject.INDEX + SPACE + Numbers.toString2Digits(index) + SPACE
        + AT_LINE + SPACE + Long.toString(line);
  }

  /** Makes a redefinition problem message with the given noun. */
  protected static String makeAgainProblem(String noun) {
    return "`" + noun + "` is defined again when it has already been defined.";
  }

  /** Makes a redefinition remedy message with the given noun. */
  protected static String makeAgainRemedy(String noun) {
    return "Erase excess `" + noun + "` definitions.";
  }

  /**
   * Makes a misnumber remedy message with the given noun, inclusive minimum and
   * maximum values.
   */
  protected static String makeMisnumberRemedy(
      String noun, int minimum, int maximum) {
    return "Correct "
        + noun + " to be within [" + minimum + ", " + maximum + "].";
  }

  /** Makes an unexpected problem message with the given noun. */
  protected static String makeUnexpectedProblem(String noun) {
    return "The " + noun + " is unexpected at its place.";
  }

  /** Makes an instance with the given track number, problem, and remedy. */
  protected BadCueSheetException(int track, String problem, String remedy) {
    this(makeSubject(track), problem, remedy);
  }

  /** Makes an instance with the given line number, problem, and remedy. */
  protected BadCueSheetException(long line, String problem, String remedy) {
    this(makeSubject(line), problem, remedy);
  }

  /**
   * Makes an instance with the given track and index numbers, problem, and
   * remedy.
   */
  protected BadCueSheetException(
      int track, int index, String problem, String remedy) {
    this(makeSubject(track, index), problem, remedy);
  }

  /**
   * Makes an instance with the given track and line numbers, problem, and
   * remedy.
   */
  protected BadCueSheetException(
      int track, long line, String problem, String remedy) {
    this(makeSubject(track, line), problem, remedy);
  }

  /**
   * Makes an instance with the given track, index, and line numbers, problem,
   * and remedy.
   */
  protected BadCueSheetException(
      int track, int index, long line, String problem, String remedy) {
    this(makeSubject(track, index, line), problem, remedy);
  }

  /** Makes an instance with the given subject, problem, and remedy. */
  protected BadCueSheetException(
      String subject, String problem, String remedy) {
    super(subject, problem, remedy);
  }

  /** To prevent null instantiations of this class. */
  protected BadCueSheetException() {
  }
}
