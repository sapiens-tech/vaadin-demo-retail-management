package io.sapiens.retail.config;

import io.sapiens.awesome.util.SecurityUtil;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

class CustomRequestCache extends HttpSessionRequestCache {

  @Override
  public void saveRequest(HttpServletRequest request, HttpServletResponse response) {

    if (!SecurityUtil.getInstance().isFrameworkInternalRequest(request)) {
      super.saveRequest(request, response);
    }
  }
}
