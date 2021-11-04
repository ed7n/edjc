package eden.common.object;

import eden.common.excep.DeadObjectUseException;

/**
 * A dieable is marked dead if something abnormal happens to it.
 *
 * Classes implementing this interface usually operates on external resources
 * that may result in abnormalities. When such are detected, they must mark
 * their objects dead, preventing further operations for safety.
 *
 * @author Brendon
 */
public interface Dieable {

  /**
   * Ensures that it is not dead.
   *
   * @throws DeadObjectUseException If it is dead.
   */
  default Dieable requireNonDead() throws DeadObjectUseException {
    if (isObjectDead())
      throw new DeadObjectUseException(toString());
    return this;
  }

  /** Marks itself dead with the given cause. */
  void die(Exception cause);

  /** Returns the cause of its death. */
  Exception getObjectDeathCause();

  /** Returns whether it is dead. */
  default boolean isObjectDead() {
    return getObjectDeathCause() != null;
  }
}
