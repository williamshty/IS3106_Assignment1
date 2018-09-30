/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbean;

import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import session.ECASessionBeanLocal;

/**
 *
 * @author tianyuan.shi
 */
@Named(value = "buyerManagedBean")
@RequestScoped
public class BuyerManagedBean {

    @EJB
    private ECASessionBeanLocal ecaSessionBeanLocal;

    /**
     * Creates a new instance of BuyerManagedBean
     */
    public BuyerManagedBean() {
    }

}
