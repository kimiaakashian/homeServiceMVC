package ir.home_service.controller;

import ir.home_service.dto.expertSuggestion.ExpertSuggestionSaveRequest;
import ir.home_service.dto.expertSuggestion.ExpertSuggestionSaveResponse;
import ir.home_service.mapper.ExpertSuggestionMapper;
import ir.home_service.model.ExpertSuggestion;
import ir.home_service.service.ExpertSuggestionService;
import ir.home_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ExpertSuggestionController {
    private final ExpertSuggestionService expertSuggestionService;
    private final OrderService orderService;

    @PostMapping("/sendSugestion/{orderId}/{expertId}")
    public ResponseEntity<ExpertSuggestionSaveResponse> sendSuggestion(@PathVariable int orderId, @PathVariable int expertId, @RequestBody ExpertSuggestionSaveRequest request) {
        ExpertSuggestion mappedExpertSuggestion = ExpertSuggestionMapper.INSTANCE.expertSuggestionSaveRequestToModel(request);
        ExpertSuggestion savedExpertSuggestion = expertSuggestionService.sendSuggestionForOrder(orderId,expertId,mappedExpertSuggestion);
        return new ResponseEntity<>(ExpertSuggestionMapper.INSTANCE.modelToExpertSuggestionSaveResponse(savedExpertSuggestion),
                HttpStatus.CREATED);
    }

    @GetMapping("/displaySuggestion/{orderId}")
    public ResponseEntity<List<ExpertSuggestionSaveResponse>> displayAllSuggestion(@PathVariable int orderId){
        List<ExpertSuggestion> expertSuggestions = orderService.getSuggestionsForOrder(orderId);
        List<ExpertSuggestionSaveResponse> expertSuggestionSaveResponses = new ArrayList<>();
        for (ExpertSuggestion expertSuggestion : expertSuggestions) {
            expertSuggestionSaveResponses.add(ExpertSuggestionMapper.INSTANCE.modelToExpertSuggestionSaveResponse(expertSuggestion));

        }
        return new ResponseEntity<>(expertSuggestionSaveResponses,HttpStatus.OK);

    }
}
