package eden.common.object;

/**
 * Consists of utility methods for operating on API objects.
 *
 * @author Brendon
 */
public class EDENObjects {

  /**
   * Ensures that the given dieable is not dead.
   *
   * @see Dieable#requireNonDead()
   */
  public static Dieable requireNonDead(Dieable dieable) {
    return dieable.requireNonDead();
  }

  /**
   * Ensures that the given nullifiable is not nullified.
   *
   * @see Nullifiable#requireNonNullified()
   */
  public static Nullifiable requireNonNullified(Nullifiable nullifiable) {
    return nullifiable.requireNonNullified();
  }

  /** To prevent instantiations of this class. */
  protected EDENObjects() {
  }
}
