package com.tof.user.customer.mapper;

import com.tof.user.config.MapperConfig;
import com.tof.user.customer.dto.CustomerDto;
import com.tof.user.customer.dto.CustomerUserDetailsDto;
import com.tof.user.customer.dto.RegistrationDto;
import com.tof.user.customer.entity.Customer;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface CustomerMapper {
    Customer mapToCustomer(RegistrationDto registrationDto);
    CustomerDto mapToCustomerDto(Customer customer);
    CustomerUserDetailsDto mapToCustomerUserDetailsDto(Customer customer);
}
