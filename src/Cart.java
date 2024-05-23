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


    public double getTotalPrice() {
        double total = 0;
        for (CartItem item : items) {
            total += item.getTotalPrice();
        }
        return total;
    }

    // Please, explain it
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


    public void addToCart(Menu menu, String item_name, int quantity,  Restaurant restaurant) {
        boolean itemFound = false;

        for (CartItem item : this.getItems()) {
            if (item.getName().equals(item_name) && item.getRestaurantId() == restaurant.getId()){
                item.setQuantity(item.getQuantity() + quantity);
                CSVOperations.updateCartItemInFile(item, "./data/cartItem.csv");
                System.out.println("Item added to cart.");
                return;
            }
        }

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
