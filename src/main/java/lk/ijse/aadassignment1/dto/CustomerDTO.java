package lk.ijse.aadassignment1.dto;

import lombok.*;

import java.io.Serializable;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CustomerDTO {

    private String id;
    private String name ;
    private String address;
    private String contact;
}
