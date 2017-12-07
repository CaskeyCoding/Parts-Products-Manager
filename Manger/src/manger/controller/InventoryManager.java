/*InventoryManager.class
*  Manages all actions of InventoryManger.fxml - interfaces warehouse with end user GUI
*/
package manger.controller;

import manger.warehouse.Inventory;
import java.util.concurrent.ConcurrentHashMap;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import manger.Manager;
import manger.Manager;
import manger.warehouse.Product;
import manger.warehouse.Part;




/**
 *
 * @author Eric Caskey
 */
public class InventoryManager {
    public InventoryManager(){}
    
    @FXML
    private TableView<Product> productTable;
    @FXML
    private TableColumn<Product, Integer> productIdColumn;
    @FXML
    private TableColumn<Product, String> productNameColumn;
    @FXML
    private TableColumn<Product, Integer> productInvColumn;
    @FXML
    private TableColumn<Product, Double> productPriceColumn;

    @FXML
    private TableView<Part> partTable;
    @FXML
    private TableColumn<Part, Integer> partIdColumn;
    @FXML
    private TableColumn<Part, String> partNameColumn;
    @FXML
    private TableColumn<Part, Integer> partInvColumn;
    @FXML
    private TableColumn<Part, Double> partPriceColumn;
    @FXML
    private TextField searchParts;
    @FXML
    private TextField searchProducts;
    @FXML
    Button exitBtn;
    @FXML
    Button modPartBtn;
    @FXML
    Button addPartBtn;
    @FXML
    Button delPartBtn;
    @FXML
    Button searchPart;
    @FXML
    Button modProdBtn;
    @FXML
    Button addProdBtn;
    @FXML
    Button delProdBtn;
    @FXML
    Button searchProdBtn;
    
    private Stage Stage;
    private ObservableList<Part> partData = FXCollections.observableArrayList();
    private ObservableList<Product> productData = FXCollections.observableArrayList();
    private Manager manager;
    @FXML
    private void initialize() {
        partIdColumn.setCellValueFactory(
                cellData -> cellData.getValue().partIDProperty().asObject());
        partNameColumn.setCellValueFactory(
                cellData -> cellData.getValue().partNameProperty());
        partInvColumn.setCellValueFactory(
                cellData -> cellData.getValue().partInStockProperty().asObject());
        partPriceColumn.setCellValueFactory(
                cellData -> cellData.getValue().partPriceProperty().asObject());

        productIdColumn.setCellValueFactory(
                cellData -> cellData.getValue().productIDProperty().asObject());
        productNameColumn.setCellValueFactory(
                cellData -> cellData.getValue().productNameProperty());
        productInvColumn.setCellValueFactory(
                cellData -> cellData.getValue().productInStockProperty().asObject());
        productPriceColumn.setCellValueFactory(
                cellData -> cellData.getValue().productPriceProperty().asObject());

    }
    public void setMain(Manager main) {
        this.manager = main;
        ConcurrentHashMap<Integer,Part> partHashMap = Inventory.getAllParts();
        ConcurrentHashMap<Integer,Product> productHashMap = Inventory.getAllProducts();
        partData.clear();
        for(int key  : partHashMap.keySet() ){
            Part newPart = partHashMap.get(key);
            partData.add(newPart);
        }
        productData.clear();
        for(int key  : productHashMap.keySet() ){
            Product newProduct = productHashMap.get(key);
            productData.add(newProduct);
        }
        partTable.setItems(partData);
        productTable.setItems(productData);
    }
    public void setMainStage(Stage main) {
        this.Stage = main;
        ConcurrentHashMap<Integer,Part> partHashMap = Inventory.getAllParts();
        partData.clear();
        for(int key  : partHashMap.keySet() ){
            Part newPart = partHashMap.get(key);
            partData.add(newPart);
            System.out.println("We are updating the Table here...");
        }
        partTable.setItems(partData);
    }
    /* START PARTS */
    /* START ADD PART*/
    @FXML
    public void handleAddPart(){
       manager.showAddPart();
        boolean saveClicked = UpdatePartController.isSaveClicked();
        if(saveClicked == true){
            setMain(manager);
        }
    }    
    
    /*END ADD PART*/
    /*START UPDATE PART */
    @FXML
    private void handleUpdatePart() {
        Part selectedPart = partTable.getSelectionModel().getSelectedItem();
        System.out.println("We selected part  " + selectedPart.getPartID());
        if (selectedPart != null) {
            boolean saveClicked = manager.showUpdatePart(selectedPart);
            if (saveClicked){  
                setMain(this.manager);
            }
        } 
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(manager.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Part Selected");
            alert.setContentText("Please Select a Part to Update.");
            alert.showAndWait();
        }
    }    
    /*END UPDATE PART */    
    /*START SEARCH PART */
        @FXML
    private void handleSearchPart(){
         ConcurrentHashMap<Integer,Part> partHashMap = Inventory.getAllParts();
        partData.clear();
        for(int key  : partHashMap.keySet() ){
         
        String searchedPart = searchParts.getText().toLowerCase();
            Part newPart = partHashMap.get(key);
             if(newPart.getPartName().toLowerCase().contains(searchedPart)){
                 partData.add(newPart);
             }            
        }
        partTable.setItems(partData);
        productTable.setItems(productData);
    }    
    /*END SEARCH PART */
    /*START DELETE PART*/
    @FXML
    private void handleDeletePart() {
        int selectedIndex = partTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.initOwner(manager.getPrimaryStage());
            confirm.setTitle("Removing Part");
            confirm.setHeaderText("Please Confirm");
            confirm.setContentText("Please Confirm that you would like to remove this part.");
            confirm.showAndWait();
            if (confirm.getResult() == ButtonType.OK) {         
                partTable.getItems().remove(selectedIndex);            
                ConcurrentHashMap<Integer,Part> partHashMap = Inventory.getAllParts();
                for(int i : partHashMap.keySet()){
                    if(i==selectedIndex){
                        Part partDelete = partHashMap.get(i);
                        Inventory.deletePart(partDelete);
                    }
                }
            }        
            else{
                    confirm.close();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(manager.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Parts Selected");
            alert.setContentText("Please Select a Part to Modify");
            alert.showAndWait();
        }
    }
    /*END PARTS MANAGER*/
    
    /*START PRODUCTS MANAGER*/
    /*START UPDATE PRODUCT*/
    @FXML
    private void handleUpdateProduct() {
         Product selectedProd = productTable.getSelectionModel().getSelectedItem();
        if (selectedProd != null) {
            boolean saveClicked = manager.showUpdateProduct(selectedProd);
            if (saveClicked){  
                setMain(this.manager);
            }
        } 
        else {    
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(manager.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Product Selected");
            alert.setContentText("Please select a Product to Modify");
            alert.showAndWait();
        }
    }
    /*END UPDATE PRODUCT*/
    /*START ADD PRODUCT*/
     @FXML
    private void handleAddProduct() {         
            boolean saveClicked = manager.showAddProduct();
            if (saveClicked){  
                setMain(this.manager);
            }
    }
    /*END ADD PRODUCT*/
    /*START DELETE PRODUCT*/
    @FXML
    private void handleDeleteProduct() {
        int selectedIndex = productTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.initOwner(manager.getPrimaryStage());
            confirm.setTitle("Removing Product");
            confirm.setHeaderText("Please Confirm");
            confirm.setContentText("Please Confirm that you would like to remove this Product.");
            confirm.showAndWait();
            if (confirm.getResult() == ButtonType.OK) {    
            productTable.getItems().remove(selectedIndex);
            }
            else{
                confirm.close();
            }
        } 
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(manager.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please Select a Productto Delete");
            alert.showAndWait();
        }
    }
    /*END DELETE PRODUCT*/    
    /*START SEARCH PRODUCT*/
    @FXML
    private void handleSearchProduct(){        
        String searchedProduct = searchProducts.getText().toLowerCase();
        ConcurrentHashMap<Integer,Product> productHashMap = Inventory.getAllProducts();
        productData.clear();
        for(int key  : productHashMap.keySet() ){
            Product newProduct = productHashMap.get(key);
            
             if(newProduct.getProductName().toLowerCase().contains(searchedProduct)){
                productData.add(newProduct);
            }   
        }
        productTable.setItems(productData);
    }    
    /*END SEARCH PRODUCTS*/
    /*END PRODUCTS MANAGER*/
    
    /*START APPLICATION EXIT*/
    @FXML
    private void handleExit() {
        manager.stage.close();
    }    
    /*END APPLICATION EXIT*/
}
