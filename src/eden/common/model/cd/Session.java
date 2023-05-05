package eden.common.model.cd;

import eden.common.util.CueSheets;
import eden.common.util.Numbers;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Defines a Compact Disc (CD) session layout in terms of a cuesheet.
 *
 * @author Brendon
 */
public class Session extends CDLayoutObject implements CDTextable {

  /** Tracks. */
  protected List<Track> tracks;
  /** Media Catalog Number (MCN). */
  protected String catalog;
  /** CD-TEXT file path. */
  protected String cdTextFile;
  /** Performer. */
  protected String performer;
  /** Songwriter. */
  protected String songwriter;
  /** Title. */
  protected String title;

  /** Makes an instance with one track. */
  public Session() {
    this(1, null, null, null, null, null);
  }

  /** Makes an instance with the given number of tracks. */
  public Session(int trackCount) {
    this(trackCount, null, null, null, null, null);
  }

  /** Makes an instance with the given arguments. */
  public Session(
    int trackCount,
    String catalog,
    String cdTextFile,
    String performer,
    String songwriter,
    String title
  ) {
    super();
    Numbers.requireNonNegative(trackCount);
    this.tracks = new ArrayList<>(trackCount);
    this.catalog = catalog;
    this.cdTextFile = cdTextFile;
    this.performer = performer;
    this.songwriter = songwriter;
    this.title = title;
  }

  /** Returns its list of tracks. */
  public List<Track> getTracks() {
    return this.tracks;
  }

  /** Returns its last track. */
  public Track getLastTrack() {
    return getTrack(getTracks().size() - 1);
  }

  /** Returns its track at the given index. */
  public Track getTrack(int index) {
    return getTracks().size() > index ? getTracks().get(index) : null;
  }

  /** Returns its last index of the last track. */
  public Index getLastIndex() {
    Track last = getLastTrack();
    return last != null ? last.getLastIndex() : null;
  }

  /** Adds the given track. */
  public boolean addTrack(Track track) {
    return getTracks().add(track);
  }

  /** Removes then returns its last track. */
  public Track removeLastTrack() {
    return hasTracks() ? getTracks().remove(getTracks().size() - 1) : null;
  }

  /** Returns its CATALOG argument. */
  public String getCatalog() {
    return this.catalog;
  }

  /** Sets its CATALOG argument. */
  public void setCatalog(String catalog) {
    this.catalog = catalog;
  }

  /** Unsets its CATALOG argument. */
  public void unsetCatalog() {
    setCatalog(null);
  }

  /** Returns its CDTEXTFILE argument. */
  public String getCdTextFile() {
    return this.cdTextFile;
  }

  /** Sets its CDTEXTFILE argument. */
  public void setCdTextFile(String cdTextFile) {
    this.cdTextFile = cdTextFile;
  }

  /** Unsets its CDTEXTFILE argument. */
  public void unsetCdTextFile() {
    setCdTextFile(null);
  }

  /** {@inheritDoc} */
  @Override
  public String getPerformer() {
    return this.performer;
  }

  /** {@inheritDoc} */
  @Override
  public void setPerformer(String performer) {
    this.performer = performer;
  }

  /** {@inheritDoc} */
  @Override
  public void unsetPerformer() {
    setPerformer(null);
  }

  /** {@inheritDoc} */
  @Override
  public String getSongwriter() {
    return this.songwriter;
  }

  /** {@inheritDoc} */
  @Override
  public void setSongwriter(String songwriter) {
    this.songwriter = songwriter;
  }

  /** {@inheritDoc} */
  @Override
  public void unsetSongwriter() {
    setSongwriter(null);
  }

  /** {@inheritDoc} */
  @Override
  public String getTitle() {
    return this.title;
  }

  /** {@inheritDoc} */
  @Override
  public void setTitle(String title) {
    this.title = title;
  }

  /** {@inheritDoc} */
  @Override
  public void unsetTitle() {
    setTitle(null);
  }

  /** {@inheritDoc} */
  @Override
  public void clearCdText() {
    unsetCdTextFile();
    unsetPerformer();
    unsetSongwriter();
    unsetTitle();
  }

  /** Returns whether its CATALOG argument is set. */
  public boolean hasCatalog() {
    return getCatalog() != null;
  }

  /** Returns whether its CDTEXTFILE argument is set. */
  public boolean hasCdTextFile() {
    return getCdTextFile() != null;
  }

  /** Returns whether it has tracks. */
  public boolean hasTracks() {
    return !getTracks().isEmpty();
  }

  /** {@inheritDoc} */
  @Override
  public boolean hasPerformer() {
    return getPerformer() != null;
  }

  /** {@inheritDoc} */
  @Override
  public boolean hasSongwriter() {
    return getSongwriter() != null;
  }

  /** {@inheritDoc} */
  @Override
  public boolean hasTitle() {
    return getTitle() != null;
  }

  /** {@inheritDoc} */
  @Override
  public boolean hasCdText() {
    return hasCdTextFile() || hasPerformer() || hasSongwriter() || hasTitle();
  }

  /** {@inheritDoc} */
  @Override
  public List<CueSheetStatement> toStatements() {
    List<CueSheetStatement> out = new LinkedList<>();
    getRems().forEach(rem -> out.add(new CueSheetStatement(REM, rem)));
    if (hasCatalog()) {
      out.add(new CueSheetStatement(CATALOG, getCatalog()));
    }
    if (hasCdTextFile()) {
      out.add(
        new CueSheetStatement(
          CDTEXTFILE,
          CueSheets.ensureQuote(getCdTextFile())
        )
      );
    }
    if (hasPerformer()) {
      out.add(
        new CueSheetStatement(PERFORMER, CueSheets.ensureQuote(getPerformer()))
      );
    }
    if (hasSongwriter()) {
      out.add(
        new CueSheetStatement(
          SONGWRITER,
          CueSheets.ensureQuote(getSongwriter())
        )
      );
    }
    if (hasTitle()) {
      out.add(new CueSheetStatement(TITLE, CueSheets.ensureQuote(getTitle())));
    }
    forEachCustom((command, argument) -> {
      out.add(new CueSheetStatement(command, argument));
      return argument;
    });
    getTracks().forEach(track -> out.addAll(track.toStatements()));
    return out;
  }

  /** {@inheritDoc} */
  @Override
  public void nullifyObject() {
    if (isObjectNullified()) {
      return;
    }
    this.tracks.clear();
    this.tracks = null;
    super.nullifyObject();
  }

  /** {@inheritDoc} */
  @Override
  public boolean equals(Object object) {
    return (
      object == this ||
      (
        object != null &&
        object.getClass() == getClass() &&
        equals((Session) object)
      )
    );
  }

  /** Returns whether the given instance is equal to it. */
  protected boolean equals(Session instance) {
    return instance.hashCode() == hashCode();
  }

  /** {@inheritDoc} */
  @Override
  public int hashCode() {
    return Objects.hash(this.custom, this.remarks, this.tracks);
  }
}
