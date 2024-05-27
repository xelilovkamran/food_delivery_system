import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;    

/**
 * This class contains the methods to perform operations on CSV files
 */
public class CSVOperations {
    /**
     * @param path the path to the file
     * @return List<String[]>
     * 
     * This method reads the file and returns a list of User objects
     */
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

    /**
     * @param email the email of the user
     * @param password the password of the user
     * @return User
     * 
     * This method returns the user object if the email and password match
     * otherwise it returns null
     */
    public static User getUser(String email, String password) {
        List<User> users = loadUsers("data/users.csv");

        for (User user : users) {
            if (Validation.Authenticated(email, password, user)) {
                return user;
            }
        }

        return null;
    }

    /**
     * @param email the email of the user
     * @param password the password of the user
     * @return Admin
     * 
     * This method returns the admin object if the email and password match
     * and the user is a superuser
     * otherwise it returns null
     */
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

    /**
     * @param path the path to the file
     * @param email the email of the user
     * @param newPassword the new password
     * @return boolean
     * 
     * This method updates the password of the user with the given email in the file at the given path
     * and returns true if the password is updated successfully
     * otherwise it returns false
     */
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

    /**
     * @param path the path to the file
     * @param email the email of the user
     * @param newEmail the new email
     * @return boolean
     * 
     * This method updates the email of the user with the given email in the file at the given path
     * and returns true if the email is updated successfully
     * otherwise it returns false
     */
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

    /**
     * @param path the path to the file
     * @param email the email of the user
     * @param amount the amount to update the balance
     * @return boolean
     * 
     * This method updates the balance of the user with the given email in the file at the given path
     * and returns true if the balance is updated successfully
     * otherwise it returns false
     */
    public static boolean updateBalance(String path, String email, double amount) {
        List<String[]> userLines = new ArrayList<>();
        boolean updated = false;

        userLines = getFileLines(path);

        for (String[] userLine : userLines) {
            if (userLine[2].trim().equals(email)) {
                double balance = Double.parseDouble(userLine[4].trim());
                balance += amount;
                userLine[4] = Double.toString(balance);
                updated = true;
                break;
            }
        }

        CSVOperations.writeUsers(path, userLines);
        return updated;
    }

    /**
     * @param userLines the list of user lines from the users file
     * @param email the email of the user
     * 
     * This method returns true if the user is available in the users file
     * otherwise it returns false
     */
    public static boolean userIsAvailableInUsers(List<String[]> userLines, String email) {
        for (String[] userLine : userLines) {
            if (userLine[2].trim().equals(email)) {
                return true;
            }
        }

        return false;
    }

    /**
     * @param path the path to the file
     * @return Menu
     * 
     * This method reads the file and returns a Menu object
     */
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

    /**
     * @param item the item to add to the menu
     * @param restaurantId the id of the restaurant
     * @param path the path to the file
     * @return void
     * 
     * This method adds an item to the menu file at the given path
     * if the item already exists, it prints "Item already exists."
     * otherwise it adds the item to the file
     */
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

    /**
     * @param itemName the name of the item to remove
     * @param restaurant the restaurant object
     * @param path the path to the file
     * @return void
     * 
     * This method removes an item from the menu file at the given path
     * if the item is not found, it prints "Item not found."
     * otherwise it removes the item from the file
     * 
     * Note: The first line of the file is the header
     */
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

    /**
     * @param path the path to the file
     * @param menu the menu lines to write to the file
     * @return void
     * 
     * This method writes the menu to the file at the given path
     * 
     * Note: The first line of the file is the header
     */
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

    /**
     * @param path the path to the file
     * @param users the list of user lines to write to the file
     * @return void
     * 
     * This method writes the users to the file at the given path
     * 
     * Note: The first line of the file is the header
     */
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

    /**
     * @param path the path to the file
     * @return List<String[]>
     * 
     * This method reads the file and returns a list of lines
     * 
     * Note: The first line of the file is the header
     */
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

    /* RESTAURANT ACTIONS */

    /**
     * @param path the path to the file
     * @return List<Restaurant>
     * 
     * This method reads the file and returns a list of Restaurant objects
     */
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

    /**
     * @param path the path to the file
     * @param restaurants the list of restaurant lines to write to the file
     * @return void
     * 
     * This method writes the restaurants to the file at the given path
     * 
     * Note: The first line of the file is the header
     */
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

    /**
     * @param restaurant the restaurant to add to the file
     * @return void
     * 
     * This method adds a restaurant to the file at the given path
     * if the restaurant already exists, it prints "Restaurant already exists."
     * otherwise it adds the restaurant to the file
     */
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

    /**
     * @param id the id of the restaurant
     * @return Restaurant
     * 
     * This method returns the restaurant object with the given id
     * otherwise it returns null
     */
    public static Restaurant getRestaurantById(int id) {
        List<Restaurant> restaurants = loadRestaurants("data/restaurants.csv");

        for (Restaurant restaurant : restaurants) {
            if (restaurant.getId() == id) {
                return restaurant;
            }
        }

        return null;
    }

    /**
     * @param name the name of the restaurant
     * @param address the address of the restaurant
     * @return Restaurant
     * 
     * This method returns the restaurant object with the given name and address
     * otherwise it returns null
     */
    public static Restaurant getRestaurantByNameAndAddress(String name, String address) {
        List<Restaurant> restaurants = loadRestaurants("data/restaurants.csv");

        for (Restaurant restaurant : restaurants) {
            if (restaurant.getName().equals(name) && restaurant.getAddress().equals(address)) {
                return restaurant;
            }
        }

        return null;
    }

    /**
     * @param item_name the name of the item
     * @param menu the menu object
     * @return List<Restaurant>
     * 
     * This method returns a list of restaurants that have the given item available from the menu
     */
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

    /* CART OPERATIONS */

    /**
     * @param item the item to add to the cart
     * @param path the path to the file
     * @return void
     * 
     * This method adds a cart item to the file at the given path 
     */
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

    /**
     * @param user_id the id of the user
     * @return Cart
     * 
     * This method returns the cart object of the user with the given id
     * if the cart does not exist, it creates a new cart and adds it to the file, then returns the cart object
     * otherwise it returns the cart object
     */
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

    /**
     * @param item the item to add to the cart
     * @param path the path to the file
     * @return void
     * 
     * This method updates quantity of the cart item in the file at the given path
     * if the item is not found, it prints "Item not found."
     * otherwise it updates the quantity of the item in the file 
     */
    public static void updateCartItemInFile(CartItem item, String path) {
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

    /**
     * @param item the item to delete from the cart
     * @param path the path to the file
     * @return void
     * 
     * This method deletes the cart item from the file at the given path
     * if the item is not found, it prints "Item not found."
     * otherwise it deletes the item from the file
     */
    public static void deleteCartItemFromFile(CartItem item, String path) {
        List<String[]> lines = new ArrayList<>();
        String line;
        boolean found = false;

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            while ((line = br.readLine()) != null) {
                String[] cells = line.split(",");

                // Skip the header
                if (cells[0].trim().equals("cart_id")) {
                    continue;
                }

                // Check if the item is found
                if (Integer.parseInt(cells[0].trim()) == item.getCartId() && cells[2].trim().equals(item.getName()) && Integer.parseInt(cells[1].trim()) == item.getRestaurantId()) {
                    System.out.println("Item removed from cart.");
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

        CSVOperations.writeCartItems(path, lines);
    }

    /**
     * @param path the path to the file
     * @param cartItems the list of cart item lines to write to the file
     * @return void
     * 
     * This method writes the cart items to the file at the given path
     * 
     * Note: The first line of the file is the header
     */
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

    /**
     * @param path the path to the file
     * @param carts the list of cart lines to write to the file
     * @return void
     * 
     * This method writes the carts to the file at the given path
     * 
     * Note: The first line of the file is the header
     */
    public static void writeCarts(String path, List<String[]> carts) {
        try {
            FileWriter fw = new FileWriter(path);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write("id,  user_id,  created_at");
            bw.newLine();

            for (String[] cart : carts) {
                bw.write(String.join(", ", cart));
                bw.newLine();
            }

            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /* ORDER ACTIONS  */

    /**
     * @param path the path to the file
     * @param cart the cart object
     * @param user the user object
     * @param address the address of the user
     * @param instructions the additional notes for the order
     * @return boolean
     * 
     * This method places an order for the cart and user at the given path with the given address and instructions
     * and returns true if the order is placed successfully
     * otherwise it returns false
     */
    public static boolean placeOrder(String path, Cart cart, User user, String address, String instructions) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        // * add item to orders file
        try {
            FileWriter fw = new FileWriter(path, true);
            BufferedWriter bw = new BufferedWriter(fw);

            for (CartItem item : cart.getItems()) {
                bw.write(user.getId()  + ", " + item.getName() + ", " + item.getQuantity() + ", " + item.getPrice() * item.getQuantity() + ", " + address.replaceAll(",", " ") + ", " + "balance, " + item.getRestaurantId() + ", " + true + ", " + instructions + ", " + dtf.format(now));
                bw.newLine();
            }

            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        // * remove cart item from cartItem file
        List<String[]> cartItemLines = CSVOperations.getFileLines("data/cartItem.csv");
        List<String[]> newCartItems = new ArrayList<>();

        for (String[] line : cartItemLines) {
            if (Integer.parseInt(line[0].trim()) != cart.getId()) {
                newCartItems.add(line);
            }
        }

        CSVOperations.writeCartItems("data/cartItem.csv", newCartItems);

        // * update cart in cart file
        List<String[]> cartLines = CSVOperations.getFileLines("data/cart.csv");
        List<String[]> newCartLines = new ArrayList<>();

        for (String[] line : cartLines) {
            if (Integer.parseInt(line[0].trim()) == cart.getId()) {
                line[2] = dtf.format(now);
            }
            newCartLines.add(line);
        }

        CSVOperations.writeCarts("data/cart.csv", newCartLines);

        return true;
    }
}
