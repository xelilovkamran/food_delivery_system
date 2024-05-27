public class CartItem {
    private String name;
    private int quantity;
    private double price;
    private Integer restaurant_id;
    private Integer cart_id;

    public CartItem(String name, int quantity, double price, Integer restaurant_id, Integer cart_id) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.restaurant_id = restaurant_id;
        this.cart_id = cart_id;
    }

    public double getTotalPrice() {
        return quantity * price;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public Integer getRestaurantId() {
        return restaurant_id;
    }

    public Integer getCartId() {
        return cart_id;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * @return String
     * 
     * This method returns the information about the cart item
     * in the format "name x quantity @ price = total price"
     */
    public String toString() {
        return name + " x " + quantity + " @ $" + price + " = $" + getTotalPrice();
    }
}