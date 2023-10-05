package com.ttdat.application.dao;

import com.ttdat.application.model.OrderDetail;
import com.ttdat.application.utilities.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OrderDetailDao {
    public void save(List<OrderDetail> orderDetailList) {
        final String SQL = "INSERT INTO order_details(orderID, medicineID, quantity)" +
                "VALUES (?, ?, ?)";
        try (
                Connection connection = DBUtils.openConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL)
        ) {
            for (OrderDetail orderDetail : orderDetailList) {
                preparedStatement.setInt(1, orderDetail.getOrderID());
                preparedStatement.setInt(2, orderDetail.getMedicineID());
                preparedStatement.setInt(3, orderDetail.getQuantity());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
