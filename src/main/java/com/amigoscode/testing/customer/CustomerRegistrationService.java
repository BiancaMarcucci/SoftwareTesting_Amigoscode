package com.amigoscode.testing.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerRegistrationService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerRegistrationService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void registerNewCustomer(CustomerRegistrationRequest request){
        // want to check if:
        // 1. phone number is already taken
        // 2. if taken lets check if belongs to same customer
            //2.1 if yes return
            //2.2 otherwise throw exception
        //3. save customer
        // we will keep it simple and just compare the names of the customers, but in real life would need more unique attributes

        String phoneNumber=request.getCustomer().getPhoneNumber();
        Optional<Customer> customerOptional =customerRepository.selectCustomerByPhoneNumber(phoneNumber);
        if (customerOptional.isPresent()){
            Customer customer=customerOptional.get();
            if (customer.getName().equals(request.getCustomer().getName())){
                return;
            }
            throw new IllegalStateException(String.format("Phone number [%s] has already been taken", phoneNumber));
        }
        customerRepository.save(request.getCustomer());
    }
}
