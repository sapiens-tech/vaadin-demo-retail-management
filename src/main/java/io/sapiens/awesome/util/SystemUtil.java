package io.sapiens.awesome.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class SystemUtil {
  private static SystemUtil me;
  private Logger logger = LoggerFactory.getLogger(SystemUtil.class);

  public static SystemUtil getInstance() {
    if (me == null) me = new SystemUtil();

    return me;
  }

  public String resolveContentType(String filename) {
    String ret = "";

    if (filename.endsWith(".pdf")) ret = "application/pdf";
    if (filename.endsWith(".xls")) ret = "application/vnd.ms-excel";
    if (filename.endsWith(".doc")) ret = "application/msword";
    if (filename.endsWith(".zip")) ret = "application/x-zip";

    return ret;
  }

  public <K, V> Map<K, V> buildMap(Map<K, V> map, Object... data) {
    if (data != null) {
      if (data.length % 2 != 0) {
        throw new RuntimeException("Data must be in even key-value pairs.");
      }

      for (int i = 0; i < data.length; i += 2) {
        K key = (K) data[i];
        V value = (V) data[i + 1];

        map.put(key, value);
      }
    }

    return map;
  }

  public Set sortSet(Set set, Comparator comparator) {
    List bufferList = new ArrayList<>();
    bufferList.addAll(set);
    Collections.sort(bufferList, comparator);
    set = null;
    set = new LinkedHashSet();
    for (Iterator itr = bufferList.iterator(); itr.hasNext(); ) set.add(itr.next());
    bufferList = null;

    return set;
  }

  public String getSimpleClassName(String className) {
    return StringUtil.getInstance().getLastToken(className, ".");
  }

  public Integer getIndexInMap(Map map, Object key) {
    Integer count = 0;

    for (Iterator itr = map.keySet().iterator(); itr.hasNext(); count++) {
      if (key.equals(itr.next())) break;
    }
    if (count == map.size()) count = -1;

    return count;
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

  public String buildStringFromList(List list, String delim) {
    String[] arr = new String[list.size()];
    for (int i = 0; i < list.size(); i++) arr[i] = list.get(i).toString();
    return buildStringFromStringArray(arr, delim);
  }

  public void populate(Object dest, Map orig) {
    if (dest != null && orig != null) {
      for (Iterator itr = orig.keySet().iterator(); itr.hasNext(); ) {
        Object key = itr.next();
        Object value = orig.get(key);

        try {
          if (value != null && value.toString().length() > 0 && !value.toString().equals("0")) {
            String setterMethodName = buildFieldSetter(key.toString());
            Method setterMethod =
                dest.getClass().getMethod(setterMethodName, new Class[] {value.getClass()});

            try {
              setterMethod.invoke(dest, new Object[] {value});
            } catch (InvocationTargetException | IllegalAccessException e) {
              e.printStackTrace();
            }
          }
        } catch (NoSuchMethodException e) { // do nothing }
        }
      }
    }
  }

  public String buildFieldGetter(String fieldName) {
    char[] fieldNameChars = fieldName.toCharArray();
    fieldNameChars[0] = Character.toUpperCase(fieldNameChars[0]);
    return "get" + new String(fieldNameChars);
  }

  public String buildFieldSetter(String fieldName) {
    char[] fieldNameChars = fieldName.toCharArray();
    fieldNameChars[0] = Character.toUpperCase(fieldNameChars[0]);

    return "set" + new String(fieldNameChars);
  }

  public Object getObjectValue(Object object, String fieldName) {
    boolean instantiateNullObjects = true;

    Object ret = null;
    Object targetObj = object;

    // if no delim
    if (fieldName.indexOf(".") == -1) {
      try {
        ret =
            targetObj
                .getClass()
                .getMethod(buildFieldGetter(fieldName), null)
                .invoke(targetObj, null);
      } catch (InvocationTargetException e) {
        // e.printStackTrace();
      } catch (IllegalAccessException e) {
        // e.printStackTrace();
      } catch (NoSuchMethodException e) {
        // e.printStackTrace();
      }
    } else {
      // if have delim
      // get tokens
      List<String> tokens = new ArrayList<String>();
      StringTokenizer st = new StringTokenizer(fieldName, ".");
      while (st.hasMoreTokens()) {
        tokens.add(st.nextToken());
      }

      try {
        // go thru' tokens and get object value
        for (Iterator<String> itr = tokens.iterator(); itr.hasNext(); ) {
          String token = itr.next();
          Object objValue =
              targetObj.getClass().getMethod(buildFieldGetter(token), null).invoke(targetObj, null);

          if (instantiateNullObjects
              && objValue == null
              && itr.hasNext()) { // do not perform for LAST
            objValue = targetObj.getClass().getDeclaredField(token).getType().newInstance();
            targetObj
                .getClass()
                .getMethod(buildFieldSetter(token), objValue.getClass())
                .invoke(targetObj, objValue);
          }

          // if object value not null
          if (objValue != null) {
            // if have next, then reassign target object
            if (itr.hasNext()) {
              targetObj = objValue;
            } else {
              ret = objValue;
            }
          }
        }

        tokens = null;
      } catch (InstantiationException e) {
        // e.printStackTrace();
      } catch (NoSuchFieldException e) {
        // e.printStackTrace();
      } catch (InvocationTargetException e) {
        // e.printStackTrace();
      } catch (IllegalAccessException e) {
        // e.printStackTrace();
      } catch (NoSuchMethodException e) {
        // e.printStackTrace();
      }
    }

    return ret;
  }
}
