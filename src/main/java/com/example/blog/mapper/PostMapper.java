package com.example.blog.mapper;

import com.example.blog.dto.PostCreateDTO;
import com.example.blog.dto.PostDTO;
import com.example.blog.dto.PostUpdateDTO;
import com.example.blog.model.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        uses = { JsonNullableMapper.class, ReferenceMapper.class },
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class PostMapper {

    @Mapping(target = "author", source = "authorId")
    public abstract Post map(PostCreateDTO dto);

    @Mapping(source = "author.id", target = "authorId")
    public abstract PostDTO map(Post model);

    @Mapping(target = "author", source = "authorId")
    public abstract void update(PostUpdateDTO dto, @MappingTarget Post model);
}