package io.sapiens.retail.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class PropertyHelper {
  @Value("${meta.loginTitle}")
  public String loginTitle;

  @Value("${meta.loginDescription}")
  public String loginDescription;
}
