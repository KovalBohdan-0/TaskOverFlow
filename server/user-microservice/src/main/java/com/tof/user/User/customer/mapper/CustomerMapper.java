package com.tof.user.User.customer.mapper;

import com.tof.user.User.customer.dto.RegistrationDto;
import com.tof.user.User.config.MapperConfig;
import com.tof.user.User.customer.entity.Customer;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface CustomerMapper {
    Customer mapToCustomer(RegistrationDto registrationDto);
}
