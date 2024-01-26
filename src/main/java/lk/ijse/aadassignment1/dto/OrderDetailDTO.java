package lk.ijse.aadassignment1.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrderDetailDTO {
    private String orderId ;
    private String itemId;
    private double price;
    private int qty;
}
