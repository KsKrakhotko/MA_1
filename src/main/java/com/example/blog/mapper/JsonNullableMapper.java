package com.example.blog.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.openapitools.jackson.nullable.JsonNullable;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface JsonNullableMapper {

    default <T> JsonNullable<T> wrap(T value) {
        return JsonNullable.of(value);
    }

    default <T> T unwrap(JsonNullable<T> value) {
        return value == null ? null : value.orElse(null);
    }

    default <T> boolean isPresent(JsonNullable<T> value) {
        return value != null && value.isPresent();
    }
}