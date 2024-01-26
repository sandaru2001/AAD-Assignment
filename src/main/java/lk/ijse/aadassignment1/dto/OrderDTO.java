package lk.ijse.aadassignment1.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrderDTO {
    private String orderDate;
    private String orderId;
    private String customerId;
    private double total;
    private double discount;
    private double cash;
}
