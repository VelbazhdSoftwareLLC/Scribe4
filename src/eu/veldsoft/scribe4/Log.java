package eu.veldsoft.scribe4;

/**
 * Wrapper methods for android.util.Log calls, so we don't have to type
 * Constants.LOG_TAG over and over.
 */
public class Log {
  private Log() {
  };

  public static void v(String logMessage) {
    android.util.Log.v(Constants.LOG_TAG, logMessage);
  }

  public static void d(String logMessage) {
    android.util.Log.d(Constants.LOG_TAG, logMessage);
  }

  public static void i(String logMessage) {
    android.util.Log.i(Constants.LOG_TAG, logMessage);
  }

  public static void w(String logMessage) {
    android.util.Log.w(Constants.LOG_TAG, logMessage);
  }

  public static void e(String logMessage) {
    android.util.Log.e(Constants.LOG_TAG, logMessage);
  }
}
