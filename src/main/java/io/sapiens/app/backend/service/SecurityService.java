package io.sapiens.app.backend.service;

import org.springframework.stereotype.Service;

@Service
public class SecurityService {

  public boolean checkAuthenticated(String identifier, String password) {
    return true;
  }
}
