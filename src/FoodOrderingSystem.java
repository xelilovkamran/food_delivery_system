import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


class Order {
    private List<FoodItem> items;

    public Order() {
        items = new ArrayList<>();
    }

    public void addItem(FoodItem item) {
        items.add(item);
    }

    public List<FoodItem> getItems() {
        return items;
    }
}

class FoodOrderingSystem {
    private static final int MAX_ITEMS = 100;
    private FoodItem[] menu;
    private int itemCount;
    private FoodItem[] cart;
    private int cartSize;
    private double balance;
    private List<Order> orderHistory;

    public FoodOrderingSystem() {
        menu = new FoodItem[MAX_ITEMS];
        itemCount = 0;
        cart = new FoodItem[MAX_ITEMS];
        cartSize = 0;
        balance = 100.0; // Initial balance
        orderHistory = new ArrayList<>();
    }

    public void addItemToMenu(String name, double price) {
        if (itemCount < MAX_ITEMS) {
            menu[itemCount] = new FoodItem(name, price);
            itemCount++;
            System.out.println("Item added successfully.");
        } else {
            System.out.println("Menu is full. Cannot add more items.");
        }
    }

    public void displayMenu() {
        System.out.println("Menu:");
        for (int i = 0; i < itemCount; i++) {
            System.out.println((i + 1) + ". " + menu[i].getName() + " - $" + menu[i].getPrice());
        }
    }

    public void addToCart(int itemIndex) {
        if (cartSize < MAX_ITEMS && itemIndex >= 0 && itemIndex < itemCount) {
            cart[cartSize] = menu[itemIndex];
            cartSize++;
            System.out.println("Item added to cart.");
        } else {
            System.out.println("Invalid item selection.");
        }
    }

    public void removeFromCart(int itemIndex) {
        if (itemIndex >= 0 && itemIndex < cartSize) {
            for (int i = itemIndex; i < cartSize - 1; i++) {
                cart[i] = cart[i + 1];
            }
            cartSize--;
            System.out.println("Item removed from cart.");
        } else {
            System.out.println("Invalid item selection.");
        }
    }

    public void displayCart() {
        System.out.println("Cart:");
        double total = 0;
        for (int i = 0; i < cartSize; i++) {
            System.out.println((i + 1) + ". " + cart[i].getName() + " - $" + cart[i].getPrice());
            total += cart[i].getPrice();
        }
        System.out.println("Total: $" + total);
    }

    public void checkBalance() {
        System.out.println("Your balance: $" + balance);
    }

    public void rechargeBalance(double amount) {
        balance += amount;
        System.out.println("Balance recharged successfully. Your new balance is: $" + balance);
    }

    public void placeOrder() {
        if (cartSize == 0) {
            System.out.println("Your cart is empty. Add items to cart before placing an order.");
            return;
        }

        double total = 0;
        for (int i = 0; i < cartSize; i++) {
            total += cart[i].getPrice();
        }
        if (total > balance) {
            System.out.println("Insufficient balance. Please remove some items from cart or recharge your balance.");
        } else {
            balance -= total;
            Order order = new Order();
            for (FoodItem item : cart) {
                order.addItem(item);
            }
            orderHistory.add(order);
            System.out.println("Order placed successfully!");
            cart = new FoodItem[MAX_ITEMS]; // Empty the cart after placing order
            cartSize = 0;
        }
    }

    public void viewOrderHistory() {
        System.out.println("Order History:");
        for (int i = 0; i < orderHistory.size(); i++) {
            Order order = orderHistory.get(i);
            List<FoodItem> items = order.getItems();
            System.out.println("Order " + (i + 1) + ":");
            for (FoodItem item : items) {
                System.out.println("- " + item.getName() + " - $" + item.getPrice());
            }
        }
    }

    public static void main(String[] args) {
        FoodOrderingSystem system = new FoodOrderingSystem();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Online Food Delivery System");

        int userType;
        do {
            System.out.println("\nSelect User Type:");
            System.out.println("1. Customer");
            System.out.println("2. Manager");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            userType = scanner.nextInt();

            switch (userType) {
                case 1:
                    customerActions(system, scanner);
                    break;
                case 2:
                    managerActions(system, scanner);
                    break;
                case 3:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 3.");
            }
        } while (userType != 3);

        scanner.close();
    }

    public static void customerActions(FoodOrderingSystem system, Scanner scanner) {
        int choice;
        do {
            System.out.println("\nCustomer Actions:");
            System.out.println("1. Display Menu");
            System.out.println("2. Add Item to Cart");
            System.out.println("3. Remove Item from Cart");
            System.out.println("4. View Cart");
            System.out.println("5. Check Balance");
            System.out.println("6. Recharge Balance");
            System.out.println("7. Place Order");
            System.out.println("8. View Order History");
            System.out.println("9. Back to User Type Selection");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    system.displayMenu();
                    break;
                case 2:
                    System.out.print("Enter item number to add to cart: ");
                    int itemIndex = scanner.nextInt() - 1;
                    system.addToCart(itemIndex);
                    break;
                case 3:
                    System.out.print("Enter item number to remove from cart: ");
                    int removeIndex = scanner.nextInt() - 1;
                    system.removeFromCart(removeIndex);
                    break;
                case 4:
                    system.displayCart();
                    break;
                case 5:
                    system.checkBalance();
                    break;
                case 6:
                    System.out.print("Enter amount to recharge: $");
                    double amount = scanner.nextDouble();
                    system.rechargeBalance(amount);
                    break;
                case 7:
                    system.placeOrder();
                    break;
                case 8:
                    system.viewOrderHistory();
                    break;
                case 9:
                    System.out.println("Returning to user type selection...");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 9.");
            }
        } while (choice != 9);
    }

    public static void managerActions(FoodOrderingSystem system, Scanner scanner) {
        int choice;
        do {
            System.out.println("\nManager Actions:");
            System.out.println("1. Add Item to Menu");
            System.out.println("2. Display Menu");
            System.out.println("3. Back to User Type Selection");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter item name: ");
                    String name = scanner.next();
                    System.out.print("Enter item price: ");
                    double price = scanner.nextDouble();
                    system.addItemToMenu(name, price);
                    break;
                case 2:
                    system.displayMenu();
                    break;
                case 3:
                    System.out.println("Returning to user type selection...");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 3.");
            }
        } while (choice != 3);
    }
}
