package eden.common.excep;

/**
 * Utility methods for operating on EDENExceptions.
 *
 * @author Brendon
 */
public class EDENExceptions {

  /** Makes a subject message with the given hierarchical nodes. */
  public static String makeSubject(String... nodes) {
    return String.join(": ", nodes);
  }

  /** Makes a misformat problem message with the given noun. */
  public static String makeMisformatProblem(String noun) {
    return "The " + noun + " is misformatted.";
  }

  /** Makes a misnumber problem message with the given noun. */
  public static String makeMisnumberProblem(String noun) {
    return "The " + noun + " is misnumbered.";
  }
}
