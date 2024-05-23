import java.util.List;

public class Admin extends BaseUser {
    public Admin(Integer id, String name, String email, String password, boolean is_superuser) {
        super(id, name, email, password, is_superuser);
    }

    /**
     * @param name the name of the new admin
     * @param newPassword the password of the new admin
     * @param newEmail the email of the new admin
     * @return void
     * 
     * This method adds a new admin to the users.csv file
     */
    public void addNewAdmin(String name, String newPassword, String newEmail) {
        List<String[]> userLines = CSVOperations.getFileLines("data/users.csv");
        if (!CSVOperations.userIsAvailableInUsers(userLines, newEmail)) {
            userLines.add(new String[] { Integer.toString(userLines.size() + 1), name, newEmail, newPassword, "0", "true" });
            CSVOperations.writeUsers("data/users.csv", userLines);
        } else {
            System.out.println("Email already exists.");
        }
    }

    /**
     * @param emailToRemove the email of the user to remove
     * @return void
     * 
     * This method removes a user from the users.csv file by email
     */
    public void removeUser(String emailToRemove) {
        List<String[]> userLines = CSVOperations.getFileLines("data/users.csv");
        if (!CSVOperations.userIsAvailableInUsers(userLines, emailToRemove)) {
            System.out.println("Email doesn't exist.");
            return;
        } 

        for (int i = 0; i < userLines.size(); i++) {
            if (userLines.get(i)[2].trim().equals(emailToRemove.trim())) {
                userLines.remove(i);
            }
        }

        CSVOperations.writeUsers("data/users.csv", userLines);
        System.out.println("User removed.");
    }

    /**
     * @param restaurantName the name of the new restaurant
     * @param restaurantAddress the address of the new restaurant
     * @return void
     * 
     * This method adds a new restaurant to the restaurants.csv file
     */
    public void addNewRestaurant(String restaurantName, String restaurantAddress) {
        Restaurant restaurant = new Restaurant(-1, restaurantName, restaurantAddress); // id is -1 because it will be auto incremented in the csv file
        CSVOperations.addRestaurant(restaurant);
    }

    /**
     * @param restaurantName the name of the restaurant to remove
     * @param restaurantAddress the address of the restaurant to remove
     * @return void
     * 
     * This method removes a restaurant from the restaurants.csv file by name and address
     */
    public void removeRestaurant(String restaurantName, String restaurantAddress) {
        Restaurant restaurant = CSVOperations.getRestaurantByNameAndAddress(restaurantName, restaurantAddress);

        if (restaurant == null) {
            System.out.println("Restaurant not found.");
            return;
        }

        List<String[]> restaurantLines = CSVOperations.getFileLines("data/restaurants.csv");
        for (int i = 0; i < restaurantLines.size(); i++) {
            if (restaurantLines.get(i)[1].trim().equals(restaurantName.trim()) && restaurantLines.get(i)[2].trim().equals(restaurantAddress.trim())) {
                restaurantLines.remove(i);
            }
        }

        CSVOperations.writeRestaurants("data/restaurants.csv", restaurantLines);
        System.out.println("Restaurant removed.");
    }
}
