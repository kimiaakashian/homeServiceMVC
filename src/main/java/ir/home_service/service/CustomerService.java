package ir.home_service.service;


import ir.home_service.exception.InformationDuplicateException;
import ir.home_service.exception.InvalidInformationException;
import ir.home_service.exception.NotFoundException;
import ir.home_service.helper.DateConvertorNew;
import ir.home_service.model.Customer;
import ir.home_service.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public Customer CustomerRegistration(Customer customer) {

        if (checkData("firstName", customer.getFirstName(), "^[a-zA-Z]+$")
                && checkData("lastName", customer.getLastName(), "^[a-zA-Z]+$")
                && checkData("email", customer.getEmail(), "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")
                && checkData("password", customer.getPassword(), "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$")
        ) {
            customer.setDateOfRegistration(DateConvertorNew.todayDate());
            try {
                customer = customerRepository.save(customer);
            } catch (Exception e) {
                String message = e.getMessage().split("\\[")[1];
                if (message.indexOf("email") > 0)
                    throw new InformationDuplicateException("کاربر تکراری است");
                else
                    throw new InformationDuplicateException(message);

            }
        }
        return customer;
    }

    public boolean checkData(String fieldName, String fieldValue, String regx) {
        boolean isCorrect = false;
        if (fieldValue == null || !fieldValue.matches(regx) || fieldValue.isEmpty()) {
            throw new InvalidInformationException("اطلاعات وارد شده برای فیلد "
                    + fieldName + " صحیح نمی باشد!");
        } else {
            isCorrect = true;
        }
        return isCorrect;
    }

    public Customer findById(int customerId) {
        return customerRepository.findById(customerId).orElseThrow(
                () -> new NotFoundException("مشتری با این شناسه یافت نشد"));
    }

}
