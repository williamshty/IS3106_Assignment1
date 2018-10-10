/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbean;

import entity.Buyer;
import entity.Seller;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import session.ECASessionBeanLocal;

/**
 *
 * @author tianyuan.shi
 */
@Named(value = "adminManagedBean")
@SessionScoped
public class AdminManagedBean implements Serializable{
    @EJB
    private ECASessionBeanLocal ecaSessionBeanLocal;
    private String username;
    private String password;
    private List<Seller> sellers;
    private List<Buyer> buyers;
    /**
     * Creates a new instance of AdminManagedBean
     */
    @PostConstruct
    public void init() {
        setSellers(ecaSessionBeanLocal.viewAllSellers());
        setBuyers(ecaSessionBeanLocal.viewAllBuyers());
        System.out.print("Hello WOrld");
    }
    public String login() {
        System.out.print(getUsername());
        System.out.print(getPassword());
        ecaSessionBeanLocal.adminLogin(getUsername(), getPassword());
        return "adminConsole.xhtml";
    }
    public void deactivateSeller(long sellerID) {
        ecaSessionBeanLocal.deactivateSeller(sellerID);
        setSellers(ecaSessionBeanLocal.viewAllSellers());
    }
    public void activateSeller(long sellerID) {
        ecaSessionBeanLocal.activateSeller(sellerID);
        setSellers(ecaSessionBeanLocal.viewAllSellers());
    }
    
    public void deactivateBuyer(long buyerID) {
        ecaSessionBeanLocal.deactivateBuyer(buyerID);
         setBuyers(ecaSessionBeanLocal.viewAllBuyers());
    }
    public void activateBuyer(long buyerID) {
        ecaSessionBeanLocal.activateBuyer(buyerID);
        setBuyers(ecaSessionBeanLocal.viewAllBuyers());
    }
    
    
    /** 
     * @return the sellers
     */
    public List<Seller> getSellers() {
        return sellers;
    }

    /**
     * @param sellers the sellers to set
     */
    public void setSellers(List<Seller> sellers) {
        this.sellers = sellers;
    }

    /**
     * @return the buyers
     */
    public List<Buyer> getBuyers() {
        return buyers;
    }

    /**
     * @param buyers the buyers to set
     */
    public void setBuyers(List<Buyer> buyers) {
        this.buyers = buyers;
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
}
