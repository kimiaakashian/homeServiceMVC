package ir.home_service.dto.order;

public record OrderUpdateResponse (int id,
                                   String comments,
                                   int expertScore) {
}
