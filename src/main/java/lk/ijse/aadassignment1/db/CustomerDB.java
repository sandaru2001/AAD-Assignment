package lk.ijse.aadassignment1.db;

import lk.ijse.aadassignment1.dto.CustomerDTO;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDB {
    public boolean saveCustomer(CustomerDTO customerDTO, Connection connection) {
        String saveCustomerQuery = "INSERT INTO customer(custId, custName, custAddress, custContact) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(saveCustomerQuery);
            preparedStatement.setString(1,customerDTO.getId());
            preparedStatement.setString(2,customerDTO.getName());
            preparedStatement.setString(3,customerDTO.getAddress());
            preparedStatement.setString(4,customerDTO.getContact());

            return preparedStatement.executeUpdate()!=0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<CustomerDTO> getAllCustomer(Connection connection){
        String getAllCustomerQuery = "SELECT * FROM customer";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getAllCustomerQuery);
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<CustomerDTO> customerDTOS = new ArrayList<>();
            while (resultSet.next()){
                CustomerDTO customerDTO = new CustomerDTO(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4)
                );
                customerDTOS.add(customerDTO);
            }
            return customerDTOS;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
