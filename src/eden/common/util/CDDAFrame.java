package eden.common.util;

import eden.common.excep.string.StringMisformatException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Utility methods and constants for operating on CDDA (Compact Disc Digital
 * Audio) frames.
 *
 * @author Brendon
 */
public class CDDAFrame {

  /** Number of frames per second. */
  public static final int FPS = 75;
  /** Number of frames per minute. */
  public static final int FPM = FPS * 60;
  /** Initialization use only. */
  private static final List<Float> spf = new ArrayList<>(FPS);

  static {
    for (int i = 0; i < FPS; i++) {
      spf.add((float) i / FPS);
    }
  }

  /** Seconds per frame(s). */
  protected static final List<Float> SPF = Collections.unmodifiableList(spf);

  /** Parses the given MM:SS:FF time code to a frame number. */
  public static int parse(String timeCode) {
    Objects.requireNonNull(timeCode, "timeCode");
    if (timeCode.length() != 8) {
      throw new StringMisformatException("timeCode", "MM:SS:FF");
    }
    try {
      return (
        Integer.parseInt(timeCode.substring(6, 8)) +
        (FPS * Integer.parseInt(timeCode.substring(3, 5))) +
        (FPM * Integer.parseInt(timeCode.substring(0, 2)))
      );
    } catch (NumberFormatException exception) {
      throw new StringMisformatException("timeCode", "MM:SS:FF");
    }
  }

  /** Converts the given frame number to time in seconds. */
  public static float toSecond(int frame) {
    Numbers.requireNonNegative(frame);
    return Math.floorDiv(frame, FPS) + SPF.get(frame % FPS);
  }

  /** Formats the given frame number to an MM:SS:FF time code. */
  public static String toTimeCode(int frame) {
    Numbers.requireNonNegative(frame);
    int[] fields = new int[3];
    fields[0] = Math.floorDiv(frame, FPM);
    frame -= fields[0] * FPM;
    fields[1] = Math.floorDiv(frame, FPS);
    frame -= fields[1] * FPS;
    fields[2] = frame;
    StringBuilder out = new StringBuilder(8);
    for (int i = 0;;) {
      out.append(Numbers.toString2Digits(fields[i]));
      if (++i == fields.length) {
        break;
      }
      out.append(":");
    }
    return out.toString();
  }

  /** To prevent instantiations of this class. */
  protected CDDAFrame() {}
}
