/* Product.class
* Control how products are created and added
*/
package manger.warehouse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import static manger.warehouse.Part.partsMap;
/**
 *
 * @author Eric Caskey
 */
public class Product  {
 
    public static ArrayList<Product> productArray;
    private ArrayList<Part> parts;
    private IntegerProperty productID;
    private ArrayList<Part> associatedParts = new ArrayList<>();
    private StringProperty prodName; 
    private DoubleProperty prodPrice; 
    private IntegerProperty prodInStock;
    private IntegerProperty prodMin; 
    private IntegerProperty prodMax;

    public static ConcurrentHashMap<Integer,Product> productMap;
    static{
          productMap = new ConcurrentHashMap<>();
          productMap.put(0,new Product (0, new ArrayList<Part>(Arrays.asList(partsMap.get(0))),"Super Machine", 2101.00,100,1,708));
          productMap.put(1,new Product(1, new ArrayList<Part>(Arrays.asList(partsMap.get(0))),"Mega Ultra Machine", 570.10,100,1,100));
          productMap.put(2,new Product(2, new ArrayList<Part>(Arrays.asList(partsMap.get(0))),"Wicked Awesome Printer", 560.00,100,1,100));
          productMap.put(3,new Product(3, new ArrayList<Part>(Arrays.asList(partsMap.get(0))),"Real Deal Wireless Mouse", 6000.85,100,1,500));
          productMap.put(4,new Product(4, new ArrayList<Part>(Arrays.asList(partsMap.get(0))),"Magic  Monitor", 651.10,100,1,112));
          productMap.put(5,new Product(5, new ArrayList<Part>(Arrays.asList(partsMap.get(0))),"Holy Hand Grenade", 5101.88, 200,1,216));
          productMap.put(6,new Product(6, new ArrayList<Part>(Arrays.asList(partsMap.get(0))),"Short Circuit Silly String", 4000.50, 100,1,100));
          productMap.put(7,new Product(7, new ArrayList<Part>(Arrays.asList(partsMap.get(0))),"Really Cool Thing", 2110.70,50,1,50));
    }
    public int getProductID(){return productIDProperty().get();}
    public String getProductName(){return productNameProperty().get();}
    public double getProductPrice(){return productPriceProperty().get();}
    public int getProductInStock(){return productInStockProperty().get();}
    public int getProductMax(){return productMaxProperty().get();}
    public int getProductMin(){return productMinProperty().get();}
    public ArrayList<Part> getAssociatedParts(){return associatedParts;}
    
    public void setProductID(int productID){productIDProperty().set(productID);}
    public void setProductName(String prodName){productNameProperty().set(prodName);}
    public void setProductPrice(double prodPrice){productPriceProperty().set(prodPrice);}
    public void setProductInStock(int prodInStock){productInStockProperty().set(prodInStock);}
    public void setProductMax(int prodMax){productMaxProperty().set(prodMax);}
    public void setProductMin(int prodMin){productMinProperty().set(prodMin);}
    public IntegerProperty productIDProperty() { 
             if (productID == null) productID = new SimpleIntegerProperty(this, "productID");
             return productID; 
         }
    public StringProperty productNameProperty() { 
             if (prodName == null) prodName = new SimpleStringProperty(this, "prodName");
             return prodName; 
         }
    public DoubleProperty productPriceProperty() { 
             if (prodPrice == null) prodPrice = new SimpleDoubleProperty(this, "prodPrice");
             return prodPrice; 
         }
    public IntegerProperty productInStockProperty() { 
             if (prodInStock == null) prodInStock = new SimpleIntegerProperty(this, "prodInStock");
             return prodInStock; 
         }
    public IntegerProperty productMaxProperty() { 
             if (prodMax == null) prodMax = new SimpleIntegerProperty(this, "prodMax");
             return prodMax; 
         }
    public IntegerProperty productMinProperty() { 
             if (prodMin == null) prodMin = new SimpleIntegerProperty(this, "prodMin");
             return prodMin; 
         }
   public  Product(int a, ArrayList<Part> b, String c, double d, int e, int f, int g){
        if(b.size() > 0){
            for(Part p : b){              
                addAssociatedPart(p);
            }
        }
        System.out.println(a + "  " +c+ "  " + d + "  "  +e + "  " + f + "  " + g );
        setProductName(c);
        setProductInStock(e);
        setProductPrice(d);
        setProductMax(g);
        setProductMin(f);
        setProductID(a);

    }
    public void addPart(Part newPart){
        parts.add(newPart);
     }
    public void addAssociatedPart(Part part){
        System.out.println("Adding associated Part: " + part.getPartName());
                this.associatedParts.add(part);            
    }
    public boolean removeAssociatedPart(int i){
                this.associatedParts.remove(i);            
                return true;
    }
    public Part lookupAssociatedPart(int i){
                Part part =associatedParts.get(i);
                return part;
    }
}