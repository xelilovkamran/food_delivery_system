import java.util.List;

public class Validation {

    /**
     * @param email the email of the user
     * @param password the password of the user
     * @param user the user object
     * @return boolean
     * 
     * This method checks if the user is authenticated
     */
    public static boolean Authenticated(String email, String password, User user) {
        String userEmail = user.getEmail();
        String userPassword = user.getPassword();
        if (userEmail.equals(email) && userPassword.equals(password)) {
            return true;
        }

        return false;
    }

    /**
     * @param email the email of the user
     * @param password the password of the user
     * @return boolean
     * 
     * This method checks if the user is an admin
     */
    public static boolean isAdmin(String email, String password) {
        List<User> users = CSVOperations.loadUsers("data/users.csv");
        
        for (User user : users) {
            boolean is_superuser = user.isSuperuser();
            if (Validation.Authenticated(email, password, user) && is_superuser) {
                return true;
            }
        }

        return false;
    }

    /**
     * @param email the email of the user
     * @param password the password of the user
     * @return boolean
     * 
     * This method checks if the user is a customer
     */
    public static boolean isCustomer(String email, String password) {
        List<User> users = CSVOperations.loadUsers("data/users.csv");
        
        for (User user : users) {
            boolean is_superuser = user.isSuperuser();
            if (Validation.Authenticated(email, password, user) && !is_superuser) {
                return true;
            }
        }

        return false;
    }
}
