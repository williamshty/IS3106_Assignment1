    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbean;

import entity.Buyer;
import entity.Cart;
import entity.Item;
import entity.ItemOrder;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import session.ECASessionBeanLocal;

/**
 *
 * @author tianyuan.shi
 */
@Named(value = "buyerManagedBean")
@SessionScoped
public class BuyerManagedBean implements Serializable {

    /**
     * @return the buyer
     */
    public Buyer getBuyer() {
        return buyer;
    }

    /**
     * @param buyer the buyer to set
     */
    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }
    private Buyer buyer;
    private Long id;
    private String username;
    private String password;
    private byte gender;
    private String name;
    private boolean status;
    private ArrayList<Item> items;
    private ArrayList<Item> cart;
    private ArrayList<ItemOrder> orders;
    private ItemOrder currentOrder;
    private String rating;
    private String review;
    private String searchKeyword;
    private String searchCategory;
    private long searchQuantity;
    @EJB
    private ECASessionBeanLocal ecaSessionBeanLocal;

    /**
     * Creates a new instance of BuyerManagedBean
     */
    @PostConstruct
    public void init() {
        loadItems();
//        loadOrders();
    }

    public void getBuyerByID() {
        this.setBuyer(getEcaSessionBeanLocal().getBuyerByID(getId()));
    }

    public String registerNewBuyer() {
        Buyer newBuyer = new Buyer();
        newBuyer.setGender(getGender());
        newBuyer.setName(getName());
        newBuyer.setUsername(getUsername());
        newBuyer.setPassword(getPassword());
        newBuyer.setStatus(true);
        Cart newCart = new Cart();
        newBuyer.setCart(newCart);
        newCart.setBuyer(newBuyer);

//        newCart = ecaSessionBeanLocal.createNewCart(newCart);
        newBuyer = getEcaSessionBeanLocal().registerBuyer(newBuyer);
//        newBuyer.setCart(newCart);
//        newCart.setBuyer(newBuyer);
        return "buyerLogin.xhtml";
    }

    public String login() {
        System.out.print(getUsername());
        System.out.print(getPassword());
        setBuyer(getEcaSessionBeanLocal().buyerLogin(getUsername(), getPassword()));
        return "buyerConsole.xhtml";
    }

    public void updateProfile() {
        Buyer buyer = getBuyer();
        buyer.setName(getName());
        buyer.setGender(getGender());
        getEcaSessionBeanLocal().updateBuyerProfile(buyer);
    }

    public void loadItems() {
        List<Item> vectorItems = getEcaSessionBeanLocal().viewAllBuyerItems();
        ArrayList<Item> arrayItems = new ArrayList<>();
        arrayItems.addAll(vectorItems);
        setItems(arrayItems);
    }
    
    public void loadKeywordSearchedItems(){
        System.out.print(getSearchKeyword());
        List<Item> vectorItems = getEcaSessionBeanLocal().searchItemByKeyword(searchKeyword);
        ArrayList<Item> arrayItems = new ArrayList<>();
        arrayItems.addAll(vectorItems);
        setItems(arrayItems);
        setSearchKeyword("");
    }
    public void loadCategorySearchedItems(){
        System.out.print(getSearchCategory());
        List<Item> vectorItems = getEcaSessionBeanLocal().searchItemByCategory(searchCategory);
        ArrayList<Item> arrayItems = new ArrayList<>();
        arrayItems.addAll(vectorItems);
        setItems(arrayItems);
        setSearchCategory("");
    }
    public void loadQuantitySearchedItems(){
        System.out.print(getSearchQuantity());
        List<Item> vectorItems = getEcaSessionBeanLocal().searchItemByAvailability(searchQuantity);
        ArrayList<Item> arrayItems = new ArrayList<>();
        arrayItems.addAll(vectorItems);
        setItems(arrayItems);
        setSearchQuantity(0);
    }

    public void loadOrders() {
        List<ItemOrder> vectorOrders = getEcaSessionBeanLocal().viewAllBuyerOrders(getBuyer().getId());
        ArrayList<ItemOrder> arrayOrders = new ArrayList<>();
        arrayOrders.addAll(vectorOrders);
        setOrders(arrayOrders);
    }

    public void addItem(Item item) {
        ArrayList<Item> newCart = getCart();
        if (newCart != null) {
            newCart.add(item);
            setCart(newCart);
            System.out.println(newCart);
        } else {
            newCart = new ArrayList<Item>();
            newCart.add(item);
            setCart(newCart);
            System.out.println(newCart);
        }
    }

    public String checkoutCart() {
        if (!cart.isEmpty()) {
            for (Item item : getCart()) {
                long sellerID = item.getSeller().getId();
                long buyerID = getBuyer().getId();
                getEcaSessionBeanLocal().createOrder(item, sellerID, buyerID);
            }
        }
        setCart(new ArrayList<>());
        return "buyerPayment.xhtml";
    }

    public String addReviewStart(ItemOrder order) {
        setCurrentOrder(order);
        System.out.print(getCurrentOrder());
        return "buyerReview.xhtml";
    }

    public String addReviewFinished() {
        System.out.println(getRating());
        System.out.println(getReview());
//        System.out.println(getCurrentOrder().getId());

        getEcaSessionBeanLocal().addFeedback(getRating(), getReview(), getCurrentOrder().getId());
        getCurrentOrder().setRating(getRating());
        getCurrentOrder().setReview(getReview());
        setRating("");
        setReview("");
        return "buyerOrders.xhtml";
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

    /**
     * @return the cart
     */
    public ArrayList<Item> getCart() {
        return cart;
    }

    /**
     * @param cart the cart to set
     */
    public void setCart(ArrayList<Item> cart) {
        this.cart = cart;
    }

    /**
     * @return the orders
     */
    public ArrayList<ItemOrder> getOrders() {
        return orders;
    }

    /**
     * @param orders the orders to set
     */
    public void setOrders(ArrayList<ItemOrder> orders) {
        this.orders = orders;
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
     * @return the currentOrder
     */
    public ItemOrder getCurrentOrder() {
        return currentOrder;
    }

    /**
     * @param currentOrder the currentOrder to set
     */
    public void setCurrentOrder(ItemOrder currentOrder) {
        this.currentOrder = currentOrder;
    }

    /**
     * @return the rating
     */
    public String getRating() {
        return rating;
    }

    /**
     * @param rating the rating to set
     */
    public void setRating(String rating) {
        this.rating = rating;
    }

    /**
     * @return the review
     */
    public String getReview() {
        return review;
    }

    /**
     * @param review the review to set
     */
    public void setReview(String review) {
        this.review = review;
    }

    public String logout() {
        setBuyer(null);
        setUsername("");
        setPassword("");
        return "buyerLogin.xhtml";
    }

    /**
     * @return the searchKeyword
     */
    public String getSearchKeyword() {
        return searchKeyword;
    }

    /**
     * @param searchKeyword the searchKeyword to set
     */
    public void setSearchKeyword(String searchKeyword) {
        this.searchKeyword = searchKeyword;
    }

    /**
     * @return the searchCategory
     */
    public String getSearchCategory() {
        return searchCategory;
    }

    /**
     * @param searchCategory the searchCategory to set
     */
    public void setSearchCategory(String searchCategory) {
        this.searchCategory = searchCategory;
    }

    /**
     * @return the searchQuantity
     */
    public long getSearchQuantity() {
        return searchQuantity;
    }

    /**
     * @param searchQuantity the searchQuantity to set
     */
    public void setSearchQuantity(long searchQuantity) {
        this.searchQuantity = searchQuantity;
    }

}
