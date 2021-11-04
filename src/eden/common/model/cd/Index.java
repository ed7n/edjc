package eden.common.model.cd;

import static eden.common.shared.Constants.NUL_INT;

import eden.common.util.CDDAFrame;
import eden.common.util.Numbers;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Defines a Compact Disc (CD) index in terms of a cuesheet.
 *
 * @author Brendon
 */
public class Index extends CDLayoutObject {

  /** Null instance. */
  public static final Index nul = new Index(NUL_INT, NUL_INT);

  /** Maximum frame number in a CD. */
  public static final int MAX_FRAME = 449999;

  /** Maximum index number in a CD. */
  public static final int MAX_NUMBER = 99;

  /** Minimum frame number in a CD. */
  public static final int MIN_FRAME = 0;

  /** Minimum index number in a CD. */
  public static final int MIN_NUMBER = 0;

  /** Maximum number of indexes in a track. */
  public static final int MAX_COUNT = MAX_NUMBER - MIN_NUMBER + 1;

  /** Returns whether the given frame number is valid. */
  public static boolean isFrameValid(int frame) {
    return frame >= MIN_FRAME && frame <= MAX_FRAME;
  }

  /** Returns whether the given index number is valid. */
  public static boolean isNumberValid(int index) {
    return index >= MIN_NUMBER && index <= MAX_NUMBER;
  }

  /** FILE path. */
  protected String filePath;

  /** FILE type. */
  protected String fileType;

  /** Number arguments. */
  protected int frame, number;

  /** Makes an instance with the given index and frame numbers. */
  public Index(int number, int frame) {
    this(number, frame, null, null);
  }

  /**
   * Makes an instance with the given index and frame numbers, file path, and
   * file type.
   */
  public Index(int number, int frame, String filePath, String fileType) {
    this.filePath = filePath;
    this.fileType = fileType;
    this.frame = frame;
    this.number = number;
  }

  /** To prevent null instantiations of this class. */
  protected Index() {
  }

  /** Sets its FILE path and type. */
  public void setFile(String filePath, String fileType) {
    setFilePath(filePath);
    setFileType(fileType);
  }

  /** Unsets its FILE path and type. */
  public void unsetFile() {
    setFile(null, null);
  }

  /** Returns its FILE path. */
  public String getFilePath() {
    return this.filePath;
  }

  /** Sets its FILE path. */
  public void setFilePath(String filePath) {
    this.filePath = filePath;
  }

  /** Unsets its FILE path. */
  public void unsetFilePath() {
    setFilePath(null);
  }

  /** Returns its FILE type. */
  public String getFileType() {
    return this.fileType;
  }

  /** Sets its FILE type. */
  public void setFileType(String fileType) {
    this.fileType = fileType;
  }

  /** Unsets its FILE type. */
  public void unsetFileType() {
    setFileType(null);
  }

  /** Returns its frame number. */
  public int getFrame() {
    return this.frame;
  }

  /** Sets its frame number. */
  public void setFrame(int frame) {
    this.frame = frame;
  }

  /** Returns its index number. */
  public int getNumber() {
    return this.number;
  }

  /** Sets its index number. */
  public void setNumber(int number) {
    this.number = number;
  }

  /** Returns whether its FILE path and type are set. */
  public boolean hasFile() {
    return hasFilePath() && hasFileType();
  }

  /** Returns whether its FILE path is set. */
  public boolean hasFilePath() {
    return getFilePath() != null;
  }

  /** Returns whether its FILE type is set. */
  public boolean hasFileType() {
    return getFilePath() != null;
  }

  /** {@inheritDoc} */
  @Override
  public List<CueSheetStatement> toStatements() {
    List<CueSheetStatement> out = new LinkedList<>();
    out.add(new CueSheetStatement(
        INDENT_2 + INDEX,
        Numbers.toString2Digits(getNumber()),
        CDDAFrame.toTimeCode(getFrame())));
    getRems().forEach(rem -> out.add(new CueSheetStatement(INDENT_2_REM, rem)));
    forEachCustom((command, argument) -> {
      out.add(new CueSheetStatement(command, argument));
      return argument;
    });
    return out;
  }

  /** {@inheritDoc} */
  @Override
  public void nullifyObject() {
    if (isObjectNullified())
      return;
    this.filePath = null;
    this.fileType = null;
    this.frame = NUL_INT;
    this.number = NUL_INT;
    super.nullifyObject();
  }

  /** {@inheritDoc} */
  @Override
  public boolean equals(Object object) {
    return object == this || (object != null
        && object.getClass() == getClass() && equals((Index) object));
  }

  /** Returns whether the given instance is equal to it. */
  protected boolean equals(Index instance) {
    return instance.hashCode() == hashCode();
  }

  /** {@inheritDoc} */
  @Override
  public int hashCode() {
    return Objects.hash(this.custom,
        this.filePath, this.fileType, this.frame, this.number, this.remarks);
  }
}
