package io.sapiens.awesome.exception;

public class AbstractException extends RuntimeException {

  public AbstractException(Throwable t) {
    super(t.getMessage(), t);
  }

  protected AbstractException(String errorKey, Throwable t) {
    super(errorKey, t);
  }

  public AbstractException(String errorKey) {
    super(errorKey);
  }

  protected AbstractException() {
    super();
  }
}
