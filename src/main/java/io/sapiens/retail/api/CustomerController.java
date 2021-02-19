package io.sapiens.retail.api;

import io.sapiens.retail.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

  private UserService userService;

  @Autowired
  public void setCustomerService(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/")
  public Object retrieveAllCustomers() {
    return userService.retrieveCustomer();
  }
}
