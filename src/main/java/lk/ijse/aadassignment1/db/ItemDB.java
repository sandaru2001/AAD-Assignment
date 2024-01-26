package lk.ijse.aadassignment1.db;

import lk.ijse.aadassignment1.dto.ItemDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDB {
    public boolean saveItem(ItemDTO itemDTO, Connection connection) {
        String saveItemQuery = "INSERT INTO item(itemCode, itemName, itemQty, itemPrice) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(saveItemQuery);
            preparedStatement.setString(1, itemDTO.getCode());
            preparedStatement.setString(2, itemDTO.getName());
            preparedStatement.setInt(3, Integer.parseInt(itemDTO.getQuantity()));
            preparedStatement.setDouble(4, Double.parseDouble(itemDTO.getPrice()));

            return preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<ItemDTO> getAllItem(Connection connection){
        String getAllItemQuery = "SELECT * FROM item";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getAllItemQuery);
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<ItemDTO> itemDTOS = new ArrayList<>();
            while (resultSet.next()){
                ItemDTO itemDTO = new ItemDTO(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4)
                );
                itemDTOS.add(itemDTO);
            }
            return itemDTOS;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean updateItem(ItemDTO itemDTO, Connection connection){
        try {
            var ps = connection.prepareStatement("UPDATE item SET itemName = ?, itemQty = ?, itemPrice = ? WHERE itemCode = ?");
            ps.setString(1, itemDTO.getName());
            ps.setInt(2, Integer.parseInt(itemDTO.getQuantity()));
            ps.setDouble(3, Double.parseDouble(itemDTO.getPrice()));
            ps.setString(4, itemDTO.getCode());

            if (ps.executeUpdate() != 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deleteItem(String code, Connection connection){
        try {
            var ps = connection.prepareStatement("DELETE FROM item WHERE itemCode = ?");
            ps.setString(1, code);

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
