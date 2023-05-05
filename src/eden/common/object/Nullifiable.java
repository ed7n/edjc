package eden.common.object;

import eden.common.excep.NullifiedObjectUseException;

/**
 * A nullifiable can be nullified to prevent further operations on it.
 *
 * Classes implementing this interface must be able to modify their object
 * fields to hide or obscure them. For semantic consistency, nullified objects
 * should not be and not able to be reused.
 *
 * @author Brendon
 */
public interface Nullifiable {
  /**
   * Ensures that it is not nullified.
   *
   * @throws NullifiedObjectUseException If it is nullified.
   */
  default Nullifiable requireNonNullified() {
    if (isObjectNullified()) {
      throw new NullifiedObjectUseException(toString());
    }
    return this;
  }

  /**
   * Nullifies itself, preventing further operations on it. This is usually done
   * by zeroing all its mutable fields.
   */
  void nullifyObject();
  /** Returns whether it is nullified. */
  boolean isObjectNullified();
}
