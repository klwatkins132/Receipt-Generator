package main;

// Import all of the required classes needed
import javafx.application.Application;
import javafx.beans.value.*;
import javafx.event.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.collections.*;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.geometry.Pos;

import java.util.*;

public class Order {
    private String customerFName = "";
    private String customerLName = "";
    private String customerPhoneNum = "";
    private TextField fName = new TextField();
    private TextField lName = new TextField();
    private TextField phoneNum = new TextField();
    private String employee;
    private String orderNumber;
    private Label orderNumberLabel = new Label();
    private String orderType = "Dine In:";
    private String tableNumber = "1";

    public VBox createEmployee() {
        // Create Employee ID Label
        Label employeeID = new Label("Employee ID:");

        // Create Employee Name Label
        Label employeeNameLabel = new Label("Employee Name: ");
        // Create Employee Name ComboBox
        String[] employeeList = { "Bob", "Linda", "Tina", "Gene", "Louise" };
        ComboBox<String> employeeNameComboBox = new ComboBox<>(FXCollections.observableArrayList(employeeList));

        // Add Employee Name Label and Employee Name ComboBox to an HBox
        HBox employeeName = new HBox(employeeNameLabel, employeeNameComboBox);

        // Generate random order number
        createOrderNumber();

        // Set the Employee ID Label to the index of the selected item
        employeeNameComboBox.setOnAction((event) -> {
            int selectedIndex = employeeNameComboBox.getSelectionModel().getSelectedIndex();
            this.employee = employeeNameComboBox.getSelectionModel().getSelectedItem();
            employeeID.setText(String.format("Employee ID: %d", selectedIndex + 1));
        });

        // Create VBox to group employee data
        VBox employeeVBox = new VBox(employeeID, employeeName, orderNumberLabel);

        return employeeVBox;
    }

    public HBox createOrderType() {
        // Create ToggleGroup
        final ToggleGroup group = new ToggleGroup();

        // Create Dine In RadioButton
        RadioButton rb1 = new RadioButton("Dine In:");
        rb1.setToggleGroup(group);
        rb1.setSelected(true);

        // Create Pick Up RadioButton
        RadioButton rb2 = new RadioButton("Pick Up:");
        rb2.setToggleGroup(group);

        // Assign 'this' to a variable to make use of in ObservableValue
        Order thisOrder = this;

        // Get current ToggleGroup value
        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
            public void changed(ObservableValue<? extends Toggle> ov,
                Toggle oldToggle, Toggle newToggle) {
                    RadioButton rb = (RadioButton)group.getSelectedToggle();
    
                    if (rb != null) {
                        String str = rb.getText();
                        thisOrder.orderType = str;
                    }              
                }
        });

        // Create Table Number Label
        Label tableNumber = new Label("Table Number: ");
        // Create Table Number ComboBox
        String[] tableList = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" };
        ComboBox<String> tableComboBox = new ComboBox<>(FXCollections.observableArrayList(tableList));

        // Set first table as default
        tableComboBox.getSelectionModel().selectFirst();

        // Store the selected table number
        tableComboBox.setOnAction((event) -> {
            this.tableNumber = tableComboBox.getSelectionModel().getSelectedItem();
        });

        // Add Table Number Label and Table Number ComboBox to an HBox
        HBox tableHBox = new HBox(tableNumber, tableComboBox);

        // Create HBox for Dine In options
        HBox dineInHBox = new HBox(5);
        dineInHBox.getChildren().addAll(rb1, tableHBox);

        // Create Customer Labels
        Label fNameLabel = new Label("Customer First Name: ");
        Label lNameLabel = new Label("Customer Last Name: ");
        Label phoneNumLabel = new Label("Customer Phone Num: ");

        // Assign TextField values to instance variables
        this.fName.textProperty().addListener((observable, oldText, newText) -> {
            this.customerFName = newText;
        });
        this.lName.textProperty().addListener((observable, oldText, newText) -> {
            this.customerLName = newText;
        });
        this.phoneNum.textProperty().addListener((observable, oldText, newText) -> {
            this.customerPhoneNum = newText;
        });

        // Create HBox for each Customer Input
        HBox fNameHBox = new HBox(fNameLabel, this.fName);
        fNameHBox.setAlignment(Pos.CENTER_RIGHT);

        HBox lNameHBox = new HBox(lNameLabel, this.lName);
        lNameHBox.setAlignment(Pos.CENTER_RIGHT);
        
        HBox phoneNumHBox = new HBox(phoneNumLabel, this.phoneNum);
        phoneNumHBox.setAlignment(Pos.CENTER_RIGHT);

        // Create VBox for Customer Labels/Inputs
        VBox customerVBox = new VBox(fNameHBox, lNameHBox, phoneNumHBox);

        // Create HBox for Pick Up options
        HBox pickUpHBox = new HBox(5);
        pickUpHBox.getChildren().addAll(rb2, customerVBox);

        HBox orderTypeHBox = new HBox(50);
        orderTypeHBox.getChildren().addAll(dineInHBox, pickUpHBox);

        return orderTypeHBox;
    }

    public void createOrderNumber() {
        // Generate random number between 1 and 1000
        Random rand = new Random();
        // Convert Double to String
        this.orderNumber = String.format("%d", rand.nextInt(1000) + 1);
        // Set this.orderLabel text
        String orderNumber = "Order Number: " + this.orderNumber;
        this.orderNumberLabel.setText(orderNumber);
        return;
    }

    public void resetOrder() {
        // Generate new order number
        createOrderNumber();

        // Clear out TextFields
        this.fName.setText("");
        this.lName.setText("");
        this.phoneNum.setText("");

        return;
    }

    public String getCustomerName() {
        return customerFName + " " + customerLName;
    }

    public String getCustomerPhone() {
        return customerPhoneNum;
    }

    public String getEmployee() {
        return employee;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public String getOrderType() {
        return orderType;
    }

    public String getTableNumber() {
        return tableNumber;
    }
}
