package io.sapiens.awesome.exception;

public class SystemException extends AbstractException {

  public SystemException(Throwable t) {
    super(t.getMessage(), t);
  }

  public SystemException(String errorKey, Throwable t) {
    super(errorKey, t);
  }

  public SystemException(String errorKey) {
    super(errorKey);
  }
}
