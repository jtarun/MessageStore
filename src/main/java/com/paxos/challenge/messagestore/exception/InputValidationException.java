package com.paxos.challenge.messagestore.exception;

public class InputValidationException extends Exception {

  private static final long serialVersionUID = 1L;

  public InputValidationException() {
    super();
  }

  public InputValidationException(Throwable cause) {
    super(cause);
  }

  public InputValidationException(String message) {
    super(message);
  }

  public InputValidationException(String message, Throwable cause) {
    super(message, cause);
  }
}
