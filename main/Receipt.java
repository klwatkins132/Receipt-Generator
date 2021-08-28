package main;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Text;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import java.text.SimpleDateFormat;
import java.util.*;

public class Receipt {
    private Label dateLabel;
    private Label orderInfoLabel;
    private Label orderNumLabel;
    private Label serverLabel;
    private VBox menuItemsVBox = new VBox(10);
    private Double taxRate = 0.029;

    public VBox createEmptyReceipt() {
        Label receiptLabel = new Label("Receipt");
        Label topLabel = new Label("SunnySide Resturant");
        topLabel.setPadding(new Insets(10, 0, 0, 0));
        
        // Get current date and format it
        Date now = new Date();
        SimpleDateFormat fmtDate = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
        this.dateLabel = new Label("Date: " + fmtDate.format(now));

        // Set order info labels
        this.orderInfoLabel = new Label("Table #:");
        this.orderNumLabel = new Label("Order #:");
        this.serverLabel = new Label("Server:");

        // Create VBox for top of receipt
        VBox receiptTop = new VBox(10);
        receiptTop.getChildren().addAll(topLabel, this.dateLabel, this.orderInfoLabel, this.orderNumLabel, this.serverLabel);
        receiptTop.setAlignment(Pos.CENTER);
        receiptTop.setPrefWidth(580);

        // Create container VBox
        VBox containerVBox = new VBox(20);
        containerVBox.getChildren().addAll(receiptTop, menuItemsVBox);

        // Create ScrollPane for entire receipt
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(containerVBox);
        scrollPane.setPrefViewportHeight(600);

        // Create VBox to add label to receipt side.
        VBox receiptVBox = new VBox(5);
        receiptVBox.getChildren().addAll(receiptLabel, scrollPane);
        
        return receiptVBox; 
    }

    public void updateReceipt(Menu menu, Order order) {
        // START TOP OF RECEIPT
        // Update dateLabel
        Date now = new Date();
        SimpleDateFormat fmtDate = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
        this.dateLabel.setText("Date: " + fmtDate.format(now));

        // Create warning alert
        Alert alert = new Alert(AlertType.WARNING);

        // Update orderNumLabel
        String orderNum = order.getOrderNumber();

        // Update serverLabel
        String server = order.getEmployee();
        // If no employee selected, show an alert and return
        if (server == null) {
            alert.setContentText("Please select an Employee.");
            alert.show();
            return;
        }

        // Update orderInfoLabel
        String orderType = order.getOrderType();
        // If the orderType is Dine In, set orderInfo to show selected Table
        if (orderType == "Dine In:") {
            String tableNum = order.getTableNumber();
            this.orderInfoLabel.setText("Table #: " + tableNum);
        } else {
            // If the orderType is Pick Up, set orderInfo to customer info
            String customerName = order.getCustomerName();
            String customerPhone = order.getCustomerPhone();

            // If name or number is null, show an alert and return
            if (customerName == "" || customerPhone == "") {
                alert.setContentText("Please enter all customer information.");
                alert.show();
                return;
            }

            // Set order info to show customer name and number
            this.orderInfoLabel.setText("Customer Name: " + customerName + "\n\nCustomer Phone #: " + customerPhone);
        }

        // Set text for order number and server
        this.orderNumLabel.setText("Order #: " + orderNum);
        this.serverLabel.setText("Server: " + server);

        // START MENU ITEMS
        Double subtotal = 0.00;

        ArrayList<HBox> itemHBoxArray = new ArrayList<HBox>();
        // Loop over menu items to create HBox for each and add to ArrayList of type HBox
        for (int i = 0; i < menu.items.size(); i++) {
            MenuItem item = menu.items.get(i);

            // Create HBox containing quantity, name, and price if the quantity is greater than 0
            if (item.quantity > 0) {
                HBox itemHBox = new HBox(40);
                Label quantity = new Label(String.format("%d", item.quantity));
                Label name = new Label(item.name);
                HBox leftHBox = new HBox(20);
                leftHBox.getChildren().addAll(quantity, name);

                Double itemPrice = item.price * item.quantity;
                subtotal += itemPrice;

                Label priceLabel = new Label(String.format("%.2f", itemPrice));
                HBox priceHBox = new HBox(priceLabel);
                priceHBox.setAlignment(Pos.CENTER_RIGHT);

                itemHBox.getChildren().addAll(leftHBox, priceHBox);
                itemHBox.setHgrow(priceHBox, Priority.ALWAYS);
                itemHBox.setPadding(new Insets(0, 20, 0, 20));

                itemHBoxArray.add(itemHBox);
            }
        }

        // Subtotal Section
        Label subtotalLabel = new Label("SUBTOTAL:");
        // Convert subtotal to string and add to HBox
        HBox subtotalPrice = new HBox(new Label(String.format("%.2f", subtotal)));
        // Align HBox right
        subtotalPrice.setAlignment(Pos.CENTER_RIGHT);
        // Create container HBox
        HBox subtotalHBox = new HBox(subtotalLabel, subtotalPrice);
        // Align price to right
        subtotalHBox.setHgrow(subtotalPrice, Priority.ALWAYS);
        subtotalHBox.setPadding(new Insets(20, 20, 0, 20));

        // Tax Section
        Label taxLabel = new Label("TAX (2.9%):");
        // Calculate Tax Amount
        Double taxAmount = subtotal * taxRate;
        // Convert tax to string and add to HBox
        HBox taxPrice = new HBox(new Label(String.format("%.2f", taxAmount)));
        // Align HBox right
        taxPrice.setAlignment(Pos.CENTER_RIGHT);
        // Create container HBox
        HBox taxHBox = new HBox(taxLabel, taxPrice);
         // Align price to right
        taxHBox.setHgrow(taxPrice, Priority.ALWAYS);
        taxHBox.setPadding(new Insets(0, 20, 0, 20));

        // Total Section
        Text totalLabel = new Text("TOTAL:");
        // Style total label to larger font and bold
        totalLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14pt");
        // Calculate total amount
        Double totalAmount = subtotal + taxAmount;
        Text totalPriceText = new Text(String.format("%.2f", totalAmount));
         // Style total price to larger font and bold
        totalPriceText.setStyle("-fx-font-weight: bold; -fx-font-size: 14pt");
        HBox totalPrice = new HBox(totalPriceText);
        // Align HBox to right
        totalPrice.setAlignment(Pos.CENTER_RIGHT);
        // Create container HBox
        HBox totalHBox = new HBox(totalLabel, totalPrice);
        // Align price to right
        totalHBox.setHgrow(totalPrice, Priority.ALWAYS);
        totalHBox.setPadding(new Insets(0, 20, 0, 20));

        // START TIP SECTION
        Label tipLabel = new Label("Tip Guide");
        tipLabel.setPadding(new Insets(30, 0, 0, 0));

        // Create tip helper labels
        Label tipFifteen = new Label("15% = $" + calculateTip(0.15, subtotal));
        Label tipEighteen = new Label("18% = $" + calculateTip(0.18, subtotal));
        Label tipTwenty = new Label("20% = $" + calculateTip(0.2, subtotal));

        // Create HBox to hold tip helpers
        HBox tipHBox = new HBox(100);
        tipHBox.getChildren().addAll(tipFifteen, tipEighteen, tipTwenty);
        tipHBox.setPadding(new Insets(10, 20, 20, 20));
        tipHBox.setAlignment(Pos.CENTER);

        // Create thank you, come again labels
        Label thankYou = new Label("Thank you very much!");
        thankYou.setPadding(new Insets(20, 0, 0, 0));
        Label comeBack = new Label("Come back again!");
        comeBack.setPadding(new Insets(20, 0, 10, 0));

        // Create VBox for tip section
        VBox tipVBox = new VBox(tipLabel, tipHBox, thankYou, comeBack);
        tipVBox.setAlignment(Pos.CENTER);

        // Clear VBox in case a user is resubmitting the same order
        this.menuItemsVBox.getChildren().clear();
        // Add items, totals, and tips to receipt
        this.menuItemsVBox.getChildren().addAll(itemHBoxArray);
        this.menuItemsVBox.getChildren().addAll(subtotalHBox, taxHBox, totalHBox, tipVBox);
        this.menuItemsVBox.setPadding(new Insets(20, 0, 0, 0));
    }

    // Reset receipt to default values
    public void resetReceipt() {
        this.orderInfoLabel.setText("Table #:");
        this.orderNumLabel.setText("Order #:");
        this.serverLabel.setText("Server:");
        this.menuItemsVBox.getChildren().clear();
    }

    // Calculate tip and format to String
    String calculateTip(Double percent, Double total) {
        Double tip = total * percent;
        return String.format("%.2f", tip);
    }
}