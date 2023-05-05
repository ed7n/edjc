package eden.common.model.cd;

/**
 * Defines a CD-Textable Compact Disc (CD) layout object. Includes methods for
 * common arguments only.
 *
 * @author Brendon
 * @see eden.common.model.cd.CDLayoutObject
 */
public interface CDTextable {
  /** Returns its PERFORMER argument. */
  public String getPerformer();

  /** Sets its PERFORMER argument. */
  public void setPerformer(String performer);

  /** Unsets its PERFORMER argument. */
  public void unsetPerformer();

  /** Returns its SONGWRITER argument. */
  public String getSongwriter();

  /** Sets its SONGWRITER argument. */
  public void setSongwriter(String songwriter);

  /** Unsets its SONGWRITER argument. */
  public void unsetSongwriter();

  /** Returns its TITLE argument. */
  public String getTitle();

  /** Sets its TITLE argument. */
  public void setTitle(String title);

  /** Unsets its TITLE argument. */
  public void unsetTitle();

  /** Clears its CD-Text arguments. */
  public void clearCdText();

  /** Returns whether its PERFORMER argument is set. */
  public boolean hasPerformer();

  /** Returns whether its SONGWRITER argument is set. */
  public boolean hasSongwriter();

  /** Returns whether its TITLE argument is set. */
  public boolean hasTitle();

  /** Returns whether it has CD-Text. */
  public boolean hasCdText();
}
