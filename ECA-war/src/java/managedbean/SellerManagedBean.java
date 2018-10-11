/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbean;

import entity.Item;
import entity.ItemOrder;
import entity.SaleOrder;
import entity.Seller;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import session.ECASessionBeanLocal;

/**
 *
 * @author tianyuan.shi
 */
@Named(value = "sellerManagedBean")
@SessionScoped
public class SellerManagedBean implements Serializable {

    private Seller seller;
    private Long id;
    private String username;
    private String password;
    private byte gender;
    private String name;
    private boolean status;
    private ArrayList<Item> items;
    private String itemName;
    private String itemDescription;
    private String itemCategory;
    private float itemPrice;
    private long itemQuantity;
    private String searchKeyword;
    private Item itemEdit;
    private ArrayList<ItemOrder> orders;
    private ItemOrder currentOrder;
    private String orderStatus;
    @EJB
    private ECASessionBeanLocal ecaSessionBeanLocal;

    /**
     * Creates a new instance of SellerManagedBean
     */
    @PostConstruct
    public void init() {
        if (getSeller() != null) {
            loadItems();
        }
    }

    public String registerNewSeller() {
        Seller newSeller = new Seller();
        newSeller.setGender(getGender());
        newSeller.setName(getName());
        newSeller.setUsername(getUsername());
        newSeller.setPassword(getPassword());
        newSeller.setStatus(true);
        newSeller = getEcaSessionBeanLocal().registerSeller(newSeller);
        setSeller(newSeller);
        loadItems();
        return "sellerLogin.xhtml";
//        newSeller.setCart(newCart);
//        newCart.setSeller(newSeller);
    }

    public String login() {
        System.out.print(getUsername());
        System.out.print(getPassword());
        setSeller(getEcaSessionBeanLocal().sellerLogin(getUsername(), getPassword()));
        loadItems();
        return "sellerConsole.xhtml";
    }

    public void updateProfile() {
        Seller seller = getSeller();
        seller.setName(getName());
        seller.setGender(getGender());
        getEcaSessionBeanLocal().updateSellerProfile(seller);
    }

    public void addItem() {
        Item item = new Item();
        item.setName(getItemName());
        item.setDescription(getItemDescription());
        item.setCategory(getItemCategory());
        item.setPrice(getItemPrice());
        item.setQuantity(getItemQuantity());
        getEcaSessionBeanLocal().addItem(item, getSeller().getId());
    }

    public void loadItems() {
        setItems(getEcaSessionBeanLocal().viewAllSellerItems(getSeller().getId()));
    }

    public void loadSearchedItems() {
//        setItems(ecaSessionBeanLocal.viewSellerItems(getSeller().getId(), getSearchKeyword()));
        List<Item> vectorItems = getEcaSessionBeanLocal().viewSellerItems(getSeller().getId(), getSearchKeyword());
        ArrayList<Item> arrayItems = new ArrayList<Item>();
        arrayItems.addAll(vectorItems);
        setItems(arrayItems);
    }

    public String editItem(Item item) {
        setItemEdit(item);
        setItemName(item.getName());
        setItemDescription(item.getDescription());
        setItemCategory(item.getCategory());
        setItemPrice(item.getPrice());
        setItemQuantity(item.getQuantity());
        return "sellerEditItem.xhtml";
    }

    public String updateItem() {
        Item item = getItemEdit();
        item.setName(getItemName());
        item.setDescription(getItemDescription());
        item.setCategory(getItemCategory());
        item.setPrice(getItemPrice());
        item.setQuantity(getItemQuantity());
        setItemEdit(item);
        setItemName("");
        setItemDescription("");
        setItemCategory("");
        setItemPrice(0);
        setItemQuantity(0);
        getEcaSessionBeanLocal().editItem(item);
        return "sellerConsole.xhtml";
    }

    public void loadOrders() {
        List<ItemOrder> vectorOrders = getEcaSessionBeanLocal().viewAllSellerOrders(getSeller().getId());
        ArrayList<ItemOrder> arrayOrders = new ArrayList<>();
        arrayOrders.addAll(vectorOrders);
        setOrders(arrayOrders);
    }
    
    public String updateStatusStart(ItemOrder order){
        setCurrentOrder(order);
        return "sellerStatus.xhtml";
    }
    
    public String updateStatusFinished(){
        getEcaSessionBeanLocal().updateOrderStatus(currentOrder.getId(), orderStatus);
        loadOrders();
        setCurrentOrder(null);
        setOrderStatus("");
        return "sellerOrders.xhtml";
    }

    /**
     * @return the seller
     */
    public Seller getSeller() {
        return seller;
    }

    /**
     * @param seller the seller to set
     */
    public void setSeller(Seller seller) {
        this.seller = seller;
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
     * @return the itemName
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * @param itemName the itemName to set
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * @return the itemDescription
     */
    public String getItemDescription() {
        return itemDescription;
    }

    /**
     * @param itemDescription the itemDescription to set
     */
    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    /**
     * @return the itemCategory
     */
    public String getItemCategory() {
        return itemCategory;
    }

    /**
     * @param itemCategory the itemCategory to set
     */
    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    /**
     * @return the itemPrice
     */
    public float getItemPrice() {
        return itemPrice;
    }

    /**
     * @param itemPrice the itemPrice to set
     */
    public void setItemPrice(float itemPrice) {
        this.itemPrice = itemPrice;
    }

    /**
     * @return the itemQuantity
     */
    public long getItemQuantity() {
        return itemQuantity;
    }

    /**
     * @param itemQuantity the itemQuantity to set
     */
    public void setItemQuantity(long itemQuantity) {
        this.itemQuantity = itemQuantity;
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
     * @return the itemEdit
     */
    public Item getItemEdit() {
        return itemEdit;
    }

    /**
     * @param itemEdit the itemEdit to set
     */
    public void setItemEdit(Item itemEdit) {
        this.itemEdit = itemEdit;
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
     * @return the orderStatus
     */
    public String getOrderStatus() {
        return orderStatus;
    }

    /**
     * @param orderStatus the orderStatus to set
     */
    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
    public String logout() {
        setSeller(null);
        setUsername("");
        setPassword("");
        return "sellerLogin.xhtml";
    }
}
