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
    @EJB
    private ECASessionBeanLocal ecaSessionBeanLocal;

    /**
     * Creates a new instance of SellerManagedBean
     */
    public SellerManagedBean() {
    }
    
    public void registerNewSeller(){
        Seller newSeller = new Seller();
        newSeller.setGender(gender);
        newSeller.setName(name);
        newSeller.setUsername(username);
        newSeller.setPassword(password);
        newSeller.setStatus(true);
        newSeller = ecaSessionBeanLocal.registerSeller(newSeller);
//        newSeller.setCart(newCart);
//        newCart.setSeller(newSeller);
    }
    public String login() {
        System.out.print(getUsername());
        System.out.print(getPassword());
        setSeller(ecaSessionBeanLocal.sellerLogin(getUsername(), getPassword()));
        return "sellerConsole.xhtml";
    }
    public void updateProfile(){
        Seller seller = getSeller();
        seller.setName(name);
        seller.setGender(gender);
        ecaSessionBeanLocal.updateSellerProfile(seller);
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

}
