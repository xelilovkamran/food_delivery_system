import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;    

public class CSVOperations {
    public static List<User> loadUsers(String path) {
        List<User> users = new ArrayList<>();
        List<String[]> lines = new ArrayList<>();
        lines = getFileLines(path);

        for (String[] user : lines) {
            Integer id = Integer.parseInt(user[0].trim());
            String name = user[1].trim();
            String email = user[2].trim();
            String password = user[3].trim();
            double balance = Double.parseDouble(user[4].trim());
            boolean is_superuser = user[5].trim().equals("true");
            User newUser = new User(id, name, email, password, is_superuser, balance);
            users.add(newUser);
        }

        return users;
    }

    public static User getUser(String email, String password) {
        List<User> users = loadUsers("data/users.csv");

        for (User user : users) {
            if (Validation.Authenticated(email, password, user)) {
                return user;
            }
        }

        return null;
    }

    public static Admin getAdmin(String email, String password) {
        List<User> users = loadUsers("data/users.csv");

        for (User user : users) {
            if (Validation.Authenticated(email, password, user)) {
                Integer id = user.getId();
                String name = user.getName();
                boolean is_superuser = user.isSuperuser();
                Admin newAdmin = new Admin(id, name, email, password, is_superuser);
                return newAdmin;
            }
        }

        return null;
    }

    public static boolean updatePassword(String path, String email, String newPassword) {
        List<String[]> userLines = new ArrayList<>();
        boolean updated = false;

        userLines = getFileLines(path);

        for (String[] userLine : userLines) {
            if (userLine[2].trim().equals(email)) {
                userLine[3] = newPassword;
                updated = true;
                break;
            }
        }

        CSVOperations.writeUsers(path, userLines);
        return updated;
    }

    public static boolean updateEmail(String path, String email, String newEmail) {
        List<String[]> userLines = new ArrayList<>();
        boolean updated = false;

        userLines = getFileLines(path);

        if (userIsAvailableInUsers(userLines, newEmail)) {
            System.out.println("Email already exists.");
            return updated;
        }

        for (String[] userLine : userLines) {
            if (userLine[2].trim().equals(email)) {
                userLine[2] = newEmail;
                updated = true;
                break;
            }
        }

        CSVOperations.writeUsers(path, userLines);
        return updated;
    }

    public static boolean userIsAvailableInUsers(List<String[]> userLines, String email) {
        for (String[] userLine : userLines) {
            if (userLine[2].trim().equals(email)) {
                return true;
            }
        }

        return false;
    }

    public static Menu loadMenu(String path) {
        List<FoodItem> items = new ArrayList<>();
        List<String[]> lines = new ArrayList<>();
        
        lines = getFileLines(path);


        for (String[] item : lines) {
            String name = item[1].trim();
            double price = Double.parseDouble(item[2].trim());
            String description = item[3].trim();
            boolean is_available = item[4].trim().equals("true");
            int restaurant_id = Integer.parseInt(item[5].trim());
            FoodItem newItem = new FoodItem(name, price, description, is_available, restaurant_id);
            items.add(newItem);
        }

        return new Menu(items);
    }

    public static void addItemToMenu(FoodItem item, Integer restaurantId, String path) {
        List<String[]> lines = new ArrayList<>();
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            while ((line = br.readLine()) != null) {
                String[] cells = line.split(",");
                if (cells[1].trim().equals(item.getName()) && Integer.parseInt(cells[5].trim()) == restaurantId) {
                    System.out.println("Item already exists.");
                    return;
                }

                lines.add(cells);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            FileWriter fw = new FileWriter(path, true);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write((lines.size() + 1) + ", " + item.getName() + ", " + item.getPrice() + ", " + item.getDescription() + ", " + item.isAvailable() + ", " + item.getRestaurantId());
            bw.newLine();

            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void removeItemFromMenu(String itemName, Restaurant restaurant, String path) {
        List<String[]> lines = new ArrayList<>();
        String line;
        boolean found = false;

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            while ((line = br.readLine()) != null) {
                String[] cells = line.split(",");

                if (cells[1].trim().equals(itemName) && Integer.parseInt(cells[5].trim()) == restaurant.getId()){
                    found = true;
                    continue;
                }

                lines.add(cells);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!found) {
            System.out.println("Item not found.");
            return;
        }

        lines = lines.subList(1, lines.size());

        CSVOperations.writeMenu(path, lines);
    }

    public static void writeMenu(String path, List<String[]> menu) {
        try {
            FileWriter fw = new FileWriter(path);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write("id,  name,  price,  description,  is_available");
            bw.newLine();

            for (String[] item : menu) {
                bw.write(String.join(", ", item));
                bw.newLine();
            }

            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeUsers(String path, List<String[]> users) {
        try {
            FileWriter fw = new FileWriter(path);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write("id,  name,  email,  password,  balance, is_superuser");
            bw.newLine();

            for (String[] user : users) {
                bw.write(String.join(", ", user));
                bw.newLine();
            }

            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String[]> getFileLines(String path) {
        List<String[]> lines = new ArrayList<>();
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            while ((line = br.readLine()) != null) {
                String[] cells = line.split(",");
                lines.add(cells);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        lines = lines.subList(1, lines.size());
        
        return lines;
    }

    /* Restaurant actions */

    public static List<Restaurant> loadRestaurants(String path) {
        List<Restaurant> restaurants = new ArrayList<>();
        List<String[]> lines = new ArrayList<>();
        lines = getFileLines(path);

        for (String[] restaurant : lines) {
            Integer id = Integer.parseInt(restaurant[0].trim());
            String name = restaurant[1].trim();
            String address = restaurant[2].trim();
            Restaurant newRestaurant = new Restaurant(id, name, address);
            restaurants.add(newRestaurant);
        }

        return restaurants;
    }

    public static void writeRestaurants(String path, List<String[]> restaurants) {
        try {
            FileWriter fw = new FileWriter(path);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write("id,  name,  address");
            bw.newLine();

            for (String[] restaurant : restaurants) {
                bw.write(String.join(", ", restaurant));
                bw.newLine();
            }

            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addRestaurant(Restaurant restaurant) {
        List<String[]> lines = new ArrayList<>();
        String line;
        String path = "data/restaurants.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            while ((line = br.readLine()) != null) {
                String[] cells = line.split(",");
                if (cells[1].trim().equals(restaurant.getName()) && cells[2].trim().equals(restaurant.getAddress())) {
                    System.out.println("Restaurant already exists.");
                    return;
                }

                lines.add(cells);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            FileWriter fw = new FileWriter(path, true);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write((lines.size()) + ", " + restaurant.getName() + ", " + restaurant.getAddress());
            bw.newLine();

            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // * Find restaurant by id
    public static Restaurant getRestaurantById(int id) {
        List<Restaurant> restaurants = loadRestaurants("data/restaurants.csv");

        for (Restaurant restaurant : restaurants) {
            if (restaurant.getId() == id) {
                return restaurant;
            }
        }

        return null;
    }

    // * Find restaurant by name and address
    public static Restaurant getRestaurantByNameAndAddress(String name, String address) {
        List<Restaurant> restaurants = loadRestaurants("data/restaurants.csv");

        for (Restaurant restaurant : restaurants) {
            if (restaurant.getName().equals(name) && restaurant.getAddress().equals(address)) {
                return restaurant;
            }
        }

        return null;
    }

    public static List<Restaurant> getAvailableRestaurants(String item_name, Menu menu) {
        List<Restaurant> restaurants = new ArrayList<>();
        for (FoodItem item : menu.getItems()) {
            if (item.getName().equals(item_name) && item.isAvailable()) {
                Restaurant restaurant = getRestaurantById(item.getRestaurantId());
                restaurants.add(restaurant);
            }
        }

        return restaurants;
    }

    /* Cart operations */

    public static void addCartItemToFile(CartItem item, String path) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        
        try {
            FileWriter fw = new FileWriter(path, true);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(item.getCartId() + ", " + item.getRestaurantId() + ", " + item.getName() + ", " + item.getQuantity() + ", " + item.getPrice() + ", " + dtf.format(now));
            bw.newLine();

            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Cart getCart(int user_id) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
        LocalDateTime now = LocalDateTime.now();  

        List<String[]> lines = CSVOperations.getFileLines("data/cart.csv");
        boolean found = false;
        int cart_id = -1;

        for (String[] line : lines) {
            if (Integer.parseInt(line[1].trim()) == user_id) {
                found = true;
                cart_id = Integer.parseInt(line[0].trim());
                break;
            }
        }

        if (!found) {
            cart_id = lines.size() + 1;
            try {
                FileWriter fw = new FileWriter("data/cart.csv", true);
                BufferedWriter bw = new BufferedWriter(fw);

                bw.write(cart_id + ", " + user_id + ", " + dtf.format(now));
                bw.newLine();

                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return new Cart(new ArrayList<>(), cart_id);
        } else {
            List<String[]> cartItemLines = CSVOperations.getFileLines("data/cartItem.csv");
            List<CartItem> items = new ArrayList<>();
            for (String[] line : cartItemLines) {
                if (Integer.parseInt(line[0].trim()) == cart_id) {
                    cart_id = Integer.parseInt(line[0].trim());
                    int restaurant_id = Integer.parseInt(line[1].trim());
                    String name = line[2].trim();
                    int quantity = Integer.parseInt(line[3].trim());
                    double price = Double.parseDouble(line[4].trim());
                    CartItem item = new CartItem(name, quantity, price, restaurant_id, cart_id);
                    items.add(item);
                }
            }

            return new Cart(items, cart_id);
        }
    }
    public static void updateCartItemInFile(CartItem item, String path) {
        // What is it?
        List<String[]> lines = CSVOperations.getFileLines(path);
        boolean found = false;

        for (String[] line : lines) {
            if (Integer.parseInt(line[0].trim()) == item.getCartId() && line[2].trim().equals(item.getName())) {
                line[3] = Integer.toString(item.getQuantity());
                found = true;
            }
        }

        if (!found) {
            System.out.println("Item not found.");
            return;
        }

        CSVOperations.writeCartItems(path, lines);
    }

    // Function to remove cartItem

    public static void writeCartItems(String path, List<String[]> cartItems) {
        try {
            FileWriter fw = new FileWriter(path);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write("cart_id,  restaurant_id,  product,  quantity,  price,  created_at");
            bw.newLine();

            for (String[] item : cartItems) {
                bw.write(String.join(", ", item));
                bw.newLine();
            }

            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
