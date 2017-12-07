/*Manager.class
* This class controls what appears on the screen. It is referenced via buttons and runs classes to handle actions
 */
package manger;
import manger.controller.InventoryManager;
import manger.controller.UpdateProductController;
import manger.controller.UpdatePartController;
import manger.warehouse.Inventory;
import manger.warehouse.Product;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Eric Caskey
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import manger.*;
import manger.warehouse.Inhouse;
import manger.warehouse.Outsourced;
import manger.warehouse.Part;

public class Manager extends Application {
    
    public ConcurrentHashMap<Integer,Part> partHashMap = Inventory.partInvMap;
    public ConcurrentHashMap<Integer,Product> productHashMap = Inventory.productInvMap;
    public ObservableList<Part> getPartData(){return partData;}
    public ObservableList<Product> getProductData(){return productData;}

    public static Stage stage;
    private BorderPane main;


    /**
     * The data as an observable list of Parts and Products.
     */
    private Part b = new Inhouse ("motherboard", 600, 211.00,100,1,0,1000);
    private ArrayList<Part> c = new ArrayList<>(Arrays.asList(b));
    private ObservableList<Part> partData = FXCollections.observableArrayList();
    private ObservableList<Product> productData = FXCollections.observableArrayList(
            new Product(1, c, "Prod1", 3.50, 12, 1, 2));

    public Manager(){
        refreshManager();
    }
    public void refreshManager(){
        partHashMap = Inventory.getAllParts();
        for(int key  : partHashMap.keySet() ){
            Part newPart = partHashMap.get(key);
            partData.add(newPart);
            System.out.println("We are updating the Part Table here...");
        }
        productHashMap = Inventory.getAllProducts();
        for(int key  : productHashMap.keySet() ){
            Product newProduct = productHashMap.get(key);
            productData.add(newProduct);
            System.out.println("We are updating the Product Table here...");
        }
    }

    public void start(Stage stage2) {
        this.stage = stage2;
        this.stage.setTitle("Inventory Management");
        startScreen();
        viewAllTables();
    }
    public void startScreen() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Manager.class.getResource("MainScreen.fxml"));
            main = (BorderPane) loader.load();
            Scene scene = new Scene(main);
            stage.setScene(scene);
            stage.show();
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public  Stage getPrimaryStage() {
        return stage;
    }

    public static void main(String[] args) {
        launch(args);
    }
    public void viewAllTables() {
        
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Manager.class.getResource("InventoryManager.fxml"));
            AnchorPane inventoryOverview = (AnchorPane) loader.load();
            main.setCenter(inventoryOverview);
            InventoryManager controller = loader.getController();
            controller.setMain(this);

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*START HANDLE UPDATE PART*/
    public boolean showUpdatePart(Part part){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Manager.class.getResource("UpdatePart.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            
            System.out.println("Showing Update Screen");
            
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Modify Part");                       
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(stage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            
            UpdatePartController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPart(part);
            
            dialogStage.showAndWait();
            return controller.isSaveClicked();
        } 
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
      /*END HANDLE UPDATE PART*/
      /*START HANDLE ADD PART*/
    public boolean showAddPart(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Manager.class.getResource("UpdatePart.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            System.out.println("Showing Add Screen");
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Add Part");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(stage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            
            UpdatePartController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.initialPart();
            dialogStage.showAndWait();
            return controller.isSaveClicked();
        } 
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
        /*END HANDLE ADD PART*/
        /*START HANDLE UPDATE PRODUCT*/
    public boolean showUpdateProduct(Product prod){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Manager.class.getResource("UpdateProduct.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Modify Product");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(stage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            
            UpdateProductController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setProduct(prod);
            dialogStage.showAndWait();

            return controller.isSaveClicked();
        } 
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    /*END HANDLE UPDATE PRODUCT*/
    /*START HANDLE ADD PRODUCT*/
    @FXML
    public boolean showAddProduct(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Manager.class.getResource("UpdateProduct.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            
            dialogStage.setTitle("Add Product");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(stage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            
            UpdateProductController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.initialProduct();
            controller.setSearchParts();
            controller.setNewProductParts();
            dialogStage.showAndWait();
            
            return controller.isSaveClicked();
        } 
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /*END HANDLE ADD PRODUCT*/
    
}