package eden.common.object;

/**
 * A revivable can be revived from abnormalities.
 *
 * Classes implementing this interface must be able to modify their object
 * fields to correct from abnormalities.
 *
 * @author Brendon
 */
public interface Revivable {
  /** Corrects itself from abnormalities and returns its success. */
  boolean reviveObject();
  /** Returns the number of successful revives. */
  int getObjectReviveCount();
  /** Returns whether it is possible to revive. */
  boolean isObjectRevivable();
}
