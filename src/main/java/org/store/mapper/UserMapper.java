package org.store.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.store.domain.User;
import org.store.dto.UserDto;
import org.store.dto.request.CreateUserRequest;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface UserMapper  extends  EntityMapper<UserDto,User>{
    User toUser(CreateUserRequest request);
}