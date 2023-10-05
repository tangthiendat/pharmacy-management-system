package com.ttdat.application.dao;

import com.ttdat.application.model.Medicine;
import com.ttdat.application.utilities.DBUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MedicineDao {

    public ObservableList<Medicine> findAll() {
        final String SQL = "SELECT * FROM medicine";
        ObservableList<Medicine> medicineList = FXCollections.observableArrayList();
        try (
                Connection connection = DBUtils.openConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            Medicine medicine;
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                medicine = new Medicine(result.getInt("medicineID"), result.getString("brand"),
                        result.getString("productName"), result.getString("type"),
                        result.getFloat("price"), result.getString("status"));
                medicineList.add(medicine);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return medicineList;
    }


    public void save(Medicine newMedicine) {
        final String SQL = "INSERT INTO medicine(brand, productName, type, price, status) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (
                Connection connection = DBUtils.openConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setString(1, newMedicine.getBrand());
            preparedStatement.setString(2, newMedicine.getProductName());
            preparedStatement.setString(3, newMedicine.getType());
            preparedStatement.setDouble(4, newMedicine.getPrice());
            preparedStatement.setString(5, newMedicine.getStatus());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Medicine selectedMedicine) {
        final String SQL = "UPDATE medicine SET brand = ?, productName = ?, type = ?, price = ?, status = ? WHERE medicineID = ?";
        try (
                Connection connection = DBUtils.openConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setString(1, selectedMedicine.getBrand());
            preparedStatement.setString(2, selectedMedicine.getProductName());
            preparedStatement.setString(3, selectedMedicine.getType());
            preparedStatement.setDouble(4, selectedMedicine.getPrice());
            preparedStatement.setString(5, selectedMedicine.getStatus());
            preparedStatement.setInt(6, selectedMedicine.getMedicineID());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(Medicine selectedMedicine) {
        final String SQL = "DELETE FROM medicine WHERE medicineID = ?";
        try (
                Connection connection = DBUtils.openConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setInt(1, selectedMedicine.getMedicineID());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ObservableList<String> findAvailableTypes() {
        final String SQL;
        SQL = "SELECT DISTINCT type FROM medicine WHERE status = 'Available'";
        ObservableList<String> typeList = FXCollections.observableArrayList();
        try (
                Connection connection = DBUtils.openConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                typeList.add(result.getString("type"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return typeList;
    }

    public ObservableList<String> findProductByTypeAndBrand(String type, String brand) {
        final String SQL = "SELECT productName FROM medicine WHERE status = 'Available' AND" +
                " type = ? AND brand = ?";
        ObservableList<String> productNameList = FXCollections.observableArrayList();
        try (
                Connection connection = DBUtils.openConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setString(1, type);
            preparedStatement.setString(2, brand);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                productNameList.add(result.getString("productName"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return productNameList;
    }

    public ObservableList<String> findBrandByType(String type) {
        final String SQL = "SELECT DISTINCT brand FROM medicine WHERE status = 'Available' AND type = ?";
        ObservableList<String> brandList = FXCollections.observableArrayList();
        try (
                Connection connection = DBUtils.openConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setString(1, type);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                brandList.add(result.getString("brand"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return brandList;
    }

    public int findIDByProductName(String productName) {
        final String SQL = "SELECT medicineID FROM medicine WHERE productName = ?";

        try (
                Connection connection = DBUtils.openConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setString(1, productName);
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                return result.getInt("medicineID");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    public Medicine findByProductName(String productName) {
        final String SQL = "SELECT * FROM medicine WHERE productName = ?";
        try (
                Connection connection = DBUtils.openConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setString(1, productName);
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                return new Medicine(result.getInt("medicineID"), result.getString("brand"),
                        result.getString("productName"), result.getString("type"),
                        result.getFloat("price"), result.getString("status"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


    public int countAvailableMedicines() {
        final String SQL = "SELECT COUNT(medicineID) AS availableMedicines FROM medicine WHERE status = 'Available'";
        int availableMedicines = 0;
        try (
                Connection connection = DBUtils.openConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                availableMedicines = result.getInt("availableMedicines");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return availableMedicines;
    }
}
