package eden.common.util;

import static eden.common.model.plaintext.PlainText.BOM;
import static eden.common.shared.Constants.NUL_INT;
import static eden.common.shared.Constants.NUL_LONG;
import static eden.common.shared.Constants.NUL_STRING;

import eden.common.excep.NullifiedObjectException;
import eden.common.excep.cd.BadCueSheetException;
import eden.common.excep.cd.CatalogAgainException;
import eden.common.excep.cd.CommandUnexpectedException;
import eden.common.excep.string.StringMisquoteException;
import eden.common.model.cd.CDLayoutObject;
import eden.common.model.cd.CueSheet;
import eden.common.model.cd.CueSheetStatement;
import eden.common.model.cd.Index;
import eden.common.model.cd.Session;
import eden.common.model.cd.Track;
import eden.common.model.plaintext.LineEnding;
import eden.common.model.plaintext.PlainText;
import eden.common.object.Dieable;
import eden.common.object.Nullifiable;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * Parses a cuesheet from either a reader or a file.
 *
 * @author Brendon
 * @see eden.common.model.cd.CueSheet
 */
public class CueSheetParser implements Closeable, Dieable, Nullifiable {

  /** Reader to parse from. */
  protected Reader reader;

  /** Resulting cuesheet. */
  protected CueSheet sheet;

  /** Working track. */
  protected Track track;

  /** Working index. */
  protected Index index;

  /** Operation mode. */
  protected Mode mode = Mode.SESSION;

  /** Throwable defining its death. */
  protected Throwable deathCause;

  /** Character buffer. */
  protected StringBuilder builder
      = new StringBuilder(CueSheetStatement.LINE_WIDTH);

  /** FILE argument accumulators. */
  protected String filePath, fileType;

  /** String accumulator. */
  protected String string;

  /** Integer accumulators. */
  protected int[] acc = new int[2];

  /** Line count. */
  protected long lineCount = 1;

  /** Whether its reader has reached the end-of-file. */
  protected boolean eof = false;

  /** Whether its reader is at an end-of-line. */
  protected boolean eol = false;

  /** Whether its reader is at an end-of-word. */
  protected boolean eow = false;

  /** Whether it is bypassing word detection. */
  protected boolean esc = false;

  /** Makes an instance with the given string. */
  public CueSheetParser(String string) {
    this(new StringReader(string));
  }

  /** Makes an instance with the given reader. */
  public CueSheetParser(Reader reader) {
    this.reader = reader;
    this.sheet = new CueSheet(new Session());
  }

  /** Makes an instance with the given file. */
  public CueSheetParser(File file) throws IOException {
    this.reader = Files.newBufferedReader(Paths.get(file.toURI()));
    this.sheet = new CueSheet(new Session(), new PlainText(file));
  }

  /** Parses a cuesheet from its reader into itself, then returns it. */
  public CueSheet parse() {
    if (isObjectDead())
      return null;
    if (isEof())
      return getSheet();
    try {
      parseLineEnding();
      if (Strings.isNullOrEmpty(readWord()))
        return getSheet();
      if (getString().charAt(0) == BOM) {
        getBuilder().deleteCharAt(0);
        setStringToBuilder();
      }
      do
        if (getString().isEmpty()) {
        } else if (getString().equalsIgnoreCase(CDLayoutObject.CATALOG))
          parseCatalog();
        else if (getString().equalsIgnoreCase(CDLayoutObject.CDTEXTFILE))
          parseCdTextFile();
        else if (getString().equalsIgnoreCase(CDLayoutObject.FILE))
          parseFile();
        else if (getString().equalsIgnoreCase(CDLayoutObject.FLAGS))
          parseFlags();
        else if (getString().equalsIgnoreCase(CDLayoutObject.INDEX))
          parseIndex();
        else if (getString().equalsIgnoreCase(CDLayoutObject.ISRC))
          parseIsrc();
        else if (getString().equalsIgnoreCase(CDLayoutObject.PERFORMER))
          parsePerformer();
        else if (getString().equalsIgnoreCase(CDLayoutObject.POSTGAP))
          parsePostgap();
        else if (getString().equalsIgnoreCase(CDLayoutObject.PREGAP))
          parsePregap();
        else if (getString().equalsIgnoreCase(CDLayoutObject.REM))
          parseRem();
        else if (getString().equalsIgnoreCase(CDLayoutObject.SONGWRITER))
          parseSongwriter();
        else if (getString().equalsIgnoreCase(CDLayoutObject.TITLE))
          parseTitle();
        else if (getString().equalsIgnoreCase(CDLayoutObject.TRACK))
          parseTrack();
        else
          parseCustom();
      while (readWord() != null);
    } catch (Throwable throwable) {
      die(throwable);
    }
    return getSheet();
  }

  /** {@inheritDoc} */
  @Override
  public void die(Throwable cause) {
    this.deathCause = cause;
  }

  /** {@inheritDoc} */
  @Override
  public Throwable getObjectDeathCause() {
    return this.deathCause;
  }

  /** {@inheritDoc} */
  @Override
  public void nullifyObject() {
    if (isObjectNullified())
      return;
    Arrays.fill(this.acc, NUL_INT);
    clearBuilder();
    this.acc = null;
    this.builder = null;
    this.eof = false;
    this.eol = false;
    this.eow = false;
    this.esc = false;
    this.filePath = null;
    this.fileType = null;
    this.index = null;
    this.lineCount = NUL_LONG;
    this.mode = null;
    this.reader = null;
    this.sheet = null;
    this.string = null;
    this.track = null;
    die(NullifiedObjectException.nul);
  }

  /** {@inheritDoc} */
  @Override
  public boolean isObjectNullified() {
    return getObjectDeathCause() == NullifiedObjectException.nul;
  }

  /** {@inheritDoc} */
  @Override
  public void close() {
    if (isObjectDead())
      return;
    try {
      getReader().close();
    } catch (IOException exception) {
      die(exception);
    }
    die(new IOException("Stream closed."));
  }

  /** {@link #parse()} branch: CATALOG. */
  protected void parseCatalog() throws IOException {
    if (getSession().hasCatalog())
      throw new CatalogAgainException(getLineCount());
    getSession().setCatalog(readWord());
  }

  /** {@link #parse()} branch: CDTEXTFILE. */
  protected void parseCdTextFile() throws IOException {
    getSession().setCdTextFile(CueSheets.ensureNoQuote(readWord()));
  }

  /** {@link #parse()} branch: custom. */
  protected void parseCustom() throws IOException {
    switch (getMode()) {
      case SESSION:
        getSession().setCustom(getString(), flushLine());
        break;
      case TRACK:
        getTrack().setCustom(getString(), flushLine());
        break;
      case INDEX:
        getIndex().setCustom(getString(), flushLine());
        break;
    }
  }

  /** {@link #parse()} branch: FILE. */
  protected void parseFile() throws IOException {
    setFile(CueSheets.ensureNoQuote(readWord()), readWord());
  }

  /** {@link #parse()} branch: FLAGS. */
  protected void parseFlags() throws IOException {
    switch (getMode()) {
      case SESSION:
      case INDEX:
        throwCommandUnexpectedException();
        break;
      case TRACK:
        getTrack().setFlags(flushLine());
        break;
    }
  }

  /** {@link #parse()} branch: INDEX. */
  protected void parseIndex() throws IOException {
    setIndex(
        new Index(Integer.parseInt(readWord()), CDDAFrame.parse(readWord())));
    if (hasFile()) {
      getIndex().setFile(getFilePath(), getFileType());
      clearFile();
    }
    getTrack().addIndex(getIndex());
    setMode(Mode.INDEX);
  }

  /** {@link #parse()} branch: ISRC. */
  protected void parseIsrc() throws IOException {
    switch (getMode()) {
      case SESSION:
      case INDEX:
        throwCommandUnexpectedException();
        break;
      case TRACK:
        getTrack().setIsrc(readWord());
        break;
    }
  }

  /** {@link #parse()} branch: line ending. */
  protected void parseLineEnding() throws IOException {
    if (getSheet().hasFile() && !getSheet().getFile().hasLineEnding())
        try (
        Reader readerr
        = Files.newBufferedReader(getSheet().getFile().getFile().toPath())) {
      getSheet().getFile().setLineEnding(LineEnding.parse(readerr));
    }
  }

  /** {@link #parse()} branch: PERFORMER. */
  protected void parsePerformer() throws IOException {
    switch (getMode()) {
      case SESSION:
        getSession().setPerformer(CueSheets.ensureNoQuote(readWord()));
        break;
      case TRACK:
      case INDEX:
        getTrack().setPerformer(CueSheets.ensureNoQuote(readWord()));
        break;
    }
  }

  /** {@link #parse()} branch: POSTGAP. */
  protected void parsePostgap() throws IOException {
    switch (getMode()) {
      case SESSION:
      case TRACK:
        throwCommandUnexpectedException();
        break;
      case INDEX:
        getTrack().setPostgap(CDDAFrame.parse(readWord()));
        break;
    }
  }

  /** {@link #parse()} branch: PREGAP. */
  protected void parsePregap() throws IOException {
    switch (getMode()) {
      case SESSION:
      case INDEX:
        throwCommandUnexpectedException();
        break;
      case TRACK:
        getTrack().setPregap(CDDAFrame.parse(readWord()));
        break;
    }
  }

  /** {@link #parse()} branch: REM. */
  protected void parseRem() throws IOException {
    switch (getMode()) {
      case SESSION:
        getSession().addRem(flushLine());
        break;
      case TRACK:
        getTrack().addRem(flushLine());
        break;
      case INDEX:
        getIndex().addRem(flushLine());
        break;
    }
  }

  /** {@link #parse()} branch: SONGWRITER. */
  protected void parseSongwriter() throws IOException {
    switch (getMode()) {
      case SESSION:
        getSession().setSongwriter(CueSheets.ensureNoQuote(readWord()));
        break;
      case TRACK:
      case INDEX:
        getTrack().setSongwriter(CueSheets.ensureNoQuote(readWord()));
        break;
    }
  }

  /** {@link #parse()} branch: TITLE. */
  protected void parseTitle() throws IOException {
    switch (getMode()) {
      case SESSION:
        getSession().setTitle(CueSheets.ensureNoQuote(readWord()));
        break;
      case TRACK:
      case INDEX:
        getTrack().setTitle(CueSheets.ensureNoQuote(readWord()));
        break;
    }
  }

  /** {@link #parse()} branch: TRACK. */
  protected void parseTrack() throws IOException {
    setTrack(new Track(Integer.parseInt(readWord()), readWord()));
    getSession().getTracks().add(getTrack());
    setMode(Mode.TRACK);
  }

  /**
   * Reads and returns the remaining characters before an end-of-line from its
   * reader.
   */
  protected String flushLine() throws IOException {
    return isEol() ? NUL_STRING : readLine();
  }

  /** {@link #readItem(boolean)} branch: end-of-line. */
  protected String processEol() {
    this.eol = true;
    this.eow = true;
    if (isEsc())
      throw new StringMisquoteException(
          BadCueSheetException.makeSubject(lineCount));
    setStringToBuilder();
    return getString();
  }

  /** {@link #readItem(boolean)} branch: quote. */
  protected void processQuote() {
    this.eow = false;
    this.esc = !isEsc();
  }

  /** Reads and returns the next line from its reader. */
  protected String readLine() throws IOException {
    return readItem(false);
  }

  /** Reads and returns the next word from its reader. */
  protected String readWord() throws IOException {
    return readItem(true);
  }

  /** Reads and returns the next item from its reader. */
  protected String readItem(boolean word) throws IOException {
    if (isEof())
      return null;
    clearBuilder();
    while (true) {
      if (isEol()) {
        incrementLineCount();
        this.eol = false;
      }
      setAcc(getReader().read());
      switch (getAcc0()) {
        case '\"':
          processQuote();
          break;
        case '\t':
        case ' ':
          if (!isEsc()) {
            if (isEow())
              continue;
            this.eow = true;
            if (word) {
              setStringToBuilder();
              return getString();
            }
          }
          break;
        case '\n':
          if (getAcc1() == '\r')
            continue;
        case '\r':
          return processEol();
        case -1:
          this.eof = true;
          return processEol();
        default:
          this.eow = false;
      }
      getBuilder().appendCodePoint(getAcc0());
    }
  }

  /** Makes an CommandUnexpectedException at the current context. */
  protected void throwCommandUnexpectedException() {
    throw new CommandUnexpectedException(getLineCount(), getString());
  }

  /** Returns its reader. */
  protected Reader getReader() {
    return this.reader;
  }

  /** Returns its resulting cuesheet. */
  protected CueSheet getSheet() {
    return this.sheet;
  }

  /** Returns its cuesheet session. */
  protected Session getSession() {
    return getSheet().getSession();
  }

  /** Returns its working track. */
  protected Track getTrack() {
    return this.track;
  }

  /** Sets its working track. */
  protected void setTrack(Track track) {
    this.track = track;
  }

  /** Returns its working index. */
  protected Index getIndex() {
    return this.index;
  }

  /** Sets its working index. */
  protected void setIndex(Index index) {
    this.index = index;
  }

  /** Returns its operating mode. */
  protected Mode getMode() {
    return this.mode;
  }

  /** Sets its operating mode. */
  protected void setMode(Mode mode) {
    this.mode = mode;
  }

  /** Returns its character buffer. */
  protected StringBuilder getBuilder() {
    return this.builder;
  }

  /** Clears its character buffer. */
  protected void clearBuilder() {
    getBuilder().delete(0, getBuilder().length());
  }

  /** Returns its file path. */
  protected String getFilePath() {
    return this.filePath;
  }

  /** Returns its file type. */
  protected String getFileType() {
    return this.fileType;
  }

  /** Sets its FILE arguments. */
  protected void setFile(String path, String type) {
    this.filePath = path;
    this.fileType = type;
  }

  /** Clears its FILE arguments. */
  protected void clearFile() {
    setFile(null, null);
  }

  /** Returns its string accumulator. */
  protected String getString() {
    return this.string;
  }

  /** Sets its string accumulator to its character buffer. */
  protected void setStringToBuilder() {
    this.string = getBuilder().toString();
  }

  /** Returns the top integer accumulator. */
  protected int getAcc0() {
    return this.acc[0];
  }

  /** Returns the bottom integer accumulator. */
  protected int getAcc1() {
    return this.acc[1];
  }

  /** Pushes the given integer into its accumulators. */
  protected void setAcc(int integer) {
    this.acc[1] = getAcc0();
    this.acc[0] = integer;
  }

  /** Returns its line count. */
  protected long getLineCount() {
    return this.lineCount;
  }

  /** Sets its line count. */
  protected void setLineCount(long lineCount) {
    this.lineCount = lineCount;
  }

  /** Increments its line count by 1. */
  protected void incrementLineCount() {
    setLineCount(Math.min(Long.MAX_VALUE, getLineCount() + 1));
  }

  /** Returns whether its FILE arguments are set. */
  protected boolean hasFile() {
    return getFilePath() != null && getFileType() != null;
  }

  /** Returns whether its reader has reached the end-of-file. */
  protected boolean isEof() {
    return this.eof;
  }

  /** Returns whether its reader is at an end-of-line. */
  protected boolean isEol() {
    return this.eol;
  }

  /** Returns whether its reader is at an end-of-word. */
  protected boolean isEow() {
    return this.eow;
  }

  /** Returns whether it is bypassing word detection. */
  protected boolean isEsc() {
    return this.esc;
  }

  /** Operating modes. */
  protected enum Mode {
    EOL, SESSION, TRACK, INDEX;
  }
}
