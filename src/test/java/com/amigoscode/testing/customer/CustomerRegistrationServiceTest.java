package com.amigoscode.testing.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static net.bytebuddy.matcher.ElementMatchers.any;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;

class CustomerRegistrationServiceTest {

    private CustomerRegistrationService underTest;

    @Mock
    private CustomerRepository customerRepository;

    @Captor
    private ArgumentCaptor<Customer> customerArgumentCaptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        underTest=new CustomerRegistrationService(customerRepository);
    }

    @Test
    void itShouldRegisterNewCustomer() {
        //GIVEN a phone number and request
        String phoneNumber = "000099";
        Customer customer=new Customer(UUID.randomUUID(),"Maryam",phoneNumber);

        //... a request
        CustomerRegistrationRequest request=new CustomerRegistrationRequest(customer);

        // to make optional (inside mocked customerRepository) be empty, to later test if we can register a customer with new phone number
        given(customerRepository.selectCustomerByPhoneNumber(phoneNumber)).willReturn(Optional.empty());

        //WHEN
        underTest.registerNewCustomer(request);

        //THEN
        then(customerRepository).should().save(customerArgumentCaptor.capture());
        Customer customerArgumentCaptorValue=customerArgumentCaptor.getValue();
        assertThat(customerArgumentCaptorValue).isEqualToComparingFieldByField(customer);
    }

    @Test
    void itShouldNotRegisterNewCustomerWhenCustomerAlreadyExists() {
        //GIVEN a phone number and request
        String phoneNumber = "000099";
        UUID id=UUID.randomUUID();
        Customer customer=new Customer(id,"Maryam",phoneNumber);

        //... a request
        CustomerRegistrationRequest request=new CustomerRegistrationRequest(customer);

        // to make optional (inside mocked customerRepository) be empty, to later test if we can register a customer with new phone number
        given(customerRepository.selectCustomerByPhoneNumber(phoneNumber)).willReturn(Optional.of(customer));

        //WHEN
        underTest.registerNewCustomer(request);

        //THEN
        then(customerRepository).should().selectCustomerByPhoneNumber(phoneNumber);
        then(customerRepository).shouldHaveNoMoreInteractions();
    }
}