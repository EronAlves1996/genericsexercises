package com.eronalves.genericsexercises;

public class UnmatchedModelSpecException extends RuntimeException {

  public UnmatchedModelSpecException(String message) {
    super(message);
  }

  public UnmatchedModelSpecException(int headerFields, int lineFields, int lineNumber) {
    this(String.format("Header and line have different field number. Header: %d, Line: %d, Line count: %d",
        headerFields, lineFields, lineNumber));
  }

}
