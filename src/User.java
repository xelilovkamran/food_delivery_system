import java.util.ArrayList;
import java.util.List;


public class User extends BaseUser {
    private double balance;

    public User(Integer id, String name, String email, String password, boolean is_superuser, double balance) {
        super(id, name, email, password, is_superuser);
        this.balance = balance;
    }
    
    // public void displayMenu() {
    //     List<String[]> menuFromCSV = CSVOperations.loadMenu("data/menu.csv");
    //     System.out.println("Menu:");
    //     for (String[] item : menuFromCSV) {
    //         System.err.println(item[4].trim());
    //         System.out.println(item[0] + ". " + item[1] + " - $" + item[2] + " - " + item[3] + " - " + (item[4].trim().equals("true") ? "Available" : "Not Available"));
    //     }
    // }

    // public void addToCart(int itemIndex) {
    //     if (cartSize < MAX_ITEMS && itemIndex >= 0 && itemIndex < itemCount) {
    //         cart[cartSize] = menu[itemIndex];
    //         cartSize++;
    //         System.out.println("Item added to cart.");
    //     } else {
    //         System.out.println("Invalid item selection.");
    //     }
    // }

    // public void removeFromCart(int itemIndex) {
    //     if (itemIndex >= 0 && itemIndex < cartSize) {
    //         for (int i = itemIndex; i < cartSize - 1; i++) {
    //             cart[i] = cart[i + 1];
    //         }
    //         cartSize--;
    //         System.out.println("Item removed from cart.");
    //     } else {
    //         System.out.println("Invalid item selection.");
    //     }
    // }

    // public void displayCart() {
    //     System.out.println("Cart:");
    //     double total = 0;
    //     for (int i = 0; i < cartSize; i++) {
    //         System.out.println((i + 1) + ". " + cart[i].getName() + " - $" + cart[i].getPrice());
    //         total += cart[i].getPrice();
    //     }
    //     System.out.println("Total: $" + total);
    // }

    // public void checkBalance() {
    //     System.out.println("Your balance: $" + balance);
    // }

    // public void rechargeBalance(double amount) {
    //     balance += amount;
    //     System.out.println("Balance recharged successfully. Your new balance is: $" + balance);
    // }

    // public void placeOrder() {
    //     if (cartSize == 0) {
    //         System.out.println("Your cart is empty. Add items to cart before placing an order.");
    //         return;
    //     }

    //     double total = 0;
    //     for (int i = 0; i < cartSize; i++) {
    //         total += cart[i].getPrice();
    //     }
    //     if (total > balance) {
    //         System.out.println("Insufficient balance. Please remove some items from cart or recharge your balance.");
    //     } else {
    //         balance -= total;
    //         Order order = new Order();
    //         for (FoodItem item : cart) {
    //             order.addItem(item);
    //         }
    //         orderHistory.add(order);
    //         System.out.println("Order placed successfully!");
    //         cart = new FoodItem[MAX_ITEMS]; // Empty the cart after placing order
    //         cartSize = 0;
    //     }
    // }

    // public void viewOrderHistory() {
    //     System.out.println("Order History:");
    //     for (int i = 0; i < orderHistory.size(); i++) {
    //         Order order = orderHistory.get(i);
    //         List<FoodItem> items = order.getItems();
    //         System.out.println("Order " + (i + 1) + ":");
    //         for (FoodItem item : items) {
    //             System.out.println("- " + item.getName() + " - $" + item.getPrice());
    //         }
    //     }
    // }
}


