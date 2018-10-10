/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbean;

import entity.Item;
import entity.SaleOrder;
import entity.Seller;
import java.io.Serializable;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import session.ECASessionBeanLocal;

/**
 *
 * @author tianyuan.shi
 */
@Named(value = "sellerManagedBean")
@SessionScoped
public class SellerManagedBean implements Serializable{

    private Seller seller;
    private Long id;
    private String username;
    private String password;
    private byte gender;
    private String name;
    private boolean status;
    private ArrayList<SaleOrder> orders;
    private ArrayList<Item> items;
    private String itemName;
    private String itemDescription;
    private String itemCategory;
    private float itemPrice;
    private long itemQuantity;
    @EJB
    private ECASessionBeanLocal ecaSessionBeanLocal;

    /**
     * Creates a new instance of SellerManagedBean
     */
    public SellerManagedBean() {
    }
    
    public void registerNewSeller(){
        Seller newSeller = new Seller();
        newSeller.setGender(getGender());
        newSeller.setName(getName());
        newSeller.setUsername(getUsername());
        newSeller.setPassword(getPassword());
        newSeller.setStatus(true);
        newSeller = getEcaSessionBeanLocal().registerSeller(newSeller);
//        newSeller.setCart(newCart);
//        newCart.setSeller(newSeller);
    }
    public String login() {
        System.out.print(getUsername());
        System.out.print(getPassword());
        setSeller(getEcaSessionBeanLocal().sellerLogin(getUsername(), getPassword()));
        return "sellerConsole.xhtml";
    }
    public void updateProfile(){
        Seller seller = getSeller();
        seller.setName(getName());
        seller.setGender(getGender());
        getEcaSessionBeanLocal().updateSellerProfile(seller);
    }
    public void addItem(){
        Item item = new Item();
        item.setName(itemName);
        item.setDescription(itemDescription);
        item.setCategory(itemCategory);
        item.setPrice(itemPrice);
        item.setQuantity(itemQuantity);
        ecaSessionBeanLocal.addItem(item, getSeller().getId());
    }
    
    
    
    
    
    

    /**
     * @return the seller
     */
    public Seller getSeller() {
        return seller;
    }

    /**
     * @param seller the seller to set
     */
    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the gender
     */
    public byte getGender() {
        return gender;
    }

    /**
     * @param gender the gender to set
     */
    public void setGender(byte gender) {
        this.gender = gender;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the status
     */
    public boolean isStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(boolean status) {
        this.status = status;
    }

    /**
     * @return the orders
     */
    public ArrayList<SaleOrder> getOrders() {
        return orders;
    }

    /**
     * @param orders the orders to set
     */
    public void setOrders(ArrayList<SaleOrder> orders) {
        this.orders = orders;
    }

    /**
     * @return the items
     */
    public ArrayList<Item> getItems() {
        return items;
    }

    /**
     * @param items the items to set
     */
    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    /**
     * @return the itemName
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * @param itemName the itemName to set
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * @return the itemDescription
     */
    public String getItemDescription() {
        return itemDescription;
    }

    /**
     * @param itemDescription the itemDescription to set
     */
    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    /**
     * @return the itemCategory
     */
    public String getItemCategory() {
        return itemCategory;
    }

    /**
     * @param itemCategory the itemCategory to set
     */
    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    /**
     * @return the itemPrice
     */
    public float getItemPrice() {
        return itemPrice;
    }

    /**
     * @param itemPrice the itemPrice to set
     */
    public void setItemPrice(float itemPrice) {
        this.itemPrice = itemPrice;
    }

    /**
     * @return the itemQuantity
     */
    public long getItemQuantity() {
        return itemQuantity;
    }

    /**
     * @param itemQuantity the itemQuantity to set
     */
    public void setItemQuantity(long itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    /**
     * @return the ecaSessionBeanLocal
     */
    public ECASessionBeanLocal getEcaSessionBeanLocal() {
        return ecaSessionBeanLocal;
    }

    /**
     * @param ecaSessionBeanLocal the ecaSessionBeanLocal to set
     */
    public void setEcaSessionBeanLocal(ECASessionBeanLocal ecaSessionBeanLocal) {
        this.ecaSessionBeanLocal = ecaSessionBeanLocal;
    }

}
