package com.redcross.infra.dto;

public class ErrorDto extends AbstractDto {

  private static final String TO_STRING_DELIM = "|";

  private Boolean isFieldError;

  private String errorField;

  private String errorKey;

  private Object[] errorArgs;

  public ErrorDto(String errorField, String errorKey, Object... errorArgs) {
    this.isFieldError = true;
    this.errorField = errorField;
    this.errorKey = errorKey;
    this.errorArgs = errorArgs;
  }

  public ErrorDto(String errorKey, Object... errorArgs) {
    this.isFieldError = false;
    this.errorKey = errorKey;
    this.errorArgs = errorArgs;
  }

  public String getErrorField() {
    return errorField;
  }

  public void setErrorField(String errorField) {
    this.errorField = errorField;
  }

  public Object[] getErrorArgs() {
    return errorArgs;
  }

  public Boolean getIsFieldError() {
    return isFieldError;
  }

  public String getErrorKey() {
    return errorKey;
  }

  public String toString() {
    if (isFieldError)
      return isFieldError + TO_STRING_DELIM + errorField + TO_STRING_DELIM + errorKey;
    else return isFieldError + TO_STRING_DELIM + errorKey;
  }
}
