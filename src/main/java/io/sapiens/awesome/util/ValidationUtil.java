package io.sapiens.awesome.util;

import org.apache.commons.validator.routines.EmailValidator;

public class ValidationUtil {

  private static ValidationUtil me;

  public static ValidationUtil getInstance() {
    if (me == null) me = new ValidationUtil();

    return me;
  }

  public boolean isEmptyString(String string) {
    return string == null || string.trim().length() == 0 || string.equals("null");
  }

  public boolean isInteger(String string) {
    try {
      Long.parseLong(string);
    } catch (NumberFormatException e) {
      return false;
    }
    return true;
  }

  public boolean isDataTooLong(String string, int max) {
    if (string.length() > max) {
      return true;
    }
    return false;
  }

  public boolean isValidEmailAddress(String emailAddress) {
    if (emailAddress == null || emailAddress.trim().length() == 0) {
      return false;
    }

    return EmailValidator.getInstance().isValid(emailAddress);
  }

  public boolean isValidMobileNumber(String mobileNumber) {
    if (mobileNumber == null) return true;
    if (mobileNumber.trim().length() > 0) {
      /*take away length validation*/
      //			if (mobileNumber.length() != 8) return false;
      try {
        Long.parseLong(mobileNumber);
      } catch (NumberFormatException e) {
        return false;
      }
      return true;
    }
    return false;
  }

  public boolean isObjectNull(Object o) {
    return o == null;
  }

  public boolean isEmptyStringArray(String[] string) {
    return (string == null || string.length == 0);
  }
}
