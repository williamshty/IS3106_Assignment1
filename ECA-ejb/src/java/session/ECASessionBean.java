/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Buyer;
import entity.Cart;
import entity.Item;
import entity.Order;
import entity.Seller;
import entity.Admin;
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
        try{
        Query q = em.createQuery("SELECT a from Admin a WHERE a.username=:username AND a.password=:password ");
        q.setParameter("username", username);
        q.setParameter("password", password);
            System.out.print(q.getSingleResult());
        }
        catch(Exception e){
        System.out.println(e);
        }
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

    //Seller
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
    
    @Override
    public List<Item> viewSellerItems(long sellerID, String keyword) {
        Seller seller = em.find(Seller.class, sellerID);
        Query q;
        if (keyword == "" || keyword == null) {
            return seller.getItems();
        } else {
            q = em.createQuery("SELECT i FROM Item i, Seller s WHERE i MEMBER OF s.items "
                    + "AND s.id = :sid "
                    + "AND LOWER(i.name) LIKE :name");
            q.setParameter("name", "%" + keyword.toLowerCase() + "%");
            q.setParameter("sid", sellerID);
            return q.getResultList();
        }
    }
    
    @Override
    public void deleteItem(long itemID) {
        Item item = em.find(Item.class, itemID);
        if (item.getOrders() == null) {
            item.setQuantity(0);
        }
    }
    
    @Override
    public void editItem(Item item) {
        Item oldItem = em.find(Item.class, item.getId());
        if (oldItem != null) {
            oldItem.setName(item.getName());
            oldItem.setCategory(item.getCategory());
            oldItem.setDescription(item.getCategory());
            oldItem.setPrice(item.getPrice());
            oldItem.setQuantity(item.getQuantity());
        }
    }
    
    @Override
    public List<Order> viewAllSellerOrders(long sellerID) {
        Seller seller = em.find(Seller.class, sellerID);
        return seller.getOrders();
    }
    
    @Override
    public void updateOrderStatus(long orderID, String status) {
        Order order = em.find(Order.class, orderID);
        order.setStatus(status);
    }
    
    @Override
    public void updateSellerProfile(Seller seller) {
        Seller oldSeller = em.find(Seller.class, seller.getId());
        if (oldSeller != null) {
            oldSeller.setGender(seller.getGender());
            oldSeller.setName(seller.getName());
        }
    }
    
    @Override
    public void registerBuyer(String username, String password) {
    }
    
    @Override
    public void buyerLogin(String username, String password) {
    }
    
    @Override
    public void addItemToCart(long cartID, Item item) {
        Cart cart = em.find(Cart.class, cartID);
        cart.getItems().add(item);
    }
    
    @Override
    public void checkOutCart(long cartID) {
        Cart cart = em.find(Cart.class, cartID);
        Order order = new Order();
        order.setItems(cart.getItems());
        order.setBuyer(cart.getBuyer());
        em.persist(order);
        cart.setItems(null);
    }
    
    @Override
    public List<Order> viewAllBuyerOrders(long buyerID) {
        Buyer buyer = em.find(Buyer.class, buyerID);
        return buyer.getOrders();
    }
    
    @Override
    public void addFeedback(String rating, String review, long orderID) {
        Order order = em.find(Order.class, orderID);
        if (order.getRating() == null && order.getReview() == null) {
            order.setRating(rating);
            order.setReview(review);
        }
    }
    @Override
    public void updateBuyerProfile(Buyer buyer) {
        Buyer oldBuyer = em.find(Buyer.class, buyer.getId());
        if (oldBuyer != null) {
            oldBuyer.setGender(buyer.getGender());
            oldBuyer.setName(buyer.getName());
        }
    }
}
