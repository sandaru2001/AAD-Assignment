package lk.ijse.aadassignment1.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ItemDTO {

    private String code;
    private String name;
    private String quantity;
    private String price;
}
