/*Outsourced.class
* Control outsourced parts and add company name
*/
package manger.warehouse;

import manger.*;

/**
 *
 * @author Eric Caskey
 */
public class Outsourced extends Part{
    public String companyName;
     public Outsourced(){}
    public Outsourced(String partName, int inStock, double price, int max, int min,int partID, String comp){
        super(partName, inStock, price, max, min, partID);
        setCompanyName(comp);
    }
    public String getCompanyName(){return companyName;}
    public void setCompanyName(String companyName){this.companyName = companyName;}
}
