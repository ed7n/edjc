package eden.common.util;

import static eden.common.shared.Constants.NUL_INT;

import eden.common.excep.EDENRuntimeException;
import eden.common.excep.cd.FrameUnexpectedException;
import eden.common.excep.cd.ISRCMisformatException;
import eden.common.excep.cd.IndexMisnumberException;
import eden.common.excep.cd.IndexUnexpectedException;
import eden.common.excep.cd.MCNMisformatException;
import eden.common.excep.cd.SessionEmptyException;
import eden.common.excep.cd.SessionOverflowException;
import eden.common.excep.cd.TrackEmptyException;
import eden.common.excep.cd.TrackMisnumberException;
import eden.common.excep.cd.TrackOverflowException;
import eden.common.excep.cd.TrackUnexpectedException;
import eden.common.model.cd.CueSheet;
import eden.common.model.cd.Index;
import eden.common.model.cd.Session;
import eden.common.model.cd.Track;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Consists of utility methods for operating on cuesheets.
 *
 * @author Brendon
 * @see eden.common.model.cd.CueSheet
 */
public class CueSheets {

  /** Pattern: International Standard Recording Code (ISRC). */
  protected static final Pattern REGEX_ISRC
      = Pattern.compile("^\\p{Alnum}{5}\\p{Digit}{7}$");

  /** Pattern: Media Catalog number (MCN). */
  protected static final Pattern REGEX_MCN
      = Pattern.compile("^\\p{Alnum}{13}$");

  /** Pattern: Enclosed in double quotes. */
  protected static final Pattern REGEX_ENCLOSE
      = Pattern.compile("^\".*\"$");

  /** Pattern: No spaces nor double quotes. */
  protected static final Pattern REGEX_NOQUOTE
      = Pattern.compile("^[^\"\\p{Space}]*$");

  /**
   * Returns whether the given string has spaces and is enclosed in double
   * quotes, or has no space and starts or ends with a double quote.
   */
  public static boolean checkQuote(String string) {
    return REGEX_ENCLOSE.matcher(string).matches()
        || REGEX_NOQUOTE.matcher(string).matches();
  }

  /** Checks the given session for syntax errors then returns them. */
  public static List<EDENRuntimeException> checkSyntax(Session session) {
    List<EDENRuntimeException> out = new LinkedList<>();
    if (session.hasCatalog()
        && !REGEX_MCN.matcher(session.getCatalog()).matches())
      out.add(new MCNMisformatException("Session"));
    if (!session.hasTracks())
      out.add(new SessionEmptyException("Session"));
    else if (session.getTracks().size() > Track.MAX_COUNT)
      out.add(new SessionOverflowException("Session"));
    return out;
  }

  /** Checks the given track for syntax errors then returns them. */
  public static List<EDENRuntimeException> checkSyntax(Track track) {
    return checkSyntax(track, NUL_INT);
  }

  /**
   * Checks the given track for syntax errors then returns them.
   *
   * @param expected Expected track number.
   */
  public static List<EDENRuntimeException> checkSyntax(
      Track track, int expected) {
    List<EDENRuntimeException> out = new LinkedList<>();
    if (!Track.isNumberValid(track.getNumber()))
      out.add(new TrackMisnumberException(track.getNumber()));
    else if (expected > 0 && track.getNumber() != expected)
      out.add(new TrackUnexpectedException(track.getNumber()));
    if (track.hasIsrc() && !REGEX_ISRC.matcher(track.getIsrc()).matches())
      out.add(new ISRCMisformatException(track.getNumber()));
    if (!track.hasIndexes())
      out.add(new TrackEmptyException(track.getNumber()));
    else if (track.getIndexes().size() > Index.MAX_COUNT)
      out.add(new TrackOverflowException(track.getNumber()));
    return out;
  }

  /**
   * Checks the given track indexes for syntax errors then returns them.
   *
   * @param lastIndex The last index to check time code against.
   */
  public static List<EDENRuntimeException> checkSyntax(
      Track track, Index lastIndex) {
    List<EDENRuntimeException> out = new LinkedList<>();
    int expected = 0;
    if (track.hasIndexes()) {
      if (track.getIndex(0).getNumber() == 0) {
        if (track.getIndexes().size() == 1)
          out.add(new IndexUnexpectedException(track.getNumber(), 0));
      } else
        expected = 1;
      for (Index index : track.getIndexes()) {
        out.addAll(checkSyntax(track, index, lastIndex, expected++));
        lastIndex = index;
      }
    }
    return out;
  }

  /**
   * Checks the given index for syntax errors then returns them.
   *
   * @param expected Expected index number.
   */
  protected static List<EDENRuntimeException> checkSyntax(
      Track track, Index index, Index last, int expected) {
    List<EDENRuntimeException> out = new LinkedList<>();
    if (!Index.isNumberValid(index.getNumber()))
      out.add(new IndexMisnumberException(
          track.getNumber(), index.getNumber()));
    else if (index.getNumber() != expected)
      out.add(new IndexUnexpectedException(
          track.getNumber(), index.getNumber()));
    if (last != null && index.getFrame() < last.getFrame())
      out.add(new FrameUnexpectedException(
          track.getNumber(), index.getNumber()));
    return out;
  }

  /** Checks the given session tracks for errors then returns them. */
  public static List<EDENRuntimeException> checkSyntaxTrack(Session session) {
    List<EDENRuntimeException> out = new LinkedList<>();
    int expected = 1;
    for (Track track : session.getTracks())
      out.addAll(checkSyntax(track, expected++));
    return out;
  }

  /** Checks the given track indexes for errors then returns them. */
  public static List<EDENRuntimeException> checkSyntaxIndex(Track track) {
    return checkSyntax(track, null);
  }

  /** Removes a quotation mark from each end of the given string. */
  public static String ensureNoQuote(String string) {
    return REGEX_ENCLOSE.matcher(string).matches()
        ? string.substring(1, string.length() - 1) : string;
  }

  /** Encloses the given string in quotation marks if absent. */
  public static String ensureQuote(String string) {
    return REGEX_ENCLOSE.matcher(string).matches()
        ? string : "\"" + string + "\"";
  }

  /** Parses a cuesheet from the given file. */
  public static CueSheet parse(File file) throws Throwable {
    CueSheet out;
    try (CueSheetParser parser = new CueSheetParser(file)) {
      out = parser.parse();
      if (parser.isObjectDead())
        throw parser.getObjectDeathCause();
    }
    return out;
  }

  /** Parses a cuesheet from the given reader. */
  public static CueSheet parse(Reader reader) throws Throwable {
    CueSheet out;
    try (CueSheetParser parser = new CueSheetParser(reader)) {
      out = parser.parse();
      if (parser.isObjectDead())
        throw parser.getObjectDeathCause();
    }
    return out;
  }

  /** Parses a cuesheet from the given string. */
  public static CueSheet parse(String string) throws Throwable {
    try (Reader reader = new StringReader(string)) {
      return parse(reader);
    }
  }

  /** Writes the given cuesheet to its file. */
  public static void write(CueSheet sheet) throws IOException {
    if (!sheet.hasFile() || !sheet.getFile().hasFile())
      return;
    try (Writer writer
        = Files.newBufferedWriter(sheet.getFile().getFile().toPath())) {
      write(sheet, writer);
    }
  }

  /** Writes the given cuesheet with the given writer. */
  public static void write(CueSheet sheet, Writer writer) throws IOException {
    if (sheet.hasFile() && sheet.getFile().hasLineEnding())
      writer.write(
          sheet.getSession().toString(sheet.getFile().getLineEnding()));
    else
      writer.write(sheet.getSession().toString());
  }

  /** To prevent instantiations of this class. */
  protected CueSheets() {
  }
}
