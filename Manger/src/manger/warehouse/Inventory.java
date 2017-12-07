/*Inventory.class
* Control parts and products. Is referenced to remove, add and update parts/products
*/
package manger.warehouse;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
/**
 *
 * @author Eric Caskey
 */
public class Inventory {
    public static ArrayList<Part> allParts = Part.partArray;
    public static ArrayList<Product> products = Product.productArray;
    public static ConcurrentHashMap<Integer,Part> partInvMap = Part.partsMap;
    public static ConcurrentHashMap<Integer,Part> getAllParts(){return partInvMap;};
    public static ConcurrentHashMap<Integer,Product> productInvMap = Product.productMap;
    public static ConcurrentHashMap<Integer,Product> getAllProducts(){return productInvMap;};
    /*START PRODUCTS INVENTORY*/
    /*START ADD PRODUCT*/
    public static void addProduct(Product product){
        
        System.out.println("Adding Product  " + product.getProductName());
        productInvMap.put(productInvMap.size(),product);
    }    
    /*END ADD PRODUCT*/
    /*START DELETE PRODUCT*/
    public static boolean removeProduct(int removeID){
        for(Product product : products){
            if(product.getProductID()==removeID){
                products.remove(product);
                return true;
            }
        }
        return false;
    }    
    /*END DELETE PRODUCT*/
    /*START SEARCH PRODUCT*/
    public static Product lookupProduct(int i){         
        
        ArrayList<Part> c = new ArrayList<>();
        Part noPart =  new Inhouse("No parts", 0, 0, 0, 0, 0, 0) {};
        c.add(noPart);
        Product noProduct= new Product (0,c,"No Product", 0.0,0,0,0);
        for(Product product : products){
             int prod = product.getProductID();
             if(prod == i){
                 return product;
             }
         }
        return noProduct;
    }
    /*END SEARCH PRODUCT*/
    /*START UPDATE PRODUCT*/
    public static void updateProduct(Product productUpdate){
         for(int i: productInvMap.keySet()){
             Product p = productInvMap.get(i);
             if(p.getProductID() == productUpdate.getProductID()){
                 productInvMap.put(i,productUpdate);
             }
         }
         
    }
    /*END UPDATE PRODUCT*/
    /*END PRODUCTS INVENTORY */
    /*START PARTS INVENTORY */
    /*START ADD PART*/
    public static void addPart(Part newpart){
        System.out.println("Adding Part..." + newpart.getPartName());
        Part.partsMap.put(partInvMap.size(),newpart);

    }
    /*END ADD PART*/
    /*START DELETE PART*/ 
    public static boolean deletePart(Part removePart){
       for(Part part : allParts){
           if(part.getPartID() == removePart.getPartID()){
                allParts.remove(removePart);
                return true;
            }         
        }
       return false;
    }
    /*END DELETE PART*/ 
    /*START SEARCH PART*/    
    public static Part lookupPart(int i){       
        Part noPart =  new Part("No parts", 0, 0, 0, 0, 0) {};
        for(Part part : allParts){
             int prod = part.getPartID();
             if(prod == i){
                 return part;
             }
         }
        return noPart;
    }    
    /*END SEARCH PART*/ 
    /*START UPDATE PART*/
    public static void updatePart(Part updated){
        int updateID = updated.getPartID();
        Part.partsMap.put(updateID,updated);
    }
    /*END UPDATE PART*/
    /*END PARTS INVENTORY*/
}
