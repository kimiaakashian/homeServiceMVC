package ir.home_service.model;

import ir.home_service.model.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private Customer customer;
    @ManyToOne
    private SubService subServices;
    private double customerSuggestedPrice;
    private String orderDescription;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    private String time;
    private String Date;
    private String address;
    private String comments;
    private int expertScore;
    @OneToOne
    private ExpertSuggestion expertSuggestions;
}
