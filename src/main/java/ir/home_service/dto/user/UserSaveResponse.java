package ir.home_service.dto.user;

public record UserSaveResponse (Integer id,
                                String firstName,
                                String lastName,
                                String email,
                                String DateOfRegistration){
}
