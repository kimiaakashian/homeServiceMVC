package ir.home_service.service;

import ir.home_service.exception.InvalidSuggestionInput;
import ir.home_service.exception.NotFoundException;
import ir.home_service.model.*;
import ir.home_service.model.enums.ExpertSuggestionStatus;
import ir.home_service.model.enums.OrderStatus;
import ir.home_service.repository.ExpertSubServiceRepository;
import ir.home_service.repository.ExpertSuggestionRepository;
import ir.home_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpertSuggestionService {
    private final ExpertSuggestionRepository expertSuggestionRepository;
    private final OrderRepository orderRepository;
    private final ExpertSubServiceRepository expertSubServiceRepository;
    private final ExpertService expertService;


    public ExpertSuggestion findById(int suggestionId) {
        return expertSuggestionRepository.findById(suggestionId).orElseThrow(
                () -> new NotFoundException("پیشنهادی با این شناسه یافت نشد"));
    }

    public ExpertSuggestion sendSuggestionForOrder(int orderId, int expertId, ExpertSuggestion expertSuggestion) {
        Orders orders = orderRepository.findById(orderId).orElseThrow(
                () -> new NotFoundException("سفارشی با این شناسه یافت نشد"));
        Expert expert = expertService.findById(expertId);
        SubService subService = orders.getSubServices();
        List<ExpertSubService> expertSubServices = expertSubServiceRepository.findBySubServices(subService);


        boolean foundٍExpert = false;
        for (ExpertSubService ess : expertSubServices) {
            if (ess.getExperts().equals(expert)) {
                foundٍExpert = true;
                break;
            }
        }
        if (!foundٍExpert) {
            throw new InvalidSuggestionInput("متخصص به این زیرخدمت مرتبط نیست");
        }

        if (!(orders.getOrderStatus() == OrderStatus.منتظر_پیشنهاد_متخصصان
                || orders.getOrderStatus() == OrderStatus.منتظر_انتخاب_متخصص)) {
            throw new InvalidSuggestionInput("سفارش در وضعیت اخذ پیشنهاد متخصصان نیست");
        }

        if (expertSuggestion.getSuggestedPrice() < subService.getBasePrice()) {
            throw new InvalidSuggestionInput("قیمت پیشنهادی نباید از قیمت پایه ی زیرخدمت کمتر باشد");
        }

        expertSuggestion.setOrders(orders);
        expertSuggestion.setExperts(expert);
        expertSuggestion.setExpertSuggestionStatus(ExpertSuggestionStatus.ارسال_پیشنهاد);


        expertSuggestionRepository.save(expertSuggestion);


        orders.setOrderStatus(OrderStatus.منتظر_انتخاب_متخصص);
        orderRepository.save(orders);

        return expertSuggestion;
    }


}
