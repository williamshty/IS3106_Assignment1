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
import javax.ejb.Local;

/**
 *
 * @author tianyuan.shi
 */
@Local
public interface ECASessionBeanLocal {

    void adminLogin(String username, String password);

    List<Seller> viewAllSellers();

    List<Buyer> viewAllBuyers();

    void deactivateSeller(long sellerID);

    void deactivateBuyer(long buyerID);

    void activateSeller(long sellerID);

    void activateBuyer(long buyerID);

    Seller registerSeller(Seller seller);

    Seller sellerLogin(String username, String password);

    void addItem(Item item, long sellerID);

    List<Item> viewSellerItems(long sellerID, String keyword);

    void deleteItem(long itemID);

    void editItem(Item item);

    List<ItemOrder> viewAllSellerOrders(long sellerID);

    void updateOrderStatus(long orderID, String status);

    void updateSellerProfile(Seller seller);

    Buyer registerBuyer(Buyer buyer);

    Buyer buyerLogin(String username, String password);

    void addItemToCart(long cartID, Item item);

    void checkOutCart(long cartID);

    List<ItemOrder> viewAllBuyerOrders(long buyerID);

    void addFeedback(String rating, String review, long orderID);
    
    void updateBuyerProfile(Buyer buyer);

    Buyer getBuyerByID(long buyerID);

    Seller getSellerByID(long sellerID);

    void updateCart(Cart cart);

    ArrayList<Item> viewAllSellerItems(long sellerID);

    List<Item> viewAllBuyerItems();

    void createOrder(Item item, long sellerID, long buyerID);

    List<Item> searchItemByKeyword(String keyword);

    List<Item> searchItemByCategory(String category);

    List<Item> searchItemByAvailability(long quantity);
}
