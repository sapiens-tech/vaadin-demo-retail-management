package com.redcross.infra.dto;

import com.redcross.infra.enums.UserOperationContextResultType;

public class UserOperationContextDto extends AbstractDto {

  private String key;

  private UserOperationContextResultType resultType;

  private String
      messageFromBundle; // message gotten from context using key at UserOperationContextInterceptor

  // helper methods

  public void set(UserOperationContextResultType resultType, String key) {
    this.key = key;
    this.resultType = resultType;
  }

  public String getKey() {
    return key;
  }

  public UserOperationContextResultType getResultType() {
    return resultType;
  }

  public String getMessageFromBundle() {
    return messageFromBundle;
  }

  public void setMessageFromBundle(String messageFromBundle) {
    this.messageFromBundle = messageFromBundle;
  }
}
