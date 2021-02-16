package io.sapiens.awesome.dto;

import io.sapiens.awesome.model.User;
import io.sapiens.awesome.util.HashUtil;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class UserSessionDto extends AbstractDto {

  /* necessary for signature signing */
  private String userId;

  private Timestamp loggedInOn;

  private Timestamp lastAccessedOn;

  private String hashSalt;
  /* end signature signing */

  private Map<String, Object> valuesToPersistAcrossRequests = new LinkedHashMap<String, Object>();

  // transient
  private User user;

  private Boolean isAccessingNonAuth = false;

  private Boolean isFromSecureCookieParameter = false;

  public UserSessionDto(
      String userId, Timestamp loggedInOn, Timestamp lastAccessedOn, String hashSalt) {
    super();
    this.userId = userId;
    this.loggedInOn = loggedInOn;
    this.lastAccessedOn = lastAccessedOn;
    this.hashSalt = hashSalt;
  }

  public String buildValiditySignature() {
    String toSign = getUserId() + (getLoggedInOn().getTime() + getLastAccessedOn().getTime());
    return HashUtil.getInstance().getHash(toSign, getHashSalt());
  }

  public void addValuesToPersistOntoCookie(String key, Object value) {
    valuesToPersistAcrossRequests.put(key, value);
  }

  public void removeValuesToPersistOntoCookie(String key) {
    valuesToPersistAcrossRequests.remove(key);
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public Timestamp getLoggedInOn() {
    return loggedInOn;
  }

  public void setLoggedInOn(Timestamp loggedInOn) {
    this.loggedInOn = loggedInOn;
  }

  public Timestamp getLastAccessedOn() {
    return lastAccessedOn;
  }

  public void setLastAccessedOn(Timestamp lastAccessedOn) {
    this.lastAccessedOn = lastAccessedOn;
  }

  public String getHashSalt() {
    return hashSalt;
  }

  public void setHashSalt(String hashSalt) {
    this.hashSalt = hashSalt;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Map<String, Object> getValuesToPersistAcrossRequests() {
    return Collections.unmodifiableMap(valuesToPersistAcrossRequests);
  }

  public void setValuesToPersistAcrossRequests(Map<String, Object> valuesToPersistAcrossRequests) {
    this.valuesToPersistAcrossRequests = valuesToPersistAcrossRequests;
  }

  public Boolean getIsAccessingNonAuth() {
    return isAccessingNonAuth;
  }

  public void setIsAccessingNonAuth(Boolean isAccessingNonAuth) {
    this.isAccessingNonAuth = isAccessingNonAuth;
  }

  public Boolean getIsFromSecureCookieParameter() {
    return isFromSecureCookieParameter;
  }

  public void setIsFromSecureCookieParameter(Boolean isFromSecureCookieParameter) {
    this.isFromSecureCookieParameter = isFromSecureCookieParameter;
  }
}
