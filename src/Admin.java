import java.util.List;

public class Admin extends BaseUser {
    public Admin(Integer id, String name, String email, String password, boolean is_superuser) {
        super(id, name, email, password, is_superuser);
    }

    public void addNewAdmin(String name, String newPassword, String newEmail) {
        List<String[]> userLines = CSVOperations.getFileLines("data/users.csv");
        if (!CSVOperations.userIsAvailableInUsers(userLines, newEmail)) {
            userLines.add(new String[] { Integer.toString(userLines.size() + 1), name, newEmail, newPassword, "0", "true" });
            CSVOperations.writeUsers("data/users.csv", userLines);
        } else {
            System.out.println("Email already exists.");
        }
    }

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

    public void addNewRestaurant(String restaurantName, String restaurantAddress) {
        Restaurant restaurant = new Restaurant(-1, restaurantName, restaurantAddress);
        CSVOperations.addRestaurant(restaurant);
    }

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
