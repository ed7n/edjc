package eden.common.excep;

/**
 * Thrown when a number is negative.
 *
 * @author Brendon
 */
public class NumberNegativeException extends EDENRuntimeException {

  /** Problem description. */
  protected static final String PROBLEM
      = "The number is negative.";

  /** Makes an instance with the given byte. */
  public NumberNegativeException(byte number) {
    this(Byte.toString(number));
  }

  /** Makes an instance with the given short. */
  public NumberNegativeException(short number) {
    this(Short.toString(number));
  }

  /** Makes an instance with the given integer. */
  public NumberNegativeException(int number) {
    this(Integer.toString(number));
  }

  /** Makes an instance with the given long. */
  public NumberNegativeException(long number) {
    this(Long.toString(number));
  }

  /** Makes an instance with the given float. */
  public NumberNegativeException(float number) {
    this(Float.toString(number));
  }

  /** Makes an instance with the given double. */
  public NumberNegativeException(double number) {
    this(Double.toString(number));
  }

  /** Makes an instance with the given number label. */
  public NumberNegativeException(String number) {
    super(number, PROBLEM);
  }

  /** To prevent null instantiations of this class. */
  protected NumberNegativeException() {
  }
}
