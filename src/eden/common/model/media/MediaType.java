package eden.common.model.media;

import eden.common.excep.EDENRuntimeException;
import eden.common.excep.media.BadMediaTypeFacetException;
import eden.common.excep.media.BadMediaTypeSubtypeException;
import eden.common.excep.media.MediaTypeMisformatException;
import eden.common.excep.string.StringOverflowException;
import eden.common.util.Strings;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Defines a media type in attempted-conformity to RFCs 6838 and 2045.
 *
 * @author Brendon
 * @see <a href="https://datatracker.ietf.org/doc/rfc6838">RFC 6838</a>
 * @see <a href="https://datatracker.ietf.org/doc/rfc2045">RFC 2045</a>
 */
public class MediaType {

  /** restricted-name-first. */
  protected static final String REGEX_NAME_FIRST = "[\\p{Alnum}]";
  /** restricted-name-chars. */
  protected static final String REGEX_NAME_CHARS = "[\\p{Alnum}!#$&-^_]";
  /** Facet name. */
  protected static final String REGEX_FACET = "(" + REGEX_NAME_CHARS + "\\.)";
  /** Structured syntax suffix. */
  protected static final String REGEX_SYNTAX = "(\\+" + REGEX_NAME_CHARS + ")";
  /** Validity regular expression, except for string length. */
  protected static final Pattern REGEX_NAME = Pattern.compile(
    "^" +
    REGEX_FACET +
    "{0,}" +
    REGEX_NAME_FIRST +
    REGEX_NAME_CHARS +
    REGEX_SYNTAX +
    "?$"
  );
  /** Maximum string length. */
  protected static final int MAX_NAME_LENGTH = 127;
  /** type-name. */
  protected final MediaTypeType type;
  /** String parameters and its string form. */
  protected final String facet, subtype, syntax, parameter, string;

  /** Makes an instance with the given type and subtype. */
  public MediaType(MediaTypeType type, String subtype) {
    this(type, null, subtype, null, null);
  }

  /** Makes an instance with the given type, facet name, and subtype. */
  public MediaType(MediaTypeType type, String facet, String subtype) {
    this(type, facet, subtype, null, null);
  }

  /**
   * Makes an instance with the given type, facet name, subtype, and structured
   * syntax suffix.
   */
  public MediaType(
    MediaTypeType type,
    String facet,
    String subtype,
    String syntax
  ) {
    this(type, facet, subtype, syntax, null);
  }

  /** Makes an instance with the given parameters. */
  public MediaType(
    MediaTypeType type,
    String facet,
    String subtype,
    String syntax,
    String parameter
  ) {
    this.type = Objects.requireNonNull(type, "type");
    this.subtype = Strings.requireNonEmpty(subtype, "subtype").trim();
    this.facet = Strings.nullOrTrim(facet);
    this.syntax = Strings.nullOrTrim(syntax);
    this.parameter = Strings.nullOrTrim(parameter);
    this.string = makeString();
  }

  /** Throws the relevant exception for a invalidity within itself. */
  public void checkValidity() {
    if (toString().length() > MAX_NAME_LENGTH) {
      throw new StringOverflowException(
        Integer.toString(hashCode()),
        MAX_NAME_LENGTH
      );
    }
    if (!REGEX_NAME.matcher(toString()).matches()) {
      throw new MediaTypeMisformatException(Integer.toString(hashCode()));
    }
    if (hasFacet()) {
      if (getFacet().contains(".")) {
        throw new BadMediaTypeFacetException(Integer.toString(hashCode()));
      }
    } else if (getSubtype().contains(".")) {
      throw new BadMediaTypeSubtypeException(Integer.toString(hashCode()));
    }
  }

  /** Return its type name. */
  public MediaTypeType getType() {
    return this.type;
  }

  /** Return its facet name. */
  public String getFacet() {
    return this.facet;
  }

  /** Return its subtype name. */
  public String getSubtype() {
    return this.subtype;
  }

  /** Return its structured syntax suffix. */
  public String getSyntax() {
    return this.syntax;
  }

  /** Return its parameter. */
  public String getParameter() {
    return this.parameter;
  }

  /** Returns whether it has a facet name. */
  public boolean hasFacet() {
    return !Strings.isNullOrEmpty(getFacet());
  }

  /** Returns whether it has a parameter. */
  public boolean hasParameter() {
    return !Strings.isNullOrEmpty(getParameter());
  }

  /** Returns whether it has a structured syntax suffix. */
  public boolean hasSyntax() {
    return !Strings.isNullOrEmpty(getSyntax());
  }

  /** Returns whether it is valid instance. */
  public boolean isValid() {
    try {
      checkValidity();
    } catch (EDENRuntimeException exception) {
      return false;
    }
    return true;
  }

  /** {@inheritDoc} */
  @Override
  public boolean equals(Object object) {
    return (
      object == this ||
      (
        object != null &&
        object.getClass() == getClass() &&
        equals((MediaType) object)
      )
    );
  }

  /** Returns whether the given instance is equal to it. */
  protected boolean equals(MediaType instance) {
    return instance.hashCode() == hashCode();
  }

  /** {@inheritDoc} */
  @Override
  public int hashCode() {
    return Objects.hash(
      this.facet,
      this.parameter,
      this.string,
      this.subtype,
      this.syntax,
      this.type
    );
  }

  /** {@inheritDoc} */
  @Override
  public String toString() {
    return this.string;
  }

  /** Makes its string form. */
  protected String makeString() {
    StringBuilder out = new StringBuilder(MAX_NAME_LENGTH);
    out.append(getType().toString()).append("/");
    if (hasFacet()) {
      out.append(getFacet()).append(".");
    }
    out.append(getSubtype());
    if (hasSyntax()) {
      out.append("+").append(getSyntax());
    }
    if (hasParameter()) {
      out.append(";").append(getParameter());
    }
    return out.toString();
  }
}
