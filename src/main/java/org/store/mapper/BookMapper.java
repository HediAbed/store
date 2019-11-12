package org.store.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.store.domain.Book;
import org.store.domain.User;
import org.store.dto.BookDto;
import org.store.dto.UserDto;
import org.store.dto.request.CreateBookRequest;
import org.store.dto.request.CreateUserRequest;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface BookMapper extends  EntityMapper<BookDto, Book>{
    Book toBook(CreateBookRequest request);
}