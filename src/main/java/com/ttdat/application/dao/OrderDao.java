package com.ttdat.application.dao;

import com.ttdat.application.model.Order;
import com.ttdat.application.utilities.DBUtils;

import java.sql.*;
import java.time.LocalDate;
import java.util.TreeMap;

public class OrderDao {
    public Order findLatestOrder() {
        final String SQL = """
                 SELECT * FROM `order` WHERE orderID = (
                                    SELECT MAX(orderID) FROM `order`
                                    )
                """;
        int orderID = 0;
        try (
                Connection connection = DBUtils.openConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL)
        ) {
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                return new Order(result.getInt("orderID"), result.getString("orderNumber"),
                        result.getTimestamp("createdDate").toLocalDateTime(), result.getFloat("totalValue"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void save(Order order) {
        final String SQL = "INSERT INTO `order`(orderNumber,createdDate, totalValue) VALUES (?, ?, ?)";
        try (
                Connection connection = DBUtils.openConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL)
        ) {
            preparedStatement.setString(1, order.getOrderNumber());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(order.getCreatedDate()));
            preparedStatement.setFloat(3, order.getTotalValue());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public double calculateTotalIncome() {
        final String SQL = "SELECT SUM(totalValue) AS totalIncome FROM `order`";
        double totalIncome = 0;
        try (
                Connection connection = DBUtils.openConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL)
        ) {
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                totalIncome = result.getDouble("totalIncome");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return totalIncome;
    }

    public int countTotalOders() {
        final String SQL = "SELECT COUNT(orderID) AS totalOrders FROM `order`";
        int totalOrders = 0;
        try (
                Connection connection = DBUtils.openConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL)
        ) {
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                totalOrders = result.getInt("totalOrders");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return totalOrders;
    }

    public TreeMap<LocalDate, Float> getCurrentWeekIncomeReport(LocalDate startDate, LocalDate endDate) {
        final String SQL = """
                SELECT DATE(createdDate) date, ROUND(SUM(totalValue), 2) totalIncome
                FROM `order`
                WHERE DATE(createdDate) BETWEEN ? AND ?
                GROUP BY DATE(createdDate)""";
        TreeMap<LocalDate, Float> currentWeekIncomeReport = new TreeMap<>();
        try (
                Connection connection = DBUtils.openConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL)
        ) {
            preparedStatement.setDate(1, java.sql.Date.valueOf(startDate));
            preparedStatement.setDate(2, java.sql.Date.valueOf(endDate));
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                currentWeekIncomeReport.put(result.getDate("date").toLocalDate(), result.getFloat("totalIncome"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return currentWeekIncomeReport;
    }
}
