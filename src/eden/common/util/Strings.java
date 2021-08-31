package eden.common.util;

import static eden.common.shared.Constants.NUL_STRING;

import eden.common.excep.string.StringEmptyException;
import java.util.Objects;

/**
 * Consists of utility methods for operating on strings.
 *
 * @author Brendon
 */
public class Strings {

  /** Returns the given string, passing null returns empty. */
  public static String emptyOrAsIs(String string) {
    return defaultOrAsIs(NUL_STRING, string);
  }

  /**
   * Trims and returns the given string, passing null returns empty.
   *
   * @see java.lang.String#trim()
   */
  public static String emptyOrTrim(String string) {
    return defaultOrTrim(NUL_STRING, string);
  }

  /**
   * Trims and returns the given string, passing null returns itself.
   *
   * @see java.lang.String#trim()
   */
  public static String nullOrTrim(String string) {
    return defaultOrTrim(null, string);
  }

  /** Returns the given string, passing null returns the given default. */
  public static String defaultOrAsIs(String defaultt, String string) {
    return string == null ? defaultt : string;
  }

  /**
   * Trims and returns the given string, passing null returns the given default.
   *
   * @see java.lang.String#trim()
   */
  public static String defaultOrTrim(String defaultt, String string) {
    return string == null ? defaultt : string.trim();
  }

  /** Ensures that the given string is not empty nor null. */
  public static String requireNonEmpty(String string, String label) {
    Objects.requireNonNull(string, label);
    if (string.isEmpty())
      throw new StringEmptyException(label);
    return string;
  }

  /** Returns whether the given string is not empty nor null. */
  public static boolean isNullOrEmpty(String string) {
    return string == null || string.isEmpty();
  }

  /** To prevent instantiations of this class. */
  protected Strings() {
  }
}
