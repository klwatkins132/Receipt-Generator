package main;

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

public class Menu {
    // Create a dynamic ArrayList to store all MenuItems in
    public ArrayList<MenuItem> items = new ArrayList<MenuItem>();

    public VBox createMenu() {
        // START APPETIZERS
        // Create Label
        Label appLabel = new Label("Appetizers");

        // Declare ArrayList of MenuItem and add MenuItems to it
        ArrayList<MenuItem> apps = new ArrayList<MenuItem>();
        apps.add(new MenuItem("Avocado Hummus", 9.50));
        apps.add(new MenuItem("Vegan Cheese Plate", 10.00));
        apps.add(new MenuItem("Artichoke Dip", 9.00));
        apps.add(new MenuItem("Truffle Fries", 6.50));
        apps.add(new MenuItem("Stuffed Mushrooms", 12.00));

        ScrollPane appScrollPane = createMenuSection(apps);

        // START ENTREES
        Label entreeLabel = new Label("Entrees");

        // Declare ArrayList of MenuItem and add MenuItems to it
        ArrayList<MenuItem> entrees = new ArrayList<MenuItem>();
        entrees.add(new MenuItem("Eggplant Lasagna", 14.00));
        entrees.add(new MenuItem("Veggie Burger", 12.50));
        entrees.add(new MenuItem("Veggie Enchiladas", 13.00));
        entrees.add(new MenuItem("Mushroom Wellington", 18.00));
        entrees.add(new MenuItem("Tofu Tikka Masala", 16.50));

        ScrollPane entreeScrollPane = createMenuSection(entrees);

        // START DESSERTS
        Label dessertLabel = new Label("Desserts");

        // Declare ArrayList of MenuItem and add MenuItems to it
        ArrayList<MenuItem> desserts = new ArrayList<MenuItem>();
        desserts.add(new MenuItem("Vegan Cheesecake", 8.00));
        desserts.add(new MenuItem("Tart Cherry & Mint Sorbet", 7.50));
        desserts.add(new MenuItem("Vegan Pumpkin Pie", 8.00));
        desserts.add(new MenuItem("Dark Chocolate Cake Slice", 8.00));

        ScrollPane dessertScrollPane = createMenuSection(desserts);

        // START DRINKS
        Label drinkLabel = new Label("Drinks");

        // Declare ArrayList of MenuItem and add MenuItems to it
        ArrayList<MenuItem> drinks = new ArrayList<MenuItem>();
        drinks.add(new MenuItem("Mint Mojito", 8.00));
        drinks.add(new MenuItem("Peach Kombucha", 5.50));
        drinks.add(new MenuItem("Matcha Tea", 4.00));
        drinks.add(new MenuItem("Yerba Mate", 4.00));

        ScrollPane drinkScrollPane = createMenuSection(drinks);

        VBox menuVBox = new VBox(5);
        menuVBox.getChildren().addAll(appLabel, appScrollPane, entreeLabel, entreeScrollPane, dessertLabel, dessertScrollPane, drinkLabel, drinkScrollPane);

        return menuVBox;
    }

    private ScrollPane createMenuSection(ArrayList<MenuItem> items) {
        ArrayList<HBox> menuItemHBoxArray = new ArrayList<HBox>();
        // Loop over items
        for (int i = 0; i < items.size(); i++) {
            // Add each item to this.items
            this.items.add(items.get(i));

            // Create an instance of menuItemHBox for each item
            HBox menuItemHBox = items.get(i).menuItemHBox();

            // Add each menuItemHBox to an ArrayList of HBox
            menuItemHBoxArray.add(menuItemHBox);
        }

        // Add all the menuItemHBox nodes to a VBox
        VBox itemVBox = new VBox(10);
        itemVBox.getChildren().addAll(menuItemHBoxArray);

        // Create ScrollPane
        ScrollPane menuSectionScrollPane = new ScrollPane();
        menuSectionScrollPane.setContent(itemVBox);
        menuSectionScrollPane.setPrefViewportHeight(150);

        return menuSectionScrollPane;
    }
}