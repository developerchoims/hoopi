package com.ms.hoopi;

import com.ms.hoopi.entity.Users;
import com.ms.hoopi.join.UsersDto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-25T18:20:51+0900",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.8.jar, environment: Java 17.0.12 (Amazon.com Inc.)"
)
@Component
public class DtoEntMapperImpl implements DtoEntMapper {

    @Override
    public Users toEntity(UsersDto dto) {
        if ( dto == null ) {
            return null;
        }

        Users.UsersBuilder users = Users.builder();

        users.usersCd( dto.getUsersCd() );
        users.usersNm( dto.getUsersNm() );
        users.usersId( dto.getUsersId() );
        users.usersPw( dto.getUsersPw() );
        users.usersEmail( dto.getUsersEmail() );
        users.usersPhone( dto.getUsersPhone() );

        users.usersYn( "Y" );
        users.usersRole( "USER" );

        return users.build();
    }

    @Override
    public UsersDto toDto(Users entity) {
        if ( entity == null ) {
            return null;
        }

        UsersDto usersDto = new UsersDto();

        usersDto.setUsersCd( entity.getUsersCd() );
        usersDto.setUsersNm( entity.getUsersNm() );
        usersDto.setUsersId( entity.getUsersId() );
        usersDto.setUsersPw( entity.getUsersPw() );
        usersDto.setUsersEmail( entity.getUsersEmail() );
        usersDto.setUsersPhone( entity.getUsersPhone() );
        usersDto.setUsersRole( entity.getUsersRole() );
        usersDto.setUsersYn( entity.getUsersYn() );

        return usersDto;
    }
}
