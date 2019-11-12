package org.store.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.store.domain.User;
import org.store.domain.UserShipping;
import org.store.dto.UserDto;
import org.store.dto.UserShippingDto;
import org.store.dto.request.CreateUserRequest;
import org.store.dto.request.CreateUserShippingRequest;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface UserShippingMapper extends  EntityMapper<UserShippingDto, UserShipping>{
    UserShipping toUserShipping(CreateUserShippingRequest request);
}