import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private List<FoodItem> items;

    public Menu(List<FoodItem> items) {
        this.items = items;
    }

    public void displayMenu() {
        System.out.println("\nMenu:");
        for (int i = 0; i < items.size(); i++) {
            FoodItem item = items.get(i);
            System.out.println((i + 1) + ". " + item.getFoodInfo());
        }
    }

    public void addItem(FoodItem item) {
        CSVOperations.addItemToMenu(item, "./data/menu.csv");
        items.add(item);
    }

    public void removeItem(String itemName) {
        CSVOperations.removeItemFromMenu(itemName, "./data/menu.csv");
        
        for (int i = 0; i < items.size(); i++) {
            System.out.println(items.get(i).getName());
            if (items.get(i).getName().equals(itemName)) {
                System.out.println("Item removed.");
                items.remove(i);
                break;
            }
        }
    }

    public void updateItem(String itemName) {
        boolean found = false;
        List<String[]> lines = CSVOperations.getFileLines("./data/menu.csv");

        Scanner scanner = new Scanner(System.in);

        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getName().equals(itemName)) {
                found = true;

                System.out.println("Enter new name: (type . to keep the same name)");
                String name = scanner.nextLine();
                // scanner.nextLine();
                if (!name.equals("."))
                    items.get(i).setName(name);

                System.out.println("Enter new price: (type -1 to keep the same price)");
                double price = Double.parseDouble(scanner.nextLine());
                if (price != -1)
                    items.get(i).setPrice(price);

                System.out.println("Enter new description: (type . to keep the same description)");
                String description = scanner.nextLine();
                // scanner.nextLine();
                if (!description.equals("."))
                    items.get(i).setDescription(description);

                System.out.println("Is item available? (true/false): ");
                boolean isAvailable = Boolean.parseBoolean(scanner.nextLine());

                if (isAvailable != items.get(i).isAvailable())
                    items.get(i).setAvailability(isAvailable);

                break;
            }
        }

        if (!found) {
            System.out.println("Item not found.");
        }

        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i)[1].trim().equals(itemName)) {
                lines.get(i)[1] = items.get(i).getName();
                lines.get(i)[2] = String.valueOf(items.get(i).getPrice());
                lines.get(i)[3] = items.get(i).getDescription();
                lines.get(i)[4] = String.valueOf(items.get(i).isAvailable());
            }
        }

        CSVOperations.writeMenu( "./data/menu.csv", lines);

        // scanner.close();
    }
}
