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

    void registerSeller(String username, String password);

    void sellerLogin(String username, String password);

    void addItem(Item item, long sellerID);

}
