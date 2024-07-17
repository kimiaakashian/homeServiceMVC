package ir.home_service.dto;

import java.time.LocalDateTime;

public record ExceptionDto(String message,
                           LocalDateTime localDateTime) {
}
