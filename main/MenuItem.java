package main;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class MenuItem {
    String name;
    Double price;
    int quantity = 0;

    public MenuItem(String name, Double price) {
        this.name = name;
        this.price = price;
    }

    public HBox menuItemHBox() {
        // Create HBox for quantity and buttons
        HBox quantity = createQuanityHBox();

        // Create labels for name and price
        Label name = new Label(this.name);
        Label price = new Label(String.format("%.2f", this.price));

        HBox leftHBox = new HBox(10);
        HBox rightHBox = new HBox(10);
        HBox itemHBox = new HBox(30);

        // Add quanity and name to leftHBox and position left
        leftHBox.getChildren().addAll(quantity, name);
        leftHBox.setAlignment(Pos.CENTER_LEFT);
        leftHBox.setMargin(quantity, new Insets(0, 0, 0, 10));

        // Add price to rightHBox and position right
        rightHBox.getChildren().addAll(price);
        rightHBox.setAlignment(Pos.CENTER_RIGHT);

        // Add leftHBox and rightHBox to itemHBox
        itemHBox.getChildren().addAll(leftHBox, rightHBox);

        // Position price all the way to the right
        itemHBox.setHgrow(rightHBox, Priority.ALWAYS);

        return itemHBox;
    }

    private HBox createQuanityHBox() {
        // Create label to show quantity
        Label quantity = new Label(String.valueOf(this.quantity));

        // Create add button
        Button addBtn = new Button();
        addBtn.setText("+");
        addBtn.setMinWidth(30);
        addBtn.setOnAction((event) -> {
            this.quantity++;
            quantity.setText(String.valueOf(this.quantity));
        });

        // Create remove button
        Button removeBtn = new Button();
        removeBtn.setText("-");
        removeBtn.setMinWidth(30);
        removeBtn.setOnAction((event) -> {
            // Don't allow negative quantities
            if (this.quantity > 0) {
                this.quantity--;
            }
            quantity.setText(String.valueOf(this.quantity));
        });

        // Create VBox to hold buttons
        VBox buttonVBox = new VBox(addBtn, removeBtn);

        // Create HBox to hold label and buttons
        HBox quantityHBox = new HBox(10);
        quantityHBox.getChildren().addAll(quantity, buttonVBox);
        quantityHBox.setAlignment(Pos.CENTER_LEFT);

        return quantityHBox;
    }
}