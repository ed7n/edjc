package eden.common.model.cd;

import static eden.common.shared.Constants.NUL_INT;

import eden.common.util.CDDAFrame;
import eden.common.util.CueSheets;
import eden.common.util.Numbers;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Defines a Compact Disc (CD) track in terms of a cuesheet.
 *
 * @author Brendon
 */
public class Track extends CDLayoutObject implements CDTextable {

  /** Null instance. */
  public static final Track nul = new Track(NUL_INT);

  /** Maximum track number in a CD. */
  public static final int MAX_NUMBER = 99;

  /** Minimum track number in a CD. */
  public static final int MIN_NUMBER = 1;

  /** Maximum number of tracks in a CD. */
  public static final int MAX_COUNT = MAX_NUMBER - MIN_NUMBER + 1;

  /** Returns whether the given track number is valid. */
  public static boolean isNumberValid(int track) {
    return track >= MIN_NUMBER && track <= MAX_NUMBER;
  }

  /** Indexes. */
  protected List<Index> indexes;

  /** Flags. */
  protected List<String> flags;

  /** International Standard Recording Code (ISRC). */
  protected String isrc;

  /** Performer. */
  protected String performer;

  /** Songwriter. */
  protected String songwriter;

  /** Title. */
  protected String title;

  /** Type. */
  protected String type;

  /** Number. */
  protected int number;

  /** Postgap. */
  protected int postgap = NUL_INT;

  /** Pregap. */
  protected int pregap = NUL_INT;

  /** Makes an instance with the given track number. */
  public Track(int number) {
    this(number, 1, NUL_INT, NUL_INT,
        new LinkedList<>(), null, null, null, null, null);
  }

  /** Makes an instance with the given track number and type. */
  public Track(int number, String type) {
    this(number, 1, NUL_INT, NUL_INT,
        new LinkedList<>(), null, null, null, null, type);
  }

  /** Makes an instance with the given arguments. */
  public Track(
      int number, int indexCount, int postgap, int pregap,
      List<String> flags, String isrc, String performer,
      String songwriter, String title, String type) {
    super();
    Numbers.requireNonNegative(indexCount);
    this.custom = new HashMap<>(5);
    this.indexes = new ArrayList<>(indexCount);
    this.flags = flags;
    this.isrc = isrc;
    this.performer = performer;
    this.songwriter = songwriter;
    this.title = title;
    this.type = type;
    this.number = number;
    this.postgap = postgap;
    this.pregap = pregap;
  }

  /** To prevent null instantiations of this class. */
  protected Track() {
  }

  /** Returns its list of indexes. */
  public List<Index> getIndexes() {
    return this.indexes;
  }

  /** Returns its last index. */
  public Index getLastIndex() {
    return getIndex(getIndexes().size() - 1);
  }

  /** Returns its index at the given index number. */
  public Index getIndex(int index) {
    return getIndexes().size() > index ? getIndexes().get(index) : null;
  }

  /** Adds the given index. */
  public boolean addIndex(Index index) {
    return getIndexes().add(index);
  }

  /** Removes then returns its last index. */
  public Index removeLastIndex() {
    return hasIndexes() ? getIndexes().remove(getIndexes().size() - 1) : null;
  }

  /** Removes all of its indexes. */
  public void clearIndexes() {
    getIndexes().clear();
  }

  /** Returns its FLAGS argument. */
  public List<String> getFlags() {
    return this.flags;
  }

  /** Returns its flag at the given index. */
  public String getFlag(int index) {
    return getFlags().size() > index ? getFlags().get(index) : null;
  }

  /** Adds the given flag to its FLAGS argument. */
  public boolean addFlag(String flag) {
    return getFlags().add(flag);
  }

  /** Clears its FLAGS argument. */
  public void clearFlags() {
    getFlags().clear();
  }

  /** Returns its ISRC argument. */
  public String getIsrc() {
    return this.isrc;
  }

  /** Sets its ISRC argument. */
  public void setIsrc(String isrc) {
    this.isrc = isrc;
  }

  /** Unsets its ISRC argument. */
  public void unsetIsrc() {
    setIsrc(null);
  }

  /** Returns its track number. */
  public int getNumber() {
    return this.number;
  }

  /** Sets its track number. */
  public void setNumber(int number) {
    this.number = number;
  }

  /** Returns its track PREGAP. */
  public int getPregap() {
    return this.pregap;
  }

  /** Sets its track PREGAP. */
  public void setPregap(int pregap) {
    this.pregap = pregap;
  }

  /** Unsets its track PREGAP. */
  public void unsetPregap() {
    this.pregap = NUL_INT;
  }

  /** Returns its track POSTGAP. */
  public int getPostgap() {
    return this.postgap;
  }

  /** Sets its track POSTGAP. */
  public void setPostgap(int postgap) {
    this.postgap = postgap;
  }

  /** Unsets its track POSTGAP. */
  public void unsetPostgap() {
    this.postgap = NUL_INT;
  }

  /** Returns its track type argument. */
  public String getType() {
    return this.type;
  }

  /** Sets its track type argument. */
  public void setType(String type) {
    this.type = type;
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
    unsetIsrc();
    unsetPerformer();
    unsetSongwriter();
    unsetTitle();
  }

  /** Returns whether its FLAGS argument is set. */
  public boolean hasFlags() {
    return !getFlags().isEmpty();
  }

  /** Returns whether it has indexes. */
  public boolean hasIndexes() {
    return getIndexes().size() > 0;
  }

  /** Returns whether its ISRC argument is set. */
  public boolean hasIsrc() {
    return getIsrc() != null;
  }

  /** Returns whether its track PREGAP is set. */
  public boolean hasPregap() {
    return getPregap() != NUL_INT;
  }

  /** Returns whether its track POSTGAP is set. */
  public boolean hasPostgap() {
    return getPostgap() != NUL_INT;
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
    return hasIsrc() || hasPerformer() || hasSongwriter() || hasTitle();
  }

  /** {@inheritDoc} */
  @Override
  public List<CueSheetStatement> toStatements() {
    List<CueSheetStatement> out = new LinkedList<>();
    if (hasIndexes() && getIndex(0).hasFilePath())
      out.add(new CueSheetStatement(
          FILE,
          CueSheets.ensureQuote(getIndex(0).getFilePath()),
          getIndex(0).getFileType()));
    out.add(new CueSheetStatement(
        INDENT + TRACK, Numbers.toString2Digits(getNumber()), getType()));
    if (hasFlags())
      out.add(new CueSheetStatement(
          INDENT_FLAGS, String.join(" ", getFlags())));
    getRems().forEach(rem -> out.add(new CueSheetStatement(INDENT_2_REM, rem)));
    if (hasIsrc())
      out.add(new CueSheetStatement(INDENT_ISRC, getIsrc()));
    if (hasTitle())
      out.add(new CueSheetStatement(
          INDENT_TITLE, CueSheets.ensureQuote(getTitle())));
    if (hasPerformer())
      out.add(new CueSheetStatement(
          INDENT_PERFORMER, CueSheets.ensureQuote(getPerformer())));
    if (hasSongwriter())
      out.add(new CueSheetStatement(
          INDENT_SONGWRITER, CueSheets.ensureQuote(getSongwriter())));
    forEachCustom((command, argument) -> {
      out.add(new CueSheetStatement(command, argument));
      return argument;
    });
    if (hasPregap())
      out.add(new CueSheetStatement(
          INDENT_PREGAP, CDDAFrame.toTimeCode(getPregap())));
    if (hasIndexes())
      out.addAll(getIndex(0).toStatements());
    for (int i = 1; i < getIndexes().size(); i++) {
      if (getIndex(i).hasFilePath())
        out.add(new CueSheetStatement(
            FILE,
            CueSheets.ensureQuote(getIndex(i).getFilePath()),
            getIndex(i).getFileType()));
      out.addAll(getIndex(i).toStatements());
    }
    if (hasPostgap())
      out.add(new CueSheetStatement(
          INDENT_POSTGAP, CDDAFrame.toTimeCode(getPregap())));
    return out;
  }

  /** {@inheritDoc} */
  @Override
  public void nullifyObject() {
    if (isObjectNullified())
      return;
    this.indexes.clear();
    this.indexes = null;
    this.number = NUL_INT;
    this.pregap = NUL_INT;
    this.postgap = NUL_INT;
    super.nullifyObject();
  }

  /** {@inheritDoc} */
  @Override
  public boolean equals(Object object) {
    return object == this || (object != null
        && object.getClass() == getClass() && equals((Track) object));
  }

  /** Returns whether the given instance is equal to it. */
  protected boolean equals(Track instance) {
    return instance.hashCode() == hashCode();
  }

  /** {@inheritDoc} */
  @Override
  public int hashCode() {
    return Objects.hash(this.custom, this.indexes,
        this.number, this.postgap, this.pregap, this.remarks, this.type);
  }
}
