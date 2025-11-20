package co.com.app.api.userapp.mapper;

import co.com.app.api.userapp.dto.UserAppDto;
import co.com.app.model.userapp.UserApp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", source = "createdAt", dateFormat = "yyyy-MM-dd HH:mm:ss")
    UserApp toEntity(UserAppDto dto);

    @Mapping(target = "createdAt", source = "createdAt", dateFormat = "yyyy-MM-dd HH:mm:ss")
    UserAppDto toDto(UserApp entity);
}
