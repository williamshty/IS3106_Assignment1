/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbean;

import entity.Buyer;
import entity.Cart;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import session.ECASessionBeanLocal;

/**
 *
 * @author tianyuan.shi
 */
@Named(value="buyerManagedBean")
@SessionScoped
public class BuyerManagedBean implements Serializable{
    private Buyer buyer;
    private Long id;
    private String username;
    private String password;
    private byte gender;
    private String name;
    private boolean status;
    @EJB
    private ECASessionBeanLocal ecaSessionBeanLocal;

    /**
     * Creates a new instance of BuyerManagedBean
     */
    @PostConstruct
    public void init() {
//        setId(1L);
//        getBuyerByID();
    }
    
    public void getBuyerByID(){
        this.buyer = ecaSessionBeanLocal.getBuyerByID(getId());
    }
    
    public void registerNewBuyer(){
        Buyer newBuyer = new Buyer();
        newBuyer.setGender(gender);
        newBuyer.setName(name);
        newBuyer.setUsername(username);
        newBuyer.setPassword(password);
        newBuyer.setStatus(true);
        Cart newCart = new Cart();
        newBuyer.setCart(newCart);
        newCart.setBuyer(newBuyer);
//        newCart = ecaSessionBeanLocal.createNewCart(newCart);
        newBuyer = ecaSessionBeanLocal.registerBuyer(newBuyer);
//        newBuyer.setCart(newCart);
//        newCart.setBuyer(newBuyer);
    }
    public String login() {
        System.out.print(getUsername());
        System.out.print(getPassword());
        ecaSessionBeanLocal.buyerLogin(getUsername(), getPassword());
        return "buyerConsole.xhtml";
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
    
   
}
