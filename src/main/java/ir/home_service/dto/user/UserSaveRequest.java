package ir.home_service.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

public record UserSaveRequest(
        @Pattern(regexp = "^[a-zA-Z]+$")
        String firstName,
        @Pattern(regexp = "^[a-zA-Z]+$")

        String lastName,
        @Email
        String email,
        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")

        String password) {

}
