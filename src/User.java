
public class User extends BaseUser {
    private double balance;

    public User(Integer id, String name, String email, String password, boolean is_superuser, double balance) {
        super(id, name, email, password, is_superuser);
        this.balance = balance;
    }
    
    public void addToCart(Cart cart, Menu menu, String item_name, int quantity,  Restaurant restaurant) {
        boolean itemFound = false;

        for (CartItem item : cart.getItems()) {
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
                CartItem cartItem = new CartItem(item.getName(), quantity, item.getPrice(), restaurant.getId(), cart.getId());
                cart.addItem(cartItem);
                CSVOperations.addCartItemToFile(cartItem, "./data/cartItem.csv");
                System.out.println("Item added to cart.");
                return;
            }
        }

        if (!itemFound) {
            System.out.println("Item not found or unavailable. Please try again.");
        }
        
    }
}


