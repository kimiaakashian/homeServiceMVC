package ir.home_service.dto.expertSuggestion;

import ir.home_service.dto.expert.ExpertSaveResponse;
import ir.home_service.dto.order.OrderSaveResponse;
import ir.home_service.model.enums.ExpertSuggestionStatus;

public record ExpertSuggestionSaveResponse(
        int id,
        String date,
        String time,
        Double suggestedPrice,
        String durationOfWork,
        ExpertSaveResponse expert,
        ExpertSuggestionStatus expertSuggestionStatus,
        OrderSaveResponse order) {
}
