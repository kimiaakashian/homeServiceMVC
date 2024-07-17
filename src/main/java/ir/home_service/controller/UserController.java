package ir.home_service.controller;

import ir.home_service.dto.customer.CustomerSaveRequest;
import ir.home_service.dto.customer.CustomerSaveResponse;
import ir.home_service.dto.expert.ExpertSaveRequest;
import ir.home_service.dto.expert.ExpertSaveResponse;
import ir.home_service.helper.ImageUtil;
import ir.home_service.mapper.CustomerMapper;
import ir.home_service.mapper.ExpertMapper;
import ir.home_service.model.Customer;
import ir.home_service.model.Expert;
import ir.home_service.service.CustomerService;
import ir.home_service.service.ExpertService;
import ir.home_service.service.ExpertSubServiceService;
import ir.home_service.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
public class UserController {
    private final ExpertService expertService;
    private final CustomerService customerService;
    private final ExpertSubServiceService expertSubServiceService;
    private final UserService userService;



    @PostMapping("/CustomerRegister")
    public ResponseEntity<CustomerSaveResponse> customerRegister(@Valid @RequestBody CustomerSaveRequest request) {
        Customer mappedCustomer = CustomerMapper.INSTANCE.customerSaveRequestToModel(request);
        Customer savedCustomer = customerService.CustomerRegistration(mappedCustomer);
        return new ResponseEntity<>(CustomerMapper.INSTANCE.modelToCustomerSaveResponse(savedCustomer),
                HttpStatus.CREATED);
    }

    @PostMapping("/expertRegister")
    public ResponseEntity<ExpertSaveResponse> expertRegister(@Valid @RequestBody ExpertSaveRequest request) {
        Expert mappedExpert = ExpertMapper.INSTANCE.expertSaveRequestToModel(request);
        expertService.isValidPicture(request.pictureAddress());
        mappedExpert.setPicture( ImageUtil.readFileToByteArray(request.pictureAddress()));
        Expert savedExpert = expertService.ExpertRegistration(mappedExpert);
        return new ResponseEntity<>(ExpertMapper.INSTANCE.modelToExpertSaveResponse(savedExpert),
                HttpStatus.CREATED);
    }

    @PatchMapping("/verifiedExpert/{expertId}")
    public String verifiedExpert(@PathVariable int expertId){
        expertService.updateExpertStatusToVerified(expertId);
        return " متخصص با ایدی" + expertId + "تایید شد ";
    }


    @PatchMapping("/addExpertToSubService")
    public String addExpertToSubService(@Valid@RequestParam int expertId,@RequestParam int subServiceId){
       expertSubServiceService.addExpertToSubService(expertId,subServiceId);
        return " متخصص با ایدی" + expertId + " به زیر خدمت با ایدی "+ subServiceId +" اضافه شد ";

    }

    @GetMapping("/searchUsers")
    public List searchUsers(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String role,
            @RequestParam(required = false) String filed,
            @RequestParam(required = false) Double minScore
    ) {
        List returlList = null;

        if(role!=null && role.equals("expert") || minScore != null)
            role = "expert";
        else
            role = "costumer";

        if(role .equals("costumer"))
            returlList = userService.findUsers( firstName, lastName, email);
        else
            returlList = expertService.findExperts(firstName, lastName, email , minScore);

        return returlList;
    }


    @PatchMapping("/changePassword/{expertId}")
    public String changePassword(@RequestParam int userId, @RequestParam String oldPassword,
                                 @RequestParam String newPassword){
        userService.changePassword(userId,oldPassword,newPassword);
        return "رمز با موفقیت تغییر یافت ";
    }





}
