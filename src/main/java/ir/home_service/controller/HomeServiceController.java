package ir.home_service.controller;

import ir.home_service.dto.homeService.HomeServiceSaveRequest;
import ir.home_service.dto.homeService.HomeServiceSaveResponse;
import ir.home_service.dto.subService.SubServiceSaveResponse;
import ir.home_service.mapper.HomeServiceMapper;
import ir.home_service.mapper.SubServiceMapper;
import ir.home_service.model.HomeService;
import ir.home_service.model.SubService;
import ir.home_service.service.HomeServiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class HomeServiceController {
    private final HomeServiceService homeServiceService;

    @PostMapping("/addHomeService")
    public ResponseEntity<HomeServiceSaveResponse> addHomeService(@Valid @RequestBody HomeServiceSaveRequest request) {
        HomeService mappedHomeService = HomeServiceMapper.INSTANCE.homeServiceSaveRequestToModel(request);
        HomeService savedHomeService = homeServiceService.addHomeService(mappedHomeService);
        return new ResponseEntity<>(HomeServiceMapper.INSTANCE.modelToHomeServiceSaveResponse(savedHomeService),
                HttpStatus.CREATED);
    }


    @GetMapping("/displayHomeService")
    public ResponseEntity<List<HomeServiceSaveResponse>> displayAllTwitt(){
        List<HomeService> homeServices = homeServiceService.findAllHomeService();
        List<HomeServiceSaveResponse> homeServiceSaveResponses = new ArrayList<>();
        for (HomeService homeService : homeServices) {
            homeServiceSaveResponses.add(HomeServiceMapper.INSTANCE.modelToHomeServiceSaveResponse(homeService));

        }
        return new ResponseEntity<>(homeServiceSaveResponses,HttpStatus.OK);

    }
}
