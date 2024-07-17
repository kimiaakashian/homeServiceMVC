package ir.home_service.dto.expertSuggestion;

public record ExpertSuggestionSaveRequest(String date,
        String time,
        Double suggestedPrice,
        String durationOfWork ) {
}
