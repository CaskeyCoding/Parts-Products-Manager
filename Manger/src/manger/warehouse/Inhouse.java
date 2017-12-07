/*Inhouse.class
* Control inhour parts and add machine id
*/
package manger.warehouse;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import manger.*;
import manger.warehouse.Part;
/**
 *
 * @author Eric Caskey
 */
public class Inhouse extends Part {
    
    public int machineID;
    public  Inhouse(String partName, int inStock, double price, int max, int min, int partID, int machineID) {
        super(partName, inStock, price, max, min, partID);
        setMachineID(machineID);
    }
    public void setMachineID(int newMachineID){
        this.machineID = newMachineID;
    }
    public int getMachineID(){
        return machineID;
    }
     public String getCompanyName(){return null;}
}
