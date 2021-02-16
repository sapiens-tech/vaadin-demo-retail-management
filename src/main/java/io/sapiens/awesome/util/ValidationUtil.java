package com.redcross.infra.utils;

import org.apache.commons.validator.routines.EmailValidator;
import org.apache.log4j.Logger;

public class ValidationUtils {

  private static ValidationUtils me;
  private Logger logger = Logger.getLogger(ValidationUtils.class);

  public static ValidationUtils getInstance() {
    if (me == null) me = new ValidationUtils();

    return me;
  }

  public boolean isEmptyString(String string) {
    return (string == null
        || string.trim().length() == 0
        || (string != null && string.equals("null")));
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
    //		String expression = "^[\\w\\-]([\\.\\w])+[\\w]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
    ////		String expression =
    // "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    //		CharSequence inputStr = emailAddress;
    //		Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
    //		Matcher matcher = pattern.matcher(inputStr);
    //		return matcher.matches();
    return EmailValidator.getInstance().isValid(emailAddress);
  }

  public boolean isValidMobileNumber(String mobileNumber) {
    if (mobileNumber == null) return true;
    if (mobileNumber != null && mobileNumber.trim().length() > 0) {
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
    return (o == null) ? true : false;
  }

  public boolean isEmptyStringArray(String[] string) {
    return (string == null || string.length == 0);
  }

  public boolean isValidNric(String nric) {
    int[] weight = {2, 7, 6, 5, 4, 3, 2};
    String[] lookup_s = {"J", "Z", "I", "H", "G", "F", "E", "D", "C", "B", "A"};
    String[] lookup_t = {"G", "F", "E", "D", "C", "B", "A", "J", "Z", "I", "H"};
    String[] lookup_f = {"X", "W", "U", "T", "R", "Q", "P", "N", "M", "L", "K"};
    String[] lookup_g = {"R", "Q", "P", "N", "M", "L", "K", "X", "W", "U", "T"};

    if (nric != null && nric.length() > 0 && nric.length() == 9) {
      String prefix = nric.substring(0, 1);
      String postfix = nric.substring(8, 9);
      String number = nric.substring(1, 8);

      int c = 0;
      try {
        for (int i = 0; i < number.length(); i++) {
          int charAt = new Integer(new Character(number.charAt(i)).toString());
          c += charAt * weight[i];
        }

        c = c % 11;

        String[] touse = null;
        if (prefix.toLowerCase().equals("s")) touse = lookup_s;
        else if (prefix.toLowerCase().equals("t")) touse = lookup_t;
        else if (prefix.toLowerCase().equals("f")) touse = lookup_f;
        else if (prefix.toLowerCase().equals("g")) touse = lookup_g;
        else return false;

        if (postfix.toUpperCase().equals(touse[c])) return true;
        return false;
      } catch (Exception e) {
        return false;
      }
    }
    return false;
  }
}
