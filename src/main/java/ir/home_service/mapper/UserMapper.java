package ir.home_service.mapper;


import ir.home_service.dto.user.UserSaveRequest;
import ir.home_service.dto.user.UserSaveResponse;
import ir.home_service.model.Expert;
import ir.home_service.model.Users;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
 
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class );

 
    Users userSaveRequestToModel(UserSaveRequest request);
    UserSaveResponse modelToUserSaveResponse(Users user);


}