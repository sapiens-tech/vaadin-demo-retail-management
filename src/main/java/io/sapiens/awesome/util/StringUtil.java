package io.sapiens.awesome.util;

import io.sapiens.awesome.exception.SystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.*;

public class StringUtil {

  private static final String TOKEN_BOUND =
      "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
  private static final String DIGIT_BOUND = "0123456789";
  /** A String for a space character. */
  private static final String EMPTY = "";

  private static StringUtil me;
  private Logger logger = LoggerFactory.getLogger(StringUtil.class);

  public static StringUtil getInstance() {
    if (me == null) me = new StringUtil();
    return me;
  }

  public static String defaultString(final String str) {
    return str == null ? EMPTY : str;
  }

  public String extractOnlyAlphanumeric(String string) {
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < string.length(); i++) {
      String c = Character.toString(string.charAt(i));
      if (TOKEN_BOUND.contains(c)) {
        sb.append(c);
      }
    }
    return sb.toString();
  }

  public String removeString(String original, String toRemove) {
    if (original.contains(toRemove))
      return original.substring(0, original.indexOf(toRemove))
          + original.substring(original.indexOf(toRemove) + toRemove.length(), original.length());
    else return original;
  }

  public String replace(String originalContent, String toReplace, String replaceWith) {
    StringBuffer ret = new StringBuffer(originalContent);

    int indexes[] = getIndexes(originalContent, toReplace);
    if (indexes.length > 0) {
      for (int i = indexes.length - 1; i >= 0; i--) {
        ret.replace(
            0,
            ret.length(),
            ret.substring(0, indexes[i])
                + replaceWith
                + ret.substring(indexes[i] + toReplace.length(), ret.length()));
      }
    }

    return ret.toString();
  }

  public StringBuffer replace(StringBuffer ret, String toReplace, String replaceWith) {
    int indexes[] = getIndexes(ret.toString(), toReplace);
    if (indexes.length > 0) {
      for (int i = indexes.length - 1; i >= 0; i--) {
        ret.replace(
            0,
            ret.length(),
            ret.substring(0, indexes[i])
                + replaceWith
                + ret.substring(indexes[i] + toReplace.length(), ret.length()));
      }
    }

    return ret;
  }

  public int[] getIndexes(String content, String token) {
    ArrayList<Integer> indexes = new ArrayList<Integer>();

    // go thru content
    for (int i = 0; i < content.length(); i++) {
      // look for token in content, with starting position i
      int index = content.indexOf(token, i);
      // if found,
      if (index != -1) {
        // add the index to arraylist
        indexes.add(index);
        // shift search position forward
        i = index;
      }
    }

    // convert to int
    int[] ret = new int[indexes.size()];
    for (int i = 0; i < indexes.size(); i++) ret[i] = ((Integer) indexes.get(i)).intValue();

    indexes = null;

    return ret;
  }

  public String getCappedString(String str, Integer len) {
    if (str != null && str.length() > len) {
      return str.substring(0, len) + "...";
    }

    return str;
  }

  public String removeRestrictedCharactersForURLs(String str) {
    char replaceWith = '-';
    if (str.contains("\"")) str = str.replace('\"', replaceWith);
    if (str.contains("\'")) str = str.replace('\'', replaceWith);
    if (str.contains(",")) str = str.replace(',', replaceWith);
    if (str.contains("?")) str = str.replace('?', replaceWith);
    if (str.contains("/")) str = str.replace('/', replaceWith);
    if (str.contains("!")) str = str.replace('!', replaceWith);
    if (str.contains("@")) str = str.replace('@', replaceWith);
    if (str.contains("#")) str = str.replace('#', replaceWith);
    if (str.contains("$")) str = str.replace('$', replaceWith);
    if (str.contains("%")) str = str.replace('%', replaceWith);
    if (str.contains("^")) str = str.replace('^', replaceWith);
    if (str.contains("&")) str = str.replace('&', replaceWith);
    if (str.contains("*")) str = str.replace('*', replaceWith);
    if (str.contains(" ")) str = str.replace(' ', replaceWith);

    return str;
  }

  public String removeRestrictedCharactersForFilenames(String str) {
    if (str != null) {
      if (str.contains(" ")) str = str.replace(' ', '_');
      if (str.contains("\'")) str = str.replace('\'', '_');
      if (str.contains(",")) str = str.replace(',', '_');
      if (str.contains("?")) str = str.replace('?', '_');
      if (str.contains("/")) str = str.replace('/', '_');
      if (str.contains("!")) str = str.replace('!', '_');
      if (str.contains("@")) str = str.replace('@', '_');
      if (str.contains("#")) str = str.replace('#', '_');
      if (str.contains("$")) str = str.replace('$', '_');
      if (str.contains("%")) str = str.replace('%', '_');
      if (str.contains("^")) str = str.replace('^', '_');
      if (str.contains("&")) str = str.replace('&', '_');
      if (str.contains("*")) str = str.replace('*', '_');
    }
    return str;
  }

  public String removeSquareBrackets(String string) {
    // remove []
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < string.length(); i++) {
      char currentChar = string.charAt(i);
      if (currentChar != '[' || currentChar != ']') {
        sb.append(currentChar);
      }
    }

    return sb.toString();
  }

  public String removeSingleQuote(String string) {
    return removeCharacter(string, '\'');
  }

  public String removeCharacter(String string, char character) {
    if (string != null) {
      if (string.indexOf(character) == -1) {
        return string;
      } else {
        StringBuffer ret = new StringBuffer();
        for (int i = 0; i < string.length(); i++) {
          if (string.charAt(i) != character) ret.append(string.charAt(i));
        }

        return ret.toString();
      }
    } else return string;
  }

  public String getLastToken(String string, String delim) {
    int[] indexes = getIndexes(string, delim);
    return string.substring(indexes[indexes.length - 1] + 1, string.length());
  }

  public String getLastTokenExcluded(String string, String delim) {
    int[] indexes = getIndexes(string, delim);
    return string.substring(0, indexes[indexes.length - 1]);
  }

  public String generateRandomToken(int gen_length) {
    return generateRandomToken(gen_length, false);
  }

  public String generateRandomTokenInDigits(int gen_length) {
    StringBuffer sb = new StringBuffer();
    Random r = new Random();
    int len = DIGIT_BOUND.length();
    for (int i = 0; i < gen_length; i++) {
      sb.append(DIGIT_BOUND.charAt(r.nextInt(len)));
    }
    return sb.toString();
  }

  public String generateRandomToken(int gen_length, boolean encodeBase64) {
    StringBuffer sb = new StringBuffer();
    Random r = new Random();
    int len = TOKEN_BOUND.length();
    for (int i = 0; i < gen_length; i++) {
      sb.append(TOKEN_BOUND.charAt(r.nextInt(len)));
    }
    String ret = sb.toString();
    if (encodeBase64) {
      try {
        ret = Base64Util.getInstance().encode(sb.toString().getBytes("UTF-8"));
      } catch (UnsupportedEncodingException e) {
        throw new SystemException(e);
      }
    }
    return ret;
  }

  // ************************************** ARRAY METHODS ****************************************
  public boolean findStringInStringArray(String string, String[] array) {
    boolean ret = false;

    for (int i = 0; i < array.length; i++) {
      if (array[i].equals(string)) {
        ret = true;
        break;
      }
    }

    return ret;
  }

  public String[] buildStringArrayFromString(String string, String delim) {
    if (string != null) {
      StringTokenizer st = new StringTokenizer(string, delim);
      String[] ret = new String[st.countTokens()];

      int count = 0;
      while (st.hasMoreTokens()) {
        ret[count] = st.nextToken();
        count++;
      }

      return ret;
    } else return null;
  }

  public String buildStringFromStringArray(String[] array, String delim) {
    StringBuffer ret = new StringBuffer();

    if (array != null) {
      for (int i = 0; i < array.length; i++) {
        ret.append(array[i]);

        if (i + 1 < array.length) ret.append(delim);
      }
    }

    return ret.toString();
  }

  public List<String> buildListFromString(String string, String delim) {
    return buildListFromString(string, delim, false);
  }

  public List<String> buildListFromString(String string, String delim, boolean includeEmpty) {
    if (string == null) return null;
    List<String> ret = new ArrayList<String>();
    StringTokenizer st = new StringTokenizer(string, delim, includeEmpty);
    while (st.hasMoreTokens()) {
      String token = st.nextToken().trim();
      if (includeEmpty && token.equals(delim)) { // empty string
        ret.add(null);
        if (!st.hasMoreTokens()) ret.add(null); // if is last token, add one more
      } else {
        ret.add(token);
        if (includeEmpty && st.hasMoreTokens()) {
          st.nextToken(); // take out delim
          if (!st.hasMoreTokens()) ret.add(null); // if is last token, add one more
        }
      }
    }

    return ret;
  }

  public String buildStringFromList(Collection list, String delim) {
    StringBuffer ret = new StringBuffer();

    if (list != null) {
      ;
      for (Iterator itr = list.iterator(); itr.hasNext(); ) {
        ret.append(itr.next());

        if (itr.hasNext()) ret.append(delim);
      }
    }

    return ret.toString();
  }

  public String truncate(String text, Integer maxLen) {
    if (text.length() <= maxLen) return text;
    return text.substring(0, maxLen - 3) + "...";
  }

  /**
   * Returns either the passed in String, or {@code null} if the String is {@code null} or empty
   * String ("")
   *
   * <pre>
   * StringUtils.defaultString(null)  = {@code null}
   * StringUtils.defaultString("")    = {@code null}
   * StringUtils.defaultString("bat") = "bat"
   * </pre>
   */
  public String getNullableString(String value) {
    if (value == null) {
      return null;
    } else if (value.trim().equals("")) {
      return null;
    }
    return value;
  }

  public String buildStringFromCamelString(String camel) {
    final String regSplitCamel = "(?<=[a-z])(?=[A-Z])|(?<=[A-Z])(?=[A-Z][a-z])";
    return buildStringFromStringArray(camel.split(regSplitCamel), " ");
  }
}
