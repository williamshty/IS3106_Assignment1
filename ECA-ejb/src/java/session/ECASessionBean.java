/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Buyer;
import entity.Item;
import entity.Seller;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author tianyuan.shi
 */
@Stateless
public class ECASessionBean implements ECASessionBeanLocal {

    @PersistenceContext
    private EntityManager em;

    //Admin
    @Override
    public void adminLogin(String username, String password) {

    }

    @Override
    public List<Seller> viewAllSellers() {
        Query q = em.createQuery("SELECT * from Seller");
        return q.getResultList();
    }

    @Override
    public List<Buyer> viewAllBuyers() {
        Query q = em.createQuery("SELECT * from Buyer");
        return q.getResultList();
    }

    @Override
    public void deactivateSeller(long sellerID) {
        Seller seller = em.find(Seller.class, sellerID);
        seller.setStatus(false);
    }

    @Override
    public void deactivateBuyer(long buyerID) {
        Buyer buyer = em.find(Buyer.class, buyerID);
        buyer.setStatus(false);
    }

    @Override
    public void activateSeller(long sellerID) {
        Seller seller = em.find(Seller.class, sellerID);
        seller.setStatus(true);
    }

    @Override
    public void activateBuyer(long buyerID) {
        Buyer buyer = em.find(Buyer.class, buyerID);
        buyer.setStatus(true);
    }

    @Override
    public void registerSeller(String username, String password) {
    }

    @Override
    public void sellerLogin(String username, String password) {
    }

    @Override
    public void addItem(Item item, long sellerID) {
        Seller seller = em.find(Seller.class, sellerID);
        if (seller != null) {
            if (!seller.getItems().contains(item)) {
                em.persist(item);
                seller.getItems().add(item);
            }
        }
    }
    
    

}
