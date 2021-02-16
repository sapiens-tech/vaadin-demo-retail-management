package io.sapiens.awesome.service;

import io.sapiens.awesome.dto.UserSessionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserSessionService {
  private static final long SESSION_TIMEOUT_IN_MILLIS = 1000 * 60 * 20; // 20mins
  private static final String COOKIE_TOKEN_VALUE_DELIM = "|";
  private final ThreadLocal<UserSessionDto> userSession = new ThreadLocal<UserSessionDto>();

  private UserService userService;

  public UserSessionService(@Autowired UserService userService) {
    this.userService = userService;
  }
}
