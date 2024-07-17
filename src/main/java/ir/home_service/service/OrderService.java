package ir.home_service.service;

import ir.home_service.exception.InvalidInformationException;
import ir.home_service.exception.InvalidStatusException;
import ir.home_service.exception.InvalidSuggestionInput;
import ir.home_service.exception.NotFoundException;
import ir.home_service.helper.FormattedDateMatcher;
import ir.home_service.model.*;
import ir.home_service.model.enums.OrderStatus;
import ir.home_service.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class OrderService {
    private final OrderRepository orderRepository;
    private final ExpertSuggestionRepository expertSuggestionRepository;
    private final ExpertSuggestionService expertSuggestionService;
    private final CustomerService customerService;
    private final SubServiceService subServiceService;
    private final ExpertService expertService;
    private final ExpertRepository expertRepository;
    private final CustomerRepository customerRepository;


    public Orders findById(int orderId) {
        return orderRepository.findById(orderId).orElseThrow(
                () -> new NotFoundException("سفارش با این شناسه یافت نشد"));

    }


    public Orders sendOrderByCustomer
            (Orders orders, int customerId, int subServiceId) {
        if (!FormattedDateMatcher.checkGraterThanToday(orders.getDate()))
            throw new InvalidInformationException("تاریخ وارد شده صحیح نمی باشد");
        else {
            orders.setCustomer(customerService.findById(customerId));
            orders.setSubServices(subServiceService.findById(subServiceId));
            orders.setOrderStatus(OrderStatus.منتظر_پیشنهاد_متخصصان);

            return orderRepository.save(orders);
        }
    }

    public List<ExpertSuggestion> getSuggestionsForOrder(int orderId) {
        Orders orders = orderRepository.findById(orderId).orElseThrow(() -> new NotFoundException("سفارش با این شناسه موجود نیست"));
        Sort sort = Sort.by("experts.averageScore").descending().by("suggestedPrice").ascending();
        List<ExpertSuggestion> expertSuggestions = expertSuggestionRepository.findByOrders(orders, sort);
        //باید مرتب شده بنویسم سورت کنم
        return expertSuggestions;
    }

    public Orders acceptSuggestionsToOrder(int suggestionId) {
        ExpertSuggestion expertSuggestion = expertSuggestionService.findById(suggestionId);
        Orders orders = expertSuggestion.getOrders();

        orders.setExpertSuggestions(expertSuggestion);
        orders.setOrderStatus(OrderStatus.منتظر_آمدن_متخصص_به_محل_شما);
        return orderRepository.save(orders);

    }

    public Orders endOfWork(Integer orderId) {
        Orders orders = findById(orderId);
        if (orders.getOrderStatus().equals(OrderStatus.شروع_شده)) {
            orders.setOrderStatus(OrderStatus.انجام_شده);
            return orderRepository.save(orders);
        } else
            throw new InvalidStatusException("این سفارش در حالت شروع نشده است");
    }

    public Orders startWork(Integer orderId) {
        Orders orders = findById(orderId);
        if (orders.getOrderStatus().equals(OrderStatus.منتظر_آمدن_متخصص_به_محل_شما)) {
            orders.setOrderStatus(OrderStatus.شروع_شده);
            return orderRepository.save(orders);
        } else
            throw new InvalidStatusException("این سفارش در حالتی که شروع را بزنید نمیباشد");
    }


    public void paymentFromCredit(int orderId) {
        Orders orders = findById(orderId);
        Customer customer = customerService.findById(orders.getCustomer().getId());
        Expert expert = expertService.findById(orders.getExpertSuggestions().getExperts().getId());
        if (customer.getCustomerCredit() >= orders.getExpertSuggestions().getSuggestedPrice()) {
            customer.setCustomerCredit(customer.getCustomerCredit() - orders.getExpertSuggestions().getSuggestedPrice());
            expert.setExpertCredit(expert.getExpertCredit() + orders.getExpertSuggestions().getSuggestedPrice());
            orders.setOrderStatus(OrderStatus.پرداخت_شده);
            orderRepository.save(orders);
            expertRepository.save(expert);
            customerRepository.save(customer);
        } else
            throw new InvalidSuggestionInput("اعتبار کافی نیست");
    }

    public void onlinePayment(int orderId, String cardNumber, String date) {
        Orders orders = findById(orderId);
        Expert expert = expertService.findById(orders.getExpertSuggestions().getExperts().getId());
        Double orderPrice = orders.getExpertSuggestions().getSuggestedPrice();
        orders.setOrderStatus(OrderStatus.پرداخت_شده);
        orderRepository.save(orders);

        expert.setExpertCredit(expert.getExpertCredit() + (orderPrice * 0.7) );
        expertRepository.save(expert);
    }

    public Orders rateOrder(Orders orders){
        Orders updateOrder = findById(orders.getId());
        Optional.ofNullable(orders.getComments()).ifPresent(updateOrder::setComments);
        Optional.ofNullable(orders.getExpertScore()).ifPresent(updateOrder::setExpertScore);

        return orderRepository.save(updateOrder);

    }
}
