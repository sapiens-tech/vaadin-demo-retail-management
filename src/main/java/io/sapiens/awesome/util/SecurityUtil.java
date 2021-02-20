package io.sapiens.awesome.util;

import com.vaadin.flow.server.HandlerHelper;
import com.vaadin.flow.shared.ApplicationConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Stream;

public final class SecurityUtil {

  private Logger logger = LoggerFactory.getLogger(SecurityUtil.class);

  private SecurityUtil() {}

  private static SecurityUtil me;

  public static SecurityUtil getInstance() {
    if (me == null) me = new SecurityUtil();
    return me;
  }

  public boolean isFrameworkInternalRequest(HttpServletRequest request) {

    final String parameterValue = request.getParameter(ApplicationConstants.REQUEST_TYPE_PARAMETER);
    return parameterValue != null
        && Stream.of(HandlerHelper.RequestType.values())
            .anyMatch(r -> r.getIdentifier().equals(parameterValue));
  }

  public boolean isUserLoggedIn() {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return authentication != null
        && !(authentication instanceof AnonymousAuthenticationToken)
        && authentication.isAuthenticated();
  }
}
