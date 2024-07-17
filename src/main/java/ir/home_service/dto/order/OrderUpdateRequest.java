package ir.home_service.dto.order;

import jakarta.validation.constraints.Size;

public record OrderUpdateRequest(
        int id,
        String comments,
        int expertScore) {
}
