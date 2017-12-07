/* Part.class
* Control how parts are created and added
*/
package manger.warehouse;


import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import manger.warehouse.Inhouse;
import manger.warehouse.Outsourced;
/**
 *
 * @author Eric Caskey
 */
public abstract class Part  {
 

    public static ConcurrentHashMap<Integer,Part> partsMap;
    static{
          partsMap = new ConcurrentHashMap<>();
          partsMap.put(0,new Inhouse ("motherboard", 400, 211.00,100,1,0,1000));
          partsMap.put(1,new Outsourced ("motherboard", 300, 511.00,100,1,1,"Ecorp"));
          partsMap.put(2,new Inhouse("Ink Cartridge", 50, 56.00,100,1,2,2));
          partsMap.put(3,new Outsourced("Wireless Router", 6, 6000.85,100,100,3,"E Corp"));
          partsMap.put(4,new Outsourced("Keyboard", 80, 651.10,100,1,4,"E Corp"));
          partsMap.put(5,new Outsourced("Mouse", 55, 811.00,100,1,5,"E Corp"));
          partsMap.put(6,new Inhouse("wireless Keyboard", 805, 2101.9,100,1,6,1520));
    }
    public static ArrayList<Part> partArray = new ArrayList<Part>();
    private StringProperty partName;
    private DoubleProperty partPrice;
    private IntegerProperty partMax;
    private IntegerProperty partMin;
    public  IntegerProperty partInStock;
    private StringProperty partCompany;
    private IntegerProperty partID;
    public String getPartName(){return partNameProperty().get();}
    //public int getInvLvl(){return invLvlProperty().get();}
    public double getPartPrice(){return partPriceProperty().get();}
    
    public int getPartInStock(){return partInStockProperty().get();}
    public int getPartMax(){return partMaxProperty().get();}
    public int getPartMin(){return partMinProperty().get();}
    public String getPartCompany(){return partCompanyProperty().get();}
    public int getPartID(){return partIDProperty().get();}
    public ArrayList<Part> getPartArray(){ return partArray;}
    public static ConcurrentHashMap<Integer,Part> getPartsMap(){return partsMap;}

    public void setPartName(String partName){partNameProperty().set(partName);}
    public void setPartPrice(double price){partPriceProperty().set(price);}
    public void setPartInStock(int inStock){partInStockProperty().set(inStock);}
    public void setPartMax(int max){partMaxProperty().set(max);}
    public void setPartMin(int min){partMinProperty().set(min);}
    public void setPartCompany(String company){partCompanyProperty().set(company);}
    public void setPartID(int partID){partIDProperty().set(partID);}
    public static void setPartArray(ArrayList<Part> partArray){Part.partArray = partArray;}

    public StringProperty partNameProperty() { 
             if (partName == null) partName = new SimpleStringProperty(this, "partName");
             return partName; 
         }
    public DoubleProperty partPriceProperty() { 
             if (partPrice == null) partPrice = new SimpleDoubleProperty(this, "partPrice");
             return partPrice; 
         }    
    public IntegerProperty partInStockProperty() { 
             if (partInStock == null) partInStock = new SimpleIntegerProperty(this, "partInStock");
             return partInStock; 
         }
    public IntegerProperty partMaxProperty() { 
             if (partMax == null) partMax = new SimpleIntegerProperty(this, "partMax");
             return partMax; 
         }
    public IntegerProperty partMinProperty() { 
             if (partMin == null) partMin = new SimpleIntegerProperty(this, "partMin");
             return partMin; 
         }
    public StringProperty partCompanyProperty() { 
             if (partCompany == null) partCompany = new SimpleStringProperty(this, "partCompany");
             return partCompany; 
         }
    public IntegerProperty partIDProperty() { 
             if (partID == null) partID = new SimpleIntegerProperty(this, "partID");
             return partID; 
         }
        public Part(){}

    public  Part(String partName, int inStock, double price, int max, int min, int partID){
        System.out.println("Setting Part Now... ");
        setPartName(partName);
        setPartInStock(inStock);
        setPartPrice(price);
        setPartMax(max);
        setPartMin(min);
        //setPartCompany(company);
        setPartID(partID);
    }

    public static boolean addPart(Part newPart){
        
        partArray.add(newPart);
        partsMap.put(partsMap.size(),newPart);
        return true;
    }
    public static ConcurrentHashMap viewAllUsers(){
        return partsMap;
    }
    public static int viewPart(String part){
        int returnkey  = 0;
        for(Integer key :  partsMap.keySet()){
            Part newPart = partsMap.get(key);
            if(newPart.getPartName() == part)   
            {
                return key;
            } 
        }  
        return returnkey;
    }

    public boolean deletePart(String[] args){
        boolean deleted = false;

        for(Part part :  partArray){

            if(part.getPartName() == args[0])   
            {
                partsMap.remove(part);
                deleted = true;
            } 
        }    
        return deleted;
    }
 }
