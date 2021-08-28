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

public class Main extends Application {
    public HBox createTop(Order order) {
        // START EMPLOYEE
        VBox employeeVBox = order.createEmployee();

        // START DINE IN/PICK UP
        HBox orderTypeHBox = order.createOrderType();

        // START QUIT
        // Create Quit Button
        Button quitBtn = new Button();
        quitBtn.setText("Quit");
        quitBtn.setOnAction((event) -> {
            System.exit(0);
        });

        // Create Main HBox
        HBox hBox = new HBox(200);

        // Create Quit HBox
        HBox quitHBox = new HBox(quitBtn);
        // Position Quit HBox to top right
        quitHBox.setAlignment(Pos.TOP_RIGHT);
        // HBox.setHgrow(quitHBox, Priority.ALWAYS);

        // Add comboBox and quitHBox to hBox
        hBox.getChildren().addAll(employeeVBox, orderTypeHBox, quitHBox);
        hBox.setAlignment(Pos.CENTER);

        return hBox;
    }

    public VBox createMenu(Menu menu) {
        VBox menuVBox = menu.createMenu();
        menuVBox.setMinWidth(300);
        menuVBox.setPrefWidth(600);

        return menuVBox;
    }

    public VBox createReceipt(Receipt receipt) {
        VBox receiptVBox = receipt.createEmptyReceipt();
        receiptVBox.setMinWidth(300);
        receiptVBox.setPrefWidth(600);

        return receiptVBox;
    }

    @Override
    public void start(Stage stage) {

        // Create border pane
        BorderPane borderPane = new BorderPane();

        // Create Order
        Order order = new Order();
        HBox topHBox = createTop(order);

        // Set topHBox to BorderPane Top
        borderPane.setTop(topHBox);

        // Create Menu
        Menu menu = new Menu();
        VBox menuVBox = createMenu(menu);

        // Create Receipt
        Receipt receipt = new Receipt();
        VBox receiptVBox = createReceipt(receipt);

        // Create center HBox and add menu and receipt to it
        HBox centerHBox = new HBox(50);
        centerHBox.getChildren().addAll(menuVBox, receiptVBox);
        centerHBox.setAlignment(Pos.CENTER);
        centerHBox.setPadding(new Insets(20, 0, 0, 0));

        borderPane.setCenter(centerHBox);

        // Create Submit Order Button
        Button submitBtn = new Button("Submit Order");
        submitBtn.setOnAction((event) -> {
            receipt.updateReceipt(menu, order);
        });

        // Create Reser Order Button
        Button resetBtn = new Button("Reset Order");
        resetBtn.setOnAction((event) -> {
            // Clear out receipt
            receipt.resetReceipt();

            // Reset order number
            order.resetOrder();
        });

        HBox btmHBox = new HBox(50);
        btmHBox.getChildren().addAll(submitBtn, resetBtn);
        btmHBox.setAlignment(Pos.CENTER);
        btmHBox.setPadding(new Insets(20, 0, 20, 0));

        borderPane.setBottom(btmHBox);

        // Create root pane
        StackPane rootPane = new StackPane();

        // Add borderPane to rootPane
        rootPane.getChildren().addAll(borderPane);
        rootPane.setPadding(new Insets(20, 20, 20, 20));
        // rootPane.setStyle("-fx-background-color: #666");

        // Add rootPane to scene
        Scene scene = new Scene(rootPane, 1600, 900);
        scene.setFill(Color.GREY);
        stage.setTitle("Sunny Spot");
        scene.getStylesheets().add("Style.css");
        stage.setScene(scene);
        stage.show();
    }
}