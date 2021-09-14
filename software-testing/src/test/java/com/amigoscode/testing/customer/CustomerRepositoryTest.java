package com.amigoscode.testing.customer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository underTest;
    @Test
    void itShouldSelectCustomerByPhoneNumber() {
        //GIVEN
        //WHEN
        //THEN
    }

    @Test
    void itShouldSaveCustomer() {
        //GIVEN
        UUID id = UUID.randomUUID();
        Customer customer =new Customer(id,"Abel","0000");
        //WHEN
        underTest.save(customer);
        //THEN
        Optional<Customer> optionalCustomer=underTest.findById(id);
        assertThat(optionalCustomer).isPresent()
                .hasValueSatisfying(c->{
//                    assertThat(c.getId()).isEqualTo(id);
//                    assertThat(c.getName()).isEqualTo("Abel");
//                    assertThat(c.getPhoneNumber()).isEqualTo("0000");
                    //can write it like this:
                    assertThat(c).isEqualToComparingFieldByField(customer);

                });
    }
}