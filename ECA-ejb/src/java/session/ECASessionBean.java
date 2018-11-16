/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Buyer;
import entity.Cart;
import entity.Item;
import entity.ItemOrder;
import entity.SaleOrder;
import entity.Seller;
import java.util.ArrayList;
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
        try {
            Query q = em.createQuery("SELECT a from Admin a WHERE a.username=:username AND a.password=:password ");
            q.setParameter("username", username);
            q.setParameter("password", password);
            System.out.print(q.getSingleResult());
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public List<Seller> viewAllSellers() {
        Query q = em.createQuery("SELECT s from Seller s");
        return q.getResultList();
    }

    @Override
    public List<Buyer> viewAllBuyers() {
        Query q = em.createQuery("SELECT b from Buyer b");
        return q.getResultList();
    }

    @Override
    public void deactivateSeller(long sellerID) {
        Seller seller = em.find(Seller.class, sellerID);
        seller.setStatus(false);
        em.merge(seller);
    }

    @Override
    public void deactivateBuyer(long buyerID) {
        Buyer buyer = em.find(Buyer.class, buyerID);
        buyer.setStatus(false);
        em.merge(buyer);
    }

    @Override
    public void activateSeller(long sellerID) {
        Seller seller = em.find(Seller.class, sellerID);
        seller.setStatus(true);
        em.merge(seller);
    }

    @Override
    public void activateBuyer(long buyerID) {
        Buyer buyer = em.find(Buyer.class, buyerID);
        buyer.setStatus(true);
        em.merge(buyer);
    }

    //Seller
    @Override
    public Seller registerSeller(Seller seller) {
        em.persist(seller);
        em.flush();
        return seller;
    }

    @Override
    public Seller sellerLogin(String username, String password) {
        try {
            Query q = em.createQuery("SELECT c from Seller c WHERE c.username=:username AND c.password=:password AND c.status=true");
            q.setParameter("username", username);
            q.setParameter("password", password);
            return (Seller) q.getSingleResult();
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }

    }

    @Override
    public void addItem(Item item, long sellerID) {
        Seller seller = em.find(Seller.class, sellerID);
        if (seller != null) {
            if (!seller.getItems().contains(item)) {
                item.setSeller(seller);
                em.persist(item);
                seller.getItems().add(item);
            }
        }
    }

    @Override
    public List<Item> viewSellerItems(long sellerID, String keyword) {
        System.out.println("session.ECASessionBean.viewSellerItems()");
        Seller seller = em.find(Seller.class, sellerID);
        Query q;
        if (keyword == "" || keyword == null) {
            System.out.println("session.ECASessionBean.viewSellerItems()");
            System.out.println(seller.getItems());
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
        if (item.getOrders() == null || item.getOrders().size() == 0) {
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
    public List<ItemOrder> viewAllSellerOrders(long sellerID) {
        Seller seller = em.find(Seller.class, sellerID);
        List<Item> items = seller.getItems();
        List<ItemOrder> orders = new ArrayList<ItemOrder>();
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getOrders() != null) {
                for (int j = 0; j < items.get(i).getOrders().size(); j++) {
                    orders.add(items.get(i).getOrders().get(j));
                }
            }
        }
        return orders;
    }

    @Override
    public void updateOrderStatus(long orderID, String status) {
        ItemOrder order = em.find(ItemOrder.class, orderID);
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
    public Buyer registerBuyer(Buyer buyer) {
        em.persist(buyer);
        em.flush();
        return buyer;
    }

    @Override
    public Buyer buyerLogin(String username, String password) {
        try {
            Query q = em.createQuery("SELECT b from Buyer b WHERE b.username=:username AND b.password=:password AND b.status=true");
            q.setParameter("username", username);
            q.setParameter("password", password);
            return (Buyer) q.getSingleResult();
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    @Override
    public void addItemToCart(long cartID, Item item) {
        Cart cart = em.find(Cart.class, cartID);
        cart.getItems().add(item);
    }

    @Override
    public void checkOutCart(long cartID) {
//        Cart cart = em.find(Cart.class, cartID);
//        ItemOrder order = new ItemOrder();
//        order.setItems(cart.getItems());
//        order.setBuyer(cart.getBuyer());
//        em.persist(order);
//        cart.setItems(null);
    }

    @Override
    public List<ItemOrder> viewAllBuyerOrders(long buyerID) {
        Buyer buyer = em.find(Buyer.class, buyerID);
        return buyer.getOrders();
    }

    @Override
    public void addFeedback(String rating, String review, long orderID) {
        ItemOrder order = em.find(ItemOrder.class, orderID);
        if (order != null) {
            if (order.getRating() == null && order.getReview() == null) {
                order.setRating(rating);
                order.setReview(review);
            }
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

    @Override
    public Buyer getBuyerByID(long buyerID) {
        Buyer buyer = em.find(Buyer.class, buyerID);
        return buyer;
    }

    @Override
    public Seller getSellerByID(long sellerID) {
        Seller seller = em.find(Seller.class, sellerID);
        return seller;
    }

    @Override
    public void updateCart(Cart cart) {
        Cart oldCart = em.find(Cart.class, cart.getId());
        oldCart.setItems(cart.getItems());
    }

    @Override
    public ArrayList<Item> viewAllSellerItems(long sellerID) {
        Seller seller = em.find(Seller.class, sellerID);
        System.out.println(seller);
        return seller.getItems();
    }

    @Override
    public List<Item> viewAllBuyerItems() {
        Query q = em.createQuery("SELECT i from Item i");
        return q.getResultList();

    }

    @Override
    public void createOrder(Item item, long sellerID, long buyerID) {
        Seller seller = em.find(Seller.class, sellerID);
        Buyer buyer = em.find(Buyer.class, buyerID);
        ItemOrder order = new ItemOrder();
        em.persist(order);
//        order.setItem(item);
        buyer.getOrders().add(order);
        seller.getOrders().add(order);
        item.getOrders().add(order);
        item.setQuantity(item.getQuantity() - 1);
        em.merge(item);
        order.setItemId(item.getId());
    }

    @Override
    public List<Item> searchItemByKeyword(String keyword) {
        Query q = em.createQuery("SELECT i FROM Item i WHERE LOWER(i.name) LIKE :name");
        q.setParameter("name", "%" + keyword.toLowerCase() + "%");

        return q.getResultList();

    }

    @Override
    public List<Item> searchItemByCategory(String category) {
        Query q = em.createQuery("SELECT i FROM Item i WHERE LOWER(i.category) LIKE :category");
        q.setParameter("category", "%" + category.toLowerCase() + "%");

        return q.getResultList();
    }

    @Override
    public List<Item> searchItemByAvailability(long quantity) {
        Query q = em.createQuery("SELECT i FROM Item i "
                + "WHERE i.quantity >= :quantity");
        q.setParameter("quantity", quantity);

        return q.getResultList();
    }

    @Override
    public Seller findSellerByID(long sID) {
        Seller s = em.find(Seller.class, sID);
        return s;
    }

    @Override
    public Buyer findBuyerByID(long bID) {
        Buyer s = em.find(Buyer.class, bID);
        return s;
    }

    @Override
    public Item findItemByID(long iID) {
        Item i = em.find(Item.class, iID);
        return i;
    }

}
