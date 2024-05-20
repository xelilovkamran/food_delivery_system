import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Admin extends BaseUser {
    public Admin(Integer id, String name, String email, String password, boolean is_superuser) {
        super(id, name, email, password, is_superuser);
    }

    // public void displayMenu() {
    //     List<String[]> menuFromCSV = CSVOperations.loadMenu("data/menu.csv");
    //     System.out.println("Menu:");
    //     for (String[] item : menuFromCSV) {
    //         System.err.println(item[4].trim());
    //         System.out.println(item[0] + ". " + item[1] + " - $" + item[2] + " - " + item[3] + " - " + (item[4].trim().equals("true") ? "Available" : "Not Available"));
    //     }
    // }
    
    // public void addItemToMenu(String name) {
    //     if (CSVOperations.itemIsAvailableInMenu("data/menu.csv", name)) {
    //         System.out.println("Item already exists in the menu.");
    //         return;
    //     }

    //     Scanner scanner = new Scanner(System.in);

    //     System.out.print("Enter item price: ");
    //     double price = Double.parseDouble(scanner.next());
    //     scanner.nextLine(); // Consume newline
    //     System.out.print("Enter item description: ");
    //     String description = scanner.nextLine();
    //     description = "\"" + description + "\"";
        
    //     List<String[]> menuFromCSV = CSVOperations.loadMenu("data/menu.csv");
    //     Integer id = Integer.parseInt(menuFromCSV.get(menuFromCSV.size() - 1)[0]);
    //     String newLine = (id + 1) + ", " + name + ", " + price + ", " + description + ", true";
    //     CSVOperations.AddLineToFile("data/menu.csv", newLine);
    //     System.out.println("Item added successfully.");
    // }

    // public void updateItemInMenu(String itemName) {
    //     if (!CSVOperations.itemIsAvailableInMenu("data/menu.csv", itemName)) {
    //         System.out.println("Item not found in the menu.");
    //         return;
    //     }

    //     Scanner scanner = new Scanner(System.in);
    //     List<String[]> menuFromCSV = CSVOperations.loadMenu("data/menu.csv");
    //     List<String[]> newMenu = new ArrayList<>();
        
    //     System.out.print("Change price (press '.' to pass): ");
    //     String price = scanner.next();
    //     scanner.nextLine(); // Consume newline
    //     System.out.print("Change description (press '.' to pass): ");
    //     String description = scanner.nextLine();
    //     System.out.print("Change availability (use 'true' and 'false' keywords, press '.' to pass): ");
    //     String isAvailable = scanner.nextLine();

    //     for (String[] item : menuFromCSV) {
    //         if (item[1].trim().toLowerCase().equals(itemName.trim().toLowerCase())) {
    //             if (!price.equals(".")) {
    //                 item[2] = Double.parseDouble(price) + "";
    //             }
    //             if (!description.equals(".")) {
    //                 item[3] = "\""  + description + "\"";
    //             }
    //             if (!isAvailable.equals(".")) {
    //                 item[4] = isAvailable;
    //             }
    //         }
    //         newMenu.add(item);
    //     }
    //     CSVOperations.writeMenu("data/menu.csv", newMenu);
    //     System.out.println("Updating item in menu...");

    // }

    // public void removeItemFromMenu(String name) {
    //     if (!CSVOperations.itemIsAvailableInMenu("data/menu.csv", name)) {
    //         System.out.println("Item not found in the menu.");
    //         return;
    //     }
    //     List<String[]> menuFromCSV = CSVOperations.loadMenu("data/menu.csv");
    //     List<String[]> newMenu = new ArrayList<>();
        
    //     for (String[] item : menuFromCSV) {
    //         if (item[1].trim().toLowerCase().equals(name.trim().toLowerCase())) {
    //             continue;
    //         }
    //         newMenu.add(item);
    //     }

    //     CSVOperations.writeMenu("data/menu.csv", newMenu);
    //     System.out.println("Removing item from menu...");
    // }
}
