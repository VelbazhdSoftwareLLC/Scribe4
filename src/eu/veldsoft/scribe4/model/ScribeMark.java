package eu.veldsoft.scribe4.model;

/**
 * 
 * @author
 */
public enum ScribeMark {
  PURPLE('P'), GREEN('G'), BLUE('B'), RED('R'), EMPTY('-');

  private final char ch;

  ScribeMark(char ch) {
    this.ch = ch;
  }

  public char toChar() {
    return ch;
  }

  public static ScribeMark fromChar(char ch) {
    if (ch == PURPLE.ch)
      return PURPLE;

    if (ch == GREEN.ch)
      return GREEN;

    if (ch == BLUE.ch)
      return BLUE;

    if (ch == RED.ch)
      return RED;

    return EMPTY;
  }

  /**
   * The "other" player
   */
  public ScribeMark other() {
    switch (this) {
    case RED:
      return BLUE;
    case BLUE:
      return GREEN;
    case GREEN:
      return PURPLE;
    case PURPLE:
      return RED;
    case EMPTY:
      return EMPTY;
    default:
      return EMPTY;
    }
  }
}
