package manger.controller;


import manger.warehouse.Inventory;
import java.util.concurrent.ConcurrentHashMap;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import manger.warehouse.Part;
import static manger.warehouse.Part.partArray;
import static manger.warehouse.Part.partsMap;

/**
 *
 * @author Eric Caskey
 */
public class UpdatePartController {

    @FXML
    private TextField partID;
    @FXML
    private TextField partName;
    @FXML
    private TextField partInv;
    @FXML
    private TextField partPrice;
    @FXML
    private TextField partMin;
    @FXML
    private TextField partMax;
    @FXML
    private TextField partCompany;
    @FXML
    private TextField partMachineID;
    @FXML
    private Text titleTarget;
    @FXML
    ToggleButton Inhouse;
    @FXML
    ToggleButton Outsourced = new ToggleButton();
    @FXML
    final ToggleGroup partSource = new ToggleGroup();
    @FXML
    private Button save;
    @FXML
    private Button cancel;
    private Stage dialogStage;
    private Part part;
    public static boolean saveClicked= false;
    private Manager manager;

    @FXML
    private void initialize(){
        
        partSource.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> ov,
                                Toggle old_toggle, Toggle new_toggle) {
                
                if (Inhouse.isSelected()) {                    
                    System.out.println("InHouse Selected");
                    partCompany.setVisible(false);
                    partMachineID.setVisible(true);
                } else if (Outsourced.isSelected()){
                    partCompany.setVisible(true);
                    System.out.println("OutSource Selected");
                    partMachineID.setVisible(false);
                }
            }
        });
    }
    public void setMainApp(Manager main) {
        this.manager = main;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    public void initialPart() {        
        titleTarget.setText("Add Part");
        Inhouse.setToggleGroup(partSource);
        Outsourced.setToggleGroup(partSource);
        Inhouse.setSelected(true);        
    }
    public void setPart(Part part) {
        this.part= part;        
        titleTarget.setText("Modify Part");
        Inhouse.setToggleGroup(partSource);
        Outsourced.setToggleGroup(partSource);
   
            String id = Integer.toString(part.getPartID());
            System.out.println("Part ID  " +id);
            String name = part.getPartName();
            System.out.println("Part Name " +name);
            String inv = Integer.toString(part.getPartInStock());
            String price  = Double.toString(part.getPartPrice());
            String min  = Integer.toString(part.getPartMin());
            String max = Integer.toString(part.getPartMax());
            String comp = null;
            String mach = null;
            if(part instanceof Outsourced) {
                 comp = ((Outsourced)part).getCompanyName();         
                Outsourced.setSelected(true); 
            }
            else{
                 mach = Integer.toString(((Inhouse)part).getMachineID());
                Inhouse.setSelected(true);
            }
            partID.setText(id);
            partName.setText(name);
            partInv.setText(inv);
            partPrice.setText(price);
            partMin.setText(min);
            partMax.setText(max);
            if(comp!=null){
                partCompany.setText(comp);
            }
            else{
                partMachineID.setText(mach);
            }
        
    }
    public static boolean isSaveClicked(){return saveClicked;}

    @FXML
    private void partSave(){
        try{
            if( partInv.getText().length() > 0 &&  partName.getText().length() > 0 && partPrice.getText().length() > 0 && partInv.getText().length() > 0 && partMax.getText().length() > 0 && partMin.getText().length() > 0){
                    int min = Integer.parseInt(partMin.getText());            
                    int max = Integer.parseInt(partMax.getText());
                    String name = partName.getText();
                    int inv = Integer.parseInt(partInv.getText()); 
                    double price = Double.parseDouble(partPrice.getText());
                    int id = 0;
                    if(partID.getText().length() > 0){
                        id = Integer.parseInt(partID.getText());
                    }
                    else{
                        id = partsMap.size();
                    }
                if(min <= max){   
                    if(inv <= max){
                        if (partSource.getSelectedToggle().equals(Inhouse)){
                            int mach = Integer.parseInt(partMachineID.getText());
                            Part part = new Inhouse(name,inv,price,max,min,id,mach);
                            Inventory.updatePart(part);
                            saveClicked=true;
                            dialogStage.close();
                        } 
                        else if (partSource.getSelectedToggle().equals(Outsourced)){
                            String comp = partCompany.getText();
                            Part part = new Outsourced(name,inv,price,max,min,id,comp);
                            Inventory.updatePart(part);
                            saveClicked=true;
                            dialogStage.close();
                        }
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
    private void partCancel() {
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
}
