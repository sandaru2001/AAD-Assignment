package lk.ijse.aadassignment1.db;

import lk.ijse.aadassignment1.dto.ItemDTO;
import lk.ijse.aadassignment1.dto.OrderDTO;
import lk.ijse.aadassignment1.dto.OrderDetailDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailDB {
    public boolean saveOrderDetails(OrderDetailDTO orderDetailDTO, Connection connection) {
        String saveOrderDetailQuery = "INSERT INTO orderDetails(orderId, itemId, price, qty) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(saveOrderDetailQuery);
            preparedStatement.setString(1,orderDetailDTO.getOrderId());
            preparedStatement.setString(2,orderDetailDTO.getItemId());
            preparedStatement.setDouble(3,orderDetailDTO.getPrice());
            preparedStatement.setInt(4,orderDetailDTO.getQty());

            return preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<OrderDetailDTO> getAllOrderDetail(Connection connection){
        String getAllOrderDetailQuery = "SELECT * FROM orderDetails";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getAllOrderDetailQuery);
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<OrderDetailDTO> orderDetailDTOS = new ArrayList<>();
            while (resultSet.next()){
                OrderDetailDTO orderDetailDTO = new OrderDetailDTO(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getDouble(3),
                        resultSet.getInt(4)
                );
                orderDetailDTOS.add(orderDetailDTO);
            }
            return orderDetailDTOS;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean updateOrderDetail(OrderDetailDTO orderDetailDTO, Connection connection) {
        try {
            var ps = connection.prepareStatement("UPDATE orderDetails SET itemId = ?, price = ?, qty = ? WHERE orderId = ?");
            ps.setString(1,orderDetailDTO.getItemId());
            ps.setDouble(2,orderDetailDTO.getPrice());
            ps.setInt(3,orderDetailDTO.getQty());
            ps.setString(4,orderDetailDTO.getOrderId());

            if (ps.executeUpdate() != 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deleteOrderDetail(String orderId, Connection connection) {
        try {
            var ps = connection.prepareStatement("DELETE FROM orderDetails WHERE orderId = ?");
            ps.setString(1,orderId);

            if (ps.executeUpdate() != 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
