package com.redcross.infra.dto;

import java.util.ArrayList;
import java.util.List;

public class ErrorsDto extends AbstractDto {

  private List<ErrorDto> errors = new ArrayList<ErrorDto>();

  public boolean hasErrors() {
    return errors.size() > 0;
  }

  public List<ErrorDto> getErrors() {
    return errors;
  }

  public void setErrors(List<ErrorDto> errors) {
    this.errors = errors;
  }

  public void add(ErrorDto error) {
    getErrors().add(error);
  }

  public String toString() {
    return getErrors().toString();
  }
}
