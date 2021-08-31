package eden.common.io;

import static eden.common.shared.Constants.NUL_BYTE;
import static eden.common.shared.Constants.NUL_CHAR;
import static eden.common.shared.Constants.STDERR;
import static eden.common.shared.Constants.STDOUT;

import eden.common.excep.FatalException;
import eden.common.excep.NullifiedObjectException;
import eden.common.object.Dieable;
import eden.common.object.Nullifiable;
import java.io.Closeable;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Locale;
import java.util.Objects;

/**
 * Encapsulates a PrintStream for the printing of tagged messages. An example of
 * so follows:
 *
 * <code>[EDEN/i] This is an information message.</code>
 *
 * The text enclosed in brackets ([]) is the tag, containing the identifier:
 * EDEN, followed by the message mode symbol (i). The message follows after the
 * tag.
 *
 * This version implements six message modes: {@link #PROMPT}, {@link #INFO},
 * {@link #ALERT}, {@link #ERROR}, {@link #INPUT}, and {@link #DEBUG}, each of
 * which is assigned a filter bit pattern spanning a byte.
 *
 * It is recommended to use the constants provided to create mode filters--which
 * can be done by chaining the desired modes with the bitwise OR operator
 * (|)--and to specify message modes.
 *
 * Extra caution must be exercised when using a PrintStream that wraps either
 * {@code System.out} or {@code System.err}. See {@link #close()} for details.
 *
 * For transitional convenience, all but the {@code write} methods of this class
 * are symbolically compatible to those in Java's PrintStream, and that their
 * behaviors are similar.
 *
 * @author Brendon
 * @version u0r7, 08/27/2021.
 */
public class Modal implements Appendable, Closeable, Dieable, Nullifiable {

  /** Message mode: prompt. */
  public static final byte PROMPT = (byte) 0b00000001;

  /** Message mode: information. */
  public static final byte INFO = (byte) 0b00000010;

  /** Message mode: alert. */
  public static final byte ALERT = (byte) 0b00000100;

  /** Message mode: error. */
  public static final byte ERROR = (byte) 0b00001000;

  /** Message mode: input. This mode is a subset of {@link #PROMPT}. */
  public static final byte INPUT = (byte) 0b00010001;

  /** Message mode: debug. */
  public static final byte DEBUG = (byte) 0b10000000;

  /** Mode symbol: prompt. */
  public static final char SYMBOL_PROMPT = '?';

  /** Mode symbol: information. */
  public static final char SYMBOL_INFO = 'i';

  /** Mode symbol: alert. */
  public static final char SYMBOL_ALERT = '!';

  /** Mode symbol: error. */
  public static final char SYMBOL_ERROR = 'X';

  /** Mode symbol: input. */
  public static final char SYMBOL_INPUT = '_';

  /** Mode symbol: debug. */
  public static final char SYMBOL_DEBUG = '$';

  /** Unfiltered filter bit pattern. */
  public static final int UNFILTERED = 255;

  /** Message mode: unused bits. */
  protected static final byte UNUSED = (byte) 0b01100000;

  /** Returns the first symbol of the given message mode. */
  public static char getSymbol(byte mode) {
    char out = NUL_CHAR;
    if (patternIntersectsMode(mode, PROMPT))
      out = ((mode & INPUT) != PROMPT) ? SYMBOL_INPUT : SYMBOL_PROMPT;
    else if (patternIntersectsMode(mode, INFO))
      out = SYMBOL_INFO;
    else if (patternIntersectsMode(mode, ALERT))
      out = SYMBOL_ALERT;
    else if (patternIntersectsMode(mode, ERROR))
      out = SYMBOL_ERROR;
    else if (patternIntersectsMode(mode, DEBUG))
      out = SYMBOL_DEBUG;
    return out;
  }

  /**
   * Returns whether the given filter bit pattern intersects the given message
   * mode.
   */
  protected static boolean patternIntersectsMode(byte pattern, byte mode) {
    return (pattern & mode) != 0;
  }

  /** Returns whether the given filter bit pattern has unused bits. */
  protected static boolean patternHasUnusedBits(byte pattern) {
    return patternIntersectsMode(pattern, UNUSED);
  }

  /** Returns the byte cut of the given integer. */
  protected static byte toByte(int integer) {
    return (byte) (integer & 255);
  }

  /** PrintStream to which messages are to be printed. */
  protected final PrintStream stream;

  /** Identifier. */
  protected final String name;

  /** Filter bit pattern. */
  protected final byte filter;

  /** Throwable defining its death. */
  protected Throwable deathCause;

  /** Current message mode. To be used in conjunction with `inline`. */
  protected byte mode;

  /**
   * Indicates whether the next print will be inline. This is set to true after
   * a `print`, and held until the next print is either of a different mode, or
   * a `println`.
   */
  protected boolean inline = false;

  /**
   * Makes an unfiltered instance with the given name and {@code System.out}.
   */
  public Modal(String name) throws IllegalArgumentException {
    this(name, STDOUT, UNFILTERED);
  }

  /** Makes an unfiltered instance with the given name and PrintStream. */
  public Modal(String name, PrintStream stream)
      throws IllegalArgumentException {
    this(name, stream, UNFILTERED);
  }

  /**
   * Makes an instance with the given name, PrintStream, and filter bit pattern.
   */
  public Modal(String name, PrintStream stream, int filter) {
    Objects.requireNonNull(name, "name");
    Objects.requireNonNull(stream, "stream");
    this.name = name;
    this.stream = stream;
    this.filter = toByte(filter);
  }

  /** Flushes its PrintStream, then checks and returns its error state. */
  public boolean checkError() {
    return getPrintStream().checkError();
  }

  /** Flushes its PrintStream. */
  public void flush() {
    getPrintStream().flush();
  }

  /**
   * Prints the given formatted string using the default locale with the given
   * arguments. The default locale is obtained with {@code Locale.getDefault()}.
   */
  public Modal format(String format, Object... arguments) {
    return format(Locale.getDefault(), format, arguments);
  }

  /**
   * Prints the given formatted string using the given locale with the given
   * arguments.
   *
   * @param locale Localization to apply during formatting. null skips this.
   */
  public Modal format(Locale locale, String format, Object... arguments) {
    print(String.format(locale, format, arguments));
    return this;
  }

  /** Prints itself. */
  public void print() {
    print(toString());
  }

  /** Prints the given boolean. */
  public void print(boolean b) {
    print(Boolean.toString(b));
  }

  /** Prints the given byte. */
  public void print(byte b) {
    print(Byte.toString(b));
  }

  /** Prints the given character. */
  public void print(char c) {
    print(Character.toString(c));
  }

  /** Prints the given short. */
  public void print(short s) {
    print(Short.toString(s));
  }

  /** Prints the given integer. */
  public void print(int i) {
    print(Integer.toString(i));
  }

  /** Prints the given long. */
  public void print(long l) {
    print(Long.toString(l));
  }

  /** Prints the given float. */
  public void print(float f) {
    print(Float.toString(f));
  }

  /** Prints the given double. */
  public void print(double d) {
    print(Double.toString(d));
  }

  /** Prints the given character sequence. */
  public void print(CharSequence charSequence) {
    print(charSequence.toString());
  }

  /** Prints the given object. */
  public void print(Object object) {
    print(String.valueOf(object));
  }

  /** Prints the given string. */
  public void print(String string) {
    print(string, Modal.INFO);
  }

  /** Prints the given message as the given message mode. */
  public void print(String message, int mode) {
    if (isObjectDead())
      return;
    byte modeByte = toByte(mode);
    if (isModeFiltered(modeByte))
      return;
    char symbol = validateAndGetSymbol(modeByte);
    this.inline = modeByte == this.mode;
    if (this.inline)
      getPrintStream().print(message);
    else {
      getPrintStream().print("[" + getName() + "/" + symbol + "] " + message);
      this.mode = modeByte;
    }
  }

  /** Alias to {@link #format(String, Object...)}. */
  public Modal printf(String format, Object... arguments) {
    return format(format, arguments);
  }

  /** Alias to {@link #format(Locale, String, Object...)}. */
  public Modal printf(Locale locale, String format, Object... arguments) {
    return format(locale, format, arguments);
  }

  /** Prints itself followed by the line separator. */
  public void println() {
    println(toString());
  }

  /** Prints the given boolean followed by the line separator. */
  public void println(boolean b) {
    println(Boolean.toString(b));
  }

  /** Prints the given byte followed by the line separator. */
  public void println(byte b) {
    println(Byte.toString(b));
  }

  /** Prints the given character followed by the line separator. */
  public void println(char c) {
    println(Character.toString(c));
  }

  /** Prints the given short followed by the line separator. */
  public void println(short s) {
    println(Short.toString(s));
  }

  /** Prints the given integer followed by the line separator. */
  public void println(int i) {
    println(Integer.toString(i));
  }

  /** Prints the given long followed by the line separator. */
  public void println(long l) {
    println(Long.toString(l));
  }

  /** Prints the given float followed by the line separator. */
  public void println(float f) {
    println(Float.toString(f));
  }

  /** Prints the given double followed by the line separator. */
  public void println(double d) {
    println(Double.toString(d));
  }

  /** Prints the given character sequence followed by the line separator. */
  public void println(CharSequence charSequence) {
    println(charSequence.toString());
  }

  /** Prints the given object followed by the line separator. */
  public void println(Object object) {
    println(String.valueOf(object));
  }

  /** Prints the given string followed by the line separator. */
  public void println(String string) {
    println(string, Modal.INFO);
  }

  /**
   * Prints the given message as the given message mode followed by the line
   * separator.
   */
  public void println(String message, int mode) {
    if (isObjectDead())
      return;
    byte pattern = toByte(mode);
    if (isModeFiltered(pattern))
      return;
    char symbol = validateAndGetSymbol(pattern);
    this.inline = false;
    if (pattern == this.mode)
      getPrintStream().println(message);
    else
      getPrintStream().println("[" + getName() + "/" + symbol + "] " + message);
    this.mode = NUL_BYTE;
  }

  /** Returns its filter bit pattern. */
  public byte getFilter() {
    return this.filter;
  }

  /** Returns its identifier. */
  public String getName() {
    return this.name;
  }

  /** Returns its PrintStream. */
  public PrintStream getPrintStream() {
    return this.stream;
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
    this.mode = NUL_BYTE;
    this.inline = false;
    die(NullifiedObjectException.nul);
  }

  /** {@inheritDoc} */
  @Override
  public boolean isObjectNullified() {
    return getObjectDeathCause() == NullifiedObjectException.nul;
  }

  /**
   * Shorthand to {@link #print(char)}, with the addition of returning itself.
   */
  @Override
  public Modal append(char character) {
    print(character);
    return this;
  }

  /**
   * Shorthand to {@link #print(CharSequence)}, with the addition of returning
   * itself.
   */
  @Override
  public Modal append(CharSequence charSequence) {
    print(charSequence);
    return this;
  }

  /**
   * Prints the given character sequence at the given index range, then returns
   * itself.
   */
  @Override
  public Modal append(CharSequence charSequence, int start, int end) {
    if (charSequence == null)
      print("null");
    else {
      if (start < 0 || end < 0 || start > end || end > charSequence.length())
        throw new IndexOutOfBoundsException(
            "[start, end]: [" + start + ", " + end
            + "] > [0, " + charSequence.length() + "]");
      print(charSequence.subSequence(start, end).toString());
    }
    return this;
  }

  /**
   * Closes its PrintStream, enabling the dead flag.
   *
   * If the PrintStream is either {@code System.out} or {@code System.err}, then
   * then this method does nothing.
   */
  @Override
  public void close() {
    if (isObjectDead())
      return;
    if (getPrintStream() != STDOUT && getPrintStream() != STDERR) {
      getPrintStream().close();
      die(new IOException("Stream closed."));
    }
  }

  /** Validates the given message mode and returns its associated symbol. */
  protected char validateAndGetSymbol(byte mode) {
    if (patternHasUnusedBits(mode))
      throw new IllegalArgumentException(
          "isModeUnused: " + Integer.toString(filter & UNUSED, 2));
    char out = getSymbol(mode);
    if (out == NUL_CHAR)
      throw new FatalException(getClass().getName() + "#" + getName());
    return out;
  }

  /** Returns whether the given message mode is filtered. */
  protected boolean isModeFiltered(byte mode) {
    return (getFilter() & mode) == 0;
  }
}
