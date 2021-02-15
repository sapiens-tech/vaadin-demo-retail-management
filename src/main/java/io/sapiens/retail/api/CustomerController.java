package io.sapiens.retail.api;

import io.sapiens.retail.backend.enums.Role;
import io.sapiens.retail.backend.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

  private final CustomerService customerService;

  public CustomerController(@Autowired CustomerService customerService) {
    this.customerService = customerService;
  }

  @GetMapping("/")
  public Object retrieveAllCustomers() {
    return customerService.retrieveByRole(Role.CUSTOMER);
  }
}
