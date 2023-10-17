package com.ttdat.application.controllers;

import com.ttdat.application.dao.MedicineDao;
import com.ttdat.application.dao.OrderDao;
import com.ttdat.application.dao.OrderDetailDao;
import com.ttdat.application.model.Medicine;
import com.ttdat.application.model.Order;
import com.ttdat.application.model.OrderDetail;
import com.ttdat.application.model.PaymentReportDetail;
import com.ttdat.application.utilities.DateUtils;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

import java.net.URL;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class DashboardController implements Initializable {

    @FXML
    private TextField addMedicines_brand;

    @FXML
    private TableColumn<Medicine, String> addMedicines_col_brand;

    @FXML
    private TableColumn<Medicine, String> addMedicines_col_medicineID;

    @FXML
    private TableColumn<Medicine, String> addMedicines_col_price;

    @FXML
    private TableColumn<Medicine, String> addMedicines_col_productName;

    @FXML
    private TableColumn<Medicine, String> addMedicines_col_status;

    @FXML
    private TableColumn<Medicine, String> addMedicines_col_type;

    @FXML
    private AnchorPane addMedicines_form;

    @FXML
    private TextField addMedicines_price;

    @FXML
    private TextField addMedicines_productName;

    @FXML
    private TextField addMedicines_search;

    @FXML
    private ComboBox<String> addMedicines_status;

    @FXML
    private TableView<Medicine> addMedicines_tableView;

    @FXML
    private ComboBox<String> addMedicines_type;

    @FXML
    private Button btnAddMedicines;

    @FXML
    private Button btnDashboard;

    @FXML
    private Button btnPurchaseMedicines;

    @FXML
    private Label dashboard_availableMedicines;

    @FXML
    private LineChart<String, Number> dashboard_chart;

    @FXML
    private AnchorPane dashboard_form;

    @FXML
    private Label dashboard_totalIncome;

    @FXML
    private Label dashboard_totalOrders;

    @FXML
    private TextField purchase_cash;

    @FXML
    private Label purchase_balance;

    @FXML
    private ComboBox<String> purchase_brand;


    @FXML
    private TableColumn<PaymentReportDetail, String> purchase_col_price;

    @FXML
    private TableColumn<PaymentReportDetail, String> purchase_col_productName;

    @FXML
    private TableColumn<PaymentReportDetail, String> purchase_col_quantity;

    @FXML
    private TableColumn<PaymentReportDetail, String> purchase_col_subtotal;

    @FXML
    private AnchorPane purchase_form;

    @FXML
    private ComboBox<String> purchase_productName;

    @FXML
    private Label purchase_total;

    @FXML
    private ComboBox<String> purchase_type;

    @FXML
    private Spinner<Integer> purchase_quantity;

    @FXML
    private TableView<PaymentReportDetail> purchase_tableView;

    @FXML
    private TextField purchase_price;

    private final MedicineDao medicineDao = new MedicineDao();
    private final OrderDao orderDao = new OrderDao();
    private final OrderDetailDao orderDetailDao = new OrderDetailDao();

    //=====NAVIGATION BAR=====

    public void switchForm(ActionEvent event) {
        if (event.getSource() == btnDashboard) {
            dashboard_form.setVisible(true);
            addMedicines_form.setVisible(false);
            purchase_form.setVisible(false);

            btnDashboard.setStyle("-fx-background-color: #53e0e0");
            btnAddMedicines.setStyle("-fx-background-color: transparent");
            btnPurchaseMedicines.setStyle("-fx-background-color: transparent");

        } else if (event.getSource() == btnAddMedicines) {
            dashboard_form.setVisible(false);
            addMedicines_form.setVisible(true);
            purchase_form.setVisible(false);

            btnDashboard.setStyle("-fx-background-color: transparent");
            btnAddMedicines.setStyle("-fx-background-color: #53e0e0");
            btnPurchaseMedicines.setStyle("-fx-background-color: transparent");
        } else if (event.getSource() == btnPurchaseMedicines) {
            dashboard_form.setVisible(false);
            addMedicines_form.setVisible(false);
            purchase_form.setVisible(true);

            btnDashboard.setStyle("-fx-background-color: transparent");
            btnAddMedicines.setStyle("-fx-background-color: transparent");
            btnPurchaseMedicines.setStyle("-fx-background-color: #53e0e0");
        }
    }

    public void showErrorAlert(String contextText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Message");
        alert.setHeaderText(null);
        alert.setContentText(contextText);
        alert.showAndWait();
    }

    //=====ADD MEDICINES FORM=====

    //INPUT FORM
    public void addMedicine() {
        String brand = addMedicines_brand.getText();
        String productName = addMedicines_productName.getText();
        String type = String.valueOf(addMedicines_type.getSelectionModel().getSelectedItem());
        String price = addMedicines_price.getText();
        String status = String.valueOf(addMedicines_status.getSelectionModel().getSelectedItem());

        if (brand.isEmpty() || productName.isEmpty()
                || type.isEmpty() || price.isEmpty() || status.isEmpty()) {
            showErrorAlert("Please fill all blank fields");
        } else {
            Medicine newMedicine = new Medicine(brand, productName, type,
                    Float.parseFloat(price), status);
            medicineDao.save(newMedicine);
            showMedicineListData();
            setUpPurchaseFormData();
            clearAllInfo();
        }
    }

    public void deleteMedicine() {
        Medicine selectedMedicine = addMedicines_tableView.getSelectionModel().getSelectedItem();

        if (selectedMedicine.getBrand().isEmpty() || selectedMedicine.getProductName().isEmpty()
                || selectedMedicine.getType().isEmpty() || selectedMedicine.getStatus().isEmpty()) {
            showErrorAlert("Please fill all blank fields");
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to DELETE this medicine?");
            Optional<ButtonType> option = alert.showAndWait();
            if (Objects.requireNonNull(option.orElse(null)).equals(ButtonType.OK)) {
                medicineDao.delete(selectedMedicine);
                showMedicineListData();
                setUpPurchaseFormData();
                clearAllInfo();
            }
        }
    }

    public void updateMedicine() {
        Medicine selectedMedicine = addMedicines_tableView.getSelectionModel().getSelectedItem();
        int medicineID = selectedMedicine.getMedicineID();
        String brand = addMedicines_brand.getText();
        String productName = addMedicines_productName.getText();
        String type = addMedicines_type.getSelectionModel().getSelectedItem();
        float price = Float.parseFloat(addMedicines_price.getText());
        String status = addMedicines_status.getSelectionModel().getSelectedItem();
        if (brand.isEmpty() || productName.isEmpty() || type.isEmpty() || status.isEmpty()) {
            showErrorAlert("Please fill all blank fields");
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to UPDATE this medicine?");
            Optional<ButtonType> option = alert.showAndWait();
            if (Objects.requireNonNull(option.orElse(null)).equals(ButtonType.OK)) {
                Medicine updatedMedicine = new Medicine(medicineID, brand, productName, type, price, status);
                medicineDao.update(updatedMedicine);
                showMedicineListData();
                setUpPurchaseFormData();
                clearAllInfo();
            }
        }
    }

    public void clearAllInfo() {
        addMedicines_brand.setText("");
        addMedicines_productName.setText("");
        addMedicines_type.valueProperty().set(null);
        addMedicines_price.setText("");
        addMedicines_status.valueProperty().set(null);
    }

    //TABLE

    public void setTypeData() {
        String[] typeList = {"Hydrocodone", "Antibiotics", "Metformin", "Losartan", "Albuterol"};
        addMedicines_type.setItems(FXCollections.observableArrayList(typeList));
    }

    public void setStatusData() {
        String[] statusList = {"Available", "Not available"};
        addMedicines_status.setItems(FXCollections.observableArrayList(statusList));
    }

    private ObservableList<Medicine> medicineList;

    public void showMedicineListData() {
        medicineList = medicineDao.findAll();

        addMedicines_col_medicineID.setCellValueFactory(new PropertyValueFactory<>("medicineID"));
        addMedicines_col_brand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        addMedicines_col_productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        addMedicines_col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        addMedicines_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        addMedicines_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));

        addMedicines_tableView.setItems(medicineList);
        searchAndShowResults();
    }

    //Dynamic filter when user type in search bar
    public void searchAndShowResults() {
        FilteredList<Medicine> filteredData = new FilteredList<>(medicineList, e -> true);
        addMedicines_search.setOnKeyTyped(e -> {
            addMedicines_search.textProperty().addListener((observable, oldValue, newValue) -> filteredData.setPredicate(predicateMedicineData -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String searchKey = newValue.toLowerCase();
                if (String.valueOf(predicateMedicineData.getMedicineID()).contains(searchKey)) {
                    return true;
                } else if (predicateMedicineData.getBrand().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateMedicineData.getProductName().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateMedicineData.getType().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (String.valueOf(predicateMedicineData.getPrice()).contains(searchKey)) {
                    return true;
                } else return predicateMedicineData.getStatus().toLowerCase().contains(searchKey);
            }));
            SortedList<Medicine> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(addMedicines_tableView.comparatorProperty());
            addMedicines_tableView.setItems(sortedData);
        });

    }

    public void showSelectedMedicineInfo() {
        Medicine medicine = addMedicines_tableView.getSelectionModel().getSelectedItem();
        if (medicine == null) {
            return;
        }
        addMedicines_brand.setText(medicine.getBrand());
        addMedicines_productName.setText(medicine.getProductName());
        addMedicines_type.getSelectionModel().select(medicine.getType());
        addMedicines_price.setText(String.valueOf(medicine.getPrice()));
        addMedicines_status.getSelectionModel().select(medicine.getStatus());
    }

    public void setUpAddMedicinesFormData() {
        showMedicineListData();
        setTypeData();
        setStatusData();
    }

    //PURCHASE FORM

    public void showPurchaseType() {
        purchase_type.setItems(medicineDao.findAvailableTypes());
    }

    public void showPurchaseBrand() {
        //Display Brand corresponding to Type
        purchase_brand.itemsProperty().bind(Bindings.createObjectBinding(() -> {
            String type = purchase_type.getSelectionModel().getSelectedItem();
            if (type == null)
                return null;
            return medicineDao.findBrandByType(type);
        }, purchase_type.valueProperty()));
    }

    public void showPurchaseProduct() {
        //Display Product corresponding to Brand
        purchase_productName.itemsProperty().bind(Bindings.createObjectBinding(() -> {
            String type = purchase_type.getSelectionModel().getSelectedItem();
            String brand = purchase_brand.getSelectionModel().getSelectedItem();
            if (type == null || brand == null)
                return null;
            return medicineDao.findProductByTypeAndBrand(type, brand);
        }, purchase_type.valueProperty(), purchase_brand.valueProperty()));
    }

    public void showProductPrice() {
        //Display Price corresponding to Product
        String productName = purchase_productName.getSelectionModel().getSelectedItem();
        if (productName != null) {
            Medicine selectedMedicine = medicineDao.findByProductName(productName);
            purchase_price.setText(String.valueOf(selectedMedicine.getPrice()));
        } else {
            purchase_price.setText("");
        }

    }

    public void setPurchaseQuantity() {
        SpinnerValueFactory<Integer> spinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0);
        purchase_quantity.setValueFactory(spinnerValueFactory);
    }

    private final List<OrderDetail> orderDetailList = new ArrayList<>();
    List<PaymentReportDetail> paymentReportDetailList = new ArrayList<>();
    private float orderTotalValue = 0f;

    public boolean checkExistCartDetail(String productName) {
        for (PaymentReportDetail paymentReportDetail : paymentReportDetailList) {
            if (paymentReportDetail.getProduct().equals(productName))
                return true;
        }
        return false;
    }

    //Add detail to table when user click on "Add to cart" button
    public void addToCart() {
        //Get all entered info
        String productName = purchase_productName.getSelectionModel().getSelectedItem();
        Medicine selectedMedicine = medicineDao.findByProductName(productName);
        int quantity = purchase_quantity.getValue();
        if (quantity > 0) {
            if (checkExistCartDetail(productName)) {
                showErrorAlert("This product exists in the table. Please choose another product or modify the quantity in the table.");
            } else {
                //Add detail to detail list
                float subtotal = selectedMedicine.getPrice() * quantity;
                DecimalFormat decimalFormat = new DecimalFormat("0.00");
                orderTotalValue += subtotal;
                paymentReportDetailList.add(new PaymentReportDetail(productName, decimalFormat.format(selectedMedicine.getPrice()), String.valueOf(quantity),
                        decimalFormat.format(subtotal)));
                //Show detail to table
                showCartDetails();
                //Set the data that will be stored to database
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setMedicineID(selectedMedicine.getMedicineID());
                orderDetail.setQuantity(quantity);
                orderDetailList.add(orderDetail);
                setEditableQuantityColumn();
                deleteEnteredInfo();
                showTotalValue();
            }
        } else {
            showErrorAlert("Quantity must be greater than 0.");
        }

    }

    public void showCartDetails() {
        purchase_col_productName.setCellValueFactory(new PropertyValueFactory<>("product"));
        purchase_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        purchase_col_quantity.setCellValueFactory(new PropertyValueFactory<>("qty"));
        purchase_col_subtotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        purchase_tableView.setItems(FXCollections.observableArrayList(paymentReportDetailList));
    }

    public void setEditableQuantityColumn() {
        purchase_col_quantity.setCellFactory(TextFieldTableCell.forTableColumn());
        purchase_col_quantity.setOnEditCommit(event -> {
            //Set new quantity to table cell
            String newQuantity = event.getNewValue();
            PaymentReportDetail row = event.getTableView().getItems().get(event.getTablePosition().getRow());
            row.setQty(newQuantity);
            //Set the new total in the list
            row.setTotal(String.valueOf(Integer.parseInt(newQuantity) * Float.parseFloat(row.getPrice())));
            //Set new total value of the order
            orderTotalValue = 0;
            for (PaymentReportDetail detail : paymentReportDetailList) {
                orderTotalValue += Integer.parseInt(detail.getQty()) * Float.parseFloat(detail.getPrice());
            }
            showTotalValue();
            purchase_tableView.refresh();
        });

    }

    public void deleteEnteredInfo() {
        purchase_type.valueProperty().set(null);
        purchase_brand.valueProperty().set(null);
        purchase_productName.valueProperty().set(null);
        purchase_quantity.getValueFactory().setValue(0);
    }

    public OrderDetail getOrderDetailByID(int medicineID) {
        for (OrderDetail orderDetail : orderDetailList) {
            if (orderDetail.getMedicineID() == medicineID)
                return orderDetail;
        }
        return null;
    }

    public void deleteOrderInfo() {
        PaymentReportDetail selectedDetail = purchase_tableView.getSelectionModel().getSelectedItem();
        int selectedProductID = medicineDao.findIDByProductName(selectedDetail.getProduct());
        orderDetailList.remove(getOrderDetailByID(selectedProductID));
        purchase_tableView.getItems().remove(selectedDetail);
        paymentReportDetailList.remove(selectedDetail);
    }

    public void saveOrder() {
        Order order = new Order(generateOrderNumber(), LocalDateTime.now(), orderTotalValue);
        orderDao.save(order);
        Order latestOrder = orderDao.findLatestOrder();
        for (OrderDetail orderDetail : orderDetailList) {
            orderDetail.setOrderID(latestOrder.getOrderID());
        }
        orderDetailDao.save(orderDetailList);
    }

    public void showTotalValue() {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        purchase_total.setText("$" + decimalFormat.format(orderTotalValue));
    }

    public void showBalance() {
        double amount = Double.parseDouble(purchase_cash.getText());
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        purchase_balance.setText("$" + decimalFormat.format(amount - orderTotalValue));
    }

    int everydayOrderCounter = 0;

    public String generateOrderNumber() {
        Order latestOrder = orderDao.findLatestOrder();
        if (latestOrder != null) {
            LocalDate latestOrderDate = latestOrder.getCreatedDate().toLocalDate();
            LocalDate today = LocalDate.now();
            if (today.isEqual(latestOrderDate)) {
                everydayOrderCounter = Integer.parseInt(latestOrder.getOrderNumber().substring(9));
            } else if (today.isAfter(latestOrderDate)) {
                everydayOrderCounter = 0;
            }
            everydayOrderCounter++;
        }
        return String.valueOf(LocalDate.now()).replace("-", "")
                + String.format("%04d", everydayOrderCounter);
    }

    public void printOrder() throws JRException {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        JasperReport paymentReport = JasperCompileManager.compileReport(getClass().getResourceAsStream("/print/invoice.jrxml"));
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("order_number", generateOrderNumber());
        parameters.put("date_created", Timestamp.valueOf(LocalDateTime.now()));
        parameters.put("total", "$" + decimalFormat.format(orderTotalValue));
        parameters.put("cash", "$" + decimalFormat.format(Float.parseFloat(purchase_cash.getText())));
        parameters.put("balance", "$" + decimalFormat.format(Float.parseFloat(purchase_cash.getText()) - orderTotalValue));
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(paymentReportDetailList);
        JasperPrint print = JasperFillManager.fillReport(paymentReport, parameters, dataSource);
        JasperViewer.viewReport(print, false);
    }

    public void exportOrder() throws JRException {
        printOrder();
        saveOrder();
        //Clear all details
        orderTotalValue = 0f;
        paymentReportDetailList.clear();
        purchase_tableView.getItems().clear();
        orderDetailList.clear();
        purchase_total.setText("$0.00");
        purchase_cash.setText("");
        purchase_balance.setText("$0.00");
        //Show new data in the chart
        initChart();

    }


    public void setUpPurchaseFormData() {
        showPurchaseType();
        showPurchaseBrand();
        showPurchaseProduct();
        setPurchaseQuantity();
    }

    //DASH BOARD
    public void displayAvailableMedicines() {
        dashboard_availableMedicines.setText(String.valueOf(medicineDao.countAvailableMedicines()));
    }

    public void displayTotalIncome() {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        double totalIncome = orderDao.calculateTotalIncome();
        dashboard_totalIncome.setText(String.valueOf(decimalFormat.format(totalIncome)));
    }

    public void displayTotalOrders() {
        dashboard_totalOrders.setText(String.valueOf(orderDao.countTotalOders()));
    }

    public void setDefaultNavbar() {
        btnDashboard.setStyle("-fx-background-color: #53e0e0");
    }

    TreeMap<LocalDate, Float> currentWeekIncomeReport = new TreeMap<>();
    List<LocalDate> currentWeekDayList;

    public void loadNewChartDataFromDB() {
        currentWeekDayList = DateUtils.getCurrentWeekDays();
        LocalDate firstWeekDay = currentWeekDayList.get(0);
        LocalDate lastWeekDay = currentWeekDayList.get(currentWeekDayList.size() - 1);
        currentWeekIncomeReport = orderDao.getCurrentWeekIncomeReport(firstWeekDay, lastWeekDay);
    }

    public void initChart() {
        loadNewChartDataFromDB();
        //Show all days in x-axis
        CategoryAxis xAxis = (CategoryAxis) dashboard_chart.getXAxis();
        ObservableList<String> categories = FXCollections.observableArrayList();
        for (LocalDate date : currentWeekDayList) {
            categories.add(String.valueOf(date));
        }
        xAxis.setCategories(categories);
        //Show data until the current day
        for (int i = 0; i <= currentWeekDayList.indexOf(LocalDate.now()); i++) {
            if (!currentWeekIncomeReport.containsKey(currentWeekDayList.get(i))) {
                //If there is no order on that day, the income will be 0
                currentWeekIncomeReport.put(currentWeekDayList.get(i), 0f);
            }
        }
        //The line chart has a series, and you want to update it
        if (!dashboard_chart.getData().isEmpty()) {
            dashboard_chart.getData().remove(0);
        }
        //Set data to the chart
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Sales");
        for (LocalDate date : currentWeekIncomeReport.keySet()) {
            series.getData().add(new XYChart.Data<>(date.toString(), currentWeekIncomeReport.get(date)));
        }
        dashboard_chart.getData().add(series);
        //Show value when user hover in line chart point
        for (XYChart.Data<String, Number> data : series.getData()) {
            Tooltip tooltip = new Tooltip(data.getYValue().toString());
            Tooltip.install(data.getNode(), tooltip);
        }

    }


    public void setUpDashboardData() {
        setDefaultNavbar();
        displayAvailableMedicines();
        displayTotalIncome();
        displayTotalOrders();
        initChart();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //DASHBOARD
        setUpDashboardData();

        //ADD MEDICINES FORM
        setUpAddMedicinesFormData();

        //PURCHASE MEDICINES FORM
        setUpPurchaseFormData();
    }
}
