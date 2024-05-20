import java.util.List;

public class Validation {
    public static boolean Authenticated(String email, String password, User user) {
        String userEmail = user.getEmail();
        String userPassword = user.getPassword();
        if (userEmail.equals(email) && userPassword.equals(password)) {
            return true;
        }

        return false;
    }

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
