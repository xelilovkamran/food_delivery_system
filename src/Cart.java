import java.util.List;

public class Cart {
    private List<CartItem> items;
    private int id;

    public Cart(List<CartItem> items, int id) {
        this.items = items;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void addItem(CartItem item) {
        items.add(item);
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }


    /**
     * @return double
     * 
     * This method calculates the total price of all items in the cart
     * by iterating over the items and summing the total price of each item
     */
    public double getTotalPrice() {
        double total = 0;
        for (CartItem item : items) {
            total += item.getTotalPrice();
        }
        return total;
    }

    /**
     * @return String
     * 
     * This method returns the information about the cart
     * in the format "1. item1 x quantity1 @ price1 = total price1"
     * It iterates over the items and appends the information about each item to the StringBuilder
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int index = 1;
        for (CartItem item : items) {
            sb.append(index++ + ". " + item.toString());
            sb.append("\n");
        }
        sb.append("Total: $" + getTotalPrice());
        return sb.toString();
    }


    /**
     * @param menu the menu object
     * @param item_name the name of the item
     * @param quantity the quantity of the item
     * @param restaurant the restaurant object
     * @return void
     * 
     * This method adds an item to the cart by checking if the item is already in the cart and updating the quantity if it is,
     * or by checking if the item is in the menu and adding it to the cart if it is not already there
     */
    public void addToCart(Menu menu, String item_name, int quantity,  Restaurant restaurant) {
        boolean itemFound = false;

        // * Check if the item is already in the cart and update the quantity
        for (CartItem item : this.getItems()) {
            if (item.getName().equals(item_name) && item.getRestaurantId() == restaurant.getId()){
                item.setQuantity(item.getQuantity() + quantity);
                CSVOperations.updateCartItemInFile(item, "./data/cartItem.csv");
                System.out.println("Item added to cart.");
                return;
            }
        }

        // * Check if the item is in the menu and add it to the cart if it is not already there
        for (FoodItem item : menu.getItems()) {
            if (item.getName().equals(item_name) && item.isAvailable() && item.getRestaurantId() == restaurant.getId()){
                itemFound = true;
                CartItem cartItem = new CartItem(item.getName(), quantity, item.getPrice(), restaurant.getId(), this.getId());
                this.addItem(cartItem);
                CSVOperations.addCartItemToFile(cartItem, "./data/cartItem.csv");
                System.out.println("Item added to cart.");
                return;
            }
        }

        if (!itemFound) {
            System.out.println("Item not found or unavailable. Please try again.");
        }
    }

    /**
     * @param itemIndex the index of the item to remove
     * @param quantityToRemove the quantity to remove
     * @return void
     * 
     * This method removes an item from the cart by checking if the item is in the cart and updating the quantity if it is,
     * or by removing the item from the cart if the quantity to remove is equal to the quantity of the item
     */
    public void removeItem(int itemIndex, int quantityToRemove) {
        System.out.println(this.getItems().size());
        if (itemIndex > this.getItems().size() || itemIndex < 1) {
            System.out.println("Invalid item index.");
            return;
        }

        if (this.getItems().get(itemIndex - 1).getQuantity() < quantityToRemove) {
            System.out.println("Invalid quantity.");
            return;
        }

        if (this.getItems().get(itemIndex - 1).getQuantity() == quantityToRemove) {
            CSVOperations.deleteCartItemFromFile(this.getItems().remove(itemIndex - 1), "./data/cartItem.csv");
            // this.getItems().remove(itemIndex - 1);
        } else {
            this.getItems().get(itemIndex - 1).setQuantity(this.getItems().get(itemIndex - 1).getQuantity() - quantityToRemove);
            CSVOperations.updateCartItemInFile(this.getItems().get(itemIndex - 1), "./data/cartItem.csv");
        }

        System.out.println(this.getItems().size());
    }
}
