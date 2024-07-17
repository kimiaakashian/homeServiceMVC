package ir.home_service.controller;


import ir.home_service.dto.subService.SubServiceSaveRequest;
import ir.home_service.dto.subService.SubServiceSaveResponse;
import ir.home_service.dto.subService.SubServiceUpdateRequest;
import ir.home_service.dto.subService.SubServiceUpdateResponse;
import ir.home_service.mapper.SubServiceMapper;
import ir.home_service.model.SubService;
import ir.home_service.service.SubServiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class SubServiceController {
    private final SubServiceService subServiceService;


    @PostMapping("/addSubService")
    public ResponseEntity<SubServiceSaveResponse> addSubService(@Valid @RequestBody SubServiceSaveRequest request, @Valid@RequestParam int homeServiceId) {
        SubService mappedSubService = SubServiceMapper.INSTANCE.subServiceSaveRequestToModel(request);
        SubService savedSubService = subServiceService.addSubService(mappedSubService,homeServiceId);
        return new ResponseEntity<>(SubServiceMapper.INSTANCE.modelToSubServiceSaveResponse(savedSubService),
                HttpStatus.CREATED);
    }

    @PatchMapping("/updateSubService")
    public ResponseEntity<SubServiceUpdateResponse> updateSubService(@Valid @RequestBody SubServiceUpdateRequest request) {
        SubService mappedSubService = SubServiceMapper.INSTANCE.subServiceUpdateRequestToModel(request);
        SubService savedSubService = subServiceService.updateSubService(mappedSubService);
        return new ResponseEntity<>(SubServiceMapper.INSTANCE.modelToSubServiceUpdateResponse(savedSubService),
                HttpStatus.OK);
    }


    @GetMapping("/displaySubService")
    public ResponseEntity<List<SubServiceSaveResponse>> displayAllTwitt(@RequestParam int homeServiceId){
        List<SubService> subServices = subServiceService.displayAllSubServicesByHomeServiceId(homeServiceId);
        List<SubServiceSaveResponse> subServiceSaveResponses = new ArrayList<>();
        for (SubService subService : subServices) {
            subServiceSaveResponses.add(SubServiceMapper.INSTANCE.modelToSubServiceSaveResponse(subService));

        }
        return new ResponseEntity<>(subServiceSaveResponses,HttpStatus.OK);

    }
}
