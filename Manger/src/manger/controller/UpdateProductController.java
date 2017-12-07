/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manger.controller;
import manger.warehouse.Inventory;
import manger.warehouse.Part;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToggleButton;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import static manger.warehouse.Inventory.addPart;
import manger.Manager;
import manger.Manager;
import manger.warehouse.Inhouse;
import manger.warehouse.Outsourced;
import manger.warehouse.Product;
import static manger.warehouse.Part.partArray;

/**
 *
 * @author Eric Caskey
 */
public class UpdateProductController {
   
    @FXML
    private TableView<Part>  productPartTable;
    @FXML
    private  TableColumn<Part, Integer>  productPartIdColumn;
    @FXML
    private TableColumn<Part, String> productPartNameColumn;
    @FXML
    private TableColumn<Part, Integer> productPartInvColumn;
    @FXML
    private TableColumn<Part, Double> productPartPriceColumn;
    @FXML
    private TableView<Part> searchPartTable;
    @FXML
    private TableColumn<Part, Integer> searchPartIdColumn;
    @FXML
    private TableColumn<Part, String> searchPartNameColumn;
    @FXML
    private TableColumn<Part, Integer> searchPartInvColumn;
    @FXML
    private TableColumn<Part, Double> searchPartPriceColumn;
    @FXML
    private Button saveProduct;
    @FXML
    private Button cancel;
    @FXML
    private Button deletePart;
    @FXML
    private TextField productID;
    @FXML
    private TextField productName;
    @FXML
    private TextField productInv;
    @FXML
    private TextField productPrice;
    @FXML
    private TextField productMin;
    @FXML
    private TextField productMax;
    @FXML
    private TextField searchParts;
    @FXML
    private Text titleTarget;
    private Stage dialogStage;
    private Product product;
    public boolean saveClicked= false;
    private Manager manager;
    private ObservableList<Part> productPartData = FXCollections.observableArrayList();
    private ObservableList<Part> searchPartData = FXCollections.observableArrayList();

    private void initialize(){


    }
    public void setMain(Manager main) {
        this.manager = main;
      
    }
    public void setProduct(Product modProd) {
        this.product= modProd;        
        titleTarget.setText("Modify Product");   
        String partID = Integer.toString(modProd.getProductID());
        String partName = modProd.getProductName();
        String inv = Integer.toString(modProd.getProductInStock());
        String price  = Double.toString(modProd.getProductPrice());
        String min  = Integer.toString(modProd.getProductMin());
        String max = Integer.toString(modProd.getProductMax());
        for(Part p : modProd.getAssociatedParts()){
            productPartData.add(p);
            System.out.println("Part " + p.getPartName() + " belongs to this product");
        }
        productID.setText(partID);
        productName.setText(partName);
        productInv.setText(inv);
        productPrice.setText(price);
        productMin.setText(min);
        productMax.setText(max);
        
        productPartTable.setItems(productPartData);
        productPartIdColumn.setCellValueFactory(
                cellData -> cellData.getValue().partIDProperty().asObject());
        productPartNameColumn.setCellValueFactory(
                cellData -> cellData.getValue().partNameProperty());
        productPartInvColumn.setCellValueFactory(
                cellData -> cellData.getValue().partInStockProperty().asObject());
        productPartPriceColumn.setCellValueFactory(
                cellData -> cellData.getValue().partPriceProperty().asObject());
        setSearchParts();
    }
    
    public void initialProduct() {        
        titleTarget.setText("Add Product");   
    }
    public void setSearchParts() {
        ConcurrentHashMap<Integer,Part> partHashMap = Inventory.getAllParts();
         searchPartData.clear();
        for(int key  : partHashMap.keySet() ){
            Part newPart = partHashMap.get(key);
            searchPartData.add(newPart);
            System.out.println("We are updating the Table here...");
        }
        searchPartTable.setItems(searchPartData);
                //Initialize the product table with the 4 columns.
        searchPartIdColumn.setCellValueFactory(
                cellData -> cellData.getValue().partIDProperty().asObject());
        searchPartNameColumn.setCellValueFactory(
                cellData -> cellData.getValue().partNameProperty())
                
                ;
        searchPartInvColumn.setCellValueFactory(
                cellData -> cellData.getValue().partInStockProperty().asObject());
        searchPartPriceColumn.setCellValueFactory(
                cellData -> cellData.getValue().partPriceProperty().asObject());
        
    }
     public void setNewProductParts() {
        //productPartData.add(new Inhouse("No Parts Yet",0,0,0,0,999,0));
        productPartData.clear();
        productPartTable.setItems(productPartData);
        productPartIdColumn.setCellValueFactory(
                cellData -> cellData.getValue().partIDProperty().asObject());
        productPartNameColumn.setCellValueFactory(
                cellData -> cellData.getValue().partNameProperty());
        productPartInvColumn.setCellValueFactory(
                cellData -> cellData.getValue().partInStockProperty().asObject());
        productPartPriceColumn.setCellValueFactory(
                cellData -> cellData.getValue().partPriceProperty().asObject());
        setSearchParts();
        
    }
    public boolean isSaveClicked(){return saveClicked;}
    public void setMainApp(Manager main) {
        this.manager = main;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

        @FXML
    private void productCancel() {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.initOwner(dialogStage);
        confirm.setTitle("Cancel");
        confirm.setHeaderText("Cancel Operation");
        confirm.setContentText("Please Confirm that you would like to cancel this edit.");
        confirm.showAndWait();
        if (confirm.getResult() == ButtonType.OK) {    
            dialogStage.close();
            dialogStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent we) { }
                });
        }
        else{
            confirm.close();
        }
    }
    @FXML
    private void productSave() {
        ArrayList<Part> productParts = new ArrayList<Part>();
        ObservableList<Part> allParts = productPartTable.getItems();
        try{
            if( productInv.getText().length() > 0 &&  productName.getText().length() > 0 && productPrice.getText().length() > 0 && productInv.getText().length() > 0  && productMax.getText().length() > 0){

                double price = Double.parseDouble(productPrice.getText());
                double totalPrice = 0;
                if(Integer.parseInt(productMin.getText()) <= Integer.parseInt(productMax.getText())){
                    if(allParts.size() > 0){   
                        for(Part p : allParts){
                            System.out.println("Adding part " + p.getPartName());
                            totalPrice = p.getPartPrice() + totalPrice;
                            productParts.add(p);                        
                        }
                        System.out.println("Modifying Product...");
                        String name = productName.getText();
                        if(totalPrice <= price){                            
                            int inv = Integer.parseInt(productInv.getText());
                            int min = Integer.parseInt(productMin.getText());            
                            int max = Integer.parseInt(productMax.getText());
                            if(inv <= max){
                                int id;
                                if(productID.getText().length()<1){

                                    id = Inventory.productInvMap.size();
                                    Product newProduct = new Product(id,productParts,name,price,inv,min,max);
                                    System.out.println("Adding Product " +newProduct.getProductName() + " " +productParts.size() + " " +name+ " " + price + " " + inv   + " " +min + " "+max);
                                    Inventory.addProduct(newProduct);
                                    }
                                    else{
                                    id = Integer.parseInt(productID.getText());

                                    Product updatedProduct = new Product(id,productParts,name,price,inv,min,max);
                                    Inventory.updateProduct(updatedProduct);
                                }
                                 System.out.println("Product ID " + id);
                                        saveClicked=true;
                                        dialogStage.close();
                            }
                            else{
                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                alert.initOwner(dialogStage);
                                alert.setTitle("Inventory Error");
                                alert.setHeaderText("Inventory Exceeds Maximum");
                                alert.setContentText("The inventory amount ("+inv+") exceeds the maximum allowed amount " + max +".");
                                alert.showAndWait();
                            }
                        }
                        else{
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.initOwner(dialogStage);
                            alert.setTitle("Exceeded Price");
                            alert.setHeaderText("Product Price Error");
                            alert.setContentText("All total price ("+totalPrice+") exceed Product Price. Please increase the price or remove parts so total price is less than " + price);
                            alert.showAndWait();
                            }

                    }            
                    else{
                        System.out.println("No parts");
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.initOwner(dialogStage);
                        alert.setTitle("No parts selected");
                        alert.setHeaderText("Please add parts");
                        alert.setContentText("Please add parts to this product to save.");
                        alert.showAndWait();
                    }
                }
                else{
                    System.out.println("Min is greater Than Max");
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.initOwner(dialogStage);
                    alert.setTitle("Min > Max");
                    alert.setHeaderText("Min Greater than Max");
                    alert.setContentText("Please change minimum to a lower entry than maximum.");
                    alert.showAndWait();
                }
            }
            else{
                System.out.println("Invalid Entry");
                Alert alert = new Alert(AlertType.ERROR);   
                alert.initOwner(dialogStage);
                alert.setTitle("Invlaid Entry");
                alert.setHeaderText("Not all fields have values");
                alert.setContentText("Please entry an entry in each field input.");
                alert.showAndWait();
            }
        }
        catch(NumberFormatException e){
            e.printStackTrace();
            System.out.println("Invalid Foramt");
            Alert alert = new Alert(AlertType.ERROR);   
            alert.initOwner(dialogStage);
            alert.setTitle("Invlaid Format");
            alert.setHeaderText("Not all fields have proper values.");
            alert.setContentText("Please check your entries and try again.");
            alert.showAndWait();

        }
    }
     @FXML
    private void handleAddPart() throws InterruptedException {
         Part selectedPart = searchPartTable.getSelectionModel().getSelectedItem();
         if (selectedPart != null) {
            ObservableList<Part> existingParts = productPartTable.getItems();

            existingParts.add(selectedPart);
            productPartTable.setItems(existingParts);
                   //Initialize the product table with the 4 columns.
           searchPartIdColumn.setCellValueFactory(
                   cellData -> cellData.getValue().partIDProperty().asObject());
           searchPartNameColumn.setCellValueFactory(
                   cellData -> cellData.getValue().partNameProperty())

                   ;
           searchPartInvColumn.setCellValueFactory(
                   cellData -> cellData.getValue().partInStockProperty().asObject());
           searchPartPriceColumn.setCellValueFactory(
                   cellData -> cellData.getValue().partPriceProperty().asObject());
               
        }
         else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(manager.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Part Selected");
            alert.setContentText("Please select a part to add.");
            alert.showAndWait();
        }
    }
    @FXML
    private void handlePartSearch(){
        String searchedPart = searchParts.getText().toLowerCase();
        ConcurrentHashMap<Integer,Part> partHashMap = Inventory.getAllParts();
         searchPartData.clear();
        for(int key  : partHashMap.keySet() ){            
            Part newPart = partHashMap.get(key);
            if(newPart.getPartName().toLowerCase().contains(searchedPart)){
                searchPartData.add(newPart);
                System.out.println("We are updating the Table here...");
        
            }
        }
        searchPartTable.setItems(searchPartData);
        
    }
    @FXML
    private void handleDeletePart() {
        int selectedIndex = productPartTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Alert confirm = new Alert(AlertType.CONFIRMATION);
            confirm.initOwner(dialogStage);
            confirm.setTitle("Removing Part");
            confirm.setHeaderText("Please Confirm");
            confirm.setContentText("Please Confirm that you would like to remove this part.");
            confirm.showAndWait();
            if (confirm.getResult() == ButtonType.OK) {         
                productPartTable.getItems().remove(selectedIndex);
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
            alert.initOwner(dialogStage);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Part Selected");
            alert.setContentText("Please select a part to delete.");

            alert.showAndWait();
        }
    }
}


