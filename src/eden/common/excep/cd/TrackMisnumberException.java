package eden.common.excep.cd;

import eden.common.excep.EDENExceptions;
import eden.common.model.cd.Track;

/**
 * Thrown when a track is misnumbered.
 *
 * @author Brendon
 */
public class TrackMisnumberException extends BadCueSheetException {

  /** Problem description. */
  protected static final String PROBLEM
      = EDENExceptions.makeMisnumberProblem("track");

  /** Suggested remedy. */
  protected static final String REMEDY
      = makeMisnumberRemedy(
          "the track number", Track.MIN_NUMBER, Track.MAX_NUMBER);

  /** Makes an instance with the given track number. */
  public TrackMisnumberException(int track) {
    super(track, PROBLEM, REMEDY);
  }

  /** Makes an instance with the given track and line numbers. */
  public TrackMisnumberException(int track, long line) {
    super(track, line, PROBLEM, REMEDY);
  }

  /** Makes an instance with the given track label. */
  public TrackMisnumberException(String track) {
    super(track, PROBLEM, REMEDY);
  }

  /** To prevent null instantiations of this class. */
  protected TrackMisnumberException() {
  }
}
