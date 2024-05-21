import java.util.List;
import java.util.Scanner;

class FoodOrderingSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        System.out.println("Welcome to Online Food Delivery System");

        do {
            System.out.println("\n1. Sign In");
            System.out.println("2. Sign Up");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter email: ");
                    String email = scanner.next();
                    System.out.print("Enter password: ");
                    String password = scanner.next();

                    if (Validation.isCustomer(email, password)) {
                        customerActions( scanner, email, password);
                    } else if (Validation.isAdmin(email, password)) {
                        managerActions( scanner, email, password);
                    } else {
                        System.out.println("You are not authorized to access this feature.");
                    }
                    break;
                case 2:
                    System.out.print("Enter email address: ");
                    email = scanner.next();
                    System.out.print("Enter name: ");
                    String name = scanner.next();
                    System.out.print("Enter password: ");
                    password = scanner.next();
                    System.out.print("Repeat password: ");
                    String repeatedPassword = scanner.next();

                    if (password.equals(repeatedPassword)) {
                        List<String[]> userLines = CSVOperations.getFileLines("data/users.csv");
                        if (!CSVOperations.userIsAvailableInUsers(userLines, email)) {
                            userLines.add(new String[] { Integer.toString(userLines.size() + 1), name, email, password, "0", "false" });
                            CSVOperations.writeUsers("data/users.csv", userLines);
                            customerActions( scanner, email, password);
                        } else {
                            System.out.println("Email already exists.");
                        }
                    } else {
                        System.out.println("Passwords do not match.");
                    }
                    
                    break;
                case 3:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 3.");
            }
        } while (choice != 3);


        

        scanner.close();
    }

    public static void customerActions(Scanner scanner, String email, String password) {
        // int choice;
        User user = CSVOperations.getUser(email, password);
        System.out.println(user.getName());
        // do {
        //     System.out.println("\nCustomer Actions:");
        //     System.out.println("1. Display Menu");
        //     System.out.println("2. Add Item to Cart");
        //     System.out.println("3. Remove Item from Cart");
        //     System.out.println("4. View Cart");
        //     System.out.println("5. Check Balance");
        //     System.out.println("6. Recharge Balance");
        //     System.out.println("7. Place Order");
        //     System.out.println("8. View Order History");
        //     System.out.println("9. Back to User Type Selection");
        //     System.out.print("Enter your choice: ");
        //     choice = scanner.nextInt();

        //     switch (choice) {
        //         case 1:
        //             user.displayMenu();
        //             break;
        //         case 2:
        //             System.out.print("Enter item number to add to cart: ");
        //             int itemIndex = scanner.nextInt() - 1;
        //             user.addToCart(itemIndex);
        //             break;
        //         case 3:
        //             System.out.print("Enter item number to remove from cart: ");
        //             int removeIndex = scanner.nextInt() - 1;
        //             user.removeFromCart(removeIndex);
        //             break;
        //         case 4:
        //             user.displayCart();
        //             break;
        //         case 5:
        //             user.checkBalance();
        //             break;
        //         case 6:
        //             System.out.print("Enter amount to recharge: $");
        //             double amount = scanner.nextDouble();
        //             user.rechargeBalance(amount);
        //             break;
        //         case 7:
        //             user.placeOrder();
        //             break;
        //         case 8:
        //             user.viewOrderHistory();
        //             break;
        //         case 9:
        //             System.out.println("Returning to user type selection...");
        //             break;
        //         default:
        //             System.out.println("Invalid choice. Please enter a number between 1 and 9.");
        //     }
        // } while (choice != 9);
    }

    public static void managerActions(Scanner scanner, String email, String password) {
        int choice;
        Admin admin = CSVOperations.getAdmin(email, password);
        Menu menu = CSVOperations.loadMenu("data/menu.csv");

        do {
            System.out.println("\nManager Actions:");
            System.out.println("********************* Account Actions *********************");
            System.out.println("1. Display Account Information");
            System.out.println("2. Change Password");
            System.out.println("3. Change Email");
            System.out.println("********************* Store Actions *********************");
            System.out.println("4. Display Menu");
            System.out.println("5. Add Item to Menu");
            System.out.println("6. Remove Item from Menu");
            System.out.println("7. Update Item in Menu");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println(admin.getUserInfo());
                    break;
                case 2:
                    System.out.print("Enter new password: ");
                    String newPassword = scanner.next();
                    admin.setPassword(newPassword);
                    break;
                case 3:
                    System.out.print("Enter new email: ");
                    String newEmail = scanner.next();
                    admin.setEmail(newEmail);
                    break;
                case 4:
                    menu.displayMenu();
                    break;
                case 5:
                    String name, description;
                    double price;
                    boolean isAvailable;

                    System.out.print("Enter item name: ");
                    name = scanner.next();
                    scanner.nextLine();

                    System.out.println("Enter item price: ");
                    price = Double.parseDouble(scanner.nextLine());

                    System.out.println("Enter item description: ");
                    description = scanner.nextLine();

                    System.out.println("Is item available? (true/false): ");
                    isAvailable = Boolean.parseBoolean(scanner.nextLine());

                    FoodItem item = new FoodItem(name, price, description, isAvailable);
                    menu.addItem(item);
                    break;
                case 6:
                    System.out.print("Enter item name to remove: ");
                    String itemName = scanner.next();
                    System.out.println(itemName);
                    menu.removeItem(itemName);
                    break;
                case 7:
                    System.out.print("Enter item name to update: ");
                    itemName = scanner.next();
                    menu.updateItem(itemName);
                    break;
                case 8:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 3.");
            }
        } while (choice != 8);
    }
}
