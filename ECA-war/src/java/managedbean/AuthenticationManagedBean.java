/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbean;

import javax.inject.Named;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import session.ECASessionBeanLocal;

/**
 *
 * @author tianyuan.shi
 */
@Named(value = "authenticationManagedBean")
@ApplicationScoped
public class AuthenticationManagedBean implements Serializable {

    @EJB
    private ECASessionBeanLocal ecaSessionBeanLocal;
    private String username = null;
    private String password = null;
    //type: "ADMIN", "SELLER", "BUYER"
    private String type;

    /**
     * Creates a new instance of AuthenticationManagedBean
     */
    public AuthenticationManagedBean() {
    }

    public void login() {
        System.out.print(getUsername());
        System.out.print(getPassword());
        ecaSessionBeanLocal.adminLogin(getUsername(), getPassword());
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
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }
}
